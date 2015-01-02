package project.ga;

public class Queen {
	private int row;
	private int col;
	private int targets;

	public Queen(int row, int col) {
		this.row = row;
		this.col = col;
		this.targets = -1;
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

	public int getTargets() {
		return targets;
	}

	public void setTargets(int targets) {
		this.targets = targets;
	}

}