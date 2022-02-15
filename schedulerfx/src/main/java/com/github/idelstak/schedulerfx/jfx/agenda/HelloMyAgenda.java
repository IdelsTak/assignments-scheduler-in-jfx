package com.github.idelstak.schedulerfx.jfx.agenda;

import com.github.idelstak.schedulerfx.Activity;
import com.github.idelstak.schedulerfx.Assignment;
import com.github.idelstak.schedulerfx.Timetable;
import com.github.idelstak.schedulerfx.Worker;
import com.github.idelstak.schedulerfx.data.ActivityProvider;
import com.github.idelstak.schedulerfx.data.WorkerProvider;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class HelloMyAgenda extends Application {

  private final WorkerProvider workers = new WorkerProvider();
  private final ActivityProvider activities = new ActivityProvider();
  final Timetable timetable = new Timetable();
  final MyAgenda agenda = new MyAgenda(timetable);

  @Override
  public void start(Stage stage) throws Exception {
    agenda.setWorkerInCreation(workers.getAll().get(0));
    agenda.setActivityInCreation(activities.getAll().get(0));
    agenda.setFilter(WorkerFilter.getShowAllFilter());

    VBox vbox = new VBox();
    Group group = new Group();

    Button resetButton = new Button("reset");
    Button deleteButton = new Button("delete selected");
    final ComboBox<Worker> workersComboBox = createWorkersComboBox();
    final ComboBox<Activity> activitiesComboBox = createActivitiesComboBox();

    HBox hbox = new HBox();
    hbox.setSpacing(4);
    hbox.getChildren().add(workersComboBox);
    hbox.getChildren().add(activitiesComboBox);
    hbox.getChildren().add(resetButton);
    hbox.getChildren().add(deleteButton);
    hbox.getChildren().addAll(createWorkerFilter());

    group.getChildren().add(agenda);
    vbox.getChildren().add(group);
    vbox.getChildren().add(hbox);

    resetButton.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent arg0) {
            timetable.getAssignments().clear();
            agenda.refresh();
          }
        });

    deleteButton.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent arg0) {
            Assignment assignment = agenda.getSelectedAssignment();
            if (assignment != null) {
              timetable.getAssignments().remove(assignment);
              agenda.refresh();
            }
          }
        });

    Scene scene = new Scene(vbox, 800, 500);

    stage.initStyle(StageStyle.DECORATED);
    stage.setOpacity(1.0);
    stage.setScene(scene);
    stage.show();
  }

  private ComboBox<Worker> createWorkersComboBox() {
    ComboBox<Worker> comboBox = new ComboBox<Worker>();
    comboBox.getItems().addAll(workers.getAll());
    comboBox.setValue(comboBox.getItems().get(0));

    comboBox
        .valueProperty()
        .addListener(
            new ChangeListener<Worker>() {
              @Override
              public void changed(
                  ObservableValue<? extends Worker> arg0, Worker old, Worker worker) {
                agenda.setWorkerInCreation(worker);
              }
            });

    return comboBox;
  }

  private ComboBox<Activity> createActivitiesComboBox() {
    ComboBox<Activity> comboBox = new ComboBox<Activity>();
    comboBox.getItems().addAll(activities.getAll());
    comboBox.setValue(comboBox.getItems().get(0));

    comboBox
        .valueProperty()
        .addListener(
            new ChangeListener<Activity>() {
              @Override
              public void changed(
                  ObservableValue<? extends Activity> arg0, Activity old, Activity newValue) {
                agenda.setActivityInCreation(newValue);
              }
            });

    return comboBox;
  }

  private List<RadioButton> createWorkerFilter() {
    final ToggleGroup tg = new ToggleGroup();
    ArrayList<RadioButton> radioButtons = new ArrayList<>();

    RadioButton showAllRadioButton = createRadioButton(WorkerFilter.getShowAllFilter(), tg);
    showAllRadioButton.setSelected(true);
    radioButtons.add(showAllRadioButton);

    for (Worker worker : workers.getAll()) {
      WorkerFilter filter = WorkerFilter.createFor(worker);
      RadioButton rb = createRadioButton(filter, tg);
      radioButtons.add(rb);
    }

    tg.selectedToggleProperty()
        .addListener(
            new ChangeListener<Toggle>() {
              public void changed(
                  ObservableValue<? extends Toggle> ov, Toggle old, Toggle newToggle) {
                agenda.setFilter(((WorkerFilter) newToggle.getUserData()));
                agenda.refresh();
              }
            });

    return radioButtons;
  }

  private RadioButton createRadioButton(WorkerFilter wf, ToggleGroup tg) {
    RadioButton rb = new RadioButton();
    rb.setUserData(wf);
    rb.setText(wf.getDescription());
    rb.setToggleGroup(tg);
    return rb;
  }

  public static void main(String[] args) {
    launch(args);
  }
}
