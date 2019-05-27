package miniProject.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class ImageScene extends ScrollPane{

	public ImageScene(final ObjectProperty<Image> poster) {
		super();
		setBackground(new Background(new BackgroundFill(Color.WHITE,CornerRadii.EMPTY,Insets.EMPTY)));
		// TODO Auto-generated constructor stub
		final ReadOnlyDoubleProperty widthProperty = widthProperty();
		final ReadOnlyDoubleProperty heightProperty = heightProperty();
		setHbarPolicy(ScrollBarPolicy.NEVER);
		setVbarPolicy(ScrollBarPolicy.NEVER);
		setContent(new ImageView() {{
			imageProperty().bind(poster);
			setPreserveRatio(true);
	        setSmooth(true);
	        
	        fitWidthProperty().bind(widthProperty);
	        fitHeightProperty().bind(heightProperty);

	     }});
	}
	
}
