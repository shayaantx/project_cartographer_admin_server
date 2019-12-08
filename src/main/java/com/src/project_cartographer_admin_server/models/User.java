package com.src.project_cartographer_admin_server.models;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Entity
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer userId;

  @Column(name = "username")
  private String username;

  @Column(name = "email")
  private String email;

  @Column(name = "last_log")
  private String lastLoginDttm;

  @Column(name = "user_ban_date")
  private Date userBanStartDate;

  @Column(name = "user_ban_date_end")
  private Date userBanEndDate;

  @Column(name = "user_ip")
  private String ipAddress;

  @Column(name = "user_comments")
  private String comments;

  @Enumerated(EnumType.ORDINAL)
  @Column(name = "user_type")
  private UserAccountType userAccountType;

  @ManyToMany
  @JoinTable(
    name = "user_comp",
    joinColumns = {@JoinColumn(name = "user_id")},
    inverseJoinColumns = @JoinColumn(name = "comp_id"))
  private Set<Machine> machines;

  public String getLastLoginDttm() {
    return lastLoginDttm;
  }

  public void setLastLoginDttm(String lastLoginDttm) {
    this.lastLoginDttm = lastLoginDttm;
  }

  public String getIpAddress() {
    return ipAddress;
  }

  public void setIpAddress(String ipAddress) {
    this.ipAddress = ipAddress;
  }

  public UserAccountType getUserAccountType() {
    return userAccountType;
  }

  public void setUserAccountType(UserAccountType userAccountType) {
    this.userAccountType = userAccountType;
  }

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Date getUserBanStartDate() {
    return userBanStartDate;
  }

  public void setUserBanStartDate(Date userBanStartDate) {
    this.userBanStartDate = userBanStartDate;
  }

  public Date getUserBanEndDate() {
    return userBanEndDate;
  }

  public void setUserBanEndDate(Date userBanEndDate) {
    this.userBanEndDate = userBanEndDate;
  }

  public String getComments() {
    return comments;
  }

  public void setComments(String comments) {
    this.comments = comments;
  }

  public Set<Machine> getMachines() {
    return machines;
  }

  public void setMachines(Set<Machine> machines) {
    this.machines = machines;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return Objects.equals(userId, user.userId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId);
  }
}