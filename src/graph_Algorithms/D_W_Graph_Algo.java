package graph_Algorithms;

import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;
import api.EdgeData;
import api.NodeData;
import com.google.gson.Gson;
import com.google.gson.*;
import data_Structure.*;

import javax.swing.text.html.HTMLDocument;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;

import java.util.*;

public class D_W_Graph_Algo implements DirectedWeightedGraphAlgorithms {
    private DirectedWeightedGraph graph;



    public D_W_Graph_Algo() {
        this.graph = new D_W_Graph();
    }

    public D_W_Graph_Algo(DirectedWeightedGraph other) {
        init(other);
    }

    @Override
    public void init(DirectedWeightedGraph g) {
        this.graph = g;

    }

    @Override
    public DirectedWeightedGraph getGraph() {
        return this.graph;
    }

    @Override
    public DirectedWeightedGraph copy() {
        DirectedWeightedGraph deepCopy = new D_W_Graph();
        Iterator iter = this.graph.nodeIter();
        Iterator edge_iter = this.graph.edgeIter();
        while (iter.hasNext()) {
            NodeData node = new Node_Data((Node_Data) iter.next());
            deepCopy.addNode(node);
        }
        while (edge_iter.hasNext()) {
            EdgeData edge = new Edge_Data((Edge_Data) edge_iter.next());
            deepCopy.connect(edge.getSrc(), edge.getDest(), edge.getWeight());
        }
        return deepCopy;

    }

    @Override
    //check if graph G is strongly connected
    //we going to run DFS twice first time: starting from random vertex v if there is no path from 'v' to all node
    // the graph is not connected. if there is path to every node  we going to run DFS for the second time but now for
    // the graph G transpose and we gonna start DFS with 'v' and if in G transpose 'v' didnt reach all node
    ///it means that in the original graph G there is some vertix 'u' who dosent have a path from 'u' to 'v'
    //there for the graph isnt connected if the graph pass both DFS means the graph strongly connected
    public boolean isConnected() {
        if (this.graph.nodeSize() == 1 || this.graph.nodeSize() == 0) return true;
        DirectedWeightedGraph copy = this.copy();
        setTagzero(copy);
        Iterator iter = copy.nodeIter();
        NodeData node = (NodeData) iter.next();
        DFS(copy, node);
        while (iter.hasNext()) {
            if (((NodeData) iter.next()).getTag() == 0) return false;
        }
        G_transopse(copy);
        setTagzero(copy);
        Iterator iter1 = copy.nodeIter();
        NodeData node1 = (NodeData) iter1.next();
        DFS(copy, node1);
        while (iter1.hasNext()) {
            if (((NodeData) iter1.next()).getTag() == 0) return false;
        }
        return true;


    }

    private void setTagzero(DirectedWeightedGraph g) {
        Iterator iter = g.nodeIter();
        while (iter.hasNext()) {
            NodeData node = (Node_Data) iter.next();
            int ind = node.getKey();
            g.getNode(ind).setTag(0);

        }
    }

    public static int BFS(DirectedWeightedGraph g, NodeData node) {
        int ans = 0;
        LinkedList<Integer> queue = new LinkedList<Integer>();
        node.setTag(1);
        queue.add(node.getKey());
        while (queue.size() != 0) {
            int curr = queue.poll();
            Iterator iter = g.edgeIter(curr);
            while (iter.hasNext()) {
                EdgeData edge = (Edge_Data) iter.next();
                int u = edge.getDest();
                if (g.getNode(u) != null && g.getNode(u).getTag() == 0) {
                    g.getNode(u).setTag(1);
                    queue.add(g.getNode(u).getKey());
                    ans = g.getNode(u).getKey();
                }
            }
        }
        return ans;
    }

    public static void DFS(DirectedWeightedGraph g, NodeData node) {
        node.setTag(1);
        Iterator iter = g.edgeIter(node.getKey());
        if (iter != null) {
            while (iter.hasNext()) {
                EdgeData edge = (Edge_Data) iter.next();
                int u = edge.getDest();
                if (g.getNode(u) != null && g.getNode(u).getTag() == 0) {
                    DFS(g, g.getNode(u));
                }

            }
        }
    }

