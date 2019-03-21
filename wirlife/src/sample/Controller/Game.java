package sample.Controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;
import sample.Model.Cell;
import sample.View.GameView;
import sample.View.GridMaker;
import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;

public class Game  extends Application implements Observer {

    //KONS: inicjalizowac tu czy w konstruktorze? Starcie?
    private int size = 20;
    private double speed = 1;
    private Cell[][] cells;
    private HashSet<Cell> neighboursOfChangedCells = new HashSet<>();
    private HashSet<Cell> toBeChanged = new HashSet<>();

    private Stage stage;
    public Timeline timeline;
    private GameView gameView;


    public void start(Stage stage) {
        this.stage = stage;
        prepareCellsGrid();
        gameView = new GameView(this, stage, getSize());
        gameView.prepareBoard();
        activateTimeline();
    }


    public void restart(double speed, int size) {
        System.out.println("Size before (restart): " + size);
        timeline.stop();
        setSpeed((double) 1 / speed);//nazwy zmiuennych - speed vs delay
        setSize(size);
        System.out.println("Size after (restart): " + size);
        toBeChanged = new HashSet<>();
        neighboursOfChangedCells = new HashSet<>();
        start(stage);
    }


    public void prepareCellsGrid() {
        cells = new Cell[size][size];
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                cells[row][col] = new Cell(row, col);
                cells[row][col].addObserver(this);
            }
        }
    }


    public void update(Observable ob, Object arg) {

        if (ob instanceof Cell) {
            // get cell coordinates
            Cell cell = (Cell) ob;
            int row = cell.getRowIdx();
            int col = cell.getColIdx();
            gameView.toggleCellColor(row, col);
            //add neighbours
            neighboursOfChangedCells.addAll(cell.getNeighbours(cells));
//            neighboursOfChangedCells.add(cell);

        } else if (ob instanceof GridMaker && arg instanceof int[]) {
            if (timeline.getStatus() != Animation.Status.RUNNING) {
                int[] coordinates = (int[]) arg;
                int row = coordinates[0];
                int col = coordinates[1];
                cells[row][col].toggleIsAlive();
            }

        }
    }

    public void activateTimeline() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(speed), ev -> {
            for (Cell c : neighboursOfChangedCells) {
                if (c.isAlive() && (c.getNumOfAliveNeighbours(cells) < 2 || c.getNumOfAliveNeighbours(cells) > 3)) {
                    toBeChanged.add(c);
                } else if (!(c.isAlive()) && c.getNumOfAliveNeighbours(cells) == 3) {
                    toBeChanged.add(c);
                }
            }
            neighboursOfChangedCells.clear();

            for (Cell c : toBeChanged) {
                c.toggleIsAlive();
            }
            toBeChanged.clear();

        }));
        timeline.setCycleCount(Animation.INDEFINITE);
    }


    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }


    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

}