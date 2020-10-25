/*
 * TCSS 305 Autumn 2018
 * Assignment 6
 */

package view;

import java.awt.EventQueue;
import java.io.IOException;

/**
 * The entry point of the program.
 * 
 * @author Sam Servane
 * @version 27 Nov 2018
 */
public final class Main {

    /** Prevents instantiation of Main class. */
    private Main() {

    }

    /**
     * Starts the program and initializes the Graphical User Interface.
     * 
     * @param theArgs The command line arguments are non-existing and are
     *            ignored.
     */
    public static void main(final String[] theArgs) {

        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {

                try {
                    new GUI().build();
                } catch (final IOException e) {
                    // TODO Auto-generated catch block
                }
                
            }

        });
        
    }

}
