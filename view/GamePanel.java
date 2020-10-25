/*
 * TCSS 305 Autumn 2018
 * Assignment 6
 */

package view;

import controller.GameListener;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;
import model.Board;

/**
 * GamePanel is a panel that stores a runnable
 * Tetris game.
 * 
 * @author Sam Servane
 * @version 27 Nov 2018
 */
public class GamePanel extends JPanel implements PropertyChangeListener {
    
    /** Generated serial version UID in compliance to implementation contract. */
    private static final long serialVersionUID = 7889528028283061178L;
    
    /** The default block size for Tetris pieces. 30. */
    private static final int BLOCK_SIZE = 32;
    
    /** The row offset value for resizing array that stores game state. */
    private static final int ROW_START_OFFSET = 5;
    
    /** The default background color of the game panel. */
    private static final Color DEFAULT_COLOR = new Color(36, 182, 109);
    
    /** The default block color. */
    private static final Color BLOCK_COLOR = new Color(146, 146, 255, 255);
    
    /** The default color for the game over splash. */
    private static final Color GAME_OVER_COLOR = new Color(0, 0, 0, 150);
    
    /** The default color for the pause screen splash. */
    private static final Color PAUSE_COLOR = new Color(0, 0, 0, 255);
    
    /** The default color for the grid. */
    private static final Color GRID_COLOR = Color.CYAN;
    
    /** The default dimensions of the game panel. 300, 600. */
    private static final Dimension DEFAULT_DIMENSION = new Dimension(320, 640);
    
    /** Point object that holds the Game Over location to be drawn. */
    private static final Point GAME_OVER_LOCATION = new Point(62, 300);
    
    /** Point object that holds the Paused location to be drawn. */
    private static final Point PAUSE_LOCATION = new Point(94, 300);
    
    /** Index value for the blue emerald image. */
    private static final int BLUE_EMERALD = 0;
    
    /** Index value for the cyan emerald image. */
    private static final int CYAN_EMERALD = 1;
    
    /** Index value for the green emerald image. */
    private static final int GREEN_EMERALD = 2;
    
    /** Index value for the pink emerald image. */
    private static final int PINK_EMERALD = 3;
    
    /** Index value for the red emerald image. */
    private static final int RED_EMERALD = 4;
    
    /** Index value for the silver emerald image. */
    private static final int SILVER_EMERALD = 5;
    
    /** Index value for the yellow emerald image. */
    private static final int YELLOW_EMERALD = 6;
    
    /** The default timer delay. */
    private static final int TIMER_DELAY = 1000;
    
    /** The scale factor for timer delay adjustments. */
    private static final double HARMONIC_SCALE = 0.3;
    
    /** 
     *  A value that will be subtracted from the initial delay constantly.
     *  The value is an infinitely converging. It converges to the value 748.5470
     *  at n = 1000, meaning level 1000.
     */
    private double myHarmonicDelay;
    
    /** Stores the value for the next level. Used for calculating the delay. */
    private int myN = 1;
    
    /** The current active board being interacted with. */
    private Board myTetrisGameBoard;
    
    /** The current active score panel. */
    private ScorePanel myScorePanel;
    
    /** The active timer in the panel. */
    private Timer myTetrisTimer;
    
    /** The current state of the board as String type. */
    private String myCurrentState;
    
    /** Flag for updating game over status. */
    private boolean myGameIsOver;
    
    /** Flag that updates the status if the game is active or not. */
    private boolean myGameIsOn;
    
    /** Flag for updating game pausing status. */
    private boolean myGameIsPaused;
    
    /** File location for the custom font. */
    private String myAndesFont = "/resources/Andes.ttf";
    
    /** Stores pictures of emerald blocks. */
    private List<BufferedImage> myEmeralds;
    
