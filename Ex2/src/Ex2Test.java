import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;
import api.EdgeData;
import api.NodeData;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Time;
import java.util.*;

class Ex2Test {
    DirectedWeightedGraphAlgorithms algo;
    String j1 = "data/G1.json";
    String j2 = "data/G2.json";
    String j3 = "data/G3.json";
    String j4 = "data/G4.json";     //this is json we write for small testing
    String j1k = "data/1000Nodes.json";
    String j10k = "data/10000Nodes.json";
    String j100k = "data/100000.json";

    @org.junit.jupiter.api.Test
    void getGrapg() {
        DirectedWeightedGraph g = Ex2.getGrapg(j1);
        System.out.println(g.getNode(0));
    }

    @org.junit.jupiter.api.Test
    void getGrapgAlgo() {
        DirectedWeightedGraphAlgorithms algo = Ex2.getGrapgAlgo(j1);
        Assert.assertFalse(algo.equals(null));
        System.out.println(algo.getGraph());
    }

    @org.junit.jupiter.api.Test
    void serializeTest() {
        DirectedWeightedGraphAlgorithms algo = Ex2.getGrapgAlgo(j1);
        Assert.assertFalse(algo.equals(null));  //check that load was success
        Assert.assertTrue(algo.save("data/graphOutput.json"));
    }

    @org.junit.jupiter.api.Test
    void runGUI() {
    }

    @org.junit.jupiter.api.Test
    void centerTest1() {
        algo = Ex2.getGrapgAlgo(j1);
        Assert.assertFalse(algo.equals(null));  //check that load was success
        Assert.assertEquals(algo.center().getKey(), 8);
    }
    @org.junit.jupiter.api.Test
    void centerTest2() {
        algo = Ex2.getGrapgAlgo(j2);
        Assert.assertFalse(algo.equals(null));  //check that load was success
        Assert.assertEquals(algo.center().getKey(), 0);
    }
    @org.junit.jupiter.api.Test
    void centerTest3() {
        algo = Ex2.getGrapgAlgo(j3);
        Assert.assertFalse(algo.equals(null));  //check that load was success
        Assert.assertEquals(algo.center().getKey(), 40);
    }
    @org.junit.jupiter.api.Test
    void centerTest1k() {
        algo = Ex2.getGrapgAlgo(j1k);
        Assert.assertFalse(algo.equals(null));  //check that load was success
        Assert.assertEquals(algo.center().getKey(), 362);
    }
    @org.junit.jupiter.api.Test
    void centerTest10k() {
        algo = Ex2.getGrapgAlgo(j10k);
        Assert.assertFalse(algo.equals(null));  //check that load was success
        Assert.assertEquals(algo.center().getKey(),  3846);
    }
    @org.junit.jupiter.api.Test
    void centerTest100k() {
        algo = Ex2.getGrapgAlgo(j100k);
        Assert.assertFalse(algo.equals(null));  //check that load was success
        Assert.assertEquals(algo.center().getKey(),  3846);
    }


    @Test
    void testRunGUI1() {
        Ex2.runGUI(j1);
    }
    @Test
    void testRunGUI12() {
        Ex2.runGUI(j2);
    }
    @Test
    void testRunGUI3() {
        Ex2.runGUI(j3);
    }
    @Test
    void testRunGUI4() {
        Ex2.runGUI(j4);
    }

    @org.junit.jupiter.api.Test
    void intShortestPath1() {
        DirectedWeightedGraphAlgorithms algo4 = Ex2.getGrapgAlgo(j1);
        double sum = algo4.shortestPathDist(0, 1);
        Assertions.assertEquals(1.232037506070033, sum);
    }
    @org.junit.jupiter.api.Test
    void intShortestPath1k() {
        DirectedWeightedGraphAlgorithms algo2 = Ex2.getGrapgAlgo(j1k);
        double sum2 = algo2.shortestPathDist(0,1);
        Assertions.assertEquals(683.6924200182026,sum2);
    }


    @org.junit.jupiter.api.Test
    void ShortestPath100k() {   //Todo: outOfMemory
        DirectedWeightedGraphAlgorithms algo = Ex2.getGrapgAlgo(j100k);
        List<NodeData> list = algo.shortestPath(0, 1);
        NodeData n1 = algo.getGraph().getNode(0);
        NodeData n2 = algo.getGraph().getNode(1);
        List<NodeData> list_ans = new ArrayList<>();
        list_ans.add(n1);
        list_ans.add(n2);
        Assertions.assertEquals(list, list_ans);
    }
    @org.junit.jupiter.api.Test
    void ShortestPath1k() {
        DirectedWeightedGraphAlgorithms algo2 = Ex2.getGrapgAlgo(j1k);
        double sum2 = algo2.shortestPathDist(0,1);
        Assertions.assertEquals(683.6924200182026,sum2);
    }


