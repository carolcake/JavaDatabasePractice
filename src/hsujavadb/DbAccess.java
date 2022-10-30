/*
Caroline Hsu - 10/19/2021
This class is the main GUI frame that displays all of the buttons to insert,
display, update, delete, or quit the program. It also has a help button in case
help is needed. 

DbAccess
declare buttons and panels
create the buttons and panels and implement action listener
add panels and buttons to frame and set visible as true
do action performed for buttons and make sure display has names for db and table

Delete
declare buttons and panels
create the buttons and panels and implement action listener
add panels and buttons to frame and set visible as true
make sure you have the radio buttons for user to choose and the groups
do action performed and change the query
don't forget errors with warning frames

Display
declare panels and button
make sure to declare array list for data, table, scroll pane, headers, and columns
construct the GUI and make sure to receive the data for the table as parameters
create the tables with the headers and columns
main class should have db info to construct the frame
action performed has close and help frames

Distance
computation class with attributes
one constructor with parameters, other without
private accessors and mutators
toString method
and main method with example 

Driver
Declares JavaDatabase of ForceCalc and also opens DbAccess GUI

Help
Declare panel and buttons
construct the frame and receive a help message for the explanation label
main method constructs the frame

Insert
declare panels, buttons, text fields, and labels for data
construct the frame, panels, buttons, text fields, and labels
main method makes gui
action performed should have done, help, and add buttons
dbquery should insert into db table Distance
warning frame should pop up if there is an error

InstallDB
class creates table and database ForceCalc and table Distance

JavaDatabase
three attributes of name, dbconn, and arraylist for data
constructors, accessors and mutators that are private are needed
set db conn method, getdata and set data methods, close the connection method, 
creating the table or database methods, and 2d array conversion methods are needed
main method does data insert
if errors occur warning frame will be thrown

Update
declare button panels and buttons, radio buttons, text fields, labels
action performed is most important
conditional if old name button is chosen
  new columns of name, time, and rate can be chosen and the calculation will
  be updated with the distance class
warnings will be thrown just in case errors occur

Warning
Regular frame with panel and button and message
construct GUI
message is received in constructor
main method uses example warning statement with GUI
action performed for buttons for user
 */
package hsujavadb;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class DbAccess extends JFrame implements ActionListener
{
  // declare two panels for buttons (top are display insert update delete)
  // buttom are quit and help frames
  private JPanel buttonPanel;
  private JPanel bottomPanel;
  private JButton displayButton;
  private JButton insertButton;
  private JButton deleteButton;
  private JButton updateButton;
  private JButton quitButton;
  private JButton helpButton;
  
  public DbAccess()
  {
    // constructing the frame
    super("Database Access");
    this.setBounds(50, 50, 1000, 700);
    this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    this.getContentPane().setBackground(Color.pink);
    this.setLayout(new BorderLayout());
    
    // create button panels and insert and display panel
    this.buttonPanel = new JPanel(new FlowLayout());
    buttonPanel.setBackground(Display.PANEL_COLOR);
    this.bottomPanel = new JPanel(new FlowLayout());
    bottomPanel.setBackground(Display.GRID_COLOR);
    this.displayButton = new JButton("Display");
    displayButton.addActionListener(this);
    this.insertButton = new JButton("Insert");
    insertButton.addActionListener(this);
    this.deleteButton = new JButton("Delete");
    deleteButton.addActionListener(this);
    this.updateButton = new JButton("Update");
    updateButton.addActionListener(this);
    this.quitButton = new JButton("Quit");
    quitButton.addActionListener(this);
    this.helpButton = new JButton("Help");
    helpButton.addActionListener(this);
    
    // add buttons to the panels
    buttonPanel.add(displayButton);
    buttonPanel.add(insertButton);
    buttonPanel.add(deleteButton);
    buttonPanel.add(updateButton);
    bottomPanel.add(quitButton);
    bottomPanel.add(helpButton);
    
    // add panels to the frame and set visible true
    this.add(buttonPanel, BorderLayout.NORTH);
    this.add(bottomPanel, BorderLayout.SOUTH);
    this.setVisible(true);
  }
  
  public static void main (String[] args)
  {
    // main method for the GUI
    new DbAccess();
  }

  @Override
  public void actionPerformed(ActionEvent e)
  {
    // gets source of the command for actionPerformed
    Object command = e.getSource();
    // ifi the display button is entered
    if (command == displayButton)
    {
      // db info for the Display frame
      String dbName = "ForceCalc";
      String tableName = "Distance";
      String[] columnNames = {"name", "time", "rate", "distance"};
      new Display(dbName, tableName, columnNames);
    }
    // if the insert button is chosen new insert
    else if (command == insertButton)
    {
      new Insert();
    }
    // if the delete button is chosen new delete
    else if (command == deleteButton)
    {
      new Delete();
    }
    // if the update button is chosen new update
    else if (command == updateButton)
    {
      new Update();
    }
    // if the help button is chosen new help with the message
    else if (command == helpButton)
    {
      new Help("<html> <center> Hi, my program calculates distance."
        + " <br> You can calculate it with rate and time in decimals. "
        + "</center> </html>");
    }
    // if the quit button is chosen quit the program
    else if (command == quitButton)
    {
      System.exit(0);
    }
  }
  
}
