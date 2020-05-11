package com.src.project_cartographer_admin_server.services;

import com.src.project_cartographer_admin_server.businessobjects.NewUserBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Unfortunately the cartographer activation emails have been blacklist by hotmail and often get put in spam folder by other
 * email providers. So we will activate new users periodically (every 5 minutes) instead
 */
@Service
public class NewUserActivator {
  @Scheduled(fixedDelay = 360000)
  public void scheduleFixedDelayTask() {
    newUserBO.activateAll();
  }

  @Autowired
  private NewUserBO newUserBO;

  @Value("${new.user.activator:false}")
  private boolean activateNewUsers;
}
