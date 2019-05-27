package miniProject.main;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import miniProject.balancedTree.BalancedTree;
import miniProject.linkedList.LinkedList;
import miniProject.stack.Stack;

public class mainApp extends Application{
	
	@Override
	public void start(final Stage primaryStage) {
		
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		double windowWidth = gd.getDisplayMode().getWidth() * 7/12;
		double windowHeight = gd.getDisplayMode().getHeight() * 3/4;
		
		double height = windowHeight / 10;
		double width = windowWidth / 4;
		
		StackPane root = new StackPane();
		
		VBox menu = new VBox();
		menu.setTranslateY(- 1.5 *height);
		menu.setSpacing(20);
		
		Text title = new Text("Data Structure Illustration.");
		title.setStyle("-fx-fill: white;"
				+ " -fx-font-size: 50;"
				+ "-fx-font-weight: bold;"
				+ " -fx-font-family: \"Montserrat\";");
		
		VBox buttons = new VBox();
		buttons.setSpacing(15);
		
		Button stackButton = new Button("Stack");
		stackButton.setPrefWidth(width);
		stackButton.getStylesheets().add("menuButton.css");
		stackButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Stack stack = new Stack();
				stack.initModality(Modality.APPLICATION_MODAL);
				stack.initOwner(primaryStage);
				stack.show();
			}
		});
		
		Button linkedListButton = new Button("Linked List");
		linkedListButton.setPrefWidth(width);
		linkedListButton.getStylesheets().add("menuButton.css");
		linkedListButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				LinkedList linkedList = new LinkedList();
				linkedList.initModality(Modality.APPLICATION_MODAL);
				linkedList.initOwner(primaryStage);
				linkedList.show();
			}
		});
		
		Button balancedTreeButton = new Button("Balanced Tree");
		balancedTreeButton.setPrefWidth(width);
		balancedTreeButton.getStylesheets().add("menuButton.css");
		balancedTreeButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				BalancedTree balancedTree = new BalancedTree();
				balancedTree.initModality(Modality.APPLICATION_MODAL);
				balancedTree.initOwner(primaryStage);
				balancedTree.show();
			}
		});
		
		buttons.getChildren().addAll(stackButton,linkedListButton,balancedTreeButton);
		buttons.setAlignment(Pos.CENTER);
		
		menu.getChildren().addAll(title,buttons);
		buttons.setTranslateY(1.5 * height);
		
		menu.setAlignment(Pos.CENTER);
		
		root.getStylesheets().add("menu.css");
		root.getChildren().add(menu);
		
		Scene scene = new Scene(root,windowWidth,windowHeight);
		
		primaryStage.setTitle("Data Structure Illustration");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
