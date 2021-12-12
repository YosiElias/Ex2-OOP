import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;
import api.EdgeData;
import api.NodeData;

import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


class node{
    NodeDataClass _n;
    double wight;
    node(NodeDataClass n) {
        _n = n;
        double temp = n.get_wight();
        wight = temp;
    }
    public String toString(){
        return "id: "+_n.getKey()+
                " wight: "+wight;
    }
}

public class DWGalgo implements DirectedWeightedGraphAlgorithms {

    private DirectedWeightedGraphClass _graph;
    public static final int INF=99999;

    public DWGalgo(){
        this._graph = null;
    }

    @Override
    public void init(DirectedWeightedGraph g) {
        _graph = (DirectedWeightedGraphClass) g;
    }

    @Override
    public DirectedWeightedGraph getGraph() {
        return this._graph;
    }


    @Override
    public DirectedWeightedGraph copy() {
        DirectedWeightedGraph ans= new DirectedWeightedGraphClass(_graph);
        return ans;
    }

    @Override
    public boolean isConnected() {
        boolean ans = false;
        if (BfsFrom()) {
            ans= BfsTo();
        }
        return ans;
    }

    private boolean BfsTo() {
        int counter = 0;
        Queue<NodeData> q = new LinkedList<>();
        Iterator<NodeData> it = _graph.nodeIter();
        HashMap<Integer,NodeData> hash=_graph.getNodeHash();
        reset(hash);
        NodeData node = it.next();
        q.add(node);
        counter++;
        node.setInfo(""+0);
        while (!q.isEmpty()) {
            NodeData temp_n = q.poll();
            HashMap<String, EdgeData> ee = _graph.getNeighboursToHash(temp_n.getKey());
            for (Map.Entry<String, EdgeData> set :
                    ee.entrySet()) {
                int i = set.getValue().get_src();
                NodeData n = _graph.getNode(i);
                if (n!= null && n.getInfo() == ""+(-1)) {
                    counter++;
                    n.setInfo(""+0);
                    q.add(n);
                }
            }
        }
        if(counter==_graph.nodeSize()){
            return true;
        }
        return false;
    }


    private boolean BfsFrom() {
        int counter = 0;
        Queue<NodeData> q = new LinkedList<>();
        Iterator<NodeData> it = _graph.nodeIter();
        HashMap<Integer,NodeData> hash=_graph.getNodeHash();
        reset(hash);
        NodeData node = it.next();
        q.add(node);
        counter++;
        node.setInfo(""+0);
        while (!q.isEmpty()) {
            NodeData temp_n = q.poll();
            HashMap<String, EdgeData> ee = _graph.getNeighboursFromHash(temp_n.getKey());
            for (Map.Entry<String, EdgeData> set :
                    ee.entrySet()) {
                int i = set.getValue().get_dest();
                NodeData n = _graph.getNode(i);
                if (n != null) {
                    if (n.getInfo() == "" + (-1)) {
                        counter++;
                        n.setInfo("" + 0);
                        q.add(n);
                    }
                }
            }
        }
        if(counter==_graph.nodeSize()){
            return true;
        }
        return false;
    }

    private static void reset(HashMap<Integer, NodeData> nodeHash) {
        for (Map.Entry<Integer, NodeData> set :
                nodeHash.entrySet()) {
            set.getValue().setInfo(""+(-1));
            set.getValue().setTag(-1);
        }
    }

    private static void resetForShortDist(HashMap<Integer, NodeData> nodeHash) {
        for (Map.Entry<Integer, NodeData> set :
                nodeHash.entrySet()) {
            set.getValue().setInfo(""+(DWGalgo.INF));
            set.getValue().setTag(-1);  // Todo: !
        }
    }


    @Override
    public double shortestPathDist(int src, int dest) {
        double ans = sshortestPathDist(src, dest, _graph);
        if (ans==DWGalgo.INF)   //Todo: change !
            return -1;
        else
            return ans;
    }


