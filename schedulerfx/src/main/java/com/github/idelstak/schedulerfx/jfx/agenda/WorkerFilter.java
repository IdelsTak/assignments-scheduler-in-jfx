package com.github.idelstak.schedulerfx.jfx.agenda;

import com.github.idelstak.schedulerfx.Worker;

public class WorkerFilter {
  private Worker worker;
  private boolean showAll;
  private static WorkerFilter showAllFilterSingleton;

  public static WorkerFilter createFor(Worker worker) {
    WorkerFilter wf = new WorkerFilter();
    wf.worker = worker;
    return wf;
  }

  public static WorkerFilter getShowAllFilter() {
    if (showAllFilterSingleton == null) {
      showAllFilterSingleton = new WorkerFilter();
      showAllFilterSingleton.showAll = true;
    }
    return showAllFilterSingleton;
  }

  public boolean showAll() {
    return showAll;
  }

  public Worker getWorker() {
    return worker;
  }

  public String getDescription() {
    return showAll ? "show all" : worker.getName();
  }
}