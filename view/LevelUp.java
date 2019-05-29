/*
 * TCSS 305 Autumn 2018
 * Assignment 6
 */

package view;

/**
 * LevelUp is a value wrapper for the current level and the
 * next target goal for lines cleared.
 * 
 * @author Sam Wainright
 * @version 7 Dec 2018
 */
public class LevelUp {
    
    /** The current level of the game. */
    private int myLevel;
    
    /** The target lines cleared. */
    private int myTarget;

    /**
     * Public constructor for LevelUp.
     * 
     * @param theLevelGained The new level obtained for storage.
     * @param theNewTarget The new target assigned value for leveling up.
     */
    public LevelUp(final int theLevelGained, final int theNewTarget) {
        
        myLevel = theLevelGained;
        myTarget = theNewTarget;
        
    }

    /**
     * Return the current level.
     * 
     * @return The level value.
     */
    public int getMyLevel() {
        
        return myLevel;
        
    }

    /**
     * Return the target value.
     * 
     * @return The target value that must be surpased in order to level up.
     */
    public int getMyTarget() {
        return myTarget;
    }
    
    /**
     * Sets the level to a new one.
     * 
     * @param theLevel The level the object is being updated to.
     */
    public void setMyLevel(final int theLevel) {
        
        myLevel = theLevel;
        
    }

    /** 
     * Set target threshold value.
     * 
     * @param theTarget The new target value.
     */
    public void setMyTarget(final int theTarget) {
        
        myTarget = theTarget;
        
    }
    
}
