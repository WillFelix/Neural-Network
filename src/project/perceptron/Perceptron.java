package project.perceptron;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import project.brain.FileRead;

public class Perceptron {
	public Scanner scanner = new Scanner(System.in);
	public final int PERFECT = 0;
	public final int TRAIN = 1, TEST = 2;

	public int iterations = 0;
	public int output1 = 1, output2 = 2, major = 0, minor = 0;
	public int activation = 0, error = 0;
	public double n = 0.0, thresholding = 0, ux = 0.0;

	public static List<Neuron> neurons = new ArrayList<Neuron>();
	public static double[] weights;

	public static void main(String[] args) {
		Perceptron neuron = new Perceptron();
		neuron.init();
	}

	public void init() {
		int op = 0;
		
		do {
			iterations = 0;
			
			System.out.println("Neuron Perceptron\n--------------------------------------------\n");
			System.out.println("1. Train Base");
			System.out.println("2. Test Base");
			System.out.println("3. Exit");
			System.out.print("\nEscolha uma opção: ");
			op = scanner.nextInt();

			switch (op) {
			case 1:
				menu();
				train();
				break;
			case 2:
				test();
				break;
			default:
				break;
			}

			System.out.println("\n\n\n--------------------------------------------");
		} while (op != 3);

		scanner.close();
	}
	
	public void menu() {
		int op = 0;
		do {
			System.out.println("\n\nNeuron Perceptron\n--------------------------------------------\n");
			System.out.println("1. Simular a porta AND");
			System.out.println("2. Simular a porta OR");
			System.out.println("3. Continuar");
			System.out.print("\nEscolha uma opção: ");
			op = scanner.nextInt();

			switch (op) {
			case 1:
				and();
				wizard(TRAIN);
				break;
			case 2:
				or();
				break;
			default:
				break;
			}

		} while (op > 3 || op < 1);
		
		wizard(TRAIN);
	}

	public void wizard(int menu) {
		
		if (neurons.isEmpty()) {
			System.out.println("\nDeseja carregar os dados por algum arquivo? [s|N]");
			String op = scanner.next();
			
			if ("S".equals(op) || "s".equals(op)) {
				neurons.clear();
				
				System.out.print("Digite o nome da base: ");
				String base = scanner.next();
				List<Double[]> database = FileRead.pull("database/" + base + "_perceptron", ";");
				for (Double[] data : database) {
					
					Neuron n = new Neuron();
					n.getInput().add(-1.0);
					n.getInput().add(data[0]);
					n.getInput().add(data[1]);
					n.setYd( data[2].intValue() );
					
					neurons.add(n);
				}
				System.out.println("Neurons carregados...");
			}
		}
		
		switch (menu) {
			case TRAIN:
				wizardTrain();
				break;
			case TEST:
				wizardTest();
			default:
				break;
		}
	}
	
	public void wizardTest() {
		if (neurons.isEmpty()) {
			
			System.out.print("Quantos registros tem a base? R: ");
			int qtd = scanner.nextInt();
			for (int i = 0; i < qtd; i++) {
				List<Double> xs = new ArrayList<Double>();
				for (int j = 0; j < weights.length; j++) {
					System.out.print("[t" + i + "] Digite o x" + j + ": ");
					xs.add(scanner.nextDouble());
				}
				
				Neuron n = new Neuron();
				n.getInput().addAll(xs);
				neurons.add(n);
				System.out.println();
			}
			
		}
	}

	public void wizardTrain() {
		System.out.print("Taxa de Aprendizagem: ");
		n = scanner.nextDouble();
		
		int qtd = 0;
		if (neurons.isEmpty()){
			System.out.print("Quantos registros tem a base? R: ");
			qtd = scanner.nextInt();
		}
		
		int qtdIn = 0;
		if (!neurons.isEmpty()) {
			qtdIn = neurons.get(0).getInput().size();
		} else {
			System.out.print("Quantas entradas? R: ");
			qtdIn = scanner.nextInt();
		}
		
		for (int i = 0; i < qtd; i++) {
			List<Double> xs = new ArrayList<Double>();
			for (int j = 0; j < qtdIn; j++) {
				System.out.print("[t" + i + "] Digite o x" + j + ": ");
				xs.add(scanner.nextDouble());
			}

			System.out.print(".. Digite a saída desejada: ");
			int yd = scanner.nextInt();
			System.out.print("\n");
			
			Neuron n = new Neuron();
			n.getInput().addAll(xs);
			n.setYd(yd);
			neurons.add(n);
		}

		System.out.println("\n[Pesos]");
		weights = new double[qtdIn];
		for (int j = 0; j < qtdIn; j++) {
			System.out.print("Digite o w" + j + ": ");
			weights[j] = scanner.nextDouble();
		}
		
		// Garnish
		System.out.println("\n");
		for (int i = 0; i < weights.length; i++)
			System.out.print("x" + i + " \t w" + i + " \t ");
		System.out.println("u(x) \t a(ux) \t yo \t yd \t error ");
		System.out.println("-------------------------------------------------------------------------------------------");
	}
	
