package miniProject.linkedList.path;

import javafx.scene.layout.StackPane;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

public class moveFrontAtPos extends Path{
	public moveFrontAtPos(int pos, StackPane linkedListLayer, float width, float height) {
		super();
		this.getElements().add(new MoveTo(linkedListLayer.getWidth()/2 - 4.5*width + pos * width,linkedListLayer.getHeight() / 2 - height/2));
		this.getElements().add(new LineTo(linkedListLayer.getWidth()/2 - 4.5*width + pos * width - width,linkedListLayer.getHeight() / 2 - height/2));
	}
}
