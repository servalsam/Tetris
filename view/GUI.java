/*
 * TCSS 305 Autumn 2018
 * Assignment 6
 */

package view;

import controller.AboutAction;
import controller.DirectionalKeyListener;
import controller.EndGameAction;
import controller.GameKeyListener;
import controller.NewGameAction;
import controller.PauseKeyListener;
import controller.WindowCommands;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.Border;
import model.Board;

/**
 * The primary graphical user interface of the game. Encapsulates and displays
 * the functions of a Tetris game.
 * 
 * @author Sam Wainright
 * @version 27 Nov 2018
 */
public final class GUI extends JFrame implements PropertyChangeListener {

    /** Generated serial version UID in compliance with extension contract. */
    private static final long serialVersionUID = -2395601466722013248L;

    /** Determines how to scale the contents of the GUI. */
    private static final int SCALE = 6;
    
    /** Base width of the GUI frame. */
    private static final int WIDTH = 160;
    
    /** Base height of the GUI frame. */
    private static final int HEIGHT = WIDTH / 12 * 10;
    
    /** Default size of struts for panels. */
    private static final int DEFAULT_STRUT_SIZE = 50;
    
    /** Default dimensions of the eastward panel. */
    private static final Dimension DEFAULT_DIMENSION_EAST = new Dimension(300, 500);
    
    /** Default background color of the east panel. */
    private static final Color EAST_COLOR = new Color(237, 210, 164, 255);
    
    /** Raised bevel border used for setting borders to the peculiar style. */
    private static final Border RAISED_BEVEL = BorderFactory.createRaisedBevelBorder();
    
    /** The background image of the content panel. */
    private BufferedImage myBgImage;
    
    /** The icon for the game. */
    private BufferedImage myProgramIcon;
    
    /** The background image of the east panel. */
    private BufferedImage myEastPanelBgImage;
    
    /** The current game board being interacted with. */
    private Board myTetrisGameBoard;
    
    /** The panel for displaying the Tetris game. */
    private GamePanel myTetrisGamePanel;
    
    /** Overall panel structure that supports embedded panels. */
    private JPanel myContentPanel;
    
    /** The panel that displays the next piece in the Tetris game. */
    private PreviewPanel myPiecePreview;
    
    /** The panel that displays the current score. */
    private ScorePanel myScoreBox;
    
    /** The menu of the base frame. */
    private JMenu myGameMenu;
    
    /** The menu item that starts a new game. */
    private JMenuItem myNewGameItem;
    
    /** The menu item that ends the current game. */
    private JMenuItem myEndGameItem;
    
    /** The menu item that explains various information about the program to the user. */
    private JMenuItem myAboutItem;
    
    /** The primary key listener for the frame that listens to main keyboard events. */
    private GameKeyListener myGameKeyboardListener;
    
    /** The secondary key listener for the frame that listens to directional key pad events. */
    private DirectionalKeyListener myDirectionalPadListener;
    
    /** The key listener for the frame that listens to pause key events. */
    private PauseKeyListener myPauseKeyListener;
    
    
    
    // ================================= Public Methods ======================================
    
    /**
     * Builds the components of the Graphical User Interface.
     * 
     * @throws IOException Thrown wrong file is read while building the graphics or panels.
     */
    public void build() throws IOException {

        buildMainFrame();
        buildGraphics();
        buildContentPanel();
        buildGamePanel();
        buildEastPanel();
        setJMenuBar(buildFrameMenu());
        buildListeners();
        
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
    }
    
    /**
     * Public getter for the "New Game" menu item option.
     * 
     * @return The menu option with the "New Game" option binded to it.
     */
    public JMenuItem getMyNewGameItem() {
        
        return myNewGameItem;
        
    }
    
    /**
     * Public getter for the "End Game" menu item option.
     * 
     * @return The menu option with the "End Game" option binded to it.
     */
    public JMenuItem getMyEndGameItem() {
        
        return myEndGameItem;
        
    }

    // ============================ Private Helper Methods ===================================
    
    /**
     * Private helper method that builds the main frame
     * of the Graphical User Interface.
     * @throws IOException Thrown when the wrong image file is selected.
     */
    private void buildMainFrame() throws IOException {
        
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        myProgramIcon = ImageIO.read(this.getClass().
                                       getResource("/resources/programicon.png"));
        setTitle("TCSS 305 - Tetris");
        setIconImage(myProgramIcon);
        
        setPreferredSize(new Dimension(WIDTH * (SCALE - 1), HEIGHT * SCALE));
        setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
         
        pack();
        
        setLocation((int) Math.round((screenSize.getWidth() 
                        - getWidth()) / 2), 
                    (int) Math.round((screenSize.getHeight()
                        - getHeight()) / 2));
        
    }
    
    /**
     * Private helper method for building a default foundation
     * panel for nesting other panels.
     * @throws IOException Thrown when the wrong file is uploaded.
     */
    private void buildContentPanel() throws IOException {
        
        // Anonymous Inner class
        myContentPanel = new JPanel() {
            
            /** Auto-generated serial version UID in compliance with extension contract. */
            private static final long serialVersionUID = 6332404119828695346L;

            protected void paintComponent(final Graphics theGraphics) {
                
                theGraphics.drawImage(myBgImage, 0, 0, null);
                
            }
        };
        myContentPanel.setLayout(new BorderLayout());
        myContentPanel.setPreferredSize(new Dimension(WIDTH * (SCALE / 2), HEIGHT * SCALE));
        myContentPanel.setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        add(myContentPanel, BorderLayout.CENTER);
        
    }
    
