package de.odinoxin.aidware.aiddesk;

import javafx.application.Application;
import javafx.stage.Stage;

public class AidDesk extends Application {

    /**
     * Lanches AidDesk
     *
     * @param args Command parameters
     */
    public static void main(String[] args) {
        AidDesk.launch(args);
    }


    /**
     * Starts the Application
     *
     * @param primaryStage The primary stage (unused)
     * @throws Exception May throw an Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        new Login(); //Start Login plugin
    }
}
