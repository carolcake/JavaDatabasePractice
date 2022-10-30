/*
Caroline Hsu - 10/24/2021
This class allows the user to insert any data. A warning frame will pop up
if data is entered incorrectly.
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
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Insert extends JFrame implements ActionListener
{
  // "name", "time", "rate", "distance"
  // declare button panel for name age temp buttons and text fields
  private JPanel insertPanel;
  private JTextField nameField;
  private JTextField timeField;
  private JTextField rateField;
  private JLabel nameLabel;
  private JLabel timeLabel;
  private JLabel rateLabel;

  private JPanel buttonPanel;
  private JButton doneButton;
  private JButton addButton;
  private JPanel bottomPanel;
  private JButton helpButton;

  public Insert()
  {
    // construct the frame
    super("Insert");
    this.setBounds(100, 100, 800, 700);
    this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    this.getContentPane().setBackground(Color.green);
    this.setLayout(new BorderLayout());

    // construct the panel for data with text fields and labels
    insertPanel = new JPanel(new FlowLayout());
    nameField = new JTextField(18);
    timeField = new JTextField(6);
    rateField = new JTextField(6);
    nameLabel = new JLabel("Name:");
    timeLabel = new JLabel("Time (dec):");
    rateLabel = new JLabel("Rate (dec):");

    // add labels and fields to the panel
    insertPanel.add(nameLabel);
    insertPanel.add(nameField);
    insertPanel.add(timeLabel);
    insertPanel.add(timeField);
    insertPanel.add(rateLabel);
    insertPanel.add(rateField);

    // construct the other panel and buttons
    buttonPanel = new JPanel(new FlowLayout());
    doneButton = new JButton("Done");
    doneButton.addActionListener(this);
    addButton = new JButton("Add");
    addButton.addActionListener(this);
    helpButton = new JButton("Help");
    helpButton.addActionListener(this);

    // add to panel
    buttonPanel.add(helpButton);
    buttonPanel.add(doneButton);
    buttonPanel.add(addButton);

    // add all to frame and set visible as true
    this.add(insertPanel, BorderLayout.NORTH);
    this.add(buttonPanel, BorderLayout.SOUTH);
    this.setVisible(true);
  }

  public static void main(String[] args)
  {
    // makes a new Insert GUI
    new Insert();
  }

  @Override
  public void actionPerformed(ActionEvent e)
  {
    // db info
    String dbName = "ForceCalc";
    String tableName = "Distance";
    String[] columnNames ={"name", "time", "rate", "distance"};
    String dbQuery = "INSERT INTO Distance VALUES (?,?,?,?)";
    // db connection
    JavaDatabase dbObj = new JavaDatabase(dbName);
    Connection myDbConn = dbObj.getDbConn();
    
    // declare variables for the text field values and the insertion of data
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
    // if the user needs help they click the help button
    else if (command == helpButton)
    {
      new Help("<html> <center> Hi, my program calculates distance."
        + " <br> You can calculate it with rate and time in decimals. "
        + "</center> </html>");
    }
    // if they want to add data
    else if (command == addButton)
    {
      // try and catch for exceptions
      try
      {
        // get the fields and use the Distance class for the calculation
        name = nameField.getText();
        time = Double.parseDouble(timeField.getText());
        rate = Double.parseDouble(rateField.getText());
        Distance distanceObj = new Distance(name, time, rate);
        name = distanceObj.getName();
        time = distanceObj.getTime();
        rate = distanceObj.getRate();
        distance = distanceObj.getDistance();
        // set text fields as blank
        nameField.setText("");
        timeField.setText("");
        rateField.setText("");

        // prepare statement
        PreparedStatement ps = myDbConn.prepareStatement(dbQuery);
        // enter data into query
        ps.setString(1, name);
        ps.setDouble(2, time);
        ps.setDouble(3, rate);
        ps.setDouble(4, distance);
        // execute the query
        ps.executeUpdate();
        System.out.println("Data inserted successfully.");
      }
      // if an error is caught throw a warning frame
      catch (SQLException se)
      {
        new Warning("Error inserting data");
      }
      catch (NumberFormatException nfe)
      {
        new Warning("Error converting a text field to a double.");
      }
    }
  }
}
