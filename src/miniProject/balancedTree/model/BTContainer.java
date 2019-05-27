package miniProject.balancedTree.model;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.util.ArrayList;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.util.Duration;

public class BTContainer {
	
//Balanced Tree fields	
	public BTNode Root = null; 
	Duration speed = Duration.millis(350);
	
	GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	
	ArrayList<markedLine> lineCollector = new ArrayList<markedLine>();
	DoubleProperty radiusAdjust = new SimpleDoubleProperty(gd.getDisplayMode().getWidth() / 65);
	
	public float rootCoordinateX = (float) (gd.getDisplayMode().getWidth() / 3.5);
	public float rootCoordinateY = gd.getDisplayMode().getHeight() / 20;	
	
	public float disX = gd.getDisplayMode().getWidth() / 50;
	public float disY = gd.getDisplayMode().getHeight() / 17;
	
	public ArrayList<Animation> animateSequence;
	public void setAnimateSequence(ArrayList<Animation> animateSequence) {
		this.animateSequence = animateSequence;
	}
	public ArrayList<Animation> getAnimateSequence() {
		return animateSequence;
	}

	int nodeHeight(BTNode node) {
		if(node == null) return 0;
		return 1 + max(nodeHeight(node.getLeft()), nodeHeight(node.getRight()));
	}
	
	int max(int a, int b) {
		return (a > b) ? a : b;
	}
	
	boolean isEmpty(BTNode node) {
		return node == null;
	}
	
	boolean isLeaf(BTNode node) {
		return (node.getLeft() == null && node.getRight() == null);
	}
	
//	Balanced Tree methods
	BTNode make_node(Pane window, int num) {
		BTNode newNode = new BTNode(num);
		window.getChildren().add(newNode);
		return newNode;
	}
	
	BTNode find_node(BTNode root, int key) {
		if(isEmpty(root))
			return null;
		if(root.getNum() == key) return root;
		if(root.getNum() > key) return find_node(root.getLeft(),key);
		else return find_node(root.getRight(),key);
	}
	
//	Get Parent Node
	BTNode getParentNode(BTNode node) {
		return parentHelper(this.Root,node);
	}
	
	private BTNode parentHelper(BTNode currentNode, BTNode node) {
		if(node == this.Root || currentNode == null)
			return null;
		else {
			if(currentNode.getLeft() == node || currentNode.getRight() == node)
				return currentNode;
			else {
				if(currentNode.getNum() < node.getNum())
					return parentHelper(currentNode.getRight(),node);
				else
					return parentHelper(currentNode.getLeft(),node);
			}
		}
	}
	
//	Get SubTree
	ArrayList<BTNode> getGroupTree(BTNode node) {
		ArrayList<BTNode> tree = new ArrayList<BTNode>();
		if(node.getLeft() != null)
			tree.addAll(getGroupTree(node.getLeft()));
		if(node != null) tree.add(node);
		if(node.getRight() != null)
			tree.addAll(getGroupTree(node.getRight()));
		return tree;
	}
	
//	Find RIGHT MOST
	BTNode find_rightMost(BTNode root) {
		if(isLeaf(root)) return root;
		if(root.getRight() != null)
			return find_rightMost(root.getRight());
		return root;
	}
	
//	Find LEFT MOST
	BTNode find_leftMost(BTNode root) {
		if(isLeaf(root)) return root;
		if(root.getLeft() != null)
			return find_leftMost(root.getLeft());
		return root;
	}
	
//	Rotate Right
	BTNode singleRotateRight(Pane window,BTNode root, Circle glow) {
		BTNode father = getParentNode(root);
		BTNode leftChild = root.getLeft();
		BTNode rightSubTree = leftChild.getRight();
		
		
		ArrayList<BTNode> childTree = getGroupTree(leftChild);
		ArrayList<BTNode> rootTree = getGroupTree(root);
		rootTree.removeAll(childTree);
		
		if(rightSubTree != null){
			ArrayList<BTNode> rightSub = getGroupTree(rightSubTree);
			childTree.removeAll(rightSub);
		}
		
//		Rotate Right Animation
		double moveChildX = root.coordinateX - leftChild.coordinateX;
		double moveChildY = root.coordinateY - leftChild.coordinateY;
		double moveRootX;
		double moveRootY;
		BTNode rootRightChild = root.getRight();
		if(rootRightChild != null) {
			moveRootX = rootRightChild.coordinateX - root.coordinateX;
			moveRootY = rootRightChild.coordinateY - root.coordinateY;
		}
		else {
			moveRootX = disX;
			moveRootY = disY;
		}
		
		Timeline timeline = new Timeline();
		
		for(int i = 0; i < childTree.size(); i++) {
			KeyFrame k1 = new KeyFrame(speed,
					new KeyValue(childTree.get(i).translateXProperty(), childTree.get(i).coordinateX + moveChildX),
					new KeyValue(childTree.get(i).translateYProperty(), childTree.get(i).coordinateY + moveChildY));
			timeline.getKeyFrames().add(k1);
			childTree.get(i).setCoordinateX(childTree.get(i).coordinateX + moveChildX);
			childTree.get(i).setCoordinateY(childTree.get(i).coordinateY + moveChildY);
		}
		
		for(int i = 0; i < rootTree.size(); i++) {
			KeyFrame k2 = new KeyFrame(speed,
					new KeyValue(rootTree.get(i).translateXProperty(), rootTree.get(i).coordinateX + moveRootX),
					new KeyValue(rootTree.get(i).translateYProperty(), rootTree.get(i).coordinateY + moveRootY));
			timeline.getKeyFrames().add(k2);
			rootTree.get(i).setCoordinateX(rootTree.get(i).coordinateX + moveRootX);
			rootTree.get(i).setCoordinateY(rootTree.get(i).coordinateY + moveRootY);
		}
		
		if(father != null) {
			ArrayList<Integer> oldRootEnds = new ArrayList<Integer>();
			oldRootEnds.add(root.getNum());
			oldRootEnds.add(father.getNum());
		
			for(int i = 0; i < lineCollector.size(); i++) {
				if(lineCollector.get(i).ends.containsAll(oldRootEnds)) {
					markedLine line = lineCollector.get(i);
					lineCollector.remove(i);
					window.getChildren().remove(line);
				}
			}
			markedLine oldRootLine = new markedLine();
			
			oldRootLine.startXProperty().bind(father.getBody().centerXProperty().add(father.translateXProperty().add(radiusAdjust)));
			oldRootLine.startYProperty().bind(father.getBody().centerYProperty().add(father.translateYProperty().add(radiusAdjust)));
			oldRootLine.endXProperty().bind(leftChild.getBody().centerXProperty().add(leftChild.translateXProperty().add(radiusAdjust)));
			oldRootLine.endYProperty().bind(leftChild.getBody().centerYProperty().add(leftChild.translateYProperty().add(radiusAdjust)));
			
			oldRootLine.setViewOrder(2);
			oldRootLine.setStroke(Color.GREENYELLOW);
			oldRootLine.setStrokeWidth(3);
			oldRootLine.ends.add(father.getNum());
			oldRootLine.ends.add(leftChild.getNum());
		    lineCollector.add(oldRootLine);
		    window.getChildren().add(oldRootLine);
		}
		
		timeline.setOnFinished(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				root.heightDisplay.setTranslateX(root.radius * 2);
				root.heightDisplay.setText(Integer.toString(root.getHeight()));
				glow.setVisible(false);
			}
		});
		
		getAnimateSequence().add(timeline);
		
		if(father != null) {
			if(root == father.getLeft()) {
				father.setLeft(leftChild);
			}
			else if(root == father.getRight()) {
				father.setRight(leftChild);
			}
		}
		else {
			Root = leftChild;
		}
		leftChild.setRight(root);
		root.setLeft(rightSubTree);
		
