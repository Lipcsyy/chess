import GamePanels.GameFrame;
import Main.ChessController;
import Main.ChessModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class Tests {

    ChessModel chessModel;
    ChessController chessController;

    private boolean doBoardsEqual( char[][] board1, char[][] board2 ) {

        for ( int row = 0 ; row < 8; row++ ) {
            for ( int col = 0 ; col < 8; col++ ) {
                if ( board1[row][col] != board2[row][col] ) {
                    return false;
                }
            }
        }

        return true;

    }

    private void makeMove( int moveToRow, int moveToColumn ) {
        chessModel.updateBoard( moveToRow, moveToColumn );
        if(chessModel.isMovePseudoLegal( moveToRow, moveToColumn )) {
            chessModel.move( chessModel.getSelectedPiece(), moveToRow, moveToColumn, true );
        }
    }

    @Before
    public void setUp() {
        GameFrame frame = new GameFrame();
        chessController = new ChessController(frame);
        chessModel = chessController.getChessModel();
    }

    @Test
    public void isChessModelInitializedCorrectly() {
        Assert.assertNotNull(chessModel);
    }

    @Test
    public void isChessBoardInitializedCorrectly() {

        char[][] chessboard = chessModel.getChessboard();

        char[][] board = new char[][]{
                { 'r', 'n', 'b', 'q', 'k', 'b', 'n', 'r' },
                { 'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p' },
                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                { 'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P' },
                { 'R', 'N', 'B', 'Q', 'K', 'B', 'N', 'R' },

        };

        Assert.assertTrue( doBoardsEqual( chessboard, board ) );


    }

    //TESTING MOVEMENTS
    @Test
    public void isPawnMovedCorrectly() {

            chessModel.setSelectedPiece( chessModel.getPiece( 6, 4 ) );
            int moveToRow = 4;
            int moveToCol = 4;

            chessModel.updateBoard( moveToRow, moveToCol );
            if(chessModel.isMovePseudoLegal( moveToRow, moveToCol )) {
                chessModel.move( chessModel.getSelectedPiece(), moveToRow, moveToCol, true );
            }

            var chessboard = chessModel.getChessboard();

            char[][] board = new char[][]{
                    { 'r', 'n', 'b', 'q', 'k', 'b', 'n', 'r' },
                    { 'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p' },
                    { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                    { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                    { ' ', ' ', ' ', ' ', 'P', ' ', ' ', ' ' },
                    { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                    { 'P', 'P', 'P', 'P', ' ', 'P', 'P', 'P' },
                    { 'R', 'N', 'B', 'Q', 'K', 'B', 'N', 'R' },

            };

            Assert.assertTrue( doBoardsEqual( chessboard, board ) );
    }

    @Test
    public void isPawnMoveInvalid() {

        chessModel.setSelectedPiece( chessModel.getPiece( 6, 4 ) );
        int moveToRow = 5;
        int moveToColumn = 5;

        boolean isMoveValid = chessModel.isMovePseudoLegal( moveToRow, moveToColumn );

        Assert.assertFalse( isMoveValid );

    }

    @Test
    public void isPawnMoveInvalid2() {

        chessModel.setSelectedPiece( chessModel.getPiece( 6, 4 ) );
        int moveToRow = 5;
        int moveToColumn = 3;

        boolean isMoveValid = chessModel.isMovePseudoLegal( moveToRow, moveToColumn );

        Assert.assertFalse( isMoveValid );

    }

    @Test
    public void isKnightMoveValid() {

            chessModel.setSelectedPiece( chessModel.getPiece( 7, 1 ) );
            int moveToRow = 5;
            int moveToColumn = 2;

            boolean isMoveValid = chessModel.isMovePseudoLegal( moveToRow, moveToColumn );

            Assert.assertTrue( isMoveValid );
    }

    @Test
    public void isKnightMoveInvalid() {
        chessModel.setSelectedPiece( chessModel.getPiece( 7, 1 ) );
        int moveToRow = 5;
        int moveToColumn = 3;

        boolean isMoveValid = chessModel.isMovePseudoLegal( moveToRow, moveToColumn );

        Assert.assertFalse( isMoveValid );
    }

    @Test
    public void isKnightMoveInvalid2() {
        chessModel.setSelectedPiece( chessModel.getPiece( 7, 1 ) );
        int moveToRow = 6;
        int moveToColumn = 3;

        boolean isMoveValid = chessModel.isMovePseudoLegal( moveToRow, moveToColumn );

        Assert.assertFalse( isMoveValid );
    }

    @Test
    public void isBishopMoveValid() {

        char[][] board = new char[][]{
                { ' ', ' ', ' ', 'q', 'k', ' ', ' ', ' ' },
                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                { ' ', ' ', ' ', ' ', 'p', ' ', ' ', ' ' },
                { ' ', ' ', ' ', 'B', ' ', ' ', ' ', ' ' },
                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                { ' ', ' ', ' ', 'Q', 'K', ' ', ' ', ' ' },

        };

        chessModel.setChessboard(board);
        chessModel.setPiecesList( chessModel.getPiecesFromBoard() );

        chessModel.setSelectedPiece( chessModel.getPiece( 4, 3 ) );
        int moveToRow = 3;
        int moveToColumn = 2;

        boolean isMoveValid = chessModel.isMovePseudoLegal( moveToRow, moveToColumn );

        Assert.assertTrue( isMoveValid );

    }

    @Test
    public void isBishopMoveInvalid() {

        char[][] board = new char[][]{
                { ' ', ' ', ' ', 'q', 'k', ' ', ' ', ' ' },
                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                { ' ', ' ', ' ', ' ', 'p', ' ', ' ', ' ' },
                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                { ' ', ' ', ' ', 'B', ' ', ' ', ' ', ' ' },
                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                { ' ', ' ', ' ', 'Q', 'K', ' ', ' ', ' ' },
                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },

        };

        chessModel.setChessboard(board);
        chessModel.setPiecesList( chessModel.getPiecesFromBoard() );

        chessModel.setSelectedPiece( chessModel.getPiece( 4, 3 ) );
        int moveToRow = 5;
        int moveToColumn = 3;

        boolean isMoveValid = chessModel.isMovePseudoLegal( moveToRow, moveToColumn );

        Assert.assertFalse( isMoveValid );

    }

    @Test
    public void isQueenMoveValid() {

        char[][] board = new char[][]{
                { ' ', ' ', ' ', 'q', 'k', ' ', ' ', ' ' },
                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                { ' ', ' ', ' ', 'p', ' ', ' ', ' ', ' ' },
                { ' ', ' ', ' ', ' ', 'p', ' ', ' ', ' ' },
                { ' ', ' ', ' ', 'Q', ' ', ' ', ' ', ' ' },
                { ' ', ' ', ' ', ' ', 'p', ' ', ' ', ' ' },
                { ' ', ' ', ' ', 'P', ' ', ' ', ' ', ' ' },
                { ' ', ' ', ' ', ' ', 'K', ' ', ' ', ' ' },

        };

        chessModel.setChessboard(board);
        chessModel.setPiecesList( chessModel.getPiecesFromBoard() );

        chessModel.setSelectedPiece( chessModel.getPiece( 4, 3 ) );
        int moveToRow = 5;
        int moveToColumn = 2;

        boolean isMoveValid = chessModel.isMovePseudoLegal( moveToRow, moveToColumn );

        Assert.assertTrue( isMoveValid );
}

    @Test
    public void isQueenMoveValid2() {

            char[][] board = new char[][]{
                    { ' ', ' ', ' ', 'q', 'k', ' ', ' ', ' ' },
                    { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                    { ' ', ' ', ' ', 'p', ' ', ' ', ' ', ' ' },
                    { ' ', ' ', ' ', ' ', 'p', ' ', ' ', ' ' },
                    { ' ', ' ', ' ', 'Q', ' ', ' ', ' ', ' ' },
                    { ' ', ' ', ' ', ' ', 'p', ' ', ' ', ' ' },
                    { ' ', ' ', ' ', 'P', ' ', ' ', ' ', ' ' },
                    { ' ', ' ', ' ', ' ', 'K', ' ', ' ', ' ' },

            };

            chessModel.setChessboard(board);
            chessModel.setPiecesList( chessModel.getPiecesFromBoard() );

            chessModel.setSelectedPiece( chessModel.getPiece( 4, 3 ) );
            int moveToRow = 5;
            int moveToColumn = 3;

            boolean isMoveValid = chessModel.isMovePseudoLegal( moveToRow, moveToColumn );

            Assert.assertTrue( isMoveValid );
    }

    @Test
    public void isQueenMoveInvalid() {

        char[][] board = new char[][]{
            { ' ', ' ', ' ', 'q', 'k', ' ', ' ', ' ' },
            { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
            { ' ', ' ', ' ', 'p', ' ', ' ', ' ', ' ' },
            { ' ', ' ', ' ', ' ', 'p', ' ', ' ', ' ' },
            { ' ', ' ', ' ', 'Q', ' ', ' ', ' ', ' ' },
            { ' ', ' ', ' ', ' ', 'p', ' ', ' ', ' ' },
            { ' ', ' ', ' ', 'P', ' ', ' ', ' ', ' ' },
            { ' ', ' ', ' ', ' ', 'K', ' ', ' ', ' ' },
        };

        chessModel.setChessboard(board);
        chessModel.setPiecesList( chessModel.getPiecesFromBoard() );

        chessModel.setSelectedPiece( chessModel.getPiece( 4, 3 ) );
        int moveToRow = 6;
        int moveToColumn = 3;

        boolean isMoveValid = chessModel.isMovePseudoLegal( moveToRow, moveToColumn );

        Assert.assertFalse( isMoveValid );
    }

    @Test
    public void isRookMoveValid() {

        char[][] board = new char[][]{
                { ' ', ' ', ' ', 'q', 'k', ' ', ' ', ' ' },
                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                { ' ', ' ', ' ', 'p', ' ', ' ', ' ', ' ' },
                { ' ', ' ', ' ', ' ', 'p', ' ', ' ', ' ' },
                { ' ', ' ', ' ', 'R', ' ', ' ', ' ', ' ' },
                { ' ', ' ', ' ', ' ', 'p', ' ', ' ', ' ' },
                { ' ', ' ', ' ', 'P', ' ', ' ', ' ', ' ' },
                { ' ', ' ', ' ', ' ', 'K', ' ', ' ', ' ' },

        };

        chessModel.setChessboard(board);
        chessModel.setPiecesList( chessModel.getPiecesFromBoard() );

        chessModel.setSelectedPiece( chessModel.getPiece( 4, 3 ) );
        int moveToRow = 5;
        int moveToColumn = 3;

        boolean isMoveValid = chessModel.isMovePseudoLegal( moveToRow, moveToColumn );

        Assert.assertTrue( isMoveValid );
    }

    @Test
    public void isRookMoveValid2() {

        char[][] board = new char[][]{
                { ' ', ' ', ' ', 'q', 'k', ' ', ' ', ' ' },
                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                { ' ', ' ', ' ', 'p', ' ', ' ', ' ', ' ' },
                { ' ', ' ', ' ', ' ', 'p', ' ', ' ', ' ' },
                { ' ', ' ', ' ', 'R', ' ', ' ', ' ', ' ' },
                { ' ', ' ', ' ', ' ', 'p', ' ', ' ', ' ' },
                { ' ', ' ', ' ', 'P', ' ', ' ', ' ', ' ' },
                { ' ', ' ', ' ', ' ', 'K', ' ', ' ', ' ' },

        };

        chessModel.setChessboard(board);
        chessModel.setPiecesList( chessModel.getPiecesFromBoard() );

        chessModel.setSelectedPiece( chessModel.getPiece( 4, 3 ) );
        int moveToRow = 4;
        int moveToColumn = 0;

        boolean isMoveValid = chessModel.isMovePseudoLegal( moveToRow, moveToColumn );

        Assert.assertTrue( isMoveValid );
    }

    @Test
    public void isRookMoveInvalid() {

        char[][] board = new char[][]{
                { ' ', ' ', ' ', 'q', 'k', ' ', ' ', ' ' },
                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                { ' ', ' ', ' ', 'p', ' ', ' ', ' ', ' ' },
                { ' ', ' ', ' ', ' ', 'p', ' ', ' ', ' ' },
                { ' ', ' ', ' ', 'R', ' ', ' ', ' ', ' ' },
                { ' ', ' ', ' ', ' ', 'p', ' ', ' ', ' ' },
                { ' ', ' ', ' ', 'P', ' ', ' ', ' ', ' ' },
                { ' ', ' ', ' ', ' ', 'K', ' ', ' ', ' ' },
        };

        chessModel.setChessboard(board);
        chessModel.setPiecesList( chessModel.getPiecesFromBoard() );

        chessModel.setSelectedPiece( chessModel.getPiece( 4, 3 ) );
        int moveToRow = 6;
        int moveToColumn = 3;

        boolean isMoveValid = chessModel.isMovePseudoLegal( moveToRow, moveToColumn );

        Assert.assertFalse( isMoveValid );
    }

    //TESTING CAPTURES

    @Test
    public void isPawnCaptureMoveValid() {

        char[][] board = new char[][]{
                { 'r', 'n', 'b', 'q', 'k', 'b', 'n', 'r' },
                { 'p', 'p', ' ', 'p', 'p', 'p', 'p', 'p' },
                { ' ', ' ', 'p', ' ', ' ', ' ', ' ', ' ' },
                { ' ', ' ', ' ', 'P', ' ', ' ', ' ', ' ' },
                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                { ' ', ' ', 'p', ' ', ' ', ' ', ' ', ' ' },
                { 'P', 'P', 'P', ' ', 'P', 'P', 'P', 'P' },
                { 'R', 'N', 'B', 'Q', 'K', 'B', 'N', 'R' },

        };

        chessModel.setChessboard(board);
        chessModel.setPiecesList( chessModel.getPiecesFromBoard() );

        chessModel.setSelectedPiece( chessModel.getPiece( 3, 3 ) );
        int moveToRow = 2;
        int moveToColumn = 2;

        boolean isMoveValid = chessModel.isMovePseudoLegal( moveToRow, moveToColumn );

        Assert.assertTrue( isMoveValid );

    }

    @Test
    public void doesPawnCapture() {

        char[][] board = new char[][]{
                { 'r', 'n', 'b', 'q', 'k', 'b', 'n', 'r' },
                { 'p', 'p', ' ', 'p', 'p', 'p', 'p', 'p' },
                { ' ', ' ', 'p', ' ', ' ', ' ', ' ', ' ' },
                { ' ', ' ', ' ', 'P', ' ', ' ', ' ', ' ' },
                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                { 'P', 'P', 'P', ' ', 'P', 'P', 'P', 'P' },
                { 'R', 'N', 'B', 'Q', 'K', 'B', 'N', 'R' },

        };

        chessModel.setChessboard(board);
        chessModel.setPiecesList( chessModel.getPiecesFromBoard() );

        chessModel.setSelectedPiece( chessModel.getPiece( 3, 3 ) );
        int moveToRow = 2;
        int moveToColumn = 2;

        chessModel.updateBoard( moveToRow, moveToColumn );
        if(chessModel.isMovePseudoLegal( moveToRow, moveToColumn )) {
            chessModel.move( chessModel.getSelectedPiece(), moveToRow, moveToColumn, true );
        }

        var chessboard = chessModel.getChessboard();

        char[][] board2 = new char[][]{
                { 'r', 'n', 'b', 'q', 'k', 'b', 'n', 'r' },
                { 'p', 'p', ' ', 'p', 'p', 'p', 'p', 'p' },
                { ' ', ' ', 'P', ' ', ' ', ' ', ' ', ' ' },
                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                { 'P', 'P', 'P', ' ', 'P', 'P', 'P', 'P' },
                { 'R', 'N', 'B', 'Q', 'K', 'B', 'N', 'R' },
        };

        Assert.assertTrue( doBoardsEqual( chessboard, board2 ) );
    }

    @Test
    public void isKnightCaptureMoveValid() {

        char[][] board = new char[][]{
                { 'r', 'n', 'b', 'q', 'k', 'b', 'n', 'r' },
                { 'p', 'p', ' ', 'p', 'p', 'p', 'p', 'p' },
                { ' ', ' ', 'P', ' ', ' ', ' ', ' ', ' ' },
                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                { 'P', 'P', 'P', ' ', 'P', 'P', 'P', 'P' },
                { 'R', 'N', 'B', 'Q', 'K', 'B', 'N', 'R' },

        };

        chessModel.setChessboard(board);
        chessModel.setPiecesList( chessModel.getPiecesFromBoard() );
        chessModel.setWhiteNextTurn( false );
        chessModel.setSelectedPiece( chessModel.getPiece( 0, 1 ) );

        int moveToRow = 2;
        int moveToColumn = 2;

        boolean isMoveValid = chessModel.isMovePseudoLegal( moveToRow, moveToColumn );

        Assert.assertTrue( isMoveValid );
    }

    @Test
    public void doesKnighCapture() {
        char[][] board = new char[][]{
                { 'r', 'n', 'b', 'q', 'k', 'b', 'n', 'r' },
                { 'p', 'p', ' ', 'p', 'p', 'p', 'p', 'p' },
                { ' ', ' ', 'P', ' ', ' ', ' ', ' ', ' ' },
                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                { 'P', 'P', 'P', ' ', 'P', 'P', 'P', 'P' },
                { 'R', 'N', 'B', 'Q', 'K', 'B', 'N', 'R' },

        };

        chessModel.setChessboard(board);
        chessModel.setPiecesList( chessModel.getPiecesFromBoard() );
        chessModel.setWhiteNextTurn( false );
        chessModel.setSelectedPiece( chessModel.getPiece( 0, 1 ) );

        int moveToRow = 2;
        int moveToColumn = 2;

        makeMove( moveToRow, moveToColumn );

        var chessboard = chessModel.getChessboard();

        char[][] board2 = new char[][]{
                { 'r', ' ', 'b', 'q', 'k', 'b', 'n', 'r' },
                { 'p', 'p', ' ', 'p', 'p', 'p', 'p', 'p' },
                { ' ', ' ', 'n', ' ', ' ', ' ', ' ', ' ' },
                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                { 'P', 'P', 'P', ' ', 'P', 'P', 'P', 'P' },
                { 'R', 'N', 'B', 'Q', 'K', 'B', 'N', 'R' },
        };

        Assert.assertTrue( doBoardsEqual( chessboard, board2 ) );

    }

    @Test
    public void isBishopCaptureValid() {

            char[][] board = new char[][]{
                    { ' ', ' ', ' ', 'q', 'k', ' ', ' ', ' ' },
                    { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                    { ' ', ' ', ' ', 'p', ' ', ' ', ' ', ' ' },
                    { ' ', ' ', ' ', ' ', 'p', ' ', ' ', ' ' },
                    { ' ', ' ', ' ', 'B', ' ', ' ', ' ', ' ' },
                    { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                    { 'P', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                    { ' ', ' ', ' ', 'Q', 'K', ' ', ' ', ' ' },

            };

            chessModel.setChessboard(board);
            chessModel.setPiecesList( chessModel.getPiecesFromBoard() );
            chessModel.setWhiteNextTurn( true );
            chessModel.setSelectedPiece( chessModel.getPiece( 4, 3 ) );

            int moveToRow = 3;
            int moveToColumn = 4;

            boolean isMoveValid = chessModel.isMovePseudoLegal( moveToRow, moveToColumn );

            Assert.assertTrue( isMoveValid );
    }

    @Test
    public void doesBishopCapture() {

            char[][] board = new char[][]{
                    { ' ', ' ', ' ', 'q', 'k', ' ', ' ', ' ' },
                    { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                    { ' ', ' ', ' ', 'p', ' ', ' ', ' ', ' ' },
                    { ' ', ' ', ' ', ' ', 'p', ' ', ' ', ' ' },
                    { ' ', ' ', ' ', 'B', ' ', ' ', ' ', ' ' },
                    { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                    { 'P', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                    { ' ', ' ', ' ', 'Q', 'K', ' ', ' ', ' ' },

            };

            chessModel.setChessboard(board);
            chessModel.setPiecesList( chessModel.getPiecesFromBoard() );
            chessModel.setWhiteNextTurn( true );
            chessModel.setSelectedPiece( chessModel.getPiece( 4, 3 ) );

            int moveToRow = 3;
            int moveToColumn = 4;

            makeMove( moveToRow, moveToColumn );

            var chessboard = chessModel.getChessboard();

            char[][] board2 = new char[][]{
                    { ' ', ' ', ' ', 'q', 'k', ' ', ' ', ' ' },
                    { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                    { ' ', ' ', ' ', 'p', ' ', ' ', ' ', ' ' },
                    { ' ', ' ', ' ', ' ', 'B', ' ', ' ', ' ' },
                    { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                    { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                    { 'P', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                    { ' ', ' ', ' ', 'Q', 'K', ' ', ' ', ' ' },

            };

            Assert.assertTrue( doBoardsEqual( chessboard, board2 ) );
    }

    @Test
    public void isRookCaptureValid() {

            char[][] board = new char[][]{
                { ' ', ' ', ' ', 'q', 'k', ' ', ' ', ' ' },
                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                { ' ', ' ', ' ', 'p', ' ', ' ', ' ', ' ' },
                { ' ', ' ', ' ', ' ', 'p', ' ', ' ', ' ' },
                { ' ', ' ', ' ', 'R', ' ', ' ', ' ', ' ' },
                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                { 'P', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                { ' ', ' ', ' ', 'Q', 'K', ' ', ' ', ' ' },

            };

            chessModel.setChessboard(board);
            chessModel.setPiecesList( chessModel.getPiecesFromBoard() );
            chessModel.setWhiteNextTurn( true );
            chessModel.setSelectedPiece( chessModel.getPiece( 4, 3 ) );

            int moveToRow = 2;
            int moveToColumn = 3;

            boolean isMoveValid = chessModel.isMovePseudoLegal( moveToRow, moveToColumn );

            Assert.assertTrue( isMoveValid );
    }

    @Test
    public void doesRookCapture() {
        char[][] board = new char[][]{
            { ' ', ' ', ' ', 'q', 'k', ' ', ' ', ' ' },
            { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
            { ' ', ' ', ' ', 'p', ' ', ' ', ' ', ' ' },
            { ' ', ' ', ' ', ' ', 'p', ' ', ' ', ' ' },
            { ' ', ' ', ' ', 'R', ' ', ' ', ' ', ' ' },
            { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
            { 'P', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
            { ' ', ' ', ' ', 'Q', 'K', ' ', ' ', ' ' },

        };

        chessModel.setChessboard(board);
        chessModel.setPiecesList( chessModel.getPiecesFromBoard() );
        chessModel.setWhiteNextTurn( true );
        chessModel.setSelectedPiece( chessModel.getPiece( 4, 3 ) );

        int moveToRow = 2;
        int moveToColumn = 3;

        makeMove( moveToRow, moveToColumn );

        var chessboard = chessModel.getChessboard();

        char[][] board2 = new char[][]{
                { ' ', ' ', ' ', 'q', 'k', ' ', ' ', ' ' },
                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                { ' ', ' ', ' ', 'R', ' ', ' ', ' ', ' ' },
                { ' ', ' ', ' ', ' ', 'p', ' ', ' ', ' ' },
                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                { 'P', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                { ' ', ' ', ' ', 'Q', 'K', ' ', ' ', ' ' },

        };

        Assert.assertTrue( doBoardsEqual( chessboard, board2 ) );
    }

    @Test
    public void isQuuenCaptureValid() {

            char[][] board = new char[][]{
                    { ' ', ' ', ' ', 'q', 'k', ' ', ' ', ' ' },
                    { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                    { ' ', ' ', ' ', 'p', ' ', ' ', ' ', ' ' },
                    { ' ', ' ', ' ', ' ', 'p', ' ', ' ', ' ' },
                    { ' ', ' ', ' ', 'Q', ' ', ' ', ' ', ' ' },
                    { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                    { 'P', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                    { ' ', ' ', ' ', ' ', 'K', ' ', ' ', ' ' },

            };

            chessModel.setChessboard(board);
            chessModel.setPiecesList( chessModel.getPiecesFromBoard() );
            chessModel.setWhiteNextTurn( true );
            chessModel.setSelectedPiece( chessModel.getPiece( 4, 3 ) );

            int moveToRow = 2;
            int moveToColumn = 3;

            boolean isMoveValid = chessModel.isMovePseudoLegal( moveToRow, moveToColumn );

            Assert.assertTrue( isMoveValid );
    }

    @Test
    public void doesQueenCapture() {
        char[][] board = new char[][]{
            { ' ', ' ', ' ', 'q', 'k', ' ', ' ', ' ' },
            { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
            { ' ', ' ', ' ', 'p', ' ', ' ', ' ', ' ' },
            { ' ', ' ', ' ', ' ', 'p', ' ', ' ', ' ' },
            { ' ', ' ', ' ', 'Q', ' ', ' ', ' ', ' ' },
            { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
            { 'P', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
            { ' ', ' ', ' ', ' ', 'K', ' ', ' ', ' ' },

        };

        chessModel.setChessboard(board);
        chessModel.setPiecesList( chessModel.getPiecesFromBoard() );
        chessModel.setWhiteNextTurn( true );
        chessModel.setSelectedPiece( chessModel.getPiece( 4, 3 ) );

        int moveToRow = 2;
        int moveToColumn = 3;

        makeMove( moveToRow, moveToColumn );

        var chessboard = chessModel.getChessboard();

        char[][] board2 = new char[][]{
                { ' ', ' ', ' ', 'q', 'k', ' ', ' ', ' ' },
                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                { ' ', ' ', ' ', 'Q', ' ', ' ', ' ', ' ' },
                { ' ', ' ', ' ', ' ', 'p', ' ', ' ', ' ' },
                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                { 'P', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                { ' ', ' ', ' ', ' ', 'K', ' ', ' ', ' ' },

        };

        Assert.assertTrue( doBoardsEqual( chessboard, board2 ) );
    }

    @Test
    public void isKingCaptureValid() {

        char[][] board = new char[][]{
                { ' ', ' ', ' ', 'q', 'k', ' ', ' ', ' ' },
                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                { ' ', ' ', ' ', ' ', 'p', ' ', ' ', ' ' },
                { ' ', ' ', ' ', ' ', 'K', ' ', ' ', ' ' },
                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                { 'P', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },

        };

        chessModel.setChessboard(board);
        chessModel.setPiecesList( chessModel.getPiecesFromBoard() );
        chessModel.setWhiteNextTurn( true );
        chessModel.setSelectedPiece( chessModel.getPiece( 4, 4 ) );

        int moveToRow = 3;
        int moveToColumn = 4;

        boolean isMoveValid = chessModel.isMovePseudoLegal( moveToRow, moveToColumn );

        Assert.assertTrue( isMoveValid );
    }

    @Test
    public void doesKingCapture() {
        char[][] board = new char[][]{
            { ' ', ' ', ' ', 'q', 'k', ' ', ' ', ' ' },
            { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
            { ' ', ' ', ' ', ' ', ' ', ' ', 'P', ' ' },
            { ' ', ' ', ' ', ' ', 'p', ' ', ' ', ' ' },
            { ' ', ' ', ' ', ' ', 'K', ' ', ' ', ' ' },
            { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
            { 'P', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
            { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },

        };

        chessModel.setChessboard(board);
        chessModel.setPiecesList( chessModel.getPiecesFromBoard() );
        chessModel.setWhiteNextTurn( true );
        chessModel.setSelectedPiece( chessModel.getPiece( 4, 4 ) );

        int moveToRow = 3;
        int moveToColumn = 4;

        makeMove( moveToRow, moveToColumn );

        var chessboard = chessModel.getChessboard();

        char[][] board2 = new char[][]{
                { ' ', ' ', ' ', 'q', 'k', ' ', ' ', ' ' },
                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                { ' ', ' ', ' ', ' ', ' ', ' ', 'P', ' ' },
                { ' ', ' ', ' ', ' ', 'K', ' ', ' ', ' ' },
                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                { 'P', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },

        };

        Assert.assertTrue( doBoardsEqual( chessboard, board2 ) );
    }


    //EN-PASSANT TESTS
    @Test
    public void doesEnPassantWork() {

        char[][] board = new char[][]{
                { 'r', 'n', 'b', 'q', 'k', 'b', 'n', 'r' },
                { 'p', 'p', 'p', ' ', 'p', ' ', 'p', 'p' },
                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                { ' ', ' ', ' ', ' ', 'P', 'p', ' ', ' ' },
                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                { 'P', 'P', 'P', 'P', ' ', 'P', 'P', 'P' },
                { 'R', 'N', 'B', 'Q', 'K', 'B', 'N', 'R' },

        };

        chessModel.setChessboard(board);
        chessModel.setPiecesList( chessModel.getPiecesFromBoard() );
        chessModel.setWhiteNextTurn( true );
        chessModel.setSelectedPiece( chessModel.getPiece( 3, 4 ) );

        int moveToRow = 2;
        int moveToColumn = 5;

        makeMove( moveToRow, moveToColumn );

        char[][] board2 = new char[][]{
                { 'r', 'n', 'b', 'q', 'k', 'b', 'n', 'r' },
                { 'p', 'p', 'p', ' ', 'p', ' ', 'p', 'p' },
                { ' ', ' ', ' ', ' ', ' ', 'P', ' ', ' ' },
                { ' ', ' ', ' ', ' ', ' ', 'p', ' ', ' ' },
                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                { 'P', 'P', 'P', 'P', ' ', 'P', 'P', 'P' },
                { 'R', 'N', 'B', 'Q', 'K', 'B', 'N', 'R' },

        };

        Assert.assertEquals( chessModel.getChessboard(), board2 );
    }

    @Test
    public void doesPawnPromotionWork() {

        char[][] board = new char[][]{
                { ' ', ' ', ' ', 'q', 'k', ' ', ' ', ' ' },
                { 'P', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                { ' ', ' ', ' ', ' ', 'p', ' ', ' ', ' ' },
                { ' ', ' ', ' ', ' ', 'K', ' ', ' ', ' ' },
                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                { ' ', 'P', 'P', 'P', ' ', 'P', 'P', 'P' },
                { ' ', ' ', ' ', 'Q', ' ', ' ', ' ', ' ' },

        };

        chessModel.setChessboard(board);
        chessModel.setPiecesList( chessModel.getPiecesFromBoard() );
        chessModel.setWhiteNextTurn( true );
        chessModel.setSelectedPiece( chessModel.getPiece( 1, 0 ) );

        int moveToRow = 0;
        int moveToColumn = 0;


        if(chessModel.isMovePseudoLegal( moveToRow, moveToColumn )) {
            chessModel.move( chessModel.getSelectedPiece(), moveToRow, moveToColumn, true );
            chessModel.updateBoard( moveToRow, moveToColumn );
        }

        chessModel.printGameState();

        char[][] board2 = new char[][]{
                { 'Q', ' ', ' ', 'q', 'k', ' ', ' ', ' ' },
                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                { ' ', ' ', ' ', ' ', 'p', ' ', ' ', ' ' },
                { ' ', ' ', ' ', ' ', 'K', ' ', ' ', ' ' },
                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                { ' ', 'P', 'P', 'P', ' ', 'P', 'P', 'P' },
                { ' ', ' ', ' ', 'Q', ' ', ' ', ' ', ' ' },

        };

        Assert.assertEquals( chessModel.getChessboard(), board2 );
    }

}
