# Graphs Structure Algorithms
In this task we were required to create a structure that will present a graph.
We were given some interfaces that we have implemented.
In this project we need to calculate algorithms on the graph that we created (center, shortest path between two nodes, shortest path distance between two nodes...).
Eventually, we created a GUI class that draws the graph, and shows the algorithms on it.

# DFS Algorithm logic
The algorithm logic is based on the following:
1) changing the node tag to 1.
2) going through all of his edges.
3) for each edge, the algorithm will call the DFS again with the dest key.
4) by that, the algorithm will go through all of the accesible nodes in a depth search from the specific node.

# Transpose Algorithm logic
The algorithm logic is based on the following:
1) copying the graph.
2) going through all of the edges.
3) for each edge, we are changing the direction of the edge (source and dest).
4) if both edges exist between two node (for both ways), we just swapping the weight between them.
5) if an edge exist only in one way we will remove the edge and create it in the opposite way.

# Is Connected Algorithm logic
The algorithm logic is based on the following:
1) copying the graph.
2) choosing a specific (random) node and running on the copied graph it DFS algorithm.
2) if theres a node in the copied graph that his tag didn't change to 1, then we return false.
3) if all nodes tag has been changed to 1, we will reset the nodes of the copied graph and perform a Transpose algorithm on the copied graph.
4) now we will perform again a DFS algorithm from the same node.
5) if there's a node with tag 0, we will return false, else, true.

# Dijkstra Algorithm logic
The algorithm logic is based on the following:
1) creating a priority queue.
2) adding the node to the queue.
3) going through all of his edges.
4) if the node dest tag of the specific edge is 0 we will add it to the queue.
5) calculting the weight of each node and updating it.
6) after going through all of his edges, we will change the source tag to 1.
7) by that, we will prevent from going back to a node that has already been "scanned".

# Shortest Path Distance Algorithm logic
The algorithm logic is based on the following:
1) running the Dijkstra algorithm on the given source node.
2) checking the weight of the dest node.

# Shortest Path Algorithm logic
The algorithm logic is based on the following:
1) copying the graph and creating a list of NodeData.
2) sending the source and dest nodes to the Shortest Path Distance Algorithm.
2) if it won't return us -1, that means that there's a track between them.
3) we will transpose the copied graph.
4) now we will start from the dest node, add it to the list, and check for his neighbors which neighbor weight + edge weight = dest weight.
5) the one node that will give us the equality is our next node, we will add it to the list, and now check on this node, the previous question.
6) we will continue doing that until we will get to the source node.
7) in the end, we will reverse the list.

# Center Algorithm logic
The algorithm logic is based on the following:
1) going through all of the graph nodes.
2) for each node we will perform the Dijkstra Algorithm.
3) find the maximum weight relative to all other nodes.
4) going to the next node and doing the same.
5) find the minimum of the maximum weights.
6) the one node that gave us the minimum is the center of the graph.

# TSP Algorithm logic
The algorithm logic is based on the following:
1) creating an ArrayList that will present the sequence of the given nodes that we will need to perform shortest path.
2) going through all of the nodes in the given list.
3) creating an ArrayList that will present an optional sequence of the given nodes.
4) adding the node to the optional ArrayList.
5) sending the node to a help function (tspHelp) and it will return us the next node (from the given nodes) that we would like to move to.
6) add the next node to the optional ArrayList.
7) keep sending to tspHelp and finding the next node until we did it size of the given nodes list.
8) going through the optional ArrayList, and calculating the shortest path dist in this list order.
9) going to the next given node and doing the above.
10) the minimum shortest path dist will be the final ArrayList.
11) sending the final ArrayList to the Shortest Path algorithm and return this list.

# Project structure
Interface name | description
--- | ---
DirectedWeightedGraph | Interface to present a directed weighted graph.
DirectedWeightedGraphAlgorithms | Interface to present a directed weighted graph algorithms.
EdgeData | Interface to present an edge.
GeoLocation | Interface to present a node location.
NodeData | Interface to present a node.


Class name | description
--- | ---
D_W_Graph | Present a graph.
Edge_Data | Present a graph edge.
Geo_Location | present a location of the graph node.
Node_Data | present a graph node.
D_W_Graph_Algo | Present a class to perform algorithms on a graph.
GUI | Drawing the graph and the algorithms on it.
Main | Creates a jar file that runs the projects.

# Simulation results
node size | edge size | build time | isConnected time | center time | transpose time
--- | --- | --- | --- | --- | ---
1,000 | 9,000 | 195 ms | 216 ms | 1.87 sec | 199 ms
10,000 | 100,000 | 616 ms | 767 ms | 204 sec | 775 ms
100,000 | 1,000,000 | 4.5 sec | 3.871 sec | - | 3.91 sec
1,000,000 | 20,000,000 | 25.4 sec | 21.5 sec | - | 22.7 sec

# UML
![image](https://user-images.githubusercontent.com/63747865/145728360-c602caed-8626-4ecb-8108-9742dde9b1f6.png)

# How to run
Run the main.java file, with the right json file that present graph.
Need to put Ex2.jar(path) ans graph.json(path)
Template for running the algorithm:

java -jar Ex2.jar <grpah json>

# project creators
Matan Yarin Shimon & Yarin Hindi