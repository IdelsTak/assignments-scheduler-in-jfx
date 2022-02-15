package com.github.idelstak.schedulerfx;

public class Activity {

  private final String name;
  private Customer owner;

  public Activity(String name) {
    this.name = name;
  }

  public Activity(String name, Customer coop) {
    this.name = name;
    this.owner = coop;
  }

  public String getName() {
    return name;
  }

  public void setCustomer(Customer owner) {
    this.owner = owner;
  }

  public Customer getCustomer() {
    return owner;
  }

  @Override
  public String toString() {
    return name;
  }
}
