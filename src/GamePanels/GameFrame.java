package GamePanels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameFrame extends JFrame {

    private CardLayout cardLayout = new CardLayout();
    private JPanel cardPanel;

    public GameFrame() {

        this.setDefaultCloseOperation( EXIT_ON_CLOSE );
        this.setSize( 800, 800 );
        this.setResizable( false );
        this.setLocationRelativeTo( null );
        this.setFocusable(true);
        this.requestFocusInWindow();


        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        ChessPanel chessPanel = new ChessPanel( this );

        cardPanel.add( chessPanel, "Chess Game");
        cardPanel.add( new menuPanel(this, chessPanel), "Menu" );
        cardPanel.add( new pausePanel( this, chessPanel ), "Pause" );

        cardLayout.show( cardPanel, "Menu" );

        this.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed( KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {

                    JPanel currentCard = null;

                    for ( Component comp : cardPanel.getComponents() ) {
                        if ( comp.isVisible() ) {
                            System.out.println("Currently showing " +((JPanel) comp).getName()  );
                            currentCard = (JPanel) comp;
                        }
                    }

                    if ( currentCard.getName() == "ChessPanel" ) {
                        cardLayout.show( cardPanel, "Pause" );
                        return;
                    }

                    if ( currentCard.getName() == "PausePanel") {
                        cardLayout.show( cardPanel, "Chess Game" );
                        return;
                    }

                }
            }

            @Override
            public void keyTyped( KeyEvent e ) {

            }

            @Override
            public void keyReleased( KeyEvent e ) {

            }
        });
        this.add( cardPanel );
        this.pack();
    }

    public void showChessGame() {
        cardLayout.show( cardPanel, "Chess Game" );
    }


}
