package com.github.idelstak.schedulerfx.data;

import com.github.idelstak.schedulerfx.Activity;
import com.github.idelstak.schedulerfx.Customer;
import java.util.ArrayList;
import java.util.List;

public class ActivityProvider {
  private final List<Activity> activities = new ArrayList<>();
  private final Customer customer = new Customer("coop");

  public ActivityProvider() {
    activities.add(new Activity("office", customer));
    activities.add(new Activity("territory", customer));
    activities.add(new Activity("public relations", customer));
  }

  public List<Activity> getAll() {
    return activities;
  }
}
