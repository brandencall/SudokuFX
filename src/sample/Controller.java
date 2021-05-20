package sample;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.util.Random;

public class Controller {
    @FXML
    private GridPane grid;

    public final int COLUMN = 9;
    public final int ROW = 9;

    public Node getNodeByRowColumnIndex (int row, int column) {
        Node result = null;
        ObservableList<Node> children = grid.getChildren();

        for (Node node : children) {
            if(grid.getRowIndex(node) == row && grid.getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }

        return result;
    }
    public int getNumByRowColumnIndex (int row, int column) {
        String str = ((TextField) getNodeByRowColumnIndex(row, column)).getText();

        return str == null || str.isEmpty() ? 0 : Integer.parseInt(str);

    }

    public boolean isNumInRow(int row, int num){
        for (int c = 0; c < COLUMN; c++){
            if (getNumByRowColumnIndex(row, c) == num){
                return true;
            }
        }
        return false;
    }

    public boolean isNumInCol(int col, int num){
        for (int r = 0; r < ROW; r++){
            if (getNumByRowColumnIndex(r, col) == num){
                return true;
            }
        }
        return false;
    }

    //First have to see what "miniboard" we are working with then
    //check to see if the number is in that "miniboard".
    public boolean isNumInMiniBoard(int row, int col, int num){
        int firstRow;
        int firstCol;
        if(row < 3){
            firstRow = 0;
        }else if(row < 6){
            firstRow = 3;
        }else {
            firstRow = 6;
        }
        if(col < 3){
            firstCol = 0;
        }else if(col < 6){
            firstCol = 3;
        }else{
            firstCol = 6;
        }
        for (int r = 0; r < 3; r++){
            for (int c = 0; c < 3; c++){
                if (getNumByRowColumnIndex(firstRow + r, firstCol + c) == num){
                    return true;
                }
            }
        }
        return false;
    }

    //Returns true if the move is an eligible move.
     boolean isLegal(int row, int col, int num){
        return(!isNumInRow(row, num) &&
                !isNumInCol(col, num) &&
                !isNumInMiniBoard(row, col, num));
    }

    @FXML
    public void generateNumbers(){
        grid.getChildren().clear();
        Random random = new Random();
        for (int r = 0; r < ROW; r++){
            for (int c = 0; c < COLUMN; c++){
                while(getNumByRowColumnIndex(r, c) == 0){
                    int randomNumber = random.nextInt(9) + 1;
                    if (isLegal(r, c, randomNumber)){
                        ((TextField)getNodeByRowColumnIndex(r, c)).setText(Integer.toString(randomNumber));
                        break;
                    }
                }
            }
        }
    }


}
