package Garph_GUI;

import api.DirectedWeightedGraph;
import api.NodeData;
import data_Structure.D_W_Graph;
import data_Structure.Edge_Data;
import data_Structure.Geo_Location;
import data_Structure.Node_Data;
import graph_Algorithms.D_W_Graph_Algo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class Frame extends JFrame implements ActionListener {
    public DirectedWeightedGraph graph;
    public D_W_Graph_Algo algo;
    private int modecount;

    //MENU BAR
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
    private JMenuBar menuBar;

    // PANEL
    // PANEL
    public class Graph_panel extends JPanel {
        HashMap<Integer, Geo_Location> Point2d = new HashMap<>();
        HashMap<Geo_Location, HashMap<Geo_Location, Edge_Data>> niber = new HashMap<>();
        JTextField text;
        JLabel l;

        public Graph_panel() {
            Iterator iter = graph.nodeIter();
            while (iter.hasNext()) {
                Node_Data node = (Node_Data) iter.next();
                Point2d.put(node.getKey(),(Geo_Location)node.getLocation());
                Iterator iter1 = graph.edgeIter(node.getKey());
                while (iter1 != null && iter1.hasNext()) {
                    Edge_Data edge = (Edge_Data) iter1.next();
                    Node_Data node1 = (Node_Data) graph.getNode(edge.getDest());
                    if (niber.get(node.getLocation()) == null) {
                        HashMap<Geo_Location, Edge_Data> init = new HashMap<Geo_Location, Edge_Data>();
                        niber.put((Geo_Location) node.getLocation(), init);
                        niber.get((Geo_Location) node.getLocation()).put((Geo_Location) node1.getLocation(), edge);
                    } else {
                        niber.get((Geo_Location) node.getLocation()).put((Geo_Location) node1.getLocation(), edge);
                    }
                }
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int i=1;
            Set sets=Point2d.keySet();
            Iterator iter = sets.iterator();
            while(iter.hasNext()){
                int id = (int)iter.next();
                Geo_Location p = Point2d.get(id);
                g.setColor(Color.black);
                g.fillOval((int) p.x() -5, (int) p.y() -5, 10, 10);
                g.drawString("node_id -"+id+"",(int)p.x() -5 , (int) p.y() -10);

            }
            Set set = niber.keySet();
            Iterator iter3 = set.iterator();
            while (iter3.hasNext()) {
                Geo_Location src = (Geo_Location) iter3.next();
                Iterator iter4 = niber.get(src).keySet().iterator();
                while (iter4.hasNext()) {
                    Geo_Location dest = (Geo_Location) iter4.next();
                    Edge_Data edgeData = niber.get(src).get(dest);
                    double weight =(double)Math.round(edgeData.getWeight() * 100d) / 100d;
                    g.drawLine((int) src.x(), (int) src.y(), (int) dest.x(), (int) dest.y());
                    Geo_Location midlle = new Geo_Location((src.x()+dest.x())/2,(src.y()+dest.y())/2,0);
                    g.setColor(Color.red);
                    g.drawString(weight+"",(int)midlle.x(),(int)midlle.y());
                    g.setColor(Color.black);
                    Geo_Location inmid = new Geo_Location(dest.x()- src.x(), dest.y()-src.y(), 0);
                    Geo_Location MID = new Geo_Location(src.x()+0.9*inmid.x(),src.y()+0.9*inmid.y(),0);
                    g.setColor(Color.blue);
                    // g.fillOval((int) MID.x()-3 , (int) MID.y()-3, 6, 6);
                    int dx = (int)(dest.x()-src.x());
                    int dy = (int)(dest.y()-src.y());
                    double d = Math.sqrt(dx*dx+ dy*dy);
                    double xm = d-6;
                    double xn = xm;
                    double ym = 6;
                    double yn = -6,x;
                    double sin = dy/d,cos=dx/d;
                    x = xm*cos - ym*sin + src.x();
                    ym = xm*sin + ym*cos + src.y();
                    xm = x;
                    x = xn*cos - yn*sin + src.x();
                    yn = xn*sin + yn*cos + src.y();
                    xn = x;
                    int[] xpoints = {(int)dest.x(), (int) xm, (int) xn};
                    int[] ypoints = {(int)dest.y(), (int) ym, (int) yn};
                    g.fillPolygon(xpoints,ypoints,3);
                    g.setColor(Color.black);
                }
            }
        }
    }



    public Frame() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.getContentPane().setBackground(new Color(255, 255, 255, 255));
        this.setSize(screenSize.width/2 , screenSize.height/2 );
        //this.setLayout(new GridLayout());

        this.algo = new D_W_Graph_Algo();
        this.graph = new D_W_Graph();
        algo.init(graph);



        // MENU
        this.menuBar = new JMenuBar();

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

        menuBar.add(fileMenu);
        menuBar.add(runMenu);

        this.setJMenuBar(this.menuBar);
        this.setVisible(true);
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
                this.graph = (D_W_Graph) algo.getGraph();
                System.out.println(this.graph.nodeSize());
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
            algo.save(textField.getText());
            this.remove(button);
            this.remove(textField);
        }
        if (e.getSource() == this.edit) {
            System.out.println("edit has been clicked");
        }
        // Run menu
        if (e.getSource() == this.show_grpah) {
            System.out.println("show has been clicked");
            this.scaleGraph();
            this.fetch();
        }
        if (e.getSource() == this.show_center) {

        }
        if (e.getSource() == this.show_shortest_path) {

        }
    }

    public void fetch() {
        this.add(new Graph_panel());
    }

    public double maxX() {
        double max = Integer.MIN_VALUE;
        Iterator iter = this.graph.nodeIter();
        while (iter.hasNext()) {
            Node_Data node = (Node_Data) iter.next();
            if (node.getLocation().x() > max) {
                max = node.getLocation().x();
            }
        }
        return max;
    }

    public double minX() {
        double min = Integer.MAX_VALUE;
        Iterator iter = this.graph.nodeIter();
        while (iter.hasNext()) {
            Node_Data node = (Node_Data) iter.next();
            if (node.getLocation().x() < min) {
                min = node.getLocation().x();
            }
        }
        return min;
    }

    public double maxY() {
        double max = Integer.MIN_VALUE;
        Iterator iter = this.graph.nodeIter();
        while (iter.hasNext()) {
            Node_Data node = (Node_Data) iter.next();
            if (node.getLocation().y() > max) {
                max = node.getLocation().y();
            }
        }
        return max;
    }

    public double minY() {
        double min = Integer.MAX_VALUE;
        Iterator iter = this.graph.nodeIter();
        while (iter.hasNext()) {
            Node_Data node = (Node_Data) iter.next();
            if (node.getLocation().y() < min) {
                min = node.getLocation().y();
            }
        }
        return min;
    }

    public void addNode(double x, double y) {
        checkKeys(this.graph.nodeIter());
        Geo_Location p;
        Node_Data node;
        p = new Geo_Location(x, y, 0);
        node = new Node_Data(p);
        this.graph.addNode(node);
    }

    public void add_edge(int src, int dest, double weight) {
        this.graph.connect(src, dest, weight);
    }

    public void checkKeys(Iterator<NodeData> temp) {
        int key = Integer.MIN_VALUE;
        while (temp.hasNext()) {
            Node_Data node = (Node_Data) temp.next();
            if (node.getKey() > key) {
                key = node.getKey();
            }
        }
        if (key == Integer.MIN_VALUE) key = 0;
        Node_Data.key_track = ++key;
    }

    public void scaleGraph() {
        double maxx = (double) this.maxX();
        double minx = (double) this.minX();
        double maxy = (double) this.maxY();
        double miny = (double) this.minY();
        Iterator iter = this.graph.nodeIter();
        while (iter.hasNext()) {
            Node_Data node = (Node_Data) iter.next();
            Geo_Location g = (Geo_Location) node.getLocation();
            double yss = (double) g.y();
            double newX = (double) (this.getWidth()) * ((double) g.x() - minx) / (maxx - minx) + 30;
            double newY = (double) (this.getHeight()) * (maxy - (double)g.y()) / (maxy - miny) + 30;
            Geo_Location gN = new Geo_Location(newX, newY, 0);
            node.setLocation(gN);
        }
    }

    public static void main(String[] args) {
        Frame frame = new Frame();
        // load C:\Users\matan\IdeaProjects\Graphs_Structure-Algorithms-\data\G1.json
        frame.scaleGraph();
        frame.fetch();
    }
}