package org.cis1200.Minesweeper;

/**
 * Class for the Cell object, which makes up the entire game board
 */
public class Cell {

    /*** Instance Vars ***/
    private int numAdjBombs; // -1 for all bombs
    private boolean clicked;
    private boolean bomb;
    private boolean flagged;

    /*** Getters and Setters ***/
    public int getNumAdjBombs() {
        return this.numAdjBombs;
    }

    public boolean getClicked() {
        return this.clicked;
    }

    public boolean getBomb() {
        return this.bomb;
    }

    public boolean getFlagged() {
        return this.flagged;
    }

    public void setNumAdjBombs(int numAdjBombs) {
        this.numAdjBombs = numAdjBombs;
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }

    public void setBomb(boolean bomb) {
        this.bomb = bomb;
    }

    public void setFlagged(boolean flagged) {
        this.flagged = flagged;
    }

    public Cell(boolean bomb) { // constructor for beginning of game
        this.bomb = bomb;
        this.flagged = false;
        this.clicked = false;
        if (bomb) {
            this.numAdjBombs = -1;
        } else {
            this.numAdjBombs = 0; // will change later
        }
    }
}