    public static double sshortestPathDist(int src, int dest, DirectedWeightedGraphClass _graph) {
        NodeData cur = _graph.getNode(src);
        NodeData dst = _graph.getNode(dest);
        if (cur == null || dst == null)
            return -1;
        HashMap<Integer, NodeData> hash = _graph.getNodeHash();
        resetForShortDist(hash);
        cur.setInfo("" + (0));
        PriorityQueue<node> heap = new PriorityQueue<>(hash.size()*hash.size(), (n1, n2) -> Double.compare(n1.wight,n2.wight));

        for (Map.Entry<Integer,NodeData> set2 :
                hash.entrySet()) {
            int i = set2.getValue().getKey();
            NodeData n = _graph.getNode(i);
            node node = new node((NodeDataClass) n);
            heap.add(node);
        }

        boolean stop = false;
        HashMap<Integer, NodeDataClass> visited = new HashMap<>();
        node currNode = heap.remove();
        cur = currNode._n;
        while (!stop && !heap.isEmpty()) {
            int cur_key = cur.getKey();
            HashMap<String, EdgeData> n_list = _graph.getNeighboursFromHash(cur_key);
            for (Map.Entry<String, EdgeData> set :
                    n_list.entrySet()) {
                int i = set.getValue().get_dest();
                NodeData n = _graph.getNode(i);
                if (n!=null) {
                    EdgeData edge = _graph.getEdge(cur_key, n.getKey());
                    double new_tag = edge.get_weight() + Double.parseDouble(cur.getInfo());
                    if (new_tag < DWGalgo.INF && (Double.parseDouble(n.getInfo()) == DWGalgo.INF || Double.parseDouble(n.getInfo()) > new_tag)) {
                        currNode.wight = DWGalgo.INF;   //Todo: change !
                        n.setTag(cur_key);
                        n.setInfo("" + new_tag);
                        node newNode = new node((NodeDataClass) n);
                        heap.add(newNode);
                    }
                }
            }
            visited.put(cur.getKey(), (NodeDataClass) cur);
            while (!stop && !heap.isEmpty() && visited.containsKey(cur.getKey()))   //  && ((NodeDataClass) cur).get_mark()
            {
                node tempNode = heap.poll();//Todo: change ! was: remove();
                NodeDataClass temp = tempNode._n;
                cur = temp;
                currNode = tempNode;
                if (currNode.wight==DWGalgo.INF)
                    stop = true;
            }
        }
        return Double.parseDouble(dst.getInfo());
    }



    @Override
    public List<NodeData> shortestPath(int src, int dest) {
        return ssshortestPath(src, dest, _graph);
    }

    public static List<NodeData> ssshortestPath(int src, int dest, DirectedWeightedGraphClass _graph) {
        sshortestPathDist(src, dest, _graph);
        Stack<NodeData> rev_path = new Stack<>();
        NodeData curr = _graph.getNode(dest);
        while (curr.getKey() != src) {  //Todo: BAG - sortpath -> tsp 123-2 javaHeap
            rev_path.push(curr);
            if (curr.getTag() == -1) {
                return null;
            }
            curr = _graph.getNode(curr.getTag());
        }
        rev_path.push(curr);
        List<NodeData> path = new ArrayList<>();
        while (!rev_path.isEmpty()) {
            path.add(rev_path.pop());
        }
        return path;
    }

    @Override
    public NodeData center() {
        NodeDataClass comparTo = null;
        for (Iterator<NodeData> it = _graph.nodeIter(); it.hasNext(); ) //loop on all node as 'src'
        {   //find the max dist of any node from node 'n'
            NodeDataClass n = (NodeDataClass) it.next();
            //reset distances of 'n' node:
            if (comparTo == null) {  //if first loop:
                comparTo = n;
                Iterator<NodeData> itComp = _graph.nodeIter();
                while (comparTo == n && itComp.hasNext()){    //find any other node to compare to
                    comparTo = (NodeDataClass) itComp.next();
                }
            }
            findMaxDist(n,comparTo);
        }

        boolean isNotConnected = false;
        NodeDataClass minNode = null;
        for (Iterator<NodeData> itAns = _graph.nodeIter(); itAns.hasNext(); ) //loop on all nodes
        {   //find the min dist - 'minNode'
            NodeDataClass n = (NodeDataClass) itAns.next();
            if (minNode == null) { //first loop:
                minNode =  n;
            }
            else if (n.getMaxDist() < minNode.getMaxDist())
                minNode = n;
            if (n.getMaxDist() == DWGalgo.INF){
                isNotConnected = true;
                break;
            }
        }
        if (!isNotConnected)
            return minNode;
        else
            return null;    //if the graph is not connected
    }


