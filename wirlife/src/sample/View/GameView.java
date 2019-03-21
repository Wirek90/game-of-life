package sample.View;

import javafx.animation.Animation;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import sample.Controller.Game;

public class GameView {
    Game game;
    Stage stage;
    int size;
    GridMaker gridMaker;

    //View pola
    private int menuItemFontSize = 15;
    private int menuHeadlineSize = 30;
    private int defaultSpeed = 1;


    public GameView(Game game, Stage stage, int size) {
        this.game = game;
        this.stage = stage;
        this.size = size;
    }


    public void prepareBoard() {
        Insets vBoxInsets = new Insets(0, 60, 400, 60);
        Insets hBoxInsets = new Insets(60, 0, 10, 0);

        BorderPane bPane = new BorderPane();

        setBgImage(bPane);

        gridMaker = new GridMaker();
        gridMaker.addObserver(game);
        GridPane group = gridMaker.getGridPane(size);

        //top
        ImageView headLine = getHeadLine();

        //right
        VBox rightVBox = new VBox();
        VBox menuBox = getMenu();
        rightVBox.setPadding(vBoxInsets);
        rightVBox.getChildren().addAll(menuBox);

        //bottom
        HBox bottomHBox = new HBox();
        bottomHBox.setPadding(hBoxInsets);

        //left
        VBox leftVBox = new VBox();
        leftVBox.setPadding(vBoxInsets);

        bPane.setMargin(group,  new Insets(0, 50, 100, 50));
        bPane.setMargin(bottomHBox,  new Insets(50, 50, 50, 50));

        //Setting the top, bottom, center, right and left nodes to the pane
        bPane.setTop(headLine);
        bPane.setBottom(bottomHBox);
        bPane.setLeft(leftVBox);
        bPane.setRight(rightVBox);
        bPane.setCenter(group);

        Scene scene = new Scene(bPane);

        stage.setTitle("Game Of Life");
        stage.setScene(scene);
        stage.show();
    }


    public Label getMenuLabel(String labelText, int size) {
        Label label = new Label(labelText);
        label.setFont(Font.font("Courier New",  FontWeight.BOLD, size));
        label.setTextFill(Color.GHOSTWHITE);
        return label;
    }


    public VBox getMenuBlock() {
        VBox menuBlock = new VBox();
        menuBlock.setPadding(new Insets(10,50, 10, 50));
        menuBlock.setStyle(
                "-fx-border-style: solid inside;" +
                        "-fx-border-width: 5;" +
                        "-fx-border-radius: 5;" +
                        "-fx-border-color: darkblue;");
        return menuBlock;
    }


    ImageView getHeadLine() {
        ImageView hdlineImgView = new ImageView();
        hdlineImgView.setImage(new Image("hdl2.png"));
        hdlineImgView.fitWidthProperty().bind(stage.widthProperty());
        return hdlineImgView;
    }


    public void setBgImage(BorderPane bPane) {
        Image bgImage = new Image("bground.jpg");
        BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, true);
        bPane.setBackground(new Background(new BackgroundImage(bgImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                bSize)));
    }




    public VBox getMenu() {
        //osobne metody do przyciskow

        //create menuBox
        VBox menuBox = new VBox();
        setMenuBg(menuBox);


        //upper part of menu
        VBox upperMenuBox = getMenuBlock();
        final Label mainLabel = getMenuLabel("MENU ", menuHeadlineSize);
        upperMenuBox.getChildren().addAll(mainLabel);
        upperMenuBox.setBackground(new Background(new BackgroundFill(Color.DARKBLUE, CornerRadii.EMPTY, Insets.EMPTY)));

        //middle part of menu
        VBox middleMenuBox = getMenuBlock();

        //upper menu label
        final Label startPauseLabel = getMenuLabel("START / PLAY ", menuItemFontSize);


        //creating pause/start button
        ImageView startBtnImgView = new ImageView(new Image("start.png"));
        ImageView pauseBtnImgView = new ImageView(new Image("pause.png"));
        Button startPauseBtn = new Button();
        startPauseBtn.setStyle(
                "-fx-background-radius: 30em; ");
        startPauseBtn.setGraphic(startBtnImgView);

        //toggle button on click
        startPauseBtn.setOnAction(e -> {
            if (game.timeline.getStatus() == Animation.Status.RUNNING) {
                game.timeline.pause();
                startPauseBtn.setGraphic(startBtnImgView);
            } else {
                startPauseBtn.setGraphic(pauseBtnImgView);
                game.timeline.play();
            }
        });

        middleMenuBox.getChildren().addAll(startPauseLabel, startPauseBtn);


        VBox lowerMenuBox = getMenuBlock();

        int chBoxValue = (int) (1/game.getSpeed());
        int sliderValue = game.getSize();

        Label chBoxLabel = getMenuLabel("CHOOSE SPEED:", menuItemFontSize);
        ChoiceBox chBox = new ChoiceBox(FXCollections.observableArrayList(1, 2, 3, 4, 5));
        chBox.setValue(chBoxValue);

        //KONS: Czy to juz magic numbers?
        Slider sizeSlider = new Slider(2, 50, sliderValue);
        final Label sliderCaption = getMenuLabel("CHOOSE GRID SIZE:", menuItemFontSize);
        final Label sizeSliderValue = getMenuLabel(Integer.toString((int) sizeSlider.getValue()), menuItemFontSize);
        sizeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                sizeSliderValue.setText(String.format("%.0f", new_val));
            }
        });


        //creating confirm slider button
        final Label gridSizeLabel = getMenuLabel("CONFIRM CHANGES:",menuItemFontSize);
        ImageView gridSizeBtnImV = new ImageView(new Image("changeSize.png"));
        Button gridSizeBtn = new Button();

        gridSizeBtn.setOnAction(e -> {
            int chosenSize;
            double chosenSpeed;
            if (chBox.getValue() != null) {
                int chosenSpeed1 = ((int) chBox.getValue());
                chosenSpeed = (double) chosenSpeed1;
            } else {
                chosenSpeed = defaultSpeed;
            }
            chosenSize = (int) sizeSlider.getValue();
            startPauseBtn.setGraphic(startBtnImgView);
            game.restart(chosenSpeed, chosenSize);
            });


        gridSizeBtn.setStyle("-fx-background-radius: 10em; ");
        gridSizeBtn.setGraphic(gridSizeBtnImV);

        lowerMenuBox.getChildren().addAll(chBoxLabel, chBox, sliderCaption, sizeSlider, sizeSliderValue, gridSizeLabel);
        lowerMenuBox.getChildren().addAll(gridSizeBtn);

        //add slider to menuBox:
        menuBox.getChildren().addAll(upperMenuBox);
        menuBox.getChildren().addAll(middleMenuBox);
        menuBox.getChildren().addAll(lowerMenuBox);

        return menuBox;
    }


    void setMenuBg(VBox menuBox) {
        // create background image
        Image menuImg = new Image("menu.png");
        BackgroundSize bMenuSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, true);
        menuBox.setBackground(new Background(new BackgroundImage(menuImg,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                bMenuSize)));
    }


public void toggleCellColor(int row, int col) {
        gridMaker.toggleCellColor(row, col);


}




}