//		end Rotate Right Animation
		
//		Move rightSubTree of leftChild
		Timeline rightChildMove = new Timeline();
		
		if(rightSubTree != null) {
			
			double moveRightSubX = root.coordinateX - disX - rightSubTree.coordinateX;
			double moveRightSubY = root.coordinateY + disY - rightSubTree.coordinateY;
			
			ArrayList<BTNode> rightSub = getGroupTree(rightSubTree);
			
			for(int i = 0; i < rightSub.size(); i++) {
				KeyFrame kf = new KeyFrame(speed,
						new KeyValue(rightSub.get(i).translateXProperty(), rightSub.get(i).coordinateX + moveRightSubX),
						new KeyValue(rightSub.get(i).translateYProperty(), rightSub.get(i).coordinateY + moveRightSubY));
				rightChildMove.getKeyFrames().add(kf);
				rightSub.get(i).setCoordinateX(rightSub.get(i).coordinateX + moveRightSubX);
				rightSub.get(i).setCoordinateY(rightSub.get(i).coordinateY + moveRightSubY);
			}
			
			timeline.setOnFinished(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					leftChild.heightDisplay.setText(Integer.toString(leftChild.getHeight()));
					root.heightDisplay.setText(Integer.toString(root.getHeight()));
					ArrayList<Integer> rightChildEnds = new ArrayList<Integer>();
					
					rightChildEnds.add(leftChild.getNum());
					rightChildEnds.add(rightSubTree.getNum());
					
					for(int i = 0; i < lineCollector.size(); i++) {
						if(lineCollector.get(i).ends.containsAll(rightChildEnds)) {
							markedLine line = lineCollector.get(i);
							lineCollector.remove(i);
							window.getChildren().remove(line);
						}
					}
					markedLine rightChildLine = new markedLine();
					
					rightChildLine.startXProperty().bind(root.getBody().centerXProperty().add(root.translateXProperty().add(radiusAdjust)));
					rightChildLine.startYProperty().bind(root.getBody().centerYProperty().add(root.translateYProperty().add(radiusAdjust)));
					rightChildLine.endXProperty().bind(rightSubTree.getBody().centerXProperty().add(rightSubTree.translateXProperty().add(radiusAdjust)));
					rightChildLine.endYProperty().bind(rightSubTree.getBody().centerYProperty().add(rightSubTree.translateYProperty().add(radiusAdjust)));
					
					rightChildLine.setViewOrder(2);
					rightChildLine.setStroke(Color.GREENYELLOW);
					rightChildLine.setStrokeWidth(3);
					rightChildLine.ends.add(root.getNum());
					rightChildLine.ends.add(rightSubTree.getNum());
				    lineCollector.add(rightChildLine);
				    window.getChildren().add(rightChildLine);
				}
			});
			
			getAnimateSequence().add(rightChildMove);
		}
		
		root.setHeight(nodeHeight(root));
		leftChild.setHeight(nodeHeight(leftChild));
		
		if(leftChild.getNum() < Root.getNum()) {
			adjust_tree(Root, find_rightMost(root), new Circle(), find_rightMost(root).getNum());
			if(rightSubTree != null)
				adjust_tree(Root, find_leftMost(rightSubTree), new Circle(), find_leftMost(rightSubTree).getNum());
		}
		else{
			if(root.getRight() != null) {
				if(find_leftMost(root.getRight()) != root.getRight())
					adjust_tree(Root, find_leftMost(root.getRight()), new Circle(), find_leftMost(root.getRight()).getNum());
			}
			if(rightSubTree != null)
				adjust_tree(Root, find_leftMost(rightSubTree), new Circle(), find_leftMost(rightSubTree).getNum());
		}
			
		return leftChild;
	}
	
