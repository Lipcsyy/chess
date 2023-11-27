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


    transient  String playerWhite = "";
    transient  String playerBlack = "";

    public ChessModel( ChessController _controller ) {

        this.controller = _controller;

        this.chessboard = new char[][] {
                {' ', ' ' ,' ' ,' ', 'k',' ', ' ' ,'r'},
                {' ', ' ' ,' ' ,' ', 'q',' ', ' ' ,' '},
                {' ', ' ' ,' ' ,' ', ' ',' ', ' ' ,' '},
                {' ', ' ' ,' ' ,' ', ' ',' ', ' ' ,' '},
                {' ', ' ' ,' ' ,' ', ' ',' ' , ' ' ,' '},
                {' ', ' ' ,' ' ,' ', ' ',' ', ' ' ,' '},
                {' ', ' ' ,' ' ,' ', ' ',' ', ' ' ,' '},
                {' ', ' ' ,' ' ,'K', ' ',' ', ' ' ,' '},
        };

        /*this.chessboard = new char[][] {
                { 'r', 'n', 'b', 'q', 'k', 'b', 'n', 'r' },
                { 'p', 'p', 'p', ' ', 'p', ' ', 'P', 'p' },
                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                { 'P', 'P', 'P', 'P', ' ', 'P', 'P', 'P' },
                { 'R', 'N', 'B', 'Q', 'K', 'B', 'N', 'R' },
        };*/


        /*this.chessboard = new char[][] {
                { 'r', 'n', 'b', 'q', 'k', 'b', 'n', 'r' },
                { 'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p' },
                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                { 'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P' },
                { 'R', 'N', 'B', 'Q', 'K', 'B', 'N', 'R' },
        };*/

        addToGameState();
        addPieces();

    }


    // -----------------PIECE FUNCTIONS-----------------
    //--------------------------------------------------
    /**
     * This method is used to add pieces to the chessboard.
     * It iterates over the chessboard and for each non-empty cell, it creates a new piece of the appropriate type.
     * The type of the piece is determined by the character in the cell ('n' for Knight, 'p' for Pawn, etc.).
     * The color of the piece is determined by the case of the character (lowercase for black, uppercase for white).
     * The newly created piece is then added to the list of pieces.
     */
    public void addPieces() {

    // Iterate over the rows of the chessboard
    for ( int row = 0; row < 8; row++ ){
        // Iterate over the columns of the chessboard
        for ( int col = 0; col < 8; col++) {

            // Get the character representing the piece at the current cell
            char piece = chessboard[row][col];

            // If the cell is not empty
            if ( piece != ' ' ) {

                // Determine the color of the piece (true for white, false for black)
                boolean isWhite = !Character.isLowerCase( piece );

                // Get the current state of the chessboard
                ChessBoard board = controller.getBoard();

                // Create a new piece of the appropriate type and add it to the list of pieces
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

    /**
     * This method is used to get a piece from the chessboard at a specific location.
     * It iterates over the list of pieces and returns the piece that matches the given row and column.
     * If no piece is found at the given location, it returns null.
     *
     * @param row The row of the piece on the chessboard
     * @param col The column of the piece on the chessboard
     * @return The piece at the given location or null if no piece is found
     */
    public Piece getPiece( int row, int col ) {

        // Iterate over the list of pieces
        for ( Piece piece : getPiecesList() ) {
            // If the piece's row and column match the given row and column, return the piece
            if ( piece.row == row && piece.col == col) {
                return piece;
            }
        }

        // If no piece is found at the given location, return null
        return null;

    }


    /**
     * This method is used to remove a piece from the chessboard at a specific location.
     * It iterates over the list of pieces and removes the piece that matches the given row and column.
     * If no piece is found at the given location, it throws an IllegalArgumentException.
     * If the removed piece is the currently selected piece, it resets the selected piece index.
     * If the removed piece's index is less than the selected piece index, it decrements the selected piece index.
     *
     * @param row The row of the piece on the chessboard
     * @param col The column of the piece on the chessboard
     */
    public void removePiece( int row, int col) {

        // Find the piece to remove
        Piece pieceToRemove = pieces.stream().filter( piece -> piece.row == row && piece.col == col ).findFirst().orElse(null );

        // If no piece is found at the given location, throw an IllegalArgumentException
        if ( pieceToRemove == null ) {
            throw new IllegalArgumentException();
        }

        // If the removed piece is the currently selected piece, reset the selected piece index
        if ( pieces.indexOf( pieceToRemove ) == selectedPieceIndex ) {
            selectedPieceIndex = -1;
        }

        // If the removed piece's index is less than the selected piece index, decrement the selected piece index
        else if ( pieces.indexOf( pieceToRemove ) < selectedPieceIndex ) {
            selectedPieceIndex--;
        }

        // Remove the piece from the list of pieces
        pieces.remove( pieceToRemove );

}


    /**
     * This method is used to get the list of pieces currently on the chessboard.
     * It returns a list of Piece objects, where each Piece represents a chess piece on the board.
     *
     * @return A list of Piece objects representing the chess pieces on the board
     */
    public List<Piece> getPiecesList() {
        return pieces;
    }


    /**
     * This method is used to set the list of pieces currently on the chessboard.
     * It takes a list of Piece objects as an argument and sets the pieces field to this list.
     *
     * @param pieces A list of Piece objects representing the chess pieces to be placed on the board
     */
    public void setPiecesList( List< Piece > pieces ) {
        this.pieces = pieces;
    }


    /**
     * This method is used to get the currently selected piece on the chessboard.
     * It returns the Piece object at the index specified by the selectedPieceIndex field.
     * If the selectedPieceIndex is -1, which indicates that no piece is currently selected, it returns null.
     *
     * @return The currently selected Piece object, or null if no piece is currently selected
     */
    public Piece getSelectedPiece() {

        if ( selectedPieceIndex == -1 )
            return null;

        return pieces.get( selectedPieceIndex );
    }

    // -----------------MOVING AND CAPTURING PIECES-----------------
    // -------------------------------------------------------------

    /**
     * This method is used to handle the situation when a piece is moved to a location occupied by a piece of the same color.
     * It resets the position of the selected piece to its original location.
     *
     * @param selectedPiece The piece that is being moved
     * @param oldPiece The piece that was originally at the destination location
     * @param oldRow The original row of the selected piece
     * @param oldCol The original column of the selected piece
     */
    public void handleSameColorPiece(Piece selectedPiece, Piece oldPiece, int oldRow, int oldCol) {
        selectedPiece.col = oldCol;
        selectedPiece.row = oldRow;

        selectedPiece.xPos = oldCol * ChessView.boardView.tileSize;
        selectedPiece.yPos = oldRow * ChessView.boardView.tileSize;
    }


    /**
     * This method is used to check if the current game state is a checkmate.
     * It first checks if the king of the current player is in check.
     * Then it checks if the current player has any legal moves left.
     * If the king is in check and the player has no legal moves, it is a checkmate.
     *
     * @return true if the current game state is a checkmate, false otherwise
     */
    public boolean isCheckMate() {

        //first we check if the king is checked
        if ( !checkScanner.isKingChecked( !getSelectedPiece().isWhite ) ) {
            return false;
        }

        if ( hasSpecificSideAnyMoves(!getSelectedPiece().isWhite) ) return false;

        return true;

    }


    /**
     * This method is used to check if a specific side has any legal moves left.
     * It iterates over all the pieces of the specified color and checks if any of them have a legal move.
     *
     * @param isCheckingWhite true if checking for white pieces, false if checking for black pieces
     * @return true if the specified side has any legal moves, false otherwise
     */
    private boolean hasSpecificSideAnyMoves( boolean isCheckingWhite ) {

        //second we check if they are no possible moves for the other side
        for ( int i = 0; i < pieces.size(); i++ ) {

            Piece piece = pieces.get( i );

            //we want to check for the opposite side
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


    /**
     * This method is used to check if the current game state is a draw.
     * It checks for several conditions that would result in a draw, including stalemate, threefold repetition, insufficient material, and others.
     *
     * @return true if the current game state is a draw, false otherwise
     */
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


    /**
     * This method is used to move a selected piece to a specified location on the chessboard.
     * The method handles different types of pieces differently.
     * For pawns, it handles en passant and promotion.
     * For kings, it handles castling.
     * For other pieces, it simply moves the piece to the new location.
     * If the move results in a capture, it removes the captured piece from the board.
     * After the move, it updates the game state and the position of the piece on the UI.
     *
     * @param selectedPiece The piece that is being moved
     * @param row The row to which the piece is being moved
     * @param col The column to which the piece is being moved
     * @param shouldUpdateUI A boolean indicating whether the UI should be updated after the move
     */
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


    /**
     * This method is used to set the position of the selected piece on the UI.
     * It calculates the x and y coordinates based on the column and row of the piece and the size of the tiles on the UI.
     *
     * @param row The row of the piece on the chessboard
     * @param col The column of the piece on the chessboard
     */
    public void setPiecePosition( int row, int col ) {
        getSelectedPiece().xPos = col * controller.chessView.tileSize;
        getSelectedPiece().yPos = row * controller.chessView.tileSize;
    }


    /**
     * This method is used to get the number of the tile at a specific location on the chessboard.
     * It calculates the tile number based on the row and column of the tile.
     *
     * @param col The column of the tile on the chessboard
     * @param row The row of the tile on the chessboard
     * @return The number of the tile at the given location
     */
    public int getTileNum( int col, int row ) {
        return row*8 + col;
    }


    /**
     * This method is used to promote a pawn to a queen when it reaches the opposite end of the chessboard.
     * It removes the pawn from the board and adds a new queen at the same location.
     *
     * @param selectedPiece The pawn that is being promoted
     * @param row The row of the pawn on the chessboard
     * @param col The column of the pawn on the chessboard
     */
    private void promotePawn( Piece selectedPiece, int row, int col ) {
        removePiece( row, col );
        pieces.add( new Queen( this, row, col, selectedPiece.isWhite ));
        setSelectedPiece( pieces.get( pieces.size() - 1 ) );
        setBoardFromPieces( pieces );
    }


    /**
     * This method is used to handle the movement of a pawn.
     * It handles en passant and captures.
     * If the pawn reaches the opposite end of the chessboard, it promotes the pawn to a queen.
     *
     * @param selectedPiece The pawn that is being moved
     * @param row The row to which the pawn is being moved
     * @param col The column to which the pawn is being moved
     * @param shouldUpdateUI A boolean indicating whether the UI should be updated after the move
     */
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
        else {
            if ( shouldUpdateUI ) {
                setPiecePosition( row, col );
                gameStates.clear();//after a move was made we clear the gameStates
                addToGameState();
            }
        }

    }


    /**
     * This method is used to handle the movement of a king.
     * It handles castling by moving the rook to the appropriate location when the king castles.
     *
     * @param selectedPiece The king that is being moved
     * @param row The row to which the king is being moved
     * @param col The column to which the king is being moved
     */
    private void moveKing( Piece selectedPiece, int row, int col ) {
        if ( Math.abs( selectedPiece.col - col ) == 2 ) {
            Piece rook;
            if (  selectedPiece.col < col ) {
                rook = getPiece( row, 7 );
                if ( rook != null ) {
                    rook.col = 5;
                }
            } else {
                rook = getPiece( row, 0 );
                if ( rook != null ) {
                    rook.col = 3;
                }
            }

            if (rook != null )
                rook.xPos = rook.col * controller.chessView.tileSize;
        }
    }


    /**
     * This method is used to capture a piece at a specific location on the chessboard.
     * It removes the piece from the board.
     *
     * @param row The row of the piece on the chessboard
     * @param col The column of the piece on the chessboard
     */
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

    /**
     * This method is used to get the current state of the chessboard.
     * It returns a 2D array of characters, where each character represents a piece on the board.
     *
     * @return A 2D array of characters representing the current state of the chessboard
     */
    public char[][] getChessboard() {
        return chessboard;
    }


    /**
     * This method is used to set the current state of the chessboard.
     * It takes a 2D array of characters as an argument and sets the chessboard field to this array.
     *
     * @param chessboard A 2D array of characters representing the new state of the chessboard
     */
    public void setChessboard( char[][] chessboard ) {
        this.chessboard = chessboard;
    }


    /**
     * This method is used to find the king of a specific color on the chessboard.
     * It iterates over the list of pieces and returns the first piece that is a king and matches the given color.
     * If no king of the given color is found, it returns null.
     *
     * @param isWhite A boolean indicating the color of the king to find (true for white, false for black)
     * @return The king of the given color, or null if no king of the given color is found
     */
    Piece findKing( boolean isWhite ) {
        for ( Piece piece : pieces ) {
            if ( Character.toLowerCase(piece.name) == 'k' && piece.isWhite == isWhite ) {
                return piece;
            }
        }
        return null;
    }


    /**
     * This method is used to check if two pieces are on the same team.
     * It compares the colors of the two pieces and returns true if they are the same, false otherwise.
     *
     * @param p1 The first piece
     * @param p2 The second piece
     * @return true if the two pieces are on the same team, false otherwise
     */
    public boolean isSameTeam( Piece p1, Piece p2 ) {
        return p1.isWhite == p2.isWhite;
    }


    /**
     * This method is used to get the target tile for en passant.
     * It returns the number of the tile that is the target for en passant.
     *
     * @return The number of the target tile for en passant
     */
    public int getEnPassantTarget() {
        return enPassantTile;
    }


    /**
     * This method is used to set the target tile for en passant.
     * It takes a number as an argument and sets the enPassantTile field to this number.
     *
     * @param enPassantTarget The number of the new target tile for en passant
     */
    public void setEnPassantTarget( int enPassantTarget ) {
        enPassantTile = enPassantTarget;
    }


    /**
     * This method is used to get the size of the tiles on the UI.
     * It returns the size of the tiles in pixels.
     *
     * @return The size of the tiles on the UI in pixels
     */
    public int getTileSize() {
       return controller.chessView.tileSize;
    }


    /**
     * This method is used to check whose turn it is.
     * It returns true if it is white's turn, false otherwise.
     *
     * @return true if it is white's turn, false otherwise
     */
    public boolean isWhiteNextTurn() {
        return isWhiteNextTurn;
    }


    /**
     * This method is used to set whose turn it is.
     * It takes a boolean as an argument and sets the isWhiteNextTurn field to this value.
     *
     * @param whiteNextTurn A boolean indicating whose turn it is (true for white, false for black)
     */
    public void setWhiteNextTurn( boolean whiteNextTurn ) {
        isWhiteNextTurn = whiteNextTurn;
    }


    /**
     * This method is used to set the position of the selected piece on the UI.
     * It takes the x and y coordinates as arguments and sets the xPos and yPos fields of the selected piece to these values.
     *
     * @param x The new x coordinate of the selected piece on the UI
     * @param y The new y coordinate of the selected piece on the UI
     */
    public void setSelectedPieceUIPosition( int x, int y ) {
       getSelectedPiece().xPos = x;
       getSelectedPiece().yPos = y;
    }


    /**
     * This method is used to get the index of the currently selected piece in the list of pieces.
     * It returns the index of the selected piece in the list of pieces.
     *
     * @return The index of the currently selected piece in the list of pieces
     */
    public int getSelectedPieceIndex() {
        return selectedPieceIndex;
    }


    /**
     * This method is used to set the currently selected piece.
     * It takes a Piece object as an argument and sets the selectedPieceIndex field to the index of this piece in the list of pieces.
     *
     * @param piece The Piece object to be set as the currently selected piece
     */
    public void setSelectedPiece( Piece piece ) {
        this.selectedPieceIndex = pieces.indexOf( piece ) ;
    }


    /**
     * This method is used to set the index of the currently selected piece in the list of pieces.
     * It takes an index as an argument and sets the selectedPieceIndex field to this index.
     *
     * @param selectedPieceIndex The index to be set as the index of the currently selected piece in the list of pieces
     */
    public void setSelectedPieceIndex( int selectedPieceIndex ) {
        this.selectedPieceIndex = selectedPieceIndex;
    }


    /**
     * This method is used to print the current state of the chessboard to the console.
     * It iterates over the chessboard and prints each piece.
     * Each row of the chessboard is printed on a new line.
    */
    public void printGameState() {
        for ( char[] row : chessboard ) {
            for ( char piece : row ) {
                System.out.print( piece + " " );
            }
            System.out.println();
        }
    }


    /**
     * This method is used to print a given state of the chessboard to the console.
     * It takes a 2D array of characters representing the state of the chessboard as an argument.
     * It iterates over the chessboard and prints each piece.
     * Each row of the chessboard is printed on a new line.
     *
     * @param chessboard A 2D array of characters representing the state of the chessboard to be printed
     */
    public void printGameState( char[][] chessboard ) {
        for ( char[] row : chessboard ) {
            for ( char piece : row ) {
                System.out.print( piece + " " );
            }
            System.out.println();
        }
    }


    /**
     * This method is used to convert the current state of the chessboard to a string.
     * It takes a 2D array of characters representing the chessboard as an argument.
     * It iterates over the chessboard and appends each character to a StringBuilder.
     * The resulting string represents the current state of the chessboard.
     *
     * @param board A 2D array of characters representing the state of the chessboard
     * @return A string representing the current state of the chessboard
     */
    private String convertToString(char[][] board) {
        StringBuilder sb = new StringBuilder();
        for (char[] row : board) {
            for (char cell : row) {
                sb.append(cell);
            }
        }
        return sb.toString();
    }


    /**
     * This method is used to add the current state of the chessboard to the game states.
     * It converts the current state of the chessboard to a string and adds it to the gameStates field.
     * If the current state of the chessboard is already in the gameStates field, it increments the count of this state.
     * Otherwise, it adds the state to the gameStates field with a count of 1.
     */
    private void addToGameState() {
        gameStates.put( convertToString( chessboard ), gameStates.getOrDefault( convertToString( chessboard ), 0 ) + 1 );
    }

    public void updateBoard(int newRow, int newCol) {

       /*// Clear the old position
        chessboard[getSelectedPiece().row][getSelectedPiece().col] = ' ';

        // Move the piece to the new position
        chessboard[newRow][newCol] = getSelectedPiece().name;*/

        setBoardFromPieces( pieces );

    }

    /**
     * This method is used to get a list of pieces from the current state of the chessboard.
     * It iterates over the chessboard and for each non-empty cell, it creates a new piece of the appropriate type.
     * The type of the piece is determined by the character in the cell ('n' for Knight, 'p' for Pawn, etc.).
     * The color of the piece is determined by the case of the character (lowercase for black, uppercase for white).
     * The newly created piece is then added to the list of pieces.
     *
     * @return A list of Piece objects representing the pieces on the chessboard
     */
    public List< Piece> getPiecesFromBoard() {

       List< Piece > piecesFromBoard = new ArrayList< Piece >();

       for ( int row = 0; row < 8; row++ ){
           for ( int col = 0; col < 8; col++) {

               char piece = chessboard[row][col];

               if ( piece != ' ' ) {

                   boolean isWhite = !Character.isLowerCase( piece );

                   ChessBoard board = controller.getBoard();

                   switch ( Character.toLowerCase( piece ) ) {
                       case 'n' -> piecesFromBoard.add( new Knight( this, row, col, isWhite ) );
                       case 'p' -> piecesFromBoard.add( new Pawn( this, row, col, isWhite ) );
                       case 'q' -> piecesFromBoard.add( new Queen( this, row, col, isWhite ) );
                       case 'b' -> piecesFromBoard.add( new Bishop( this, row, col, isWhite ) );
                       case 'r' -> piecesFromBoard.add( new Rook( this, row, col, isWhite ) );
                       case 'k' -> piecesFromBoard.add( new King( this, row, col, isWhite ) );
                   }
               }
           }
       }

       return piecesFromBoard;
   }


    /**
     * This method is used to set the current state of the chessboard based on a list of pieces.
     * It takes a list of Piece objects as an argument and updates the chessboard field accordingly.
     *
     * @param pieces A list of Piece objects representing the pieces to be placed on the chessboard
     */
    public void setBoardFromPieces( List< Piece > pieces ) {

        char[][] chessboard = new char[8][8];

        for ( int row = 0; row < 8; row++ ){
            for ( int col = 0; col < 8; col++) {
                chessboard[row][col] = ' ';
            }
        }

        for ( Piece piece : pieces ) {
            chessboard[piece.row][piece.col] = piece.name;
        }

        this.chessboard = chessboard;
    }


    /**
     * This method is used to set the names of the players.
     * It takes two strings as arguments and sets the playerWhite and playerBlack fields to these strings.
     *
     * @param playerWhite The name of the player playing as white
     * @param playerBlack The name of the player playing as black
     */
    public void setPlayers(String playerWhite, String playerBlack) {
        this.playerWhite = playerWhite;
        this.playerBlack = playerBlack;
    }


    /**
     * This method is used to get the name of the player playing as white.
     *
     * @return The name of the player playing as white
     */
    public String getPlayerWhite() {
        return playerWhite;
    }


    /**
     * This method is used to get the name of the player playing as black.
     *
     * @return The name of the player playing as black
     */
    public String getPlayerBlack() {
        return playerBlack;
    }

}
