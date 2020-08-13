package com.src.project_cartographer_admin_server.businessobjects;

import com.src.project_cartographer_admin_server.dataaccessobjects.UserDAO;
import com.src.project_cartographer_admin_server.models.Machine;
import com.src.project_cartographer_admin_server.models.User;
import com.src.project_cartographer_admin_server.models.UserAccountType;
import com.src.project_cartographer_admin_server.transactions.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by shayaantx on 1/25/2018.
 */
@Component
public class UserBO {
  private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  private static final Logger LOGGER = LogManager.getLogger();
  private static final Logger LOGGER_AUDIT = LogManager.getLogger("Audit");
  @Autowired
  private TransactionHelper transactionHelper;
  @Autowired
  private UserDAO userDAO;

  private static List<String> getAccountTypeFilterValues() {
    List<String> accountTypeFilterValues = new ArrayList<>();
    accountTypeFilterValues.add("Select account type");
    for (UserAccountType userAccountType : UserAccountType.values()) {
      if (userAccountType == UserAccountType.DUMMY) {
        continue;
      }
      accountTypeFilterValues.add(userAccountType.name());
    }
    return accountTypeFilterValues;
  }

  private static Date getDttmFromEpocTime(String epocTimeStr) {
    if (epocTimeStr != null) {
      long epocTime = Long.parseLong(epocTimeStr);
      if (epocTime > 0) {
        return new Date(epocTime * 1000);
      }
    }
    return null;
  }

  public ModelAndView getScreenConfig() {
    List<DisplayColumn> headerRow = new ArrayList<>();
    headerRow.add(new DisplayColumn("Id"));
    headerRow.add(new DisplayColumn("name"));
    headerRow.add(new DisplayColumn("email"));
    headerRow.add(new DisplayColumn("last_login"));
    headerRow.add(new DisplayColumn("ip_address"));
    headerRow.add(new DisplayColumn("account_type"));
    headerRow.add(new DisplayColumn("banned"));
    List<String> accountTypeFilterValues = new ArrayList<>();
    accountTypeFilterValues.add("Select account type");
    for (UserAccountType userAccountType : UserAccountType.values()) {
      if (userAccountType == UserAccountType.DUMMY) {
        continue;
      }
      accountTypeFilterValues.add(userAccountType.name());
    }

    return new ModelAndView("users")
      .addObject("headerRow", headerRow)
      .addObject("accountTypeFilters", accountTypeFilterValues);
  }

  public ModelAndView loadUser(Integer idParam, String username, String email) {
    ModelAndView modelAndView = new ModelAndView("user");
    boolean hasUser = false;
    try {
      User user = null;
      if (idParam != null) {
        user = userDAO.loadEntity(idParam);
      }
      if (username != null && !username.isEmpty()) {
        user = userDAO.getUserByUsername(username);
        if (user == null) {
          modelAndView.addObject("error", "Could not find user for username " + username);
        }
      }
      if (user != null) {
        fillModelViewWithUserData(modelAndView, user);
        hasUser = true;
      }
    } catch (Exception e) {
      LOGGER.error("Error looking up user", e);
      modelAndView.addObject("error", e.getMessage());
    }
    modelAndView.addObject("hasUser", hasUser);
    return modelAndView;
  }

