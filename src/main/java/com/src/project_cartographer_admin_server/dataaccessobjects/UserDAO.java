package com.src.project_cartographer_admin_server.dataaccessobjects;

import com.src.project_cartographer_admin_server.models.Machine;
import com.src.project_cartographer_admin_server.models.NewUser;
import com.src.project_cartographer_admin_server.models.User;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.*;

/**
 * Created by shayaantx on 3/24/2018.
 */
@Component
public class UserDAO {
  public List<User> getLinkedUsers(User user) {
    Set<User> linkedUsers = new HashSet<>();
    for (Machine machine : user.getMachines()) {
      for (User linkedUser : machine.getUsers()) {
        if (user.getUserId() != linkedUser.getUserId()) {
          linkedUsers.add(linkedUser);
        }
      }
    }
    return new ArrayList<>(linkedUsers);
  }
  public User getUserByUsername(String username) {
    Query usernameQuery = entityManager.createQuery("select u from User u where lower(u.username) = :username");
    usernameQuery.setParameter("username", username.toLowerCase());
    Object userObj = usernameQuery.getSingleResult();
    if (userObj == null) {
      return null;
    } else {
      return (User) userObj;
    }
  }

  public List<User> getUserByFilterText(String filterText) {
    Query usernameQuery = entityManager.createQuery("select u from User u where lower(u.username) like :username");
    usernameQuery.setParameter("username", "%" + filterText.toLowerCase() + "%");
    List users = usernameQuery.getResultList();
    if (users == null) {
      return null;
    } else {
      List<User> realUsers = new ArrayList<>();
      for (Object object : users) {
        realUsers.add((User) object);
      }
      return realUsers;
    }
  }

  public List<NewUser> getNewUsers() {
    Query newUsers = entityManager.createQuery("select u from NewUser u");
    List users = newUsers.getResultList();
    if (users == null) {
      return null;
    } else {
      List<NewUser> realUsers = new ArrayList<>();
      for (Object object : users) {
        realUsers.add((NewUser) object);
      }
      return realUsers;
    }
  }

  @PersistenceContext
  private EntityManager entityManager;
}
