package com.enqueapp.data;

public class QueueObject {
  public String name;
  public String queueLength;
  public String rating;

  public QueueObject(String name, String queueLength, String rating) {
    this.name = name;
    this.queueLength = queueLength;
    this.rating = rating;
  }
}