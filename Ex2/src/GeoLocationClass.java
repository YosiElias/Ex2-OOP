import api.GeoLocation;

public class GeoLocationClass implements GeoLocation {
    private double _x;
    private double _y;
    private double _z;

    public GeoLocationClass(double x, double y, double z){
        this._x = x;
        this._y = y;
        this._z = z;
    }

    // Copy constructor:
    public GeoLocationClass(GeoLocation p) {
        this._x = p.x();
        this._y = p.y();
        this._z = p.z();
    }


    @Override
    public double x() {
        return this._x;
    }

    @Override
    public double y() {
        return this._y;
    }

    @Override
    public double z() {
        return this._z;
    }

    @Override
    public double distance(GeoLocation g) {
        return Math.sqrt(Math.pow(this._x - g.x(), 2)
                + Math.pow(this._y - g.y(), 2)
                + Math.pow(this._z - g.z(), 2));
    }

    //only for self testing:
    @Override
    public String toString() {
        return "(" +
                "" + _x +
                ", " + _y +
                ", " + _z +
                ')';
    }
}
