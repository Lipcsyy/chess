package GamePanels;

import javax.swing.*;
import java.awt.*;

/**
 * The menuPanel class extends JPanel and represents the menu panel in the chess game.
 * It contains a reference to the GameFrame to handle navigation between panels.
 * It also contains buttons to start a new game, load a saved game, and view the leaderboard.
 */
public class menuPanel extends JPanel {

    GameFrame gameFrame;


    /**
     * The constructor for the menuPanel class.
     * It initializes the gameFrame, sets up the size and layout of the panel, and adds the buttons.
     * The "Start game" button navigates to the game start panel.
     * The "Load" button opens a file chooser to load a saved game.
     * The "Leaderboard" button navigates to the leaderboard panel.
     *
     * @param _gameFrame The frame for the chess game
     * @param chessPanel The panel for the chess game
     */
    public menuPanel( GameFrame _gameFrame, ChessPanel chessPanel ) {

        this.setName("MenuPanel");
        this.gameFrame = _gameFrame;

        this.setPreferredSize( new Dimension(800, 800) );
        this.setSize( new Dimension(800, 800) );
        this.setBackground( Color.BLUE );

        JButton startGameButton = new JButton("Start game");
        startGameButton.addActionListener( e -> {
            gameFrame.showGameStart();
        } );

        JButton loadGameButton = new JButton("Load");

        loadGameButton.addActionListener( e -> {
            loadGame( chessPanel );
        } );

        JButton leaderboardButton = new JButton("Leaderboard");
        leaderboardButton.addActionListener( e -> {
            gameFrame.showLeaderboard();
        } );

        int buttonWidth = 200;
        int buttonHeight = 100;

        startGameButton.setBounds( (int)this.getSize().width/ 2 - buttonWidth/2 ,400, buttonWidth, buttonHeight );
        startGameButton.setBackground( Color.RED );

        this.add( startGameButton );
        this.add( leaderboardButton );
        this.add( loadGameButton );
    }


    /**
     * This method is used to load a saved game.
     * It opens a file chooser and lets the user select a file.
     * It then calls the loadGame method of the ChessController with the selected file.
     *
     * @param chessPanel The panel for the chess game
     */
    private void loadGame( ChessPanel chessPanel ) {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showOpenDialog(this);
        String filename = null;
        if (option == JFileChooser.APPROVE_OPTION) {
            filename = fileChooser.getSelectedFile().getAbsolutePath();
            System.out.println(filename);
        }

        chessPanel.getController().loadGame( filename );

    }

}