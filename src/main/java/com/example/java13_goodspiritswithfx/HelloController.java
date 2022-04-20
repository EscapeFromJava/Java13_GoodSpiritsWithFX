package com.example.java13_goodspiritswithfx;

import com.example.java13_goodspiritswithfx.universe.Universe;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

public class HelloController {
    @FXML
    TextArea txtArea;
    @FXML
    Label lblResult;
    @FXML
    ComboBox comboBoxList;

    public void onCreateButtonClick() {
        String text = txtArea.getText();
        Universe universe = new Universe(text);
        lblResult.setText(String.valueOf(universe.findBestPath()));
    }

    public void onPlanetListClick() throws IOException {
        int indexOfUniverse = comboBoxList.getSelectionModel().getSelectedIndex();
        File dir = new File(getClass().getClassLoader().getResource("inputData").getFile());
        File[] arrFiles = dir.listFiles();
        List<File> lst = Arrays.asList(arrFiles);
        var temp = Files.readString(lst.get(indexOfUniverse).toPath());
        txtArea.setText(String.valueOf(temp));
    }
}