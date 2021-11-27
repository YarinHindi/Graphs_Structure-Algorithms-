package data_Structure.Test;

import data_Structure.Geo_Location;
import data_Structure.Node_Data;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Node_DataTest {
    Node_Data node = new Node_Data(new Geo_Location(1,2,3));
    Node_Data node2 = new Node_Data(new Geo_Location(1,1,1),2.0,"iam new node");

    @Test
    void getKey() {
        assertEquals(node.getKey(),1);
        assertEquals(node2.getKey(),2);
    }

    @Test
    void getLocation() {
        assertEquals(node.getLocation().x(),1);
        assertEquals(node.getLocation().y(),2);
        assertEquals(node.getLocation().z(),3);
    }

    @Test
    void setLocation() {
        node.setLocation(new Geo_Location(1,1,1));
        assertEquals(node.getLocation().toString(),"(1.0,1.0,1.0)");
    }

}