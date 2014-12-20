package project.ga;

import java.util.Random;
import java.util.Scanner;

public class GeneticAlgorithm {
	public int[] subjects;
	
	public static void main(String[] args) {
		GeneticAlgorithm ga = new GeneticAlgorithm();
		ga.init();
	}
	
	public void init() {
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("Sua população terá quantos indivíduos?\nR: ");
		int qtd = scanner.nextInt();
		subjects = new int[qtd];
		for (int i = 0; i < qtd; i++) {
			subjects[i] = new Random().nextInt(10);
			System.out.print(subjects[i] + " ");
		}
		
		
		System.out.print("Deseja codificar seu AG como vetor binário? (S | N)\nR: ");
		String op = scanner.next();
		if ("S".equals(op)) {
			
		}
		
		scanner.close();
	}

}