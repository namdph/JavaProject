package miniProject.linkedList.path;

import javafx.scene.layout.StackPane;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

public class deleteAtPos extends Path{

	public deleteAtPos(int pos,StackPane linkedListLayer,float width,float height) {
		super();
		// TODO Auto-generated constructor stub
		this.getElements().add(new MoveTo(linkedListLayer.getWidth() / 2 - 4.5*width + pos*width,linkedListLayer.getHeight() / 2 - height/2));
		this.getElements().add(new LineTo(linkedListLayer.getWidth() / 2 - 4.5*width + pos*width,height * 2));
		this.getElements().add(new LineTo(linkedListLayer.getWidth() /2 + 4.5*width,height * 2));
	}
	
}
