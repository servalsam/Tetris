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
 * NewGameAction solely starts the current game's timer.
 * 
 * @author Sam Servane
 * @version 1 Dec 2018
 */
public class NewGameAction implements ActionListener {
    
    /** The panel that hosts the current board game. */
    private GamePanel myCurrentGame;
    
    /** The menu item disabled for selecting this option. */
    private JMenuItem myNewGameOption;
    
    /** The menu item enabled for selecting this option. */
    private JMenuItem myEndGameOption;
    
    /**
     * The public constructor for the NewGameAction.
     * 
     * @param theGamePanel The panel that hosts a board game.
     * @param theMenuItem The menu item that hosts the "New Game" action. 
     * @param theMenuItem2 The menu item that hosts the "End Game" action.
     */
    public NewGameAction(final GamePanel theGamePanel,
                         final JMenuItem theMenuItem,
                         final JMenuItem theMenuItem2) {
        
        myCurrentGame = theGamePanel;
        myNewGameOption = theMenuItem;
        myEndGameOption = theMenuItem2;
        
    }

    /**
     * {@inheritDoc}
     * 
     * When the "Start Game" option is selected, the game is set into motion.
     * The option is only available while a game is not in session. The End Game
     * menu option will be toggled to enabled, while the New Game option will
     *  be toggled to disabled. Also begins the timer.
     */
    @Override
    public void actionPerformed(final ActionEvent theActionEvent) {
        
        myCurrentGame.startTimer();
        myNewGameOption.setEnabled(false);
        myEndGameOption.setEnabled(true);
        
    }
    
}
