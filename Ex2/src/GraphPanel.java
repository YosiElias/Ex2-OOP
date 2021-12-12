import api.*;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class GraphPanel extends JPanel {

    private DWGalgo _algo;
    private DirectedWeightedGraphClass _graph;
    private String message;
    private boolean _clean = true;

    public GraphPanel(DWGalgo algo, DirectedWeightedGraph graph)
    {
        super();
        _algo = algo;
        _graph = (DirectedWeightedGraphClass) graph;
        this.setBackground(new Color(7, 43, 73)); //change color of background
        message = "Welcome! To Load a graph press File -> Load Graph";
    }
    public void reset() {
        repaint();
    }

    @Override
    public void paintComponent(Graphics g)
    {
//        System.out.println("GET TO PAINTCOMPONENT   "+_graph);    //only for self testing
        super.paintComponent(g);
        g.setFont(new Font("MV Boli",Font.PLAIN,25)); //set font of text
        g.setColor(Color.white);
        g.drawString(message, 100,100);

        if (!_clean) {
            plotGraph(g);
        }
    }

    private void plotGraph(Graphics g) {
        if (_graph==null)
            return;
        for (Iterator<NodeData> it = _graph.nodeIter(); it.hasNext(); ) {
            NodeDataClass n = (NodeDataClass) it.next();
            GeoLocationClass gLocScale = (GeoLocationClass) n.get_gLocScale();
            g.setColor(n.getColor());
            if (n.getColor().equals(new Color(56, 251, 56))) {  // if canter node:
                g.setFont(new Font("MV Boli", Font.TRUETYPE_FONT, 15)); //set font of text
                g.drawString("CENTER NODE", (int) (gLocScale.x() - 5), (int) (gLocScale.y() - 5));
                g.fillOval((int) gLocScale.x() - 6, (int) gLocScale.y() - 6, 11, 11);
            }
            else
                g.fillOval((int) gLocScale.x() - 5, (int) gLocScale.y() - 5, 10, 10);
        }
        boolean first = true;
        for (Iterator<EdgeData> it = _graph.edgeIter(); it.hasNext(); ) {
            EdgeDataClass e = (EdgeDataClass) it.next();
            NodeDataClass dest = (NodeDataClass) _graph.getNode(e.get_dest());
            NodeDataClass src = (NodeDataClass) _graph.getNode(e.get_src());
            drawArrowHead((Graphics2D) g, dest, src, e.getColor());
            GeoLocation gScaleTip = dest.get_gLocScale();
            GeoLocation gScaleTail = src.get_gLocScale();
            if (_graph.getEdge(e.get_dest(), e.get_src()) != null) {    //color the other line in between this nodes
                EdgeDataClass otherE = (EdgeDataClass) _graph.getEdge(e.get_dest(), e.get_src());
                if (otherE.getColor().equals(new Color(251, 219, 23)) || otherE.getColor().equals(new Color(12, 243, 224)))
                    g.setColor(otherE.getColor());
            }
            else
                g.setColor(e.getColor());
            g.drawLine((int) gScaleTail.x(), (int) gScaleTail.y(),
                    (int) gScaleTip.x(), (int) gScaleTip.y());
            if (e.getColor().equals(new Color(251, 219, 23)) && first) {  // if short path:
                g.setFont(new Font("MV Boli", Font.TRUETYPE_FONT, 15)); //set font of text
                g.drawString("SHORT PATH", (int) ((gScaleTail.x() + gScaleTip.x()) / 2)+6, (int) ((gScaleTail.y() + gScaleTip.y()) / 2)+6);
                first = false;
            }
            if (e.getColor().equals(new Color(12, 243, 224)) && first) {  // if TSP path:
                g.setFont(new Font("MV Boli", Font.TRUETYPE_FONT, 15)); //set font of text
                g.drawString("TSP PATH", (int) ((gScaleTail.x() + gScaleTip.x()) / 2)+6, (int) ((gScaleTail.y() + gScaleTip.y()) / 2)+6);
                first = false;
            }
        }
    }


    private void drawArrowHead(Graphics2D g2, NodeDataClass tip, NodeDataClass tail, Color color)
    {
        int barb;
        if (color.equals(Color.magenta))
            barb = 10;
        else
            barb = 15;
        double phi = Math.toRadians(30);

        g2.setPaint(color);
        GeoLocation gScaleTip = tip.get_gLocScale();
        GeoLocation gScaleTail = tail.get_gLocScale();
        double dy = gScaleTip.y() -  gScaleTail.y();
        double dx = gScaleTip.x() -  gScaleTail.x();
        double theta = Math.atan2(dy, dx);
        double x, y, rho = theta + phi;
        for(int j = 0; j < 2; j++)
        {
            x = gScaleTip.x() - barb * Math.cos(rho);
            y = gScaleTip.y() - barb * Math.sin(rho);
            g2.draw(new Line2D.Double(gScaleTip.x(), gScaleTip.y(), x, y));
            rho = theta - phi;
        }
    }

    public void set_graph(DirectedWeightedGraphClass graph) {
        this._graph = graph;
        message = "";
        double[] maxXY = _algo.maxXY();   //'z' not relevant
        double[] minXY = _algo.minXY();   //'z' not relevant
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        int widht = Toolkit.getDefaultToolkit().getScreenSize().width;
        for (Iterator<NodeData> it = _graph.nodeIter(); it.hasNext(); ) {   //Todo: kind of test to Iterator
            NodeDataClass n = (NodeDataClass) it.next();
            n.set_gLocScale(height, widht, maxXY, minXY);
        }
    }


    public void set_clean(boolean b) {
        if (b==true) {
            _graph = null;
            message = "Graph cleaned";
        }
        _clean = b;
    }

    public void shortestPathFunc(int src, int dest) {
        if (_graph == null)
            return;
        resrtColor();
        List<NodeData> ans = _algo.shortestPath(src, dest);
        NodeDataClass prev = null;
        String strAns = "";
        if (ans==null){
            strAns = " EROR: There is no way to reach the destination from the source ! ";
        }
        else {
            for (int i = 0; i < ans.size(); i++) {
                NodeDataClass n = (NodeDataClass) ans.get(i);
                if (i == ans.size() - 1)
                    strAns += n.getKey();
                else
                    strAns += n.getKey() + " -> ";
                n.setColor(new Color(251, 219, 23));
                if (prev != null) //if not first node:
                {
                    EdgeDataClass e = (EdgeDataClass) _graph.getEdge(prev.getKey(), n.getKey());
                    e.setColor(new Color(251, 219, 23));
                }
                prev = n;
            }
        }
        GuiGraph.infoBox("The list of shortest Path is: \n"+strAns, "Shortest Path", JOptionPane.PLAIN_MESSAGE);
        repaint();
    }

    public void shortestPathDistFunc(int src, int dest) {
        if (_graph == null)
            return;
        resrtColor();
        double ans = _algo.shortestPathDist(src, dest);
        GuiGraph.infoBox("The dist of shortest Path is: \n"+ans, "Shortest Path Dist", JOptionPane.PLAIN_MESSAGE);
    }

    public void centerFunc() {
        if (_graph == null)
            return;
        resrtColor();
        NodeDataClass n = (NodeDataClass) _algo.center();
        if (n==null)
            GuiGraph.infoBox("The center node is: \nnull\nThe graph is not connected so there is no center node", "Center Node", JOptionPane.PLAIN_MESSAGE);
        else {
            n.setColor(new Color(56, 251, 56));
            getGraphics().setColor(Color.RED);
//        getGraphics().setFont(new Font("MV Boli", Font.TRUETYPE_FONT, 15)); //set font of text
//        getGraphics().drawString("CENTER NODE", (int) (n.get_gLocScale().x() - 5), (int) (n.get_gLocScale().y() - 5));
            GuiGraph.infoBox("The center node is: \n" + n.getKey(), "Center Node", JOptionPane.PLAIN_MESSAGE);
            repaint();
        }
    }

    public void tspFunc(List<Integer> citiesID) {
        if (_graph == null)
            return;
        resrtColor();
        List<NodeData> cities = new ArrayList<>();
        for (int i = 0; i < citiesID.size(); i++) {
            cities.add(_graph.getNode(citiesID.get(i)));
        }
        List<NodeData> ans = _algo.tsp(cities);
        String strAns = "";
        NodeDataClass prev = null;
        for (int i = 0; i < ans.size(); i++)
        {
            NodeDataClass n = (NodeDataClass) ans.get(i);
            if (i == ans.size() - 1)
                strAns += n.getKey();
            else
                strAns += n.getKey() + " -> ";
            n.setColor(new Color(12, 243, 224));
            if (prev != null) //if not first node:
            {
                //Todo: to add try-cath for any case
                EdgeDataClass e = (EdgeDataClass) _graph.getEdge(prev.getKey(), n.getKey());
                e.setColor(new Color(12, 243, 224));
            }
            prev = n;
        }
        GuiGraph.infoBox("The list of \'tsp\' is: \n"+strAns, "Tsp", JOptionPane.PLAIN_MESSAGE);
        repaint();
    }

    private void resrtColor() {
        for (Iterator<EdgeData> it = _graph.edgeIter(); it.hasNext(); ) {
            EdgeDataClass e = (EdgeDataClass) it.next();
            e.setColor(Color.MAGENTA);
        }
        for (Iterator<NodeData> it = _graph.nodeIter(); it.hasNext(); ) {
            NodeDataClass n = (NodeDataClass) it.next();
            n.setColor(Color.white);
        }
    }


    public void addNodeFunc(double x, double y, double z, int id) {
        if (_graph == null)
            return;
        resrtColor();
        NodeData n = new NodeDataClass(id, x, y, z);
        _graph.addNode(n);
        this.set_graph(_graph);
        repaint();
    }

    public void deletNodeFunc(int id) {
        if (_graph == null)
            return;
        resrtColor();
        NodeData n = _graph.removeNode(id);
        this.set_graph(_graph);
        if (n!= null)
            GuiGraph.infoBox("Delete of node  "+n.getKey()+"  is compete :)", "Delete Node", JOptionPane.PLAIN_MESSAGE);
        else
            GuiGraph.infoBox("Delete of node  "+id+"  is not success !\nPlease check that the edge exists", "Delete Node", JOptionPane.PLAIN_MESSAGE);
        repaint();
    }

    public void addEdgeFunc(int src, int dest, double w) {
        if (_graph == null)
            return;
        resrtColor();
        _graph.connect(src, dest, w);
        this.set_graph(_graph);
        repaint();
    }

    public void deletEdgeFunc(int src, int dest) {
        if (_graph == null)
            return;
        resrtColor();
        EdgeDataClass e = (EdgeDataClass) _graph.removeEdge(src, dest);
        this.set_graph(_graph);
        if (e != null)
            GuiGraph.infoBox("Delete of edge  "+e.get_src()+"->"+e.get_dest()+"  is compete :)", "Delete Edge", JOptionPane.PLAIN_MESSAGE);
        else
            GuiGraph.infoBox("Delete of edge  "+src+"->"+dest+"  is not success !\nPlease check that the edge exists", "Delete Edge", JOptionPane.PLAIN_MESSAGE);

        repaint();
    }

    public void set_algo(DirectedWeightedGraphClass graph) {
        _algo.init(graph);
    }
}
