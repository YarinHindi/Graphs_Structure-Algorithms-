package Garph_GUI;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {
    public Frame() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(500, 500);
        this.setLayout(new FlowLayout());
        this.setJMenuBar(new MenuBar());
        this.setVisible(true);
    }

    public static void main(String[] args) {
        Frame frame = new Frame();
    }
}