//	Rotate Left
	BTNode singleRotateLeft(Pane window, BTNode root, Circle glow) {
		BTNode father = getParentNode(root);
		BTNode rightChild = root.getRight();
		BTNode leftSubTree = rightChild.getLeft();
		
		ArrayList<BTNode> childTree = getGroupTree(rightChild);
		ArrayList<BTNode> rootTree = getGroupTree(root);
		rootTree.removeAll(childTree);
		
		if(leftSubTree != null){
			ArrayList<BTNode> rightSub = getGroupTree(leftSubTree);
			childTree.removeAll(rightSub);
		}
		
//		Rotate Left Animation
		double moveChildX = root.coordinateX - rightChild.coordinateX;
		double moveChildY = root.coordinateY - rightChild.coordinateY;
		double moveRootX;
		double moveRootY;
		BTNode rootLeftChild = root.getLeft();
		if(rootLeftChild != null) {
			moveRootX = rootLeftChild.coordinateX - root.coordinateX;
			moveRootY = rootLeftChild.coordinateY - root.coordinateY;
		}
		else {
			moveRootX = -disX;
			moveRootY = disY;
		}
		
		Timeline timeline = new Timeline();
		
		for(int i = 0; i < childTree.size(); i++) {
			KeyFrame k1 = new KeyFrame(speed,
					new KeyValue(childTree.get(i).translateXProperty(), childTree.get(i).coordinateX + moveChildX),
					new KeyValue(childTree.get(i).translateYProperty(), childTree.get(i).coordinateY + moveChildY));
			timeline.getKeyFrames().add(k1);
			childTree.get(i).setCoordinateX(childTree.get(i).coordinateX + moveChildX);
			childTree.get(i).setCoordinateY(childTree.get(i).coordinateY + moveChildY);
		}
		
		for(int i = 0; i < rootTree.size(); i++) {
			KeyFrame k2 = new KeyFrame(speed,
					new KeyValue(rootTree.get(i).translateXProperty(), rootTree.get(i).coordinateX + moveRootX),
					new KeyValue(rootTree.get(i).translateYProperty(), rootTree.get(i).coordinateY + moveRootY));
			timeline.getKeyFrames().add(k2);
			rootTree.get(i).setCoordinateX(rootTree.get(i).coordinateX + moveRootX);
			rootTree.get(i).setCoordinateY(rootTree.get(i).coordinateY + moveRootY);
		}
		
		if(father != null) {
			ArrayList<Integer> oldRootEnds = new ArrayList<Integer>();
			oldRootEnds.add(root.getNum());
			oldRootEnds.add(father.getNum());
		
			for(int i = 0; i < lineCollector.size(); i++) {
				if(lineCollector.get(i).ends.containsAll(oldRootEnds)) {
					markedLine line = lineCollector.get(i);
					lineCollector.remove(i);
					window.getChildren().remove(line);
				}
			}
			markedLine oldRootLine = new markedLine();
			
			oldRootLine.startXProperty().bind(father.getBody().centerXProperty().add(father.translateXProperty().add(radiusAdjust)));
			oldRootLine.startYProperty().bind(father.getBody().centerYProperty().add(father.translateYProperty().add(radiusAdjust)));
			oldRootLine.endXProperty().bind(rightChild.getBody().centerXProperty().add(rightChild.translateXProperty().add(radiusAdjust)));
			oldRootLine.endYProperty().bind(rightChild.getBody().centerYProperty().add(rightChild.translateYProperty().add(radiusAdjust)));
			
			oldRootLine.setViewOrder(2);
			oldRootLine.setStroke(Color.GREENYELLOW);
			oldRootLine.setStrokeWidth(3);
			oldRootLine.ends.add(father.getNum());
			oldRootLine.ends.add(rightChild.getNum());
		    lineCollector.add(oldRootLine);
		    window.getChildren().add(oldRootLine);
		}
		
		timeline.setOnFinished(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				root.heightDisplay.setText(Integer.toString(root.getHeight()));
				glow.setVisible(false);
			}
		});
		
		getAnimateSequence().add(timeline);
		
		if(father != null) {
			if(root == father.getLeft()) {
				father.setLeft(rightChild);
			}
			else if(root == father.getRight()) {
				father.setRight(rightChild);
			}
		}
		else {
			Root = rightChild;
		}
		rightChild.setLeft(root);
		root.setRight(leftSubTree);
		
//		end Rotate Left Animation
		
//		Move leftSubTree of rightChild
		Timeline leftChildMove = new Timeline();
		
		if(leftSubTree != null) {
			
			double moveRightSubX = root.coordinateX + disX - leftSubTree.coordinateX;
			double moveRightSubY = root.coordinateY + disY - leftSubTree.coordinateY;
			
			ArrayList<BTNode> leftSub = getGroupTree(leftSubTree);
			
			for(int i = 0; i < leftSub.size(); i++) {
				KeyFrame kf = new KeyFrame(speed,
						new KeyValue(leftSub.get(i).translateXProperty(), leftSub.get(i).coordinateX + moveRightSubX),
						new KeyValue(leftSub.get(i).translateYProperty(), leftSub.get(i).coordinateY + moveRightSubY));
				leftChildMove.getKeyFrames().add(kf);
				leftSub.get(i).setCoordinateX(leftSub.get(i).coordinateX + moveRightSubX);
				leftSub.get(i).setCoordinateY(leftSub.get(i).coordinateY + moveRightSubY);
			}
			
			timeline.setOnFinished(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					rightChild.heightDisplay.setText(Integer.toString(rightChild.getHeight()));
					root.heightDisplay.setText(Integer.toString(root.getHeight()));
					ArrayList<Integer> rightChildEnds = new ArrayList<Integer>();
					
					rightChildEnds.add(rightChild.getNum());
					rightChildEnds.add(leftSubTree.getNum());
					
					for(int i = 0; i < lineCollector.size(); i++) {
						if(lineCollector.get(i).ends.containsAll(rightChildEnds)) {
							markedLine line = lineCollector.get(i);
							lineCollector.remove(i);
							window.getChildren().remove(line);
						}
					}
					markedLine leftChildLine = new markedLine();
					
					leftChildLine.startXProperty().bind(root.getBody().centerXProperty().add(root.translateXProperty().add(radiusAdjust)));
					leftChildLine.startYProperty().bind(root.getBody().centerYProperty().add(root.translateYProperty().add(radiusAdjust)));
					leftChildLine.endXProperty().bind(leftSubTree.getBody().centerXProperty().add(leftSubTree.translateXProperty().add(radiusAdjust)));
					leftChildLine.endYProperty().bind(leftSubTree.getBody().centerYProperty().add(leftSubTree.translateYProperty().add(radiusAdjust)));
					
					leftChildLine.setViewOrder(2);
					leftChildLine.setStroke(Color.GREENYELLOW);
					leftChildLine.setStrokeWidth(3);
					leftChildLine.ends.add(root.getNum());
					leftChildLine.ends.add(leftSubTree.getNum());
				    lineCollector.add(leftChildLine);
				    window.getChildren().add(leftChildLine);
				}
			});
			
			getAnimateSequence().add(leftChildMove);
		}
		
		root.setHeight(nodeHeight(root));
		rightChild.setHeight(nodeHeight(rightChild));
		
		if(rightChild.getNum() > Root.getNum()) {
			adjust_tree(Root, find_leftMost(root), new Circle(), find_leftMost(root).getNum());
			if(leftSubTree != null)
				adjust_tree(Root, find_rightMost(leftSubTree), new Circle(), find_rightMost(leftSubTree).getNum());
		}
		else{
			if(root.getLeft() != null) {
				if(find_rightMost(root.getLeft()) != root.getLeft())
					adjust_tree(Root, find_rightMost(root.getLeft()), new Circle(), find_rightMost(root.getLeft()).getNum());
			}
			if(leftSubTree != null)
				adjust_tree(Root, find_rightMost(leftSubTree), new Circle(), find_rightMost(leftSubTree).getNum());
		}
		
		return rightChild;
	}
