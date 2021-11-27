package data_Structure;

import api.GeoLocation;
import javafx.geometry.Point3D;
public class Geo_Location implements GeoLocation {

    private double x;
    private double y;
    private double z;


    public Geo_Location(double x,double y,double z){
       this.x=x;
       this.y=y;
       this.z=z;
    }
    public Geo_Location(){
        this.x=0;
        this.y=0;
        this.z=0;
    }

    public Geo_Location(GeoLocation other){

        this.x = other.x();
        this.y = other.y();
        this.z = other.z();

    }

    @Override
    public double x() {
        return this.x;
    }

    @Override
    public double y() {

        return this.y;
    }

    @Override
    public double z() {

        return this.z;
    }

    @Override
    public double distance(GeoLocation g) {
        double xval = Math.pow((g.x()-this.x),2);
        double yval = Math.pow((g.y()-this.y),2);
        double zval = Math.pow((g.z()-this.z),2);
        
        return Math.sqrt(xval+yval+zval);


    }
    public String toString(){
        return "(" + this.x() + "," + this.y() + "," + this.z() + ")";
    }


}
