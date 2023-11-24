package Main;

import javax.swing.*;
import java.awt.*;

public class ChessView extends JPanel {

    public static ChessBoard boardView;
    private ChessController controller;

    public int tileSize  = 80;

    public ChessView( ChessController _controller ) {

        this.controller = _controller;

        this.setSize( new Dimension( Globals.SCREEN_WIDTH, Globals.SCREEN_HEIGHT ) );
        this.setMinimumSize( this.getMinimumSize() );

        this.setLayout( new GridLayout());

        Box box = new Box(BoxLayout.Y_AXIS);
        boardView = new ChessBoard( controller );

        box.add(Box.createVerticalGlue());
        box.add(boardView);
        box.add(Box.createVerticalGlue());

        this.add( box );

    }

    public ChessBoard getBoard() {
        return boardView;
    }

}
