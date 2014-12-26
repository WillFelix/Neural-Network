package project.knn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import project.brain.Brain;
import project.brain.Point;

public class KNN extends Brain {
	public static int op = 0;
	public int hits = 0;

	public static void main(String[] args) {
		KNN knn = new KNN();
		knn.init();
		knn.knn(op);
		knn.finish();
	}

	public void init() {

		System.out.println("Neural Network (K-Nearest Neighbors)");
		System.out.println("-------------------------------------------\n");
		points = loadDatabase(TRAIN, "_iris", ",");
		newPoints = loadDatabase(TEST, "_iris", ",");
		System.out.println("\n");

		op = 0;
		while (op < 1 || op > 3) {
			System.out.println("Qual a distância que será utilizada?");
			System.out.println("1. Distância Euclidiana");
			System.out.println("2. Distância Manhattan");
			System.out.println("3. Distância Minkowski");
			System.out.print("\nEscolha uma opção: ");
			op = scanner.nextInt();
			System.out.println("\n");
		}

		System.out.print("Digite um valor para k entre 1 e 11 (Número ímpar): ");
		k = scanner.nextInt();

	}

	public void knn(int distance) {
		List<Point> amostras = new ArrayList<Point>();
		int pow = 0;
		if (distance == 3) {
			System.out.print("Digite um valor para a potência que será aplicada na distâcia de Minkowski: ");
			pow = scanner.nextInt();
		}
		
		for (Point np1 : newPoints) {
			List<Double> p = np1.getInput();
			
			for (Point p1 : points) {
				List<Double> q = p1.getInput();
				double y = 0;
				
				if (distance == 1)					y = euclidian(p, q);
				else if (distance == 2)				y = manhattan(p, q);
				else								y = minkowski(pow, p, q);
				
				p1.setDistance(y);
			}
			
			// Current point distances
			amostras.clear();
			amostras.addAll(points);
			sortPoint(amostras);
			
			np1.printData();
			System.out.print(k + "-nn: ");
			List<Integer> klasses = new ArrayList<Integer>();
			for (int i = 0; i < k; i++) {
				System.out.print(amostras.get(i).getKlass() + " ");
				klasses.add(amostras.get(i).getKlass());
			}
			
			int klass = calculateClass(klasses);
			System.out.println("\tClass: " + klass);
			np1.setGotKlass(klass);
			
			if (klass == np1.getKlass()) {
				hits++;
			}
		}

	}

	private int calculateClass(List<Integer> klasses) {
		int result;
		int count = 0, major = 0;
		
		// Check if hava any double repetead
		result = klasses.get(0);
		sortInteger(klasses);
		for (int i = 0; i < klasses.size(); i++) {
			for (int j = i+1; j < klasses.size(); j++) {
				
				if (klasses.get(i) == klasses.get(j)) {
					count++;
				}
				
			}
			
			if (count > major) {
				major = count;
				result = klasses.get(i);
			}
			count = 0;
		}
		
		return result;
	}

	public double euclidian(List<Double> p, List<Double> q) {
		double y = 0;
		for (int i = 0; i < p.size(); i++) {
			double diff = p.get(i) - q.get(i);
			y += Math.pow(diff, 2);
		}
		y = Math.sqrt(y);

		return y;
	}

	public double manhattan(List<Double> p, List<Double> q) {
		double y = 0;
		for (int i = 0; i < p.size(); i++) {
			y += Math.abs(p.get(i) - q.get(i));
		}

		return y;
	}

	public double minkowski(int pow, List<Double> p, List<Double> q) {
		double y = 0;
		for (int i = 0; i < p.size(); i++) {
			double diff = p.get(i) - q.get(i);
			double number = Math.pow(diff, pow);
			y += Math.abs(number);
		}
		y = Math.pow(y, 1.0 / pow);

		return y;
	}

	public void sortDouble(List<Double> keys) {
		Collections.sort(keys);
	}
	
	public void sortInteger(List<Integer> keys) {
		Collections.sort(keys);
	}
	
	public void sortPoint(List<Point> keys) {
		Collections.sort(keys, new Comparator<Point>() {

			@Override
			public int compare(Point o1, Point o2) {
				return o1.getDistance().compareTo(o2.getDistance());
			}
			
		});
	}

	public void finish() {
		System.out.println("\n\n\n");
		
		System.out.println("Total de Amostras Analisadas: " + newPoints.size());
		System.out.println("Total Acertos: " + hits);
		
		System.out.print("\n");
		
		/**
		 * Check amount hits by class
		 */
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		for (int i = 0; i < newPoints.size() - 1; i++) {
			int count = 0;
			int klass = newPoints.get(i).getKlass();
			int hitsk = newPoints.get(i).getGotKlass() == klass ? 1 : 0;
			
			if (map.containsKey(klass)) {
				count = map.get(klass);
				count += hitsk;
			}
			map.put(klass, count);
		}
		
		for (Entry<Integer, Integer> entry : map.entrySet()) {
			System.out.println("Classe: " + entry.getKey());
			System.out.println("Acertos: " + entry.getValue());
			System.out.println("Porcentagem: " + ((entry.getValue() * 100) / hits) + "%");
			System.out.println();
		}
	}
}
