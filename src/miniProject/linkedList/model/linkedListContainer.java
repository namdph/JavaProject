package miniProject.linkedList.model;

import java.util.ArrayList;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class linkedListContainer extends VBox{
	HBox ListBox;
	HBox ListPos;
	ArrayList<LLContainerNode> boxNodes;
	ArrayList<LLContainerNode> posNodes;
	
	public ArrayList<LLContainerNode> getBoxNodes() {
		return boxNodes;
	}

	public ArrayList<LLContainerNode> getPosNodes() {
		return posNodes;
	}
	
	public linkedListContainer(float width, float height) {
		super();
		this.ListBox = new HBox();
		this.ListPos = new HBox();
		// TODO Auto-generated constructor stub
		this.boxNodes = new ArrayList<LLContainerNode>();
		this.posNodes = new ArrayList<LLContainerNode>();
		
		for(int i = 0; i < 10; i++) {
			LLContainerNode boxNode = new LLContainerNode(width, height);
			this.boxNodes.add(boxNode);
			this.ListBox.getChildren().add(boxNode);
		}
		for(int i = 0; i < 10; i++) {
			LLContainerNode posNode = new LLContainerNode(width, height, Integer.toString(i+1));
			this.posNodes.add(posNode);
			this.ListPos.getChildren().add(posNode);
		}
		this.getChildren().addAll(ListBox,ListPos);
	}
}