  private void fillModelViewWithUserData(ModelAndView modelAndView, User user) {
    modelAndView.addObject("id", user.getUserId());
    modelAndView.addObject("username", user.getUsername());
    modelAndView.addObject("email", user.getEmail());
    modelAndView.addObject("ipAddress", user.getIpAddress());
    Date lastLoginDttm = getDttmFromEpocTime(user.getLastLoginDttm());
    modelAndView.addObject("loginDttm", lastLoginDttm != null ? DATE_FORMAT.format(lastLoginDttm) : null);
    modelAndView.addObject("accountType", user.getUserAccountType().name());
    modelAndView.addObject("accountTypeFilterValues", getAccountTypeFilterValues());
    modelAndView.addObject("banned", user.getUserAccountType().equals(UserAccountType.NORMAL_CHEATER));
    modelAndView.addObject("comments", user.getComments());

    List<DisplayColumn> headerRow = new ArrayList<>();
    List<DisplayRow> machines = new ArrayList<>();
    headerRow.add(new DisplayColumn("Id"));
    headerRow.add(new DisplayColumn("serial1"));
    headerRow.add(new DisplayColumn("serial2"));
    headerRow.add(new DisplayColumn("serial3"));
    headerRow.add(new DisplayColumn("operatingSystem"));
    headerRow.add(new DisplayColumn("link"));
    headerRow.add(new DisplayColumn("machineBanned"));
    headerRow.add(new DisplayColumn("comments"));
    headerRow.add(new DisplayColumn("actions"));
    for (Machine machine : user.getMachines()) {
      List<String> values = new ArrayList<>();
      values.add(machine.getComp_id().toString());
      values.add(machine.getSerial1());
      values.add(machine.getSerial2());
      values.add(machine.getSerial3());
      values.add(machine.getOperatingSystem());
      values.add(machine.getCompLink().toString());
      values.add(String.valueOf(machine.isBanned()));
      values.add(machine.getComments());
      values.add(machine.isBanned() ? "unban" : "ban");
      machines.add(new DisplayRow(values));
    }
    modelAndView.addObject("headerRow", headerRow);
    modelAndView.addObject("machines", machines);

    List<DisplayColumn> linkedUsersHeader = new ArrayList<>();
    linkedUsersHeader.add(new DisplayColumn("Id"));
    linkedUsersHeader.add(new DisplayColumn("name"));
    linkedUsersHeader.add(new DisplayColumn("email"));
    linkedUsersHeader.add(new DisplayColumn("last_login"));
    linkedUsersHeader.add(new DisplayColumn("ip_address"));
    linkedUsersHeader.add(new DisplayColumn("account_type"));
    linkedUsersHeader.add(new DisplayColumn("banned"));
    modelAndView.addObject("linkedUsersHeader", linkedUsersHeader);
    modelAndView.addObject("users", populateUsersTransaction(userDAO.getLinkedUsers(user)));
  }

  public UserResponse banUser(BanUserRequest request) {
    transactionHelper.banUser(request.getId(), request.getComments());
    return toTransaction(request.getId());
  }

  public UserResponse unbanUser(UnbanUserRequest request) {
    transactionHelper.unbanUser(request.getId(), request.getComments());
    return toTransaction(request.getId());
  }

  @Transactional
  public FilterResponse searchForUser(String filterText) {
    //transform the user model into the transaction
    Iterable<User> allUsers = userDAO.getUserByFilterText(filterText);
    return new FilterResponse(populateUsersTransaction(allUsers));
  }

  private List<DisplayRow> populateUsersTransaction(Iterable<User> allUsers) {
    List<DisplayRow> users = new ArrayList<>();
    for (User user : allUsers) {
      Date lastLoginDttm = getDttmFromEpocTime(user.getLastLoginDttm());
      List<String> values = new ArrayList<>();
      values.add(user.getUserId().toString());
      values.add(user.getUsername());
      values.add(user.getEmail());
      values.add(lastLoginDttm != null ? DATE_FORMAT.format(lastLoginDttm) : null);
      values.add(user.getIpAddress());
      values.add(user.getUserAccountType().name());
      values.add(user.getUserAccountType().equals(UserAccountType.NORMAL_CHEATER) ? "BANNED" : "");
      users.add(new DisplayRow(values));
    }
    return users;
  }

  public UserResponse updateUser(Integer userIdParam, String username, String email, String userType, String comments) {
    transactionHelper.updateUser(userIdParam, username, email, userType, comments);
    return toTransaction(userIdParam);
  }

  @Transactional
  public ModelAndView getSuccesfulUser(Integer userIdParam, String successMessage) {
    ModelAndView modelAndView = loadUser(userIdParam, null, null);
    modelAndView.addObject("success", successMessage);
    return modelAndView;
  }

  @Transactional
  public ModelAndView getErrorUser(Integer userIdParam, String errorMessage) {
    ModelAndView modelAndView = loadUser(userIdParam, null, null);
    modelAndView.addObject("error", errorMessage);
    return modelAndView;
  }

