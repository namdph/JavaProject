package miniProject.balancedTree.model;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javafx.scene.Group;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class BTNode extends Group{
	Circle body;
	Text value;
	public Text heightDisplay;
	int height,num;
	double coordinateX , coordinateY, radius;
	StackPane treeNode;
	
	BTNode left, right;
	
	
	public double getCoordinateX() {
		return coordinateX;
	}

	public void setCoordinateX(double coordinateX) {
		this.coordinateX = coordinateX;
	}

	public double getCoordinateY() {
		return coordinateY;
	}

	public void setCoordinateY(double coordinateY) {
		this.coordinateY = coordinateY;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public BTNode getLeft() {
		return left;
	}

	public void setLeft(BTNode left) {
		this.left = left;
	}

	public BTNode getRight() {
		return right;
	}

	public void setRight(BTNode right) {
		this.right = right;
	}

	public Circle getBody() {
		return body;
	}

	public StackPane getTreeNode() {
		return treeNode;
	}

	public BTNode(int num) {
		
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		radius = gd.getDisplayMode().getWidth() / 65;
		
		this.num = num;
		
		body = new Circle(radius);
		body.setFill(Color.CADETBLUE);
		
		height = 1;
		
		value = new Text();
		value.setText(Integer.toString(num));

		treeNode = new StackPane();
		treeNode.getChildren().addAll(this.body,this.value);
		
		heightDisplay  = new Text();
		heightDisplay.setText(Integer.toString(height));
		heightDisplay.setLayoutX(coordinateX);
		heightDisplay.setLayoutY(coordinateY);
		
		this.setLeft(null);
		this.setRight(null);
		
		this.getChildren().addAll(this.heightDisplay,this.treeNode);
	}
	
}
