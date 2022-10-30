/*
Caroline Hsu - 10/18/2021
This JavaDatabase class connects to the Driver to get to the database. 

 */
package hsujavadb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

class JavaDatabase
{

  // three attributes
  private String dbName;
  private Connection dbConn;
  private ArrayList<ArrayList<String>> data;

  // constructor without parameters
  public JavaDatabase()
  {
    dbName = "";
    dbConn = null;
    data = null;
  }

  // parameter with the name because we have name
  // db is dynamic, data values will change
  public JavaDatabase(String dbName)
  {
    setDbName(dbName);
    setDbConn();
    data = null;
  }

  // get and set methods for the attribute of name and dbConn 
  public String getDbName()
  {
    return this.dbName;
  }
  public void setDbName(String dbName)
  {
    this.dbName = dbName;
  }
  public Connection getDbConn()
  {
    return dbConn;
  }

  public void setDbConn()
  {
    // URL/path to database
    String connectionURL = "jdbc:derby:" + this.dbName; // address to db
    // to avoid compiler warning
    this.dbConn = null;
    try
    {
      // find the driver
      Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
      // use the driver and URL to get connection
      this.dbConn = DriverManager.getConnection(connectionURL);
    }
    catch (ClassNotFoundException ex) // class.forName exception
    {
      // always make sure driver is added to library
      new Warning("<html> <center>Driver not found, check library.</center> </html>");
    }
    catch (SQLException se) // getConnection exception
    {
      new Warning("<html> <center>SQL Connection Error!</center> </html>");
    }
  }

  public ArrayList<ArrayList<String>> getData(String tableName, String[] tableHeaders)
  {
    int columnCount = tableHeaders.length; // number of columns
    Statement s = null; // blank statement for SQL
    ResultSet rs = null; // pointer to data
    String dbQuery = "SELECT * FROM " + tableName; // to read data
    this.data = new ArrayList<>(); // construct new data
    // read the data
    try
    {
      // executes return statement
      // send the query and receive the data
      s = this.dbConn.createStatement();
      rs = s.executeQuery(dbQuery);
      // rs will point to beginning of the database, first row
      while (rs.next())
      {
        // row object to hold one row data
        ArrayList<String> row = new ArrayList<>();
        // go through the row and read each cell
        for (int i = 0; i < columnCount; i++)
        {
          // read cell i
          // table header at index 0 is 1st column
          String cell = rs.getString(tableHeaders[i]);
          // add the cell to the row
          row.add(cell);
        }
        // add the row to the data
        this.data.add(row);
      }
    }
    // throw a warning frame if there's a sql error
    catch (SQLException se)
    {
      new Warning("<html> <center>SQL Error: Not able to get data.</center> </html>");
    }
    // return the array list
    return data;
  }

  public ArrayList<String> getColumnData(String tableName, String tableHeader)
  {
    Statement s = null; // blank statement for SQL
    ResultSet rs = null; // pointer to data
    String dbQuery = "SELECT " + tableHeader + " FROM " + tableName; // to read data
    ArrayList<String> data = new ArrayList<String>(); // construct new data
    // read the data
    try
    {
      // executes return statement
      // send the query and receive the data
      s = this.dbConn.createStatement();
      rs = s.executeQuery(dbQuery);
      // rs will point to beginning of the database, first row
      while (rs.next())
      {
        // go through the row and read each cell
        for (int i = 0; i < 1; i++)
        {
          String cell = rs.getString(0);
          // add the cell to the row
          data.add(cell);
        }
      }
    }
    // throw a warning frame if there's a sql error
    catch (SQLException se)
    {
      new Warning("<html> <center>SQL Error: Not able to get data.</center> </html>");
    }
    // return the array list
    return data;
  }
  
  // set data 
  public void setData(ArrayList<ArrayList<String>> data)
  {
    this.data = data;
  }

  // close the db connection and throw an error with warning frame
  public void closeDbConn()
  {
    try
    {
      this.dbConn.close();
    }
    catch (Exception err)
    {
      new Warning("<html> <center>DB closing error</center> </html>");
    }

  }