    private void G_transopse(DirectedWeightedGraph g) {

        Iterator iter = g.edgeIter();
        while (iter.hasNext()) {
            EdgeData e = (Edge_Data) iter.next();
            int tempsrc = e.getSrc();
            int tempdest = e.getDest();
            double tempweight = e.getWeight();

            Edge_Data edgeData1 = (Edge_Data) g.getEdge(tempsrc, tempdest);
            Edge_Data edgeData2 = (Edge_Data) g.getEdge(tempdest, tempsrc);

            if (edgeData1 != null && edgeData2 != null && edgeData1.getTag() != 1 && edgeData2.getTag() != 1) {
                double temp = edgeData1.getWeight();
                edgeData1.setW(edgeData2.getWeight());
                edgeData2.setW(temp);
                edgeData1.setTag(1);
                edgeData2.setTag(1);
            } else if (edgeData1.getTag() != 1 && edgeData2 == null) {
                g.removeEdge(tempsrc, tempdest);
                g.connect(tempdest, tempsrc, tempweight);

            }
        }
    }

    public void setValue() {
        Iterator iter = this.graph.nodeIter();
        while (iter.hasNext()) {
            NodeData node = (NodeData) iter.next();
            node.setTag(0);
            node.setWeight(Double.MAX_VALUE);
        }
    }

    public void DijkstraForShortestPath(Node_Data src, Node_Data dest) {
        PriorityQueue<NodeData> pq = new PriorityQueue<>();
        pq.add(src);
        while (pq.size() != 0) {
            Node_Data node = (Node_Data) pq.poll();
            this.graph.getNode(node.getKey()).setTag(1);
            if (node.getKey() == dest.getKey()) {
                return;
            }
            Iterator iter = this.graph.edgeIter(node.getKey());
            while (iter.hasNext()) {
                EdgeData edge = (EdgeData) iter.next();
                double weight_check = node.getWeight() + edge.getWeight();
                if (this.graph.getNode(edge.getDest()).getWeight() == Double.MAX_VALUE) {
                    pq.add(this.graph.getNode(edge.getDest()));
                }
                if (this.graph.getNode(edge.getDest()).getWeight() > weight_check) {
                    this.graph.getNode(edge.getDest()).setWeight(weight_check);
                }
            }
        }
    }
        public void DijkstraForCenter(int key){
        PriorityQueue<NodeData> PQ = new PriorityQueue<>();
        //Update the weight of each node to infinity and update the color(info) of all the nodes to white.
        //White node - means we have not visited it yet.
        setValue();
        this.graph.getNode(key).setWeight(0);
        //weights.get(key).setDistance(0);
        PQ.add(this.graph.getNode(key));
        //A loop that goes through all the nodes in the graph.
        while (PQ.size() != 0) {
            NodeData u = PQ.poll();
            //Black node - means we have updated the minimum weight of the node
            if (!(this.graph.getNode(u.getKey()).getTag()==1)) {
                Iterator iter2 = this.graph.edgeIter(u.getKey());
                while (iter2.hasNext()) {
                    EdgeData ni = (EdgeData) iter2.next();
                    if (!(this.graph.getNode(ni.getDest()).getTag()==1)) {
                        double t = ni.getWeight() + this.graph.getNode(u.getKey()).getWeight();
                        //Update the min weight of the neighbors of node u.
                        if (this.graph.getNode(ni.getDest()).getWeight() > t) {
                            this.graph.getNode(ni.getDest()).setWeight(t);
                            //Update the parent node of edge ni.
                            //parents.put(ni.getDest(), u.getK());
                        }
                        PQ.add(this.graph.getNode(ni.getDest()));
                    }
                }
            }
            this.graph.getNode(u.getKey()).setTag(1);
        }
    }

