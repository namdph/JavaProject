package miniProject.model;

import java.util.ArrayList;

import javafx.animation.Animation;

public class TimeCapsule extends ArrayList<Animation>{
//	1 for PUSH, 0 for POP
	ArrayList<Integer> type_checker = new ArrayList<Integer>();

	public ArrayList<Integer> getType_checker() {
		return type_checker;
	}

}