//	Adjust pos
	void adjust_tree(BTNode root, BTNode insertNode, Circle insertCir, int num) {
		if(num < root.getNum()) {
			if(root.getLeft() != null && num > root.getLeft().getNum()) {
				ArrayList<BTNode> movedSubTree = getGroupTree(root.getLeft());
				Timeline timeline = new Timeline();
				
				if(root.getLeft().getRight() != null && num < root.getLeft().getRight().getNum()) {
					movedSubTree.removeAll(getGroupTree(root.getLeft().getRight()));
					adjust_tree(root.getLeft().getRight(),insertNode,insertCir,num);
				}
					
				else if( root.getLeft().getRight().getRight() != null && num < root.getLeft().getRight().getRight().getNum()) {
					movedSubTree.removeAll(getGroupTree(root.getLeft().getRight().getRight()));
					adjust_tree(root.getLeft().getRight().getRight(),insertNode,insertCir,num);
				}
				
				else{
					KeyFrame kf = new KeyFrame(speed, new KeyValue(insertCir.translateXProperty(),- disX));
					timeline.getKeyFrames().add(kf);
				}
				
				for(int i = 0; i < movedSubTree.size(); i++) {
					KeyFrame kf = new KeyFrame(speed, new KeyValue(movedSubTree.get(i).translateXProperty(),movedSubTree.get(i).coordinateX - disX));
					timeline.getKeyFrames().add(kf);
					movedSubTree.get(i).setCoordinateX(movedSubTree.get(i).coordinateX - disX);
				}
				
				timeline.setOnFinished(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						insertCir.setTranslateX(0);
						insertCir.setVisible(false);
					}
				});
				this.getAnimateSequence().add(timeline);
			}
			else if(root.getLeft() != null && num < root.getLeft().getNum())
				adjust_tree(root.getLeft(),insertNode,insertCir,num);
		}
		
		else if(num > root.getNum()) {
			if(root.getRight() != null && num < root.getRight().getNum()) {
				ArrayList<BTNode> movedSubTree = getGroupTree(root.getRight());
				Timeline timeline = new Timeline();
				
				if(root.getRight().getLeft() != null && num > root.getRight().getLeft().getNum()) {
					movedSubTree.removeAll(getGroupTree(root.getRight().getLeft()));
					adjust_tree(root.getRight().getLeft(),insertNode,insertCir,num);
				}
			
				else if( root.getRight().getLeft().getLeft() != null && num > root.getRight().getLeft().getLeft().getNum()) {
					movedSubTree.removeAll(getGroupTree(root.getRight().getLeft().getLeft()));
					adjust_tree(root.getRight().getLeft().getLeft(),insertNode,insertCir,num);
				}
				else {
					KeyFrame kf = new KeyFrame(speed, new KeyValue(insertCir.translateXProperty(), disX));
					timeline.getKeyFrames().add(kf);
				}
			
				for(int i = 0; i < movedSubTree.size(); i++) {
					KeyFrame kf = new KeyFrame(speed, new KeyValue(movedSubTree.get(i).translateXProperty(),movedSubTree.get(i).coordinateX + disX));
					timeline.getKeyFrames().add(kf);
					movedSubTree.get(i).setCoordinateX(movedSubTree.get(i).coordinateX + disX);
				}

				timeline.setOnFinished(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						insertCir.setTranslateX(0);
						insertCir.setVisible(false);
					}
				});
			
				this.getAnimateSequence().add(timeline);
			}
			else if(root.getRight() != null && num > root.getRight().getNum())
				adjust_tree(root.getRight(),insertNode,insertCir,num);
		}
	}
	
	
//	Re-Adjust
	void re_adjust(BTNode root) {
		Timeline reAdjust = new Timeline();
		if(root.getLeft() != null) {
			ArrayList<BTNode> leftSubTree = getGroupTree(root.getLeft());
			BTNode leftAdjust = find_rightMost(root.getLeft());
			if((root.coordinateX - leftAdjust.coordinateX) > disX) {
				for(int i = 0; i < leftSubTree.size(); i++) {
					KeyFrame kf = new KeyFrame(speed,
							new KeyValue(leftSubTree.get(i).translateXProperty(), leftSubTree.get(i).coordinateX + root.coordinateX - disX - leftAdjust.coordinateX));
					leftSubTree.get(i).setCoordinateX( leftSubTree.get(i).coordinateX + root.coordinateX - disX - leftAdjust.coordinateX);
					reAdjust.getKeyFrames().add(kf);
				}
			}
		}
		if(root.getRight() != null) {
			ArrayList<BTNode> rightSubTree = getGroupTree(root.getRight());
			BTNode rightAdjust = find_leftMost(root.getRight());
			if((rightAdjust.coordinateX - root.coordinateX) > disX) {
				for(int i = rightSubTree.size() - 1; i >= 0; i--) {
					KeyFrame kf = new KeyFrame(speed,
							new KeyValue(rightSubTree.get(i).translateXProperty(),rightSubTree.get(i).coordinateX + root.coordinateX + disX - rightAdjust.coordinateX));
					rightSubTree.get(i).setCoordinateX(rightSubTree.get(i).coordinateX + root.coordinateX + disX - rightAdjust.coordinateX);
					reAdjust.getKeyFrames().add(kf);
				}
			}
		}
		this.getAnimateSequence().add(reAdjust);
	}
