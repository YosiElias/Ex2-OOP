import api.DirectedWeightedGraph;
import api.EdgeData;
import api.NodeData;

import java.util.*;

public class DirectedWeightedGraphClass implements DirectedWeightedGraph {
    private Map<Integer, NodeData> _Nodes;
    private Map<String, EdgeData> _Edges;
    private Map<Integer, Map<String, EdgeData>> _neighborsOfNode;
    private Map<Integer, Map<String, EdgeData>> _neighborsFromNode;
    private Map<Integer, Map<String, EdgeData>> _neighborsToNode;
    private int MC=0;

    public DirectedWeightedGraphClass(){
        _Edges = new HashMap<String, EdgeData>();
        _Nodes = new HashMap<Integer, NodeData>();
        _neighborsOfNode = new HashMap<Integer, Map<String, EdgeData>>();
        _neighborsFromNode = new HashMap<Integer, Map<String, EdgeData>>();
        _neighborsToNode = new HashMap<Integer, Map<String, EdgeData>>();
    }

    //deep copy constructor:
    public DirectedWeightedGraphClass(DirectedWeightedGraphClass other) {

        _Edges = new HashMap<String, EdgeData>();
        _Nodes = new HashMap<Integer, NodeData>();
        _neighborsOfNode = new HashMap<Integer, Map<String, EdgeData>>();
        _neighborsFromNode = new HashMap<Integer, Map<String, EdgeData>>();
        _neighborsToNode = new HashMap<Integer, Map<String, EdgeData>>();

        for (Iterator<NodeData> it = other.nodeIter(); it.hasNext(); ) {
            NodeDataClass nOther = (NodeDataClass) it.next();
            NodeData n= new NodeDataClass(nOther);
            this.addNode(n);
        }
        for (Iterator<EdgeData> it = other.edgeIter(); it.hasNext(); ) {
            EdgeDataClass eOther = (EdgeDataClass) it.next();
            EdgeDataClass e = new EdgeDataClass(eOther);
            this.connect(e.get_src(), e.get_dest(), e.get_weight());
        }
        this.MC=0;
    }

    public HashMap<String,EdgeData> getEdgeHash(){
        return (HashMap<String, EdgeData>) this._Edges;
    }

    public HashMap<Integer, NodeData> getNodeHash(){
        return (HashMap<Integer, NodeData>) this._Nodes;
    }

    public HashMap<String, EdgeData> getNeighboursFromHash(int key){
        return (HashMap<String, EdgeData>) this._neighborsFromNode.get(key);
    }
    public  HashMap<String, EdgeData> getNeighboursToHash(int key){
        return (HashMap<String, EdgeData>) this._neighborsToNode.get(key);
    }

    @Override
    public NodeData getNode(int key) {
        return _Nodes.get(key);
    }

    @Override
    public EdgeData getEdge(int src, int dest) {
        return _Edges.get(""+src+"-"+dest);
    }

    /**
     * add to Map is O(1)
     * @param n NodeData to add
     */
    @Override
    public void addNode(NodeData n) {
        _Nodes.put(n.getKey(), (NodeDataClass) n);
        _neighborsFromNode.put(n.getKey(), new HashMap<String, EdgeData>());
        _neighborsToNode.put(n.getKey(), new HashMap<String, EdgeData>());
        _neighborsOfNode.put(n.getKey(), new HashMap<String, EdgeData>());
        MC++;
    }

    /**
     * Connects an edge with weight w between node src to node dest.
     * Add to Map is O(1), and add to list is also O(1) - Amortized analysis
     * @param src - the source of the edge.
     * @param dest - the destination of the edge.
     * @param w - positive weight representing the cost (aka time, price, etc) between src-->dest.
     */
    @Override
    public void connect(int src, int dest, double w) {
        EdgeDataClass e = new EdgeDataClass(src, dest, w);
        _Edges.put(""+src+"-"+dest, e);
        _neighborsFromNode.get(src).put(""+src+"-"+dest, e);
        _neighborsToNode.get(dest).put(""+src+"-"+dest, e);
        _neighborsOfNode.get(src).put(""+src+"-"+dest, e);
        _neighborsOfNode.get(dest).put(""+src+"-"+dest, e);
        MC++;
    }


    @Override
    public Iterator<NodeData> nodeIter() {
        return new Iterator<NodeData>() {
            Iterator<NodeData> iter = _Nodes.values().iterator();
            private NodeData cur = null;
            private NodeData lst = null;
            private NodeData temp = null;
            private int N_ITERMC = MC;

            @Override
            public boolean hasNext() {
                if (N_ITERMC != MC) {
                    throw new RuntimeException("the graph has changed");
                }
                return iter.hasNext();
            }

            @Override
            public NodeData next() {
                if (N_ITERMC != MC) {
                    throw new RuntimeException("the graph has changed");
                }
                cur = iter.next();;
                return cur;
            }
            @Override
            public void remove() {
                if (N_ITERMC != MC) {
                    throw new RuntimeException("the graph has changed");
                } else if (cur!=null){
                    Iterator<NodeData> iterLast = _Nodes.values().iterator();
                    temp = null;
                    while (iterLast.hasNext() && temp != cur) { //for case of remove
                        lst = temp;
                        temp = iterLast.next();
                    }

                    int keyCur = cur.getKey();
                    removeNode(keyCur);
                    iter = _Nodes.values().iterator();
                    temp = null;
                    while (iter.hasNext() && temp != lst) { //for case of remove
                        temp = iter.next();
                    }
                    cur = temp;
                    N_ITERMC = MC;
                }
            }
        };
    }

