package miniProject.model;

import java.util.ArrayList;

import javafx.scene.layout.StackPane;

public class SceneCollector {
	ArrayList<StackPane> sceneCollection;
	int sceneIndex;
	
	public void setSceneIndex(int sceneIndex) {
		this.sceneIndex = sceneIndex;
	}
	public int getSceneIndex() {
		return sceneIndex;
	}
	public void addSceneIndex() {
		this.sceneIndex += 1;
	}
	public void subSceneIndex() {
		this.sceneIndex -= 1;
	}
	public ArrayList<StackPane> getSceneCollectiion() {
		return sceneCollection;
	}
	
	public SceneCollector() {
		super();
		// TODO Auto-generated constructor stub
		this.sceneCollection = new ArrayList<StackPane>();
		this.sceneIndex = 0;
	}
	
	
}
