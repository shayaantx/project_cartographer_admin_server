package com.src.project_cartographer_admin_server.businessobjects;

import com.src.project_cartographer_admin_server.dataaccessobjects.NewUserDAO;
import com.src.project_cartographer_admin_server.models.NewUser;
import com.src.project_cartographer_admin_server.transactions.DisplayColumn;
import com.src.project_cartographer_admin_server.transactions.DisplayRow;
import com.src.project_cartographer_admin_server.transactions.EditNewUserEmailRequest;
import com.src.project_cartographer_admin_server.transactions.ResendNewUserEmailRequest;
import com.src.project_cartographer_admin_server.utils.EmailHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.src.project_cartographer_admin_server.utils.EmailHelper.isValidEmailAddress;

@Component
public class NewUserBO {
  private static final Logger LOGGER = LogManager.getLogger();
  @Autowired
  private NewUserDAO newUserDAO;

  private static void sendActivationEmailTemplate(String activationToken, String username, String email) {
    String activationTemplate = getActivationEmailTemplate();
    String subject = "Halo 2 Account Activation: " + username;
    String activationUrl = "https://cartographer.online/activate1?key=" + activationToken;
    EmailHelper.sendEmail(email, subject, String.format(activationTemplate, activationUrl, activationUrl));
  }

  private static String getActivationEmailTemplate() {
    Resource resource = new ClassPathResource("activationEmailTemplate.txt");

    try {
      try (InputStream inputStreamSource = resource.getInputStream()) {
        final int bufferSize = 1024;
        final char[] buffer = new char[bufferSize];
        final StringBuilder out = new StringBuilder();
        Reader in = new InputStreamReader(inputStreamSource, "UTF-8");
        for (; ; ) {
          int rsz = in.read(buffer, 0, buffer.length);
          if (rsz < 0)
            break;
          out.append(buffer, 0, rsz);
        }
        return out.toString();
      }
    } catch (Exception e) {
      throw new RuntimeException("Error getting the activation email template", e);
    }
  }

  public ModelAndView getNewUsers() {
    List<DisplayColumn> headerRow = new ArrayList<>();
    headerRow.add(new DisplayColumn("username"));
    headerRow.add(new DisplayColumn("activationToken"));
    headerRow.add(new DisplayColumn("validEmail"));
    headerRow.add(new DisplayColumn("email"));
    headerRow.add(new DisplayColumn("actions"));

    List<DisplayRow> users = new ArrayList<>();
    Iterable<NewUser> newUsers = newUserDAO.getUsers();
    for (NewUser newUser : newUsers) {
      List<String> values = new ArrayList<>();
      values.add(newUser.getUsername());
      values.add(newUser.getValidationToken());
      values.add(String.valueOf(isValidEmailAddress(newUser.getEmail())));
      values.add(newUser.getEmail());
      values.add("actions");
      users.add(new DisplayRow(values));
    }
    return new ModelAndView("newUsers")
      .addObject("headerRow", headerRow)
      .addObject("newUsers", users);
  }

  @Transactional
  public ModelAndView activateUser(String validationToken) {
    try {
      URL url = new URL("https://cartographer.online/activate1?key=" + validationToken);
      HttpURLConnection con = (HttpURLConnection) url.openConnection();
      con.setRequestMethod("GET");

      try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
        String inputLine;
        boolean success = false;
        while ((inputLine = in.readLine()) != null) {
          if (inputLine.contains("Account Successfully Activated")) {
            LOGGER.info("Succesfully activated validation token " + validationToken);
            success = true;
          }
        }
        if (!success) {
          LOGGER.error("Could not activate token " + validationToken);
        }
      }
    } catch (Exception e) {
      LOGGER.error("Error sending activate request");
    }
    return getNewUsers();
  }

  @Transactional
  public boolean editEmail(EditNewUserEmailRequest request) {
    NewUser newUser = newUserDAO.loadEntity(request.username);
    newUser.setEmail(request.email);
    return true;
  }

  public boolean resendActivationEmail(ResendNewUserEmailRequest request) {
    NewUser newUser = newUserDAO.loadEntity(request.getUsername());
    sendActivationEmailTemplate(newUser.getValidationToken(), newUser.getUsername(), newUser.getEmail());
    return true;
  }
}
