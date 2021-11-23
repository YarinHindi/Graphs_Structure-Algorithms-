package data_Structure;

import api.DirectedWeightedGraph;
import api.EdgeData;
import api.NodeData;
import javafx.geometry.Point3D;
//import Node_Data;

import java.util.*;

/**
 * node_map - gonna represnt the node by a hash map,the key gonna be the node.key and the value gonna be the NodeData
 * edge_map - gonna represnt the edges between the node by a hash map,the key gonna be src node and the value
 * gonna be a hash map which is key gonna be the src node and the value gonna be the EdgeData.
 * edgeSize - gonna help us later to know how much edge we got at all
 * nodeSize - same as edgeSize gonna help us later.
 * MC - with MC we track how many changes we made in the graph.
 */
public class D_W_Graph implements DirectedWeightedGraph {
    private Map<Integer, NodeData> node_map = new HashMap<>();
    private Map<Integer,HashMap<Integer,EdgeData>> edege_map = new HashMap<>();
    private int edgeSize;
    private int nodeSize;
    private int MC;

    public D_W_Graph(){
        this.node_map=new HashMap<>();
        this.edege_map= new HashMap<>();
        this.edgeSize=0;
        this.nodeSize=0;
        this.MC=0;
    }
    @Override
    public NodeData getNode(int key) {
      try{
          return this.node_map.get(key);
      }catch(NullPointerException e){
          return null;
      }
    }

    @Override
    public EdgeData getEdge(int src,int dest) {
        try {
            return this.edege_map.get(src).get(dest);
        }catch(NullPointerException e){
                return null;
        }
    }


    @Override
    public void addNode(NodeData n) {
          this.node_map.put(n.getKey(),n);
          this.nodeSize++;
          this.MC++;
    }

    @Override
    public void connect(int src, int dest, double w) {
        if(w<0) throw new RuntimeException("edge weight must be positive");
     if(this.getNode(src)!=null&&this.getNode(dest)!=null){//check if node existing
         EdgeData temp = new Edge_Data(src,dest,w);
         if(this.edege_map.get(src)==null){//there is no neighbors already to node src
             HashMap<Integer,EdgeData> init = new HashMap<Integer,EdgeData>();
             this.edege_map.put(src,init);
             this.edege_map.get(src).put(dest,temp);
         }else{
             this.edege_map.get(src).put(dest,temp);//if there is no edge we gonna add
                                                    //if there a edge we gonna add and replace to the new value
         }
         this.edgeSize++;
         this.MC++;
     }else {
         throw new RuntimeException("At list one of the Node isnt exist");
     }
    }

    @Override
    public Iterator<NodeData> nodeIter() {
        return null;
    }

    @Override
    public Iterator<EdgeData> edgeIter() {
        return null;
    }

    @Override
    public Iterator<EdgeData> edgeIter(int node_id) {
        return null;
    }

    @Override
    public NodeData removeNode(int key) {
        return null;
    }

    @Override
    public EdgeData removeEdge(int src, int dest) {
        return null;
    }

    @Override
    public int nodeSize() {
        return 0;
    }

    @Override
    public int edgeSize() {
        return 0;
    }

    @Override
    public int getMC() {
        return 0;
    }
}