//	Find insert Position
	BTNode find_insert(Pane window, Circle glow, BTNode root, int num) {
		glow.setStroke(Color.RED);
		if(num > root.getNum() && root.getRight() == null) {
			root.setRight(make_node(window,num));
			return root.getRight();
		}
		if(num < root.getNum() && root.getLeft() == null) {
			root.setLeft(make_node(window,num));
			return root.getLeft();
		}
		if(num > root.getNum()) {
			Timeline timeline = new Timeline();
			KeyFrame kfRight = new KeyFrame(speed,
					new KeyValue(glow.centerXProperty(), root.getRight().coordinateX + root.radius),
					new KeyValue(glow.centerYProperty(), root.getRight().coordinateY + root.radius));
			timeline.getKeyFrames().add(kfRight);
			this.getAnimateSequence().add(timeline);
			
			return find_insert(window, glow,root.getRight(),num);
		}
		if(num < root.getNum()) {
			Timeline timeline = new Timeline();
			KeyFrame kfLeft = new KeyFrame(speed,
					new KeyValue(glow.centerXProperty(), root.getLeft().coordinateX + root.radius),
					new KeyValue(glow.centerYProperty(), root.getLeft().coordinateY + root.radius));
			timeline.getKeyFrames().add(kfLeft);
			this.getAnimateSequence().add(timeline);
			return find_insert(window,glow,root.getLeft(),num);
			
		}
		return null;
	}
	
	BTNode find_delete(Circle glow, BTNode root, int num) {
		glow.setStroke(Color.RED);
		if(root.getNum() == num) 
			return root;

		Timeline timeline = new Timeline();
		if(num < root.getNum()) {
			KeyFrame kfLeft = new KeyFrame(speed,
					new KeyValue(glow.centerXProperty(), root.getLeft().coordinateX + root.radius),
					new KeyValue(glow.centerYProperty(), root.getLeft().coordinateY + root.radius));
			timeline.getKeyFrames().add(kfLeft);
			this.getAnimateSequence().add(timeline);
			return find_delete(glow,root.getLeft(),num);
		}
		if(num > root.getNum()) {
			KeyFrame kfRight = new KeyFrame(speed,
					new KeyValue(glow.centerXProperty(), root.getRight().coordinateX + root.radius),
					new KeyValue(glow.centerYProperty(), root.getRight().coordinateY + root.radius));
			timeline.getKeyFrames().add(kfRight);
			this.getAnimateSequence().add(timeline);
			return find_delete(glow,root.getRight(),num);
		}
		
		return null;
	}
	
//	Unwinding Recursion
	BTNode unwinding(Pane window, Circle glow, BTNode root) {
		glow.setVisible(true);
		root.setHeight(nodeHeight(root));
		
		re_adjust(root);
		if(getParentNode(root) == null) {
			root.heightDisplay.setText(Integer.toString(root.getHeight()));
			return root;
		}
		
		Timeline timeline = new Timeline();
		KeyFrame kf = new KeyFrame(speed,
				new KeyValue(glow.centerXProperty(),getParentNode(root).coordinateX + root.radius),
				new KeyValue(glow.centerYProperty(),getParentNode(root).coordinateY + root.radius));
		timeline.getKeyFrames().add(kf);
		
		timeline.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				root.heightDisplay.setText(Integer.toString(root.getHeight()));
			}			
		});
		
		getAnimateSequence().add(timeline);
		
		if((nodeHeight(getParentNode(root).getLeft()) - nodeHeight(getParentNode(root).getRight())) >= 2) {
			if(nodeHeight(getParentNode(root).getLeft().getRight()) - nodeHeight(getParentNode(root).getLeft().getLeft()) == 1) {
				BTNode rotate1 = singleRotateLeft(window,getParentNode(root).getLeft(),glow);
				BTNode rotate2= singleRotateRight(window,getParentNode(rotate1),glow);
				return unwinding(window,glow,rotate2);
			}
			BTNode newCursor = singleRotateRight(window, getParentNode(root),glow);
			return unwinding(window,glow,newCursor);
		}
		
		if((nodeHeight(getParentNode(root).getRight()) - nodeHeight(getParentNode(root).getLeft())) >= 2) {
			if(nodeHeight(getParentNode(root).getRight().getLeft()) - nodeHeight(getParentNode(root).getRight().getRight()) == 1) {
				BTNode rotate1 = singleRotateRight(window, getParentNode(root).getRight(), glow);
				BTNode rotate2 = singleRotateLeft(window,getParentNode(rotate1),glow);
				return unwinding(window,glow,rotate2);
			}
			BTNode newCursor = singleRotateLeft(window, getParentNode(root),glow);
			return unwinding(window,glow,newCursor);
		}
		
		return unwinding(window,glow,getParentNode(root));
	}
	
