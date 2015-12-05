/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.philstar.app.rafflestar;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Window;

/**
 *
 * @author Hyun Suk Noh <hsnoh@philstar.biz>
 */
public class ListPane extends BorderPane {
    ObservableList<String> list;
    ListView<String> listView;
 
    public ListPane(Window window) {
        this.list = FXCollections.observableArrayList();
        this.listView = new ListView<>(list);
        
        Button listLoad = new Button("Load list...");
        this.setTop(new Label("List"));
        this.setBottom(listLoad);
        this.setCenter(listView);
        
        listLoad.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open List File");
            File selectedFile = fileChooser.showOpenDialog(window);
            if (selectedFile != null) {
                try (BufferedReader reader = Files.newBufferedReader(selectedFile.toPath())) {
                    while(reader.ready()) {
                        list.add(reader.readLine());
                    }
                    
                } catch (IOException ex) {
                    Logger.getLogger(ListPane.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    public ObservableList<String> getList() {
        return list;
    }
}
