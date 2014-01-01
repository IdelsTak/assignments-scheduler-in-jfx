package it.hijack.scheduler;

import it.hijack.scheduler.jfx.agenda.WeekDays;

import java.util.Calendar;

public class Assignment {

	private Activity activity;
	private Worker worker;
	private int startHour;
	private int stopHour;
	private WeekDays dayOfWeek = WeekDays.MONDAY;

	public Assignment(Activity activity) {
		this.setActivity(activity);
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public Assignment assignTo(Worker worker) {
		this.worker = worker;
		return this;
	}

	public Assignment from(int startHour) {
		this.startHour = startHour;
		return this;
	}

	public Assignment to(int stopHour) {
		this.stopHour = stopHour;
		return this;
	}

	public Worker getWorker() {
		return worker;
	}

	public int getStartHour() {
		return startHour;
	}
	
	public int getStopHour() {
		return stopHour;
	}

	public Assignment to(Worker worker) {
		return assignTo(worker);
	}

	public boolean isAssignedTo(Worker worker) {
		return this.worker != null && this.worker.equals(worker);
	}

	public void setWorker(Worker worker) {
		this.worker = worker;
	}

	public int getTotalHours() {
		return stopHour - startHour;
	}

	public Assignment on(WeekDays dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
		return this;
	}

	public WeekDays getDayOfWeek() {
		return dayOfWeek;
	}
}
