package com.github.idelstak.schedulerfx.data;

import com.github.idelstak.schedulerfx.Worker;

import java.util.ArrayList;
import java.util.List;

public class WorkerProvider {

  private final List<Worker> workers = new ArrayList<>();

  public WorkerProvider() {
    workers.add(new Worker("Mario"));
    workers.add(new Worker("Luigi"));
    workers.add(new Worker("Giovanna"));
    workers.add(new Worker("Veronica"));
  }

  public List<Worker> getAll() {
    return workers;
  }
}
