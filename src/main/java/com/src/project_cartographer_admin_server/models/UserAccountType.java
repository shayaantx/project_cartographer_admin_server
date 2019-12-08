package com.src.project_cartographer_admin_server.models;

/**
 * 1 == 4 : Public Network
 * 2 == 5 : Private Network
 * 3 == 6 : Cheater Network
 */
public enum UserAccountType {
  //hibernate can map ordinals or the actual enum names, the ordinals are 0-5, when stored initially, but since they are stored currently 1-6, we can get mapping issues
  //we use this dummy row to workaround it
  DUMMY,
  NORMAL_PUBLIC,
  NORMAL_PRIVATE,
  NORMAL_CHEATER,
  DEVELOPER_PUBLIC,
  DEVELOPER_PRIVATE,
  DEVELOPER_CHEATER
}
