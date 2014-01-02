package it.hijack.scheduler.jfx.agenda;

import it.hijack.scheduler.Timetable;
import it.hijack.scheduler.Worker;
import javafx.scene.control.Control;

public class MyAgenda extends Control {

	final private Timetable timetable;
	private Worker worker;
	private WorkerFilter filter;
	
	public MyAgenda(Timetable timetable) {
		this.timetable = timetable;
		getStyleClass().add(getClass().getSimpleName());
	}

	@Override
	protected String getUserAgentStylesheet() {
		return getClass().getResource(getClass().getSimpleName() + ".css").toExternalForm();
	}

	public Timetable getTimetable() {
		return timetable;
	}
	
	public void refresh(){
		MyAgendaSkin skin = (MyAgendaSkin)getSkin();
		skin.refresh();
	}

	public void setDefaultWorker(Worker worker) {
		this.worker = worker;
	}
	
	public Worker getDefaultWorker() {
		return worker;
	}

	public void setFilter(WorkerFilter workerFilter) {
		this.filter = workerFilter;
	}
	
	public WorkerFilter getWorkerFilter() {
		return filter;
	}
}