    /**
     * Private helper method for building the panel
     * that game content resides in.
     * @throws IOException Thrown when wrong file is read.
     */
    private void buildGamePanel() throws IOException {
        
        final Border raisedbevel = BorderFactory.createRaisedBevelBorder();
        myTetrisGameBoard = new Board();
        myScoreBox = new ScorePanel(myTetrisGameBoard);
        myTetrisGamePanel = new GamePanel(myTetrisGameBoard, myScoreBox);
        
        
        myTetrisGamePanel.setBorder(raisedbevel);
        
        // Set the content panel spacing
        final Box box = new Box(BoxLayout.Y_AXIS);
        box.setAlignmentX(Box.LEFT_ALIGNMENT);
        box.setBackground(Color.LIGHT_GRAY);
        box.add(Box.createVerticalGlue());
        box.add(myTetrisGamePanel);
        box.add(Box.createVerticalGlue());
        
        myContentPanel.add(box, BorderLayout.CENTER); 
        
    }
    
    /** 
     * Private helper method to build the east panel on the frame.
     * @throws IOException When the wrong file is read in the preview panel.
     */
    private void buildEastPanel() throws IOException {
        
        myPiecePreview = new PreviewPanel(myTetrisGameBoard, myTetrisGamePanel);
        
        
        // Creates East Panel with two entities
        final JPanel eastPanel = new JPanel()  {

            /** Auto-generated serial version UID in compliance with extension contract. */
            private static final long serialVersionUID = 6332404119828695346L;

            protected void paintComponent(final Graphics theGraphics) {
                
                theGraphics.drawImage(myEastPanelBgImage, 2, 2, null);
                
            }
        };
        
        // Set-up East Panel
        eastPanel.setMaximumSize(DEFAULT_DIMENSION_EAST);
        eastPanel.setBorder(RAISED_BEVEL);
        final BoxLayout secondBox = new BoxLayout(eastPanel, BoxLayout.PAGE_AXIS);
        eastPanel.setLayout(secondBox);
        eastPanel.setBackground(EAST_COLOR);
        eastPanel.add(Box.createVerticalStrut(DEFAULT_STRUT_SIZE));
        myPiecePreview.setBorder(RAISED_BEVEL);
        eastPanel.add(myPiecePreview);
        eastPanel.add(Box.createVerticalStrut(DEFAULT_STRUT_SIZE));
        myScoreBox.setBorder(RAISED_BEVEL);
        eastPanel.add(myScoreBox);
        add(eastPanel, BorderLayout.EAST);
        eastPanel.add(Box.createVerticalStrut(DEFAULT_STRUT_SIZE));
        
    }
    
    /**
     * Private helper method for creating a menu bar for the frame.
     * 
     * @return The created menu.
     * @throws IOException Thrown when the wrong file is uploaded in AboutAction.
     */
    private JMenuBar buildFrameMenu() throws IOException {
        
        final JMenuBar mainMenuBar = new JMenuBar();
        myGameMenu = new JMenu("Game");
        myNewGameItem = new JMenuItem("New Game");
        myEndGameItem = new JMenuItem("End Game");
        
        final JMenu helpMenu = new JMenu("Help");
        myAboutItem = new JMenuItem("About...");
        
        // Create New Game option
        myNewGameItem.addActionListener(new NewGameAction(myTetrisGamePanel,
                                                          myNewGameItem,
                                                          myEndGameItem));
        myGameMenu.add(myNewGameItem);
        
        myGameMenu.addSeparator();
        
        // Create End Game option
        myEndGameItem.addActionListener(new EndGameAction(myTetrisGamePanel,
                                                          myEndGameItem,
                                                          myNewGameItem));
        
        // Add Game Menu to menu bar
        myEndGameItem.setEnabled(false);
        myGameMenu.add(myEndGameItem);
        mainMenuBar.add(myGameMenu);
        
        // Add Help Menu to menu bar
        myAboutItem.addActionListener(new AboutAction());
        helpMenu.add(myAboutItem);
        mainMenuBar.add(helpMenu);
        
        
        return mainMenuBar;
        
    }
    
    /**
     * Builds the active Listener objects for the game
     * and assigns them to the designated objects.
     */
    private void buildListeners() {
        
        myGameKeyboardListener = new GameKeyListener(myTetrisGameBoard,
                                                     myTetrisGamePanel);
        myDirectionalPadListener = new DirectionalKeyListener(myTetrisGameBoard,
                                                              myTetrisGamePanel);
        myPauseKeyListener = new PauseKeyListener(myTetrisGamePanel);
        
        addKeyListener(myGameKeyboardListener);
        addKeyListener(myDirectionalPadListener);
        addKeyListener(myPauseKeyListener);
        addWindowListener(new WindowCommands(myTetrisGamePanel));
        
        // Register GUI to the game board as a PropertyChangeListener
        myTetrisGameBoard.addPropertyChangeListener(this);
        
        // Register the GamePanel as receiver for ScorePanel
        myScoreBox.addPropertyChangeListener(myTetrisGamePanel);
        
    }
    
    /**
     * Private helper method that draws the graphics of the frame.
     * 
     * @throws IOException Thrown when the wrong file is input.
     */
    private void buildGraphics() throws IOException {
        
        myBgImage = ImageIO.read(this.getClass().
                                 getResource("/resources/contentbackground.png"));
        
        myEastPanelBgImage = ImageIO.read(this.getClass().
                                 getResource("/resources/eastpanelbackground.png"));
                        
    }

    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        
        if ("game over".equals(theEvent.getPropertyName())) {
            
            myNewGameItem.setEnabled(true);
            myEndGameItem.setEnabled(false);
            
        }
        
    }
}
