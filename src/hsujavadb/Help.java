/*
Caroline Hsu - 10/29/2021
This frame is the help frame that opens up when the user needs assistance.
 */
package hsujavadb;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Help extends JFrame implements ActionListener
{  
  // declare buttons and panel for returning or exiting the program
  // explanation label
  private JPanel buttonPanel;
  private JButton exitButton;
  private JButton returnButton;
  private JLabel explanationLabel;
  
  // constructor receives the help message from the different classes
  public Help(String helpString)
  {
    // initializing the frame
    super("Help Frame");
    this.setBounds(400,100,800,700);
    this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    this.getContentPane().setBackground(Color.GREEN);
    this.setLayout(new BorderLayout());
    
    // declare label
    this.explanationLabel = new JLabel(helpString);
    
    // construct the panel and buttons
    this.buttonPanel = new JPanel(new FlowLayout());
    buttonPanel.setBackground(Color.WHITE);
    this.exitButton = new JButton("Exit the program");
    exitButton.addActionListener(this);
    this.returnButton = new JButton("Return to last frame");
    returnButton.addActionListener(this);
    
    // add buttons to the panel
    buttonPanel.add(returnButton);
    buttonPanel.add(exitButton);
    
    // add labels and panel to the frame
    this.add(explanationLabel, BorderLayout.CENTER);
    this.add(buttonPanel, BorderLayout.SOUTH);
    this.setVisible(true);
  }
  
  public static void main (String[] args)
  {
    // main method to construct the frame with the string
    new Help("<html> <center> Hi, my program calculates distance."
        + " <br> You can calculate it with rate and time in decimals. "
        + "</center> </html>");
  }

  @Override
  public void actionPerformed(ActionEvent e)
  {
    // gets the source of the command (button)
    Object command = e.getSource();
    // if the button to return is pushed
    if (command == returnButton)
    {
      // then dispose this frame since it's a pop up
      this.dispose();
    }
    // else if the button to exit is pushed
    else if (command == exitButton)
    {
      // quit the program
      System.exit(0);
    }
    // validate and repaint the frame
    this.validate();
    this.repaint();
  }
}
