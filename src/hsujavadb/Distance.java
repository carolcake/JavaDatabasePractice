/*
Caroline Hsu - 10/22/2021
This class calculates distance from rate and time and also receives name. 
The end result is the distance which it also returns. 
 */
package hsujavadb;

public class Distance
{
  // attributes
  private double time;
  private double rate;
  private double distance;
  private String name;
  
  // constructor with parameters receives name and time and rate
  public Distance(String n, double t, double r)
  {
    this.time = t;
    this.rate = r;
    this.name = n;
    
    // calculates distance
    this.distance = rate * time;
  }
  
  // constructor with no parameters also calculates
  public Distance()
  {
    this.time = 0;
    this.rate = 0;
    this.name = "";
    
    this.distance = rate * time;
  }
  
  // accessors and mutators
  public double getTime()
  {
    return this.time;
  }
  public double getRate()
  {
    return this.rate;
  }
  public double getDistance()
  {
    return this.distance;
  }
  public String getName()
  {
    return this.name;
  }
  public void setTime(double time)
  {
    this.time = time;
  }
  public void setRate(double rate)
  {
    this.rate = rate;
  }
  public void setDistance(double distance)
  {
    this.distance = distance;
  }
  public void setName(String name)
  {
    this.name = name;
  }
  
  // toString method returns all values
  @Override
  public String toString()
  {
    String message = "Your distance from rate of " + this.rate + " and "
        + "a time of " + this.time + " is " + this.distance;
    return message;
  }
  
  public static void main (String[] args)
  {
    // main class with example
    Distance objDist = new Distance("school", 6.0, 8.0);
    System.out.println(objDist);
  }
}
