/*
 * TCSS 305 Autumn 2018
 * Assignment 6
 */

package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import model.Board;

/**
 * PreviewPanel is an augmented JPanel that displays
 * the next piece from the game's board.
 * 
 * @author Sam Servane
 * @version 29 Nov 2018
 */
public class PreviewPanel extends JPanel implements PropertyChangeListener {
    
    /** Generated serial version UID in compliance with implementation contract. */
    private static final long serialVersionUID = -8173688087126184470L;
    
    /** The default block size for the Tetris game's pieces. */
    private static final int BLOCK_SIZE = 32;
    
    /** 
     * The default background color for the preview panel. 
     * A globe/school cutting-board green.
     */
    private static final Color DEFAULT_COLOR = new Color(36, 182, 109);
    
    /** The default dimension for the preview panel. */
    private static final Dimension DEFAULT_DIMENSION = new Dimension(160, 160);
    
    /** The default array size of the block array for capturing pieces. */
    private static final int BLOCK_ARRAY_SIZE = 4;
    
    /** Centering offset in the x-coordinate direction for the "I" block. */
    private static final int HORIZONTAL_OFFSET_I = 15;
    
    /** Centering offset in the y-coordinate direction for the "I" block. */
    private static final int VERTICAL_OFFSET_I = 25;
    
    /** Centering offset in the x coordinate direction for the "O" block. */
    private static final int HORIZONTAL_OFFSET_O = 15;
    
    /** Centering offset in the y coordinate direction for the "O" block. */
    private static final int VERTICAL_OFFSET_O = 15;
    
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
    
    /** The current game board being interacted with. */
    private Board myTetrisGameBoard;
    
    /** The game panel that hosts the current game board. */
    private GamePanel myTetrisGamePanel;
    
    /** The next piece to be played in the game as String. */
    private String myNextPiece;
    
    /** Stores pictures of emerald blocks. */
    private List<BufferedImage> myEmeralds;

    /**
     * The public constructor of PreviewPanel. 
     * 
     * @param theBoard The current game board being interacted with.
     * @param theGamePanel The panel that host the current game board.
     * @throws IOException When the wrong file is read.
     */
    public PreviewPanel(final Board theBoard, 
                        final GamePanel theGamePanel) throws IOException {
        
        myTetrisGameBoard = theBoard;
        myTetrisGamePanel = theGamePanel;
        
        setBackground(DEFAULT_COLOR);
        setPreferredSize(DEFAULT_DIMENSION);
        setMaximumSize(DEFAULT_DIMENSION);
        
        myTetrisGameBoard.addPropertyChangeListener(this);
        
        buildEmeralds();
        
    }
    
    @Override
    public void paintComponent(final Graphics theGraphics) {
        
        super.paintComponent(theGraphics);
        final Graphics2D g2d = (Graphics2D) theGraphics;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                             RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Paint next piece only if the game is on
        if (myTetrisGamePanel.getMyGameIsOn()) {
            
            buildNextPiece(g2d);
            
        }
        
    }
    
    /**
     * {@inheritDoc}
     * 
     * Overridden propertyChange method that listens 
     * to game board state changes.
     */
    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        
        if (theEvent.getPropertyName().equals("preview")) {
            
            myNextPiece = theEvent.getNewValue().toString();
            
            repaint();
        }
    }
    
    // ================================ Private Helper Methods ===============================

    
    /**
     * The next piece to be drawn in the panel.
     * 
     * @param theGraphics The graphics being drawn.
     */
    private void buildNextPiece(final Graphics2D theGraphics) {
        
        // Set-up graphics
        final Graphics2D g2d = theGraphics;
        g2d.setStroke(new BasicStroke(1));
        g2d.setColor(Color.CYAN);
        
        // Split game board state string
        final String[] output = myNextPiece.split("\n");
        String[] suboutput;
        final String[][] throughput = new String[BLOCK_ARRAY_SIZE][BLOCK_ARRAY_SIZE];
        
        for (int i = 0; i < output.length; i++) {
            
            suboutput = output[i].split("");
            
            // Populate array
            for (int k = 0; k < suboutput.length; k++) {
                
                throughput[i][k] = suboutput[k];
            }
            
        }
        
        // Draw each and every block in the array
        for (int l = 0; l < throughput.length; l++) {
            
            for (int m = 0; m < throughput[l].length; m++) {
                
                if (!throughput[l][m].equals(" ")) {
                    
                    drawEmeralds(throughput[l][m], g2d, m + 1, l);
                    
                }
                
            }
            
        }
        
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
     * piece, from a stored list.
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
                              theX * BLOCK_SIZE - HORIZONTAL_OFFSET_I,
                              theY * BLOCK_SIZE + VERTICAL_OFFSET_I,
                              null);
                break;
                
            case "J":
                g2d.drawImage(myEmeralds.get(CYAN_EMERALD),
                              theX * BLOCK_SIZE,
                              theY * BLOCK_SIZE,
                              null);
                break;
                
            case "L":
                g2d.drawImage(myEmeralds.get(GREEN_EMERALD),
                              theX * BLOCK_SIZE,
                              theY * BLOCK_SIZE,
                              null);
                break;
                
            case "O":
                g2d.drawImage(myEmeralds.get(PINK_EMERALD),
                              theX * BLOCK_SIZE - HORIZONTAL_OFFSET_O,
                              theY * BLOCK_SIZE + VERTICAL_OFFSET_O,
                              null);
                break;
                
            case "S":
                g2d.drawImage(myEmeralds.get(RED_EMERALD),
                              theX * BLOCK_SIZE,
                              theY * BLOCK_SIZE,
                              null);
                break;
                
            case "T":
                g2d.drawImage(myEmeralds.get(SILVER_EMERALD),
                              theX * BLOCK_SIZE,
                              theY * BLOCK_SIZE,
                              null);
                break;
                
            case "Z":
                g2d.drawImage(myEmeralds.get(YELLOW_EMERALD),
                              theX * BLOCK_SIZE,
                              theY * BLOCK_SIZE,
                              null);
                break;
                
            default:
                break;
                
        }
        
    }

}
