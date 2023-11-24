package GamePanels;

import javax.swing.*;
import java.awt.*;

public class pausePanel  extends JPanel {

    GameFrame gameFrame;

    public pausePanel(GameFrame _gameFrame, ChessPanel chessPanel) {

        this.setName("PausePanel");

        gameFrame = _gameFrame;

        this.setPreferredSize( new Dimension(800, 800) );
        this.setSize( new Dimension(800, 800) );


        JButton saveButton = new JButton("Save");

        saveButton.setRequestFocusEnabled( false );

        saveButton.addActionListener( e -> {
            chessPanel.getController().saveGame();
        });


        this.add( saveButton );
    }

}
