package data_Structure;

import api.GeoLocation;
import api.NodeData;

public class Node_Data implements NodeData,Comparable<NodeData>{
    private int key;
    private Geo_Location location;
    private double weight;
    private int tag;
    private String info;
    static public int key_track=0;


    public  Node_Data(Geo_Location location,int key){
       this.location=location;
       this.key=key;
    }
    public Node_Data(int key){
        this.key = key;
        this.location = new Geo_Location(0,0,0);
        this.weight=0;

    }
    public Node_Data(Geo_Location location,double weight,String info){
        this.key = this.key_track;
        this.key_track++;
        this.location = new Geo_Location(location);
        this.weight=weight;
        this.info=info;
    }
    /**
     *
     * @param location constructor by location
     */
    public Node_Data(Geo_Location location){
        this.key=  this.key_track;
        this.key_track++;
        this.location = new Geo_Location(location);
    }
    /**
     *
     * @param other copy constructor
     */
    public Node_Data(Node_Data other){
        this.location = new Geo_Location(other.location);
        this.tag=other.tag;
        this.info= other.info;
        this.weight=other.weight;
        this.key=other.key;
    }
    @Override

    public int getKey() {
        return this.key;
    }

    @Override
    public GeoLocation getLocation() {
        return this.location;
    }

    @Override
    public void setLocation(GeoLocation p) {
       this.location = new Geo_Location(p);


    }

    @Override
    public double getWeight() {
        return this.weight;
    }

    @Override
    public void setWeight(double w) {
      this.weight = w;

    }

    @Override
    public String getInfo() {
        return this.info;
    }

    @Override
    public void setInfo(String s) {
     this.info=s;
    }

    @Override
    public int getTag() {
        return this.tag;
    }

    @Override
    public int compareTo(NodeData nodeDistance) {
        int ans = 0;
        //if the distance of this node distance is bigger return 1
        if (this.getWeight()- nodeDistance.getWeight() > 0){
            ans = 1;
        }
        //if the distance of this node distance is smaller return -1
        else if (this.getWeight()- nodeDistance.getWeight() < 0){
            ans = -1;
        }
        //if the distance of this node info equals to the distance of node_info n return 0
        return ans;
    }


    @Override
    public void setTag(int t) {
       this.tag=t;
    }
}
