package sample.View;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Observable;

//KONS: Czy gridMaker jako klasa jest potrzebny...?
public class GridMaker extends Observable {

    Rectangle[][] rectangles;

    int size;

    public GridPane getGridPane(int size) {

        this.size = size;    // number of rectangles in a row / column
        int width = (100 / size) * 5; //rectangle width
        rectangles = new Rectangle[size][size];
        GridPane gridPane = new GridPane();


        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {

                rectangles[row][col] = new Rectangle();
                rectangles[row][col].setWidth(width);
                rectangles[row][col].setHeight(width);
                rectangles[row][col].setFill(Color.LINEN);
                rectangles[row][col].setStroke(Color.BLACK);
                rectangles[row][col].setOnMouseClicked(this::handleButtonAction);

                GridPane.setRowIndex(rectangles[row][col], row);
                GridPane.setColumnIndex(rectangles[row][col], col);
                gridPane.getChildren().add(rectangles[row][col]);

            }
        }
        return gridPane;

    }



    //KONS: czy widok moze byc obserwatorem / dostac GAME i zmieniac
    private void handleButtonAction(MouseEvent event) {
        Node source = (Node) event.getSource();
        Integer colIndex = GridPane.getColumnIndex(source);
        Integer rowIndex = GridPane.getRowIndex(source);
        int[] coordinates = new int[]{rowIndex, colIndex};
        setChanged();
        notifyObservers(coordinates);
    }


    public void toggleCellColor(int row, int col) {
        if (rectangles[row][col].getFill() == Color.LINEN) rectangles[row][col].setFill(Color.BLACK);
        else if (rectangles[row][col].getFill() == Color.BLACK) rectangles[row][col].setFill(Color.LINEN);

    }


}