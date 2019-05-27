package miniProject.model;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;

public class Node_t extends StackPane{
	Rectangle body;
	Text value;
	public Rectangle getBody() {
		return body;
	}

	public void setBody(Rectangle body) {
		this.body = body;
	}

	public Text getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value.setText(value);
	}

	public Node_t(float width, float height) {
		super();
		this.body = new Rectangle();
		this.body.setFill(Color.BISQUE);	
		this.body.setWidth(width);
		this.body.setHeight(height);
		this.body.setStroke(Color.BLACK);
		this.body.setStrokeType(StrokeType.INSIDE);
		
		this.value = new Text();
		this.value.setText("");
		this.value.setStyle("-fx-text-fill: white;\r\n" + 
				"-fx-font-family: \"Proxima Nova\";\r\n" + 
				"-fx-font-size: 15;\r\n" + 
				"-fx-font-weight: bold;");
		this.getChildren().addAll(this.body,this.value);
	}
	public Node_t(float width, float height, String Num) {	
		super();
		this.body = new Rectangle();
		this.body.setFill(Color.BISQUE);	
		this.body.setWidth(width);
		this.body.setHeight(height);
		this.body.setStroke(Color.BLACK);
		this.body.setStrokeType(StrokeType.INSIDE);
		
		this.value = new Text();
		this.value.setText(Num);
		this.value.setStyle("-fx-text-fill: white;\r\n" + 
				"-fx-font-family: \"Proxima Nova\";\r\n" + 
				"-fx-font-size: 15;\r\n" + 
				"-fx-font-weight: bold;");
		this.getChildren().addAll(this.body,this.value);
	}
}