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
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
    
    public DrawPane(Window window, ListPane l, PrizePane p) {
        list = l.getList();
        prize = p.getList();
        this.result = FXCollections.observableArrayList();
        this.resultView = new ListView<>(result);
        rand = new Random();
        
        Map<String, String> listMap = new HashMap<>();
        Map<String, String> prizeMap = new HashMap<>();
        
        Button draw = new Button("Draw Next");
        Button save = new Button("Save...");
        Button delete = new Button("Delete");

        HBox menuBar = new HBox();
        menuBar.getChildren().addAll(draw, save, delete);
        
        this.setTop(new Label("Draw"));
        this.setBottom(menuBar);
        this.setCenter(resultView);
        
        draw.setOnAction(event -> {
            if(!prize.isEmpty() && !list.isEmpty()) {
                int s = rand.nextInt(list.size());
                
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Raffle Draw Confirmation");
                alert.setHeaderText(prize.get(0));
                alert.setContentText(list.get(s));
                // alert.getDialogPane().setStyle("-fx-font-size: 60pt;");
                alert.getDialogPane().getStylesheets().add("/styles/Styles.css");
                alert.getDialogPane().getStyleClass().add("drawDialog");
                alert.getDialogPane().setMinWidth(1200);
                
                String drawItem;
                
                Optional<ButtonType> dres = alert.showAndWait();
                if (dres.get() == ButtonType.OK){
                    String pp = prize.remove(0);
                    String ll = list.remove(s);
                    
                    l.refreshCount();
                    p.refreshCount();
                    
                    drawItem = pp + " : " + ll;
                    result.add(drawItem);
                    listMap.put(drawItem, ll);
                    prizeMap.put(drawItem, pp);
                }
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
        
        delete.setOnAction(event -> {
            String selectedItem = resultView.getSelectionModel().getSelectedItem();
            
            if(selectedItem != null) {   
                result.remove(selectedItem);
                list.add(0, listMap.get(selectedItem));
                prize.add(0, prizeMap.get(selectedItem));
            }
        });
        
    }
}
