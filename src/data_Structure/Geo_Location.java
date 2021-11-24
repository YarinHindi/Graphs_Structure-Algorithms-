package data_Structure;

import api.GeoLocation;
import javafx.geometry.Point3D;
public class Geo_Location implements GeoLocation {

    private Point3D location;

    public Geo_Location(double x,double y,double z){
       this.location = new Point3D(x,y,z);
    }
    public Geo_Location(){
        this.location = new Point3D(0.0,0.0,0.0);
    }

    public Geo_Location(GeoLocation other){
        this.location= new Point3D(other.x(),other.y(),other.z());
    }

    @Override
    public double x() {
        return this.location.getX();
    }

    @Override
    public double y() {
        return this.location.getY();
    }

    @Override
    public double z() {
        return this.location.getZ();
    }

    @Override
    public double distance(GeoLocation g) {
        
        return this.location.distance(g.x(),g.y(),g.z());
    }
}
