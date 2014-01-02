package it.hijack.scheduler;

public class Activity {

	private String name;
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
	
	public String toString() {
		return name;
	}
}
