package Pieces;
import Main.ChessBoard;
import Main.ChessModel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public abstract class Piece implements Serializable {

    public int col, row;
    public int xPos, yPos;
    public char name;

    public boolean isWhite;
    public boolean isFirstMove = true;

    BufferedImage image;
    ChessModel model;

    public abstract boolean isValidMove( int row, int col );

    public abstract boolean collidesWithPiece( int row, int col );


    public Piece( ChessModel _model, boolean isWhite ) {
        this.model = _model;
        this.isWhite = isWhite;
    }

    public Piece( ChessModel _model, boolean isWhite, int row, int col, int xPos, int yPos ) {
        this.model = _model;
        this.isWhite = isWhite;
        this.row = row;
        this.col = col;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public void paint( Graphics2D g2d ) {
        g2d.drawImage( image, xPos, yPos, model.getTileSize() , model.getTileSize(),null );
    }

    public abstract Piece copy();

}
