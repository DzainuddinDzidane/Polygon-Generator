import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Dimension;

public class ContainerPanel extends JPanel{

    ContainerFrame conFrame;

    //Constructor to save reference to main frame so panel can reach which polygon should be drawn
    public ContainerPanel(ContainerFrame cf) {
        conFrame = cf;
    }

    //Method to paint Polygon
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D comp = (Graphics2D)g;
        Dimension size = getSize();

        //retrieves whichever polygon user searched or added
        RegPolygon poly = conFrame.getCurrentPolygon();

        //Prevents errors if no polygon is selected
        if (poly != null) {
            poly.drawPolygon(comp, size);
        }
    }
}
