package miniProject.stack.model;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class StackContainer extends Group {
	int stackHeight;
	
	public int getStackHeight() {
		return stackHeight;
	}

	public void setStackHeight(int stackHeight) {
		this.stackHeight = stackHeight;
	}

	public StackContainer(int width,int height) {
		setStackHeight(height * 8);
		Line side1 = new Line(0,0,0,stackHeight);
		side1.setStrokeWidth(5);
		side1.setStroke(Color.BLACK);
		
		Line side2 = new Line(width +10,0,width+10,stackHeight);
		side2.setStrokeWidth(5);
		side2.setStroke(Color.BLACK);
		
		Line bottom = new Line(0,stackHeight,width + 10,stackHeight);
		bottom.setStrokeWidth(5);
		bottom.setStroke(Color.BLACK);
		this.getChildren().addAll(bottom,side1,side2);
	}
}
