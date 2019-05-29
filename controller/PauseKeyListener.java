package controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import view.GamePanel;

/**
 * PauseKeyListener is a key adapter that listens for the 
 * key events related to the pause key binded.
 * 
 * @author Sam Wainright
 * @version 5 Dec 2018
 */
public class PauseKeyListener extends KeyAdapter {
    
    /** The current active game panel. */
    private GamePanel myCurrentGamePanel;
    
    /**
     * The GameKeyListener constructor.
     * 
     * @param theGamePanel The current panel with an active Tetris game board.
     */
    public PauseKeyListener(final GamePanel theGamePanel) {
        
        myCurrentGamePanel = theGamePanel;
        
    }
    
    /**
     * {@inheritDoc}
     * 
     * keyPressed listens for key events that match the game's
     * configurations for the pause command.
     * 
     * @param theKeyEvent The key event being listened for. 
     */
    @Override
    public void keyPressed(final KeyEvent theKeyEvent) {
        
        if (myCurrentGamePanel.getMyGameIsOn()) {
            
            if (theKeyEvent.getKeyCode() == KeyEvent.VK_P) {
                
                if (!myCurrentGamePanel.getMyGameIsPaused()) {
                    
                    myCurrentGamePanel.setMyGameToPaused(true);
                    myCurrentGamePanel.pause();
                    
                } else {
                    
                    myCurrentGamePanel.setMyGameToPaused(false);
                    myCurrentGamePanel.unpause();
                    
                }
                
            }
        }
        
    }
    
}
