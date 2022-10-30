/*
Caroline Hsu - 10/22/2021
This class deletes data with radio buttons and a text field to enter the value
or name you want to be deleted.
 */
package hsujavadb;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class Delete extends JFrame implements ActionListener
{

  // declare button panel for conditions and buttons for delete
  // make sure to have radio buttons for the groups for user to choose
  private JPanel dataPanel;
  private JTextField conditionField;
  private JLabel conditionLabel;

  private JPanel buttonPanel;
  private JButton deleteButton;
  private JButton doneButton;

  private JRadioButton nameButton;
  private JRadioButton timeButton;
  private JRadioButton rateButton;
  private JRadioButton distanceButton;
  private ButtonGroup columnGroup;

  public Delete()
  {
    // construct the frame
    super("Delete");
    this.setBounds(100, 100, 800, 700);
    this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    this.getContentPane().setBackground(Color.YELLOW);
    this.setLayout(new BorderLayout());

    // declare the data panel with the text fields
    dataPanel = new JPanel(new FlowLayout());
    conditionField = new JTextField(6);
    conditionLabel = new JLabel("Value or name you want to delete:");
    dataPanel.add(conditionLabel);
    dataPanel.add(conditionField);

    // button panel and buttons for user actions with the radio buttons so
    // user can choose
    buttonPanel = new JPanel(new FlowLayout());
    doneButton = new JButton("Done");
    doneButton.addActionListener(this);
    deleteButton = new JButton("Delete");
    deleteButton.addActionListener(this);
    nameButton = new JRadioButton("Name");
    nameButton.addActionListener(this);
    timeButton = new JRadioButton("Time");
    timeButton.addActionListener(this);
    rateButton = new JRadioButton("Rate");
    rateButton.addActionListener(this);
    distanceButton = new JRadioButton("Distance");
    distanceButton.addActionListener(this);
    columnGroup = new ButtonGroup();
    columnGroup.add(nameButton);
    columnGroup.add(timeButton);
    columnGroup.add(rateButton);
    columnGroup.add(distanceButton);

    // add buttons to the panel
    buttonPanel.add(doneButton);
    buttonPanel.add(deleteButton);
    buttonPanel.add(nameButton);
    buttonPanel.add(timeButton);
    buttonPanel.add(rateButton);
    buttonPanel.add(distanceButton);

    // add to frame and set visible true
    this.add(dataPanel, BorderLayout.NORTH);
    this.add(buttonPanel, BorderLayout.SOUTH);
    this.setVisible(true);
  }

  public static void main(String[] args)
  {
    // main method for the GUI
    new Delete();
  }

  @Override
  public void actionPerformed(ActionEvent e)
  {
    // db info
    String dbName = "ForceCalc";
    String tableName = "Distance";
    String[] columnNames = {"name", "time", "rate", "distance"};
    // query for delete
    String dbQuery = "DELETE FROM Distance WHERE ? = ?";
    // db connection
    JavaDatabase dbObj = new JavaDatabase(dbName);
    Connection myDbConn = dbObj.getDbConn();
    String name;
    double time;
    double rate;
    double distance;

    // gets the source of the command for action performed with the button
    Object command = e.getSource();
    // if the user is done they can dispose the frame
    if (command == doneButton)
    {
      this.dispose();
    }
    // if statement for if the delete button is chosen
    else if (command == deleteButton)
    {
      // another if statement for if the name button is selected
      if (nameButton.isSelected())
      {
        try
        {
          // change the query to name because name buttn
          dbQuery = "DELETE FROM Distance WHERE name = ?";
          // get text and set text as blank
          name = conditionField.getText();
          conditionField.setText("");

          // prepare statement
          PreparedStatement ps = myDbConn.prepareStatement(dbQuery);
          // enter data into query
          ps.setString(1, name);
          // execute the query
          ps.executeUpdate();
          System.out.println("Data deleted successfully.");
        }
        // catches errors with the warning GUI
        catch (SQLException se)
        {
          new Warning("<html> <center>Error inserting data.</center> </html>");
        }
        catch (NumberFormatException nfe)
        {
          new Warning("<html> <center>Error converting a text field to an "
              + "integer.</center> </html>");
        }
      }
      // else if statement for if the time button is selected
      else if (timeButton.isSelected())
      {
        try
        {
          // change the query
          dbQuery = "DELETE FROM Distance WHERE time = ?";
          // parse the condition field into double for time and set blank
          time = Double.parseDouble(conditionField.getText());
          conditionField.setText("");

          // prepare statement
          PreparedStatement ps = myDbConn.prepareStatement(dbQuery);
          // enter data into query
          ps.setDouble(1, time);
          // execute the query
          ps.executeUpdate();
          System.out.println("Data deleted successfully.");
        }
        // warning gui for exception errors
        catch (SQLException se)
        {
          new Warning("<html> <center>Error inserting data.</center> </html>");
        }
        catch (NumberFormatException nfe)
        {
          new Warning("<html> <center>Error converting a text field to an "
              + "integer.</center> </html>");
        }
      }
      // else if statement for if the rate button is selected
      else if (rateButton.isSelected())
      {
        try
        {
          // change the query, parse the fields and set as blank
          dbQuery = "DELETE FROM Distance WHERE rate = ?";
          rate = Double.parseDouble(conditionField.getText());
          conditionField.setText("");
          
          // prepare statement
          PreparedStatement ps = myDbConn.prepareStatement(dbQuery);
          // enter data into query
          ps.setDouble(1, rate);
          // execute the query
          ps.executeUpdate();
          System.out.println("Data deleted successfully.");
        }
        // warning GUI for exception errors
        catch (SQLException se)
        {
          new Warning("<html> <center>Error inserting data.</center> </html>");
        }
        catch (NumberFormatException nfe)
        {
          new Warning("<html> <center>Error converting a text field to an "
              + "integer.</center> </html>");
        }
      }
      // else if statement for if the distance button is selected
      else if (distanceButton.isSelected())
      {
        try
        {
          // change the query for distance condition and parse and set clear
          dbQuery = "DELETE FROM Distance WHERE distance = ?";
          distance = Double.parseDouble(conditionField.getText());
          conditionField.setText("");
          // prepare statement
          PreparedStatement ps = myDbConn.prepareStatement(dbQuery);
          // enter data into query
          ps.setDouble(1, distance);
          // execute the query
          ps.executeUpdate();
          System.out.println("Data deleted successfully.");
        }
        // excepton errors for GUI warning frame
        catch (SQLException se)
        {
          new Warning("<html> <center>Error inserting data.</center> </html>");
        }
        catch (NumberFormatException nfe)
        {
          new Warning("<html> <center>Error converting a text field to an "
              + "integer.</center> </html>");
        }
      }
    }
  }
}

