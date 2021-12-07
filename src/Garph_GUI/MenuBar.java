package Garph_GUI;

import data_Structure.D_W_Graph;
import graph_Algorithms.D_W_Graph_Algo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MenuBar extends JMenuBar implements ActionListener {
    private JMenu fileMenu;
    private JMenu runMenu;
    private JMenuItem load;
    private JMenuItem save;
    private JMenuItem edit;
    private JMenuItem show_grpah;
    private JMenuItem show_center;
    private JMenuItem show_shortest_path;
    private JButton button;
    private JTextField textField;
    private D_W_Graph_Algo algo;
    private D_W_Graph graph;

    public MenuBar() {
        this.algo = new D_W_Graph_Algo();
        this.graph = new D_W_Graph();
        this.algo.init(this.graph);

        this.fileMenu = new JMenu("file");
        this.runMenu = new JMenu("run");

        this.load = new JMenuItem("load");
        this.save = new JMenuItem("save");
        this.edit = new JMenuItem("edit");

        this.show_grpah = new JMenuItem("show graph");
        this.show_center = new JMenuItem("show center");
        this.show_shortest_path = new JMenuItem("show shortest path");

        this.load.addActionListener(this);
        this.save.addActionListener(this);
        this.edit.addActionListener(this);

        this.show_grpah.addActionListener(this);
        this.show_center.addActionListener(this);
        this.show_shortest_path.addActionListener(this);

        this.fileMenu.add(load);
        this.fileMenu.add(save);
        this.fileMenu.add(edit);

        this.runMenu.add(show_grpah);
        this.runMenu.add(show_center);
        this.runMenu.add(show_shortest_path);

        this.add(fileMenu);
        this.add(runMenu);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.load) {
            System.out.println("load has been clicked");
            JFileChooser fileChooser = new JFileChooser();
            int response = fileChooser.showOpenDialog(null);
            if (response == JFileChooser.APPROVE_OPTION) {
                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                String file_name = String.valueOf(file);
                System.out.println(file);
                System.out.println(file_name);
                this.algo.load(file_name);
                this.graph = (D_W_Graph) this.algo.getGraph();
            }
        }
        if (e.getSource() == this.save) {
            System.out.println("save has been clicked");
            button = new JButton("submit");
            button.addActionListener(this);
            textField = new JTextField();
            textField.setPreferredSize(new Dimension(100, 40));
            textField.setText("Enter file name that you want to create");
            this.add(button);
            this.add(textField);
        }
        if (e.getSource() == button) {
            System.out.println("submit has been clicked");
            this.algo.save(textField.getText());
            this.remove(button);
            this.remove(textField);
        }
        if (e.getSource() == this.edit) {
            System.out.println("edit has been clicked");
        }
        // Run menu
        if (e.getSource() == this.show_grpah) {
            System.out.println("show has been clicked");

        }
        if (e.getSource() == this.show_center) {

        }
        if (e.getSource() == this.show_shortest_path) {

        }
    }
}