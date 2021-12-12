import java.awt.*;

public class EdgeDataClass implements api.EdgeData {
    private int _src;
    private int _dest;
    private double _weight;
    private Color color = Color.MAGENTA;


    public EdgeDataClass(int src, int dest, double weight){
        this._src = src;
        this._dest = dest;
        this._weight = weight;
    }

    public EdgeDataClass(EdgeDataClass other) {
        this._src = other._src;
        this._dest = other._dest;
        this._weight = other._weight;
        this.color = other.color;
    }


    @Override
    public int get_src() {
        return this._src;
    }

    @Override
    public int get_dest() {
        return this._dest;
    }

    @Override
    public double get_weight() {
        return this._weight;
    }

    @Override
    public String getInfo() {   //Todo: is relevant ?
        return null;
    }

    @Override
    public void setInfo(String s) {

    }

    @Override
    public int getTag() {   //Todo: is relevant ?
        return 0;
    }

    @Override
    public void setTag(int t) {

    }

    //only for self testing:
    @Override
    public String toString() {
        return "{" +
                "_src=" + _src +
                ", _dest=" + _dest +
                ", _weight=" + _weight +
                '}'+"\t";
    }

    //only for self testing of deep copy:
    @Override
    public boolean equals(Object obj) {
        EdgeDataClass ePther = (EdgeDataClass) obj;
        return this.color.equals(ePther.color) &&
                this._weight == ePther._weight &&
                this._dest == ePther._dest &&
                this._src == ePther._src;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