  // create db method receiving the db name
  public void createDb(String newDbName)
  {
    setDbName(newDbName); // set dbName to newDbName
    // creates a db if not existing
    String connectionURL = "jdbc:derby:" + this.dbName
        + ";create=true"; // creates a new database
    this.dbConn = null;
    try
    {
      // driver with class creates new database of dbName
      Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
      this.dbConn = DriverManager.getConnection(connectionURL);
      System.out.println("New Database " + this.dbName + " created!");
    }
    // throw warnings if not able to find the driver
    catch (ClassNotFoundException ex)
    {
      new Warning("Driver not found, check library");
    }
    catch (SQLException se)
    {
      new Warning("SQL connection error, DB was not created!");
    }
  }

  // creates table
  public void createTable(String newTable, String dbName)
  {
    setDbName(dbName); // set dbName to dbName above
    setDbConn(); // set database connection
    Statement s; // blank statement for SQL
    try
    {
      s = this.dbConn.createStatement(); // create blank statement
      s.execute(newTable); // place the SQL in S and execute
      System.out.print("New table has been created!");
      this.dbConn.close();
    }
    // throw an exception error with warning frame
    catch (SQLException se)
    {
      new Warning("Error creating table " + newTable);
    }
  }

  // to convert a 2d arraylist to 2d array:
  public Object[][] to2darray(ArrayList<ArrayList<String>> data)
  {
    if (data.size() == 0) // if empty data (from database)
    {
      // a 2d array to receive values from data
      Object[][] dataList = new Object[0][0];
      return dataList; // returns an empty dataList
    }
    else // if we have data, there is a table with existing data
    {
      int columnCount = data.get(0).size(); // number of columns
      // construct dataList based on size of data
      Object[][] dataList = new Object[data.size()][columnCount];
      // loop to read each cell, reach each cell of each row into array
      for (int r = 0; r < data.size(); r++)
      {
        /* start at row 0 column 0 and will return data from [0][0]
        then you loop and do row 0 column 1, then column 2
        and once you get to the end of columns, will end loop
        row 1 column 0, row 1 column 1, row 1 column 2, etc */
        ArrayList<String> row = data.get(r); // get the row
        // loop to read each member of the row
        for (int c = 0; c < columnCount; c++)
        {
          // read the cell and store
          dataList[r][c] = row.get(c);
          // r and c = rows and columns
        }
      }
      // return the dataList
      return dataList;
    }
  }

  public static void main(String[] args)
  {
    // db info
    String dbName = "ForceCalc";
    String tableName = "Distance";
    String[] columnNames =
    {
      "name", "time", "rate", "distance"
    };
    String dbQuery = "INSERT INTO Distance VALUES (?,?,?,?)";

    // db connection
    JavaDatabase dbObj = new JavaDatabase(dbName);
    Connection myDbConn = dbObj.getDbConn();

    // values to insert string vs varchar, data to be entered to be replaced 
    // with textfields in GUI
    String name = "Problem 1";
    double time = 5.0;
    double rate = 20.0;
    double distance = 100.0;

    try
    {
      // prepare statement
      PreparedStatement ps = myDbConn.prepareStatement(dbQuery);
      // enter data into query
      // set string 1 starts from 1 not 0
      ps.setString(1, name);
      ps.setDouble(2, time);
      ps.setDouble(3, rate);
      ps.setDouble(4, distance);
      // execute the query
      ps.executeUpdate();
      // insert data
      System.out.println("Data inserted successfully.");
    }
    // throw warning frames
    catch (SQLException se)
    {
      new Warning("<html> <center>Error inserting data.</center> </html>");
    }
    catch (NumberFormatException nfe)
    {
      new Warning("<html> <center>Error converting a text field to an "
          + "integer.</center> </html>");
    }
    // prints data with the array list
    ArrayList<ArrayList<String>> myData
        = dbObj.getData(tableName, columnNames);
    System.out.println(myData);
  }

}