    /**
     * The public constructor for the GamePanel.
     * 
     * @param theBoard The current board being interacted with.
     * @param theScorePanel The current active score panel.
     * @throws IOException Thrown when wrong file is read.
     */
    public GamePanel(final Board theBoard,
                     final ScorePanel theScorePanel) throws IOException {
        
        setBackground(DEFAULT_COLOR);
        setPreferredSize(DEFAULT_DIMENSION);
        setMaximumSize(DEFAULT_DIMENSION);
        
        // Set-up the game panel
        myTetrisGameBoard = theBoard; 
        myScorePanel = theScorePanel;
        myTetrisTimer = new Timer(TIMER_DELAY, new GameListener(myTetrisGameBoard));
        myTetrisGameBoard.addPropertyChangeListener(this);
        
        // Build the emerald pictures
        buildEmeralds();
        
    }
    
    @Override
    public void paintComponent(final Graphics theGraphics) {
        
        super.paintComponent(theGraphics);
        final Graphics2D g2d = (Graphics2D) theGraphics;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                             RenderingHints.VALUE_ANTIALIAS_ON);
        
        if (!myGameIsOn) { // Draw only if the game is off
            
            buildGrid(g2d);
            
        } else { // Draw everything otherwise
            
            buildGrid(g2d);
            buildBlocks(g2d);
            
        }
        
        if (myGameIsOver) { // Draw game over scenario
            
            buildGrid(g2d);
            buildBlocks(g2d);
            try {
                
                buildGameOver(g2d);
                
            } catch (final FontFormatException e) {
                
                e.printStackTrace();
            } catch (final IOException e) {

                e.printStackTrace();
                
            }
            
        }
        
