package com.philstar.app.rafflestar;

import gradle.GradleProject;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * @author Hyun Suk Noh <hsnoh@philstar.biz>
 */
public class RaffleStar extends Application {

    @Override
    public void start(Stage stage) {
        BorderPane borderPane = new BorderPane();

        ListPane list = new ListPane(stage);
        PrizePane prize = new PrizePane(stage);
        borderPane.setLeft(list);
        borderPane.setRight(prize);
        borderPane.setCenter(new DrawPane(stage, list, prize));

        Scene scene = new Scene(borderPane, 1000, 700);
        scene.getStylesheets().add(
                Objects.requireNonNull(
                        getClass().getClassLoader().getResource("raffle_star_styles.css")).toExternalForm());

        stage.setTitle("RaffleStar " + GradleProject.ApplicationVersion);
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
