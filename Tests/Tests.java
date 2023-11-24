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

        chessModel.printGameState();

        chessModel.setSelectedPiece( chessModel.getPiece( 4, 3 ) );
        int moveToRow = 3;
        int moveToColumn = 2;

        boolean isMoveValid = chessModel.isMovePseudoLegal( moveToRow, moveToColumn );

        Assert.assertTrue( isMoveValid );

    }

}
