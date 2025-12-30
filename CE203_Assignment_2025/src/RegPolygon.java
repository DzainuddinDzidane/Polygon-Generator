import java.awt.*;

public class RegPolygon implements Comparable<RegPolygon>{

    Color pColor = Color.BLACK;
    int pId = 000000;
    int pSides;
    double pStarting_angle;
    double pRadius;
    int polyCenX;
    int polyCenY;
    double [] pointsX;
    double [] pointsY;

    //Polygon Constructor
    public RegPolygon(int id, int p_sides, double st_angle, double rad, Color color) {
        this.pId = id;
        this.pSides = p_sides;
        this.pStarting_angle = st_angle;
        this.pRadius = rad;
        this.pColor = color;


        pointsX = new double[pSides];
        pointsY = new double[pSides];
    }

    //calculates all vertex coordinates for user created polygon using trigonometry
    private Polygon getPolygonPoints(Dimension dim) {

        polyCenX = dim.width / 2;
        polyCenY = dim.height / 2;
        double angleIncrement = 2 * Math.PI / pSides;
        Polygon p = new Polygon();
        double angle = Math.toRadians(pStarting_angle);

        for(int i = 0; i < pSides; i++)
        {
            double x = polyCenX + pRadius * Math.cos(angle);
            double y = polyCenY + pRadius * Math.sin(angle);

            pointsX[i] = x;
            pointsY[i] = y;

            p.addPoint((int)x, (int)y);

            angle += angleIncrement;
        }
        return p;
    }

    //Draws Polygon
    public void drawPolygon(Graphics2D g, Dimension d) {
        Polygon p = getPolygonPoints(d);
        g.setColor(pColor);
        g.fillPolygon(p);
        g.setColor(Color.black);
        g.drawPolygon(p);
    }

    //gets polygon id so other classes can read it
    public int getID() {
        return pId;
    }

    @Override
    //Used by handleSortButton to sort polygon based in ascending order of their ID
    public int compareTo(RegPolygon o) {
        return Integer.compare(this.pId, o.pId);
    }

    //used by handleSortButton to print out available polygons
    public String toString()
    {
        return "Polygon ID: " + pId +
                "\n Sides: " + pSides +
                "\n Start Angle: " + pStarting_angle +
                "\n Radius: " + pRadius +
                "\n Color: " + pColor;
    }
}
