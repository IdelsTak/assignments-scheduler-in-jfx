package it.hijack.scheduler.jfx;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class GridPaneTest extends Application {

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
		
		StackPane stack = new StackPane();
	    stack.getChildren().addAll(new Rectangle(100,100,Color.CORAL), new Label("Pulizie"));
	     
		gridpane.add(buildGridCell("coop"), 1, 1); // column=1 row=1
		gridpane.add(buildGridCell("coop"), 1, 2);
		gridpane.add(buildGridCell("pulizie"), 2, 1);
		gridpane.add(buildGridCell("pulizie"), 2, 2);

		return gridpane;
	}
	
	private Region buildGridCell(String title) {
		StackPane stack = new StackPane();
		VBox box = new VBox();
		box.setFillWidth(true);
		box.setStyle("-fx-background-color: #336699;");
		
		Rectangle rectangle = new Rectangle(100,100,Color.CORAL);
		
//	    stack.getChildren().addAll(rectangle, new Label(title));
	    
//	    rectangle.setOnMousePressed(new EventHandler<MouseEvent>(){
//			@Override
//			public void handle(MouseEvent event) {
//				System.out.println("PRESSED "+event.getSource());
//				Rectangle source = (Rectangle) event.getSource();
//				source.setFill(Color.RED);
//			}
//	    });
//
//	    rectangle.setOnMouseReleased(new EventHandler<MouseEvent>(){
//	    	@Override
//	    	public void handle(MouseEvent event) {
//	    		System.out.println("RELEASED");
//	    		Rectangle source = (Rectangle) event.getSource();
//				source.setFill(Color.CORAL);
//	    	}
//	    });
	    
	    box.getChildren().add(rectangle);
	    DragResizer.makeResizable(box);
	    return box;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
