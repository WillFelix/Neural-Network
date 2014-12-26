package project.brain;

import java.util.ArrayList;
import java.util.List;

public class Point {
	private int id;

	private List<Double> input;

	private int klass;

	private int gotKlass;

	private Double distance;

	public Point() {
		input = new ArrayList<Double>();
	}
	
	public Point(int id) {
		this.id = id;
		input = new ArrayList<Double>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public int getGotKlass() {
		return gotKlass;
	}

	public void setGotKlass(int gotKlass) {
		this.gotKlass = gotKlass;
	}

	public void printData() {
		if (input != null && !input.isEmpty()) {
			for (Double x : input) {

				System.out.print(x + "\t|\t");

			}
		}
	}

}
