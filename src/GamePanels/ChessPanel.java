package GamePanels;

import Main.ChessController;

import javax.swing.*;

public class ChessPanel extends JPanel {

    private ChessController controller;
    private GameFrame gameFrame;

    public ChessPanel( GameFrame _gameFrame ) {

        this.gameFrame = _gameFrame;
        this.setName( "ChessPanel" );
        this.setSize( 800, 800 );

        controller = new ChessController( gameFrame );
        this.add( controller.getChessView() );

    }
    public ChessController getController() {
        return controller;
    }


}
