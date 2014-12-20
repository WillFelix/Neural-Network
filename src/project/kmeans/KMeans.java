package project.kmeans;

import java.util.ArrayList;
import java.util.List;

import project.brain.Brain;
import project.brain.Point;

public class KMeans extends Brain {
	
	public static void main(String[] args) {
		KMeans kmeans = new KMeans();
		kmeans.init();
	}
	
	public void init() {
		System.out.println("Neural Networks (K-Means)");
		System.out.println("----------------------------------------\n");
		
		points = loadDatabase(TRAIN);
		
		do {
			
			System.out.print("Defina um valor inteiro maior que zero para k (2 ou 3): ");
			k = scanner.nextInt();
			
		} while (k < 2 || k > 3);
		
		kmeans();
	}
	
	/**
	 * This method is followed by 4 steps;
	 * 1 - Partitions of objects in k groups not empty.
	 * 2 - To define the centroids values of current partition group.
	 * 3 - Generate a matrix of distance between each point and the centroids.
	 * 4 - Go back the step 2 if the groups were modified.
	 */
	public void kmeans() {
		boolean newPartitions = true;
		
		Group[] G = step1();
		
		while (newPartitions) {
			double[][] centroids = step2(G);
			List<double[]> distMatrix = step3(centroids);
			newPartitions = step4(G, distMatrix);
		}
		
		
	}
	
	/**
	 * Step 1 - Partitions of objects in k groups not empty. 
	 * @return
	 */
	public Group[] step1() {
		Group[] G = new Group[k];
		
		int index = 0, gindex = 0, i = 0;
		int groupSize = points.size() / k;
		while (index < points.size()) {
			if (G[gindex] == null)
				G[gindex] = new Group(i++);
			
			G[gindex].add(points.get(index));
			index++;
			
			if (index % groupSize == 0 && index + 1 < points.size()) gindex++;
		}
		
		return G;
	}
	
	/**
	 * Step 2 - To define the centroids values of current partition group.  
	 * @return
	 */
	public double[][] step2(Group[] G) {
		double[][] centroideG = new double[k][points.get(0).getInput().size()];
		
		// Iterando sobre os groups G
		for (int i = 0; i < k; i++) {
			Group g = G[i];
			if (!g.getPoints().isEmpty()) {
				
				// Iterando sobre os Points dos groups
				for (int j = 0; j < g.getPoints().get(0).getInput().size(); j++) {
					
					double sum = 0.0;
					for (Point p : g.getPoints()) {
						sum += p.getInput().get(j);
					}
					centroideG[i][j] = (sum / g.getPoints().size());
				
				}
				
			}
		}
		
		return centroideG;
	}
	
	/**
	 * Step 3 - Generate a matrix of distance between each point and the centroids.
	 * @return
	 */
	public List<double[]> step3(double[][] centroids) {
		List<double[]> res = new ArrayList<double[]>();
		
		// sqrt((1.83 − 1.0)² + (2.33 − 1.0)²)
		// sqrt((g1[i] − p1[j])² + (g2[i] − p2[j])²)
		
		// CENTROIDS ITERATION
		for (int i = 0; i < centroids.length; i++) {
			double[] cent = centroids[i];
			double[] distG = dist(cent, points);
			
			res.add(distG);
		}
		
		return res;
	}
	
	/**
	 * Step 4 - Go back the step 2 if the groups were modified.
	 * @return
	 */
	public boolean step4(Group[] G, List<double[]> distMatrix) {
		boolean result = true;
		int size = distMatrix.get(0).length;
		Group[] G2 = new Group[G.length];
		
		for (int i = 0; i < G2.length; i++) {
			G2[i] = new Group(i);
		}
		
		for (int i = 0; i < size; i++) {
			
			double[] g = new double[distMatrix.size()];
			for (int j = 0; j < distMatrix.size(); j++) {
				g[j] = distMatrix.get(j)[i];
			}
			
			int index = 0;
			double number = 0;
			for (int k = 0; k < g.length; k++) {
				if (k == 0) number = g[k];
				
				if (number > g[k]) {
					number = g[k];
					index = k;
				}
			}
			
			G2[index].add(points.get(i));
				
		}
		
		result = isDiferentGroups(G, G2);
		
		return result;
	}
	
	
	public double[] dist(double[] cent, List<Point> points) {
		int index = 0;
		double[] result = new double[points.size()];
		
		for (Point point : points) {
			List<Double> in = point.getInput();
			
			for (int i = 0; i < cent.length; i++) {
				double number = cent[i] - in.get(i);
				result[index] += Math.pow(number, 2);
			}
			
			result[index] = Math.sqrt(result[index]);
			index++;
		}
		
		return result;
	}
	
	
	private boolean isDiferentGroups(Group[] originalGroup, Group[] newGroup) {
		// Check groups amount between the two big groups
		if (originalGroup.length != newGroup.length) {
			return true;
		} else {
			
			for (int i = 0; i < newGroup.length; i++) {
				System.out.print("Group " + (i+1) + ": ");
				
				for (int j = 0; j < newGroup[i].getPoints().size(); j++) {
					List<Point> points = newGroup[i].getPoints();
					System.out.print(points.get(j).getKlass() + " ");
				}
				
				System.out.println();
			}
			
			return false;
		}
	}
	
	
	public void inputSample() {
		points = new ArrayList<Point>();
		
		Point p = new Point();
		p.setKlass(1);
		p.getInput().add(1.0);
		p.getInput().add(1.0);
		points.add(p);
		
		Point p2 = new Point();
		p2.setKlass(2);
		p2.getInput().add(1.5);
		p2.getInput().add(2.0);
		points.add(p2);
		
		Point p3 = new Point();
		p3.setKlass(3);
		p3.getInput().add(3.0);
		p3.getInput().add(4.0);
		points.add(p3);
		
		Point p4 = new Point();
		p4.setKlass(4);
		p4.getInput().add(5.0);
		p4.getInput().add(7.0);
		points.add(p4);
		
		Point p5 = new Point();
		p5.setKlass(5);
		p5.getInput().add(3.5);
		p5.getInput().add(5.0);
		points.add(p5);
		
		Point p6 = new Point();
		p6.setKlass(6);
		p6.getInput().add(4.5);
		p6.getInput().add(5.0);
		points.add(p6);
		
		Point p7 = new Point();
		p7.setKlass(7);
		p7.getInput().add(3.5);
		p7.getInput().add(4.5);
		points.add(p7);
	}
}