    @org.junit.jupiter.api.Test
    void connect1() {
        DirectedWeightedGraphAlgorithms algo1 = Ex2.getGrapgAlgo(j1);
        boolean ans1 = algo1.isConnected();
        Assertions.assertEquals(ans1, true);
    }
    @org.junit.jupiter.api.Test
    void connect2() {
        DirectedWeightedGraphAlgorithms algo1 = Ex2.getGrapgAlgo(j2);
        boolean ans1 = algo1.isConnected();
        Assertions.assertEquals(ans1, true);
    }
    @org.junit.jupiter.api.Test
    void connect3() {
        DirectedWeightedGraphAlgorithms algo1 = Ex2.getGrapgAlgo(j3);
        boolean ans1 = algo1.isConnected();
        Assertions.assertEquals(ans1, true);
    }
    @org.junit.jupiter.api.Test
    void connect4() {
        DirectedWeightedGraphAlgorithms algo = Ex2.getGrapgAlgo(j4);
        boolean ans = algo.isConnected();
        Assertions.assertEquals(ans, false);
    }
    @org.junit.jupiter.api.Test
    void connect10k() {
        DirectedWeightedGraphAlgorithms algo1 = Ex2.getGrapgAlgo(j10k);
        boolean ans1 = algo1.isConnected();
        Assertions.assertEquals(ans1, true);
    }
    @org.junit.jupiter.api.Test
    void connect100k() {
        DirectedWeightedGraphAlgorithms algo2 = Ex2.getGrapgAlgo(j100k);
        boolean ans2 =algo2.isConnected();
        Assertions.assertEquals(ans2,true);
    }


    @Test
    void iteratorTest() {
        DirectedWeightedGraphAlgorithms algo;
        algo = Ex2.getGrapgAlgo(j1);
        Assert.assertFalse(algo.equals(null));  //check that load was success
        boolean exceptionThrow = false;

        //test itrerator of Nodes:
        Iterator<NodeData> it1 = algo.getGraph().nodeIter();
        it1.forEachRemaining(node -> System.out.print(node.getKey() +","));     //expect to print "0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,"
        algo.getGraph().removeNode(4);  //make change in graph after get the iterator
        try {
            it1.next();
        } catch (RuntimeException e) {
            exceptionThrow = true;    //the iterator throw NoSuchElementException as we expected
        }
        Assert.assertTrue(exceptionThrow);
        exceptionThrow = false;



        //test itrerator of Edges:
        Iterator<EdgeData> itEdge = algo.getGraph().edgeIter();
        algo.getGraph().removeEdge(0, 1);  //make change in graph after get the iterator
        try {
            itEdge.next();
        } catch (RuntimeException e) {
            exceptionThrow = true;   //the iterator throw NoSuchElementException as we expected
        }
        Assert.assertTrue(exceptionThrow);
        exceptionThrow = false;


        //test itrerator of NeighborsEdges:
        Iterator<EdgeData> itEdgNieg = algo.getGraph().edgeIter(0);
        algo.getGraph().removeNode(1);  //make change in graph after get the iterator
        try {
            itEdgNieg.next();
        } catch (RuntimeException e) {
            exceptionThrow = true;    //the iterator throw NoSuchElementException as we expected
        }
        Assert.assertTrue(exceptionThrow);
    }


    @Test
    void DirectedWeightedGraphCopyTest() {
        DirectedWeightedGraphAlgorithms algo;
        algo = Ex2.getGrapgAlgo(j2);
        Assert.assertFalse(algo.equals(null));  //check that load was success

        DirectedWeightedGraphClass copyAlgo = (DirectedWeightedGraphClass) algo.copy();
        DirectedWeightedGraphClass firstAlgo = (DirectedWeightedGraphClass) algo.getGraph();

        Iterator<NodeData> itCopy = copyAlgo.nodeIter();
        for (Iterator<NodeData> itFirsr = firstAlgo.nodeIter(); itFirsr.hasNext(); ) {
            NodeDataClass firstN = (NodeDataClass) itFirsr.next();
            NodeDataClass copyN = (NodeDataClass) itCopy.next();
            Assert.assertTrue(copyN != firstN);     //check deep copy
            Assert.assertTrue(copyN.equals(firstN));     //check deep copy
        }

        Iterator<EdgeData> copyIt = copyAlgo.edgeIter();
        for (Iterator<EdgeData> firstIt = firstAlgo.edgeIter(); firstIt.hasNext(); ) {
            EdgeDataClass firstE = (EdgeDataClass) firstIt.next();
            EdgeDataClass copyE = (EdgeDataClass) copyIt.next();
            Assert.assertTrue(copyE != firstE && copyE.equals(firstE));     //check deep copy
        }

    }




}