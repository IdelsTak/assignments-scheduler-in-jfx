package it.hijack.scheduler.jfx.agenda;

import it.hijack.scheduler.Activity;
import it.hijack.scheduler.Customer;
import it.hijack.scheduler.Timetable;
import it.hijack.scheduler.Worker;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class HelloMyAgenda extends Application {

	int startHour = 8;

	@Override
	public void start(Stage stage) throws Exception {

		final Timetable timetable = new Timetable();
		final Activity office = new Activity("office", new Customer("coop"));
		
		final ComboBox<Worker> workers = createWorkersComboBox();
		final ComboBox<String> days = createDaysComboBox();

		VBox vbox = new VBox();
		Group group = new Group();
		final MyAgenda agenda = new MyAgenda(timetable);

		Button btn = new Button("new assignment");
		Button resetButton = new Button("reset");

		HBox hbox = new HBox();
		hbox.getChildren().add(workers);
		hbox.getChildren().add(days);
		hbox.getChildren().add(btn);
		hbox.getChildren().add(resetButton);

		group.getChildren().add(agenda);
		vbox.getChildren().add(group);
		vbox.getChildren().add(hbox);

		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				System.out.println("adding one appointment");
				timetable.assign(office).to(workers.getValue()).from(startHour).to(startHour + 2).on(WeekDays.valueOf(days.getValue()));
				agenda.refresh();
				startHour += 3;
			}
		});

		resetButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				timetable.getAssignments().clear();
				agenda.refresh();
				startHour = 8;
			}
		});

		Scene scene = new Scene(vbox, 800, 500);

		stage.initStyle(StageStyle.DECORATED);
		stage.setOpacity(1.0);
		stage.setScene(scene);
		stage.show();
	}

	private ComboBox<Worker> createWorkersComboBox() {
		ObservableList<Worker> workers = FXCollections.observableArrayList(new Worker("Mario"), new Worker("Luigi"));
		ComboBox<Worker> comboBox = new ComboBox<Worker>(workers);
		comboBox.setValue(workers.get(0));

		return comboBox;
	}

	private ComboBox<String> createDaysComboBox() {
		ComboBox<String> comboBox = new ComboBox<String>();
		for(WeekDays dayOfWeek : WeekDays.values())
			comboBox.getItems().add(dayOfWeek.name());
		comboBox.setValue(WeekDays.MONDAY.name());
		
		return comboBox;
	}

	public static void main(String[] args) {
		launch(args);
	}

}
