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

    public DirectedWeightedGraph graph;
    public D_W_Graph_Algo algo;
    private int modecount;
    private JFrame frame;

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
  //      this.frame.add(new Mypanel());

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
            double newX = (double) (this.frame.getWidth()+100) * ((double) g.x() - minx) / (maxx - minx)+30;
            double newY = (double) (this.frame.getHeight()+100) * (maxy - (double) g.y()) / (maxy - miny)+30;
            Geo_Location gN = new Geo_Location(newX, newY, 0);
            node.setLocation(gN);
        }
    }
        public  void loadgraphjson (String file) {
            this.algo.init(this.graph);
            this.algo.load("C:\\Users\\yarin\\Desktop\\G1.json");
//            this.algo.load(file);
            this.graph = algo.getGraph();
            this.scaleGarph();
        }


        public static void main (String[]args){
            GUI g = new GUI();
              g.loadgraphjson("s");
            System.out.println(g.graph.edgeSize());
//            g.addNode(2000, 1500);
//            g.addNode(3000, 1200);
//            g.addNode(4000, 1100);
//            g.addNode(5000, 1300);
//            g.addNode(500,100);
//            g.addNode(600,200);
//            g.add_edge(1, 2, 30);
//            g.add_edge(2, 3, 10);
//            g.add_edge(1, 3, 3);
//            g.add_edge(1,4,20);
//            g.add_edge(6,1,1.65);
//            g.add_edge(5,6,2.33);
            g.scaleGarph();
            g.fetch();

        }

    }

