package project.kmeans;

import java.util.ArrayList;
import java.util.List;

import project.brain.Point;

public class Group {
	private int id;

	private List<Point> points;
	
	public Group(int i) {
		this.id = i;
	}

	public void add(Point p) {
		if (points == null) {
			points = new ArrayList<Point>();
		}

		points.add(p);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Point> getPoints() {
		return points;
	}

	public void setPoints(List<Point> points) {
		this.points = points;
	}
}
