package data_Structure.Test;

import api.GeoLocation;
import data_Structure.Geo_Location;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class Geo_LocationTest {
    Geo_Location point3d= new Geo_Location(0,0,0);
    Geo_Location getPoint3d_2 = new Geo_Location(0,10,0);
    @Test
    void x() {
        assertEquals(point3d.x(),0);
        assertEquals(getPoint3d_2.x(),1);
    }

    @Test
    void y() {
        assertEquals(point3d.y(),0);
        assertEquals(getPoint3d_2.y(),1);
    }

    @Test
    void z() {
        assertNotEquals(point3d.z(),1);
        assertEquals(getPoint3d_2.z(),2);
    }

    @Test
    void distance() {
        double s = point3d.distance(getPoint3d_2);
      assertEquals(s,10);
    }
}