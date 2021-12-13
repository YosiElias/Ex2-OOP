# OOP - Ex2
![This is an image](https://github.com/YosiElias/Ex2-OOP/blob/master/Ex2/src/resources/Graph.jpeg)
This project is the third task in the course. The task implements a data structure of weighted and directional gragh. The project implements a number of algorithms on the graph including the check if the graph is connected, calculate a short path between two vertices in the graph and the ability to implement graphical interface which includes presenting the graph, load it from files and run algorithms on the graph and more. In the paragraphs below I will detail the classes in the project, explain the principles of the implementation and more.

**ID of presenters: 315858506,316156108**

## Table of contents
* [Main classes planning](#Main-classes-planning)
* [DirectedWeightedGraph Class](#DirectedWeightedGraph-Class)
* [DWGalgo class](#DWGalgo-class)
* [Implementations and principles](#Implementations-and-principles)
* [GUI](#GUI)
* [Performances](#Performances)
* [Visualization](#Visualization)
* [External info](#External-info)



###  Main classes planning
> The project has 5 interfaces to implement and 3 main classes.

### DirectedWeightedGraph Class
> This class implements DirectedWeightedGraph interface. The class contains inner classes - **NodeData**- which implements NodeData interface, and **EdgeData**-  which implements EdgeData interface. Short brief on those classes:

#### Nodedata class
Every node has his own id, position, info and more. Few methods of the class is:
- *getKey* - returns the key of the node.
- *getLocation* - returns the position of the node(x,y). 
- *get info, set info* - allows to change the info of node and get it.

#### Nodedata class
Every edge has his own weight, source, and dest. Few methods of the class is:
- *getSrc* - returns the source which the node came from.
- *getDst* - returns the dest which the node goes to.

#### Back to DirectedWeightedGraph Class
 This class represents a Directional Weighted Graph. It supports a large number of nodes.
 This implementation based on HashMap data structure. Each graph has few fields:
 * HashMap that stores all the nodes.
 * HashMap that stores all the edges.
 * HashMap of HashMaps such that every node has its edges HashMap that coming out of him.
 * HashMap of HashMaps such that every node has its edges HashMap that coming into him.
 * MC : Mode Count, a variable that stored the amount of changes(add node, remove node, add edge, remove edge)made in this graph.
 
> NOTE: because the graph is directed - the 2 HashMap of HashMaps are probably different, so we decided to keep them both as fiels - this will help us in the algorithms as we see next.

### Few Methods
*addNode* - adds a new node to the graph with the given node_data - O(1).
*Connect* - connects an edge with weight w between node src to node dest - O(1).

*remove node* - Deletes the edge from the graph - O(1).

*Returns iterators* of the nodes and the edges.

> NOTE: beacuse the implement needs to support few methods of O(1) - we used HashMaps to save the graph so the implement of this functions is O(1).

## DWGalgo class
This class implements Directional Weighted Graph algo interface. The only field in the class is a weighted graph on which we want to perform the methods.

#### main methods
- *Copy* - Computes a deep copy of this weighted graph.
- *isConnected* - returns true if and only if (iff) there is a valid path from each node to each other node. This method uses BFS algorithm that will be detailed below.
- *ShortestPathDist* - Computes the length of the shortest path between src to dest. This method uses dijkstra algorithm that will be detailed below.
- *center* - finds the NodeData which minimizes the max distance to all the other nodes. Assuming the graph isConnected
- *Tsp* - computes a list of consecutive nodes which go over all the nodes in cities. the sum of the weights of all the consecutive (pairs) of nodes (directed) is the "cost" of the solution - the lower the better.
- *save* - saves this weighted (directed) graph to the given file name - in JSON format
- *load* - this method loads a graph to this graph algorithm.


## Implementations and principles
* As said before, we used HashMaps to store the graph, so the structure will support the run time that required from the methods.  
* The graph is recieved from getGraphAlgo method in Ex2 class. And because the algo class gets a graph we can use the methos and check them and use all the methods in the class represents graph.

* **functions**

   - *isConnect* - to efficient the run time we took some node and we based on BFS algo,such that this node will be added to a queue, and all his'neighbours'(edges goes out **from** him, stored in HashMap of HasMaps asdetailed above), also will be added(each node will enter only once), andso on. If all nodes has been added to the queue in some point, then wecheck BFS on the flipped graph(flip the edges direction), which we alsostored in HashMap of HasMaps as detailed above(edges goes **into** eachnode).Also now, if all nodes has been added to the queue in  some point -the graph is connected. O(|v|+|e|) (BFS twice)                                                                                       
   
   - *ShortestPathDist* - 
       In this function, we implemented the Dijkstra algorithm, in the                    implementation we use Priority Queue where we made use of which simulates a        bit the use of the minimum heap with the help of a manipulation we                performed.
          
       We created a new object 'node' where we kept as a 'data' the NodeData, and        the updated weight according to the time the object 'node'  was created we        kept in the field 'weight'.       
          
       In this way every time we wanted to update the weight of a certain NodData        we created a new object 'node' with the updated weight, and put it in the          Priority Queue  (an action that takes O (log n)).
          
       Since the queue takes the minimum 'node' each time, for each 'data' ( any          NodData 'n') we will always get the NodData 'n' with the minimum weight,          i.e., the most up to date.
          
       At the same time, we will create a HashMap  that contains all the 'node' we        have already gone through and when we remove the minimal organ from the            queue we will check if we have already visited it with the HashMap and            continue the process (perform  'relax' on each 'node') only for 'node' we          have not yet performed  'relax'.
          
       The running time of extracting the minimum 'node' from the queue will take        O(1), and this is the reason we choose to use this data struct for this            algorithm.




   - *ShortestPath* - based on ShortestPathDist(SPD), which there the tag of each node                     updates to be the node key from which he came to. Then start insert the tags(of the relevant path to a list





   - *Center* - based on shortest path.To efficient run time - SPD also calculate the distance                 from some node to all other nodes, so we check for all node the distances to                 all other nodes and return the node with the shortest path to all other nodes
                (we dont need to check fron every node to every node).

* **Junit** - In order to check the implementations we also used the given json files and                 we also create a graph from a small json file so we can follow more intensive                for the methods and how they works step by step using debug.
     * Because the task contains a lot of classes and code, we used junit at every step to          check everything works well - the aspect of the correctness and also the aspect of          the performances.
     * If the check for some method has failed - most of the time we make intensive                examination on the small graph we created because it is easier to follow, and then          changed the implementation until the method passed for all jsons.


## GUI
In order to run the algorithms on a particular graph:
1.  Run the command line - java -jar Ex2.jar 'File location'/'file name'.json
        The graph will now be displayed in a new window where you can perform all the steps         below.
2.  You can now execute all the algorithms by selecting the menu 'Function' (or click           ALT+a)     ->  'algorithm function name' (e.g.  'isConnected'). 
        * Now for each function that requires input, a new input window will be displayed              depending on the selected function.
        * Note: A valid input must be entered (for example, do not enter an id Node that               does not exist in the graph on 'shortPath' function).
          If you do so, a corresponding error message will be received in a new window.
        * Note: In the 'TSP' function after entering all the data, enter 'Z' / 'z' to close           the input window (as explained in the window itself).
3.   You can edit the graph you loaded by selecting the menu 'Edit' (or click ALT+e)  ->          'Edit funftion' (e.g.  'Add Node').
        * Note: A valid input must be entered (for example, do not enter an id Node that               does not exist in the graph on 'Remove Node' function).
4.   In addition, you can perform general operations on the graph, such as loading a new          graph, clearing the board from the existing graph, saving the graph in a JSON file,          and exiting the program.
        * These operations are performed by selecting the menu 'File' (or click ALT+f) ->              'file function name' (e.g.  'Load Graph').
        * Note: If you load a new graph, the current graph will be automatically deleted               from the board and the new graph will be displayed in its place.
        * To load a new graph you can also press ALT+F+L.
        * To close the program, you can also click ALT+F4.



## Performances
* IsConnected: 
   - j1 = 86 ms (milisecond)
   - 1000 Nodes = 238 ms
   - 10000 Nodes = 486 ms
   - 100000 Nodes = 11.95 sec
   - 1000000 Nodes = timeout
   
* Center:
   - j1 = 111 ms
   - 1000 Nodes = 11.62 sec
   - 10000 Nodes = 38 min
   - 100000 Nodes = timeout
   - 1000000 Nodes = timeout
   
* ShortestpathDist: 
   - j1 = 112 ms
   - 1000 Nodes = 309 ms
   - 10000 Nodes = 1.146 sec
   - 100000 Nodes = timeout 
   - 1000000 Nodes = timeout

* ShortestpathDist: 
   - j1 = 106 ms
   - 1000 Nodes = 393 ms
   - 10000 Nodes = 1.203 sec
   - 100000 Nodes = timeout
   - 1000000 Nodes = timeout

## Visualization
json1 with tsp function of some nodes:
![This is an image](https://github.com/YosiElias/Ex2-OOP/blob/master/Ex2/src/resources/TSPgraph.jpeg)

json2 with shortest path between 2 nodes
![This is an image](https://github.com/YosiElias/Ex2-OOP/blob/master/Ex2/src/resources/g2short.jpeg)

json2 after removing 3 nodes
![This is an image](https://github.com/YosiElias/Ex2-OOP/blob/master/Ex2/src/resources/g2remove.jpeg)



### External info

* More about directed weighted graph:  [http://math.oxford.emory.edu/site/cs171/directedAndEdgeWeightedGraphs/](http://math.oxford.emory.edu/site/cs171/directedAndEdgeWeightedGraphs/)

* More about Dijkstra's algorithm:
 [https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm](https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm)

* More about BFS algorithm:
[https://en.wikipedia.org/wiki/Breadth-first_search](https://en.wikipedia.org/wiki/Breadth-first_search)



