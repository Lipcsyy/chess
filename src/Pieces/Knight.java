package Pieces;
import Main.ChessBoard;
import Main.ChessModel;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Knight extends Piece {

    public Knight( ChessModel _model, int row, int col , boolean isWhite ) {
        this( _model, row, col, isWhite, col * _model.getTileSize(), row * _model.getTileSize() );
    }

    public Knight( ChessModel _model, int row, int col , boolean isWhite, int xPos, int yPos ) {

        super(_model, isWhite, row, col, xPos, yPos);

        this.name = isWhite ? 'N' : 'n';

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
    public boolean isValidMove( int _row, int _col ) {
        return Math.abs( _col - this.col ) * Math.abs( _row - this.row ) == 2;
    }

    @Override
    public boolean collidesWithPiece( int row, int col ) {
        return false;
    }

    @Override
    public Piece copy() {
        return new Knight( model, row, col, isWhite, xPos, yPos);
    }
}
