package data_Structure.Test;

import data_Structure.Edge_Data;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Edge_DataTest {
    Edge_Data Edge = new Edge_Data(0,1,2.2);


    @Test
    void getSrc() {
        assertEquals(Edge.getSrc(),0);
    }

    @Test
    void getDest() {
        assertEquals(Edge.getDest(),1);
    }

    @Test
    void getWeight() {
        assertEquals(Edge.getWeight(),2.2);
    }

    @Test
    void getInfo() {
        assertEquals(Edge.getInfo(),"");
    }

    @Test
    void setInfo() {
        Edge.setInfo("set");
        assertEquals(Edge.getInfo(),"set");
    }

    @Test
    void getTag() {
        assertEquals(Edge.getTag(),0);
    }

    @Test
    void setTag() {
        Edge.setTag(12);
        assertNotEquals(Edge.getTag(),0);
        assertEquals(Edge.getTag(),12);
    }

}