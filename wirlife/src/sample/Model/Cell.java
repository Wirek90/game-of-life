package sample.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Observable;

public class Cell extends Observable {
    private int colIdx;
    private int rowIdx;
    private int cellIdx;
    public static int idx;


    private boolean isAlive;

    public Cell(int rowIdx, int colIdx) {
        idx++;
        this.colIdx = colIdx;
        this.rowIdx = rowIdx;
        this.isAlive = false;
        this.cellIdx = idx;
    }


    public boolean isAlive() {
        return isAlive;
    }


    public void toggleIsAlive() {
        this.isAlive = !isAlive;
        setChanged();
        // trigger notification
        notifyObservers();
        clearChanged();
    }


    public int getColIdx() {
        return colIdx;
    }

    public int getRowIdx() {
        return rowIdx;
    }


    //KONS: WTF?
    public ArrayList<Cell> getNeighbours(Cell[][] cellGrid) {
        ArrayList <Cell> neighboursOfCell = new ArrayList();
        int n = cellGrid.length;

//     nie dziala!! :(
//        for (int r = rowIdx - 1; r <= rowIdx + 1; r++) {
//            for (int c = colIdx - 1; c <= colIdx + 1; c++ ) {
//                if ((c >= 0 && c < n) && (r >= 0 && r < n)) {
//                    neighboursOfCell.add(cellGrid[r][c]);
//                }
//            }
//        }
//
//        return neighboursOfCell;
//
//
//    }
        //dodaj sasiadow komorki


        if (rowIdx > 0 && rowIdx < (n - 1) && colIdx > 0 && colIdx < (n - 1)) {
            neighboursOfCell.add(cellGrid[rowIdx - 1][colIdx - 1]);
            neighboursOfCell.add(cellGrid[rowIdx - 1][colIdx]);
            neighboursOfCell.add(cellGrid[rowIdx - 1][colIdx + 1]);

            neighboursOfCell.add(cellGrid[rowIdx][colIdx + 1]);
            neighboursOfCell.add(cellGrid[rowIdx][colIdx - 1]);

            neighboursOfCell.add(cellGrid[rowIdx + 1][colIdx - 1]);
            neighboursOfCell.add(cellGrid[rowIdx + 1][colIdx]);
            neighboursOfCell.add(cellGrid[rowIdx + 1][colIdx + 1]);
        }

        //komorka z gornego rzedu
        else if (rowIdx == 0 && colIdx > 0 && colIdx < (n - 1)) {
            neighboursOfCell.add(cellGrid[rowIdx][colIdx + 1]);
            neighboursOfCell.add(cellGrid[rowIdx][colIdx - 1]);

            neighboursOfCell.add(cellGrid[rowIdx + 1][colIdx - 1]);
            neighboursOfCell.add(cellGrid[rowIdx + 1][colIdx]);
            neighboursOfCell.add(cellGrid[rowIdx + 1][colIdx + 1]);
        }

        //komorka z dolnego rzedu
        else if (rowIdx == (n - 1) && colIdx > 0 && colIdx < (n - 1)) {
            neighboursOfCell.add(cellGrid[rowIdx][colIdx + 1]);
            neighboursOfCell.add(cellGrid[rowIdx][colIdx - 1]);

            neighboursOfCell.add(cellGrid[rowIdx - 1][colIdx - 1]);
            neighboursOfCell.add(cellGrid[rowIdx - 1][colIdx]);
            neighboursOfCell.add(cellGrid[rowIdx - 1][colIdx + 1]);
        }

        //komorka z lewej strony
        else if (colIdx == 0 && rowIdx > 0 && rowIdx < (n - 1)) {

            neighboursOfCell.add(cellGrid[rowIdx][colIdx + 1]);

            neighboursOfCell.add(cellGrid[rowIdx - 1][colIdx]);
            neighboursOfCell.add(cellGrid[rowIdx - 1][colIdx + 1]);

            neighboursOfCell.add(cellGrid[rowIdx + 1][colIdx + 1]);
            neighboursOfCell.add(cellGrid[rowIdx + 1][colIdx]);
        }


        //komorka z prawej strony
        else if (colIdx == (n - 1) && rowIdx > 0 && rowIdx < (n - 1)) {
            neighboursOfCell.add(cellGrid[rowIdx][colIdx - 1]);

            neighboursOfCell.add(cellGrid[rowIdx - 1][colIdx]);
            neighboursOfCell.add(cellGrid[rowIdx - 1][colIdx - 1]);

            neighboursOfCell.add(cellGrid[rowIdx + 1][colIdx]);
            neighboursOfCell.add(cellGrid[rowIdx + 1][colIdx - 1]);
        }


        //lewy gorny rog
        else if (colIdx == 0 && rowIdx == 0) {
            neighboursOfCell.add(cellGrid[rowIdx][colIdx + 1]);
            neighboursOfCell.add(cellGrid[rowIdx + 1][colIdx]);
            neighboursOfCell.add(cellGrid[rowIdx + 1][colIdx + 1]);
        }

        //prawy gorny rog
        else if (colIdx == n - 1 && rowIdx == 0) {
            neighboursOfCell.add(cellGrid[rowIdx][colIdx - 1]);
            neighboursOfCell.add(cellGrid[rowIdx + 1][colIdx]);
            neighboursOfCell.add(cellGrid[rowIdx + 1][colIdx - 1]);
        }

        //prawy dolny rog
        else if (colIdx == n - 1 && rowIdx == n - 1) {
            neighboursOfCell.add(cellGrid[rowIdx][colIdx - 1]);
            neighboursOfCell.add(cellGrid[rowIdx - 1][colIdx]);
            neighboursOfCell.add(cellGrid[rowIdx - 1][colIdx - 1]);
        }

        //lewy dolny rog
        else if (colIdx == 0 && rowIdx == n - 1) {
            neighboursOfCell.add(cellGrid[rowIdx][colIdx + 1]);
            neighboursOfCell.add(cellGrid[rowIdx - 1][colIdx]);
            neighboursOfCell.add(cellGrid[rowIdx - 1][colIdx + 1]);
        }

        return neighboursOfCell;


    }


    public int getNumOfAliveNeighbours(Cell[][] cellGrid) {
        ArrayList<Cell> neighbours = this.getNeighbours(cellGrid);
        int sum = 0;
        for (Cell c : neighbours) {
            if (c.isAlive()) sum++;
        }

        return sum;

    }



}
