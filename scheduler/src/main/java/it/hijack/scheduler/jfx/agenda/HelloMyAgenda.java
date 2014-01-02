package it.hijack.scheduler.jfx.agenda;

import it.hijack.scheduler.Timetable;
import it.hijack.scheduler.Worker;
import it.hijack.scheduler.data.WorkersProvider;

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

	private WorkersProvider workers = new WorkersProvider();
	final Timetable timetable = new Timetable();
	final MyAgenda agenda = new MyAgenda(timetable);

	@Override
	public void start(Stage stage) throws Exception {
		agenda.setDefaultWorker(workers.getAll().get(0));
		agenda.setFilter(WorkerFilter.getShowAllFilter());

		VBox vbox = new VBox();
		Group group = new Group();

		Button resetButton = new Button("reset");
		final ComboBox<Worker> workersComboBox = createWorkersComboBox(workers);

		HBox hbox = new HBox();
		hbox.setSpacing(4);
		hbox.getChildren().add(workersComboBox);
		hbox.getChildren().add(resetButton);
		hbox.getChildren().addAll(createWorkerFilter());

		group.getChildren().add(agenda);
		vbox.getChildren().add(group);
		vbox.getChildren().add(hbox);

		resetButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				timetable.getAssignments().clear();
				agenda.refresh();
			}
		});

		Scene scene = new Scene(vbox, 800, 500);

		stage.initStyle(StageStyle.DECORATED);
		stage.setOpacity(1.0);
		stage.setScene(scene);
		stage.show();
	}

	private ComboBox<Worker> createWorkersComboBox(WorkersProvider workers) {
		ComboBox<Worker> comboBox = new ComboBox<Worker>();
		comboBox.getItems().addAll(workers.getAll());
		comboBox.setValue(comboBox.getItems().get(0));

		comboBox.valueProperty().addListener(new ChangeListener<Worker>() {
			@Override
			public void changed(ObservableValue<? extends Worker> arg0, Worker old, Worker worker) {
				agenda.setDefaultWorker(worker);
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

		tg.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			public void changed(ObservableValue<? extends Toggle> ov, Toggle old, Toggle newToggle) {
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
