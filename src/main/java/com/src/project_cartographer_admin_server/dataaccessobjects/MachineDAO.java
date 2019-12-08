package com.src.project_cartographer_admin_server.dataaccessobjects;

import com.src.project_cartographer_admin_server.models.Machine;
import org.springframework.stereotype.Component;

@Component
public class MachineDAO extends CommonDAO<Machine> {
  @Override
  public Class<Machine> getClazz() {
    return Machine.class;
  }
}
