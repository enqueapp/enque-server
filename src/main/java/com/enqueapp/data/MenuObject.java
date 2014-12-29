package com.enqueapp.data;

import java.util.ArrayList;

public class MenuObject {
  public String name;
  public String description;
  public String menuId;

  public MenuObject(String menuId, String name, String description) {
    this.menuId = menuId;
    this.name = name;
    this.description = description;
  }

  public void setMenuId(String menuId) {
    this.menuId = menuId;
  }

  public String getMenuId() {
    return this.menuId;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.name = description;
  }
}