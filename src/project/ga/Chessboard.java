package project.ga;

import java.util.ArrayList;
import java.util.List;

public class Chessboard {

	private List<Queen> queens;

	public Chessboard() {
		queens = new ArrayList<Queen>();
	}

	public List<Queen> getQueens() {
		return queens;
	}

	public int[] getRowQueens() {
		int[] rows = new int[queens.size()];
		for (int i = 0; i < queens.size(); i++) {
			rows[i] = queens.get(i).getRow();
		}

		return rows;
	}

	public void setRowQueens(int[] rows) {
		for (int i = 0; i < queens.size(); i++) {
			queens.get(i).setRow(rows[i]);
		}
	}

	public boolean addQueen(Queen q) {
		return queens.add(q);
	}

	public void addAllQueens(List<Queen> queens) {
		this.queens = queens;
	}
	
	
	public int observeEnemies() {
		int result = 0;
		
		for (int i = 0; i < queens.size(); i++) {
			Queen q = queens.get(i);
			int targets = lookAroundForEnemies(q, q.getRow(), q.getCol());
			result += targets;
		}
		
		return result;
	}
	
	private int lookAroundForEnemies(Queen queen, int r, int c) {
		int row = queen.getRow();
		int col = queen.getCol();
		int enemiesAmount = 0;

		for (int i = 0; i < queens.size(); i++) {
			Queen q = queens.get(i);
			
			// Horizontal
			if (q.getRow() == row && (i != c)) {
				enemiesAmount++;
			}

			// Vertical
			if (q.getCol() == col && (i != r)) {
				enemiesAmount++;
			}
		}

		// Diagonal
		// Down Right
		for (int i = row, j = col; i < queens.size() && j < queens.size(); i++, j++) {
			Queen q = queens.get(i);
			
			if ((q.getRow() - q.getCol() == row - col) && (i != r && j != c)) {
				enemiesAmount++;
			}
		}

		// Down Left
		for (int i = row, j = col; i < queens.size() && j >= 0; i++, j--) {
			Queen q = queens.get(i);
			
			if ((q.getRow() + q.getCol() == row + col) && (i != r && j != c)) {
				enemiesAmount++;
			}
		}

		queen.setTargets(enemiesAmount);
		return enemiesAmount;

	}

}