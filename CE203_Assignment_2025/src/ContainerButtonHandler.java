import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

class ContainerButtonHandler implements ActionListener {

    //Uses ContainerFrame so button handler can access attributes
    ContainerFrame theApp;
    ContainerButtonHandler(ContainerFrame app ) {
        theApp = app;
    }

    //method to use the necessary handler when user clicks on a button
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == theApp.chooseColorButton){
            handleChooseColor();
        } else if (source == theApp.addPolygonButton) {
            handleAddPolygon();
        } else if (source == theApp.removePolygonButton) {
            handleRemovePolygon();
        } else if (source == theApp.searchPolygonButton) {
            handleSearchPolygon();
        } else if (source == theApp.sortButton) {
            handleSortPolygon();
        }

        //refreshes the app whenever user does something after using a button
        theApp.repaint();
    }

    //handles the Choose Color Button
    private void handleChooseColor(){
        //pops up a window where use can choose color. default color is black
        Color chosen = JColorChooser.showDialog(theApp,"Choose Color",Color.BLACK);

        //display the chosen color in Color Preview Panel
        if (chosen != null) {
            theApp.selectColor = chosen;
            theApp.colorPreviewPanel.setBackground(theApp.selectColor);
            theApp.colorPreviewPanel.repaint();
            System.out.println("Selected Color: " + theApp.selectColor);
        } else {
            System.out.println("No Color Selected");
        }
    }

    //handles add polygon button
    private void handleAddPolygon() {
        //Reads user inputs as texts. trims text if user adds extra spacing
        String idText = theApp.idField.getText().trim();
        String sidesText = theApp.sidesField.getText().trim();
        String angleText = theApp.angleField.getText().trim();
        String radiusText = theApp.radiusField.getText().trim();

        //display what user inputted in console
        System.out.println("ID: " + idText);
        System.out.println("Sides: " + sidesText);
        System.out.println("Angle: " + angleText);
        System.out.println("Radius: " + radiusText);
        System.out.println("Select Color: " + theApp.selectColor);

      int newPolygonID;

      //error handling block to catch input that's under 6 digits or letters
      try{
          newPolygonID = Integer.parseInt(idText);
          if (idText.length() != 6){
              throw  new NumberFormatException();
          }
      } catch(NumberFormatException e){ //program uses this block when user inputs anything with letters
          System.out.println("Creation Failed! ID strictly must be 6 digits numbers");
          JOptionPane.showMessageDialog(theApp,"Creation Failed! ID strictly must be 6 digits numbers");
          return;
      }

        //Checks list to see if a polygon ID exist when creating a new polygon
        for (RegPolygon p : theApp.polygonsList) {

            //Display error message if it does exist and stops it from getting add to the list
            if (p.getID() == newPolygonID){
                System.out.println("Creation Failed! Polygon ID already exists");
                JOptionPane.showMessageDialog(theApp, "Creation Failed! Polygon ID already exists");
                return;
            }
        }

        try {
            int newSides = Integer.parseInt(sidesText); //converts text into Integer

            //checks if polygon is less than 3, if true then shows error
            if (newSides < 3){
                System.out.println("Creation Failed! A polygon should have at least 3 sides");
                JOptionPane.showMessageDialog(theApp, "Creation Failed! A polygon should have at least 3 sides");
                return;
            }
        } catch (NumberFormatException e) { //display error message if user inputs other than Integer
            System.out.println("Creation Failed!Sides must be a whole number");
            JOptionPane.showMessageDialog(theApp, "Creation Failed! Sides must be a whole number");
            return;
        }

        //same logic
        try {
            Double.parseDouble(angleText);
        } catch (NumberFormatException e) {
            System.out.println("Creation Failed! Angle must be numeric");
            JOptionPane.showMessageDialog(theApp, "Creation Failed! Angle must be numeric");
            return;
        }

        try {
            double newRadius = Double.parseDouble(radiusText);
            if (newRadius <= 0){ //checks if radius is less or equal to 0, shows error message if true
                System.out.println("Creation Failed! Radius must be more than 0");
                JOptionPane.showMessageDialog(theApp, "Creation Failed! Radius must be more than 0");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Creation Failed! Radius must be numeric");
            JOptionPane.showMessageDialog(theApp, "Creation Failed! Radius must be a numeric");
            return;
        }

        //finalizes what the user inputs, before this was just error handling
        int finalId = Integer.parseInt(idText);
        int finalSides = Integer.parseInt(sidesText);
        double finalAngle = Double.parseDouble(angleText);
        double finalRadius = Double.parseDouble(radiusText);
        Color finalColor = theApp.selectColor;

        //uses constructor from RegPolygon to build polygon based on what the user inputted
        RegPolygon newPolygon = new RegPolygon(finalId, finalSides,finalAngle, finalRadius, finalColor);
        theApp.polygonsList.add(newPolygon); //adds new polygon into Array List
        theApp.currentPolygon = newPolygon; //display new user created polygon on screen after repaint() is called
        System.out.println("Polygon " + finalId + " created.");
        theApp.repaint();
    }

    //Handles the remove polygon button
    private void handleRemovePolygon() {
        //creates a list that displays all polygon IDs
        ArrayList<Integer> idList = new ArrayList<>();
        //loops through every single polygon on array list and adds it to newly created list by their ids
        for (RegPolygon p : theApp.polygonsList) {
            idList.add(p.getID());
        }

        //check if there is no polygons, display error message if true
        if (idList.isEmpty()){
            System.out.println("No polygons found.");
            JOptionPane.showMessageDialog(theApp, "No polygons found.");
            return;
        }

        //converts polygon IDs into String
        String[] idArray = new String[idList.size()];
        for (int i = 0; i < idArray.length; i++){
            idArray[i] = String.valueOf(idList.get(i));
        }

        //adds converted IDs into a JList
        JList <String> idJList = new JList<>(idArray);

        //creates text field where user can type polygon ID, max 10 chars
        JTextField idInputField = new JTextField(10);
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        //Created to display a list of available polygon IDs that can be removed
        panel.add(new JLabel("Available Polygon ID:"), BorderLayout.NORTH);
        panel.add(new JScrollPane(idJList), BorderLayout.CENTER);

        //Shows where user can input ID to remove polygon
        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Enter ID to remove:"));
        inputPanel.add(idInputField);
        panel.add(inputPanel, BorderLayout.SOUTH);

        //creates a popup dialog window
        int option = JOptionPane.showConfirmDialog(
                theApp,
                panel,
                "Remove Polygon",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        //returns to main app user clicks cancels
        if (option != JOptionPane.OK_OPTION){
            return;
        }

        String idText = idInputField.getText().trim();

       int removePolygonID;

       //checks if user inputted a valid id format
       try{
           removePolygonID = Integer.parseInt(idText);
           if (idText.length() != 6){
               throw new NumberFormatException();
           }
       } catch(NumberFormatException e){
           System.out.println("Removal Failed! ID strictly must be 6 digits numbers");
           JOptionPane.showMessageDialog(theApp, "Removal Failed! ID strictly must be 6 digits numbers");
           return;
       }

        //loops list to find inputted id and removes it from list
        for  (RegPolygon p : theApp.polygonsList) {
            if (p.getID() == removePolygonID){
                theApp.polygonsList.remove(p);
                theApp.currentPolygon = null;
                theApp.repaint();
                JOptionPane.showMessageDialog(theApp, "Polygon " + removePolygonID + " removed.");
                System.out.println("Polygon " + removePolygonID + " removed successfully.");
                return;
            }
        }

        //if user inputs valid ID that does not exist
        System.out.println("Polygon " + removePolygonID + " not found.");
        JOptionPane.showMessageDialog(theApp, "Polygon " + removePolygonID + " not found.");
    }

    //method to search polygons
    private void handleSearchPolygon() {

        //same logic applies like the remove polygon handler
        ArrayList<Integer> idList = new ArrayList<>();
        for (RegPolygon p : theApp.polygonsList) {
            idList.add(p.getID());
        }

        if (idList.isEmpty()){
            System.out.println("No polygons found.");
            JOptionPane.showMessageDialog(theApp, "No polygons found.");
            return;
        }

        String[] idArray = new String[idList.size()];
        for (int i = 0; i < idArray.length; i++){
            idArray[i] = String.valueOf(idList.get(i));
        }

        JList <String> idJList = new JList<>(idArray);

        JTextField idInputField = new JTextField(10);
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.add(new JLabel("Available Polygon ID:"), BorderLayout.NORTH);
        panel.add(new JScrollPane(idJList), BorderLayout.CENTER);

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Enter ID to search:"), BorderLayout.NORTH);
        inputPanel.add(idInputField);
        panel.add(inputPanel, BorderLayout.SOUTH);

        int option = JOptionPane.showConfirmDialog(
                theApp,
                panel,
                "Search Polygon",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (option != JOptionPane.OK_OPTION){
            return;
        }

        String idText = idInputField.getText().trim();

        int searchID;

        //checks if user inputted an ID with the correct format
        try{
            searchID = Integer.parseInt(idText);
            if (idText.length() != 6){
                throw new  NumberFormatException();
            }
        } catch(NumberFormatException e){
            System.out.println("Search Failed! ID strictly must be 6 digits numbers");
            JOptionPane.showMessageDialog(theApp, "Search Failed! ID strictly must be 6 digits numbers");
            return;
        }

        //same logic applies like remove polygon but instead of removing, it uses repaint() to display searched polygon
        for (RegPolygon p : theApp.polygonsList) {
            if (p.getID() == searchID){
                theApp.currentPolygon = p;
                theApp.repaint();
                System.out.println("Polygon " + searchID + " found.");
                JOptionPane.showMessageDialog(theApp, "Polygon " + searchID + " found.");
                return;
            }
        }
        System.out.println("Polygon " + searchID + " not found.");
        JOptionPane.showMessageDialog(theApp, "Polygon " + searchID + " not found.");
    }

    //sort polygon handler
    private void handleSortPolygon(){

        //checks if list is empty
        if (theApp.polygonsList.isEmpty()){
            System.out.println("No polygons found.");
            JOptionPane.showMessageDialog(theApp, "No polygons found.");
            return;
        }

        //Sorts all polygon in list based on ascending order via ID
        Collections.sort(theApp.polygonsList);

        //prints out list of polygons in console
        System.out.println("\n---Polygon List---");
        for (RegPolygon p : theApp.polygonsList){
            System.out.println(p);
        }

        JOptionPane.showMessageDialog(theApp, "Polygon list sorted. Check console output");
    }
}