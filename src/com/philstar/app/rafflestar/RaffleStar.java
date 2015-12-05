/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.philstar.app.rafflestar;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * @author Hyun Suk Noh <hsnoh@philstar.biz>
 */
public class RaffleStar extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        // Parent root = FXMLLoader.load(getClass().getResource("/fxml/Scene.fxml"));
        // control = new Control();
        // drawPane = new DrawPane(control);
        BorderPane borderPane = new BorderPane();
        
        ListPane list = new ListPane(stage);
        PrizePane prize = new PrizePane(stage);
        borderPane.setLeft(list);
        borderPane.setRight(prize);
        borderPane.setCenter(new DrawPane(stage, list.getList(), prize.getList()));
        
        Scene scene = new Scene(borderPane, 1000, 800);
        scene.getStylesheets().add("/styles/Styles.css");
        
        stage.setTitle("RaffleStar");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
