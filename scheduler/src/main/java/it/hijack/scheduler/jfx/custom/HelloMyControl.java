package it.hijack.scheduler.jfx.custom;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class HelloMyControl extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		Group group = new Group();
		MyControl myControl = new MyControl();
		group.getChildren().add(myControl);

		Scene scene = new Scene(group, 600, 300);

		stage.initStyle(StageStyle.DECORATED);
		stage.setOpacity(1.0);
		stage.setScene(scene);
		stage.show();
	}

	
	public static void main(String[] args) {
		launch(args);
	}
}