//	Insert Node
	public BTNode insert_node(Pane window, BTNode root, int num) {
		BTNode tmp = find_node(root,num);
		if(tmp != null) {
			Alert DuplicateInput = new Alert(AlertType.ERROR);
			DuplicateInput.setTitle("Input ERROR");
			DuplicateInput.setHeaderText(null);
			DuplicateInput.setContentText("Invalid Input: Node value already existed");
			DuplicateInput.showAndWait();
			return root;
		}
			
		setAnimateSequence(new ArrayList<Animation>());
		
//		Initiate Root
		if(isEmpty(root)) {
			root = make_node(window,num);
			TranslateTransition initNode = new TranslateTransition(speed,root);
			initNode.setFromX(root.coordinateX);
			initNode.setToX(rootCoordinateX);
			initNode.setFromY(root.coordinateY);
			initNode.setToY(rootCoordinateY);
			
			root.setCoordinateX(rootCoordinateX);
			root.setCoordinateY(rootCoordinateY);
			root.setLeft(null);
			root.setRight(null);
			
			this.Root = root;
			initNode.play();
			return root;
		}
		
		Circle insertCir = new Circle(rootCoordinateX + root.radius,rootCoordinateY + root.radius,gd.getDisplayMode().getWidth() / 65, Color.TRANSPARENT);
		insertCir.setStrokeWidth(3);
		insertCir.setStrokeType(StrokeType.INSIDE);
		window.getChildren().add(insertCir);
		insertCir.setViewOrder(0);
		
		BTNode insertNode = find_insert(window,insertCir,this.Root,num);
		if(insertNode == null) 
			return root;
		
//	 	Adjust Node Position
		adjust_tree(root,insertNode,insertCir,num);
		
//		Adding Node
		
//		ADD LEFT
		if(getParentNode(insertNode).getLeft() == insertNode) {
		    
			markedLine line = new markedLine(100,100,300,200);

			line.startXProperty().bind(getParentNode(insertNode).getBody().centerXProperty().add(getParentNode(insertNode).translateXProperty().add(radiusAdjust)));
			line.startYProperty().bind(getParentNode(insertNode).getBody().centerYProperty().add(getParentNode(insertNode).translateYProperty().add(radiusAdjust)));
			line.endXProperty().bind(insertNode.getBody().centerXProperty().add(insertNode.translateXProperty().add(radiusAdjust)));
			line.endYProperty().bind(insertNode.getBody().centerYProperty().add(insertNode.translateYProperty().add(radiusAdjust)));
		    
			line.ends.add(insertNode.getNum());
			line.ends.add(getParentNode(insertNode).getNum());
		    lineCollector.add(line);
		    
			line.setStroke(Color.GREENYELLOW);
			line.setStrokeWidth(3);
			window.getChildren().add(line);
			line.setViewOrder(2);
			root.setViewOrder(1);
			
			
			TranslateTransition insertRight = new TranslateTransition(speed, insertNode);
			insertRight.setFromX(insertNode.coordinateX);
			insertRight.setToX(getParentNode(insertNode).coordinateX - disX);
			insertRight.setFromY(insertNode.coordinateY);
			insertRight.setToY(getParentNode(insertNode).coordinateY + disY);
			
			insertNode.setCoordinateX(getParentNode(insertNode).getCoordinateX() - disX);
			insertNode.setCoordinateY(getParentNode(insertNode).getCoordinateY() + disY);
			
			this.getAnimateSequence().add(insertRight);
			
			Timeline timeline0 = new Timeline();
			KeyFrame kf0 = new KeyFrame(Duration.millis(5),
					new KeyValue(insertCir.centerXProperty(), insertNode.coordinateX + root.radius),
					new KeyValue(insertCir.centerYProperty(), insertNode.coordinateY + root.radius));
			timeline0.getKeyFrames().add(kf0);
			timeline0.setOnFinished(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					insertCir.setStroke(Color.BLUE);
				}
			});
			this.getAnimateSequence().add(timeline0);
		}
		
//		ADD RIGHT
		if(getParentNode(insertNode).getRight() == insertNode) {
			
			markedLine line = new markedLine(100,100,300,200);
			
			line.startXProperty().bind(getParentNode(insertNode).getBody().centerXProperty().add(getParentNode(insertNode).translateXProperty().add(radiusAdjust)));
			line.startYProperty().bind(getParentNode(insertNode).getBody().centerYProperty().add(getParentNode(insertNode).translateYProperty().add(radiusAdjust)));
			line.endXProperty().bind(insertNode.getBody().centerXProperty().add(insertNode.translateXProperty().add(radiusAdjust)));
			line.endYProperty().bind(insertNode.getBody().centerYProperty().add(insertNode.translateYProperty().add(radiusAdjust)));
			
			line.ends.add(insertNode.getNum());
			line.ends.add(getParentNode(insertNode).getNum());
			lineCollector.add(line);
			line.setStroke(Color.GREENYELLOW);
			line.setStrokeWidth(3);
			window.getChildren().add(line);
			line.setViewOrder(2);
			root.setViewOrder(1);
			
			insertNode.heightDisplay.setTranslateX(insertNode.coordinateX + 2 * insertNode.radius);
			
			TranslateTransition insertRight = new TranslateTransition(speed, insertNode);
			insertRight.setFromX(insertNode.coordinateX);
			insertRight.setToX(getParentNode(insertNode).coordinateX + disX);
			insertRight.setFromY(insertNode.coordinateY);
			insertRight.setToY(getParentNode(insertNode).coordinateY + disY);
			
			insertNode.setCoordinateX(getParentNode(insertNode).getCoordinateX() + disX);
			insertNode.setCoordinateY(getParentNode(insertNode).getCoordinateY() + disY);
			
			this.getAnimateSequence().add(insertRight);
			
			Timeline timeline0 = new Timeline();
			KeyFrame kf0 = new KeyFrame(Duration.millis(100),
					new KeyValue(insertCir.centerXProperty(), insertNode.coordinateX + root.radius),
					new KeyValue(insertCir.centerYProperty(), insertNode.coordinateY + root.radius));
			timeline0.getKeyFrames().add(kf0);
			timeline0.setOnFinished(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					insertCir.setStroke(Color.BLUE);
				}
			});
			this.getAnimateSequence().add(timeline0);
		}
		
		root = unwinding(window, insertCir, insertNode);
		
		SequentialTransition sequence = new SequentialTransition();
		sequence.getChildren().addAll(getAnimateSequence());
		sequence.play();
		sequence.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				window.getChildren().remove(insertCir);
			}
		});
		
		return root;
	}
	
	void remove_lineToNode(Pane window, BTNode node) {
		BTNode parent = getParentNode(node);
		BTNode leftChild = node.getLeft();
		BTNode rightChild = node.getRight();
		
		if(parent != null) {
			ArrayList<Integer> parentEnds = new ArrayList<Integer>();
			parentEnds.add(node.getNum());
			parentEnds.add(parent.getNum());
			markedLine line = null;
			for(int i = 0; i < lineCollector.size(); i++) {
				if(lineCollector.get(i).ends.containsAll(parentEnds)) {
					line = lineCollector.get(i);
					lineCollector.remove(i);
					window.getChildren().remove(line);
				}
			}
		}
		if(leftChild != null) {
			ArrayList<Integer> leftChildEnds = new ArrayList<Integer>();
			leftChildEnds.add(node.getNum());
			leftChildEnds.add(leftChild.getNum());
			markedLine line = null;
			for(int i = 0; i < lineCollector.size(); i++) {
				if(lineCollector.get(i).ends.containsAll(leftChildEnds)) {
					line = lineCollector.get(i);
					lineCollector.remove(i);
					window.getChildren().remove(line);
				}
			}
		}
		if(rightChild != null) {
			ArrayList<Integer> rightChildEnds = new ArrayList<Integer>();
			rightChildEnds.add(node.getNum());
			rightChildEnds.add(rightChild.getNum());
			markedLine line = null;
			for(int i = 0; i < lineCollector.size(); i++) {
				if(lineCollector.get(i).ends.containsAll(rightChildEnds)) {
					line = lineCollector.get(i);
					lineCollector.remove(i);
					window.getChildren().remove(line);
				}
			}
		}
	}
	
	public BTNode remove_node(Pane window, BTNode root, int num) {
		BTNode tmp = find_node(root, num);
		if(tmp == null) {
			Alert NotExistInput = new Alert(AlertType.ERROR);
			NotExistInput.setTitle("Input ERROR");
			NotExistInput.setHeaderText(null);
			NotExistInput.setContentText("Invalid Input: Node value doesn't exist");
			NotExistInput.showAndWait();
			return root;
		}
		
		setAnimateSequence(new ArrayList<Animation>());
		
		Circle glow = new Circle(rootCoordinateX + root.radius,rootCoordinateY + root.radius,gd.getDisplayMode().getWidth() / 65, Color.TRANSPARENT);
		glow.setStrokeWidth(3);
		glow.setStrokeType(StrokeType.INSIDE);
		window.getChildren().add(glow);
		
		BTNode deletedNode = find_delete(glow, root, num);
		BTNode parent = getParentNode(deletedNode); 
		
		
//		If deletedNode is leaf, delete it
		if(isLeaf(deletedNode)) {
			if(parent != null) {
				Timeline timeline0 = new Timeline();
				
				KeyFrame kf0 = new KeyFrame(Duration.millis(100),
						new KeyValue(glow.centerXProperty(), parent.coordinateX + root.radius),
						new KeyValue(glow.centerYProperty(), parent.coordinateY + root.radius));
				timeline0.getKeyFrames().add(kf0);
				timeline0.setOnFinished(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent event) {
						glow.setStroke(Color.BLUE);
						window.getChildren().remove(deletedNode);
					}
				});
				remove_lineToNode(window, deletedNode);
				
				this.getAnimateSequence().add(timeline0);
				if(deletedNode == parent.getLeft()) 
					parent.setLeft(null);
				else if(deletedNode == parent.getRight()) 
					parent.setRight(null);
				if(parent.getLeft() != null)
					root = unwinding(window,glow,parent.getLeft());
				if(parent.getRight() != null)
					root = unwinding(window, glow, parent.getRight());
			}
			else {
				this.Root = null;
				window.getChildren().remove(glow);
				window.getChildren().remove(deletedNode);
			
				return null;
			}
		}
		
