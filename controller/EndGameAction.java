/*
 * TCSS 305 Autumn 2018
 * Assignment 6
 */

package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import view.GamePanel;

/**
 * EndGameAction ends the game when
 * a selection is made to do so.
 * 
 * @author Sam Wainright
 * @version 1 Dec 2018
 */
public class EndGameAction implements ActionListener {
    
    /** The panel that stores the current active game board. */
    private GamePanel myCurrentGame;
    
    /** The menu option selected. */
    private JMenuItem myEndGameOption;
    
    /** The menu option enabled in reaction to selected action. */
    private JMenuItem myNewGameOption;
    
    /**
     * The public constructor for EndGameAction.
     * 
     * @param theGamePanel The panel hosting the current active game.
     * @param theMenuItem The menu item option that hosts the End Game action.
     * @param theMenuItem2 The menu item option that hosts the New Game action.
     */
    public EndGameAction(final GamePanel theGamePanel,
                         final JMenuItem theMenuItem,
                         final JMenuItem theMenuItem2) {
        
        myCurrentGame = theGamePanel;
        myEndGameOption = theMenuItem;
        myNewGameOption = theMenuItem2;
        
    }

    /**
     * {@inheritDoc}
     * 
     * When the "End Game option is selected, the game is ended.
     * The option is only available while a game is in session. The New Game
     * menu option will be toggled to enabled, while the End Game option will
     *  be toggled to disabled.
     */
    @Override
    public void actionPerformed(final ActionEvent theActionEvent) {
        
        // End the game and swap active buttons
        myCurrentGame.gameOver();
        myEndGameOption.setEnabled(false);
        myNewGameOption.setEnabled(true);
        
        myCurrentGame.repaint();
        
    }

}
