package graph_Algorithms;

import api.NodeData;
import data_Structure.D_W_Graph;
import data_Structure.Geo_Location;
import data_Structure.Node_Data;
import org.junit.jupiter.api.Assertions;
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
        graph = new D_W_Graph();
        algo = new D_W_Graph_Algo();
        graph.addNode(node1);
        algo.init(graph);
        assertEquals(algo.getGraph().getNode(0).getKey(),0);

    }

    @Test
    void getGraph() {
        graph = new D_W_Graph();
        algo = new D_W_Graph_Algo();
        graph.addNode(node1);
        graph.addNode(node2);
        algo.init(graph);
        assertEquals(algo.getGraph(),graph);
    }

    @Test
    void copy() {
        graph = new D_W_Graph();
        algo = new D_W_Graph_Algo();
        graph.addNode(node1);
        graph.addNode(node2);
        graph.addNode(node3);
        graph.addNode(node4);
        graph.connect(1,2,3);
        graph.connect(1,3,3);
        algo.init(graph);
        D_W_Graph copy = (D_W_Graph) algo.copy();
        assertEquals(copy.nodeSize(),algo.getGraph().nodeSize());
        assertNotEquals(copy.getNode(1),algo.getGraph().getNode(1));


    }

    @Test
    void isConnected() {
        graph = new D_W_Graph();
        algo = new D_W_Graph_Algo();
        algo.load("C:\\Users\\yarin\\Desktop\\10000Nodes.json");
        assertEquals(algo.isConnected(),true);
        ///System.out.println(algo.getGraph().edgeSize());
//        algo.load("C:\\Users\\yarin\\Desktop\\G2.json");
//        assertEquals(algo.isConnected(),true);

    }

    @Test
    void DFS() {
        graph = new D_W_Graph();
        algo = new D_W_Graph_Algo();
        algo.load("a.json");
        algo.DFS(algo.getGraph(),algo.getGraph().getNode(0));
        for (int i = 0; i < algo.getGraph().nodeSize(); i++) {
            assertEquals(algo.getGraph().getNode(i).getTag(),1);

        }
    }

    @Test
    void g_transopse() {
        graph = new D_W_Graph();
        algo = new D_W_Graph_Algo();
        algo.load("C:\\Users\\yarin\\Desktop\\100000.json");
        D_W_Graph copy = (D_W_Graph) algo.copy();
        algo.G_transopse(copy);
        if(copy.getEdge(1,2)!=null&&algo.getGraph().getEdge(2,1)!=null){
            assertEquals(copy.getEdge(1,2).getWeight(),algo.getGraph().getEdge(2,1).getWeight());
        }



    }

    @Test
    void shortestPathDist() {
        graph = new D_W_Graph();
        algo = new D_W_Graph_Algo();
        algo.load("C:\\Users\\yarin\\Desktop\\100000.json");
        double a = algo.shortestPathDist(20,50);
        System.out.println(a);

        }


  //  }
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
        algo.init(graph);
        algo.load("C:\\Users\\yarin\\Desktop\\1000Nodes.json");
        int ans =algo.center().getKey();
        assertEquals(ans,362);
    }

    @Test
    void tsp() {
        graph = new D_W_Graph();
        algo = new D_W_Graph_Algo();
        graph.addNode(node1);
        graph.addNode(node2);
        graph.addNode(node3);
        graph.addNode(node4);
        graph.connect(0,1,4);
        graph.connect(1,2,10);
        graph.connect(0,2,10);
        graph.connect(2,1,1);
        graph.connect(0,3,1);
        graph.connect(3,2,1);
        algo.init(graph);
        List<NodeData> cities = new ArrayList<>();
        List<NodeData> ans = new ArrayList<>();
        cities.add(node1);
        cities.add(node2);
        cities.add(node3);
        ans = algo.tsp(cities);

      assertEquals(ans.get(0).getKey(),0);
      assertEquals(ans.get(1).getKey(),3);
      assertEquals(ans.get(2).getKey(),2);

    }

    @Test
    void save() {

//        graph = new D_W_Graph();
//        algo = new D_W_Graph_Algo();
//        algo.load("C:\\Users\\yarin\\Desktop\\G2.json");
//        algo.save("a.json");
//        algo.load("C:\\Users\\yarin\\IdeaProjects\\Graphs_Structure-Algorithms-\\a.json");
//        assertEquals(algo.getGraph().edgeSize(),80);
        graph = new D_W_Graph();
        algo = new D_W_Graph_Algo();
        graph.addNode(node1);
        graph.addNode(node2);
        graph.addNode(node3);
        graph.addNode(node4);
        graph.addNode(node5);
        node1.setLocation(new Geo_Location(2500,5000,0));
        node2.setLocation(new Geo_Location(5000,5000,0));
        node3.setLocation(new Geo_Location(3500,2500,0));
        node4.setLocation(new Geo_Location(100,100,0));
        node5.setLocation(new Geo_Location(10000,10000,0));
        graph.connect(0,1,4);
        graph.connect(0,2,10);
        graph.connect(1,2,10);
        graph.connect(3,0,2);
        graph.connect(1,4,9);
        algo.init(graph);
        algo.save("a.json");
    }

    @Test
    void load() {
        graph = new D_W_Graph();
        algo = new D_W_Graph_Algo();
        algo.load("C:\\Users\\yarin\\Desktop\\100000.json");
//        assertEquals(algo.getGraph().nodeSize(),31);
//        assertEquals(algo.getGraph().edgeSize(),80);
    }
}