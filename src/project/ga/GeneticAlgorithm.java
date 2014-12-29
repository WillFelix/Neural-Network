package project.ga;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class GeneticAlgorithm {
	private static final int QUEEN = 1;
	private static final int NUMBER_OF_QUEENS = 8;
	private static final int MUTATION_NUMBER = 45;
	private static final Random random = new Random();

	private int[] subjects;
	private static int MUTATION_QUANTITY = 0;
	private int[][] chessboard = new int[8][8];
	private List<Queen> queens = new ArrayList<Queen>();
	
	
	public static void main(String[] args) {
		GeneticAlgorithm ga = new GeneticAlgorithm();
		ga.init();
		ga.darwin();
	}
	
	
	private void init() {
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Neural Network (Genetic Algorithm)");
		System.out.println("-------------------------------------------\n");

		subjects = new int[NUMBER_OF_QUEENS];
		
		// Half of a population is generated of a random way
		for (int i = 0; i < NUMBER_OF_QUEENS / 2; i++) {
			subjects[i] = new Random().nextInt(NUMBER_OF_QUEENS);
			queens.add(new Queen(subjects[i], i));
			chessboard[subjects[i]][i] = QUEEN;
		}
		
		// The other half I reverse the bits
		for (int i = NUMBER_OF_QUEENS / 2, j = 0; i < NUMBER_OF_QUEENS; i++, j++) {
			int subject = queens.get(j).getRow();
			int[] bits = intToBit(subject);
			int[] reverseBits = new int[bits.length];
			for (int k = 0; k < reverseBits.length; k++) {
				reverseBits[k] = bits[k] == 0 ? 1 : 0;
			}
			
			subject = bitToInt(reverseBits);
			queens.add(new Queen(subject, i));
			chessboard[subject][i] = QUEEN;
		}
		
		System.out.println("Tabuleiro Inicial:");
		print(chessboard);
		
		scanner.close();
	}
	
	private void darwin() {
		int cycles = 0;
		boolean goal = false;
		
		while (!goal) {
			goal = checkGoal();
			
			if (!goal) {
				generateKids();
				updateChessboard();
			}
			
			cycles++;
		}
		
		System.out.println("Tabuleiro Final: ");
		print(chessboard);
		System.out.println("Finalizado com " + cycles + " iterações.\nQuantidade de Mutações: " + MUTATION_QUANTITY);
	}
	
	private void generateKids() {
		int i = 0;
		List<Queen> kids = new ArrayList<Queen>();
		
		// CrossOver (Each crossover makes 2 kids)
		while (i < 2) {
			Queen father = queens.get( random.nextInt(queens.size()) );
			Queen mother = queens.get( random.nextInt(queens.size()) );
			kids.addAll( crossOver(father, mother) );
			
			i++;
		}
		
		// Mutação
		int luckNumber = random.nextInt(100);
		if (luckNumber == MUTATION_NUMBER) {
			
			i = random.nextInt( kids.size() );
			Queen junior = kids.get(i);
			kids.set(i, xmen(junior));
			
			GeneticAlgorithm.MUTATION_QUANTITY++;
		}
		
		// Changing the queens by the newbies
		for (i = 0;  i < queens.size(); i++) {
			for (Queen kid : kids) {
				
				if (queens.get(i).getCol() == kid.getCol()) {
					queens.set(i, kid);
				}
				
			}
		}
		
		
	}
	
	private void updateChessboard() {
		
		for (int i = 0; i < chessboard.length; i++) {
			for (int j = 0; j < chessboard[0].length; j++) {
				chessboard[i][j] = 0;
			}
		}
		
		for (int i = 0; i < queens.size(); i++) {
			int row = queens.get(i).getRow();
			int col = queens.get(i).getCol();
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
	
	private List<Queen> crossOver(Queen father, Queen mother) {
		int male, female, temp;
		int[] maleBit, femaleBit;
		int pointCut = random.nextInt(3);
		ArrayList<Queen> kids = new ArrayList<Queen>();

		maleBit = intToBit( father.getRow() );
		femaleBit = intToBit( mother.getRow() );

		for (int i = pointCut; i < 3; i++) {
			temp = maleBit[i];
			maleBit[i] = femaleBit[i];
			femaleBit[i] = temp;
		}
		
		male = bitToInt(maleBit);
		female = bitToInt(femaleBit);
		
		father.setRow(male);
		mother.setRow(female);
		
		kids.add(father);
		kids.add(mother);
		
		return kids;
	}

	private Queen xmen(Queen junior) {
		int row = junior.getRow();
		int[] bit = intToBit(junior.getRow());
		int hotspot = random.nextInt( bit.length );
		
		bit[hotspot] = bit[hotspot] == 0 ? 1 : 0;
		
		row = bitToInt(bit);
		junior.setRow(row);
		
		return junior;
	}
	
	private int lookAroundForEnemies(Queen queen, int r, int c) {
		int row = queen.getRow();
		int col = queen.getCol();
		int enemiesAmount = 0;

		for (int i = 0; i < chessboard.length; i++) {
			// Horizontal
			if (chessboard[row][i] == QUEEN && (i != c)) {
				enemiesAmount++;
			}

			// Vertical
			if (chessboard[i][col] == QUEEN && (i != r)) {
				enemiesAmount++;
			}
		}

		// Diagonal
		// Down Right
		for (int i = row, j = col; i < chessboard.length && j < chessboard.length; i++, j++) {
			if (chessboard[i][j] == QUEEN && (i != r && j != c)) {
				enemiesAmount++;
			}
		}

		// Down Left
		for (int i = row, j = col; i < chessboard.length && j >= 0; i++, j--) {
			if (chessboard[i][j] == QUEEN && (i != r && j != c)) {
				enemiesAmount++;
			}
		}

		// Up Right
		for (int i = row, j = col; i >= 0 && j < chessboard.length; i--, j++) {
			if (chessboard[i][j] == QUEEN && (i != r && j != c)) {
				enemiesAmount++;
			}
		}

		// Up Left
		for (int i = row, j = col; i >= 0 && j >= 0; i--, j--) {
			if (chessboard[i][j] == QUEEN && (i != r && j != c)) {
				enemiesAmount++;
			}
		}

		queen.setNextQueens(enemiesAmount);
		return enemiesAmount;

	}

	private boolean checkGoal() {
		boolean NO_ENEMIES = true;
		int allRight = 0;
		
		for (Queen q : queens) {
			if (analyseCurrentPosition(q) == NO_ENEMIES)
				allRight++;
		}
		
		if (allRight == NUMBER_OF_QUEENS)
			return true;
		
		return false;
	}

	private boolean analyseCurrentPosition(Queen q) {
		int i = lookAroundForEnemies(q, q.getRow(), q.getCol());
		if (i > 0)
			return false;

		return true;
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