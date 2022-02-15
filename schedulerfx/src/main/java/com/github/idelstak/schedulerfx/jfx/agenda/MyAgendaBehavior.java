package com.github.idelstak.schedulerfx.jfx.agenda;

import com.sun.javafx.scene.control.behavior.BehaviorBase;
import com.sun.javafx.scene.control.inputmap.InputMap;

public class MyAgendaBehavior extends BehaviorBase<MyAgenda> {

  public MyAgendaBehavior(MyAgenda control) {
    super(control);
  }

  @Override
  public InputMap<MyAgenda> getInputMap() {
    throw new UnsupportedOperationException("Not supported yet.");
  }
}
