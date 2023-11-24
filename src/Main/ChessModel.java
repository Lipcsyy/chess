package Main;

import Pieces.*;

import java.io.Serializable;
import java.util.*;

public class ChessModel implements Serializable {

    transient ChessController controller;
    transient public CheckScanner checkScanner = new CheckScanner(this);

    public char[][] chessboard;
    transient private List< Piece > pieces = new ArrayList< Piece >();
    HashMap<String, Integer> gameStates = new HashMap<>();


    private boolean isWhiteNextTurn = true;
    public int enPassantTile = -1;
    transient private int selectedPieceIndex = -1;


    public ChessModel( ChessController _controller ) {

        this.controller = _controller;

        /*this.chessboard = new char[][] {
                {' ', ' ' ,' ' ,' ', 'k',' ', ' ' ,' '},
                {' ', ' ' ,' ' ,' ', 'p',' ', ' ' ,' '},
                {' ', ' ' ,' ' ,' ', ' ',' ', ' ' ,' '},
                {' ', ' ' ,' ' ,' ', ' ',' ', ' ' ,' '},
                {' ', ' ' ,' ' ,' ', ' ',' ' , ' ' ,' '},
                {' ', ' ' ,' ' ,' ', ' ',' ', ' ' ,' '},
                {' ', ' ' ,' ' ,'P', ' ',' ', ' ' ,' '},
                {' ', ' ' ,' ' ,'K', ' ',' ', ' ' ,' '},
        };
        */

        this.chessboard = new char[][] {
                {'r', 'n' ,'b' ,'q', 'k','b', 'n' ,'r'},
                {'p', 'p' ,'p' ,'p', 'p','p', 'p' ,'p'},
                {' ', ' ' ,' ' ,' ', ' ',' ', ' ' ,' '},
                {' ', ' ' ,' ' ,' ', ' ',' ', ' ' ,' '},
                {' ', ' ' ,' ' ,' ', ' ',' ' , ' ' ,' '},
                {' ', ' ' ,' ' ,' ', ' ',' ', ' ' ,' '},
                {'P', 'P' ,'P' ,'P', 'P','P', 'P' ,'P'},
                {'R', 'N' ,'B' ,'Q', 'K','B', 'N' ,'R'},
        };

        addToGameState();
        addPieces();

    }


    // -----------------PIECE FUNCTIONS-----------------
    //--------------------------------------------------
    public void addPieces() {

        for ( int row = 0; row < 8; row++ ){
            for ( int col = 0; col < 8; col++) {

                char piece = chessboard[row][col];

                if ( piece != ' ' ) {

                    boolean isWhite = !Character.isLowerCase( piece );

                    ChessBoard board = controller.getBoard();

                    switch ( Character.toLowerCase( piece ) ) {
                        case 'n' -> pieces.add( new Knight( this, row, col, isWhite ) );
                        case 'p' -> pieces.add( new Pawn( this, row, col, isWhite ) );
                        case 'q' -> pieces.add( new Queen( this, row, col, isWhite ) );
                        case 'b' -> pieces.add( new Bishop( this, row, col, isWhite ) );
                        case 'r' -> pieces.add( new Rook( this, row, col, isWhite ) );
                        case 'k' -> pieces.add( new King( this, row, col, isWhite ) );
                    }
                }
            }
        }
    }

    public Piece getPiece( int row, int col ) {

        for ( Piece piece : getPiecesList() ) {
            if ( piece.row == row && piece.col == col) {
                return piece;
            }
        }

        return null;

    }

    public void removePiece( int row, int col) {

        Piece pieceToRemove = pieces.stream().filter( piece -> piece.row == row && piece.col == col ).findFirst().orElse(null );

        if ( pieceToRemove == null ) {
            throw new IllegalArgumentException();
        }

        if ( pieces.indexOf( pieceToRemove ) == selectedPieceIndex ) {
            selectedPieceIndex = -1;
        }

        else if ( pieces.indexOf( pieceToRemove ) < selectedPieceIndex ) {
            selectedPieceIndex--;
        }

        pieces.remove( pieceToRemove );

    }

    public List<Piece> getPiecesList() {
        return pieces;
    }

    public void setPiecesList( List< Piece > pieces ) {
        this.pieces = pieces;
    }

    public Piece getSelectedPiece() {

        if ( selectedPieceIndex == -1 )
            return null;

        return pieces.get( selectedPieceIndex );
    }

    // -----------------MOVING AND CAPTURING PIECES-----------------
    // -------------------------------------------------------------
    public void handleSameColorPiece(Piece selectedPiece, Piece oldPiece, int oldRow, int oldCol) {
        selectedPiece.col = oldCol;
        selectedPiece.row = oldRow;

        selectedPiece.xPos = oldCol * ChessView.boardView.tileSize;
        selectedPiece.yPos = oldRow * ChessView.boardView.tileSize;
    }