        if (myGameIsPaused) { // Draw pause screen overlay
            
            try {
                
                buildPauseScreen(g2d);
                
            } catch (final FontFormatException e) {
                
                e.printStackTrace();
                
            } catch (final IOException e) {
                
                e.printStackTrace();
            }
            
        }
    }
    
    /**
     * Starts the board game and the game's timer.
     */
    public void startTimer() {
        
        myGameIsOn = true;
        myGameIsOver = false;
        
        myTetrisGameBoard.newGame();
        myHarmonicDelay = TIMER_DELAY;
        
        myScorePanel.reset();
        
        myTetrisTimer.start();
        
    }
    
    /**
     * Ends the board game and the board game's timer. Also,
     * it enables the "New Game" option in the program's graphical user interface.
     */
    public void gameOver() {
        
        myTetrisTimer.stop();
        myGameIsOver = true;
        myGameIsOn = false;
        
        repaint();
    }
    
    /**
     * Returns the flag for the game's activity status, whether it is
     * running or not.
     * 
     * @return The active current game status.
     */
    public boolean getMyGameIsOn() {
        
        return myGameIsOn;
        
    }
    
    /**
     * Returns the flag for the game's paused stated, whether it is
     * running or not.
     * 
     * @return The current pause game status.
     */
    public boolean getMyGameIsPaused() {
        
        return myGameIsPaused;
        
    }
    
    /**
     * Public setter for the game's paused state. Sets state to
     * the new input value.
     * 
     * @param thePauseFlag The new paused state.
     */
    public void setMyGameToPaused(final boolean thePauseFlag) {
        
        myGameIsPaused = thePauseFlag;
        
    }
    
    /**
     * Stops the timer when called.
     */
    public void pause() {
        
        myTetrisTimer.stop();
        
        repaint();
    }
    
    /**
     * Restarts the timer when called.
     */
    public void unpause() {
        
        myTetrisTimer.restart();
        
        repaint();
    }
    
    /** 
     * Levels up the game and updates the delay using a harmonic converging sequence.
     */
    public void levelUp() {
        
        myHarmonicDelay -= ((TIMER_DELAY * HARMONIC_SCALE) / 2) / myN;
        myN++;
        
        myTetrisTimer.setDelay(Integer.valueOf((int) myHarmonicDelay));
        
    }
    
    /**
     * {@inheritDoc}
     * 
     * Listens to property changes from the game board, updating the
     * current state of board as a String, and listening for the game over
     * event String.
     */
    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        
        if ("piece moved".equals(theEvent.getPropertyName())) {
            
            myCurrentState = theEvent.getNewValue().toString();
            
            repaint();
            
        } else if ("game over".equals(theEvent.getPropertyName())) {
            
            gameOver();
            
        } else if ("level up".equals(theEvent.getPropertyName())) {
            
            levelUp();
            
        }
    }
    
    // ============================== Private Helper Methods =================================
    
    /**
     * Draws the game board as a grid.
     * 
     * @param theGraphics The current graphics being drawn.
     */
    private void buildGrid(final Graphics2D theGraphics) {
        
        // Set-up graphics
        final Graphics2D g2d = theGraphics;
        g2d.setStroke(new BasicStroke(2));
        g2d.setColor(GRID_COLOR);
        
        // Draw grid
        for (int i = 0; i <= (getWidth() / BLOCK_SIZE); i++) {
            
            for (int j = 0; j <= (getHeight() / BLOCK_SIZE); j++) {
                
                g2d.drawRect(i * BLOCK_SIZE, j * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                
            }
            
        }   
        
    }
    
    /**
     * Draws the moveable Tetris pieces utilizing the current
     * state String, populating the string into an array and
     * draws each separate block using 2D array coordinates.
     * 
     * @param theGraphics The graphics being drawn.
     */
    private void buildBlocks(final Graphics2D theGraphics) {
        
        final Graphics2D g2d = theGraphics;
        g2d.setStroke(new BasicStroke(1));
        g2d.setColor(BLOCK_COLOR);
        
        final String[] output = myCurrentState.split("\n"); // Split the game into rows
        String[] suboutput;
        final String[][] throughput = new String[myTetrisGameBoard.getHeight()]
                        [myTetrisGameBoard.getWidth()];

        // "Line-by-line, character-by-character -- Jeremy Parks, previous 142 Lecturer"
        for (int i = ROW_START_OFFSET; i < output.length - 1; i++) {
            
            suboutput = output[i].split(""); // Split all the characters in the rows
            
            for (int k = 1; k < suboutput.length - 1; k++) {
                
                throughput[i - ROW_START_OFFSET][k - 1] = suboutput[k]; //Populate 2D array
                
            }
            
        }
        
        // Utilize 2D array for drawing coordinates
        for (int n = 0; n < throughput.length; n++) {
            
            for (int m = 0; m < throughput[n].length; m++) {
                
                drawEmeralds(throughput[n][m], theGraphics, m, n);
                
            }
                
        }
            
    }

    /**
     * Builds options for the Game Over screen and paints
     * it on the panel.
     * 
     * @param theGraphics The graphics being drawn.
     * @throws IOException Thrown when the file is read wrongly.
     * @throws FontFormatException Thrown when the font is formatted wrongly.
     */
    private void buildGameOver(final Graphics2D theGraphics) throws FontFormatException,
                                                                            IOException {
        
        // Set up graphics
        final Graphics2D g2d = theGraphics;
        g2d.setStroke(new BasicStroke(1));
        g2d.setColor(GAME_OVER_COLOR);
        
        // Draw opaque rectangle
        g2d.fillRect(0, 0, (int) DEFAULT_DIMENSION.getWidth(),
                     (int) DEFAULT_DIMENSION.getHeight());
        
        // Set up graphics, font, and draw "Game Over"
        final InputStream is = this.getClass().getResourceAsStream(myAndesFont);
        final Font font = Font.createFont(Font.PLAIN, is).deriveFont(Font.BOLD, 24f);
        
        g2d.setColor(Color.WHITE);
        g2d.setFont(font);
        g2d.drawString("G A M E   O V E R", (int) GAME_OVER_LOCATION.getX(),
                       (int) GAME_OVER_LOCATION.getY());
    }
    
    
    /**
     * Builds options for the Pause screen and paints
     * it on the panel. It obscures the user's view to prevent
     * cheating!
     * 
     * @param theGraphics The graphics being drawn.
     * @throws IOException Thrown when the wrong file is read.
     * @throws FontFormatException Thrown when the font is wrongly formatted.
     */
    private void buildPauseScreen(final Graphics2D theGraphics) throws FontFormatException,
                                                                               IOException {
        
        // Set up graphics
        final Graphics2D g2d = theGraphics;
        g2d.setStroke(new BasicStroke(1));
        g2d.setColor(PAUSE_COLOR);
        
        // Draw opaque rectangle
        g2d.fillRect(0, 0, (int) DEFAULT_DIMENSION.getWidth(),
                     (int) DEFAULT_DIMENSION.getHeight());

        // Set-up font
        final InputStream is = this.getClass().getResourceAsStream(myAndesFont);
        final Font font = Font.createFont(Font.PLAIN, is).deriveFont(Font.BOLD, 24f);
        
        g2d.setColor(Color.WHITE);
        g2d.setFont(font);
        g2d.drawString("P A U S E D", (int) PAUSE_LOCATION.getX(),
                       (int) PAUSE_LOCATION.getY());
    }
    
    /**
     * Creates an list of emerald image files to be used for the blocks.
     * 
     * @throws IOException When the wrong file is uploaded.
     */
    private void buildEmeralds() throws IOException {
        
        myEmeralds = new ArrayList<BufferedImage>();
        
        final BufferedImage blueemerald = ImageIO.read(this.getClass().
                                 getResource("/resources/blueemerald.png"));
        final BufferedImage cyanemerald = ImageIO.read(this.getClass().
                                                 getResource("/resources/cyanemerald.png"));
        final BufferedImage greenemerald = ImageIO.read(this.getClass().
                                                 getResource("/resources/greenemerald.png"));
        final BufferedImage pinkemerald = ImageIO.read(this.getClass().
                                                 getResource("/resources/pinkemerald.png"));
        final BufferedImage redemerald = ImageIO.read(this.getClass().
                                                 getResource("/resources/redemerald.png"));
        final BufferedImage silveremerald = ImageIO.read(this.getClass().
                                                 getResource("/resources/silveremerald.png"));
        final BufferedImage yellowemerald = ImageIO.read(this.getClass().
                                                 getResource("/resources/yellowemerald.png"));
        
        myEmeralds.add(blueemerald);
        myEmeralds.add(cyanemerald);
        myEmeralds.add(greenemerald);
        myEmeralds.add(pinkemerald);
        myEmeralds.add(redemerald);
        myEmeralds.add(silveremerald);
        myEmeralds.add(yellowemerald);
        
    }
    
    /**
     * Draws the emerald blocks on the game panel, 
     * adjusts their color based on the shape of the Tetris
     * piece.
     * 
     * @param theLetter The letter character associated with the piece.
     * @param theGraphics The graphics being drawn.
     * @param theX The x-coordinate of the board.
     * @param theY The y-coordinate of the board.
     */
    private void drawEmeralds(final String theLetter,
                              final Graphics2D theGraphics,
                              final int theX,
                              final int theY) {
        
        final Graphics2D g2d = theGraphics;
        
        switch (theLetter) {
            
            case "I":
                g2d.drawImage(myEmeralds.get(BLUE_EMERALD),
                              theX * BLOCK_SIZE + 1,
                              theY * BLOCK_SIZE + 1,
                              null);
                break;
                
            case "J":
                g2d.drawImage(myEmeralds.get(CYAN_EMERALD),
                              theX * BLOCK_SIZE + 1,
                              theY * BLOCK_SIZE + 1,
                              null);
                break;
                
            case "L":
                g2d.drawImage(myEmeralds.get(GREEN_EMERALD),
                              theX * BLOCK_SIZE + 1,
                              theY * BLOCK_SIZE + 1,
                              null);
                break;
                
            case "O":
                g2d.drawImage(myEmeralds.get(PINK_EMERALD),
                              theX * BLOCK_SIZE + 1,
                              theY * BLOCK_SIZE + 1,
                              null);
                break;
                
            case "S":
                g2d.drawImage(myEmeralds.get(RED_EMERALD),
                              theX * BLOCK_SIZE + 1,
                              theY * BLOCK_SIZE + 1,
                              null);
                break;
                
            case "T":
                g2d.drawImage(myEmeralds.get(SILVER_EMERALD),
                              theX * BLOCK_SIZE + 1,
                              theY * BLOCK_SIZE + 1,
                              null);
                break;
                
            case "Z":
                g2d.drawImage(myEmeralds.get(YELLOW_EMERALD),
                              theX * BLOCK_SIZE + 1,
                              theY * BLOCK_SIZE + 1,
                              null);
                break;
                
            default:
                break;
                
        }
        
    }

}
