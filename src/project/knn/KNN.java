package project.knn;

import java.util.List;

import project.brain.Brain;
import project.brain.Point;

public class KNN extends Brain {
	
	public static void main(String[] args) {
		KNN knn = new KNN();
		knn.init();
	}
	
	public void init() {
		int op = 0;
		
		System.out.println("Neural Network (K-Nearest Neighbors)");
		System.out.println("-------------------------------------------\n");
		points = loadDatabase(TRAIN);
		newPoints = loadDatabase(TEST);
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
		
		switch (op) {
			case 1:
				euclidian();
				break;
			case 2:
				manhattan();
				break;
			case 3:
				System.out.print("Digite um valor para a potência que será aplicada na distâcia de Minkowski: ");
				int pow = scanner.nextInt();
				minkowski(pow);
				break;
			default:
				break;
		}
	}

	public void euclidian() {
		for (Point p1 : newPoints) {
			List<Double> p = p1.getInput();
			Point result = null;
			double x = 0;

			for (int index = 0; index < k; index++) {
				List<Double> q = points.get(index).getInput();
				
				double y = 0;
				for (int i = 0; i < p.size(); i++) {
					double diff = p.get(i) - q.get(i);
					y += Math.pow(diff, 2);
				}
				y = Math.sqrt(y);
				
				if (y > x){
					x = y;
					result = points.get(index);
				}
			}
			
			p1.printData();
			System.out.println("Class: " + result.getKlass());
		}
	}
	
	public void manhattan() {
		for (Point p1 : newPoints) {
			List<Double> p = p1.getInput();
			Point result = null;
			double x = 0;

			for (int index = 0; index < k; index++) {
				List<Double> q = points.get(index).getInput();
				
				double y = 0;
				for (int i = 0; i < p.size(); i++) {
					y += Math.abs(p.get(i) - q.get(i));
				}
				
				if (y > x){
					x = y;
					result = points.get(index);
				}
			}
			
			p1.printData();
			System.out.println("Class: " + result.getKlass());
		}
	}
	
	public void minkowski(int pow) {
		for (Point p1 : newPoints) {
			List<Double> p = p1.getInput();
			Point result = null;
			double x = 0;

			for (int index = 0; index < k; index++) {
				List<Double> q = points.get(index).getInput();
				
				double y = 0;
				for (int i = 0; i < p.size(); i++) {
					double diff = p.get(i) - q.get(i);
					double number = Math.pow(diff, pow);
					y += Math.abs(number);
				}
				y = Math.pow(y, 1.0/pow);
				
				if (y > x){
					x = y;
					result = points.get(index);
				}
			}
			
			p1.printData();
			System.out.println("Class: " + result.getKlass());
		}
	}
	
	public void sample() {

		double a1 = 5.1, a2 = 3.5, a3 = 1.4, a4 = 0.2;
		double b1 = 4.9, b2 = 3.0, b3 = 1.4, b4 = 0.2;
		double c1 = 4.7, c2 = 3.2, c3 = 1.3, c4 = 0.2;

		double d1 = 6.9, d2 = 3.1, d3 = 4.9, d4 = 1.5;
		double e1 = 5.5, e2 = 2.3, e3 = 4.0, e4 = 1.3;
		double f1 = 6.5, f2 = 2.8, f3 = 4.6, f4 = 1.5;

		double g1 = 6.7, g2 = 2.5, g3 = 5.8, g4 = 1.8;
		double h1 = 7.2, h2 = 3.6, h3 = 6.1, h4 = 2.5;
		double i1 = 6.5, i2 = 3.2, i3 = 5.1, i4 = 2.0;

		double r01 = 6.8, r02 = 3.0, r03 = 5.5, r04 = 2.1;

		System.out.println("Setosa");
		double result = Math.sqrt(Math.pow(r01 - a1, 2) + Math.pow(r02 - a2, 2)
				+ Math.pow(r03 - a3, 2) + Math.pow(r04 - a4, 2));
		System.out.println(result);

		result = Math.sqrt(Math.pow(r01 - b1, 2) + Math.pow(r02 - b2, 2)
				+ Math.pow(r03 - b3, 2) + Math.pow(r04 - b4, 2));
		System.out.println(result);
		result = Math.sqrt(Math.pow(r01 - c1, 2) + Math.pow(r02 - c2, 2)
				+ Math.pow(r03 - c3, 2) + Math.pow(r04 - c4, 2));
		System.out.println(result);

		System.out.println("\nVersicolour");
		result = Math.sqrt(Math.pow(r01 - d1, 2) + Math.pow(r02 - d2, 2)
				+ Math.pow(r03 - d3, 2) + Math.pow(r04 - d4, 2));
		System.out.println(result);
		result = Math.sqrt(Math.pow(r01 - e1, 2) + Math.pow(r02 - e2, 2)
				+ Math.pow(r03 - e3, 2) + Math.pow(r04 - e4, 2));
		System.out.println(result);
		result = Math.sqrt(Math.pow(r01 - f1, 2) + Math.pow(r02 - f2, 2)
				+ Math.pow(r03 - f3, 2) + Math.pow(r04 - f4, 2));
		System.out.println(result);

		System.out.println("\nVirginica");
		result = Math.sqrt(Math.pow(r01 - g1, 2) + Math.pow(r02 - g2, 2)
				+ Math.pow(r03 - g3, 2) + Math.pow(r04 - g4, 2));
		System.out.println(result);
		result = Math.sqrt(Math.pow(r01 - h1, 2) + Math.pow(r02 - h2, 2)
				+ Math.pow(r03 - h3, 2) + Math.pow(r04 - h4, 2));
		System.out.println(result);
		result = Math.sqrt(Math.pow(r01 - i1, 2) + Math.pow(r02 - i2, 2)
				+ Math.pow(r03 - i3, 2) + Math.pow(r04 - i4, 2));
		System.out.println(result);
	}

}
