package com.enqueapp.data;

import java.util.ArrayList;

public class OrderObject {
  public ArrayList<MenuItemObject> orderedItems;

  public OrderObject(ArrayList<MenuItemObject> orderedItems) {
    this.orderedItems = orderedItems;
  }
}