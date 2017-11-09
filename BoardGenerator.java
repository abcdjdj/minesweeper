import java.util.Random;

/* BoardGenerator class contains all UI-independept logic. All functions
 * of this class strictly operate on Cell objects/arrays only! */
public class BoardGenerator {

	static void uncoverAll(Cell arr[][]) {
		for(Cell[] row : arr)
			for(Cell c : row)
				c.setCovered(false);
	}

	static void recursiveUncover(Cell arr[][], int x, int y) {
		/* Base cases */
		if(x < 0 || x >= Board.BOARD_ROWS || y < 0 || y >= Board.BOARD_COLS)
			return;
		if(arr[x][y].beenTraversed())
			return;
		if(arr[x][y].getCount()!=0) {
			arr[x][y].setCovered(false);
			return;
		}

		int i,j;
		arr[x][y].setCovered(false);
		arr[x][y].setTraversed(true);
		for(i = x - 1; i <= x+1; ++i) {
			for(j = y-1; j <= y+1; ++j) {
				recursiveUncover(arr, i, j);
			}
		}
	}

	/* Returns a 2D array of Cell objects. Total number of mines is decided
	 * by the Board.PROBABILITY constant. Index locations of mines are
	 * randomly alloted */
	static Cell[][] generateBoard() {
	        int rows, cols, i, j, m, n, count;
	        rows = Board.BOARD_ROWS;
	        cols = Board.BOARD_COLS;
	        Cell board[][] = new Cell[rows][cols];

		Random random = new Random();
		int totalMines = (int)(Board.PROBABILITY*rows*cols);

	        for (i = 0; i < rows; i++)
	            for (j = 0; j < cols; j++)
	                board[i][j] = new Cell();

		while(totalMines > 0) {
			i = random.nextInt(Board.BOARD_ROWS);
			j = random.nextInt(Board.BOARD_COLS);
			/* If board[i][j] has already been set as a mine, skip */
			if(board[i][j].getCount()==9)
				continue;
			board[i][j].setCount(9); //9=mine!!
			--totalMines;
		}


	        for (i = 0; i < rows; i++) {
	            for (j = 0; j < cols; j++) {
	                if (!board[i][j].isMine()) {
	                    count = 0;
			    /* Looping through all adjacent cells */
	                    for (m = i - 1; m <= i + 1; m++) {
	                        for (n = j - 1; n <= j + 1; n++) {
	                            try {
	                                if (board[m][n].isMine()) {
	                                    count++;
	                                    board[i][j].setCount(count);
	                                }
				    } catch (ArrayIndexOutOfBoundsException ex) {
	                            }
	                        }
	                    }
	                }

	            }
	        }
	        for (i = 0; i < rows; i++) {
	            for (j = 0; j < cols; j++) {
	                if (board[i][j].isMine()) {
	                    System.out.print("    x");
	                } else {
	                    System.out.print("    " + board[i][j].getCount());
	                }
	            }
	            System.out.println();
	        }

		return board;
	}
}
