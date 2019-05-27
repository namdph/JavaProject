package miniProject.linkedList;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import miniProject.linkedList.model.LLVisualizeContent;
import miniProject.model.TheoreticalBackground;
import miniProject.model.menuSpace;

public class LinkedList extends Stage{
	
	public LinkedList(){
		super();
		
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		float windowWidth = gd.getDisplayMode().getWidth() * 4/5;
		float windowHeight = (float) (windowWidth / 1.725);
		
		HBox root = new HBox();
		root.setStyle("-fx-background-image: url(\"/miniProject/main/Icons/nature.jpg\");\r\n" + 
				"    -fx-background-size: cover;  \r\n" + 
				"    -fx-background-position: center center;\r\n" + 
				"    -fx-effect: dropshadow(three-pass-box, black, 30, 0.5, 0, 0); ");
		
		@SuppressWarnings("unused")
		int width = gd.getDisplayMode().getWidth() / 8;
		int height = gd.getDisplayMode().getHeight() / 20;
		
		
//		menuSpace
		
		float menuWidth = windowWidth /4;
		float menuHeight = windowHeight;
		
		menuSpace menuSpace = new menuSpace("Stack");

//		THEORICAL BACKGROUND INDEX
        
		VBox indexContent = new VBox();
		Button definition = new Button("Definition");
		
		definition.setPrefSize(menuWidth, height);
		definition.getStylesheets().add("buttonStyle.css");
		
		TitledPane operation = new TitledPane();
		operation.setText("Operations");
		operation.setExpanded(false);
		operation.getStylesheets().add("titledStyle.css");
		
		VBox operationContent = new VBox();
		Button insert = new Button("Insertion");
		insert.setPrefSize(menuWidth, height);
		insert.getStylesheets().add("buttonStyle.css");
		
		Button delete = new Button("Deletion");
		delete.setPrefSize(menuWidth, height);
		delete.getStylesheets().add("buttonStyle.css");
		
		operationContent.getChildren().addAll(insert,delete);
		operation.setContent(operationContent);
		
		indexContent.getChildren().addAll(definition,operation);
		menuSpace.setIndexContent(indexContent);
		menuSpace.getIndex().setContent(indexContent);
		
//		demoSpace
		StackPane demoSpace = new StackPane();
		
		HBox.setHgrow(demoSpace, Priority.ALWAYS);
		float demoHeight =  windowHeight - 100;
		demoSpace.setAlignment(null);
		demoSpace.setMinHeight(demoHeight);
		
		LLVisualizeContent visualizeSpace = new LLVisualizeContent();
		TheoreticalBackground theoSpace = new TheoreticalBackground("/miniProject/linkedList/slides", 16);
		
		demoSpace.getChildren().addAll(visualizeSpace, theoSpace);
		
		visualizeSpace.setVisible(false);
		theoSpace.setVisible(false);
		
		menuSpace.getVisualizeButton().setOnAction(new EventHandler<ActionEvent>() {
			@Override 
			public void handle(ActionEvent event) {
				menuSpace.getIndex().setVisible(false);
				menuSpace.getVisualBar().setVisible(true);
				
				if(demoSpace.getChildren().get(demoSpace.getChildren().size() - 1) instanceof TheoreticalBackground && (demoSpace.getChildren().get(demoSpace.getChildren().size() - 1)).isVisible() == true) {
					demoSpace.getChildren().get(demoSpace.getChildren().size() -1).setVisible(false);
					demoSpace.getChildren().get(0).setVisible(true);
					demoSpace.getChildren().get(0).toFront();
				}
				else {
					visualizeSpace.setVisible(true);
					visualizeSpace.toFront();
				}
			}
		});
		
		menuSpace.getTheoButton().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				menuSpace.getIndex().setVisible(true);
				menuSpace.getVisualBar().setVisible(false);
				
				if(demoSpace.getChildren().get(demoSpace.getChildren().size() - 1) instanceof LLVisualizeContent && (demoSpace.getChildren().get(demoSpace.getChildren().size() - 1)).isVisible() == true) {
					demoSpace.getChildren().get(demoSpace.getChildren().size() - 1).setVisible(false);
					demoSpace.getChildren().get(0).setVisible(true);
					demoSpace.getChildren().get(0).toFront();
				}
				else {
					theoSpace.setVisible(true);
					theoSpace.toFront();
				}
			}
		});
		
