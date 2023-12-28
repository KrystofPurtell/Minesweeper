package org.cis1200.Minesweeper;

import javax.swing.*;
import java.awt.*;

public class RunMinesweeper implements Runnable {

    @Override
    public void run() {

        final JFrame frame = new JFrame("Minesweeper");
        frame.setLocation(0, 0); // 500, 300 prev
        frame.setPreferredSize(new Dimension(400, 500));

        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);

        final MinesweeperGUI game = new MinesweeperGUI();
        frame.add(game, BorderLayout.CENTER);


        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.SOUTH);
        final JButton resetButton = new JButton("Restart Game");
        resetButton.addActionListener(e -> game.reset());
        control_panel.add(resetButton);
        final JButton flagModeButton = new JButton("Flag Mode On/Off");
        flagModeButton.addActionListener(e -> game.changeFlagMode());
        control_panel.add(flagModeButton);

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        game.reset();
    }
}