  public org.springframework.security.core.userdetails.User getUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null) {
      return null;
    }
    return (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
  }

  private UserResponse toTransaction(int id) {
    User user = userDAO.loadEntity(id);
    return new UserResponse(
      user.getUserId(),
      user.getUsername(),
      user.getEmail(),
      user.getUserAccountType().name(),
      user.getComments(),
      user.getUserAccountType().equals(UserAccountType.NORMAL_CHEATER));
  }

  @Component
  public class TransactionHelper {
    @Autowired
    private UserDAO userDAO;

    @Transactional(value = Transactional.TxType.REQUIRES_NEW)
    public void banAllMachines(Integer userIdParam) {
      User user = userDAO.loadEntity(userIdParam);
      Set<Machine> machineSet = user.getMachines();
      for (Machine machine : machineSet) {
        machine.setBanned(true);
      }
      LOGGER_AUDIT.info("User " + getUser().getUsername() + " banned users " + user.getUsername() + " machines");
    }

    @Transactional(value = Transactional.TxType.REQUIRES_NEW)
    public void banMachine(Integer userId, Integer machineId) {
      User user = userDAO.loadEntity(userId);
      Set<Machine> machineSet = user.getMachines();
      for (Machine machine : machineSet) {
        if (machine.getComp_id().equals(machineId)) {
          machine.setBanned(true);
          LOGGER_AUDIT.info("User " + getUser().getUsername() + " banned users " + user.getUsername() + " machine id " + machine.getComp_id());
          return;
        }
      }
      LOGGER.warn("Could not find machine for username " + user.getUsername() + ", machine id = " + machineId);
    }

    @Transactional(value = Transactional.TxType.REQUIRES_NEW)
    public void unbanMachine(Integer userId, Integer machineId) {
      User user = userDAO.loadEntity(userId);
      Set<Machine> machineSet = user.getMachines();
      for (Machine machine : machineSet) {
        if (machine.getComp_id().equals(machineId)) {
          machine.setBanned(false);
          LOGGER_AUDIT.info("User " + getUser().getUsername() + " unbanned users " + user.getUsername() + " machine id " + machine.getComp_id());
          return;
        }
      }
      LOGGER.warn("Could not find machine for username " + user.getUsername() + ", machine id = " + machineId);
    }

    @Transactional(value = Transactional.TxType.REQUIRES_NEW)
    public void unbanUser(Integer userIdParam, String comments) {
      User user = userDAO.loadEntity(userIdParam);
      user.setUserAccountType(UserAccountType.NORMAL_PUBLIC);
      updateComments(comments, user);
      LOGGER_AUDIT.info("User " + getUser().getUsername() + " unbanned user " + user.getUsername());
    }

    @Transactional(value = Transactional.TxType.REQUIRES_NEW)
    public void banUser(Integer userIdParam, String comments) {
      User user = userDAO.loadEntity(userIdParam);
      user.setUserAccountType(UserAccountType.NORMAL_CHEATER);
      updateComments(comments, user);
      LOGGER_AUDIT.info("User " + getUser().getUsername() + " banned user " + user.getUsername());
    }

    @Transactional(value = Transactional.TxType.REQUIRES_NEW)
    public void updateUser(Integer userIdParam, String username, String email, String userType, String comments) {
      User user = userDAO.loadEntity(userIdParam);
      if (!user.getUsername().equals(username)) {
        user.setUsername(username);
        LOGGER_AUDIT.info("Username " + getUser().getUsername() + " changed to " + user.getUsername());
      }
      if (!email.equalsIgnoreCase(user.getEmail())) {
        user.setEmail(email);
        LOGGER_AUDIT.info("User email " + user.getEmail() + " changed to " + email + " email");
      }
      if (userType != null && !userType.isEmpty()) {
        UserAccountType userAccountType = UserAccountType.valueOf(userType);
        if (!userAccountType.equals(user.getUserAccountType())) {
          user.setUserAccountType(userAccountType);
          LOGGER_AUDIT.info("User " + user.getUserAccountType() + " type changed to " + userAccountType);
        }
      }

      updateComments(comments, user);
    }

    private void updateComments(String comments, User user) {
      if (Strings.isNotBlank(comments) && !comments.equalsIgnoreCase(user.getComments())) {
        //only change comments if they are not blank and not the same as original comments
        user.setComments(comments);
        LOGGER_AUDIT.info("User comments to changed " + comments);
      }
    }
  }
}
