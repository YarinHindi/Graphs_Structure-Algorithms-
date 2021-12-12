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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.security.PublicKey;
import java.util.*;
import java.util.List;


public class GUI implements ActionListener {
    public D_W_Graph graph;
    public D_W_Graph_Algo algo;
    private int modecount;
    private JFrame frame;
    private Mypanel panel;
    private String path;
    private boolean first = true;
    private boolean center_paint = false;
    private Geo_Location center;

    private JMenu fileMenu;
    private JMenu editMenu;
    private JMenu runMenu;

    private JMenuItem load;
    private JMenuItem save;

    private JMenuItem deleteNode;
    private JMenuItem deleteEdge;
    private JMenuItem addNode;
    private JMenuItem addEdge;

    private JMenuItem show_grpah;
    private JMenuItem show_center;
    private JMenuItem show_shortest_path;
    private JMenuItem show_tsp;

    private JMenuBar menuBar;

    // to write file name that you want to create
    private JButton button;
    private JTextField textField;

    // to choose two node keys that you wish to perform a  shortest path algorithm
    private JButton but;
    private JTextField src;
    private JTextField dest;

    // to choose a node key that we want to remove
    private JButton but1;
    private JTextField node_key;

    // to choose a src and dest so that the edge between them will be removed
    private JButton but2;
    private JTextField src1;
    private JTextField dest1;

    // to choose a src and dest so that the program will add an edge between them
    private JButton but3;
    private JTextField src3;
    private JTextField dest3;
    private JTextField weight;

    // when adding a new node to the graph, we need to know the x and y coordinates
    private JButton but4;
    private JTextField x_coor;
    private JTextField y_coor;

    // to choose nodes key to find tsp;
    private JButton but5;
    private JTextField nodes_key;

    public class Mypanel extends JPanel {
        HashMap<Integer,Geo_Location> Point2d = new HashMap<>();
        HashMap<Geo_Location, HashMap<Geo_Location, Edge_Data>> niber = new HashMap<>();
        JTextField text;
        JLabel l;

