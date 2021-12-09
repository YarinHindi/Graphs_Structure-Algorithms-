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
    /**
     * this function compute a deep copy of the graph.
     */
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
    /**  This function check if graph G is strongly connected
     we going to run DFS twice first time: starting from random vertex v if there is no path from 'v' to all node
     the graph is not connected. if there is path to every node  we going to run DFS for the second time but now for
     the graph G transpose and we gonna start DFS with 'v' and if in G transpose 'v' didnt reach all node
     /it means that in the original graph G there is some vertix 'u' who dosent have a path from 'u' to 'v'
     there for the graph isnt connected if the graph pass both DFS means the graph strongly connected
     *
     */
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

    /**
     *
     * @param g The graph we are working on right now
     *          this function set all node's to 0 means they are not yet visited.
     */
    private void setTagzero(DirectedWeightedGraph g) {
        Iterator iter = g.nodeIter();
        while (iter.hasNext()) {
            NodeData node = (Node_Data) iter.next();
            int ind = node.getKey();
            g.getNode(ind).setTag(0);

        }
    }
    /**
     *
     * @param g The graph we are checking
     * @param node starting node for traversal
     *        the function doing the classic DFS
     */
    public  void DFS(DirectedWeightedGraph g, NodeData node) {
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
    /**
     * @param g The graph we want to transpose.
     *          we're changing the direction of the edge
     *          if the edge existed between two node for both way(a->b,b->) we are just swapping
     *          between the edge weight.
     *          if only one way existed between the node we're removing the edge and connect it the opposite way.
     */
    public void G_transopse(DirectedWeightedGraph g) {

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
    /**
     * This function set the value int the graph before using some function
     * the function all the tag value to zero and the weight of the Node to MAX_VALUE
     */
    public void setValue() {
        Iterator iter = this.graph.nodeIter();
        while (iter.hasNext()) {
            NodeData node = (NodeData) iter.next();
            node.setTag(0);
            node.setWeight(Double.MAX_VALUE);
        }
    }
    /**
     * @param key Starting node
     *        This function finding the fastest route from node src to all other node in graph.
     *        along the way the function are uptading the node weight which represent how much time.
     *        it takes to reach from the node src to other node at the graph in the shortest way.
     *        the function will end when we reach all node we can go meaning there is a path.
     */
        public void Dijkstra(int key){
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
    @Override
    /**
     *
     * @param src - start node
     * @param dest - end (target) node
     * @return the minimal time it take to reach from src to dest in the using Dijkstra
     */

    public double shortestPathDist(int src, int dest) {
        if (src == dest) return 0;
        if(this.graph.getNode(src)==null||this.graph.getNode(dest)==null)return -1;
        setValue();
        NodeData node = this.graph.getNode(src);
        node.setWeight(0);
        Dijkstra(src);
        double ans = this.graph.getNode(dest).getWeight();
        if (ans == Double.MAX_VALUE) {
            return -1;
        } else {
            return ans;
        }
    }

    @Override
    /**
     *
     * @param src - start node
     * @param dest - end (target) node
     * @return List of nodes representing the route between the src to dest
     * The function using shortestPathDist3 to check if there is a route between the two nodes
     * if the time isn't equal (-1) it means there is a route after the shortestPathDist are applied
     * the nodes weight are updating and to find the route we are transpose the direction of the
     * edge in the graph, and then we're checking which node that is neighbour to dest node are the node
     * that takes us to dest we doing it by checking the (weight node + the edge weight) and if it equals
     * too dest node weight we are adding it to our List and we now that this node is the parent of
     * dest, and then we keep doing it until we reach dest.
     * after we finish we are reversing the List, and then we will get final answer.
     *
     *
     */
    public List<NodeData> shortestPath(int src, int dest) {
        List<NodeData> nodePathReavrse = new ArrayList<>();
        double time = this.shortestPathDist(src, dest);
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
    @Override
    /**
     *
     * @return The node center
     * This function finding the center in the graph first we need to check if
     * the graph is connected if not we return null
     * after we check it we are going to call to shortestPathDist for each node
     * and then the weight going to be updated for all node we take the eccentricity which is the longest
     * distance between this node to other node
     * after we check all node we are going to choose the node with the smaller eccentricity and this node going to
     * be the center is graph.
     */
    public NodeData center() {
        if(this.isConnected()==false||this.graph.nodeSize()==0) return null;
        double min = Double.MAX_VALUE;
        int ind =7;
        for (int i = 0; i < this.graph.nodeSize(); i++) {
            Dijkstra(i);
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
    /**
     * This function return list of NodeData
     * the function gets a list of nodes that we are asking to travel somehow in the graph.
     * the function need to return a list of nodes which represent a path that are visiting
     * in all the node list we get and we need to find the path with the minimal time
     * if there is now path from nodes in the list we are getting we gonna return null
     * the function use dijkstra to hopefully find the shortest path
     * this is a greedy algorithm and the solution will not be always the best solution.
     */
    public List<NodeData> tsp(List<NodeData> cities) {
        if(cities.size() == 0) {
            return null;
        }
        List<NodeData> ans = new ArrayList<NodeData>();
        int start = Integer.MAX_VALUE;
        double min = Integer.MAX_VALUE;
        ArrayList<Integer> right_track = new ArrayList<>();
        for (int i = 0; i < cities.size(); i++) {
            ArrayList<Integer> track = new ArrayList<>();
            int count = 1;
            int index = cities.get(i).getKey();
            track.add(index);
            while (count < cities.size()) {
                index = rec(index, track, cities);
                if (index == -1) {
                    break;
                }
                track.add(index);
                count++;
            }
            if (index != -1) {
                double weight = 0;
                for (int j = 0; j < track.size()-1; j++) {
                    weight += shortestPathDist(track.get(j), track.get(j+1));
                }
                System.out.println("wi: "+weight);
                if (weight < min) {
                    right_track.clear();
                    min = weight;
                    start = i;
                    System.out.println("track added to right track");
                    for (int j = 0; j < track.size(); j++) {
                        right_track.add(track.get(j));
                        System.out.print(track.get(j)+",");
                    }
                    System.out.println();
                }
            }
        }
        System.out.println("right track");
        for (int i = 0; i < right_track.size(); i++) {
            System.out.print(right_track.get(i)+" ");
        }
        System.out.println();
        for (int i = 0; i < right_track.size()-1; i++) {
            List<NodeData> l = shortestPath(right_track.get(i), right_track.get(i+1));
            for (int j = 0; j < l.size(); j++) {
                System.out.print(l.get(j).getKey()+" ");
            }
            System.out.println();
            if (i == 0) {
                for (int j = 0; j < l.size(); j++) {
                    ans.add(l.get(j));
                }
            }
            else {
                for (int j = 1; j < l.size(); j++) {
                    ans.add(l.get(j));
                }
            }
        }
        if (ans.size() == 0) {
            return null;
        }
        return ans;
    }

    public int rec(int key, ArrayList<Integer> track, List<NodeData> cities) {
        Dijkstra(key);
        //PriorityQueue<NodeData> nodeData = new PriorityQueue<>();
        int next_key = -1;
        double min = Integer.MAX_VALUE;
        for (int i = 0; i < cities.size(); i++) {
            if (!track.contains(cities.get(i).getKey())) {
                double len = this.graph.getNode(cities.get(i).getKey()).getWeight();
                if (len < min) {
                    min = len;
                    next_key = cities.get(i).getKey();
                }
            }
        }
        return next_key;
    }
    /**
     *
     * @param file - the file name (may include a relative path).
     * @return true if the graph has been saved to the file
     * false if something went worng.
     */
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
    /**
     *
     * @param file - file name of JSON file
     * @return  true if we succeeded to load the graph from the json file to our graph.
     */
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