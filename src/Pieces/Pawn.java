package Pieces;
import Main.ChessBoard;
import Main.ChessModel;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Pawn extends Piece {

    public Pawn( ChessModel _model, int row, int col , boolean isWhite ) {
        this( _model, row, col, isWhite, col * _model.getTileSize(), row * _model.getTileSize() );
    }

    public Pawn( ChessModel _model, int row, int col , boolean isWhite, int xPos, int yPos ) {

        super(_model, isWhite, row, col, xPos, yPos);

        this.name = isWhite ? 'P' : 'p';


        String fileName = (isWhite ? "w_" : "b_") + Character.toLowerCase( name );
        String path = "./src/Resources/" + fileName + ".png";

        try {
            this.image = ImageIO.read( new File(path) );
        }
        catch ( IOException e ) {
            throw new RuntimeException( e );
        }

    }

    @Override
    public boolean isValidMove( int row, int col ) {

        int colorIndex = isWhite ? 1 : -1;

        //move pawn one
        if ( this.col == col && row == this.row - colorIndex &&  model.getPiece( row, col ) == null )
            return true;

        //move pawn two
        if ( this.row == (isWhite ? 6 : 1) && (this.col == col && row == this.row - colorIndex * 2 &&  model.getPiece( row, col ) == null &&  model.getPiece( row + colorIndex, col ) == null ))
            return true;

        //capture left
        if ( this.col == col - 1  && row == this.row - colorIndex &&  model.getPiece( row, col ) != null )
            return true;

        //capture right
        if ( this.col == col +   1  && row == this.row - colorIndex &&  model.getPiece( row, col ) != null )
            return true;

        //en passent left
        if (  model.getTileNum( row, col ) == model.enPassantTile && col == this.col - 1 && row == this.row - colorIndex && model.getPiece( row + colorIndex, col ) != null )
            return true;

        //en passent right
        if (  model.getTileNum( row, col ) == model.enPassantTile && col == this.col + 1 && row == this.row - colorIndex && model.getPiece( row + colorIndex, col ) != null )
            return true;


        return false;
    }

    @Override
    public boolean collidesWithPiece( int row, int col ) {
        return false;
    }

    @Override
    public Piece copy() {
        return new Pawn( model, row, col, isWhite, xPos, yPos);
    }

}

