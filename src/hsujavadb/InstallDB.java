/*
Caroline Hsu - 10/19/2021 
This class installs the DB and creates a database and a table.
 */
package hsujavadb;

public class InstallDB
{

  public static void main(String[] args)
  {
    String dbName = "ForceCalc";
    // create object of JavaDatabase class 
    JavaDatabase dbObj = new JavaDatabase();
    
    // create database
    dbObj.createDb(dbName);
    // create a SQL statement to create table
    String newTable = "CREATE TABLE Distance (name varChar(100), " + 
                      "time double, rate double, distance double)";
    // create table
    dbObj.createTable(newTable, dbName);
  } 
}
