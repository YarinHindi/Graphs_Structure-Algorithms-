package data_Structure.Test;

import api.NodeData;
import data_Structure.D_W_Graph;
import data_Structure.Edge_Data;
import data_Structure.Geo_Location;
import data_Structure.Node_Data;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class D_W_GraphTest {
    D_W_Graph graph = new D_W_Graph();
    Node_Data node = new Node_Data(new Geo_Location(1,2,3));
    Node_Data node2 = new Node_Data(new Geo_Location(4,5,6));
    Node_Data node3 = new Node_Data(new Geo_Location(1,1,1));
    Node_Data node4 = new Node_Data(new Geo_Location(2,2,2));
    Node_Data node5 = new Node_Data(new Geo_Location(3,3,3));

    @Test
    void getNode() {
        graph.addNode(node);
        assertEquals(graph.getNode(1),node);
        assertEquals(graph.getNode(2),null);
    }

    @Test
    void getEdge() {

        assertEquals(graph.getEdge(1,2),null);
        graph.addNode(node);
        graph.addNode(node2);
        graph.connect(1,2,5);
        assertEquals(graph.getEdge(1,2).toString(),"(1 , 2 , 5.0)");


    }

    @Test
    void addNode() {
        graph.addNode(node);
        assertEquals(graph.nodeSize(),1);
    }


    @Test
    void nodeIter() {
        graph.addNode(node);
        graph.addNode(node2);
        graph.addNode(node3);
        graph.addNode(node4);
        graph.addNode(node5);



        }


    @Test
    void edgeIterAlledge() {
        graph.addNode(node);
        graph.addNode(node2);
        graph.connect(1,2,5);
        graph.connect(2,1,7);
        Iterator iter = graph.edgeIter();
        assertEquals(iter.next().toString(),"(1 , 2 , 5.0)");
        assertEquals(iter.next().toString(),"(2 , 1 , 7.0)");



    }

    @Test
    void EdgeIterForOneNode() {
        graph.addNode(node);
        graph.addNode(node2);
        graph.addNode(node3);
        graph.connect(1,2,5);
        graph.connect(2,1,7);
        graph.connect(1,3,2.5);
        Iterator iter = graph.edgeIter(1);
        assertEquals(iter.next().toString(),"(1 , 2 , 5.0)");
        assertEquals(iter.next().toString(),"(1 , 3 , 2.5)");

    }

    @Test
    void removeNode() {
            graph.addNode(node);
            graph.addNode(node2);
            graph.addNode(node3);
            graph.connect(1,2,5);
            graph.connect(2,1,7);
            graph.connect(1,3,2.5);
            graph.removeNode(1);
            assertEquals(graph.getNode(1),null);
            assertEquals(graph.getEdge(1,2),null);
            assertEquals(graph.getEdge(1,3),null);
            assertEquals(graph.getEdge(2,1),null);
    }

}