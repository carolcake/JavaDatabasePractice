/*
Caroline Hsu - 10/25/2021
This frame is the warning frame that pops up with a received message in case
of an error with SQL or other exception error this program may catch.
 */
package hsujavadb;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class Warning extends JFrame implements ActionListener
{
  // button for return, button panel, help button and message label
  private JPanel buttonPanel;
  private JButton returnButton;
  private JButton helpButton;
  private JLabel messageLabel;
  
  // receive the message for the label
  public Warning(String warningMessage)
  {
    // initializing the frame
    super("Warning!");
    this.setBounds(400, 100, 800, 700);
    this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    this.getContentPane().setBackground(Color.RED);
    this.setLayout(new BorderLayout());
    
    // construct the button and the button panel
    this.buttonPanel = new JPanel(new FlowLayout());
    buttonPanel.setBackground(Color.WHITE);
    this.returnButton = new JButton("Return");
    returnButton.addActionListener(this);
    helpButton = new JButton("Help");
    helpButton.addActionListener(this);
    // add buttons to the panel
    buttonPanel.add(helpButton);
    buttonPanel.add(returnButton);
    
    // label for warning message
    this.messageLabel = new JLabel(warningMessage);
    
    // add panel to frame and set visible true
    this.add(buttonPanel, BorderLayout.SOUTH);
    this.add(messageLabel, BorderLayout.CENTER);
    this.setVisible(true);
  }
  
  public static void main (String[] args)
  {
    // main method with the string
    new Warning("<html> <center>We have found an error in your program. Please use"
        + " the help frame if you have more questions.</center> </html>");
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
    // else if for if the user needs help
    else if (command == helpButton)
    {
      new Help("<html> <center> Hi, my program calculates distance."
        + " <br> You can calculate it with rate and time in decimals. "
        + "</center> </html>");
    }
  }
}
