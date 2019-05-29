/*
 * TCSS 305 Autumn 2018
 * Assignment 6
 */

package controller;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import view.GamePanel;

/**
 * WindowCommands is a WindowAdapter that activates the
 * "game over" process of the current game session to achieve
 * a graceful close option while the game had been started and the
 * window had been forcibly closed.
 * 
 * @author Sam Wainright
 * @version 5 Dec 2018
 */
public final class WindowCommands extends WindowAdapter {
    
    /** The game panel hosting the current active game session. */
    private GamePanel myCurrentGamePanel;
    
    /**
     * Public constructor of WindowCommands.
     * 
     * @param theGamePanel The game panel with the current active game session.
     */
    public WindowCommands(final GamePanel theGamePanel) {
        
        myCurrentGamePanel = theGamePanel;
        
    }

    /**
     * {@inheritDoc}
     * 
     * Ends the current active game session in the game panel upon
     * window close or dispose.
     * 
     */
    @Override
    public void windowClosing(final WindowEvent theWindowEvent) {
        
        myCurrentGamePanel.gameOver();
        
    }
    
}

