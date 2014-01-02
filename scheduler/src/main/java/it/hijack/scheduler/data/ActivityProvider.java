package it.hijack.scheduler.data;

import it.hijack.scheduler.Activity;
import it.hijack.scheduler.Customer;

import java.util.ArrayList;
import java.util.List;

public class ActivityProvider {
	private List<Activity> activities = new ArrayList<Activity>();
	private Customer customer = new Customer("coop");

	public ActivityProvider() {
		activities.add(new Activity("office", customer));
		activities.add(new Activity("territory", customer));
		activities.add(new Activity("public relations", customer));
	}

	public List<Activity> getAll() {
		return activities;
	}
}
