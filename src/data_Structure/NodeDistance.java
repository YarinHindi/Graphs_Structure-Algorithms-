package data_Structure;


/**
 * This class represents the distance of node in the graph in some path
 * in the graph.
 * Each node has an identity number(unique key) and distance.
 */

public class NodeDistance implements Comparable<NodeDistance>{

    private int key;
    private double distance;

    /**
     * Constructor to create a new NodeData:
     */

    public NodeDistance(int key, double dis){
        this.key = key;
        this.distance = dis;
    }

    /**
     * This function return the distance this node.
     * @return  distance
     */

    public double getDistance() {
        return this.distance;
    }

    /**
     * This function changing the distance of this node.
     * @param s- weight
     */

    public void setDistance(double s) {
        this.distance=s;
    }


    /**
     * This function return the unique key (id) associated with this node.
     * @return  key
     */

    public int getK(){
        return this.key;
    }


    /**
     * This function compares between the distance of two nodes.
     * @param nodeDistance
     * @return  ans
     */

    @Override
    public int compareTo(NodeDistance nodeDistance) {
        int ans = 0;
        //if the distance of this node distance is bigger return 1
        if (this.getDistance()- nodeDistance.getDistance() > 0){
            ans = 1;
        }
        //if the distance of this node distance is smaller return -1
        else if (this.getDistance()-nodeDistance.getDistance() < 0){
            ans = -1;
        }
        //if the distance of this node info equals to the distance of node_info n return 0
        return ans;
    }

}