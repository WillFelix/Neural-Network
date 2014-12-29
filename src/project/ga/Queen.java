package project.ga;

public class Queen {
	private int row;
	private int col;
	private int nextQueens;

	
	public Queen(int row, int col) {
		this.row = row;
		this.col = col;
		this.nextQueens = -1;
	}

	
	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public int getNextQueens() {
		return nextQueens;
	}

	public void setNextQueens(int nextQueens) {
		this.nextQueens = nextQueens;
	}

}