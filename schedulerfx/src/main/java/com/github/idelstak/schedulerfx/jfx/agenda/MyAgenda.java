package com.github.idelstak.schedulerfx.jfx.agenda;

import com.github.idelstak.schedulerfx.Activity;
import com.github.idelstak.schedulerfx.Assignment;
import com.github.idelstak.schedulerfx.Timetable;
import com.github.idelstak.schedulerfx.Worker;
import javafx.scene.control.Control;

public class MyAgenda extends Control {

  private final Timetable timetable;
  private Worker worker;
  private Activity activity;
  private WorkerFilter filter;
  private Assignment selectedAssignment;

  public MyAgenda(Timetable timetable) {
    this.timetable = timetable;
    getStyleClass().add(getClass().getSimpleName());
  }

  @Override
  public String getUserAgentStylesheet() {
    return getClass().getResource(getClass().getSimpleName() + ".css").toExternalForm();
  }

  public Timetable getTimetable() {
    return timetable;
  }

  public void refresh() {
    MyAgendaSkin skin = (MyAgendaSkin) getSkin();
    skin.refresh();
  }

  public void setWorkerInCreation(Worker worker) {
    this.worker = worker;
  }

  public Worker getWorkerInCreation() {
    return worker;
  }

  public void setActivityInCreation(Activity activity) {
    this.activity = activity;
  }

  public Activity getActivityInCreation() {
    return activity;
  }

  public void setFilter(WorkerFilter workerFilter) {
    this.filter = workerFilter;
  }

  public WorkerFilter getWorkerFilter() {
    return filter;
  }

  public Assignment getSelectedAssignment() {
    return selectedAssignment;
  }

  public void setSelectedAssignment(Assignment selectedAssignment) {
    this.selectedAssignment = selectedAssignment;
  }
}
