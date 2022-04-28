package com.example.java13_goodspiritswithfx;

import com.example.java13_goodspiritswithfx.universe.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

public class HelloController {
    @FXML
    AnchorPane anchorPainSub;
    @FXML
    Button btnCreatePlanets, btnCreatePaths;
    @FXML
    ComboBox comboBoxListPlanet;
    @FXML
    Label lblResult;
    @FXML
    TextArea txtArea;
    @FXML
    TextField textFieldVerticalSpacing, textFieldPlanetRadius, textFieldHorizontalSpacing;
    @FXML
    VBox vBoxGraphic;
    ArrayList<Tonnel> tonnelList = new ArrayList<>();
    Map<Planet, Circle> mapList = new HashMap<>();
    ObservableList<String> universeList;

    SpacePath bestSpacePath;
    Universe universe;

    double VERTICAL_SPASING;
    double HORIZONTAL_SPACING;
    double PLANET_RADIUS;

    int FONT_SIZE = 15;

    private void initializeComboBoxListPlanet() {
        File dir = new File(getClass().getClassLoader().getResource("inputData").getFile());
        File[] arrFiles = dir.listFiles();
        List<File> lst = Arrays.asList(arrFiles);

        universeList = FXCollections.observableArrayList();
        for (var el : lst) {
            universeList.add(el.getName().split("\\.")[0]);
        }
        comboBoxListPlanet.setItems(universeList);
    }

    private void initializeParameterFields() {
        VERTICAL_SPASING = Double.parseDouble(textFieldVerticalSpacing.getText());
        HORIZONTAL_SPACING = Double.parseDouble(textFieldHorizontalSpacing.getText());
        PLANET_RADIUS = Double.parseDouble(textFieldPlanetRadius.getText());
        vBoxGraphic.setSpacing(VERTICAL_SPASING);

    }

    public void initialize() {
        initializeComboBoxListPlanet();
        initializeParameterFields();
        lblResult.textProperty().addListener((observableValue, s, t1) -> {
            ImageView imageViewIvan = new ImageView(new Image(HelloApplication.class.getResourceAsStream("/images/ivan.png")));
            vBoxGraphic.getChildren().add(imageViewIvan);
            HBox hBoxCurrentLevel;
            for (LevelOfUniverse currentLevel : universe.listLevels) {
                hBoxCurrentLevel = new HBox();
                hBoxCurrentLevel.setAlignment(Pos.CENTER);
                hBoxCurrentLevel.setSpacing(HORIZONTAL_SPACING);
                for (Planet planet : currentLevel.getPlanets()) {
                    Label namePlanet = new Label(planet.toString());
                    namePlanet.setFont(Font.font(FONT_SIZE));
                    namePlanet.setFont(Font.font("Tahoma", FontWeight.BOLD, 15));

                    Circle planetBody = new Circle(PLANET_RADIUS);
                    planetBody.setFill(Color.BLUE);
                    planetBody.setStroke(Color.BLACK);

                    mapList.put(planet, planetBody);

                    VBox vBoxForCurrentPlanet = new VBox();
                    vBoxForCurrentPlanet.setAlignment(Pos.CENTER);
                    vBoxForCurrentPlanet.getChildren().add(planetBody);
                    vBoxForCurrentPlanet.getChildren().add(namePlanet);

                    hBoxCurrentLevel.getChildren().add(vBoxForCurrentPlanet);
                }
                vBoxGraphic.getChildren().add(0, hBoxCurrentLevel);
            }
        });
        txtArea.textProperty().addListener((observableValue, s, t1) -> {
            btnCreatePlanets.setDisable(false);
        });
    }
    
    public void onComboBoxListPlanetClick() {
        clearData();
        int indexOfUniverse = comboBoxListPlanet.getSelectionModel().getSelectedIndex();
        File dir = new File(getClass().getClassLoader().getResource("inputData").getFile());
        File[] arrFiles = dir.listFiles();
        List<File> lst = Arrays.asList(arrFiles);
        String temp = null;
        try {
            temp = Files.readString(lst.get(indexOfUniverse).toPath());
            txtArea.setText(String.valueOf(temp));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void onButtonCreatePlanetsClick() throws InterruptedException {
        initializeParameterFields();
        String text = txtArea.getText();
        universe = new Universe(text);
        universe.getTonnelList(tonnelList);
        bestSpacePath = universe.findBestPath();
        lblResult.setText(String.valueOf(universe.findBestPath()));
        btnCreatePaths.setDisable(false);
        btnCreatePlanets.setDisable(true);
    }

    public void onButtonCreatePathsClick() {
        createPaths();
    }

    public void createPaths() {
        btnCreatePaths.setDisable(true);
        for (Tonnel tonnel : tonnelList) {
            Planet from = tonnel.from;
            Point2D point2DFrom = mapList.get(from).localToScene(mapList.get(from).getLayoutBounds().getCenterX(), mapList.get(from).getLayoutBounds().getCenterY());

            Planet to = tonnel.to;
            Point2D point2DTo = mapList.get(to).localToScene(mapList.get(to).getLayoutBounds().getCenterX(), mapList.get(to).getLayoutBounds().getCenterY());

            Line line = new Line(point2DFrom.getX(), point2DFrom.getY(), point2DTo.getX(), point2DTo.getY());
            Label label = new Label(String.valueOf(tonnel.cost));
            label.setFont(Font.font(FONT_SIZE));
            label.setFont(Font.font("Tahoma", 15));
            label.setTextFill(Color.DARKGREEN);
            label.setLayoutX(((point2DFrom.getX() + point2DTo.getX()) / 2) - PLANET_RADIUS * 2);
            label.setLayoutY(((point2DFrom.getY() + point2DTo.getY()) / 2));

            if (bestSpacePath.steps.contains(tonnel)) {
                line.setStroke(Color.ORANGE);
                line.setStrokeWidth(3);
                label.setTextFill(Color.RED);
                label.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
                label.setLayoutX(((point2DFrom.getX() + point2DTo.getX()) / 2));
            } else
                line.getStrokeDashArray().addAll(20.0);

            anchorPainSub.getChildren().add(line);
            anchorPainSub.getChildren().add(label);
        }
    }

    private void clearData() {
        lblResult.setText("");
        vBoxGraphic.getChildren().clear();
        anchorPainSub.getChildren().clear();
        tonnelList.clear();
        bestSpacePath = new SpacePath();
        txtArea.clear();

    }
}