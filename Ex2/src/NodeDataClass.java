import api.GeoLocation;
import api.NodeData;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class NodeDataClass implements NodeData {
    private int _key;
    private GeoLocation _gLoc;
    private GeoLocation _gLocScale;
    private Color color = Color.white;
    //    private List<NodeDataClass> pathList;
    private int tag;
    private String info;
    private double maxDist = Double.MAX_VALUE;
    private boolean mark;


    public NodeDataClass(int id, double x, double y, double z) {
        this.mark = false;

        //for NodeDataClass:
        this._key = id;
        this._gLoc = new GeoLocationClass(x, y, z);
//        this.pathList = new ArrayList<>();
        this.tag = -1;
        this.info = "" + (-1);
    }

    public NodeDataClass(NodeDataClass other) {
        this._key = other._key;
        this._gLoc = new GeoLocationClass(other._gLoc.x(), other._gLoc.y(), other._gLoc.z());
        if (other.get_gLocScale() != null)
            this._gLocScale = new GeoLocationClass(other._gLocScale.x(), other._gLocScale.y(), other._gLocScale.z());
        this.color = other.color;
        this.info = new String(other.info);
        this.tag = other.tag;
        this.maxDist = other.maxDist;
    }

    @Override
    public int getKey() {
        return this._key;
    }

    @Override
    public GeoLocation getLocation() {
        return this._gLoc;
    }

    @Override
    public void setLocation(GeoLocation p) {
        this._gLoc = new GeoLocationClass(p);   //use copy constructor
    }

    @Override
    public double getWeight() {    //Todo: changed !
        return Double.parseDouble(this.getInfo());
    }

    @Override
    public void setWeight(double w) {   //Todo: changed !
        setInfo(""+w);
    }


    @Override
    public String getInfo() {   //Todo: is relevant ?
        return this.info;
    }

    @Override
    public void setInfo(String s) {   //Todo: is relevant ?
        this.info=s;
    }

    @Override
    public int getTag() {   //Todo: is relevant ?
        return this.tag;
    }

    @Override
    public void setTag(int t) {   //Todo: is relevant ?
        this.tag=t;
    }


    //Todo: only for self testing:
    @Override
    public String toString() {
        return "{" +
                "_key=" + _key +
                " wight= "+ this.info+
                '}'+"\t";
    }

    public void set_gLocScale(int height, int widht, double[] maxXY, double[] minXY) {
        double maxX = maxXY[0];
        double maxY = maxXY[1];
        double minX = minXY[0];
        double minY = minXY[1];
        double x = (_gLoc.x() - minX) * widht/(maxX - minX) * 0.95 + 5;
        double y = (_gLoc.y() - minY) * height /(maxY-minY) * 0.95 + 5;
        this._gLocScale = new GeoLocationClass(x, y, _gLoc.z());
    }

    public GeoLocation get_gLocScale() {
        return _gLocScale;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public double getMaxDist() {
        return maxDist;
    }

    public void setMaxDist(double maxDist) {
        this.maxDist = maxDist;
    }

    @Override   //for testing of deep copy:
    public boolean equals(Object obj) {
        NodeDataClass other = (NodeDataClass) obj;
        return this._gLoc.x() == other._gLoc.x() &&
                this._gLoc.y() == other._gLoc.y() &&
                this._gLoc.z() == other._gLoc.z() &&
                this.info.equals(other.info) &&
                this._key==other._key &&
                this.color.equals(other.color) &&
                this.maxDist==other.maxDist &&
                this.tag== other.tag;
    }

    void set_wight(double x) {
        this.setWeight(x);  //Todo: changed !
//        setInfo(""+x);
    }

    double get_wight() {
        return Double.parseDouble(this.getInfo());
    }

    void set_mark(boolean m) {
        this.mark = m;
    }

    boolean get_mark() {
        return this.mark;
    }
}
