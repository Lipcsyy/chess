package Main;

import GamePanels.GameFrame;
import Pieces.Piece;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The ChessController class is responsible for handling user input and managing the game state.
 * It communicates with the ChessModel to update the game state and with the ChessView to update the UI.
 */
public class ChessController {

    protected ChessView chessView;
    protected ChessModel chessModel;

    protected GameFrame gameFrame;


    /**
     * The constructor for the ChessController class.
     * It initializes the chessView and chessModel and adds a click listener to the board view.
     *
     * @param _gameFrame The frame for the chess game
     */
    public ChessController( GameFrame _gameFrame ) {

        this.gameFrame = _gameFrame;
        this.chessView = new ChessView( this );
        this.chessModel = new ChessModel(this);

        ChessView.boardView.addClickListener( new Input() );
    }


    /**
     * This method is used to load a saved game from a file.
     * It deserializes the ChessModel object from the file and updates the chessModel field.
     * It also updates the UI to reflect the loaded game state.
     *
     * @param filename The name of the file from which to load the game
     */
    public void loadGame( String filename ) {

        try ( ObjectInputStream in = new ObjectInputStream( new FileInputStream( filename ) ) ) {

            ChessModel inputChessModel = (ChessModel) in.readObject();

            inputChessModel.controller = this;
            inputChessModel.setPiecesList( new ArrayList<Piece>() );
            inputChessModel.addPieces();
            inputChessModel.checkScanner = new CheckScanner( inputChessModel );

            chessModel = inputChessModel;

            gameFrame.showChessGame("", "");
            ChessView.boardView.repaint();

        } catch (FileNotFoundException e) {



        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    /**
     * This method is used to save the current game state to a file.
     * It serializes the ChessModel object to a file with a name based on the current date and time.
     */
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


    /**
     * This class is used to handle user input.
     * It extends MouseAdapter and overrides the mousePressed, mouseDragged, and mouseReleased methods to handle piece selection and movement.
     */
    public class Input extends MouseAdapter {

        int oldRow, oldCol;

        /**
         * This method is called when the mouse button has been pressed on a component.
         * It gets the selected piece based on the mouse position.
         *
         * @param e The MouseEvent
         */
        @Override
        public void mousePressed( MouseEvent e ) {

            oldRow = e.getY() / 80;
            oldCol = e.getX() / 80;

            chessModel.setSelectedPiece( chessModel.getPiece( oldRow, oldCol ) );

        }

        /**
         * This method is called when a mouse button is pressed on a component and then dragged.
         * It updates the position of the selected piece based on the mouse position.
         *
         * @param e The MouseEvent
         */
        @Override
        public void mouseDragged( MouseEvent e ) {
            if ( chessModel.getSelectedPiece() != null ) {

                chessModel.getSelectedPiece().xPos = e.getX() - ChessView.boardView.tileSize / 2;
                chessModel.getSelectedPiece().yPos = e.getY() - ChessView.boardView.tileSize / 2;

                ChessView.boardView.repaint();
            }
        }

        /**
         * This method is called when a mouse button has been released on a component.
         * It moves the selected piece to the new location if the move is legal.
         * If the move results in a checkmate or draw, it updates the game state accordingly.
         *
         * @param e The MouseEvent
         */
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

                if ( !chessModel.isMovePseudoLegal( row, col ) ) {
                    chessModel.handleSameColorPiece(chessModel.getSelectedPiece() , oldPiece, oldRow, oldCol);
                }
                else {

                    if ( chessModel.getSelectedPiece() == null ) {
                        throw new IllegalArgumentException();
                    }

                    chessModel.move(chessModel.getSelectedPiece() , row, col, true);
                    chessModel.updateBoard( row, col );

                    boolean isDraw = chessModel.isDraw();
                    boolean isCheckmate = chessModel.isCheckMate();

                    if ( isDraw || isCheckmate ) {

                        String playerWhite = chessModel.getPlayerWhite();
                        String playerBlack = chessModel.getPlayerBlack();

                        if ( !( playerWhite.isEmpty() && playerBlack.isEmpty() ) ) {
                            try {
                                Map<String, Double> leaderboard = readLeaderboard();
                                updatePoints(leaderboard, playerWhite, isDraw, isCheckmate, getSelectedPiece().isWhite);
                                updatePoints(leaderboard, playerBlack, isDraw, isCheckmate, getSelectedPiece().isWhite);
                                writeLeaderboard(leaderboard);
                            } catch ( IOException ex ) {
                                throw new RuntimeException( ex );
                            }
                        }

                        gameFrame.showWonPanel( isDraw, isCheckmate, playerWhite, playerBlack, getSelectedPiece().isWhite );

                    }

                    chessModel.setWhiteNextTurn( !chessModel.isWhiteNextTurn() );

                    System.out.println("DRAW " + isDraw);
                    System.out.println("CHECKMATE " +  isCheckmate);

                }

                chessModel.setSelectedPiece( null );

                ChessView.boardView.repaint();
            }
        }

    }


