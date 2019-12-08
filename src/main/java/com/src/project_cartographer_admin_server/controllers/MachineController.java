package com.src.project_cartographer_admin_server.controllers;

import com.src.project_cartographer_admin_server.businessobjects.MachineBO;
import com.src.project_cartographer_admin_server.transactions.BanAllMachinesRequest;
import com.src.project_cartographer_admin_server.transactions.BanMachineRequest;
import com.src.project_cartographer_admin_server.transactions.UnBanMachineRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MachineController {
  @RequestMapping(value = "/banMachine")
  @ResponseBody
  public boolean banMachine(@RequestBody BanMachineRequest request) {
    return machineBO.banMachine(request.getMachineId());
  }

  @RequestMapping(value = "/banAllMachines")
  @ResponseBody
  public boolean banAllMachines(@RequestBody BanAllMachinesRequest request) {
    return machineBO.banAllMachines(request.getUserId());
  }

  @RequestMapping(value = "/unbanMachine")
  @ResponseBody
  public boolean unbanMachine(@RequestBody UnBanMachineRequest request) {
    return machineBO.unbanMachine(request.getMachineId());
  }

  @Autowired
  private MachineBO machineBO;
}
