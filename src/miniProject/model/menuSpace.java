package miniProject.model;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class menuSpace extends VBox{
	
	Text menuName; 
	
	Button visualizeButton;
	public Button getVisualizeButton() {
		return visualizeButton;
	}
	
	Rectangle visualBar;
	public Rectangle getVisualBar() {
		return visualBar;
	}

	Button theoButton;
	
	public Button getTheoButton() {
		return theoButton;
	}

	TitledPane index;
	public TitledPane getIndex() {
		return index;
	}

	VBox indexContent;
	public void setIndexContent(VBox indexContent) {
		this.indexContent = indexContent;
	}

	Button backButton;
	public Button getBackButton() {
		return backButton;
	}

	Button nextButton;
	public Button getNextButton() {
		return nextButton;
	}
	
	ToggleButton pauseButton;
	public ToggleButton getPauseButton() {
		return pauseButton;
	}
	
	Button resetButton;
	public Button getResetButton() {
		return resetButton;
	}

	Button exitButton;
	public Button getExitButton() {
		return exitButton;
	}

	public menuSpace(String dataName) {
		super();
		
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		
		float windowWidth = gd.getDisplayMode().getWidth() * 4/5;
		float windowHeight = (float) (windowWidth / 1.725);
		
		int width = gd.getDisplayMode().getWidth() / 8;
		int height = gd.getDisplayMode().getHeight() / 20;
		
		float menuWidth = windowWidth /4;
		float menuHeight = windowHeight;
		this.setMaxWidth(menuWidth);
		this.setSpacing(3);
		
		StackPane menuTitle = new StackPane();
		menuTitle.setPadding(new Insets(20));
	
//		Set Name of Window
		menuName = new Text(dataName);
		menuName.setStyle("-fx-fill: white;"
				+ " -fx-font-size: 30;"
				+ "-fx-font-weight: bold;"
				+ " -fx-font-family: \"Montserrat\";");
		menuTitle.setPrefSize(menuWidth, height * 1.5 + 10);
		menuTitle.getChildren().add(menuName);
		StackPane.setAlignment(menuName, Pos.CENTER);
		
		this.getChildren().add(menuTitle);
        
//        SELECT
        
        VBox SelectContent = new VBox();
        
        StackPane visualizeSelect = new StackPane();
        visualizeSelect.setPrefSize(menuWidth, height);
        visualBar = new Rectangle();
        visualBar.setFill(Paint.valueOf("#5385BF"));
        visualBar.setHeight(height);
        visualBar.setWidth(10);
        visualBar.setVisible(false);
        
        ImageView visualizeView = new ImageView("/miniProject/main/Icons/visualize.png");
        visualizeButton = new Button("Visualization",visualizeView);
		visualizeButton.setPrefSize(menuWidth, height);
		visualizeButton.getStylesheets().add("selectButtonStyle.css");
		visualizeSelect.getChildren().addAll(visualBar,visualizeButton);
		visualBar.setViewOrder(0);
		visualizeButton.setViewOrder(1);
		StackPane.setAlignment(visualBar, Pos.CENTER_LEFT);
        
		StackPane theoSelect = new StackPane();
        visualizeSelect.setPrefSize(menuWidth, height);
        Rectangle theoBar = new Rectangle();
        theoBar.setFill(Color.TRANSPARENT);
        theoBar.setHeight(height);
        theoBar.setWidth(10);
        
		ImageView theoView = new ImageView("/miniProject/main/Icons/background.png");
		theoButton = new Button("Theoretical Background",theoView);
		theoButton.setPrefSize(menuWidth, height);
		theoButton.getStylesheets().add("selectButtonStyle.css");
        theoSelect.getChildren().addAll(theoBar,theoButton);
        theoBar.setViewOrder(0);
		theoButton.setViewOrder(1);
        StackPane.setAlignment(theoBar, Pos.CENTER_LEFT);
        
        SelectContent.getChildren().addAll(visualizeSelect,theoSelect);
		this.getChildren().add(SelectContent);
		VBox.setVgrow(SelectContent, Priority.ALWAYS);
		
//		THEORICAL BACKGROUND INDEX
		index = new TitledPane();
		index.setText("Background Contents");
		index.setExpanded(false);
		index.getStylesheets().add("titledStyle.css");
        
		index.setContent(indexContent);
		
		Rectangle indexSpace = new Rectangle(20,height);
		indexSpace.setFill(Paint.valueOf("#121212"));
		
		HBox indexContainer = new HBox();
		indexContainer.getChildren().addAll(indexSpace,index);
		
		SelectContent.getChildren().add(indexContainer);
		
		index.setVisible(false);
		
		
		
//		Menu BAR
		HBox menuBar = new HBox();
		double buttonSize = menuWidth / 5;
		double buttonHeight = buttonSize * 6/9;
		
		String defaultBARStyle = new String("-fx-background-color: #282828;-fx-opacity: 0.6");
		String hightlightBARStyle = new String("-fx-background-color: #282828");
		
		ImageView backView = new ImageView("/miniProject/main/Icons/left.png");
		backButton = new Button("",backView);
		backButton.setPrefSize(buttonSize,buttonHeight);
		backButton.setStyle(defaultBARStyle);
		backButton.setOnMouseEntered(e->backButton.setStyle(hightlightBARStyle));
        backButton.setOnMouseExited(e->backButton.setStyle(defaultBARStyle));
        backButton.setTooltip(
        	    new Tooltip("Back")
        	);
		
        ImageView nextView = new ImageView("/miniProject/main/Icons/right.png");
		nextButton = new Button("",nextView);
		nextButton.setPrefSize(buttonSize,buttonHeight);
		nextButton.setStyle(defaultBARStyle);
		nextButton.setOnMouseEntered(e->nextButton.setStyle(hightlightBARStyle));
        nextButton.setOnMouseExited(e->nextButton.setStyle(defaultBARStyle));
        nextButton.setTooltip(
        	    new Tooltip("Next")
        	);
        
        ImageView pauseView = new ImageView("/miniProject/main/Icons/pause.png");
		pauseButton = new ToggleButton("",pauseView);
		pauseButton.setPrefSize(buttonSize,buttonHeight);
		pauseButton.getStylesheets().add("ToggleButton.css");
		pauseButton.setTooltip(
        	    new Tooltip("Pause")
        	);
        pauseButton.setSelected(false);
        
		ImageView resetView = new ImageView("/miniProject/main/Icons/reset.png");
		resetButton = new Button("",resetView);
		resetButton.setPrefSize(buttonSize,buttonHeight);
		resetButton.setStyle(defaultBARStyle);
		resetButton.setOnMouseEntered(e->resetButton.setStyle(hightlightBARStyle));
        resetButton.setOnMouseExited(e->resetButton.setStyle(defaultBARStyle));
        resetButton.setTooltip(
        	    new Tooltip("Reset")
        	);
        
		ImageView exitView = new ImageView("/miniProject/main/Icons/exit.png");
		exitButton = new Button("",exitView);
		exitButton.setPrefSize(buttonSize,buttonHeight);
		exitButton.setStyle(defaultBARStyle);
		exitButton.setOnMouseEntered(e->exitButton.setStyle(hightlightBARStyle));
        exitButton.setOnMouseExited(e->exitButton.setStyle(defaultBARStyle));
        exitButton.setTooltip(
        	    new Tooltip("Exit")
        	);
        
        menuBar.getChildren().addAll(resetButton,backButton,pauseButton,nextButton,exitButton);
        this.getChildren().add(menuBar);
        
		this.setMinSize(menuWidth,menuHeight);
		this.setStyle("-fx-background-color: #121212; -fx-opacity: 0.6;");
	}
}