//		If deleted Node has left, right child
		else if(deletedNode.getLeft() != null && deletedNode.getRight() != null) {
			BTNode leftChild = find_rightMost(deletedNode.getLeft());
			int leftSubChecker = 0;
			if(leftChild != deletedNode.getLeft()) leftSubChecker = 1;
			BTNode rightChild = deletedNode.getRight();
			
			Timeline timeline = new Timeline();
			KeyFrame kf = new KeyFrame(speed,
					new KeyValue(leftChild.translateXProperty(),deletedNode.coordinateX),
					new KeyValue(leftChild.translateYProperty(),deletedNode.coordinateY));
			timeline.getKeyFrames().add(kf);
			leftChild.setCoordinateX(deletedNode.coordinateX);
			leftChild.setCoordinateY(deletedNode.coordinateY);
			timeline.setOnFinished(new EventHandler<ActionEvent>() {
				@Override 
				public void handle(ActionEvent event) {
					glow.setStroke(Color.BLUE);
					if(parent != null) {
						
						markedLine leftChildtoRootLine = new markedLine();
						leftChildtoRootLine.startXProperty().bind(parent.getBody().centerXProperty().add(parent.translateXProperty().add(radiusAdjust)));
						leftChildtoRootLine.startYProperty().bind(parent.getBody().centerYProperty().add(parent.translateYProperty().add(radiusAdjust)));
						leftChildtoRootLine.endXProperty().bind(leftChild.getBody().centerXProperty().add(leftChild.translateXProperty().add(radiusAdjust)));
						leftChildtoRootLine.endYProperty().bind(leftChild.getBody().centerYProperty().add(leftChild.translateYProperty().add(radiusAdjust)));
							
						leftChildtoRootLine.ends.add(leftChild.getNum());
						leftChildtoRootLine.ends.add(parent.getNum());
						lineCollector.add(leftChildtoRootLine);
						leftChildtoRootLine.setStroke(Color.GREENYELLOW);
						leftChildtoRootLine.setStrokeWidth(3);
						window.getChildren().add(leftChildtoRootLine);
						leftChildtoRootLine.setViewOrder(2);
					}
						
					markedLine rightChildtoLeftChildLine = new markedLine();
					rightChildtoLeftChildLine.startXProperty().bind(leftChild.getBody().centerXProperty().add(leftChild.translateXProperty().add(radiusAdjust)));
					rightChildtoLeftChildLine.startYProperty().bind(leftChild.getBody().centerYProperty().add(leftChild.translateYProperty().add(radiusAdjust)));
					rightChildtoLeftChildLine.endXProperty().bind(rightChild.getBody().centerXProperty().add(rightChild.translateXProperty().add(radiusAdjust)));
					rightChildtoLeftChildLine.endYProperty().bind(rightChild.getBody().centerYProperty().add(rightChild.translateYProperty().add(radiusAdjust)));
							
					rightChildtoLeftChildLine.ends.add(leftChild.getNum());
					rightChildtoLeftChildLine.ends.add(rightChild.getNum());
					lineCollector.add(rightChildtoLeftChildLine);
					rightChildtoLeftChildLine.setStroke(Color.GREENYELLOW);
					rightChildtoLeftChildLine.setStrokeWidth(3);
					window.getChildren().add(rightChildtoLeftChildLine);
					rightChildtoLeftChildLine.setViewOrder(2);
						
					
					window.getChildren().remove(deletedNode);
				}	
			});
			this.getAnimateSequence().add(timeline);
			remove_lineToNode(window, deletedNode);
			
			if(leftSubChecker == 1) {
				getParentNode(leftChild).setRight(null);
				leftChild.setLeft(deletedNode.getLeft());
			}
			else
				leftChild.setLeft(null);
			
			if(parent != null) {
				if(parent.getLeft() == deletedNode) {
					parent.setLeft(leftChild);
				}
				else if(parent.getRight() == deletedNode) {
					parent.setRight(leftChild);
				}
			}
			else {
				this.Root = leftChild;
				root = leftChild;
				System.gc();
			}
			
			
			leftChild.setRight(rightChild);
			root = unwinding(window, glow, leftChild.getRight());
		}
		
