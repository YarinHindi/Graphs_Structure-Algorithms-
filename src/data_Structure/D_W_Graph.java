package data_Structure;

import api.DirectedWeightedGraph;
import api.EdgeData;
import api.NodeData;
import java.util.Iterator;
import javafx.geometry.Point3D;
//import Node_Data;

import java.util.*;

/**
 * node_map - gonna represent the node by a hash map,the key gonna be the node.key and the value gonna be the NodeData
 * edge_map - gonna represent the edges between the node by a hash map,the key gonna be src node and the value
 * gonna be a hash map which is  the key gonna be the dest node and the value gonna be the EdgeData.
 * edgeSize - gonna help us later to know how much edge we got at all
 * nodeSize - same as edgeSize gonna help us later.
 * MC - with MC we track how many changes we made in the graph.
 */
public class D_W_Graph implements DirectedWeightedGraph {
    private Map<Integer, NodeData> node_map = new HashMap<>();
    private Map<Integer,HashMap<Integer,EdgeData>> edge_map = new HashMap<>();
    private int edgeSize;
    private int nodeSize;
    private static int MC;
    public boolean edgeIterByKeyflag=false;
    public boolean edgeIterAllflag=false;
    public boolean nodeIterflag=false;
    private int keepCurrEdge=0;


    public D_W_Graph(D_W_Graph other){
        this.node_map= other.node_map;
        this.edge_map = other.edge_map;
        this.edgeSize= other.edgeSize;
        this.nodeSize= other.nodeSize;
        this.MC=0;
    }
    public D_W_Graph(){
        this.node_map=new HashMap<>();
        this.edge_map= new HashMap<>();
        this.edgeSize=0;
        this.nodeSize=0;
        this.MC=0;
    }
    /**
     * Return the node by the key if not existed return null
     */
    @Override
    public NodeData getNode(int key) {
      try{
          return this.node_map.get(key);
      }catch(NullPointerException e){
          return null;
      }
    }

    /**
     * Return the edge by the src and dest if not existed return null
     */
    @Override
    public EdgeData getEdge(int src,int dest) {
        try {
            return this.edge_map.get(src).get(dest);
        }catch(NullPointerException e){
                return null;
        }
    }
    /**
     * this function add a node to the hash map that keep nodes
     */
    @Override
    public void addNode(NodeData n) {
   //     if(this.nodeIterflag==true) throw new RuntimeException("Iterator has been constructed cant add node ");
          this.node_map.put(n.getKey(),n);
          this.nodeSize++;
          this.MC++;
    }

    @Override
    /**
     * this function connect two node and adding an edge between them.
     */
    public void connect(int src, int dest, double w) {
        if(w<0) throw new RuntimeException("edge weight must be positive");
     if(this.getNode(src)!=null&&this.getNode(dest)!=null){//check if node existing
         EdgeData temp = new Edge_Data(src,dest,w);
         if(this.edge_map.get(src)==null){//there is no neighbors already to node src
             HashMap<Integer,EdgeData> init = new HashMap<Integer,EdgeData>();
             this.edge_map.put(src,init);
             this.edge_map.get(src).put(dest,temp);
         }else{
             this.edge_map.get(src).put(dest,temp);//if there is no edge we gonna add
                                                    //if there a edge we gonna add and replace to the new value
         }
         //if there is a edge check and not add to edgesize++;
         this.edgeSize++;
         this.MC++;
     }else {
         throw new RuntimeException("At list one of the Node isnt exist");
     }
    }
    /**
     * this function return an Iterator for all nodes in graph.
     */
    @Override
    public Iterator<NodeData> nodeIter() {
        try {
            this.nodeIterflag = true;
            return this.node_map.values().iterator();
        }catch (NullPointerException e){
            return null;
        }

    }
    /**
     * this function return an Iterator for all edges in graph.
     */
    @Override
    public Iterator<EdgeData> edgeIter() {
        try {
            this.edgeIterAllflag=true;
            ArrayList<EdgeData> ans = new ArrayList<>();
            Set set = this.edge_map.keySet();
            Iterator iter = set.iterator();
            while (iter.hasNext()) {
                Iterator edge_iter = this.edgeIter((int) iter.next());
                while (edge_iter.hasNext()) {
                    ans.add((EdgeData) edge_iter.next());
                }
            }
            return ans.iterator();
        }catch (NullPointerException e){
            return null;
        }

    }

    /**
     * this function clear the graph removing all data from the graph.
     *
     */
    public void graphClear(){
        this.node_map.clear();
        this.edge_map.clear();
        this.nodeSize=0;
        this.edgeSize=0;
    }
    /**
     * this function return an Iterator for all edges that going out from node_id in graph.
     */
    @Override
    public Iterator<EdgeData> edgeIter(int node_id) {
        try {
            this.keepCurrEdge=node_id;
            this.edgeIterByKeyflag=true;
            return this.edge_map.get(node_id).values().iterator();
        } catch (NullPointerException e) {
            return null;
        }
    }

    /**
     * this function return the node we are removing.
     * this function remove also all edges that going out from this node and to him.
     * we put in try and catch cause we might try removing a node that node existed.
     */
    @Override
    public NodeData removeNode(int key) {

        try{
            //remove to edge from this node to other nodes.

            NodeData temp =this.node_map.get(key);
            this.edgeSize-=this.edge_map.get(key).size();
            this.edge_map.remove(key);
            this.node_map.remove(key);
            this.nodeSize--;
            this.MC++;
            //remove all edge associated to that node.
            int check =0;
            Set set = this.edge_map.keySet();
            Iterator iter = set.iterator();
            while(iter.hasNext()){
                int ind = (int)iter.next();
                if(this.getEdge(ind,key)!=null){
                    this.removeEdge(ind,key);
                    check++;
                }
            }
            return  temp;



        }catch(NullPointerException e){
            return null;
        }
    }
    /**
     * this return the edge we are removing
     * we put in try and catch because we might try to remove a edge that don't exists
     */
    @Override
    public EdgeData removeEdge(int src, int dest) {
        try {
            EdgeData temp = getEdge(src, dest);
            this.edge_map.get(src).remove(dest);
            this.edgeSize--;
            this.MC++;
            return temp;

        } catch (NullPointerException e) {
            return null;
        }
    }

    @Override
    public int nodeSize() {
        return this.nodeSize;
    }

    @Override
    public int edgeSize() {
        return this.edgeSize;
    }

    /**
     * this function return the MC representing the changes in the graph.
     */
    @Override
    public int getMC() {

        return this.MC;
    }

}