    public boolean isCheckMate() {

        //first we check if the king is checked
        if ( !checkScanner.isKingChecked( !getSelectedPiece().isWhite ) ) {
            return false;
        }

        if ( hasSpecificSideAnyMoves(getSelectedPiece().isWhite) ) return false;

        return true;

    }

    private boolean hasSpecificSideAnyMoves( boolean isCheckingWhite ) {

        //second we check if they are no possible moves for the other side
        for ( int i = 0; i < pieces.size(); i++ ) {

            Piece piece = pieces.get( i );

            if ( piece.isWhite != isCheckingWhite ) {
                continue;
            }

            //we need to fake all the moves here

            int oldSelectedPieceIndex = getSelectedPieceIndex() ;
            setSelectedPiece( piece );

            boolean whosTurnItIs = isWhiteNextTurn; //false after white moved
            setWhiteNextTurn( isCheckingWhite );

            for ( int row = 0; row < 8; row++ ) {
                for ( int col = 0; col < 8; col++ ) {
                    if (  isMovePseudoLegal( row, col )  ) {
                        setWhiteNextTurn( whosTurnItIs );
                        setSelectedPieceIndex(oldSelectedPieceIndex);
                        return true;
                    }

                }
            }

            setSelectedPieceIndex(oldSelectedPieceIndex);
            setWhiteNextTurn( whosTurnItIs );
        }
        return false;
    }

    public boolean isDraw() {

        //IF THE KING CAN'T MOVE AND IS NOT CHECKED IT'S A DRAW

        //check for the white
        if ( !checkScanner.isKingChecked( true ) && !hasSpecificSideAnyMoves(true) ) {
            return true;
        }

        //check for the black
        if ( !checkScanner.isKingChecked( false ) && !hasSpecificSideAnyMoves(false) ) {
            return true;
        }

        //THREEFOLD RULE: It happens when the same position is repeated three times. We check if there is three equal positions in the gamestate
        //If we move a pawn, or capture with any piece, we clear the gamestate. Otherwise we add to the game state.

        if ( gameStates.getOrDefault( convertToString( chessboard ), 0 ) == 3 ) {
            return true;
        }

        //two kings left
        if ( pieces.size() == 2 ) {
            return true;
        }

        //two kings and a knight or bishop
        if ( pieces.size() == 3 ) {
            for ( Piece piece : pieces ) {
                if ( piece instanceof Bishop || piece instanceof Knight ) {
                    return true;
                }
            }
        }

        //two kings and two bishops that are on the same color tile
        if ( pieces.size() == 4 ) {

            ArrayList<Piece> bishops = new ArrayList<>();

            for ( Piece piece : pieces ) {
                if ( piece instanceof Bishop ) {
                    bishops.add( piece );
                }
            }

            if ( bishops.size() == 2 ) {
                Piece bishop1 = bishops.get( 0 );
                Piece bishop2 = bishops.get( 1 );

                boolean isBishop1White = (bishop1.row + bishop1.col) % 2 == 0; //this checks if the bishop is on a white tile
                boolean isBishop2White = (bishop2.row + bishop2.col) % 2 == 0; //this checks if the bishop is on a white tile

                //if both of them are on the same color tile it's a draw
                if ( isBishop1White == isBishop2White ) {
                    return true;
                }

            }

        }

        return false;
    }

    public void move( Piece selectedPiece ,int row, int col,  boolean shouldUpdateUI)  {

        if ( Character.toLowerCase( selectedPiece.name ) == 'p' ) {
            pawnMove(selectedPiece, row, col, shouldUpdateUI );
        }
        else if ( Character.toLowerCase( selectedPiece.name ) == 'k' ) {

            moveKing(selectedPiece, row, col);

            selectedPiece.isFirstMove = false;

            if ( this.getPiece( row,col ) != null ) {
                capture( row, col );
            }

            selectedPiece.col = col;
            selectedPiece.row = row;

            if ( shouldUpdateUI ) {
               addToGameState();
               setPiecePosition( row, col );
            }

        }
        else {
            if ( this.getPiece( row,col ) != null && this.getPiece( row,col ).isWhite != selectedPiece.isWhite) {
                capture( row, col );
            }

            selectedPiece.isFirstMove = false;

            selectedPiece.row = row;
            selectedPiece.col = col;

            if ( shouldUpdateUI ) {
                setPiecePosition( row, col );
                gameStates.clear(); //after a move was made we clear the gameStates
                addToGameState(); //after we have cleared because of a capture we still need to add that new position to the gamestate
            }
        }
    }

    public void setPiecePosition( int row, int col ) {
        getSelectedPiece().xPos = col * controller.chessView.tileSize;
        getSelectedPiece().yPos = row * controller.chessView.tileSize;
    }

    public int getTileNum( int col, int row ) {
        return row*8 + col;
    }

    private void promotePawn( Piece selectedPiece, int row, int col ) {
        removePiece( row, col );
        pieces.add( new Queen( this, row, col, selectedPiece.isWhite ));
    }

