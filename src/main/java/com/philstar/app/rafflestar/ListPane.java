/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.philstar.app.rafflestar;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
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
public class ListPane extends BorderPane {
    private ObservableList<String> list;
    private final ListView<String> listView;
    private Label title;
 
    public ListPane(Window window) {
        this.list = FXCollections.observableArrayList();
        this.listView = new ListView<>(list);
        
        HBox buttons = new HBox();
        Button listLoad = new Button("Load list...");
        Button save = new Button("Save...");
        buttons.getChildren().addAll(listLoad, save);
        
        title = new Label("List");
        this.setTop(title);
        this.setBottom(buttons);
        this.setCenter(listView);
        
        listLoad.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open List File");
            fileChooser.setInitialDirectory(new File("."));
            File selectedFile = fileChooser.showOpenDialog(window);
            if (selectedFile != null) {
                list.clear();

                try (BufferedReader reader = Files.newBufferedReader(selectedFile.toPath())) {
                    while(reader.ready()) {
                        list.add(reader.readLine());
                    }
                    
                    refreshCount();
                } catch (IOException ex) {
                    Logger.getLogger(ListPane.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        save.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Current Remaining List");
            fileChooser.setInitialDirectory(new File("."));
            fileChooser.setInitialFileName("list " + new SimpleDateFormat("yyyyMMdd HHmmss").format(new Date()) + ".txt");
            File selectedFile = fileChooser.showSaveDialog(window);
            if (selectedFile != null) {
                try (BufferedWriter writer = Files.newBufferedWriter(selectedFile.toPath())) {
                    
                    for(String line : list) {
                        writer.write(line);
                        writer.newLine();
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
    
    public void refreshCount() {
        title.setText("List - " + list.size());
    }
}