//		Menu action
		menuSpace.getBackButton().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(demoSpace.getChildren().get(demoSpace.getChildren().size() - 1) instanceof TheoreticalBackground && (demoSpace.getChildren().get(demoSpace.getChildren().size() - 1)).isVisible() == true) {
					((TheoreticalBackground) demoSpace.getChildren().get(demoSpace.getChildren().size() - 1)).getPreviousButton().fire();
				}
			}
		});
		
		menuSpace.getNextButton().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(demoSpace.getChildren().get(demoSpace.getChildren().size() - 1) instanceof TheoreticalBackground && (demoSpace.getChildren().get(demoSpace.getChildren().size() - 1)).isVisible() == true) {
					((TheoreticalBackground) demoSpace.getChildren().get(demoSpace.getChildren().size() - 1)).getNextButton().fire();
				}
			}
		});
		
		menuSpace.getResetButton().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(demoSpace.getChildren().get(demoSpace.getChildren().size() - 1) instanceof LLVisualizeContent && (demoSpace.getChildren().get(demoSpace.getChildren().size() - 1)).isVisible() == true) {
					LLVisualizeContent resetContent = new LLVisualizeContent();
					demoSpace.getChildren().remove(demoSpace.getChildren().size() - 1);
					demoSpace.getChildren().add(resetContent);
				}
				
				else if(demoSpace.getChildren().get(demoSpace.getChildren().size() - 1) instanceof TheoreticalBackground && (demoSpace.getChildren().get(demoSpace.getChildren().size() - 1)).isVisible() == true) {
					TheoreticalBackground resetContent = new TheoreticalBackground("/miniProject/linkedList/Theoretical", 16);
					demoSpace.getChildren().remove(demoSpace.getChildren().size() -1);
					demoSpace.getChildren().add(resetContent);
				}
			}
		});

		menuSpace.getExitButton().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				close();
			}
		});
		
		root.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if(demoSpace.getChildren().get(demoSpace.getChildren().size() - 1) instanceof TheoreticalBackground && (demoSpace.getChildren().get(demoSpace.getChildren().size() - 1)).isVisible() == true){
					switch(event.getCode()) {
					case RIGHT: {
						theoSpace.getNextButton().fire();
						break;
					}
					case LEFT:{ 
						theoSpace.getPreviousButton().fire();
						break;
					}
					default:
						break; 
					}
				}
			}
		});
//		
		root.getChildren().addAll(menuSpace,demoSpace);
        menuSpace.setViewOrder(0);
        demoSpace.setViewOrder(3);
		
		Scene scene = new Scene(root,windowWidth,windowHeight);
		
		this.setTitle("Linked List");
		this.setScene(scene);
		
//		INDEX ACTION
		definition.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				int definitionIndex = 2;
				theoSpace.getSceneCollector().getSceneCollectiion().get(theoSpace.getSceneCollector().getSceneIndex()).setVisible(false);
				theoSpace.getSceneCollector().getSceneCollectiion().get(definitionIndex - 1).setVisible(true);
				theoSpace.getSceneCollector().setSceneIndex(definitionIndex - 1);
			}
		});
		
		insert.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				int insertIndex = 8;
				theoSpace.getSceneCollector().getSceneCollectiion().get(theoSpace.getSceneCollector().getSceneIndex()).setVisible(false);
				theoSpace.getSceneCollector().getSceneCollectiion().get(insertIndex - 1).setVisible(true);
				theoSpace.getSceneCollector().setSceneIndex(insertIndex - 1);
			}
		});
		
		delete.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				int deleteIndex = 13;
				theoSpace.getSceneCollector().getSceneCollectiion().get(theoSpace.getSceneCollector().getSceneIndex()).setVisible(false);
				theoSpace.getSceneCollector().getSceneCollectiion().get(deleteIndex - 1).setVisible(true);
				theoSpace.getSceneCollector().setSceneIndex(deleteIndex - 1);
			}
		});
	}

}
