package com.example.java13_goodspiritswithfx;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        URL location = getClass().getResource("hello-view.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        Parent root = (Parent) fxmlLoader.load(location.openStream());
        HelloController controller = fxmlLoader.getController();
        ComboBox universeListComboBox = controller.comboBoxList;

        initializeComboBox(universeListComboBox);

        Scene scene = new Scene(root);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    private void initializeComboBox(ComboBox universeListComboBox) {
        File dir = new File(getClass().getClassLoader().getResource("inputData").getFile());
        File[] arrFiles = dir.listFiles();
        List<File> lst = Arrays.asList(arrFiles);

        ObservableList<String> universeList = FXCollections.observableArrayList();
        for (var el : lst) {
            universeList.add(el.getName().split("\\.")[0]);
        }
        universeListComboBox.setItems(universeList);
    }

    public static void main(String[] args) {
        launch();
    }
}