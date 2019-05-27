package miniProject.linkedList.model;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;

public class LLContainerNode extends StackPane{
	Rectangle body;
	Text value;
	
	public Rectangle getBody() {
		return body;
	}

	public Text getValue() {
		return value;
	}

	public LLContainerNode(float width, float height) {
		super();
		// TODO Auto-generated constructor stub
		this.body = new Rectangle();
		this.body.setFill(Color.TRANSPARENT);
		this.body.setHeight(height);
		this.body.setWidth(width);
		
		this.getChildren().add(this.body);
	}
	
	public LLContainerNode(float width, float height, String Num) {
		super();
		this.body = new Rectangle();
		this.body.setFill(Color.TRANSPARENT);
		this.body.setWidth(width);
		this.body.setHeight(height);
		
		this.value = new Text();
		this.value.setText(Num);

		this.getChildren().addAll(this.body,this.value);
	}
	
}
