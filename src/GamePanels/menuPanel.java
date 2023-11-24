package GamePanels;

import javax.swing.*;
import java.awt.*;

public class menuPanel extends JPanel {

    GameFrame gameFrame;

    public menuPanel( GameFrame _gameFrame, ChessPanel chessPanel ) {

        this.setName("MenuPanel");
        this.gameFrame = _gameFrame;

        this.setPreferredSize( new Dimension(800, 800) );
        this.setSize( new Dimension(800, 800) );
        this.setBackground( Color.BLUE );

        JButton startGameButton = new JButton("Start game");
        startGameButton.addActionListener( e -> {
            gameFrame.showChessGame();
        } );

        JButton loadGameButton = new JButton("Load");

        loadGameButton.addActionListener( e -> {
            loadGame( chessPanel );
        } );

        int buttonWidth = 200;
        int buttonHeight = 100;

        startGameButton.setBounds( (int)this.getSize().width/ 2 - buttonWidth/2 ,400, buttonWidth, buttonHeight );
        startGameButton.setBackground( Color.RED );

        this.add( startGameButton );
        this.add( loadGameButton );
    }

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
