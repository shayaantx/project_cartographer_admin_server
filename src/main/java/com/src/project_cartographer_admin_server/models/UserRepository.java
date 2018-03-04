package com.src.project_cartographer_admin_server.models;

import com.src.project_cartographer_admin_server.models.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by shayaantx on 1/21/2018.
 */
public interface UserRepository extends CrudRepository<User, Long> {

}