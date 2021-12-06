package Garph_GUI;
import api.DirectedWeightedGraph;
import data_Structure.Edge_Data;
import data_Structure.Geo_Location;
import data_Structure.Node_Data;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class Graph_panel extends JPanel {
    HashMap<Integer, Geo_Location> Point2d = new HashMap<>();
    HashMap<Geo_Location, HashMap<Geo_Location, Edge_Data>> niber = new HashMap<>();
    JTextField text;
    JLabel l;

    public Graph_panel(DirectedWeightedGraph graph) {
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

