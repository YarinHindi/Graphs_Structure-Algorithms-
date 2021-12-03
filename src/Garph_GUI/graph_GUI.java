package Garph_GUI;
import api.EdgeData;
import api.NodeData;
import com.sun.media.jfxmedia.events.NewFrameEvent;
import data_Structure.Node_Data;
import data_Structure.Edge_Data;
import data_Structure.D_W_Graph;
import data_Structure.Geo_Location;
import graph_Algorithms.D_W_Graph_Algo;
import javax.swing.text.html.HTMLDocument;
import java.awt.*;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
public class graph_GUI extends Thread {
    public static D_W_Graph graph;
    private D_W_Graph_Algo algo;
    private int modecount;
    public int Width;
    public int Hieght;


    public graph_GUI (){
        graph= new D_W_Graph();
        algo= new D_W_Graph_Algo();
        Width=800;
        Hieght=800;
        this.openCanvas();
        modecount=graph.getMC();
        this.start();
    }
    public  double maxX(){
        double max = Integer.MIN_VALUE;
        Iterator iter = this.graph.nodeIter();
        while(iter.hasNext()){
            Node_Data node = (Node_Data)iter.next();
            if(node.getLocation().x()>max){
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
    public  double maxY(){
        double max = Integer.MIN_VALUE;
        Iterator iter = this.graph.nodeIter();
        while(iter.hasNext()){
            Node_Data node = (Node_Data)iter.next();
            if(node.getLocation().y()>max){
                max = node.getLocation().x();
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
                min = node.getLocation().x();
            }

        }
        return min;

    }

    public void checKeys(Iterator<NodeData> temp){
        int key =Integer.MIN_VALUE;
        while (temp.hasNext()) {
            Node_Data node = (Node_Data)temp.next();
            if(node.getKey()>key){
                key=node.getKey();
            }
        }
        if(key==Integer.MIN_VALUE) key =0;
        Node_Data.key_track=++key;
    }
    public void add_edge (int src,int dest,double weight) {
        graph.connect(src,dest,weight);
    }
    public  boolean isConnected(){
        algo.init(graph);
        return algo.isConnected();
    }
    public  void add_node (double x, double y) {//change key
        checKeys(graph.nodeIter());
        Geo_Location p = new Geo_Location(x,y,0);
        Node_Data n = new Node_Data(p);
        graph.addNode(n);
   //     if(x>maxX() || x<minX() || y>maxY() ||y<minY())openCanvas();

    }
    public  void remove_edge (int src,int dest) {
        graph.removeEdge(src, dest);
    }
    public void remove_node (int key) {     //change key

        graph.removeNode(key);
    }
    public Node_Data findNode(double x, double y){
        Iterator iter = graph.nodeIter();
        while (iter.hasNext()){
            Node_Data node = (Node_Data)iter.next();
            if(x>=node.getLocation().x()-0.4 && x<= node.getLocation().x()+0.4 && y>=node.getLocation().y()-0.4 && y<=node.getLocation().y()+0.4) return node;
        }
        return null;
    }
    public double ShortestPathDist(int src,int dest){
        algo.init(graph);
        return algo.shortestPathDist(src,dest);
    }
    public List<NodeData> shortestPath(int src,int dest){
        algo.init(graph);
        return algo.shortestPath(src,dest);
    }
    public void Clean(){
        graph= new D_W_Graph();
        algo.init(graph);
        this.printGraph();
    }
    public void run(){
        while(true) {
            if (modecount != graph.getMC()) {
                openCanvas();
                modecount = graph.getMC();
            }
        }
    }

    public void openCanvas(){
        StdDraw.setCanvasSize(800,800);
        StdDraw.setXscale(minX()-(minX()-maxX())*0.2,maxX()*1.1);
        StdDraw.setYscale(minY()-(minY()-maxY())*0.2,maxY()*1.1);
        printGraph();
    }
    public  void printGraph(){
        StdDraw.clear();
        double rightScaleX = ((maxX()-minX())*0.04);
        double rightScaleY =  (((maxY()-minY())*0.04));
        StdDraw.setPenColor(Color.BLUE);
        StdDraw.setPenRadius(0.15);
        D_W_Graph d = this.graph;
        if(d!=null) {
            Iterator it = d.nodeIter();
            while (it.hasNext()) {
                NodeData temp = (NodeData)it.next();
                Geo_Location p = new Geo_Location(temp.getLocation());
                StdDraw.filledCircle(p.x(), p.y(),rightScaleX*0.2);
                StdDraw.text(p.x(), p.y() +rightScaleX*0.3, "" + temp.getKey());
            }
            StdDraw.setPenColor(Color.BLACK);
            StdDraw.setPenRadius(0.003);
            Iterator it1 = d.nodeIter();
            while(it1.hasNext()){
                NodeData temp1 = (NodeData)it1.next();
                Iterator it2 = graph.edgeIter(temp1.getKey());
                if(it2!=null){
                    while(it2.hasNext()){
                        EdgeData temp2 = (EdgeData)it2.next();
                        if(temp2!=null){
                            StdDraw.setPenRadius(0.003);
                            StdDraw.setPenColor(Color.RED);
                            double weight = temp2.getWeight();
                            NodeData srcNode = d.getNode(temp2.getSrc());
                            NodeData dstNode = d.getNode(temp2.getDest());
                            Geo_Location srcP = new Geo_Location(srcNode.getLocation());
                            Geo_Location dstP = new Geo_Location(dstNode.getLocation());
                            StdDraw.line(srcP.x(), srcP.y(), dstP.x(), dstP.y());
                            double x = 0.2*srcP.x()+0.8*dstP.x();
                            double y = 0.2*srcP.y() + 0.8*dstP.y();
                            StdDraw.setPenColor(Color.BLACK);
                            StdDraw.text(x,y, "" + weight);

                            StdDraw.setPenColor(Color.YELLOW);
                            StdDraw.setPenRadius(0.15);
                            double x1 = 0.1*srcP.x()+0.9*dstP.x();
                            double y1 = 0.1*srcP.y()+0.9*dstP.y();
                            StdDraw.filledCircle(x1,y1,rightScaleX*0.2);

                        }

                    }
                }
            }

        }

    }

    public static void main(String[] args) {
      graph_GUI g = new graph_GUI();
        g.add_node(100,100);
        g.add_node(200,100);
        g.add_node(300,100);
        g.add_node(400,100);
        g.add_edge(1,2,9);
        g.add_edge(3,1,20);
        g.printGraph();


    }
}