//		deletedNode has only leftChild
		else if(deletedNode.getLeft() != null){
			BTNode leftChild = find_rightMost(deletedNode.getLeft());
			int leftSubChecker = 0;
			if(leftChild != deletedNode.getLeft()) leftSubChecker = 1;
				
				
			Timeline timeline = new Timeline();
			KeyFrame kf = new KeyFrame(speed,
					new KeyValue(leftChild.translateXProperty(),deletedNode.coordinateX),
					new KeyValue(leftChild.translateYProperty(),deletedNode.coordinateY));
			timeline.getKeyFrames().add(kf);
			leftChild.setCoordinateX(deletedNode.coordinateX);
			leftChild.setCoordinateY(deletedNode.coordinateY);
			timeline.setOnFinished(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					glow.setStroke(Color.BLUE);
					if(parent != null) {
						markedLine leftChildtoRootLine = new markedLine();
						leftChildtoRootLine.startXProperty().bind(parent.getBody().centerXProperty().add(parent.translateXProperty().add(radiusAdjust)));
						leftChildtoRootLine.startYProperty().bind(parent.getBody().centerYProperty().add(parent.translateYProperty().add(radiusAdjust)));
						leftChildtoRootLine.endXProperty().bind(leftChild.getBody().centerXProperty().add(leftChild.translateXProperty().add(radiusAdjust)));
						leftChildtoRootLine.endYProperty().bind(leftChild.getBody().centerYProperty().add(leftChild.translateYProperty().add(radiusAdjust)));
							
						leftChildtoRootLine.ends.add(leftChild.getNum());
						leftChildtoRootLine.ends.add(parent.getNum());
						lineCollector.add(leftChildtoRootLine);
						leftChildtoRootLine.setStroke(Color.GREENYELLOW);
						leftChildtoRootLine.setStrokeWidth(3);
						window.getChildren().add(leftChildtoRootLine);
						leftChildtoRootLine.setViewOrder(2);
					}
					window.getChildren().remove(deletedNode);
							
				}
			});
			this.getAnimateSequence().add(timeline);
			remove_lineToNode(window, deletedNode);
			
			if(leftSubChecker == 1) {
				getParentNode(leftChild).setRight(null);
				leftChild.setLeft(deletedNode.getLeft());
			}
			else
				leftChild.setLeft(null);
			
			if(parent != null) {
				if(parent.getLeft() == deletedNode) 
					parent.setLeft(leftChild);
				else if(parent.getRight() == deletedNode) 
					parent.setRight(leftChild);
			}
			else {
				this.Root = leftChild;
				root = leftChild;
				System.gc();
			}
			leftChild.setRight(null);
			if(leftChild.getLeft() != null)
				root = unwinding(window,glow,leftChild.getLeft());
		}
		
//		deletedNode has only rightChild
		else {
			BTNode rightChild = find_leftMost(deletedNode.getRight());
			int rightSubCheck = 0;
			if(rightChild != deletedNode.getRight()) 
				rightSubCheck = 1;
			
			Timeline timeline = new Timeline();
			KeyFrame kf = new KeyFrame(speed, 
					new KeyValue(rightChild.translateXProperty(), deletedNode.coordinateX),
					new KeyValue(rightChild.translateYProperty(), deletedNode.coordinateY));
			timeline.getKeyFrames().add(kf);
			rightChild.setCoordinateX(deletedNode.coordinateX);
			rightChild.setCoordinateY( deletedNode.coordinateY);
			timeline.setOnFinished(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
				glow.setStroke(Color.BLUE);
					if(parent != null) {
					
						markedLine rightChildtoRootLine = new markedLine();
						rightChildtoRootLine.startXProperty().bind(parent.getBody().centerXProperty().add(parent.translateXProperty().add(radiusAdjust)));
						rightChildtoRootLine.startYProperty().bind(parent.getBody().centerYProperty().add(parent.translateYProperty().add(radiusAdjust)));
						rightChildtoRootLine.endXProperty().bind(rightChild.getBody().centerXProperty().add(rightChild.translateXProperty().add(radiusAdjust)));
						rightChildtoRootLine.endYProperty().bind(rightChild.getBody().centerYProperty().add(rightChild.translateYProperty().add(radiusAdjust)));
							
						rightChildtoRootLine.ends.add(rightChild.getNum());
						rightChildtoRootLine.ends.add(parent.getNum());
						lineCollector.add(rightChildtoRootLine);
						rightChildtoRootLine.setStroke(Color.GREENYELLOW);
						rightChildtoRootLine.setStrokeWidth(3);
						window.getChildren().add(rightChildtoRootLine);
						rightChildtoRootLine.setViewOrder(2);
					}
					window.getChildren().remove(deletedNode);
				}
			});
			this.getAnimateSequence().add(timeline);
			remove_lineToNode(window, deletedNode);
			
			if(rightSubCheck == 1) {
				getParentNode(rightChild).setLeft(null);
				rightChild.setRight(deletedNode.getRight());
			}
			else
				rightChild.setRight(null);
				
			if(parent != null) {
				if(parent.getLeft() == deletedNode) 
					parent.setLeft(rightChild);
				else if(parent.getRight() == deletedNode) 
					parent.setRight(rightChild);
			}
			else {
				this.Root = rightChild;
				root = rightChild;
				System.gc();
			}
			rightChild.setLeft(null);
			if(rightChild.getRight() != null)
				root = unwinding(window, glow, rightChild.getRight());
		}
		
		root.setViewOrder(1);
		if (parent != null) 
			root = unwinding(window,glow,parent);
		
		SequentialTransition sequence = new SequentialTransition();
		sequence.getChildren().addAll(this.getAnimateSequence());
		sequence.play();
		sequence.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				window.getChildren().remove(glow);
			}
		});
		return root;
	}
	
}