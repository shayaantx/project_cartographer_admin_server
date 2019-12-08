package com.src.project_cartographer_admin_server.models.repositories;

import com.src.project_cartographer_admin_server.models.AdminUser;
import org.springframework.data.repository.CrudRepository;

public interface AdminUserRepository extends CrudRepository<AdminUser, Long> {
}
