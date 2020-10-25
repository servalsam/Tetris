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
 * GameKeyListener is a key listener for the 
 * current game state in session, which listens to key events
 * from the main keyboard section, specifically keys "A, S, W, D, Space Bar".
 * 
 * @author Sam Servane
 * @version 29 Nov 2018
 */
public class GameKeyListener extends KeyAdapter {
    
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
    public GameKeyListener(final Board theBoard, final GamePanel theGamePanel) {
        
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
                
                case KeyEvent.VK_A:
                    
                    myCurrentGame.left();
                    break;
                    
                case KeyEvent.VK_D:
                    
                    myCurrentGame.right();
                    break;
                    
                case KeyEvent.VK_W:
                    
                    myCurrentGame.rotate();
                    break;
                    
                case KeyEvent.VK_S:
                    
                    myCurrentGame.down();
                    break;
                    
                case KeyEvent.VK_SPACE:
                    
                    myCurrentGame.drop();
                    break;
                    
                default:
                    break;
                    
            }
            
        }
        
    }

}
