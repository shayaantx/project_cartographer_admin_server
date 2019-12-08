package com.src.project_cartographer_admin_server.models.repositories;

import com.src.project_cartographer_admin_server.models.NewUser;
import org.springframework.data.repository.CrudRepository;

public interface NewUserRepository extends CrudRepository<NewUser, Long> {
}