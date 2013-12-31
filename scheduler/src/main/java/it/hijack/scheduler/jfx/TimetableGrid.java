package it.hijack.scheduler.jfx;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.InputEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class TimetableGrid extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		GridPane grid = buildGrid();
		Group group = new Group();
		group.getChildren().add(grid);

		Scene scene = new Scene(group, 500, 300);

		stage.initStyle(StageStyle.DECORATED);
		stage.setOpacity(1.0);
		stage.setScene(scene);
		stage.show();
	}

	private GridPane buildGrid() {
		GridPane gridpane = new GridPane();
		gridpane.setGridLinesVisible(false);
		gridpane.setPadding(new Insets(0));
		gridpane.setHgap(10);
		gridpane.setVgap(10);

		gridpane.add(buildGridCell("coop"), 1, 1); // column=1 row=1
		gridpane.add(buildGridCell("coop"), 1, 2);
		gridpane.add(buildGridCell("pulizie"), 2, 1);
		gridpane.add(buildGridCell("pulizie"), 2, 2);

		return gridpane;
	}

	private Node buildGridCell(String title) {
		StackPane stack = new StackPane();

		final Rectangle rectangle = new Rectangle(100, 100, Color.CORAL);

		EventHandler<MouseEvent> enterHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				rectangle.setFill(Color.RED);
			}
		};
		EventHandler<MouseEvent> exitHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				rectangle.setFill(Color.CORAL);
			}
		};

		stack.addEventHandler(MouseEvent.MOUSE_ENTERED, enterHandler);
		stack.addEventHandler(MouseEvent.MOUSE_EXITED, exitHandler);

		stack.getChildren().addAll(rectangle, new Label(title));
		return stack;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
