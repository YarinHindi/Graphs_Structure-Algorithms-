package Garph_GUI;
import api.DirectedWeightedGraph;
import api.EdgeData;
import api.NodeData;
import com.sun.media.jfxmedia.events.NewFrameEvent;
import data_Structure.Node_Data;
import data_Structure.Edge_Data;
import data_Structure.D_W_Graph;
import data_Structure.Geo_Location;
import graph_Algorithms.D_W_Graph_Algo;

import javax.swing.*;
import javax.swing.text.html.HTMLDocument;
import java.awt.*;
import java.security.PublicKey;
import java.util.*;


public class GUI {


    public class Mypanel extends JPanel {
        HashMap<Integer,Geo_Location> Point2d = new HashMap<>();
        HashMap<Geo_Location, HashMap<Geo_Location, Edge_Data>> niber = new HashMap<>();
        JTextField text;
        JLabel l;

        public Mypanel() {
            Iterator iter = graph.nodeIter();
            while (iter.hasNext()) {
                Node_Data node = (Node_Data) iter.next();
      //          points.add((Geo_Location) node.getLocation());
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
            Geo_Location gs = null;
            int i=1;
            Set sets=Point2d.keySet();
            Iterator iter = sets.iterator();
            while(iter.hasNext()){
                 int id = (int)iter.next();
                 Geo_Location p = Point2d.get(id);
                g.setColor(Color.black);
                g.fillOval((int) p.x() -5, (int) p.y() -5, 10, 10);
                g.drawString(id+"",(int)p.x() -5 , (int) p.y() -10);

            }
//            for (Geo_Location p : points) {
//                g.setColor(Color.black);
//                g.fillOval((int) p.x() - 5, (int) p.y() - 5, 10, 10);
//                g.drawString(i+"",(int)p.x() -5 , (int) p.y() -10);
//                i++;
//            }
            Set set = niber.keySet();
            Iterator iter3 = set.iterator();
            while (iter3.hasNext()) {
                Geo_Location src = (Geo_Location) iter3.next();
                Iterator iter4 = niber.get(src).keySet().iterator();
                while (iter4.hasNext()) {
                    Geo_Location dest = (Geo_Location) iter4.next();
                    Edge_Data edgeData = niber.get(src).get(dest);
                    double weight =(double)Math.round(edgeData.getWeight() * 1000d) / 1000d;
                    g.drawLine((int) src.x(), (int) src.y(), (int) dest.x(), (int) dest.y());
                    Geo_Location midlle = new Geo_Location(Math.abs(src.x()-dest.x()),Math.abs(src.y()-dest.y()),0);
                    Geo_Location realmid = new Geo_Location(src.x()+0.5*midlle.x(),src.y()+0.5*midlle.y(),0);
                    g.drawString(weight+"",(int)realmid.x(),(int)realmid.y());
                }
            }


        }

    }

    public DirectedWeightedGraph graph;
    public D_W_Graph_Algo algo;
    private int modecount;
    private JFrame frame;
    Range Xrange = new Range(0, 0);
    Range Yrange = new Range(0, 0);

    public GUI() {
        this.graph = new D_W_Graph();
        this.algo = new D_W_Graph_Algo();
        this.algo.init(this.graph);
        this.frame = new JFrame();
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.frame.getContentPane().setBackground(new Color(255, 255, 255, 255));
        this.frame.setSize(screenSize.width/2 , screenSize.height/2 );
        this.frame.setVisible(true);


    }

    public boolean isConnected() {
        this.algo.init(this.graph);
        return algo.isConnected();
    }

    public void fetch() {
        this.frame.add(new Mypanel());

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
        checKeys(this.graph.nodeIter());
        Geo_Location p;
        Node_Data node;
        p = new Geo_Location(x, y, 0);
        node = new Node_Data(p);
        this.graph.addNode(node);

    }


    public void add_edge(int src, int dest, double weight) {
        graph.connect(src, dest, weight);
    }

    //    }
    public void checKeys(Iterator<NodeData> temp) {
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

    public void scaleGarph() {
        double maxx = (double) this.maxX();
        double minx = (double) this.minX();
        double maxy = (double) this.maxY();
        double miny = (double) this.minY();
        Iterator iter = this.graph.nodeIter();
        while (iter.hasNext()) {
            Node_Data node = (Node_Data) iter.next();
            Geo_Location g = (Geo_Location) node.getLocation();
            double yss = (double) g.y();
            double newX = (double) (this.frame.getWidth()) * ((double) g.x() - minx) / (maxx - minx)+30;
            double newY = (double) (this.frame.getHeight()) * (maxy - (double) g.y()) / (maxy - miny)+30;
            Geo_Location gN = new Geo_Location(newX, newY, 0);
            node.setLocation(gN);
        }
    }
        public  void loadgraphjson () {
            this.algo.init(this.graph);
            this.algo.load("C:\\Users\\yarin\\Desktop\\G2.json");
            this.graph = algo.getGraph();
            this.scaleGarph();
        }


        public static void main (String[]args){
            GUI g = new GUI();
                g.loadgraphjson();
//            g.addNode(2000, 1500);
//            g.addNode(3000, 1200);
//            g.addNode(4000, 1100);
//            g.addNode(5000, 1300);
////        g.addNode(500,100);
////        g.addNode(600,200);
//            g.add_edge(1, 2, 30);
//            g.add_edge(2, 3, 10);
//            g.add_edge(1, 3, 3);
//            g.add_edge(1,4,20);
            g.scaleGarph();
            g.fetch();

        }

    }

