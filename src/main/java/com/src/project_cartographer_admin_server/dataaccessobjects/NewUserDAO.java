package com.src.project_cartographer_admin_server.dataaccessobjects;

import com.src.project_cartographer_admin_server.models.NewUser;
import org.springframework.stereotype.Component;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Component
public class NewUserDAO extends CommonDAO<NewUser> {
  public List<NewUser> getUsers() {
    Query newUsers = entityManager.createQuery("select u from NewUser u order by u.username");
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

  @Override
  public Class<NewUser> getClazz() {
    return NewUser.class;
  }
}
