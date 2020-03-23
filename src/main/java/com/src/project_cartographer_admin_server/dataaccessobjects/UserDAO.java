package com.src.project_cartographer_admin_server.dataaccessobjects;

import com.src.project_cartographer_admin_server.models.Machine;
import com.src.project_cartographer_admin_server.models.User;
import org.springframework.stereotype.Component;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by shayaantx on 3/24/2018.
 */
@Component
public class UserDAO extends CommonDAO<User> {
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
    final Query searchQuery;
    if (filterText.matches("-?\\d+")) {
      //search by id instead
      searchQuery = entityManager.createQuery("select u from User u where u.userId = :searchText");
      searchQuery.setParameter("searchText", Integer.valueOf(filterText));
    } else {
      searchQuery = entityManager.createQuery("select u from User u where lower(u.username) like :searchText or u.userId = :searchText or lower(u.email) like :searchText or u.ipAddress like :searchText");
      searchQuery.setParameter("searchText", "%" + filterText.toLowerCase() + "%");
    }
    List users = searchQuery.getResultList();
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

  @Override
  public Class<User> getClazz() {
    return User.class;
  }
}
