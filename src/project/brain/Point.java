package project.brain;

import java.util.ArrayList;
import java.util.List;

public class Point {
	private List<Double> input;

	private int klass;

	public Point() {
		input = new ArrayList<Double>();
	}
	
	

	public List<Double> getInput() {
		return input;
	}

	public void setInput(List<Double> input) {
		this.input = input;
	}

	public int getKlass() {
		return klass;
	}

	public void setKlass(int klass) {
		this.klass = klass;
	}

	public void printData() {
		if (input != null && !input.isEmpty()) {
			for (Double x : input) {
				
				System.out.print(x + "\t|\t");
				
			}
		}
	}

}