	public void train() {
		major = output1 > output2 ? output1 : output2;
		minor = output1 < output2 ? output1 : output2;

		// int limit = 100;
		boolean baseTrained = false;
		while (!baseTrained) {

			for (int i = 0; i < neurons.size(); i++) {
				Neuron x = neurons.get(i);
				calculateOutput(x, i);
				//printStatus(x);
				
				if (error != PERFECT) {
					updateWeights(x.getInput());
				}
				
			}
			iterations++;
			baseTrained = isLearned();

		}
		
		finish();
	}

	public void test() {
		wizard(TEST);
		
		if (!neurons.isEmpty()) {
			for (Neuron neuron : neurons) {
				
				List<Double> in = neuron.getInput();
				double out = 0;
				for (int i = 0; i < weights.length; i++) {
					out += (in.get(i) * weights[i]);
				}
				
				for (int i = 0; i < in.size(); i++) {
					if (i == 0) System.out.print("[\t");
					if (i > 0)	 System.out.print("\t|\t");
					System.out.print(in.get(i));
					if (i == in.size() - 1) System.out.print("\t] : Class ");
				}
				
				if (out >= thresholding)		System.out.println(major);
				else							System.out.println(minor);
				
			}
		}
		
		System.out.println("\n");
	}

	public void calculateOutput(Neuron x, Integer index) {
		List<Double> in = x.getInput();

		// Calculating the u(x)
		ux = 0;
		for (int i = 0; i < in.size(); i++) {
			ux += (in.get(i) * weights[i]);
		}

		// Generating output
		if (ux >= thresholding)			activation = 1;
		else							activation = -1;

		if (activation == 1)			x.setYo(major);
		else							x.setYo(minor);

		// Updating output
		neurons.set(index, x);

		// Error
		error = x.generateError();
	}

	public boolean isLearned() {
		if (!neurons.isEmpty()) {
			for (Neuron neuron : neurons) {
				
				if (neuron.getYo() != neuron.getYd()) {
					return false;
				}
				
			}
		}

		return true;
	}

	public void updateWeights(List<Double> x) {
		for (int i = 0; i < weights.length; i++) {
			double w = weights[i];
			double in = x.get(i);
			double e = error;

			weights[i] = w + (n * in * e);
		}
	}

	public void printStatus(Neuron x) {
		List<Double> in = x.getInput();
		DecimalFormat df = new DecimalFormat("#.##");

		for (int i = 0; i < in.size(); i++) {
			System.out.print(df.format(in.get(i)) + " \t " + df.format(weights[i]) + " \t "); 
		}
		
		System.out.println(	df.format(ux) + " \t " + activation + "\t" +
							x.getYo() + " \t " + x.getYd() + " \t " +
							error);
	}
	
	// AND
	public void and() {
		neurons.clear();
		
		output1 = 0;
		output2 = 1;
		
		Neuron n1 = new Neuron();
		n1.getInput().addAll(Arrays.asList(-1.0, 0.0, 0.0));
		n1.setYd(0);
		neurons.add(n1);

		Neuron n2 = new Neuron();
		n2.getInput().addAll(Arrays.asList(-1.0, 0.0, 1.0));
		n2.setYd(0);
		neurons.add(n2);

		Neuron n3 = new Neuron();
		n3.getInput().addAll(Arrays.asList(-1.0, 1.0, 0.0));
		n3.setYd(0);
		neurons.add(n3);

		Neuron n4 = new Neuron();
		n4.getInput().addAll(Arrays.asList(-1.0, 1.0, 1.0));
		n4.setYd(1);
		neurons.add(n4);
	}

	// OR
	public void or() {
		neurons.clear();
		
		output1 = 0;
		output2 = 1;
		
		Neuron n1 = new Neuron();
		n1.getInput().addAll(Arrays.asList(-1.0, 0.0, 0.0));
		n1.setYd(0);
		neurons.add(n1);
		
		Neuron n2 = new Neuron();
		n2.getInput().addAll(Arrays.asList(-1.0, 0.0, 1.0));
		n2.setYd(1);
		neurons.add(n2);

		Neuron n3 = new Neuron();
		n3.getInput().addAll(Arrays.asList(-1.0, 1.0, 0.0));
		n3.setYd(1);
		neurons.add(n3);

		Neuron n4 = new Neuron();
		n4.getInput().addAll(Arrays.asList(-1.0, 1.0, 1.0));
		n4.setYd(1);
		neurons.add(n4);
	}
	
	public void finish() {
		DecimalFormat df = new DecimalFormat("#.##");
		
		// PESOS
		System.out.println("\nPesos encontrados: ");
		for (int i = 0; i < weights.length; i++) {
			System.out.print( df.format(weights[i]) + "    ");
		}
		
		// ITERAÇÕES
		System.out.println("Quantidade de Iterações: " + iterations);

		// FUNÇÃO
		System.out.println("Função Encontrada:\n");
		for (int i = 0; i < weights.length; i++) {
			if (i > 0 && i < weights.length) System.out.print(" + ");
			System.out.print( df.format(weights[i]) + "x" + i);
		}
		System.out.println(">= " + thresholding + " => " + major);
		
		for (int i = 0; i < weights.length; i++) {
			if (i > 0 && i < weights.length) System.out.print(" + ");
			System.out.print( df.format(weights[i]) + "x" + i);
		}
		System.out.println("< " + thresholding + " => " + minor);
	}
}
