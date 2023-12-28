package org.cis1200.Minesweeper;
import java.util.*;


/** This class handles all logic for the Minesweeper game. It functions independent of the
 * GUI and is the "backend" for the game.
 */

public class Minesweeper {

    /*** Instance Variables ***/
    Cell[][] gameBoard;
    boolean gameWon;
    boolean gameOver;
    boolean flagMode;
    int cellsRevealed;

    /*** Getters and Setters ***/
    public Cell[][] getGameBoard() { // note this returns a non-encapsulated board, be careful
        return this.gameBoard;
    }

    public boolean getGameWon() {
        return this.gameWon;
    }

    public boolean getGameOver() {
        return this.gameOver;
    }

    public boolean getFlagMode() {
        return this.flagMode;
    }

    public int getCellsRevealed() {
        return this.cellsRevealed;
    }

    public void setFlagMode(boolean newFlagMode) {
        this.flagMode = newFlagMode;
    }

    /*** All Other Methods ***/

    private int findNumAdjBombs(int row, int col) { // determines num of adj bombs to cell
        int numBombs = 0;

        // hardcoded for all 8 possible directions
        if (row - 1 >= 0) {
            if (gameBoard[row - 1][col] != null && gameBoard[row - 1][col].getBomb()) {
                numBombs++;
            }
            if (col - 1 >= 0) {
                if (gameBoard[row - 1][col - 1] != null && gameBoard[row - 1][col - 1].getBomb()) {
                    numBombs++;
                }
            }
            if (col + 1 <= 7) {
                if (gameBoard[row - 1][col + 1] != null && gameBoard[row - 1][col + 1].getBomb()) {
                    numBombs++;
                }
            }
        }

        if (row + 1 <= 7) {
            if (gameBoard[row + 1][col] != null && gameBoard[row + 1][col].getBomb()) {
                numBombs++;
            }
            if (col - 1 >= 0) {
                if (gameBoard[row + 1][col - 1] != null && gameBoard[row + 1][col - 1].getBomb()) {
                    numBombs++;
                }
            }
            if (col + 1 <= 7) {
                if (gameBoard[row + 1][col + 1] != null && gameBoard[row + 1][col + 1].getBomb()) {
                    numBombs++;
                }
            }
        }

        if (col - 1 >= 0) {
            if (gameBoard[row][col - 1] != null && gameBoard[row][col - 1].getBomb()) {
                numBombs++;
            }
        }

        if (col + 1 <= 7) {
            if (gameBoard[row][col + 1] != null && gameBoard[row][col + 1].getBomb()) {
                numBombs++;
            }
        }

        return numBombs;
    }

    private Cell[][] generateNewBoard() { // makes new 8x8 board w/ 10 randomized bombs

        // place bombs
        Set<Integer> bombLocations = new HashSet<>();
        int numBombs = 0;
        while (numBombs < 10) {
            int newLocation = (int) (Math.random() * 64);
            if (!bombLocations.contains(newLocation)) {
                bombLocations.add(newLocation);
                numBombs++;
            }
        }

        Cell[][] cellArr = new Cell[8][8];

        for (Integer bombSlot : bombLocations) {
            int bombRow = bombSlot / 8;
            int bombCol = bombSlot - (8 * bombRow);
            cellArr[bombRow][bombCol] = new Cell(true);
        }

        this.gameBoard = cellArr;

        // configure rest of board
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (cellArr[i][j] == null) { // not bomb if not null
                    cellArr[i][j] = new Cell(false);
                    int numAdjBombs = findNumAdjBombs(i, j);
                    cellArr[i][j].setNumAdjBombs(numAdjBombs);
                }
            }
        }

        return cellArr;
    }

    public Minesweeper() { // constructor
        this.gameWon = false;
        this.gameOver = false;
        this.flagMode = false;
        this.cellsRevealed = 0;
        this.gameBoard = generateNewBoard();
    }


    /**
     * Checks if the game is over (won if 54 cells are revealed b/c always 10 bombs)
     * @return boolean of the instance var for whether game is won or not
     */
    public void checkIfGameIsWon() {
        if (cellsRevealed >= 54) {
            this.gameWon = true;
            this.gameOver = true;
        }
    }

    private void playTurnRecursiveHelper(int row, int col) {

        // base cases
        if (row < 0 || row > 7 || col < 0 || col > 7) {
            return;
        } else if (this.gameBoard[row][col].getClicked() || this.gameBoard[row][col].getBomb()) {
            return;
        } else if (this.gameBoard[row][col].getNumAdjBombs() > 0) {
            this.gameBoard[row][col].setClicked(true);
            this.cellsRevealed++;
            return;
        } else {
            this.gameBoard[row][col].setClicked(true);
            this.cellsRevealed++;
        }

        playTurnRecursiveHelper(row - 1, col); // straight up
        playTurnRecursiveHelper(row - 1, col - 1); // upper left
        playTurnRecursiveHelper(row - 1, col + 1); // upper right
        playTurnRecursiveHelper(row + 1, col); // straight down
        playTurnRecursiveHelper(row + 1, col - 1); // lower left
        playTurnRecursiveHelper(row + 1, col + 1); // lower right
        playTurnRecursiveHelper(row, col - 1); // left
        playTurnRecursiveHelper(row, col + 1); // right
    }

    public void playTurn(int row, int col) {

        if (gameOver) {
            return; // cannot play if game is over
        }

        if (row < 0 || row > 7 || col < 0 || col > 7) { // ensure valid coordinates
            return;
        }

        if (gameBoard[row][col].getClicked()) { // already clicked -> do nothing
            return;
        }

        if (flagMode) { // flag mode -> flag that cell and move on
            this.gameBoard[row][col].setFlagged(true);
            return;
        }

        // check for bombs -> if so, update to reflect that game is lost
        if (this.gameBoard[row][col].getBomb()) {
            this.gameOver = true;
            return;
        }

        // otherwise, reveal all appropriate cells via recursion
        playTurnRecursiveHelper(row, col);

        checkIfGameIsWon();
    }

    public void resetGame() {
        this.gameWon = false;
        this.gameOver = false;
        this.flagMode = false;
        this.cellsRevealed = 0;
        this.gameBoard = generateNewBoard();
    }
}