    private void pawnMove( Piece selectedPiece, int row, int col, boolean shouldUpdateUI ) {

        //en passant
        int colorIndex = selectedPiece.isWhite ? 1 : -1;

        if ( getTileNum( row, col ) == enPassantTile ) {
            capture(row + colorIndex , col);
        }

        //capture
        if ( this.getPiece( row,col ) != null   ) {
            capture( row, col );
        }

        if ( shouldUpdateUI ) {
            enPassantTile = Math.abs( selectedPiece.row - row ) == 2 ? getTileNum( row + colorIndex, col ) : - 1;
        }

        colorIndex = selectedPiece.isWhite ? 0 : 7;

        selectedPiece.isFirstMove = false;

        selectedPiece.col = col;
        selectedPiece.row = row;

        if ( row == colorIndex ) {
            promotePawn(selectedPiece, row, col);
        }

        if  ( shouldUpdateUI ) {
            setPiecePosition( row, col );
            gameStates.clear();//after a move was made we clear the gameStates
            addToGameState();
        }

    }

    private void moveKing( Piece selectedPiece, int row, int col ) {
        if ( Math.abs( selectedPiece.col - col ) == 2 ) {
            Piece rook;
            if (  selectedPiece.col < col ) {
                rook = getPiece( row, 7 );
                rook.col = 5;
            } else {
                rook = getPiece( row, 0 );
                rook.col = 3;
            }

            rook.xPos = rook.col * controller.chessView.tileSize;

        }
    }

    public void capture( int row, int col){
        this.removePiece(row, col);
    }


    /**
     * Checks if the move is valid
     *
     * @param row the row we're moving the piece into
     * @param col the col we're moving the piece into
     * ...
     * @return It returns true if the move is valid and false otherwise
     */
   public boolean isMovePseudoLegal( int row, int col ) {

        char piece = chessboard[row][col];

        if  ( piece != ' ' && Character.isUpperCase( piece ) == getSelectedPiece().isWhite )
            return false;


        if ( getSelectedPiece().isWhite != isWhiteNextTurn ) {
            return false;
        }

        if ( !getSelectedPiece().isValidMove( row, col ) ) {
            return false;
        }

        if ( checkScanner.wouldKingBeCheckedAfterMove( row, col, getSelectedPiece().isWhite ) ) {
            return false;
        }


        if ( getSelectedPiece().collidesWithPiece( row, col ) ) {
            return false;
        }

        return true;

    }

    // -----------------BOARD FUNCTIONS-----------------
    // -------------------------------------------------

    public char[][] getChessboard() {
        return chessboard;
    }

    public void setChessboard( char[][] chessboard ) {
        this.chessboard = chessboard;
    }

    Piece findKing( boolean isWhite ) {
        for ( Piece piece : pieces ) {
            if ( Character.toLowerCase(piece.name) == 'k' && piece.isWhite == isWhite ) {
                return piece;
            }
        }
        return null;
    }

    public boolean isSameTeam( Piece p1, Piece p2 ) {
        return p1.isWhite == p2.isWhite;
    }

    public int getEnPassantTarget() {
        return enPassantTile;
    }

    public void setEnPassantTarget( int enPassantTarget ) {
        enPassantTile = enPassantTarget;
    }

    public int getTileSize() {
       return controller.chessView.tileSize;
    }

    public boolean isWhiteNextTurn() {
        return isWhiteNextTurn;
    }

    public void setWhiteNextTurn( boolean whiteNextTurn ) {
        isWhiteNextTurn = whiteNextTurn;
    }

    public void setSelectedPieceUIPosition( int x, int y ) {
       getSelectedPiece().xPos = x;
       getSelectedPiece().yPos = y;
    }

    public int getSelectedPieceIndex() {
        return selectedPieceIndex;
    }

    public void setSelectedPiece( Piece piece ) {
        this.selectedPieceIndex = pieces.indexOf( piece ) ;
    }

    public void setSelectedPieceIndex( int selectedPieceIndex ) {
        this.selectedPieceIndex = selectedPieceIndex;
    }

    public void printGameState() {
        for ( char[] row : chessboard ) {
            for ( char piece : row ) {
                System.out.print( piece + " " );
            }
            System.out.println();
        }
    }

    public void printGameState( char[][] chessboard ) {
        for ( char[] row : chessboard ) {
            for ( char piece : row ) {
                System.out.print( piece + " " );
            }
            System.out.println();
        }
    }

    private String convertToString(char[][] board) {
        StringBuilder sb = new StringBuilder();
        for (char[] row : board) {
            for (char cell : row) {
                sb.append(cell);
            }
        }
        return sb.toString();
    }

    private void addToGameState() {
        gameStates.put( convertToString( chessboard ), gameStates.getOrDefault( convertToString( chessboard ), 0 ) + 1 );
    }

    public void updateBoard(int newRow, int newCol) {
        // Clear the old position
        chessboard[getSelectedPiece().row][getSelectedPiece().col] = ' ';

        // Move the piece to the new position
        chessboard[newRow][newCol] = getSelectedPiece().name;

    }


}
