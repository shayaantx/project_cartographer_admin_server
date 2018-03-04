package com.src.project_cartographer_admin_server.businessobjects;

import com.src.project_cartographer_admin_server.models.Machine;
import com.src.project_cartographer_admin_server.models.User;
import com.src.project_cartographer_admin_server.models.UserAccountType;
import com.src.project_cartographer_admin_server.models.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by shayaantx on 1/25/2018.
 */
@Component
public class UserBO {
    public ModelAndView getScreenConfig() {
        List<Column> headerRow = new ArrayList<>();
        headerRow.add(new Column("Id"));
        headerRow.add(new Column("name"));
        headerRow.add(new Column("email"));
        headerRow.add(new Column("last_login"));
        headerRow.add(new Column("ip_address"));
        headerRow.add(new Column("account_type"));
        headerRow.add(new Column("banned"));
        //transform the user model into the transaction
        List<Row> users = new ArrayList<>();
        Iterable<User> allUsers = userRepository.findAll();
        for (User user : allUsers) {
            List<String> values = new ArrayList<>();
            values.add(user.getUserId().toString());
            values.add(user.getUsername());
            values.add(user.getEmail());
            values.add(user.getLastLoginDttm() != null ? DATE_FORMAT.format(user.getLastLoginDttm()) : null);
            values.add(user.getIpAddress());
            values.add(user.getUserAccountType().name());
            values.add(user.getUserAccountType().equals(UserAccountType.NORMAL_CHEATER) ? "BANNED" : "");
            users.add(new Row(values));
        }

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
                .addObject("users", users)
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
                Query usernameQuery = entityManager.createQuery("select u from User u where lower(u.username) = :username");
                usernameQuery.setParameter("username", username.toLowerCase());
                Object userObj = usernameQuery.getSingleResult();
                if (userObj == null) {
                    modelAndView.addObject("error", "Could not find user for username " + username);
                } else {
                    user = (User) userObj;
                }
            }
            if (user != null) {
                modelAndView.addObject("username", user.getUsername());
                modelAndView.addObject("email", user.getEmail());
                modelAndView.addObject("ipAddress", user.getIpAddress());
                modelAndView.addObject("loginDttm", user.getLastLoginDttm() != null ? DATE_FORMAT.format(user.getLastLoginDttm()) : null);
                modelAndView.addObject("accountType", user.getUserAccountType().name());
                modelAndView.addObject("accountTypeFilterValues", getAccountTypeFilterValues());
                modelAndView.addObject("banned", user.getUserAccountType().equals(UserAccountType.NORMAL_CHEATER));

                List<Column> headerRow = new ArrayList<>();
                List<Row> machines = new ArrayList<>();
                headerRow.add(new Column("Id"));
                headerRow.add(new Column("serial"));
                headerRow.add(new Column("link"));
                headerRow.add(new Column("banned"));
                headerRow.add(new Column("comments"));
                headerRow.add(new Column("actions"));
                for (Machine machine : user.getMachines()) {
                    List<String> values = new ArrayList<>();
                    values.add(machine.getComp_id().toString());
                    values.add(machine.getSerial());
                    values.add(machine.getCompLink().toString());
                    values.add(String.valueOf(machine.isBanned()));
                    values.add(machine.getComments());
                    machines.add(new Row(values));
                }
                modelAndView.addObject("headerRow", headerRow);
                modelAndView.addObject("machines", machines);
                hasUser = true;
            }
        } catch (Exception e) {
            LOGGER.error("Error looking up user", e);
            modelAndView.addObject("error", e.getMessage());
        }
        modelAndView.addObject("hasUser", hasUser);
        return modelAndView;
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

    public ModelAndView banUser(Integer userIdParam) {
        return null;
    }

    private static class Column {
        public Column(String name) {
            this.name = name;
        }
        public final String name;
    }

    private static class Row {
        public Row(List<String> values) {
            this.values = values;
        }
        public final List<String> values;
    }

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private UserRepository userRepository;

    @PersistenceContext
    private EntityManager entityManager;
}
