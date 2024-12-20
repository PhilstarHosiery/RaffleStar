package com.philstar.app.rafflestar;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Hyun Suk Noh <hsnoh@philstar.biz>
 */
public class PrizePane extends BorderPane {
    private final ObservableList<String> list;
    private final Label title;

    public PrizePane(Window window) {
        this.list = FXCollections.observableArrayList();
        ListView<String> listView = new ListView<>(list);

        HBox buttons = new HBox();
        Button listLoad = new Button("Load prize...");
        Button addItem = new Button("Add");
        buttons.getChildren().addAll(listLoad, addItem);

        title = new Label("Prize");
        this.setTop(title);
        this.setBottom(buttons);
        this.setCenter(listView);

        listLoad.setOnAction(_ -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open List File");
            fileChooser.setInitialDirectory(new File("."));
            File selectedFile = fileChooser.showOpenDialog(window);
            if (selectedFile != null) {
                list.clear();

                try (BufferedReader reader = Files.newBufferedReader(selectedFile.toPath())) {
                    while (reader.ready()) {
                        list.add(reader.readLine());
                    }

                    refreshCount();
                } catch (IOException ex) {
                    Logger.getLogger(ListPane.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        addItem.setOnAction(_ -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Additional Item");
            dialog.setHeaderText("Let's add a prize!");
            dialog.setContentText("Prize:");

            // Traditional way to get the response value.
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(list::add);
        });
    }

    public ObservableList<String> getList() {
        return list;
    }

    public void refreshCount() {
        title.setText("Prize - " + list.size());
    }
}
