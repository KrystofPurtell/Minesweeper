package org.cis1200.Minesweeper;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class MinesweeperGUI extends JPanel {

    private Minesweeper ms;

    public static final int BOARD_HEIGHT = 500;
    public static final int BOARD_WIDTH = 400;

    public MinesweeperGUI() {
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setFocusable(true);

        this.ms = new Minesweeper();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point p = e.getPoint();
                int rowOfClick = p.y / 50;
                int colOfClick = p.x / 50;
                ms.playTurn(rowOfClick, colOfClick);

                repaint();
            }
        });
    }

    public void reset() {
        this.ms.resetGame();
        repaint();
        requestFocusInWindow();
    }

    public void changeFlagMode() {
        this.ms.setFlagMode(!ms.getFlagMode());
        repaint();
        requestFocusInWindow();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
    }

    /*** Painting Methods ***/

    private void paintUnclickedUnflaggedCell(int row, int col, Graphics g) {
        g.setColor(Color.gray);
        g.fillRect(col * 50, row * 50, 50, 50);
    }

    private void paintFlaggedCell(int row, int col, Graphics g) {
        g.setColor(Color.gray);
        g.fillRect(col * 50, row * 50, 50, 50);
        g.setColor(Color.red);
        g.drawString("F", col * 50 + 21, row * 50 + 30);
    }

    private void paintClickedCellNotBomb(int row, int col, Graphics g) {
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(col * 50, row * 50, 50, 50);
        g.setColor(Color.blue);
        g.drawString(ms.getGameBoard()[row][col].getNumAdjBombs() + "",
                col * 50 + 21, row * 50 + 30);
    }

    private void paintBoardGameNotOver(Graphics g) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Cell currCell = ms.getGameBoard()[i][j];
                if (!currCell.getClicked() && !currCell.getFlagged()) {
                    paintUnclickedUnflaggedCell(i, j, g);
                } else if (!currCell.getClicked() && currCell.getFlagged()) {
                    paintFlaggedCell(i ,j, g);
                } else {
                    paintClickedCellNotBomb(i, j, g);
                }
            }
        }
    }

    private void paintBoardGameWon(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, 400, 400);
        g.setColor(Color.YELLOW);
        g.drawString("CONGRATS! YOU WON", 140, 200);
    }

    private void paintBoardGameLost(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, 400, 400);
        g.setColor(Color.blue);
        g.drawString("YOU HIT A BOMB!", 140, 200);
    }

    private void paintBoardLines(Graphics g) {
        g.setColor(Color.black);
         for (int i = 50; i < 400; i+=50) {
             g.drawLine(0, i, 400, i);
             g.drawLine(i, 0, i, 400);
         }
    }

    private void flagModeIndicator(Graphics g) {
        g.setColor(Color.red);
        if (ms.getFlagMode()) {
            g.drawString("Flag Mode On", 10, 420);
        } else {
            g.drawString("Flag Mode Off", 10, 420);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (ms.gameWon) {
            paintBoardGameWon(g);
        } else if (ms.gameOver) {
            paintBoardGameLost(g);
        } else {
            paintBoardGameNotOver(g);
            paintBoardLines(g);
            flagModeIndicator(g);
        }
    }
}
