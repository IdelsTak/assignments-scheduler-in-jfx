package it.hijack.scheduler.jfx;

import java.util.Random;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ClickableGrid extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		GridPane grid = buildGrid();
		Group group = new Group();
		group.getChildren().add(grid);

		Scene scene = new Scene(group, 600, 300);

		stage.initStyle(StageStyle.DECORATED);
		stage.setOpacity(1.0);
		stage.setScene(scene);
		stage.show();
	}

	private GridPane buildGrid() {
		GridPane gridpane = new GridPane();
		gridpane.setGridLinesVisible(false);
		gridpane.setPadding(new Insets(0));
		gridpane.setHgap(5);
		gridpane.setVgap(5);

		addMockCells(gridpane, 6, 5);

		return gridpane;
	}

	private void addMockCells(GridPane gridpane, int rows, int cols) {
		for (int c = 1; c <= cols; c++) {
			for (int r = 1; r <= rows; r++) {
				gridpane.add(buildGridCell(getRandomTitle()), c, r);
			}
		}
	}

	private String getRandomTitle() {
		Random r = new Random();
		r.nextInt(3);
		switch (r.nextInt(3)) {
		case 0:
			return "coop";
		case 1:
			return "pulizie";
		case 2:
			return "gita";
		default:
			return "default";
		}
	}

	private Node buildGridCell(String title) {
		Cell cell = new Cell(title);
		return makeTrackable(cell);
	}

	private Node makeTrackable(final Cell cell) {
		final Group wrapper = new Group(cell.createNode());

		wrapper.setOnDragDetected(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				System.out.println("onDragDetected");
				Dragboard db = wrapper.startDragAndDrop(TransferMode.COPY);
				ClipboardContent content = new ClipboardContent();
				content.putString(cell.getTitle());
				db.setContent(content);
			}
		});

		wrapper.setOnDragOver(new EventHandler<DragEvent>() {
			public void handle(DragEvent event) {
				event.acceptTransferModes(TransferMode.ANY);
			}
		});

		wrapper.setOnDragEntered(new EventHandler<DragEvent>() {
			public void handle(DragEvent event) {
				// System.out.println("onDragEntered");
				if (event.getGestureSource() != wrapper
						&& event.getDragboard().hasString()) {
					wrapper.setEffect(new DropShadow());
				}
			}
		});

		wrapper.setOnDragExited(new EventHandler<DragEvent>() {
			public void handle(DragEvent event) {
				// System.out.println("onDragExited");
				wrapper.setEffect(null);
			}
		});

		wrapper.setOnDragDropped(new EventHandler<DragEvent>() {
			public void handle(DragEvent event) {
				String sourceTitle = event.getDragboard().getString();
				String targetTitle = cell.getTitle();
				System.out.println("onDragDropped - from: " + sourceTitle
						+ " to " + targetTitle);
				cell.setTitle(sourceTitle);
			}
		});

		wrapper.setOnDragDone(new EventHandler<DragEvent>() {
			public void handle(DragEvent event) {
				System.out.println("onDragDone");
			}
		});

		return wrapper;
	}

	private static final class Cell {

		private final StringProperty title;

		public Cell(String value) {
			title = new SimpleStringProperty(value);
		}

		public String getTitle() {
			return title.get();
		}

		public void setTitle(String value) {
			title.set(value);
		}

		public Node createNode() {
			final StackPane cellNode = new StackPane();

			final Rectangle rectangle = new Rectangle(100, 30,
					Color.grayRgb(230));
			final Label label = new Label(title.get());
			label.textProperty().bind(title);

			cellNode.getChildren().addAll(rectangle, label);

			return cellNode;
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
