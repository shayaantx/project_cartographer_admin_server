package com.src.project_cartographer_admin_server.businessobjects;

import com.src.project_cartographer_admin_server.dataaccessobjects.MachineDAO;
import com.src.project_cartographer_admin_server.dataaccessobjects.UserDAO;
import com.src.project_cartographer_admin_server.models.Machine;
import com.src.project_cartographer_admin_server.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class MachineBO {
  @Transactional
  public boolean banMachine(String machineId) {
    machineDAO.loadEntity(Integer.valueOf(machineId)).setBanned(true);
    return true;
  }

  @Transactional
  public boolean unbanMachine(String machineId) {
    machineDAO.loadEntity(Integer.valueOf(machineId)).setBanned(false);
    return true;
  }

  @Transactional
  public boolean banAllMachines(String userIdParam) {
    User user = userDAO.loadEntity(Integer.valueOf(userIdParam));
    for (Machine machine : user.getMachines()) {
      machine.setBanned(true);
    }
    return true;
  }

  @Autowired
  private UserDAO userDAO;

  @Autowired
  private MachineDAO machineDAO;
}