    /**
     * This method is used to get the currently selected piece.
     * It returns the Piece object that is currently selected.
     *
     * @return The currently selected Piece object
     */
    public Piece getSelectedPiece() {
        return chessModel.getSelectedPiece();
    }


    /**
     * This method is used to get the current state of the chessboard.
     * It returns a ChessBoard object representing the current state of the chessboard.
     *
     * @return A ChessBoard object representing the current state of the chessboard
     */
    public ChessBoard getBoard() {
        return chessView.getBoard();
    }


    /**
     * This method is used to get a list of pieces currently on the chessboard.
     * It returns a list of Piece objects, where each Piece represents a chess piece on the board.
     *
     * @return A list of Piece objects representing the chess pieces on the board
     */
    public List<Piece> getPiecesList() {
        return chessModel.getPiecesList();
    }


    /**
     * This method is used to get the ChessView object.
     * It returns the ChessView object that is used to display the game.
     *
     * @return The ChessView object
     */
    public ChessView getChessView() {
        return chessView;
    }


    /**
     * This method is used to get the ChessModel object.
     * It returns the ChessModel object that represents the state of the game.
     *
     * @return The ChessModel object
     */
    public ChessModel getChessModel() {
        return chessModel;
    }


    /**
     * This method is used to set the names of the players.
     * It takes two strings as arguments and sets the playerWhite and playerBlack fields to these strings.
     *
     * @param player1 The name of the player playing as white
     * @param player2 The name of the player playing as black
     */
    public void setPlayers( String player1, String player2 ) {
        chessModel.setPlayers( player1, player2 );
    }


    /**
     * This method is used to read the leaderboard from a file.
     * It creates a new HashMap to store the leaderboard.
     * It then opens the file "leaderboard.txt" and reads each line.
     * Each line is split into two parts: the player's name and their score.
     * The player's name and score are added to the leaderboard HashMap.
     * If the file does not exist, an empty leaderboard is returned.
     *
     * @return A HashMap representing the leaderboard, where the keys are player names and the values are their scores
     * @throws IOException If an I/O error occurs
     */
    private Map<String, Double> readLeaderboard() throws IOException {
        Map<String, Double> leaderboard = new HashMap<>();
        File file = new File("leaderboard.txt");

        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    leaderboard.put(parts[0], Double.parseDouble(parts[1]));
                }
            }
        }

        return leaderboard;
    }


    /**
     * This method is used to write the leaderboard to a file.
     * It first sorts the entries in the leaderboard by score in descending order.
     * It then opens the file "leaderboard.txt" and writes each entry on a new line.
     * Each line consists of the player's name and their score, separated by a comma.
     *
     * @param leaderboard A HashMap representing the leaderboard, where the keys are player names and the values are their scores
     * @throws IOException If an I/O error occurs
     */
    private void writeLeaderboard(Map<String, Double> leaderboard) throws IOException {
        List<Map.Entry<String, Double>> sortedEntries = leaderboard.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .collect( Collectors.toList());

        // Write the sorted entries to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("leaderboard.txt"))) {
            for (Map.Entry<String, Double> entry : sortedEntries) {
                writer.write(entry.getKey() + "," + entry.getValue() + "\n");
            }
        }
    }


    /**
     * This method is used to update the points of a player in the leaderboard.
     * It first calculates the points to be added based on the game result.
     * If the player won, 1 point is added.
     * If the game was a draw, 0.5 points are added.
     * If the player lost, no points are added.
     * The player's score in the leaderboard is then updated by adding the calculated points.
     *
     * @param leaderboard A HashMap representing the leaderboard, where the keys are player names and the values are their scores
     * @param playerName The name of the player whose points are to be updated
     * @param isDraw A boolean indicating whether the game was a draw
     * @param isCheckmate A boolean indicating whether the game ended in checkmate
     * @param lastMoveWhite A boolean indicating whether the last move was made by the white player
     */
    private void updatePoints(Map<String, Double> leaderboard, String playerName, boolean isDraw, boolean isCheckmate, boolean lastMoveWhite) {
        double points = 0;

        String whitePlayersName = chessModel.getPlayerWhite();
        String blackPlayersName = chessModel.getPlayerBlack();

        //if there is checkmate and the last moved piece's color e
        if (isCheckmate && lastMoveWhite == (playerName.equals(whitePlayersName)) ) {
            points = 1;
        }else if (isCheckmate && !lastMoveWhite == (playerName.equals(blackPlayersName))) {
            points = 1;
        }
        else if (isDraw) {
            points = 0.5;
        }

        leaderboard.put(playerName, leaderboard.getOrDefault(playerName, 0.0) + points);
    }
}