    private void findMaxDist(NodeDataClass n, NodeDataClass comparTo) {
        double maxDist=Double.MIN_VALUE;
        //reset distances of 'n' node:
        this.shortestPathDist(n.getKey(), comparTo.getKey());
        for (Iterator<NodeData> itOfN = _graph.nodeIter(); itOfN.hasNext(); )  //loop on all the distance of nodes from 'n'
        {
            NodeDataClass inN = (NodeDataClass) itOfN.next();
            if (!inN.equals(n)) {
                double dist = Double.parseDouble(inN.getInfo());
                if (dist > maxDist)
                    maxDist = dist;
            }
        }
        n.setMaxDist(maxDist);
    }


    @Override
    public List<NodeData> tsp(List<NodeData> cities) {
        double minCost, finalCost=0, cost=0;
        List<NodeData> path = new ArrayList<NodeData>();
        path.add(cities.get(0));
        List<NodeData> tempPath = new ArrayList<NodeData>();
        NodeDataClass comparTo = null;

        for (int j = 0; j < cities.size(); j++)
        {   //loop on all the cities and search path from the city that my path get to
            NodeData src = path.get(path.size() - 1); //take the last city in the path
            minCost = Double.MAX_VALUE;
            //reset distances from 'src' node:
            if (j==0 && cities.size()>=2)   //if first loop AND have >= 2 node in cities:
                comparTo = (NodeDataClass) cities.get(1);
            else
                comparTo = (NodeDataClass) cities.get(0);
            findMaxDist((NodeDataClass) src, comparTo);

            for (int i = 0; i < cities.size(); i++)
            {
                NodeData dest = cities.get(i);
                if (!dest.equals(src) && !path.contains(dest))
                {   //not check path from city to itself AND search only for cities that not in the path yet
                    cost = Double.parseDouble(dest.getInfo());
                    if (cost < minCost)
                    {
                        List<NodeData> minPath = shortestPath(src.getKey(), dest.getKey());
                        minPath.remove(0);
                        minCost = cost;
                        tempPath = minPath;
                    }
                }
            }
            for (int i = 0; i < tempPath.size(); i++)
            {   // add the chosen path to cost and to path
                path.add(tempPath.get(i));
            }
            tempPath.clear();
            finalCost += minCost;
        }
        return path;
    }


    @Override
    public boolean save(String file) {  //Todo: not sure if the json need to be in specific format ?
        return serialize(file);
    }

    private boolean serialize(String file) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        HashMap<String, HashMap> h = new HashMap();
        h.put("_Nodes", _graph.getNodeHash());
        h.put("_Edges", _graph.getEdgeHash());
        String json = gson.toJson(h);

        //Write JSON to file
        try
        {
            PrintWriter pw = new PrintWriter(new File(file));
            pw.write(json);
            pw.close();
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean load(String file) {
        DirectedWeightedGraphClass g =  deserialize(file);
        if (g == null)
            return false;
        else {
            _graph = g;
            return true;
        }
    }


    private DirectedWeightedGraphClass deserialize(String file)
    {
        try
        {
            GsonBuilder builder = new GsonBuilder();
            GraphJsonDeserializer Deserializer = new GraphJsonDeserializer();
            builder.registerTypeAdapter(DirectedWeightedGraphClass.class, Deserializer);
            Gson gson = builder.create();
            FileReader reader = new FileReader(file);
            DirectedWeightedGraphClass graph = gson.fromJson(reader, DirectedWeightedGraphClass.class);
            return graph;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public double[] maxXY() {
        double maxX=Double.MIN_VALUE, maxY=Double.MIN_VALUE;
        for (Iterator<NodeData> it = _graph.nodeIter(); it.hasNext(); ) {
            NodeDataClass n = (NodeDataClass) it.next();
            GeoLocationClass g = (GeoLocationClass) n.getLocation();
            if (g.x() > maxX)
                maxX = g.x();
            if (g.y() > maxY)
                maxY = g.y();
        }
        double[] ans = {maxX, maxY};
        return ans;
    }

    public double[] minXY() {
        double minX=Double.MAX_VALUE, minY=Double.MAX_VALUE;
        for (Iterator<NodeData> it = _graph.nodeIter(); it.hasNext(); ) {
            NodeDataClass n = (NodeDataClass) it.next();
            GeoLocationClass g = (GeoLocationClass) n.getLocation();
            if (g.x() < minX)
                minX = g.x();
            if (g.y() < minY)
                minY = g.y();
        }
        double[] ans = {minX, minY};
        return ans;
    }

}
