package Pieces;
import Main.ChessBoard;
import Main.ChessModel;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Bishop extends Piece {

    public Bishop( ChessModel _model, int row, int col, boolean isWhite ) {
        this( _model, row, col, isWhite, col * _model.getTileSize(), row * _model.getTileSize() );
    }

    public Bishop( ChessModel _model, int row, int col, boolean isWhite, int xPos, int yPos ) {

        super(_model, isWhite, row, col, xPos, yPos);

        this.name = isWhite ? 'B' : 'b';

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
        return Math.abs( this.col - col ) == Math.abs( this.row - row );
    }

    @Override
    public boolean collidesWithPiece( int row, int col ) {

        //up left = row greater col less
        if ( this.col > col && this.row > row ) {
            for ( int i = 1; i < Math.abs( this.col - col ) ; i++ ) {
                if ( model.getPiece( this.row - i, this.col - i ) != null )
                    return true;
            }
        }

        //up right row greater col greater
        if ( this.col < col && this.row > row ) {
            for ( int i = 1; i < Math.abs( this.col - col ); i++ ) {
                if ( model.getPiece( this.row - i, this.col + i ) != null )
                    return true;
            }
        }

        //down left
        if ( this.col > col && this.row < row ) {
            for ( int i = 1; i < Math.abs( this.col - col ) ; i++ ) {
                if ( model.getPiece( this.row + i, this.col - i ) != null )
                    return true;
            }
        }

        //down right row greater col greater
        if ( this.col < col && this.row < row ) {
            for ( int i = 1; i < Math.abs( this.col - col ) ; i++ ) {
                if ( model.getPiece( this.row + i, this.col + i ) != null )
                    return true;
            }
        }

        return false;
    }

    @Override
    public Piece copy() {
        return new Bishop( model, row, col, isWhite, xPos, yPos );
    }
}
