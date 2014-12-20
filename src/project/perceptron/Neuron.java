package project.perceptron;

import java.util.ArrayList;
import java.util.List;

public class Neuron {

	private List<Double> input;

	private int yo = 0;

	private int yd = 0;

	public Neuron() {
		input = new ArrayList<Double>();
	}

	public List<Double> getInput() {
		return input;
	}

	public void setInput(List<Double> input) {
		this.input = input;
	}

	public int getYo() {
		return yo;
	}

	public void setYo(int yo) {
		this.yo = yo;
	}

	public int getYd() {
		return yd;
	}

	public void setYd(int yd) {
		this.yd = yd;
	}
	
	public int generateError() {
		return yd - yo;
	}
}