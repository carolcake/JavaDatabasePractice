/*
Caroline Hsu - 10/22/2021
This class displays my data table with the table headers and info. It receives
the db info and constructs it into a frame with the database table of
Distance. 
 */
package hsujavadb;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

public class Display extends JFrame implements ActionListener
{

  // declare colors and fonts for display frame
  public static final Color BACKGROUND_COLOR = new Color(171, 179, 249);
  public static final Color PANEL_COLOR = new Color(243, 186, 247);
  public static final Color GRID_COLOR = new Color(186, 221, 247);
  public static final Color HEADER_COLOR = new Color(186, 247, 210);
  public static final Font COURIER_FONT = new Font("Courier", Font.BOLD,
      20);

  // buttons and panel
  private JButton closeButton;
  private JPanel buttonPanel;
  private JButton helpButton;

  // declaring the data, table, pane, header, and column for db info 
  private ArrayList<ArrayList<String>> dataList;
  private Object[][] data;
  private JTable dbTable;
  private JScrollPane scrollTable;
  private JTableHeader header;
  private TableColumn column;

  // receive the dbname, tablename, and columnnames for the display frame
  public Display(String dbName, String tableName, String[] columnNames)
  {
    // set the frame
    super("Display");
    this.setBounds(100, 100, 1000, 1000);
    this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    this.getContentPane().setBackground(BACKGROUND_COLOR);
    this.setLayout(new BorderLayout());

    // construct button and panel
    closeButton = new JButton("Close");
    closeButton.addActionListener(this);
    helpButton = new JButton("Help");
    helpButton.addActionListener(this);
    buttonPanel = new JPanel(new FlowLayout());
    buttonPanel.setBackground(PANEL_COLOR);
    buttonPanel.add(helpButton);
    buttonPanel.add(closeButton);

    // make an object of JavaDatabase and set connection
    JavaDatabase objDb = new JavaDatabase(dbName);
    dataList = objDb.getData(tableName, columnNames);
    // transfer arraylist into 2d array
    data = objDb.to2darray(dataList);
    // construct the table
    dbTable = new JTable(data, columnNames);
    dbTable.setGridColor(GRID_COLOR);
    dbTable.setBackground(BACKGROUND_COLOR);
    dbTable.setFont(COURIER_FONT); // font for data

    // format header
    header = dbTable.getTableHeader();
    header.setBackground(HEADER_COLOR);
    header.setForeground(Color.BLACK);
    header.setFont(new Font("Times New Roman", Font.BOLD, 25));
    // format rows
    dbTable.setRowHeight(25);
    // format columns
    column = dbTable.getColumnModel().getColumn(0);
    column.setPreferredWidth(25);
    column = dbTable.getColumnModel().getColumn(1);
    column.setPreferredWidth(12);
    column = dbTable.getColumnModel().getColumn(2);
    column.setPreferredWidth(12);
    column = dbTable.getColumnModel().getColumn(3);
    column.setPreferredWidth(12);

    // put the Table into the Scroll Panel
    scrollTable = new JScrollPane();
    scrollTable.getViewport().add(dbTable);
    dbTable.setFillsViewportHeight(true);

    // add to frame and set visible true
    this.add(buttonPanel, BorderLayout.NORTH);
    this.add(scrollTable, BorderLayout.CENTER);
    this.setVisible(true);
  }

  public static void main(String[] args)
  {
    // db info for main method 
    String dbName = "ForceCalc";
    String tableName = "Distance";
    String[] columnNames =
    {
      "name", "time", "rate", "distance"
    };
    new Display(dbName, tableName, columnNames);
  }

  @Override
  public void actionPerformed(ActionEvent e)
  {
    // gets command button name
    String command = e.getActionCommand();
    // if the close button is chosen dispose the frame
    if (command.equals("Close"))
    {
      this.dispose();
    }
    // else if the help button is chosen add message
    else if (command.equals("Help"))
    {
      new Help("<html> <center> Hi, my program calculates distance."
          + " <br> You can calculate it with rate and time in decimals. "
          + "</center> </html>");
    }
  }
}
