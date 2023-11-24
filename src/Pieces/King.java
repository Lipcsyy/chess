package Pieces;
import Main.ChessBoard;
import Main.ChessModel;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class King extends Piece {

    public King( ChessModel _model, int row, int col, boolean isWhite ) {
        this( _model, row, col, isWhite, col * _model.getTileSize(), row * _model.getTileSize() );
    }

    public King( ChessModel _model, int row, int col, boolean isWhite, int xPos, int yPos ) {

        super(_model, isWhite, row, col, xPos, yPos);

        this.name = isWhite ? 'K' : 'k';

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
        return Math.abs( (col - this.col) * ( row - this.row )) == 1 || Math.abs( col - this.col ) + Math.abs( row - this.row ) == 1 || canCastle( row, col );
    }

    @Override
    public boolean collidesWithPiece( int row, int col ) {
        return false;
    }


    @Override
    public Piece copy() {
        return new King( model, row, col, isWhite, xPos, yPos );
    }


    private boolean canCastle( int row, int col ) {

        if ( this.row == row ) {
            if ( col == 6 ) {
                Piece rook = model.getPiece(row, 7);
                if ( rook != null && rook.isFirstMove && this.isFirstMove ) {
                    return model.getPiece( row, 5 ) == null &&
                            model.getPiece( row, 6 ) == null &&
                            !model.checkScanner.wouldKingBeCheckedAfterMove( row, 5, false ) &&
                            !model.checkScanner.wouldKingBeCheckedAfterMove( row, 6, false );
                }
            } else if ( col == 2 ) {
                Piece rook = model.getPiece(row, 0);
                if ( rook != null && rook.isFirstMove && this.isFirstMove ) {
                    return model.getPiece( row, 1 ) == null &&
                            model.getPiece( row, 2 ) == null &&
                            model.getPiece( row, 3 ) == null &&
                            !model.checkScanner.wouldKingBeCheckedAfterMove( row, 1, false ) &&
                            !model.checkScanner.wouldKingBeCheckedAfterMove( row, 2, false );
                }
            }
        }


        return false;
    }

}

