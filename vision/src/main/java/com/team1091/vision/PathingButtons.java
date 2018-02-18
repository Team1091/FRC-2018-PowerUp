package com.team1091.vision;

import javax.swing.*;
import java.awt.*;

public class PathingButtons {

    public void makeButtons(){
        JFrame frame = new JFrame();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel buttonPanel = new JPanel();
        JPanel containerPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(30,27));

        for (int x = 0; x < 27; x++) {
            for (int y = 0; y < 30; y++) {
                buttonPanel.add(new JButton(""+x+", "+ y));
            }
        }
        buttonPanel.setPreferredSize(new Dimension(1200, 1600));
        containerPanel.add(buttonPanel);

        frame.getContentPane().add(containerPanel);
        frame.pack();
        frame.setVisible(true);
    }
}
