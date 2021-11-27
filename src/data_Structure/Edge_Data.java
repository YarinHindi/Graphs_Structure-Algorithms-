package data_Structure;

import api.EdgeData;

public class Edge_Data implements EdgeData {
    private int src;
    private int dest;
    private double weight;
    private String info;
    private int tag;


    public Edge_Data(int src,int dest,double weight){
        if(weight<0) throw new RuntimeException("Weight must be positive.");
        this.src=src;
        this.dest=dest;
        this.weight=weight;
        this.info="";
        this.tag=0;

    }
    public Edge_Data(int src,int dest,double weight,String info,int tag) {
        this.src = src;
        this.dest = dest;
        this.weight = weight;
        this.info = info;
        this.tag = tag;
    }
    //copy constructor
    public Edge_Data(Edge_Data p){
        this.src=p.src;
        this.dest=p.dest;
        this.info=p.info;
        this.weight=p.weight;
        this.tag=p.tag;
    }

    @Override
    public int getSrc() {
        return this.src;
    }

    @Override
    public int getDest() {
        return this.dest;
    }

    @Override
    public double getWeight() {
        return this.weight;
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
    public void setTag(int t) {
        this.tag=t;
    }
    public String toString(){
        return "(" + this.getSrc() + " , " + this.getDest() + " , " + this.getWeight() + ")";
    }
}


