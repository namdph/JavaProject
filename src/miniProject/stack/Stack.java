package miniProject.stack;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.util.ArrayList;

import javafx.animation.Animation;
import javafx.animation.PathTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import miniProject.balancedTree.model.BTVisualizeContent;
import miniProject.model.TheoreticalBackground;
import miniProject.model.menuSpace;
import miniProject.stack.model.SVisualizeContent;

public class Stack extends Stage{
	
	ArrayList<Animation> timeCapsule = new ArrayList<Animation>();
	public ArrayList<Animation> getTimeCapsule() {
		return timeCapsule;
	}

	public void setTimeCapsule(ArrayList<Animation> timeCapsule) {
		this.timeCapsule = timeCapsule;
	}
	
	int timeCounter = 0;
	public int getTimeCounter() {
		return timeCounter;
	}
	public void setTimeCounter(int timeCounter) {
		this.timeCounter = timeCounter;
	}
	
	public Stack(){
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
		
//		THEORETICAL BACKGROUND INDEX
		VBox indexContent = new VBox();
		Button definition = new Button("Definition");
		
		definition.setPrefSize(menuWidth, height);
		definition.getStylesheets().add("buttonStyle.css");
		
		TitledPane operation = new TitledPane();
		operation.setText("Operations");
		operation.setExpanded(false);
		operation.getStylesheets().add("titledStyle.css");
		
		VBox operationContent = new VBox();
		
		Button init = new Button("Initialize");
		init.setPrefSize(menuWidth, height);
		init.getStylesheets().add("buttonStyle.css");
		
		Button push = new Button("Push");
		push.setPrefSize(menuWidth, height);
		push.getStylesheets().add("buttonStyle.css");
		
		Button pop = new Button("Pop");
		pop.setPrefSize(menuWidth, height);
		pop.getStylesheets().add("buttonStyle.css");
		
		operationContent.getChildren().addAll(init,push,pop);
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
		
		SVisualizeContent visualizeSpace = new SVisualizeContent();
		TheoreticalBackground theoSpace = new TheoreticalBackground("/miniProject/stack/slides",14);
		
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
				
				if(demoSpace.getChildren().get(demoSpace.getChildren().size() - 1) instanceof SVisualizeContent && (demoSpace.getChildren().get(demoSpace.getChildren().size() - 1)).isVisible() == true) {
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
		menuSpace.getPauseButton().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(menuSpace.getPauseButton().isSelected() == true) {
					if (demoSpace.getChildren().get(demoSpace.getChildren().size() - 1) instanceof SVisualizeContent && (demoSpace.getChildren().get(demoSpace.getChildren().size() - 1)).isVisible() == true) {
						setTimeCapsule(((SVisualizeContent) demoSpace.getChildren().get(demoSpace.getChildren().size() - 1)).getTimeCapsule());
						setTimeCounter(getTimeCapsule().size() - 1);
						((SVisualizeContent) demoSpace.getChildren().get(demoSpace.getChildren().size() - 1)).getPushButton().setDisable(true);
						((SVisualizeContent) demoSpace.getChildren().get(demoSpace.getChildren().size() - 1)).getPopButton().setDisable(true);
						menuSpace.getPauseButton().setTooltip(new Tooltip("Unpause"));
					}
				}
				if(menuSpace.getPauseButton().isSelected() == false){
					setTimeCounter(getTimeCounter() + 1);
					while(getTimeCounter() < getTimeCapsule().size()) {
						Animation finishedAnimation = getTimeCapsule().get(getTimeCounter());
						((PathTransition) finishedAnimation).getNode().setVisible(true);
						if(((SVisualizeContent) demoSpace.getChildren().get(demoSpace.getChildren().size() - 1)).getTimeCapsule().getType_checker().get(getTimeCounter()) == 0)
							((PathTransition) finishedAnimation).getNode().setVisible(false);
						else {
							((PathTransition) finishedAnimation).getNode().setVisible(true);
						}
						finishedAnimation.setOnFinished(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent event) {
							}
						});
						((PathTransition) finishedAnimation).setDuration(Duration.millis(10));
						finishedAnimation.setRate(1);
						finishedAnimation.play();
						setTimeCounter(getTimeCounter() + 1);
					}
					System.gc();
					setTimeCapsule(new ArrayList<Animation>());
					setTimeCounter(0);
					((SVisualizeContent) demoSpace.getChildren().get(demoSpace.getChildren().size() - 1)).getPushButton().setDisable(false);
					((SVisualizeContent) demoSpace.getChildren().get(demoSpace.getChildren().size() - 1)).getPopButton().setDisable(false);
					menuSpace.getPauseButton().setTooltip(new Tooltip("Pause"));
				}
			}
		});
		
		menuSpace.getBackButton().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(demoSpace.getChildren().get(demoSpace.getChildren().size() - 1) instanceof TheoreticalBackground && (demoSpace.getChildren().get(demoSpace.getChildren().size() - 1)).isVisible() == true) {
					((TheoreticalBackground) demoSpace.getChildren().get(demoSpace.getChildren().size() - 1)).getPreviousButton().fire();
				}
				else if (demoSpace.getChildren().get(demoSpace.getChildren().size() - 1) instanceof SVisualizeContent && (demoSpace.getChildren().get(demoSpace.getChildren().size() - 1)).isVisible() == true) {
					if(getTimeCounter() > 0) {
						menuSpace.getBackButton().setDisable(true);
						Animation backAnimatation = getTimeCapsule().get(getTimeCounter());
						((PathTransition) backAnimatation).getNode().setVisible(true);
						backAnimatation.jumpTo(backAnimatation.getTotalDuration());
						backAnimatation.setRate(-1);
						backAnimatation.play();
						
						backAnimatation.setOnFinished(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent event) {
								if(((SVisualizeContent) demoSpace.getChildren().get(demoSpace.getChildren().size() - 1)).getTimeCapsule().getType_checker().get(getTimeCounter()) == 1) 
									((PathTransition) backAnimatation).getNode().setVisible(false);
								else
									((PathTransition) backAnimatation).getNode().setVisible(true);
								menuSpace.getBackButton().setDisable(false);
								if(getTimeCounter() != 0)
									setTimeCounter(getTimeCounter() - 1);
							}
						});
					}
				}	
			}
		});
		
		menuSpace.getNextButton().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(demoSpace.getChildren().get(demoSpace.getChildren().size() - 1) instanceof TheoreticalBackground && (demoSpace.getChildren().get(demoSpace.getChildren().size() - 1)).isVisible() == true) {
					((TheoreticalBackground) demoSpace.getChildren().get(demoSpace.getChildren().size() - 1)).getNextButton().fire();
				}
				else if (demoSpace.getChildren().get(demoSpace.getChildren().size() - 1) instanceof SVisualizeContent && (demoSpace.getChildren().get(demoSpace.getChildren().size() - 1)).isVisible() == true) {
					if(getTimeCounter() + 1 < getTimeCapsule().size()){
						menuSpace.getNextButton().setDisable(true);
						setTimeCounter(getTimeCounter() + 1);
						Animation animation = getTimeCapsule().get(getTimeCounter());
						((PathTransition) animation).getNode().setVisible(true);
						animation.setOnFinished(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent event) {
								if(((SVisualizeContent) demoSpace.getChildren().get(demoSpace.getChildren().size() - 1)).getTimeCapsule().getType_checker().get(getTimeCounter()) == 1) 
									((PathTransition) animation).getNode().setVisible(true);
								else
									((PathTransition) animation).getNode().setVisible(false);
								menuSpace.getNextButton().setDisable(false);
							}
						});
						animation.setRate(1);
						animation.playFromStart();
					}
				}	
			}
		});
		
		menuSpace.getResetButton().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(demoSpace.getChildren().get(demoSpace.getChildren().size() - 1) instanceof SVisualizeContent && (demoSpace.getChildren().get(demoSpace.getChildren().size() - 1)).isVisible() == true) {
					SVisualizeContent resetContent = new SVisualizeContent();
					demoSpace.getChildren().remove(demoSpace.getChildren().size() - 1);
					demoSpace.getChildren().add(resetContent);
				}
				
				else if(demoSpace.getChildren().get(demoSpace.getChildren().size() - 1) instanceof TheoreticalBackground && (demoSpace.getChildren().get(demoSpace.getChildren().size() - 1)).isVisible() == true) {
					TheoreticalBackground resetContent = new TheoreticalBackground("/miniProject/stack/slides",14);
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
		
		this.setTitle("Stack");
		this.setScene(scene);
		
//		INDEX ACTION
		definition.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				int definitionIndex = 1;
				theoSpace.getSceneCollector().getSceneCollectiion().get(theoSpace.getSceneCollector().getSceneIndex()).setVisible(false);
				theoSpace.getSceneCollector().getSceneCollectiion().get(definitionIndex - 1).setVisible(true);
				theoSpace.getSceneCollector().setSceneIndex(definitionIndex - 1);
			}
		});
		
		init.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				int initIndex = 10;
				theoSpace.getSceneCollector().getSceneCollectiion().get(theoSpace.getSceneCollector().getSceneIndex()).setVisible(false);
				theoSpace.getSceneCollector().getSceneCollectiion().get(initIndex - 1).setVisible(true);
				theoSpace.getSceneCollector().setSceneIndex(initIndex - 1);
			}
		});
		
		push.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				int pushIndex = 13;
				theoSpace.getSceneCollector().getSceneCollectiion().get(theoSpace.getSceneCollector().getSceneIndex()).setVisible(false);
				theoSpace.getSceneCollector().getSceneCollectiion().get(pushIndex - 1).setVisible(true);
				theoSpace.getSceneCollector().setSceneIndex(pushIndex - 1);
			}
		});
		
		pop.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				int popIndex = 14;
				theoSpace.getSceneCollector().getSceneCollectiion().get(theoSpace.getSceneCollector().getSceneIndex()).setVisible(false);
				theoSpace.getSceneCollector().getSceneCollectiion().get(popIndex - 1).setVisible(true);
				theoSpace.getSceneCollector().setSceneIndex(popIndex - 1);
			}
		});
	}

}
