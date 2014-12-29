package project.ga;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Chessboard {
	public static final int QUEEN = 1;
	public static final int NUMBER_OF_QUEENS = 8;

	public int[][] board = new int[8][8];
	public int[][] chessboard = new int[8][8];
	public List<Queen> queens = new ArrayList<Queen>();
	public List<int[][]> visiteds = new ArrayList<int[][]>();

	public static void main(String[] args) {
		Chessboard chessboard = new Chessboard();
		chessboard.start();
		chessboard.greedySearch();
	}

	private void start() {
		Scanner input = new Scanner(System.in);

		System.out.println("Inteligência Artificial");
		System.out.println("-------------------------------------");

		System.out.println("Digite as " + NUMBER_OF_QUEENS + " posições das rainhas: \n");
		for (int i = 0; i < NUMBER_OF_QUEENS; i++) {
			System.out.println("Rainha " + (i + 1) + ": ");

			System.out.print("Linha:");
			int row = input.nextInt();

			queens.add(new Queen(row, i));
			chessboard[row][i] = QUEEN;
			System.out.print("\n");
		}

		input.close();
	}

	private void greedySearch() {
		int count = 0;
		System.out.print("Tabuleiro Original");
		print(chessboard);

		boolean goal = false;
		List<Queen> positions = new ArrayList<Queen>();
		while (!goal) {
			for (int index = 0; index < queens.size(); index++) {

				Queen queen = queens.get(index);
				positions = analyseMovements(queen);
				sort(positions);

				boolean contain = true;
				while (contain && !positions.isEmpty()) {
					queen = positions.remove(0);
					updateCloneBoard(queen);
					contain = contains(visiteds, board);
	
					if (!contain) {
						updateChessboard(queen);
						visiteds.add(board);
						queens.set(index, queen);
	
						System.out.print(++count + "° Passo...");
						print(board);
					}
				}
				
				goal = false;
				int allRight = checkGoal(queens);
				if (allRight == NUMBER_OF_QUEENS){
					goal = true;
					break;
				}

			}
		}

	}
	
	private int checkGoal(List<Queen> queens) {
		boolean NO_ENEMIES = true;
		int allRight = 0;
		
		for (Queen q : queens) {
			if (analyseCurrentPosition(q) == NO_ENEMIES)
				allRight++;
		}
		
		return allRight;
	}

	private boolean analyseCurrentPosition(Queen q) {
		int i = lookAroundForEnemies(q, q.getRow(), q.getCol());
		if (i > 0)
			return false;

		return true;
	}

	private void sort(List<Queen> b) {
		Collections.sort(b, new Comparator<Queen>() {
			@Override
			public int compare(Queen q1, Queen q2) {
				if (q1.getNextQueens() > q2.getNextQueens()) {
					
					return 1;
					
				} else if (q1.getNextQueens() < q2.getNextQueens()) {
					
					return -1;
					
				} else {
					
					return 0;
					
				}
			}
		});
	}

	public boolean contains(List<int[][]> visiteds, int[][] matrix) {

		for (int w = 0; w < visiteds.size(); w++) {
			int count = 0;
			int[][] v = visiteds.get(w);

			for (int i = 0; i < matrix.length; i++) {
				for (int j = 0; j < matrix.length; j++) {
					if (v[i][j] == matrix[i][j]) {
						count++;
					}
				}
			}

			if (count == (v.length * v.length)) {
				return true;
			}

		}

		return false;
	}

	private List<Queen> analyseMovements(Queen queen) {
		List<Queen> positions = new ArrayList<Queen>();
		int row = queen.getRow();
		int col = queen.getCol();

		for (int i = 0; i < chessboard.length; i++) {
			Queen q = new Queen(i, col);
			lookAroundForEnemies(q, row, col);
			positions.add(q);
		}

		return positions;
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

	public void print(int[][] matrix) {
		System.out.println("\n");
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				System.out.print(matrix[i][j] + "    ");
			}
			System.out.println("\n");
		}
		System.out.println("\n");
	}

	public void updateChessboard(Queen queen) {
		int row = queen.getRow();
		int col = queen.getCol();

		for (int i = 0; i < chessboard.length; i++) {

			if (i == row)
				chessboard[row][col] = QUEEN;
			else
				chessboard[i][col] = 0;

		}
	}

	public void updateCloneBoard(Queen queen) {
		int row = queen.getRow();
		int col = queen.getCol();

		board = cloneMatrix(chessboard);

		for (int i = 0; i < board.length; i++) {

			if (i == row)
				board[row][col] = QUEEN;
			else
				board[i][col] = 0;

		}
	}

	public int[][] cloneMatrix(int[][] matrix) {
		int[][] clone = new int[matrix.length][matrix[0].length];

		for (int i = 0; i < clone.length; i++) {
			for (int j = 0; j < clone.length; j++) {
				clone[i][j] = matrix[i][j];
			}
		}

		return clone;
	}

}