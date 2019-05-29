/*
 * TCSS 305 Autumn 2018
 * Assignment 6
 */

package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.math.BigDecimal;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;


/**
 * AboutAction is an listener type object that executes
 * a window of information about the program.
 * 
 * @author Sam Wainright
 * @version 7 Dec 2018
 */
public class AboutAction extends JOptionPane implements ActionListener {

    /** Auto-generated serial version UID in compliance with extension contract. */
    private static final long serialVersionUID = -4501707810749660762L;
    
    /**
     * Primary image icon for the option pane.
     */
    private BufferedImage myImage;
    
    /**
     * Stores value for current version of the game. 
     */
    private BigDecimal myVersionNumber;
    
    /**
     * Default constructor for AboutAction.
     * 
     * @throws IOException Thrown when the wrong file is loaded as resource.
     */
    public AboutAction() throws IOException {
        
        myImage = ImageIO.read(this.getClass().
                               getResource("/resources/sonicinfo.png"));
        
        myVersionNumber = new BigDecimal("1.00");
    }

    /**
     * {@inheritDoc}
     * 
     * Displays information about the program and rules of the game, as well as 
     * crediting information for educational use resources.
     */
    @Override
    public void actionPerformed(final ActionEvent theActionEvent) {
        
        JOptionPane.showMessageDialog(this,
                        "\n\nAuthor: Sam Wainright\t\n" 
                        + "Current Version: " 
                        + myVersionNumber + "\t"
                        + "\n\n"
                        + "Controls: \n"
                        + "A or Left Arrow Key - Move Piece Left\n"
                        + "D or Right Arrow Key - Move Piece Right\n"
                        + "S or Down Arrow Key - Move Piece Down\n"
                        + "W or Up Arrow Key - Rotate Piece\n"
                        + "Space - Drop Piece\n\n"
                        + "Scoring:\n"
                        + "Game Levels up every 4 levels.\n\n"
                        + "1 Row Cleared = 50 \u00D7 Level + 1\n"
                        + "2 Rows Cleared = 150 \u00D7 Level + 1\n"
                        + "3 Rows Cleared = 350 \u00D7 Level + 1\n"
                        + "4 Rows Cleared (Tetris) = 1000 \u00D7 Level + 1\n"
                        + "\n"
                        + "SONIC THE HEDGHOG\u2122 and " 
                        + "SONIC THE HEDGEHOG 3\u2122 are registered\n"
                        + "and trademarked by SEGA GAMES CO., LTD." 
                        + "All credit for resources goes to\n"
                        + "SEGA GAMES CO., LTD. For educational purposes only.\n",
                        "TCSS 305 - T E T R I S",
                        JOptionPane.INFORMATION_MESSAGE,
                        new ImageIcon(myImage));
        
    }

}
