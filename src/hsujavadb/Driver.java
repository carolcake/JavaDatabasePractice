/*
Caroline Hsu - 10/26/2021
This class is the driver class that opens up the DbAccess GUI.
 */
package hsujavadb;

public class Driver
{
  // declares a javadatabase ForceCalc and main method opens DbAccess
  public static JavaDatabase objDb = new JavaDatabase("ForceCalc");
  public static void main(String[] args)
  {
    new DbAccess();
  }
}
