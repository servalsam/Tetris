/*
 * TCSS 305 Autumn 2018
 * Assignment 6
 */

package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.JPanel;
import model.Board;

/**
 * ScorePanel displays the score for the current
 * game in session.
 * 
 * @author Sam Wainright
 * @version 29 Nov 2018
 */
public class ScorePanel extends JPanel implements PropertyChangeListener {
    
    /** Generated serial version UID in compliance with extension contract. */
    private static final long serialVersionUID = 1095074052127142269L;

    /** 
     * The default color for the score panel is a shade of matching a iconic mascot's hue.
     * It is the classic version of the color from 1994.
     */
    private static final Color SONIC_THE_HEDGEHOG_COLOR = new Color(13, 122, 233);
    
    /** The default dimensions for the panel. */
    private static final Dimension DEFAULT_DIMENSION = new Dimension(250, 250);
    
    /** The value that matches a one row clear  instance. */
    private static final int ONE_ROW = 1;
    
    /** The value that holds a one row score multipler. */
    private static final int ONE_ROW_BONUS = 50;
    
    /** The value that matches a two row clear instance. */
    private static final int TWO_ROWS = 2;
    
    /** The value that holds a two row score multipler. */
    private static final int TWO_ROW_BONUS = 150;
    
    /** The value that matches a three row clear  instance. */
    private static final int THREE_ROWS = 3;
    
    /** The value that holds a three row score multipler. */
    private static final int THREE_ROW_BONUS = 350;
    
    /** The value that matches a four row clear  instance. Formally known as a "Tetris". */ 
    private static final int TETRIS = 4;
    
    /** The value that holds a four row score multipler. Formally known as a "Tetris". */
    private static final int TETRIS_BONUS = 1000;
    
    /** The value for the threshold number to surpass in lines cleared to level up. */
    private static final int THRESHOLD = 4;
    
    /** The point position for the score text frame. */
    private static final Point SCORE_POINT = new Point(10, 60);
    
    /** The point position for the level text frame. */
    private static final Point LEVEL_POINT = new Point(10, 120);
    
    /** The point position for the lines cleared text frame. */
    private static final Point LINES_CLEARED_POINT = new Point(10, 180);
    
    /** The current active board being interacted with. */
    private Board myTetrisGameBoard;
    
    /** The current score of the game. */
    private int myScore;
    
    /** The total lines cleared of the game. */
    private int myLinesClearedTotal;
    
    /** Object holds the requirements to level up. */
    private LevelUp myLevelUp;
    
    /** PropertyChangeSupport for firing events. */
    private PropertyChangeSupport myPCS = new PropertyChangeSupport(this);

    /**
     * The public constructor of the score panel.
     * 
     * @param theBoard The current board game in session.
     */
    public ScorePanel(final Board theBoard) {
        
        setBackground(SONIC_THE_HEDGEHOG_COLOR);
        setPreferredSize(DEFAULT_DIMENSION);
        setMaximumSize(DEFAULT_DIMENSION);
        
        myTetrisGameBoard = theBoard;
        
        myLevelUp = new LevelUp(1, THRESHOLD);
        
        myTetrisGameBoard.addPropertyChangeListener(this);
        
    }

    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        
        if (theEvent.getPropertyName().equals("lines cleared")) {
            
            final Integer value = ((Integer[]) theEvent.getNewValue()).length;
            
            calculateScore(value);
            
            if (value > 0) {
                
                myLinesClearedTotal += value;
                checkForLevelUp();
                
            }

            repaint();
            
        }
        
    }
    
    /**
     * Resets the current data within the score panel.
     */
    public void reset() {
        
        myScore = 0;
        myLinesClearedTotal = 0;
        myLevelUp = new LevelUp(1, THRESHOLD);
        
    }
    
    /**
     * Calculates the current game's score for the player
     * by factoring in bonuses based on player performance.
     * 
     * @param theRowValue The number of rows of pieces cleared in the game.
     */
    private void calculateScore(final int theRowValue) {
        
        switch (theRowValue) {
            
            case ONE_ROW:
                myScore = myScore + ONE_ROW_BONUS * (myLevelUp.getMyLevel() + 1);
                break;
                
            case TWO_ROWS:
                myScore = myScore + TWO_ROW_BONUS * (myLevelUp.getMyLevel() + 1);
                break;
                
            case THREE_ROWS:
                myScore = myScore + THREE_ROW_BONUS * (myLevelUp.getMyLevel() + 1);
                break;
                
            case TETRIS:
                myScore = myScore + TETRIS_BONUS * (myLevelUp.getMyLevel() + 1);
                break;
                
            default:
                break;
                
        }
        
    }
    
    @Override
    public void paintComponent(final Graphics theGraphics) {
        
        super.paintComponent(theGraphics);
        final Graphics2D g2d = (Graphics2D) theGraphics;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                             RenderingHints.VALUE_ANTIALIAS_ON);
        
        try {
            
            drawScoreText(g2d);
            
        } catch (final FontFormatException e) {
            
            e.printStackTrace();
            
        } catch (final IOException e) {
            
            e.printStackTrace();
            
        }
        
    }
    
    /**
     * Draws the informational text for the game's score,
     * current level, and total lines cleared.
     * 
     * @param theGraphics The graphics being drawn.
     * @throws FontFormatException Thrown if the font is in the wrong file format.
     * @throws IOException Thrown if the file cannot be found.
     */
    private void drawScoreText(final Graphics2D theGraphics) throws FontFormatException,
                                                                    IOException {
        
        final Graphics2D g2d = theGraphics;
        
        
        final InputStream is = this.getClass().getResourceAsStream("/resources/Andes.ttf");
        final Font font = Font.createFont(Font.PLAIN, is).deriveFont(Font.BOLD, 24f);
        
        g2d.setColor(Color.WHITE);
        g2d.setFont(font);
        
        g2d.drawString("Score: " + myScore,
                       (int) SCORE_POINT.getX(),
                       (int) SCORE_POINT.getY());
        
        g2d.drawString("Level:    " + myLevelUp.getMyLevel(),
                       (int) LEVEL_POINT.getX(),
                       (int) LEVEL_POINT.getY());
        
        g2d.drawString("Lines Cleared: " + myLinesClearedTotal,
                       (int) LINES_CLEARED_POINT.getX(),
                       (int) LINES_CLEARED_POINT.getY());
        
    }
    
    /**
     * Checks if the target line count was exceeded and levels the game up.
     */
    private void checkForLevelUp() {
        
        if (myLinesClearedTotal > myLevelUp.getMyTarget()) {
            
            final int previousLevel = myLevelUp.getMyLevel();
            final int previousTarget = myLevelUp.getMyTarget();
            myPCS.firePropertyChange("level up", previousLevel, previousLevel + 1);
            myLevelUp = new LevelUp(previousLevel + 1, previousTarget + THRESHOLD);
            
        }
        
    }
    
    // ================== Required Methods for PropertyChangeSupport =========================
    

    /**
     * Adds a listener for property change events from this class.
     * 
     * @param theListener a PropertyChangeListener to add.
     */
    public void addPropertyChangeListener(final PropertyChangeListener theListener) {
        myPCS.addPropertyChangeListener(theListener);
    }
    
    /**
     * Removes a listener for property change events from this class.
     * 
     * @param theListener a PropertyChangeListener to remove.
     */
    public void removePropertyChangeListener(final PropertyChangeListener theListener) {
        myPCS.removePropertyChangeListener(theListener);
    }
    
}
