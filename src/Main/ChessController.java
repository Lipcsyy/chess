package Main;

import GamePanels.GameFrame;
import Pieces.Piece;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ChessController {

    protected ChessView chessView;
    protected ChessModel chessModel;

    protected GameFrame gameFrame;

    public ChessController( GameFrame _gameFrame ) {

        this.gameFrame = _gameFrame;
        this.chessView = new ChessView( this );
        this.chessModel = new ChessModel(this);

        ChessView.boardView.addClickListener( new Input() );
    }

    public void loadGame( String filename ) {

        try ( ObjectInputStream in = new ObjectInputStream( new FileInputStream( filename ) ) ) {

            ChessModel inputChessModel = (ChessModel) in.readObject();

            inputChessModel.controller = this;
            inputChessModel.setPiecesList( new ArrayList<Piece>() );
            inputChessModel.addPieces();
            inputChessModel.checkScanner = new CheckScanner( inputChessModel );

            chessModel = inputChessModel;

            gameFrame.showChessGame();
            ChessView.boardView.repaint();

        } catch (FileNotFoundException e) {



        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void saveGame() {

        LocalDateTime now = LocalDateTime.now();

        try( ObjectOutputStream out = new ObjectOutputStream( new FileOutputStream(now.toString() + ".res")) ) {

            out.writeObject( chessModel );

            out.close();

        } catch ( FileNotFoundException e ) {
            throw new RuntimeException( e );
        } catch ( IOException e ) {
            throw new RuntimeException( e );
        }

    }

    //Getting the user input
    public class Input extends MouseAdapter {

        int oldRow, oldCol;

        @Override
        public void mousePressed( MouseEvent e ) {

            //We need to get the selected piece here

            oldRow = e.getY() / 80;
            oldCol = e.getX() / 80;

            chessModel.setSelectedPiece( chessModel.getPiece( oldRow, oldCol ) );

        }

        @Override
        public void mouseDragged( MouseEvent e ) {
            if ( chessModel.getSelectedPiece() != null ) {

                chessModel.getSelectedPiece().xPos = e.getX() - ChessView.boardView.tileSize / 2;
                chessModel.getSelectedPiece().yPos = e.getY() - ChessView.boardView.tileSize / 2;

                ChessView.boardView.repaint();
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {

            int tileSize = ChessView.boardView.tileSize;

            int col = e.getX() / tileSize;
            int row = e.getY() / tileSize;

            if ( col < 0 || col > 7 || row < 0 || row > 7 ) {
                return;
            }

            if (chessModel.getSelectedPiece() != null) {

                Piece oldPiece = chessModel.getPiece(row, col);

                //oldPiece != null && selectedPiece.isWhite == oldPiece.isWhite

                if ( !chessModel.isMovePseudoLegal( row, col ) ) {
                    chessModel.handleSameColorPiece(chessModel.getSelectedPiece() , oldPiece, oldRow, oldCol);
                }
                else {

                    //if the move is legal we make it
                    chessModel.updateBoard( row, col );

                    if ( chessModel.getSelectedPiece() == null ) {
                        throw new IllegalArgumentException();
                    }

                    chessModel.move(chessModel.getSelectedPiece() , row, col, true);
                    chessModel.setWhiteNextTurn( !chessModel.isWhiteNextTurn() );

                    boolean isDraw = chessModel.isDraw();
                    boolean isCheckmate = chessModel.isCheckMate();

                    System.out.println("DRAW " + isDraw);
                    System.out.println("CHECKMATE " +  isCheckmate);

                }

                chessModel.setSelectedPiece( null );

                ChessView.boardView.repaint();
            }
        }

    }

    //Requesting information from the model
    public Piece getSelectedPiece() {
        return chessModel.getSelectedPiece();
    }

    public ChessBoard getBoard() {
        return chessView.getBoard();
    }

    public List<Piece> getPiecesList() {
        return chessModel.getPiecesList();
    }

    public ChessView getChessView() {
        return chessView;
    }

    public ChessModel getChessModel() {
        return chessModel;
    }
}

