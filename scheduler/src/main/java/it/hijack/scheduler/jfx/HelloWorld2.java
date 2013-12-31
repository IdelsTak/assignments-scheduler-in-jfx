package it.hijack.scheduler.jfx;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class HelloWorld2 extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Hello World Title");
		Button btn = new Button();
		btn.setText("Say Hello");
		btn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				System.out.println("Hello World");
			}
		});
		
		StackPane root = new StackPane();
		root.getChildren().add(btn);
		primaryStage.setScene(new Scene(root, 200,300));
		primaryStage.show();
	}
}
