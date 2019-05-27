package miniProject.balancedTree;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.util.ArrayList;

import javafx.animation.Animation;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import miniProject.balancedTree.model.BTVisualizeContent;
import miniProject.model.TheoreticalBackground;
import miniProject.model.menuSpace;

public class BalancedTree extends Stage{
	
	public BalancedTree(){
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
		
		menuSpace menuSpace = new menuSpace("Balanced Tree");
		
//		THEORICAL BACKGROUND INDEX
        
		VBox indexContent = new VBox();
		
		TitledPane AVLTree = new TitledPane();
		AVLTree.setText("AVL Tree");
		AVLTree.setExpanded(false);
		AVLTree.getStylesheets().add("titledStyle.css");
		
		VBox AVLContent = new VBox();
		Button AVLDef = new Button("Definition");
		AVLDef.setPrefSize(menuWidth, height * 5/6);
		AVLDef.getStylesheets().add("buttonStyle.css");
		
		Button AVLInsert = new Button("Insertion");
		AVLInsert.setPrefSize(menuWidth, height * 5/6);
		AVLInsert.getStylesheets().add("buttonStyle.css");
		
		Button AVLDeletion = new Button("Deletion");
		AVLDeletion.setPrefSize(menuWidth, height * 5/6);
		AVLDeletion.getStylesheets().add("buttonStyle.css");
		
		AVLContent.getChildren().addAll(AVLDef,AVLInsert,AVLDeletion);
		AVLTree.setContent(AVLContent);
		
		TitledPane RBTree = new TitledPane();
		RBTree.setText("Red Black Tree");
		RBTree.setExpanded(false);
		RBTree.getStylesheets().add("titledStyle.css");
		
		VBox RBContent = new VBox();
		Button RBDef = new Button("Definition");
		RBDef.setPrefSize(menuWidth, height * 5/6);
		RBDef.getStylesheets().add("buttonStyle.css");
		
		RBContent.getChildren().addAll(RBDef);
		RBTree.setContent(RBContent);
		
		TitledPane AATree = new TitledPane();
		AATree.setText("AA Tree");
		AATree.setExpanded(false);
		AATree.getStylesheets().add("titledStyle.css");
		
		VBox AAContent = new VBox();
		Button AADef = new Button("Definition");
		AADef.setPrefSize(menuWidth, height * 5/6);
		AADef.getStylesheets().add("buttonStyle.css");
		
		Button AAInsert = new Button("Insertion");
		AAInsert.setPrefSize(menuWidth, height * 5/6);
		AAInsert.getStylesheets().add("buttonStyle.css");
		
		Button AADeletion = new Button("Deletion");
		AADeletion.setPrefSize(menuWidth, height * 5/6);
		AADeletion.getStylesheets().add("buttonStyle.css");
		
		AAContent.getChildren().addAll(AADef,AAInsert,AADeletion);
		AATree.setContent(AAContent);
		
		indexContent.getChildren().addAll(AVLTree, RBTree, AATree);
		menuSpace.setIndexContent(indexContent);
		menuSpace.getIndex().setContent(indexContent);
		
		
//		demoSpace
		StackPane demoSpace = new StackPane();
		
		HBox.setHgrow(demoSpace, Priority.ALWAYS);
		float demoHeight =  windowHeight - 100;
		demoSpace.setAlignment(null);
		demoSpace.setMinHeight(demoHeight);
		
		BTVisualizeContent visualizeSpace = new BTVisualizeContent();
		TheoreticalBackground theoSpace = new TheoreticalBackground("/miniProject/balancedTree/slides",38);
		
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
				
				if(demoSpace.getChildren().get(demoSpace.getChildren().size() - 1) instanceof BTVisualizeContent && (demoSpace.getChildren().get(demoSpace.getChildren().size() - 1)).isVisible() == true) {
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
				if(demoSpace.getChildren().get(demoSpace.getChildren().size() - 1) instanceof BTVisualizeContent && (demoSpace.getChildren().get(demoSpace.getChildren().size() - 1)).isVisible() == true) {
					BTVisualizeContent resetContent = new BTVisualizeContent();
					demoSpace.getChildren().remove(demoSpace.getChildren().size() - 1);
					demoSpace.getChildren().add(resetContent);
				}
				
				else if(demoSpace.getChildren().get(demoSpace.getChildren().size() - 1) instanceof TheoreticalBackground && (demoSpace.getChildren().get(demoSpace.getChildren().size() - 1)).isVisible() == true) {
					TheoreticalBackground resetContent = new TheoreticalBackground("/miniProject/balancedTree/slides",38);
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
		AVLDef.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				int definitionIndex = 2;
				theoSpace.getSceneCollector().getSceneCollectiion().get(theoSpace.getSceneCollector().getSceneIndex()).setVisible(false);
				theoSpace.getSceneCollector().getSceneCollectiion().get(definitionIndex - 1).setVisible(true);
				theoSpace.getSceneCollector().setSceneIndex(definitionIndex - 1);
			}
		});
		
		AVLInsert.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				int insertIndex = 6;
				theoSpace.getSceneCollector().getSceneCollectiion().get(theoSpace.getSceneCollector().getSceneIndex()).setVisible(false);
				theoSpace.getSceneCollector().getSceneCollectiion().get(insertIndex - 1).setVisible(true);
				theoSpace.getSceneCollector().setSceneIndex(insertIndex - 1);
			}
		});
		
		AVLDeletion.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				int deleteIndex = 10;
				theoSpace.getSceneCollector().getSceneCollectiion().get(theoSpace.getSceneCollector().getSceneIndex()).setVisible(false);
				theoSpace.getSceneCollector().getSceneCollectiion().get(deleteIndex - 1).setVisible(true);
				theoSpace.getSceneCollector().setSceneIndex(deleteIndex - 1);
			}
		});
		
		RBDef.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				int definitionIndex = 13;
				theoSpace.getSceneCollector().getSceneCollectiion().get(theoSpace.getSceneCollector().getSceneIndex()).setVisible(false);
				theoSpace.getSceneCollector().getSceneCollectiion().get(definitionIndex - 1).setVisible(true);
				theoSpace.getSceneCollector().setSceneIndex(definitionIndex - 1);
			}
		});
		
		AADef.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				int definitionIndex = 21;
				theoSpace.getSceneCollector().getSceneCollectiion().get(theoSpace.getSceneCollector().getSceneIndex()).setVisible(false);
				theoSpace.getSceneCollector().getSceneCollectiion().get(definitionIndex - 1).setVisible(true);
				theoSpace.getSceneCollector().setSceneIndex(definitionIndex - 1);
			}
		});
		
		AAInsert.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				int insertIndex = 35;
				theoSpace.getSceneCollector().getSceneCollectiion().get(theoSpace.getSceneCollector().getSceneIndex()).setVisible(false);
				theoSpace.getSceneCollector().getSceneCollectiion().get(insertIndex - 1).setVisible(true);
				theoSpace.getSceneCollector().setSceneIndex(insertIndex - 1);
			}
		});
		
		AADeletion.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				int deleteIndex = 36;
				theoSpace.getSceneCollector().getSceneCollectiion().get(theoSpace.getSceneCollector().getSceneIndex()).setVisible(false);
				theoSpace.getSceneCollector().getSceneCollectiion().get(deleteIndex - 1).setVisible(true);
				theoSpace.getSceneCollector().setSceneIndex(deleteIndex - 1);
			}
		});
	}

}
