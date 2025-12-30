import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ContainerFrame extends JFrame{

    //public so that handlers in ContainerButtonHandler can use these attributes
    public JTextField idField;
    public JTextField sidesField;
    public JTextField angleField;
    public JTextField radiusField;
    public Color selectColor;
    public ArrayList<RegPolygon> polygonsList =  new ArrayList<>();
    public RegPolygon currentPolygon;
    public JPanel colorPreviewPanel;
    public JButton searchPolygonButton;
    public JButton sortButton;
    public JButton chooseColorButton;
    public JButton addPolygonButton;
    public JButton removePolygonButton;

    //getter method used by ContainerPanel to draw polygon
    public RegPolygon getCurrentPolygon() {
        return currentPolygon;
    }

    //creates apps UI interface
    public void createComponents() {
        //sets layout for text fields
        JPanel topPanel = new JPanel();
        topPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        topPanel.setLayout(new GridLayout(2,6,6, 6));

        //adds the text field for user input
        idField = new JTextField(6);
        sidesField = new JTextField(4);
        angleField = new JTextField(5);
        radiusField = new JTextField(5);
        colorPreviewPanel = new JPanel();

        //adds buttons for user to use and adds a use for ContainerButtonHandler
        chooseColorButton = new JButton("Color");
        addPolygonButton = new JButton("Add Polygon");
        removePolygonButton = new JButton("Remove Polygon");
        searchPolygonButton = new JButton("Search Polygon");
        sortButton = new JButton("Sort Polygons");

        //adds a label on what each text field does
        topPanel.add(new JLabel("Enter 6-Digit ID:"));
        topPanel.add(new JLabel("Enter Sides:"));
        topPanel.add(new JLabel("Start Angle:"));
        topPanel.add(new JLabel("Enter Radius:"));
        topPanel.add(new JLabel("Select Color:"));
        topPanel.add(new JLabel("Color Preview:"));

        //adds text field to the layout made for them. Added to top of app
        topPanel.add(idField);
        topPanel.add(sidesField);
        topPanel.add(angleField);
        topPanel.add(radiusField);
        topPanel.add(chooseColorButton);
        topPanel.add(colorPreviewPanel);
        add(topPanel, BorderLayout.NORTH);

        //adds button panel for the buttons on the bottom of app
        JPanel butPanel = new JPanel();
        butPanel.add(addPolygonButton);
        butPanel.add(removePolygonButton);
        butPanel.add(searchPolygonButton);
        butPanel.add(sortButton);
        add(butPanel, BorderLayout.SOUTH);

        //sets the default colour of the color preview panel and size of panel
        colorPreviewPanel.setPreferredSize(new Dimension(30,30));
        colorPreviewPanel.setBackground(Color.LIGHT_GRAY);
        colorPreviewPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        //calls out all handlers from ContainerButtonHandler
       ContainerButtonHandler cbh = new ContainerButtonHandler(this);
       chooseColorButton.addActionListener(cbh);
       addPolygonButton.addActionListener(cbh);
       removePolygonButton.addActionListener(cbh);
       searchPolygonButton.addActionListener(cbh);
       sortButton.addActionListener(cbh);

       //creates container for where the polygon should be drawn. which is the middle of app
        JPanel drawPanel = new ContainerPanel(this);
        add(drawPanel, BorderLayout.CENTER);

        //set app's size, visible to users, and closes app when user press 'X'
        setSize(700,500);
        setVisible(true);
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    }
}