    @Override
    public Iterator<EdgeData> edgeIter() {
        return new Iterator<EdgeData>() {
            Iterator<EdgeData> e_iter =_Edges.values().iterator();
            private EdgeData cur = null;
            private EdgeData lst = null;
            private EdgeData temp = null;
            private int E_ITERMC=MC;
            @Override
            public boolean hasNext() {
                if(E_ITERMC!=MC){
                    throw new RuntimeException("the graph has changed");
                }
                return e_iter.hasNext();
            }

            @Override
            public EdgeData next() {
                if(E_ITERMC!=MC){
                    throw new RuntimeException("the graph has changed");
                }
                cur = e_iter.next();
                return cur;
            }

            @Override
            public void remove() {
                if (E_ITERMC != MC) {
                    throw new RuntimeException("the graph has changed");
                } else if (cur!=null){
                    Iterator<EdgeData> iterLast = _Edges.values().iterator();
                    temp = null;
                    while (iterLast.hasNext() && temp != cur) { //for case of remove, go to last
                        lst = temp;
                        temp = iterLast.next();
                    }
                    removeEdge(cur.get_src(), cur.get_dest());
                    e_iter = _Edges.values().iterator();
                    temp = null;
                    while (e_iter.hasNext() && temp != lst) { //go to new current
                        temp = e_iter.next();
                    }
                    cur = temp;
                    E_ITERMC = MC;
                }
            }
        };
    }

    @Override
    public Iterator<EdgeData> edgeIter(int node_id) {
        return new Iterator<EdgeData>() {
            Iterator<EdgeData> e_iter =_neighborsFromNode.get(node_id).values().iterator();
            private EdgeData cur = null;
            private EdgeData lst = null;
            private EdgeData temp = null;
            private int E_ITERMC=MC;
            @Override
            public boolean hasNext() {
                if(E_ITERMC!=MC){
                    throw new RuntimeException("the graph has changed");
                }
                return e_iter.hasNext();
            }

            @Override
            public EdgeData next() {
                if(E_ITERMC!=MC){
                    throw new RuntimeException("the graph has changed");
                }
                cur = e_iter.next();
                return cur;
            }

            @Override
            public void remove() {
                if (E_ITERMC != MC) {
                    throw new RuntimeException("the graph has changed");
                } else if (cur!=null){
                    Iterator<EdgeData> iterLast =_neighborsFromNode.get(node_id).values().iterator();
                    temp = null;
                    while (iterLast.hasNext() && temp != cur) { //go to last
                        lst = temp;
                        temp = iterLast.next();
                    }
                    removeEdge(cur.get_src(), cur.get_dest());
                    e_iter = _neighborsFromNode.get(node_id).values().iterator();
                    temp = null;
                    while (e_iter.hasNext() && temp != lst) { //go to new current
                        temp = e_iter.next();
                    }
                    cur = temp;
                    E_ITERMC = MC;
                }
            }
        };
    }

    @Override
    public NodeData removeNode(int key) {
        NodeData n = _Nodes.get(key);
        _Nodes.remove(key);
        for (EdgeData e : _neighborsOfNode.get(key).values()) {  //runing time: O(k)
            _Edges.remove(""+e.get_src()+"-"+e.get_dest() );
        }
        _neighborsOfNode.remove(key);
        _neighborsFromNode.remove(key);
        _neighborsToNode.remove(key);
        MC++;
        return n;
    }

    @Override
    public EdgeData removeEdge(int src, int dest) {
        EdgeData e = _Edges.get(""+src+"-"+dest);
        _Edges.remove(""+src+"-"+dest);
        _neighborsOfNode.get(src).remove(""+src+"-"+dest);
        _neighborsOfNode.get(dest).remove(""+src+"-"+dest);
        _neighborsFromNode.get(src).remove(""+src+"-"+dest);
        _neighborsToNode.get(dest).remove(""+src+"-"+dest);
        MC++;
        return e;
    }

    @Override
    public int nodeSize() {
        return _Nodes.size();
    }

    @Override
    public int edgeSize() {
        return _Edges.size();
    }

    @Override
    public int getMC() {
        return this.MC;
    }



    //only for self testing:
    @Override
    public String toString() {
        return "Nodes: "+_Nodes.toString() + "\n"+
                "Edges: "+_Edges.toString();
    }
}
