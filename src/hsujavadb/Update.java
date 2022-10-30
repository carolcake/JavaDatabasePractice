/*
Caroline Hsu - 10/26/2021 
This class updates values and changes the result accordingly.
 */
package hsujavadb;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class Update extends JFrame implements ActionListener
{
  // declare colors
  public static final Color BACKGROUND_COLOR = new Color(141, 235, 213);
  public static final Color PANEL_COLOR = new Color(204, 255, 255);

  // declare button panel for buttons and text fields
  private JPanel dataPanel;
  private JTextField conditionField;
  private JLabel conditionLabel;
  private JLabel oldLabel;
  private JTextField updateField;
  private JLabel updateLabel;
  private JLabel newLabel;
  private JPanel buttonPanel;
  private JLabel dummyLabel;
  private JLabel dummyLabel2;
  private JButton updateButton;
  private JButton doneButton;
  // radio buttons for user with a group
  private JRadioButton oldNameButton;
  private JRadioButton newNameButton;
  private JRadioButton newTimeButton;
  private JRadioButton newRateButton;
  private ButtonGroup oldColumnGroup;
  private ButtonGroup newColumnGroup;
  
  private ArrayList<ArrayList<String>> data;
  private Connection dbConn;

  public Update()
  {
    // construct the frame
    super("Update");
    this.setBounds(100, 100, 1100, 1000);
    this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    this.getContentPane().setBackground(BACKGROUND_COLOR);
    this.setLayout(new BorderLayout());

    // construct the labels, panels, and fields
    dataPanel = new JPanel(new GridLayout(2, 5));
    dataPanel.setBackground(PANEL_COLOR);
    conditionField = new JTextField(10);
    conditionLabel = new JLabel("Condition column:");
    oldLabel = new JLabel("Enter old value:");
    updateField = new JTextField(10);
    updateLabel = new JLabel("Updated column:");
    newLabel = new JLabel("Enter new value:");
    dummyLabel = new JLabel("");
    dummyLabel2 = new JLabel("");

    buttonPanel = new JPanel(new FlowLayout());
    buttonPanel.setBackground(PANEL_COLOR);
    doneButton = new JButton("Done");
    doneButton.addActionListener(this);
    updateButton = new JButton("Update");
    updateButton.addActionListener(this);

    // radio buttons constructed along with their groups
    oldNameButton = new JRadioButton("Name");
    oldNameButton.addActionListener(this);
    oldColumnGroup = new ButtonGroup();
    oldColumnGroup.add(oldNameButton);
    newNameButton = new JRadioButton("Name");
    newNameButton.addActionListener(this);
    newTimeButton = new JRadioButton("Time");
    newTimeButton.addActionListener(this);
    newRateButton = new JRadioButton("Rate");
    newRateButton.addActionListener(this);
    newColumnGroup = new ButtonGroup();
    newColumnGroup.add(newNameButton);
    newColumnGroup.add(newTimeButton);
    newColumnGroup.add(newRateButton);

    // add all buttons and fields and labels to the panels
    buttonPanel.add(doneButton);
    buttonPanel.add(updateButton);
    dataPanel.add(conditionLabel);
    dataPanel.add(oldNameButton);
    dataPanel.add(dummyLabel);
    dataPanel.add(dummyLabel2);
    dataPanel.add(oldLabel);
    dataPanel.add(conditionField);
    dataPanel.add(updateLabel);
    dataPanel.add(newNameButton);
    dataPanel.add(newTimeButton);
    dataPanel.add(newRateButton);
    dataPanel.add(newLabel);
    dataPanel.add(updateField);

    // add to frame and set visible as true
    this.add(dataPanel, BorderLayout.NORTH);
    this.add(buttonPanel, BorderLayout.SOUTH);
    this.setVisible(true);
  }

  public static void main(String[] args)
  {
    // main method with GUI
    new Update();
  }

  @Override
  public void actionPerformed(ActionEvent e)
  {
    // db info
    String dbName = "ForceCalc";
    String tableName = "Distance";
    String[] columnNames = {"name", "time", "rate", "distance"};
    String dbQuery = "UPDATE Distance set age = ? WHERE name = ?";
    PreparedStatement ps = null;
    // db connection with result set and statement
    JavaDatabase dbObj = new JavaDatabase(dbName);
    Connection myDbConn = dbObj.getDbConn();
    // gets the old and new data
    String oldData = conditionField.getText();
    String newData = updateField.getText();
    Statement s = null;
    ResultSet rs = null;
    double time;
    double rate;
    double distance;

    // gets the source of the command for action performed with the button
    Object command = e.getSource();
    // if the done button is chosen close the frame
    if (command == doneButton)
    {
      this.dispose();
    }
    // if the update button is selected
    else if (command == updateButton)
    {
      try
      {
        // if the old name button is selected
        if (oldNameButton.isSelected()) 
        {
          // if the new name button is selected
          if (newNameButton.isSelected())
          {
            // enter a new dbQuery and set the ? as data
            dbQuery = "UPDATE Distance SET name = ? WHERE name = ?";
            ps = myDbConn.prepareStatement(dbQuery);
            ps.setString(1, newData);
            ps.setString(2, oldData);
            ps.executeUpdate();
            conditionField.setText("");
            updateField.setText("");
          }
          // else if new time button is selected 
          else if (newTimeButton.isSelected())
          {
            // select rate for calcuulations
            dbQuery = "SELECT rate FROM Distance WHERE name = '"+oldData+"'";
            s = myDbConn.createStatement();
            rs = s.executeQuery(dbQuery);
            rs.next(); // moves pointer
            rate = rs.getDouble("rate");
            time = Double.parseDouble(newData);
            // use distance class for calculation
            Distance distanceObj = new Distance(oldData, time, rate);
            time = distanceObj.getTime();
            rate = distanceObj.getRate();
            distance = distanceObj.getDistance();
            // new prepared statement and updated db query for update
            dbQuery = "UPDATE Distance SET time = ? WHERE name = ?";
            PreparedStatement ps1 = myDbConn.prepareStatement(dbQuery);
            // enter the time and name
            ps1.setDouble(1, Double.parseDouble(newData));
            ps1.setString(2, oldData);
            ps1.executeUpdate();
            // new prepared statement and updated db query for update
            dbQuery = "UPDATE Distance SET rate = ? WHERE name = ?";
            PreparedStatement ps2 = myDbConn.prepareStatement(dbQuery);
            // enter the rate and name
            ps2.setDouble(1, rate);
            ps2.setString(2, oldData);
            ps2.executeUpdate();
            // new prepared statement and updated db query for update
            dbQuery = "UPDATE Distance SET distance = ? WHERE name = ?";
            PreparedStatement ps3 = myDbConn.prepareStatement(dbQuery);
            // enter the distance and name
            ps3.setDouble(1, distance);
            ps3.setString(2, oldData);
            ps3.executeUpdate();
            conditionField.setText("");
            updateField.setText("");
          }
          // else if statement for if the rate button is selected
          else if (newRateButton.isSelected())
          {
            // db query to select data and do calculation
            dbQuery = "SELECT time FROM Distance WHERE name = '"+oldData+"'";
            s = myDbConn.createStatement();
            rs = s.executeQuery(dbQuery);
            rs.next(); // moves pointer
            time = rs.getDouble("time");
            ps = myDbConn.prepareStatement(dbQuery);
            // use distance class for calculation
            rate = Double.parseDouble(newData);
            Distance distanceObj = new Distance(oldData, time, rate);
            time = distanceObj.getTime();
            rate = distanceObj.getRate();
            distance = distanceObj.getDistance();
            // update db query with new prepared statement
            dbQuery = "UPDATE Distance SET time = ? WHERE name = ?";
            PreparedStatement ps4 = myDbConn.prepareStatement(dbQuery);
            // enter in time and name
            ps4.setDouble(1, time);
            ps4.setString(2, oldData);
            ps4.executeUpdate();
            // update db query with new prepared statement
            dbQuery = "UPDATE Distance SET rate = ? WHERE name = ?";
            PreparedStatement ps5 = myDbConn.prepareStatement(dbQuery);
            // enter in rate and name
            ps5.setDouble(1, Double.parseDouble(newData));
            ps5.setString(2, oldData);
            ps5.executeUpdate();
            // update db query with new prepared statement
            dbQuery = "UPDATE Distance SET distance = ? WHERE name = ?";
            PreparedStatement ps6 = myDbConn.prepareStatement(dbQuery);
            // enter in distance and name
            ps6.setDouble(1, distance);
            ps6.setString(2, oldData);
            ps6.executeUpdate();
            conditionField.setText("");
            updateField.setText("");
          }
        }
        System.out.println("Data updated!");
      }
      // throw warning frames in case of errors
      catch (SQLException se)
      {
        new Warning("Error updating your data!");
      }
      catch (NumberFormatException nfe)
      {
        new Warning("Incompatible data types!");
      }
    }
  }
}