/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.philstar.app.rafflestar;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Window;

/**          
 *
 * @author Hyun Suk Noh <hsnoh@philstar.biz>
 */
public class DrawPane extends BorderPane {
    private final ObservableList<String> list;
    private final ObservableList<String> prize;
    private ObservableList<String> result;
    private final ListView<String> resultView;
    private Random rand;
    
    public DrawPane(Window window, ObservableList<String> l, ObservableList<String> p) {
        list = l;
        prize = p;
        this.result = FXCollections.observableArrayList();
        this.resultView = new ListView<>(result);
        rand = new Random();
        
        Button draw = new Button("Draw Next");
        Button save = new Button("Save...");

        HBox menuBar = new HBox();
        menuBar.getChildren().addAll(draw, save);
        
        this.setTop(new Label("Draw"));
        this.setBottom(menuBar);
        this.setCenter(resultView);
        
        draw.setOnAction(event -> {
            if(!prize.isEmpty() && !list.isEmpty()) {
                String pp = prize.remove(0);
                String ll = list.remove(rand.nextInt(list.size()));
                result.add(pp + " : " + ll);
            }
        });
        
        save.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Result");
            fileChooser.setInitialDirectory(new File("."));
            fileChooser.setInitialFileName("result " + new SimpleDateFormat("yyyyMMdd HHmmss").format(new Date()) + ".txt");
            File selectedFile = fileChooser.showSaveDialog(window);
            if (selectedFile != null) {
                try (BufferedWriter writer = Files.newBufferedWriter(selectedFile.toPath())) {
                    
                    for(String line : result) {
                        writer.write(line);
                        writer.newLine();
                    }
                } catch (IOException ex) {
                    Logger.getLogger(ListPane.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
    }
}
