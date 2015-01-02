package project.ga;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GeneticAlgorithm {
	private static int MUTATION_QUANTITY = 0;
	private static final int QUEEN = 1;
	private static final int CHROMO_SIZE = 24;
	private static final int MUTATION_NUMBER = 45;
	private static final int NUMBER_OF_QUEENS = 8;
	private static final int QUANTITY_POPULATION_GENERATE = 4;
	private static final Random random = new Random();

	private int[][] chessboard = new int[8][8];
	private Chessboard board = new Chessboard();
	private List<Chessboard> population = new ArrayList<Chessboard>();

	public static void main(String[] args) {
		GeneticAlgorithm ga = new GeneticAlgorithm();
		ga.init();
		ga.darwin();
	}

	private void init() {
		System.out.println("Neural Network (Genetic Algorithm)");
		System.out.println("-------------------------------------------\n");

		
		// Half of a population is generated of a random way
		for (int index = 0; index < QUANTITY_POPULATION_GENERATE / 2; index++) {
			
			Chessboard board = new Chessboard();
			
			for (int i = 0; i < NUMBER_OF_QUEENS; i++) {
				int subject = new Random().nextInt(NUMBER_OF_QUEENS);
				Queen q = new Queen(subject, i);
				board.addQueen(q);
			}
			
			population.add(board);
			
		}
		
		// The other half I reverse the bits
		for (int index = 0; index < QUANTITY_POPULATION_GENERATE / 2; index++) {
			
			Chessboard board = new Chessboard();
			
			for (int i = 0; i < NUMBER_OF_QUEENS; i++) {
				List<Queen> queens = population.get(index).getQueens();
				int subject = queens.get(index).getRow();
				int[] bits = intToBit(subject);
				int[] reverseBits = new int[bits.length];
				for (int k = 0; k < reverseBits.length; k++) {
					reverseBits[k] = bits[k] == 0 ? 1 : 0;
				}
	
				subject = bitToInt(reverseBits);
				Queen q = new Queen(subject, i);
				board.addQueen(q);
			}
			
			population.add(board);
			
		}

		System.out.println("Tabuleiro Inicial:");
		board = population.get(0);
		updateChessboard();
		print(chessboard);
	}

	private void darwin() {
		int cycles = 0;
		boolean goal = false;

		while (!goal) {
			goal = checkGoal();

			if (!goal) {
				generateKids();
			}

			cycles++;
		}

		System.out.println("Tabuleiro Final: ");
		updateChessboard();
		print(chessboard);
		System.out.println("Finalizado com " + cycles + " iterações.");
		System.out.println("Quantidade de Mutações: " + MUTATION_QUANTITY);
	}

	private void generateKids() {
		int i = 0;
		List<Chessboard> kids = new ArrayList<Chessboard>();

		// CrossOver (Each crossover makes 2 kids)
		while (i < QUANTITY_POPULATION_GENERATE / 2) {
			Chessboard father = population.get(random.nextInt(population.size()));
			Chessboard mother = population.get(random.nextInt(population.size()));
			kids.addAll(crossOver(father, mother));

			i++;
		}

		// Mutation
		int luckNumber = random.nextInt(100);
		if (luckNumber == MUTATION_NUMBER) {
			i = random.nextInt(kids.size());
			Chessboard junior = kids.get(i);
			kids.set(i, mutation(junior));

			GeneticAlgorithm.MUTATION_QUANTITY++;
		}

		// Changing the queens by the newbies
		population.clear();
		population.addAll(kids);
	}

	private void updateChessboard() {

		for (int i = 0; i < chessboard.length; i++) {
			for (int j = 0; j < chessboard[0].length; j++) {
				chessboard[i][j] = 0;
			}
		}
		
		for (int i = 0; i < board.getQueens().size(); i++) {
			int row = board.getQueens().get(i).getRow();
			int col = board.getQueens().get(i).getCol();
			chessboard[row][col] = QUEEN;
		}

	}

	private void print(int[][] matrix) {
		System.out.println("\n");
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				System.out.print(matrix[i][j] + "    ");
			}
			System.out.println("\n");
		}
		System.out.println("\n");
	}

	private List<Chessboard> crossOver(Chessboard father, Chessboard mother) {
		int[] chromoX, chromoY;
		int temp, pointCut = random.nextInt(CHROMO_SIZE);
		
		Chessboard male, female;
		List<Chessboard> kids = new ArrayList<Chessboard>();
		
		chromoX = generateChromosomes(father.getRowQueens());
		chromoY = generateChromosomes(mother.getRowQueens());
		for (int i = pointCut; i < CHROMO_SIZE; i++) {
			temp = chromoX[i];
			chromoX[i] = chromoY[i];
			chromoY[i] = temp;
		}

		male = generateChessboardByChromosome(chromoX);
		female = generateChessboardByChromosome(chromoY);
		
		kids.add(male);
		kids.add(female);

		return kids;
	}
	
	private int[] generateChromosomes(int[] bits) {
		int index = 0;
		int[] result = new int[CHROMO_SIZE];
		
		for (int i = 0; i < bits.length; i++) {
			int[] bit = intToBit(bits[i]);
			
			for (int j = 0; j < bit.length; j++) {
				if (index >= 24) {
					System.out.println("AQUI");
				}
				result[index] = bit[j];
				index++;
			}
		}
		
		return result;
	}

	private Chessboard generateChessboardByChromosome(int[] bits) {
		Chessboard chessboard = new Chessboard();
		
		for (int i = 0, index = 0; i < bits.length; i += 3, index++) {
			int[] bit = {bits[i], bits[i+1], bits[i+2]};
			int row = bitToInt(bit);
			chessboard.addQueen(new Queen(row, index));
		}
		
		return chessboard;
	}

	private Chessboard mutation(Chessboard junior) {
		int numberQueen = random.nextInt(junior.getQueens().size());
		Queen q = junior.getQueens().get(numberQueen);
		
		int row = q.getRow();
		int[] bit = intToBit(q.getRow());
		int hotspot = random.nextInt(bit.length);

		bit[hotspot] = bit[hotspot] == 0 ? 1 : 0;
		row = bitToInt(bit);
		q.setRow(row);
		
		junior.getQueens().set(numberQueen, q);

		return junior;
	}

	private boolean checkGoal() {
		for (Chessboard p : population) {
			if (p.observeEnemies() == 0){
				board = p;
				return true;
			}
		}

		return false;
	}

	private int[] intToBit(int integer) {
		int[] bit = new int[3];

		for (int i = 0, pos = 2; i < 3; i++, pos--) {
			if (integer % 2 == 0) {
				bit[pos] = 0;
			} else {
				bit[pos] = 1;
			}
			integer /= 2;
		}
		return bit;
	}

	private int bitToInt(int[] bit) {
		int inteiro = 0;
		for (int i = 0, pos = 2; i < 3; i++, pos--) {
			if (bit[i] > 0) {
				inteiro += Math.pow(2, pos);
			}
		}
		return inteiro;
	}
}