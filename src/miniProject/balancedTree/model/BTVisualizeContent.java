package miniProject.balancedTree.model;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class BTVisualizeContent extends VBox{
	
	TextField valueInput; 
	TextField valueOutput;
	Button insertButton;
	Button deleteButton;
	
	public Button getInsertButton() {
		return insertButton;
	}

	public Button getDeleteButton() {
		return deleteButton;
	}

	public TextField getValueInput() {
		return valueInput;
	}

	public TextField getValueOutput() {
		return valueOutput;
	}

	public int count = 0;
	
	public BTContainer balancedTree;
	
	public BTContainer getBalancedTree() {
		return balancedTree;
	}

	public void setBalancedTree(BTContainer balancedTree) {
		this.balancedTree = balancedTree;
	}

	public BTVisualizeContent() {
		super();
		
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		float width = gd.getDisplayMode().getWidth() / 20;
		float height = gd.getDisplayMode().getHeight() / 20;
		
//		action Layer
		double actionMenuHeight = height * 1.5;
		GridPane actionLayer = new GridPane();
		actionLayer.setPadding(new Insets(20));
		actionLayer.setHgap(20);
		actionLayer.setVgap(15);
		actionLayer.setMaxHeight(actionMenuHeight);
		actionLayer.setMinHeight(actionMenuHeight);
		actionLayer.setAlignment(Pos.CENTER);
		
//			insert MENU
		this.valueInput = new TextField("Enter node Value");
		this.valueInput.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if(valueInput.getText().equals("Enter node Value"))
					valueInput.setText("");
			}
		});
		this.valueInput.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) {
				if(valueInput.getText().equals("Enter node Value"))
					valueInput.setText("");
			}
		});
		
		this.valueInput.setAlignment(Pos.CENTER_LEFT);
		this.valueInput.getStylesheets().add("textField.css");
		actionLayer.add(valueInput, 0, 0);
		valueInput.setOnKeyReleased(new EventHandler<KeyEvent>(){
			@Override
			public void handle(KeyEvent event) {
				switch(event.getCode()) {
				case ENTER: insertButton.fire();
				default:
					break;
				}
			}
		});
		
		this.insertButton = new Button("INSERT");
		this.insertButton.getStylesheets().add("actionLayerButton.css");
		actionLayer.add(insertButton, 1, 0);
		
		
//			delete MENU
		this.valueOutput = new TextField("Enter node Value");
		this.valueOutput.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if(valueOutput.getText().equals("Enter node Value"))
					valueOutput.setText("");
			}
		});
		this.valueOutput.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) {
				if(valueOutput.getText().equals("Enter node Value"))
					valueOutput.setText("");
			}
		});
		
		this.valueOutput.setAlignment(Pos.CENTER_LEFT);
		this.valueOutput.getStylesheets().add("textField.css");
		actionLayer.add(valueOutput, 0, 1);
		valueOutput.setOnKeyReleased(new EventHandler<KeyEvent>(){
			@Override
			public void handle(KeyEvent event) {
				switch(event.getCode()) {
				case ENTER: deleteButton.fire();
				default:
					break;
				}
			}
		});
		this.deleteButton = new Button("DELETE");
		this.deleteButton.getStylesheets().add("actionLayerButton.css");
		actionLayer.add(deleteButton, 1, 1);
		

//		Balanced Tree layer
		this.balancedTree = new BTContainer();
		Pane balancedTreeLayer = new Pane();
		
		VBox.setVgrow(balancedTreeLayer, Priority.ALWAYS);
		balancedTreeLayer.setStyle("-fx-background-color: whitesmoke; -fx-opacity: 0.9");
		
		double rootCoordinateX = balancedTreeLayer.getWidth() / 2;
		double rootCoordinateY = balancedTreeLayer.getHeight() / 2;
		int speed = 2000;
		
		
//		insert ACTION
		insertButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				boolean numeric = true;
				try {
					@SuppressWarnings("unused")
					Integer num = Integer.parseInt(valueInput.getText());
				}catch(NumberFormatException e) {
					numeric = false;
				}
				if(numeric == true) {
					int value = Integer.parseInt(valueInput.getText());
					balancedTree.Root = balancedTree.insert_node(balancedTreeLayer, balancedTree.Root, value);
					valueInput.clear();
				}
				else {
					Alert wrongInput = new Alert(AlertType.WARNING);
					wrongInput.setTitle("Input ERROR");
					wrongInput.setHeaderText(null);
					wrongInput.setContentText("Invalid Input: Please Enter an Integer Value ");
					wrongInput.showAndWait();
				}
			}
		});
		
//		Delete ACTION
		deleteButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				int value = Integer.parseInt(valueOutput.getText());
				balancedTree.Root = balancedTree.remove_node(balancedTreeLayer, balancedTree.Root, value);
				valueOutput.clear();
			}
		});
			
		this.getChildren().addAll(actionLayer,balancedTreeLayer);
		balancedTreeLayer.setViewOrder(0);
		balancedTreeLayer.setViewOrder(1);
		
	}

}
