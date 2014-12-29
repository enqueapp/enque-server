package com.enqueapp.data;

public class MenuItemObject {
  public String name;
  public String price;
  public String description;
  public String menuId;

  public MenuItemObject(String name, String price, String description, String menuId) {
    this.name = name;
    this.price = price;
    this.description = description;
    this.menuId = menuId;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPrice() {
    return this.price;
  }

  public void setPrice(String price) {
    this.price = price;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.menuId = description;
  }

  public String getMenuId() {
    return this.menuId;
  }

  public void setMenuId(String menuId) {
    this.menuId = menuId;
  }
}