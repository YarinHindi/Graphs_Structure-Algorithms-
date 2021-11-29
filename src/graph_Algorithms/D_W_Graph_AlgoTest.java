package graph_Algorithms;

import api.NodeData;
import data_Structure.D_W_Graph;
import data_Structure.Geo_Location;
import data_Structure.Node_Data;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class D_W_Graph_AlgoTest {
    D_W_Graph_Algo algo;
    D_W_Graph graph;
    Node_Data node1 = new Node_Data(new Geo_Location(1,2,3));
    Node_Data node2 = new Node_Data(new Geo_Location(4,5,6));
    Node_Data node3 = new Node_Data(new Geo_Location(1,1,1));
    Node_Data node4 = new Node_Data(new Geo_Location(2,2,2));
    Node_Data node5 = new Node_Data(new Geo_Location(3,3,3));
    Node_Data node6 = new Node_Data(new Geo_Location(3,3,3));
    Node_Data node7 = new Node_Data(new Geo_Location(2,2,2));
    Node_Data node8 = new Node_Data(new Geo_Location(3,3,3));
    Node_Data node9 = new Node_Data(new Geo_Location(3,3,3));
    @Test
    void init() {
    }

    @Test
    void getGraph() {
    }

    @Test
    void copy() {
    }

    @Test
    void isConnected() {
        graph = new D_W_Graph();
        algo = new D_W_Graph_Algo();
        graph.addNode(node1);
        graph.addNode(node2);
        graph.addNode(node3);
        graph.connect(1,2,3);
        graph.connect(2,3,2);
        graph.connect(3,1,3);
        algo.init(graph);
        assertEquals(algo.isConnected(),true);

    }
    @Test
    void BFS() {
        graph = new D_W_Graph();
        algo = new D_W_Graph_Algo();
        graph.addNode(node1);
        graph.addNode(node2);
        graph.addNode(node3);
        graph.addNode(node4);
        graph.addNode(node5);
        graph.addNode(node6);
        graph.addNode(node7);
        graph.addNode(node8);
        graph.addNode(node9);


        graph.connect(1,2,1);
        graph.connect(2,1,1);
        graph.connect(2,3,2);
        graph.connect(3,2,2);
        graph.connect(2,4,9);
        graph.connect(4,2,9);
        graph.connect(3,5,3);
        graph.connect(5,3,3);
        graph.connect(5,6,7);
        graph.connect(6,5,7);
        graph.connect(5,7,5);
        graph.connect(7,5,5);
        graph.connect(7,2,4);
        graph.connect(2,7,1);
        graph.connect(7,1,1);
        graph.connect(1,7,1);
        graph.connect(8,9,1);
        graph.connect(9,8,1);
        algo.init(graph);
        assertEquals(algo.BFS(graph,node1),5);

    }
    @Test
    void DFS() {

    }

    @Test
    void g_transopse() {
    }

    @Test
    void shortestPathDist() {
        graph = new D_W_Graph();
        algo = new D_W_Graph_Algo();
        graph.addNode(node1);
        graph.addNode(node2);
        graph.addNode(node3);
        graph.addNode(node4);
        graph.connect(1,2,1);
        graph.connect(2,3,1);
        graph.connect(3,2,10);
     ///   graph.connect(1,3,3);
        algo.init(graph);
        assertEquals(algo.shortestPathDist(1,2),1);
    }

    @Test
    void shortestPath() {
        graph = new D_W_Graph();
        algo = new D_W_Graph_Algo();
        graph.addNode(node1);
        graph.addNode(node2);
        graph.addNode(node3);
        graph.addNode(node4);
        graph.connect(1,2,1);
        graph.connect(2,4,1);
        graph.connect(1,4,10);
        graph.connect(1,3,3);
        algo.init(graph);
        List<NodeData> list= algo.shortestPath(1,4);
        List<NodeData> list2 = new ArrayList<>();
        list2.add(node1);
        list2.add(node2);
        list2.add(node4);
        for (int i = 0; i < list.size(); i++) {
            assertEquals(list.get(i).getKey(),list2.get(i).getKey());

        }


        }


    @Test
    void center() {
        graph = new D_W_Graph();
        algo = new D_W_Graph_Algo();
        graph.addNode(node1);
        graph.addNode(node2);
        graph.addNode(node3);
        graph.addNode(node4);
        graph.addNode(node5);
        graph.addNode(node6);
        graph.connect(1,2,2);
        graph.connect(2,1,2);
        graph.connect(2,3,3);
        graph.connect(3,2,3);
        graph.connect(3,4,1);
        graph.connect(4,3,1);
        graph.connect(3,5,4);
        graph.connect(5,3,4);
        graph.connect(5,4,4);
        graph.connect(4,5,4);
        graph.connect(3,6,4);
        graph.connect(6,3,4);
        algo.init(graph);

//        graph = new D_W_Graph();
//        algo = new D_W_Graph_Algo();
//        graph.addNode(node1);
//        graph.addNode(node2);
//        graph.addNode(node3);
//        graph.addNode(node4);
//        graph.addNode(node5);
//        graph.addNode(node6);
//        graph.addNode(node7);
//        graph.addNode(node8);
//        graph.addNode(node9);
//
//
//        graph.connect(1,2,1);
//        graph.connect(2,1,1);
//        graph.connect(2,3,2);
//        graph.connect(3,2,2);
//        graph.connect(3,4,9);
//        graph.connect(4,3,9);
//        graph.connect(3,5,3);
//        graph.connect(5,3,3);
//        graph.connect(5,6,7);
//        graph.connect(6,5,7);
//        graph.connect(5,7,5);
//        graph.connect(7,5,5);
//        graph.connect(7,2,4);
//        graph.connect(2,7,4);
//        graph.connect(7,8,1);
//        graph.connect(8,7,1);
//        graph.connect(8,9,1);
//        graph.connect(9,8,1);
        algo.init(graph);
        assertEquals(algo.center().getKey(),node3.getKey());
    }

    @Test
    void tsp() {
        graph = new D_W_Graph();
        algo = new D_W_Graph_Algo();
        graph.addNode(node1);
        graph.addNode(node2);
        graph.addNode(node3);
        graph.addNode(node4);
//        graph.addNode(node5);
//        graph.addNode(node6);
        graph.connect(1,2,2);
        graph.connect(2,1,2);
//        graph.connect(2,3,3);
//        graph.connect(3,2,3);
//       graph.connect(3,4,1);
//        graph.connect(4,3,1);
//        graph.connect(3,5,4);
//        graph.connect(5,3,4);
//        graph.connect(5,4,4);
//        graph.connect(4,5,4);
//        graph.connect(3,6,4);
//        graph.connect(6,3,4);
        algo.init(graph);
        List<NodeData> cities = new ArrayList<>();
        List<NodeData> ans = new ArrayList<>();
        cities.add(node1);
        cities.add(node2);
//        cities.add(node3);
//        cities.add(node4);
//        cities.add(node3);
//        cities.add(node4);
//        cities.add(node5);
//        cities.add(node6);

        ans = algo.tsp(cities);

        System.out.println(algo.tsp(cities));

    }

    @Test
    void save() {
//        graph = new D_W_Graph();
//        algo = new D_W_Graph_Algo();
//        graph.addNode(node1);
//        graph.addNode(node2);
//        graph.addNode(node3);
//        graph.addNode(node4);
//        graph.addNode(node5);
//        graph.addNode(node6);
//        graph.connect(1,2,2);
//        graph.connect(2,1,2);
//        graph.connect(2,3,3);
//        graph.connect(3,2,3);
//        graph.connect(3,4,1);
//        graph.connect(4,3,1);
//        graph.connect(3,5,4);
//        graph.connect(5,3,4);
//        graph.connect(5,4,4);
//        graph.connect(4,5,4);
//        graph.connect(3,6,4);
//        graph.connect(6,3,4);
//        algo.init(graph);
        graph = new D_W_Graph();
        algo = new D_W_Graph_Algo();
        algo.load("C:\\Users\\yarin\\Desktop\\G2.json");
        algo.save("a.txt");
    }

    @Test
    void load() {
        graph = new D_W_Graph();
        algo = new D_W_Graph_Algo();
        algo.load("C:\\Users\\yarin\\Desktop\\G2.json");
        System.out.println(algo.getGraph().nodeSize());
    }
}