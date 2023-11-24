package Main;

import Pieces.Piece;

import java.util.ArrayList;
import java.util.List;

public class CheckScanner {

    ChessModel model;

    public CheckScanner( ChessModel _model ) {
        this.model = _model;
    }

    /**
     * Checks if the king is checked
     * @param row The row we're moving the piece into
     * @param col The column we're moving the piece into
     * @return true if the king is checked, false otherwise
     */
    public boolean wouldKingBeCheckedAfterMove( int row, int col, boolean isKingWhite ) {

        Piece king = model.findKing( isKingWhite );

        int kingRow;
        int kingCol;

        if ( model.getSelectedPiece() == king ) {
            kingRow = row;
            kingCol = col;
        }
        else {
            kingRow = king.row;
            kingCol = king.col;
        }

        List< Piece > pieceListCopy = model.getPiecesList().stream().map( ( piece ) -> piece.copy() ).toList();
        int selectedPieceIndex = model.getPiecesList().indexOf( model.getSelectedPiece() );

        int oldRow = model.getSelectedPiece().row;
        int oldCol = model.getSelectedPiece().col;

        model.move( model.getSelectedPiece(), row, col, false );

        boolean result = model.getPiecesList()
                .stream()
                .filter(piece -> piece.isWhite == !isKingWhite)
                .anyMatch( piece -> {

                    //we are checking if there is a piece that pins our king
                    if (piece.isValidMove( kingRow, kingCol ) && !piece.collidesWithPiece( kingRow, kingCol ) ) {
                        return true;
                    }

                    return false;
                });


        //undoing the fake move

        model.setPiecesList( new ArrayList<>(pieceListCopy) ) ;
        model.setSelectedPiece( model.getPiecesList().get( selectedPieceIndex ) );

        return result;

    }

    /**
     * Checks if the king is checked: if the white moved, we check for blacks king
     * @param isKingWhite
     * @return true if the king is checked, false otherwise
     */
    public boolean isKingChecked( boolean isKingWhite ) {

        //
        Piece king = model.findKing( isKingWhite );
        int kingRow = king.row;
        int kingCol = king.col;

        return model.getPiecesList().stream().filter( piece -> piece.isWhite != isKingWhite )
                .anyMatch( piece -> {
                    if ( piece.isValidMove( kingRow, kingCol ) && !piece.collidesWithPiece( kingRow, kingCol ) ) {
                        return true;
                    }
                    return false;
                } );

    }

}
