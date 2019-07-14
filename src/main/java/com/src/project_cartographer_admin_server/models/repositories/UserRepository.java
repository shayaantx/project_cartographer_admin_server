package com.src.project_cartographer_admin_server.models.repositories;

import com.src.project_cartographer_admin_server.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {}