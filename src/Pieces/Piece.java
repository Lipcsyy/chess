package Pieces;
import Main.ChessBoard;
import Main.ChessModel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;

/**
 * The Piece class is an abstract class that represents a chess piece.
 * It contains information about the piece's position, color, and image, and whether it has moved yet.
 * It also contains methods to check if a move is valid and if it collides with another piece.
 */
public abstract class Piece implements Serializable {

    public int col, row; // The column and row of the piece on the chessboard
    public int xPos, yPos; // The x and y coordinates of the piece on the UI
    public char name; // The name of the piece ('p' for Pawn, 'r' for Rook, etc.)

    public boolean isWhite; // A boolean indicating the color of the piece (true for white, false for black)
    public boolean isFirstMove = true; // A boolean indicating whether the piece has moved yet

    BufferedImage image; // The image of the piece
    ChessModel model; // The model of the chess game

    /**
     * This method is used to check if a move is valid.
     * It takes the row and column of the move as arguments and returns a boolean indicating whether the move is valid.
     *
     * @param row The row of the move
     * @param col The column of the move
     * @return true if the move is valid, false otherwise
     */
    public abstract boolean isValidMove( int row, int col );


    /**
     * This method is used to check if a move collides with another piece.
     * It takes the row and column of the move as arguments and returns a boolean indicating whether the move collides with another piece.
     *
     * @param row The row of the move
     * @param col The column of the move
     * @return true if the move collides with another piece, false otherwise
     */
    public abstract boolean collidesWithPiece( int row, int col );


    /**
     * The constructor for the Piece class.
     * It initializes the model and color of the piece.
     *
     * @param _model The model for the chess game
     * @param isWhite A boolean indicating the color of the piece (true for white, false for black)
     */
    public Piece( ChessModel _model, boolean isWhite ) {
        this.model = _model;
        this.isWhite = isWhite;
    }


    /**
     * The constructor for the Piece class.
     * It initializes the model, color, row, column, and x and y coordinates of the piece.
     *
     * @param _model The model for the chess game
     * @param isWhite A boolean indicating the color of the piece (true for white, false for black)
     * @param row The row of the piece on the chessboard
     * @param col The column of the piece on the chessboard
     * @param xPos The x coordinate of the piece on the UI
     * @param yPos The y coordinate of the piece on the UI
     */
    public Piece( ChessModel _model, boolean isWhite, int row, int col, int xPos, int yPos ) {
        this.model = _model;
        this.isWhite = isWhite;
        this.row = row;
        this.col = col;
        this.xPos = xPos;
        this.yPos = yPos;
    }


    /**
     * This method is used to draw the piece on the UI.
     * It takes a Graphics2D object as an argument and draws the image of the piece at its x and y coordinates.
     *
     * @param g2d The Graphics2D object to draw on
     */
    public void paint( Graphics2D g2d ) {
        g2d.drawImage( image, xPos, yPos, model.getTileSize() , model.getTileSize(),null );
    }


    /**
     * This method is used to create a copy of the piece.
     * It returns a new Piece object that is a copy of the current piece.
     *
     * @return A new Piece object that is a copy of the current piece
     */
    public abstract Piece copy();

}