        public Mypanel() {

            // going through all of the graph nodes
            // and finding the geographoc location for each one of them
            Iterator iter = graph.nodeIter();
            while (iter.hasNext()) {
                Node_Data node = (Node_Data) iter.next();
                Geo_Location scale = scaleNode(node);
                Point2d.put(node.getKey(),scale);
                Iterator iter1 = graph.edgeIter(node.getKey());
                while (iter1 != null && iter1.hasNext()) {
                    Edge_Data edge = (Edge_Data) iter1.next();
                    Node_Data node1 = (Node_Data) graph.getNode(edge.getDest());
                    Geo_Location scale2 = scaleNode(node1);
                    if (niber.get(scale) == null) {
                        HashMap<Geo_Location, Edge_Data> init = new HashMap<Geo_Location, Edge_Data>();
                        niber.put(scale, init);
                        niber.get(scale).put(scale2, edge);
                    } else {
                        niber.get(scale).put(scale2, edge);
                    }
                }
            }
        }
        // drawing the graph
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
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
                    if(edgeData.getTag()==1){
                        g.setColor(Color.red);
                        g.drawLine((int) src.x(), (int) src.y(), (int) dest.x(), (int) dest.y());
                    }
                    else if(edgeData.getTag()==-1) {
                        g.setColor(Color.orange);
                        g.drawLine((int) src.x(), (int) src.y(), (int) dest.x(), (int) dest.y());
                    }
                    else {
                        g.setColor(Color.black);
                        g.drawLine((int) src.x(), (int) src.y(), (int) dest.x(), (int) dest.y());
                    }
                    g.setColor(Color.red);
                    Geo_Location inmid = new Geo_Location(dest.x()- src.x(), dest.y()-src.y(), 0);
                    Geo_Location MID = new Geo_Location(src.x()+0.7*inmid.x(),src.y()+0.7*inmid.y(),0);
                    g.setColor(Color.blue);
                    // drawing the arrow that we will know the edge direction
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
            // if we want to draw the center of the graph
            if (center_paint) {
                g.setColor(Color.green);
                g.fillOval((int) center.x() -5, (int) center.y() -5, 15, 15);
            }
        }
    }

    public GUI() {
        this.graph = new D_W_Graph();
        this.algo = new D_W_Graph_Algo();
        this.algo.init(this.graph);
        this.frame = new JFrame();
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.frame.getContentPane().setBackground(new Color(255, 255, 255, 255));
        this.frame.setSize(screenSize.width , screenSize.height);

        this.menuBar = new JMenuBar();

        this.fileMenu = new JMenu("file");
        this.editMenu = new JMenu("edit");
        this.runMenu = new JMenu("run");

        this.load = new JMenuItem("load");
        this.save = new JMenuItem("save");

        this.deleteNode = new JMenuItem("remove node");
        this.deleteEdge = new JMenuItem("remove edge");
        this.addNode = new JMenuItem("add node");
        this.addEdge = new JMenuItem("add edge");

        this.show_grpah = new JMenuItem("show original graph");
        this.show_center = new JMenuItem("show center");
        this.show_shortest_path = new JMenuItem("show shortest path");
        this.show_tsp = new JMenuItem("show tsp");

        this.load.addActionListener(this);
        this.save.addActionListener(this);

        this.deleteNode.addActionListener(this);
        this.deleteEdge.addActionListener(this);
        this.addNode.addActionListener(this);
        this.addEdge.addActionListener(this);

        this.show_grpah.addActionListener(this);
        this.show_center.addActionListener(this);
        this.show_shortest_path.addActionListener(this);
        this.show_tsp.addActionListener(this);

        this.fileMenu.add(this.load);
        this.fileMenu.add(this.save);

        this.editMenu.add(this.deleteNode);
        this.editMenu.add(this.deleteEdge);
        this.editMenu.add(this.addNode);
        this.editMenu.add(this.addEdge);

        this.runMenu.add(this.show_grpah);
        this.runMenu.add(this.show_center);
        this.runMenu.add(this.show_shortest_path);
        this.runMenu.add(this.show_tsp);

        this.menuBar.add(this.fileMenu);
        this.menuBar.add(this.editMenu);
        this.menuBar.add(this.runMenu);

        this.frame.setJMenuBar(this.menuBar);
        this.frame.setVisible(true);
        this.frame.setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // loading a graph through json file
        if (e.getSource() == this.load) {
            System.out.println("load has been clicked");
            JFileChooser fileChooser = new JFileChooser();
            int response = fileChooser.showOpenDialog(null);
            if (response == JFileChooser.APPROVE_OPTION) {
                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                String file_name = String.valueOf(file);
                this.path = file_name;
                System.out.println(this.path);
                if (!this.first) {
                    this.frame.remove(this.panel);
                    this.center_paint = false;
                }
                this.algo.load(file_name);
                this.graph = (D_W_Graph) this.algo.getGraph();
                System.out.println(this.graph.nodeSize());
                if (this.graph != null) {
                    this.fetch();
                    this.frame.setVisible(true);
                }
            }
            this.first = false;
        }
        // saving the current graph into a file (creating new file)
        if (e.getSource() == this.save) {
            System.out.println("save has been clicked");
            button = new JButton("submit");
            button.addActionListener(this);
            this.button.setBounds(210, 0, 80,30);
            textField = new JTextField();
            //textField.setSize(new Dimension(200, 30));
            textField.setBounds(0, 0, 210, 30);
            textField.setText("Enter file name that you want to create");
            this.frame.add(textField);
            this.frame.add(button);
        }
        if (e.getSource() == button) {
            System.out.println("submit has been clicked");
            this.algo.save(textField.getText());
            this.frame.remove(button);
            this.frame.remove(textField);
            this.frame.remove(this.panel);
            this.fetch();
            this.frame.setVisible(true);
        }
        // deleting a node from the graph
        if (e.getSource() == this.deleteNode) {
            System.out.println("delete node has been clicked");
            this.but1 = new JButton("remove");
            this.but1.addActionListener(this);
            this.but1.setBounds(210, 0, 80,30);

            this.node_key = new JTextField();
            this.node_key.setText("enter node key");
            this.node_key.setBounds(0, 0, 210, 30);

            this.frame.add(this.node_key);
            this.frame.add(this.but1);
        }
        if (e.getSource() == this.but1) {
            int removed_node = Integer.parseInt(this.node_key.getText());
            this.frame.remove(this.but1);
            this.frame.remove(this.node_key);
            this.graph.removeNode(removed_node);
            this.frame.remove(this.panel);
            this.fetch();
            this.frame.setVisible(true);
        }
        // deleting an edge from the graph
        if (e.getSource() == this.deleteEdge) {
            this.but2 = new JButton("remove");
            this.but2.setBounds(420, 0, 80,30);
            this.but2.addActionListener(this);
            this.src1 = new JTextField();
            this.src1.setBounds(0, 0, 210,30);
            this.src1.setText("enter source key");
            this.dest1 = new JTextField();
            this.dest1.setBounds(210, 0, 210,30);
            this.dest1.setText("enter dest key");
            this.frame.add(this.src1);
            this.frame.add(this.dest1);
            this.frame.add(this.but2);
        }
        if (e.getSource() == this.but2) {
            int src = Integer.parseInt(this.src1.getText());
            int dest = Integer.parseInt(this.dest1.getText());
            this.frame.remove(this.but2);
            this.frame.remove(this.src1);
            this.frame.remove(this.dest1);
            this.graph.removeEdge(src, dest);
            this.frame.remove(this.panel);
            this.fetch();
            this.frame.setVisible(true);
        }
        // adding an edge to the graph
        if (e.getSource() == this.addEdge) {
            this.but3 = new JButton("connect");
            this.but3.setBounds(630, 0, 100,30);
            this.but3.addActionListener(this);
            this.src3 = new JTextField();
            this.src3.setBounds(0, 0, 210,30);
            this.src3.setText("enter source key");
            this.dest3 = new JTextField();
            this.dest3.setBounds(210, 0, 210,30);
            this.dest3.setText("enter dest key");
            this.weight = new JTextField();
            this.weight.setBounds(420, 0, 210,30);
            this.weight.setText("enter weight");
            this.frame.add(this.src3);
            this.frame.add(this.dest3);
            this.frame.add(this.weight);
            this.frame.add(this.but3);
        }
        if (e.getSource() == this.but3) {
            int src = Integer.parseInt(this.src3.getText());
            int dest = Integer.parseInt(this.dest3.getText());
            double edge_weight = Double.parseDouble(this.weight.getText());
            this.frame.remove(this.but3);
            this.frame.remove(this.src3);
            this.frame.remove(this.dest3);
            this.frame.remove(this.weight);
            this.graph.connect(src, dest, edge_weight);
            this.frame.remove(this.panel);
            this.fetch();
            this.frame.setVisible(true);
        }
        // adding a node to the graph
        if (e.getSource() == this.addNode) {
            this.but4 = new JButton("add");
            this.but4.setBounds(420, 0, 80,30);
            this.but4.addActionListener(this);
            this.x_coor = new JTextField();
            this.x_coor.setBounds(0, 0, 210,30);
            this.x_coor.setText("enter x coordinate");
            this.y_coor = new JTextField();
            this.y_coor.setBounds(210, 0, 210,30);
            this.y_coor.setText("enter y coordinate");
            this.frame.add(this.x_coor);
            this.frame.add(this.y_coor);
            this.frame.add(this.but4);
        }
        if (e.getSource() == this.but4) {
            double x = Double.parseDouble(this.x_coor.getText());
            double y = Double.parseDouble(this.y_coor.getText());
            this.frame.remove(this.but4);
            this.frame.remove(this.x_coor);
            this.frame.remove(this.y_coor);
            this.addNode(x, y);
            this.fetch();
            this.frame.setVisible(true);
        }
        // showing the original graph
        if (e.getSource() == this.show_grpah) {
            System.out.println("show has been clicked");
            Iterator iter = this.graph.edgeIter();
            while (iter.hasNext()) {
                EdgeData edge = (EdgeData) iter.next();
                edge.setTag(0);
            }
            this.center_paint = false;
            System.out.println(this.path);
            System.out.println(this.graph.nodeSize());
            this.frame.remove(this.panel);
            this.fetch();
            this.frame.setVisible(true);
        }
        // drawing the center of the graph
        if (e.getSource() == this.show_center) {
            NodeData cen = this.algo.center();
            Geo_Location coor = (Geo_Location) this.graph.getNode(cen.getKey()).getLocation();
            Geo_Location coorScaled = scaleNode(cen);
            this.center_paint = true;
            this.center = coorScaled;
            this.frame.remove(this.panel);
            this.fetch();
            this.frame.setVisible(true);
        }
        // drawing the shortest path between two given nodes
        if (e.getSource() == this.show_shortest_path) {
            Iterator iter = this.graph.edgeIter();
            this.but = new JButton("submit");
            this.but.setBounds(420, 0, 80,30);
            this.but.addActionListener(this);
            this.src = new JTextField();
            this.src.setBounds(0, 0, 210,30);
            this.src.setText("enter source key");
            this.dest = new JTextField();
            this.dest.setBounds(210, 0, 210,30);
            this.dest.setText("enter dest key");
            this.frame.add(src);
            this.frame.add(dest);
            this.frame.add(but);
        }
        if (e.getSource() == this.but) {
            int src_key = Integer.parseInt(this.src.getText());
            int dest_key = Integer.parseInt(this.dest.getText());
            this.frame.remove(but);
            this.frame.remove(src);
            this.frame.remove(dest);
            Iterator iter = this.graph.edgeIter();
            while (iter.hasNext()) {
                EdgeData edge = (EdgeData) iter.next();
                edge.setTag(0);
            }
            List<NodeData> nodeDataList = this.algo.shortestPath(src_key, dest_key);
            String ch = "";
            if (nodeDataList != null) {
                for (int i = 0; i < nodeDataList.size()-1; i++) {
                    EdgeData edgeData=(EdgeData)this.graph.getEdge(nodeDataList.get(i).getKey(),nodeDataList.get(i+1).getKey());
                    EdgeData edgeData1=(EdgeData)this.graph.getEdge(nodeDataList.get(i+1).getKey(),nodeDataList.get(i).getKey());
                    edgeData.setTag(1);
                    edgeData1.setTag(1);
                }
            }
            this.frame.remove(this.panel);
            this.fetch();
            this.frame.setVisible(true);
        }
        // drawing the tsp track between given nodes
        if (e.getSource() == this.show_tsp) {
            this.but5 = new JButton("submit");
            this.but5.addActionListener(this);
            this.but5.setBounds(210, 0, 80,30);
            this.nodes_key = new JTextField();
            this.nodes_key.setText("example: 4,13,2,5,1");
            this.nodes_key.setBounds(0, 0, 210,30);
            this.frame.add(this.nodes_key);
            this.frame.add(this.but5);
        }
        if (e.getSource() == this.but5) {
            String [] str_keys = this.nodes_key.getText().split(",");
            this.frame.remove(this.but5);
            this.frame.remove(this.nodes_key);
            int [] keys = new int [str_keys.length];
            for (int i = 0; i < keys.length; i++) {
                keys[i] = Integer.parseInt(str_keys[i]);
            }
            System.out.println("keys size: "+keys.length);
            for (int i = 0; i < keys.length; i++) {
                System.out.print(keys[i]+" ");
            }
            List<NodeData> nodess = new ArrayList<>();
            for (int i = 0; i < keys.length; i++) {
                nodess.add(this.graph.getNode(keys[i]));
            }
            Iterator iter = this.graph.edgeIter();
            while (iter.hasNext()) {
                EdgeData edge = (EdgeData) iter.next();
                edge.setTag(0);
            }
            List<NodeData> nodeDataList = this.algo.tsp(nodess);
            String ch = "";
            System.out.println("size: "+nodeDataList.size());
            if (nodeDataList != null) {
                for (int i = 0; i < nodeDataList.size()-1; i++) {
                    EdgeData edgeData=(EdgeData)this.graph.getEdge(nodeDataList.get(i).getKey(),nodeDataList.get(i+1).getKey());
                    EdgeData edgeData1 =(EdgeData)this.graph.getEdge(nodeDataList.get(i+1).getKey(),nodeDataList.get(i).getKey());
                    edgeData.setTag(-1);
                    edgeData1.setTag(-1);
                }
            }
            this.frame.remove(this.panel);
            this.fetch();
            this.frame.setVisible(true);
        }
    }
    // loading and drawing a graph through json
    public void load_from_json(String json_file) {
        this.algo.load(json_file);
        this.graph = (D_W_Graph) this.algo.getGraph();
        if (this.graph != null) {
            this.fetch();
            this.frame.setVisible(true);
        }
        this.first = false;
    }
    // drawing the graph
    public void fetch() {
        this.panel = new Mypanel();
        this.frame.add(this.panel);
    }
    // calculating the max x coordinate of the graph nodes
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
    // calculating the min x coordinate of the graph nodes
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
    // calculating the max y coordinate of the graph nodes
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
    // calculating the min y coordinate of the graph nodes
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
    // the add node to the graph function
    public void addNode(double x, double y) {
        checKeys(this.graph.nodeIter());
        Geo_Location p;
        Node_Data node;
        p = new Geo_Location(x, y, 0);
        node = new Node_Data(p, this.graph.nodeSize());
        this.graph.addNode(node);
    }
    // the add node to the graph function
    public void add_edge(int src, int dest, double weight) {
        graph.connect(src, dest, weight);
    }
    // checking the keys of the graph nodes
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
    public Geo_Location scaleNode(NodeData n){
        double maxx = (double) this.maxX();
        double minx = (double) this.minX();
        double maxy = (double) this.maxY();
        double miny = (double) this.minY();
        double newX = (double) (this.frame.getWidth()-100) * ((double) n.getLocation().x() - minx) / (maxx - minx)+30;
        double newY = (double) (this.frame.getHeight()-200) * ((double) n.getLocation().y() - miny) / (maxy - miny)+30;
        Geo_Location ans = new Geo_Location(newX,newY,0);
        return ans;
    }

}