    public double shortestPathDist(int src, int dest) {
        if (this.graph.getNode(src) != null && this.graph.getNode(dest) != null){
            //The weight of the path of a node to itself is 0.
            if (src == dest){
                return 0;
            }
            //setValue();
            NodeData node = this.graph.getNode(src);
            node.setWeight(0);
            DijkstraForShortestPath((Node_Data) this.graph.getNode(src),(Node_Data) this.graph.getNode(dest));
            //If the weight of dest different from infinitely,
            // we will return the weight of the path from the node from which we scanned the graph(src) to dest
            if (this.graph.getNode(dest).getWeight() != Double.MAX_VALUE) {
                return this.graph.getNode(dest).getWeight();
            }
            else {
                return -1;
            }
        }
        else {
            return -1;
        }
    }
    public double shortestPathDist3(int src, int dest) {
        if (src == dest) return 0;
        setValue();
        NodeData node = this.graph.getNode(src);
        node.setWeight(0);
        DijkstraForCenter(src);
        //Dijkstra2(src);
        double ans = this.graph.getNode(dest).getWeight();
        if (ans == Double.MAX_VALUE) {
            return -1;
        } else {
            return ans;
        }
    }

    private void Dijkstra(NodeData src, NodeData dest) {
        if (src.getKey() == dest.getKey() && src.getTag() == 1) return;
        Iterator iter = this.graph.edgeIter(src.getKey());
        if (iter != null) {
            while (iter.hasNext()) {
                EdgeData edge = (Edge_Data) iter.next();
                if (this.graph.getNode(edge.getDest()).getWeight() > this.graph.getNode(edge.getSrc()).getWeight() + edge.getWeight()) {
                    this.graph.getNode(edge.getDest()).setWeight(this.graph.getNode(edge.getSrc()).getWeight() + edge.getWeight());
                    src.setTag(1);
                    Dijkstra(this.graph.getNode(edge.getDest()), dest);
                }
            }

        }
    }
    @Override
    public List<NodeData> shortestPath(int src, int dest) {
        List<NodeData> nodePathReavrse = new ArrayList<>();
        double time = this.shortestPathDist3(src, dest);
        if (time == -1) {
            return null;
        }
        DirectedWeightedGraph copied = this.copy();
        G_transopse(copied);
        NodeData firstInGTranspose = copied.getNode(dest);
        nodePathReavrse.add(firstInGTranspose);
        while (firstInGTranspose.getKey() != src) {
            Iterator iter = copied.edgeIter(firstInGTranspose.getKey());
            double nodeWeighte = firstInGTranspose.getWeight();
            if (iter != null) {
                while (iter.hasNext()) {
                    EdgeData edge = (Edge_Data) iter.next();
                    double t = copied.getNode(edge.getDest()).getWeight();
                    double t2 = edge.getWeight();
                    double val =copied.getNode(edge.getDest()).getWeight() + edge.getWeight();
                    if (val == nodeWeighte) {
                        firstInGTranspose = copied.getNode(edge.getDest());
                    }
                }
            }
            nodePathReavrse.add(firstInGTranspose);
        }
        List<NodeData> nodePath = new ArrayList<>();
        for (int i = nodePathReavrse.size() - 1; i >= 0; i--) {
            nodePath.add(nodePathReavrse.get(i));
        }
        return nodePath;
    }

    public NodeData center() {
        if(this.isConnected()==false||this.graph.nodeSize()==0) return null;
        double min = Double.MAX_VALUE;
        int ind =7;
        for (int i = 0; i < this.graph.nodeSize(); i++) {
            DijkstraForCenter(i);
            double max = Double.MIN_VALUE;
            for (int j = 0; j < this.graph.nodeSize(); j++) {

                double len = this.graph.getNode(j).getWeight();
                if (max < len) {
                    max = len;
                }
            }
            if (min > max) {
                min = max;
                ind = i;
            }
        }
        return this.graph.getNode(ind);


    }

