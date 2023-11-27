package Main;

import GamePanels.GameFrame;

public class Main {

    /**
     * The main method is the entry point of the Java application.
     * It creates an instance of the GameFrame and makes it visible.
     *
     * @param args An array of command-line arguments for the application
     */    public static void main(String[] args) {

        GameFrame gameFrame = new GameFrame();

        gameFrame.setVisible(true);

    }
}
