package miniProject.model;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class TheoreticalBackground extends StackPane{
	
	Button nextButton;
	Button previousButton;
	SceneCollector sceneCollector;
	
	public SceneCollector getSceneCollector() {
		return sceneCollector;
	}

	public Button getNextButton() {
		return nextButton;
	}

	public Button getPreviousButton() {
		return previousButton;
	}

	public TheoreticalBackground(String slicesURL,int sceneCount) {
		super();
		
		this.setPadding(new Insets(0));
    	GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int windowWidth = gd.getDisplayMode().getWidth() * 17/30 ;
		@SuppressWarnings("unused")
		int windowHeight = gd.getDisplayMode().getHeight() * 17/20 - 100;
		
    	this.nextButton = new Button("Next");
    	this.previousButton = new Button("Previous");
    	
    	this.getChildren().addAll(nextButton,previousButton);
    	nextButton.setVisible(false);
    	previousButton.setVisible(false);
    	
    	this.sceneCollector = new SceneCollector();
    	
    	for(int i = 1; i <= sceneCount; i++) {
    		StackPane sceneContainer = new StackPane();
    		sceneContainer.setBackground(new Background(new BackgroundFill(Color.WHITE,CornerRadii.EMPTY,Insets.EMPTY)));
    		final ObjectProperty<Image> poster = new SimpleObjectProperty<Image>(new Image(slicesURL + "/Scene"+i+".png"));  
    		
    		ImageScene newScene = new ImageScene(poster);
    		sceneContainer.getChildren().add(newScene);
    		sceneCollector.getSceneCollectiion().add(sceneContainer);
    		
    		this.getChildren().add(sceneContainer);
    		if(i != 1) sceneContainer.setVisible(false);
    	}
    	
    	
    	nextButton.setOnAction(new EventHandler<ActionEvent>(){
    		
    		@Override
    		public void handle(ActionEvent event) {
    			if(sceneCollector.getSceneIndex() >= sceneCount - 1) {
    			}
    			else {
    				nextButton.setDisable(true);
    				StackPane curScene = sceneCollector.getSceneCollectiion().get(sceneCollector.getSceneIndex() +1);
				
    				//Set X of second scene to Height of window
    				curScene.translateXProperty().set(windowWidth);
    				//Add second scene. Now both first and second scene is present
    				curScene.setVisible(true);
    				//Create new TimeLine animation
    				Timeline timeline = new Timeline();
    				//Animate Y property
    				KeyValue kv = new KeyValue(curScene.translateXProperty(), 0, Interpolator.EASE_IN);
    				KeyFrame kf = new KeyFrame(Duration.seconds(0.25), kv);
    				timeline.getKeyFrames().add(kf);
    				//After completing animation, remove first scene
    				timeline.setOnFinished(t -> {
    					sceneCollector.getSceneCollectiion().get(sceneCollector.getSceneIndex()).setVisible(false);
    					sceneCollector.addSceneIndex();
    					nextButton.setDisable(false);
    				});
    				timeline.play();
    			}
    		}
    	});
    	
previousButton.setOnAction(new EventHandler<ActionEvent>(){
    		
    		@Override
    		public void handle(ActionEvent event) {
    			if(sceneCollector.getSceneIndex() == 0) {
    			}
    			else {
    				previousButton.setDisable(true);
    				StackPane curScene = sceneCollector.getSceneCollectiion().get(sceneCollector.getSceneIndex());
				
    				curScene.autosize();
				
    				//Set X of second scene to Height of window
    				curScene.translateXProperty().set(0);;
    				//Add second scene. Now both first and second scene is present
    				sceneCollector.getSceneCollectiion().get(sceneCollector.getSceneIndex() -1).setVisible(true);
    				//Create new TimeLine animation
    				Timeline timeline = new Timeline();
    				//Animate Y property
    				KeyValue kv = new KeyValue(curScene.translateXProperty(),windowWidth, Interpolator.EASE_IN);
    				KeyFrame kf = new KeyFrame(Duration.seconds(0.25), kv);
    				timeline.getKeyFrames().add(kf);
    				//After completing animation, remove first scene
    				timeline.setOnFinished(t -> {
    					curScene.setVisible(false);
    					curScene.translateXProperty().set(0);
    					sceneCollector.subSceneIndex();
    					previousButton.setDisable(false);
    				});
    				timeline.play();
    			}
    		}
    	});

    	this.setOnKeyReleased(new EventHandler<KeyEvent>() {
    		@Override
    		public void handle(KeyEvent event) {
    			switch(event.getCode()) {
    			case RIGHT: {
    				nextButton.fire();
    				break;
    			}
    			case LEFT:{ 
    				previousButton.fire();
    				break;
    			}
				default:
					break; 
    			}
    		}
    	});
    		
    }
    
}