    @Override
    //need to check why always return null
    public List<NodeData> tsp(List<NodeData> cities) {
        if(cities.isEmpty())
            return null;
        List<NodeData>ans =new LinkedList<NodeData>();
        double min=Double.MAX_VALUE;
        NodeData start= cities.get(0);
        cities.remove(0);
        int curr=0;
        ans.add(start);
        while(!cities.isEmpty())
        {
            for(int i=0;i<cities.size();i++)
            {
                if(shortestPathDist(start.getKey(), cities.get(i).getKey())<min){
                    min=shortestPathDist(start.getKey(), cities.get(i).getKey());
                    curr=i;
                }
            }
            start=cities.get(curr);
            cities.remove(curr);
            min=Double.MAX_VALUE;
            ans.add(start);
        }
        return ans;
    }
    @Override
    public boolean save(String file) {
        try {

            JsonObject edgesObject = new JsonObject();

            JsonArray Edges = new JsonArray();
            JsonObject nodeObject = new JsonObject();
            JsonArray Nodes = new JsonArray();
            JsonObject AllObj = new JsonObject();
            ArrayList<NodeData> nodeArray = new ArrayList<>();
            Iterator<NodeData> nodeIter = this.graph.nodeIter();
            while (nodeIter.hasNext()) {
                nodeArray.add(nodeIter.next());
            }
            for (NodeData i : nodeArray) {
                edgesObject = new JsonObject();
                Iterator<EdgeData> edgeNode = this.graph.edgeIter(i.getKey());
                ArrayList<EdgeData> edgeArray = new ArrayList<>();
                while (edgeNode.hasNext()) {
                    edgeArray.add(edgeNode.next());
                }
                for (EdgeData e : edgeArray) {
                    edgesObject.addProperty("src", e.getSrc());
                    edgesObject.addProperty("w", e.getWeight());
                    edgesObject.addProperty("dest", e.getDest());
                    JsonObject ob = edgesObject.deepCopy();
                    Edges.add(ob);

                }
            }
            AllObj.add("Edges", Edges);

            for (NodeData i : nodeArray) {
                nodeObject = new JsonObject();
                String s = "" + i.getLocation();
                nodeObject.addProperty("pos", s);
                nodeObject.addProperty("id", i.getKey());
                Nodes.add(nodeObject);
                s = "";
            }
            AllObj.add("Nodes", Nodes);
            try {
                PrintWriter pw = new PrintWriter(file);
                pw.write(AllObj.toString());
                pw.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean load(String file) {
        try
        {
            DirectedWeightedGraph loaded = new D_W_Graph();

            JsonParser jsonParser = new JsonParser();
            FileReader reader = new FileReader(file);
            Object obj  = jsonParser.parse(reader);


            JsonObject vertex = new JsonObject();
            JsonObject edges = new JsonObject();
            JsonObject jsonObject = (JsonObject) obj;
            //      jsonObject.getAsJsonObject(jsonString);


            JsonArray vertexArray = jsonObject.getAsJsonArray("Nodes");

            for(int i=0; i<vertexArray.size(); i++)
            {
                vertex = vertexArray.get(i).getAsJsonObject();
                int n = vertex.get("id").getAsInt();
                String pos = vertex.get("pos").getAsString();
                NodeData node = new Node_Data(n);
                String [] s = pos.split(",");
                node.setLocation(new Geo_Location(Double.parseDouble(s[0]), Double.parseDouble(s[1]), Double.parseDouble(s[2])));
                loaded.addNode(node);
            }

            JsonArray edgesArray = jsonObject.getAsJsonArray("Edges");
            for(int i=0; i<edgesArray.size(); i++)
            {
                edges = edgesArray.get(i).getAsJsonObject();
                int src = edges.get("src").getAsInt();
                int dest = edges.get("dest").getAsInt();
                double w = edges.get("w").getAsDouble();
                loaded.connect(src, dest, w);
            }
            this.graph= loaded;


        }
        catch(FileNotFoundException | JsonIOException e)
        {
            e.printStackTrace();
        }
        return true;
    }
}