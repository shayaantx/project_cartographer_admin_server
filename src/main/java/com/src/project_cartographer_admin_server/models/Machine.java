package com.src.project_cartographer_admin_server.models;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Created by shayaantx on 3/3/2018.
 */
@Entity(name = "comp")
public class Machine {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer comp_id;

  @Column(name = "serial_1")
  private String serial1;

  @Column(name = "serial_2")
  private String serial2;

  @Column(name = "serial_3")
  private String serial3;

  @Column(name = "op_sys")
  private String operatingSystem;

  @Column(name = "comp_link")
  private Integer compLink;

  @Column(name = "last_active")
  private Date lastActiveDttm;

  @Column(name = "comp_ban")
  private boolean banned;

  @Column(name = "comp_ban_date")
  private Date banStartDate;

  @Column(name = "comp_ban_date_end")
  private Date banEndDate;

  @Column(name = "comp_comments")
  private String comments;

  @ManyToMany(mappedBy = "machines")
  private Set<User> users;

  public String getOperatingSystem() {
    return operatingSystem;
  }

  public void setOperatingSystem(String operatingSystem) {
    this.operatingSystem = operatingSystem;
  }

  public String getSerial1() {
    return serial1;
  }

  public void setSerial1(String serial1) {
    this.serial1 = serial1;
  }

  public String getSerial2() {
    return serial2;
  }

  public void setSerial2(String serial2) {
    this.serial2 = serial2;
  }

  public String getSerial3() {
    return serial3;
  }

  public void setSerial3(String serial3) {
    this.serial3 = serial3;
  }

  public Integer getComp_id() {
    return comp_id;
  }

  public void setComp_id(Integer comp_id) {
    this.comp_id = comp_id;
  }

  public Integer getCompLink() {
    return compLink;
  }

  public void setCompLink(Integer compLink) {
    this.compLink = compLink;
  }

  public Date getLastActiveDttm() {
    return lastActiveDttm;
  }

  public void setLastActiveDttm(Date lastActiveDttm) {
    this.lastActiveDttm = lastActiveDttm;
  }

  public boolean isBanned() {
    return banned;
  }

  public void setBanned(boolean banned) {
    this.banned = banned;
  }

  public Date getBanStartDate() {
    return banStartDate;
  }

  public void setBanStartDate(Date banStartDate) {
    this.banStartDate = banStartDate;
  }

  public Date getBanEndDate() {
    return banEndDate;
  }

  public void setBanEndDate(Date banEndDate) {
    this.banEndDate = banEndDate;
  }

  public String getComments() {
    return comments;
  }

  public void setComments(String comments) {
    this.comments = comments;
  }

  public Set<User> getUsers() {
    return users;
  }

  public void setUsers(Set<User> users) {
    this.users = users;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Machine machine = (Machine) o;

    return comp_id != null ? comp_id.equals(machine.comp_id) : machine.comp_id == null;
  }

  @Override
  public int hashCode() {
    return comp_id != null ? comp_id.hashCode() : 0;
  }
}
