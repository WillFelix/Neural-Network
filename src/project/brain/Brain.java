package project.brain;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Brain {

	public int k;

	public final int TRAIN = 0, TEST = 1;

	public Scanner scanner = new Scanner(System.in);

	public List<Point> points = null;

	public List<Point> newPoints = null;
	
	public List<Point> loadDatabase(int menu) {
		return loadDatabase(menu, "_abalone", ",");
	}
	
	public List<Point> loadDatabase(int menu, String db, String split) {
		List<Point> points = new ArrayList<Point>();
		
		int index = 0;
		String base = menu == TRAIN ? "train" : "test";
		List<Double[]> in = FileRead.pull("database/" + base + db, split);
		for (Double[] xs : in) {
			Point p = new Point(index);
			for (int i = 0; i < xs.length - 1; i++) {
				p.getInput().add(xs[i]);
			}
			p.setKlass(xs[xs.length - 1].intValue());
			points.add(p);
			index++;
		}
		
//		System.out.println("Carregando base '" + base + "'... PRONTO.");
//		System.out.println(points.size() + " registros carregados.");
//		System.out.println("-------------------------------------");
		
		return points;
	}

}
