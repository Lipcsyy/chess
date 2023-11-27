package Main;

import Pieces.*;

import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.List;

public class ChessBoard extends JPanel{

    int cols = 8;
    int rows = 8;
    public int tileSize = 80;

    protected int selectedRow;
    protected int selectedCol;

    private ChessController controller;

    /**
     * The constructor for the ChessBoard class.
     * It initializes the controller and sets up the size, layout, and color of the board.
     *
     * @param _controller The controller for the chess game
     */
    public ChessBoard( ChessController _controller ) {

        this.controller = _controller;

        // Set the size of the board
        this.setPreferredSize( new Dimension(rows*tileSize, cols*tileSize) );
        this.setMaximumSize( new Dimension(rows*tileSize, cols*tileSize) );
        this.setMinimumSize( new Dimension(rows*tileSize, cols*tileSize) );

        // The layout of the board is commented out
        //this.setLayout( new GridLayout(8, 8) );

        // The color of the board is commented out
        //paintBoard( Color.decode("#F5E8B7"), Color.decode( "#B2533E" ), chessboard );

    }

    /**
     * This method is used to paint the board.
     *
     * @param g The Graphics object to be used to paint the board
     */
    @Override
    public void paintComponent(Graphics g) {

        //this method will draw out the board every time

        Graphics2D g2d = (Graphics2D) g;

        for ( int rows = 0 ; rows < 8; rows++ ) {
            for ( int cols = 0 ; cols < 8; cols++ ) {
                g2d.setColor( ( cols + rows ) % 2 == 0 ? Color.decode( "#F5EEC8" ) : Color.decode("#9A4444") );
                g2d.fillRect( cols*tileSize, rows*tileSize, tileSize, tileSize  );
            }
        }

        //System.out.println(ChessController.selectedPiece == null);

        if ( controller.chessModel.getSelectedPiece() != null ) {
            for ( int row = 0 ; row < 8; row++ ) {
                for ( int col = 0 ; col < 8; col++ ) {
                    if  ( controller.chessModel.isMovePseudoLegal( row, col ) ) {
                        g2d.setColor( new Color(68,150,57,190) );
                        g2d.fillRect(col*tileSize, row*tileSize, tileSize, tileSize  );
                    }
                }
            }
        }

        List<Piece> pieces = controller.chessModel.getPiecesList();

        for ( Piece piece : pieces ) {
            piece.paint( g2d );
        }

    }


    /**
     * This method is used to add a click listener to the chessboard.
     * It takes a MouseAdapter object as an argument and adds it as a mouse listener and a mouse motion listener to the chessboard.
     * The mouse listener is used to handle mouse click events, and the mouse motion listener is used to handle mouse movement events.
     *
     * @param mouseAdapter The MouseAdapter object to be added as a click listener
     */
    public void addClickListener ( MouseAdapter mouseAdapter ) {
        this.addMouseListener( mouseAdapter );
        this.addMouseMotionListener( mouseAdapter );
    }


}
