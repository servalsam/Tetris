/*
 * TCSS 305 Autumn 2018
 * Assignment 6
 */

package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.Board;

/**
 * GameListener is the default listener for the
 * current game session.
 * 
 * @author Sam Wainright
 * @version 29 Nov 2018
 */
public class GameListener implements ActionListener {

    /** The current state of the game. */
    private Board myCurrentGame;
    
    /**
     * The public constructor of the GameListener class.
     * 
     * @param theBoard The current active board being interacted with.
     */
    public GameListener(final Board theBoard) {
        
        myCurrentGame = theBoard;
        
    }
    
    /**
     * {@inheritDoc}
     * 
     * Calls the game's down() function when ActionEvent created.
     */
    @Override
    public void actionPerformed(final ActionEvent theActionEvent) {
        
        myCurrentGame.down();
        
    }

}
