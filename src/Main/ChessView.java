package Main;

import javax.swing.*;
import java.awt.*;

/**
 * The ChessView class is responsible for the graphical representation of the chess game.
 * It extends JPanel and contains a ChessBoard object that represents the chessboard view.
 * It also contains a reference to the ChessController to handle user input.
 */
public class ChessView extends JPanel {

    public static ChessBoard boardView;
    private ChessController controller;

    public int tileSize  = 80;

    /**
     * The constructor for the ChessView class.
     * It initializes the controller and boardView, and sets up the layout and size of the panel.
     *
     * @param _controller The controller for the chess game
     */
    public ChessView( ChessController _controller ) {

        this.controller = _controller;

        // Set the size of the panel
        this.setSize( new Dimension( Globals.SCREEN_WIDTH, Globals.SCREEN_HEIGHT ) );
        this.setMinimumSize( this.getMinimumSize() );

        // Set the layout of the panel
        this.setLayout( new GridLayout());

        // Create a box layout and add the chessboard view to it
        Box box = new Box(BoxLayout.Y_AXIS);
        boardView = new ChessBoard( controller );

        // Add vertical glue to center the chessboard view vertically
        box.add(Box.createVerticalGlue());
        box.add(boardView);
        box.add(Box.createVerticalGlue());

        // Add the box layout to the panel
        this.add( box );

    }

    /**
     * This method is used to get the ChessBoard object.
     * It returns the ChessBoard object that is used to display the game.
     *
     * @return The ChessBoard object
     */
    public ChessBoard getBoard() {
        return boardView;
    }

}