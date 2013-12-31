package it.hijack.scheduler.viewer;

import it.hijack.scheduler.Activity;
import it.hijack.scheduler.Assignment;
import it.hijack.scheduler.Customer;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

public class SchedulerTableView extends Application {

	private TableView<Assignment> table = new TableView<Assignment>();
	
	private final ObservableList<Assignment> data = FXCollections.observableArrayList(
			new Assignment(new Activity("pulizie", new Customer("rossi"))),
			new Assignment(new Activity("gita", new Customer("bianchi"))),
			new Assignment(new Activity("ufficio", new Customer("cooperativa"))));
	
	@Override
	public void start(Stage stage) throws Exception {
		Scene scene = new Scene(new Group());
        stage.setTitle("Assignment Table");
        stage.setWidth(450);
        stage.setHeight(550);
        
        TableColumn<Assignment, String> activityNameColumn = new TableColumn<Assignment, String>("Activity Name");
        activityNameColumn.setMinWidth(100);
//        activityNameColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("activity"));
        
        activityNameColumn.setCellValueFactory(new Callback<CellDataFeatures<Assignment, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(
					CellDataFeatures<Assignment, String> p) {
				return new ReadOnlyObjectWrapper(p.getValue().getActivity().getName());
			}
         });
        
        table.setItems(data);
        table.getColumns().addAll(activityNameColumn);
        
        
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().add(table);
 
        ((Group) scene.getRoot()).getChildren().addAll(vbox);
        addStackPane(vbox);
        
        stage.setScene(scene);
        stage.show();
	}
	
	public void addStackPane(VBox vbox) {
		 StackPane stack = new StackPane();
		    Rectangle helpIcon = new Rectangle(30.0, 25.0);
		    helpIcon.setFill(new LinearGradient(0,0,0,1, true, CycleMethod.NO_CYCLE,
		        new Stop[]{
		        new Stop(0,Color.web("#4977A3")),
		        new Stop(0.5, Color.web("#B0C6DA")),
		        new Stop(1,Color.web("#9CB6CF")),}));
		    helpIcon.setStroke(Color.web("#D0E6FA"));
		    helpIcon.setArcHeight(3.5);
		    helpIcon.setArcWidth(3.5);

		    Text helpText = new Text("?");
		    helpText.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
		    helpText.setFill(Color.WHITE);
		    helpText.setStroke(Color.web("#7080A0")); 

		    stack.getChildren().addAll(helpIcon, helpText);
		    stack.setAlignment(Pos.CENTER_RIGHT);     // Right-justify nodes in stack
		    StackPane.setMargin(helpText, new Insets(0, 10, 0, 0)); // Center "?"

		    vbox.getChildren().add(stack);            // Add to HBox from Example 1-2
		    HBox.setHgrow(stack, Priority.ALWAYS);    // Give stack any extra space
	}

	public static void main(String[] args) {
		launch(args);
	}
}
