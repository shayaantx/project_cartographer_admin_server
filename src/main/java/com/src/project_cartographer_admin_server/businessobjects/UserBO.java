package com.src.project_cartographer_admin_server.businessobjects;

import com.src.project_cartographer_admin_server.dataaccessobjects.UserDAO;
import com.src.project_cartographer_admin_server.models.Machine;
import com.src.project_cartographer_admin_server.models.User;
import com.src.project_cartographer_admin_server.models.UserAccountType;
import com.src.project_cartographer_admin_server.transactions.DisplayColumn;
import com.src.project_cartographer_admin_server.transactions.DisplayRow;
import com.src.project_cartographer_admin_server.transactions.FilterResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by shayaantx on 1/25/2018.
 */
@Component
public class UserBO {
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
                user = entityManager.getReference(User.class, idParam);
            }
            if (username != null && !username.isEmpty()) {
                user = userDAO.getUserByUsername(username);
                if (user == null) {
                    modelAndView.addObject("error", "Could not find user for username " + username);
                }
            }
            if (user != null) {
                fillUserTransaction(modelAndView, user);
                hasUser = true;
            }
        } catch (Exception e) {
            LOGGER.error("Error looking up user", e);
            modelAndView.addObject("error", e.getMessage());
        }
        modelAndView.addObject("hasUser", hasUser);
        return modelAndView;
    }

    private static void fillUserTransaction(ModelAndView modelAndView, User user) {
        modelAndView.addObject("id", user.getUserId());
        modelAndView.addObject("username", user.getUsername());
        modelAndView.addObject("email", user.getEmail());
        modelAndView.addObject("ipAddress", user.getIpAddress());
        modelAndView.addObject("loginDttm", user.getLastLoginDttm() != null ? DATE_FORMAT.format(user.getLastLoginDttm()) : null);
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
    }

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

    @Transactional
    public ModelAndView banUser(Integer userIdParam) {
        try {
            transactionHelper.banUser(userIdParam);
            return getSuccesfulUser(userIdParam, "banned user");
        } catch (Exception e) {
            LOGGER.error("Error banning user", e);
        }
        return getErrorUser(userIdParam, "failed to ban user");
    }

    @Transactional
    public ModelAndView unbanUser(Integer userIdParam) {
        try {
            transactionHelper.unbanUser(userIdParam);
            return getSuccesfulUser(userIdParam, "unbanned user");
        } catch (Exception e) {
            LOGGER.error("Error unbanning user", e);
        }
        return getErrorUser(userIdParam, "failed to unban user");
    }

    @Transactional
    public FilterResponse searchForUser(String filterText) {
        //transform the user model into the transaction
        List<DisplayRow> users = new ArrayList<>();
        Iterable<User> allUsers = userDAO.getUserByFilterText(filterText);
        for (User user : allUsers) {
            List<String> values = new ArrayList<>();
            values.add(user.getUserId().toString());
            values.add(user.getUsername());
            values.add(user.getEmail());
            values.add(user.getLastLoginDttm() != null ? DATE_FORMAT.format(user.getLastLoginDttm()) : null);
            values.add(user.getIpAddress());
            values.add(user.getUserAccountType().name());
            values.add(user.getUserAccountType().equals(UserAccountType.NORMAL_CHEATER) ? "BANNED" : "");
            users.add(new DisplayRow(values));
        }
        return new FilterResponse(users);
    }

    public ModelAndView updateUser(Integer userIdParam, String username, String email, String userType, String comments) {
        try {
            transactionHelper.updateUser(userIdParam, username, email, userType, comments);
            return getSuccesfulUser(userIdParam, "updated user");
        } catch (Exception e) {
            LOGGER.error("Error updating user", e);
        }
        return getErrorUser(userIdParam, "failed to update user");
    }

    public ModelAndView banMachine(Integer userIdParam, Integer machineId) {
        try {
            transactionHelper.banMachine(userIdParam, machineId);
            return getSuccesfulUser(userIdParam, "banned machine");
        } catch (Exception e) {
            LOGGER.error("Error banning machine", e);
        }
        return getErrorUser(userIdParam, "failed to ban machine");
    }

    public ModelAndView unbanMachine(Integer userIdParam, Integer machineId) {
        try {
            transactionHelper.unbanMachine(userIdParam, machineId);
            return getSuccesfulUser(userIdParam, "unbanned machine");
        } catch (Exception e) {
            LOGGER.error("Error unbanning machine", e);
        }
        return getErrorUser(userIdParam, "failed to unban machine");
    }

    public ModelAndView banAllMachines(Integer userIdParam) {
        try {
            transactionHelper.banAllMachines(userIdParam);
            return getSuccesfulUser(userIdParam, "banned all machines");
        } catch (Exception e) {
            LOGGER.error("Error banning all machines", e);
        }
        return getErrorUser(userIdParam, "failed to ban all machines");
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

    @Component
    public class TransactionHelper {
        @Transactional(value = Transactional.TxType.REQUIRES_NEW)
        public void banAllMachines(Integer userIdParam) {
            User user = entityManager.getReference(User.class, userIdParam);
            Set<Machine> machineSet = user.getMachines();
            for (Machine machine : machineSet) {
                machine.setBanned(true);
            }
        }

        @Transactional(value = Transactional.TxType.REQUIRES_NEW)
        public void banMachine(Integer userId, Integer machineId) {
            User user = entityManager.getReference(User.class, userId);
            Set<Machine> machineSet = user.getMachines();
            for (Machine machine : machineSet) {
                if (machine.getComp_id().equals(machineId)) {
                    machine.setBanned(true);
                    return;
                }
            }
            LOGGER.warn("Could not find machine for username " + user.getUsername() + ", machine id = " + machineId);
        }

        @Transactional(value = Transactional.TxType.REQUIRES_NEW)
        public void unbanMachine(Integer userId, Integer machineId) {
            User user = entityManager.getReference(User.class, userId);
            Set<Machine> machineSet = user.getMachines();
            for (Machine machine : machineSet) {
                if (machine.getComp_id().equals(machineId)) {
                    machine.setBanned(false);
                    return;
                }
            }
            LOGGER.warn("Could not find machine for username " + user.getUsername() + ", machine id = " + machineId);
        }

        @Transactional(value = Transactional.TxType.REQUIRES_NEW)
        public void unbanUser(Integer userIdParam) {
            User user = entityManager.getReference(User.class, userIdParam);
            user.setUserAccountType(UserAccountType.NORMAL_PUBLIC);
        }

        @Transactional(value = Transactional.TxType.REQUIRES_NEW)
        public void banUser(Integer userIdParam) {
            User user = entityManager.getReference(User.class, userIdParam);
            user.setUserAccountType(UserAccountType.NORMAL_CHEATER);
        }

        @Transactional(value = Transactional.TxType.REQUIRES_NEW)
        public void updateUser(Integer userIdParam, String username, String email, String userType, String comments) {
            User user = entityManager.getReference(User.class, userIdParam);
            if (!user.getUsername().equalsIgnoreCase(username)) {
                user.setUsername(username);
            }
            if (!email.equalsIgnoreCase(user.getEmail())) {
                user.setEmail(email);
            }
            if (userType != null && !userType.isEmpty()) {
                UserAccountType userAccountType = UserAccountType.valueOf(userType);
                if (!userAccountType.equals(user.getUserAccountType())) {
                    user.setUserAccountType(userAccountType);
                }
            }
            if (comments != null && !comments.isEmpty() && !comments.equalsIgnoreCase(user.getComments())) {
                user.setComments(comments);
            }
        }
        @PersistenceContext
        private EntityManager entityManager;
    }

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private TransactionHelper transactionHelper;

    @Autowired
    private UserDAO userDAO;

    @PersistenceContext
    private EntityManager entityManager;
}
