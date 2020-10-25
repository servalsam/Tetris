/*
 * TCSS 305 Autumn 2018
 * Assignment 6
 */

package controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import model.Board;
import view.GamePanel;

/**
 * DirectionalKeyListener is a key listener for the 
 * current game state in session, which listens to key events from
 * the directional number pad's arrows.
 * 
 * @author Sam Servane
 * @version 5 Dec 2018
 */
public class DirectionalKeyListener extends KeyAdapter {
    
    /** The current state of the game board. */
    private Board myCurrentGame;
    
    /** The current active game panel. */
    private GamePanel myCurrentGamePanel;
    
    /**
     * The GameKeyListener constructor.
     * 
     * @param theBoard The current state of the Tetris game.
     * @param theGamePanel The current panel with an active Tetris game board.
     */
    public DirectionalKeyListener(final Board theBoard, final GamePanel theGamePanel) {
        
        myCurrentGame = theBoard;
        myCurrentGamePanel = theGamePanel;
        
    }

    /**
     * {@inheritDoc}
     * 
     * keyPressed listens for key events that match the game's
     * configurations for movements and actions.
     * 
     * @param theKeyEvent The key event being listened for. 
     */
    @Override
    public void keyPressed(final KeyEvent theKeyEvent) {
        
        if (myCurrentGamePanel.getMyGameIsOn()
                        && !myCurrentGamePanel.getMyGameIsPaused()) {
            
            switch (theKeyEvent.getKeyCode()) {
                    
                case KeyEvent.VK_LEFT:
                    
                    myCurrentGame.left();
                    break;
                    
                case KeyEvent.VK_RIGHT:
                    
                    myCurrentGame.right();
                    break;
                    
                case KeyEvent.VK_UP:
                    
                    myCurrentGame.rotate();
                    break;
                    
                case KeyEvent.VK_DOWN:
                    
                    myCurrentGame.down();
                    break;
                    
                default:
                    break;
                    
            }
            
        }
        
    }

}
