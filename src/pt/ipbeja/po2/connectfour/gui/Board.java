package pt.ipbeja.po2.connectfour.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import pt.ipbeja.po2.connectfour.View;
import pt.ipbeja.po2.connectfour.model.Cell;
import pt.ipbeja.po2.connectfour.model.ConnectFourModel;

/**
 * @author André Dâmaso
 * @number 15307
 */

public class Board extends GridPane implements View{

    private final ConnectFourModel GAME_MODEL;
    private CellButton[][] buttons;

    public Board(){
        this.GAME_MODEL = new ConnectFourModel(this);
        this.createBoard();
    }

    private void createBoard() {

        CellButtonHandler handler = new CellButtonHandler();
        this.buttons = new CellButton[this.GAME_MODEL.LINES][this.GAME_MODEL.COLS];

        for (int i = 0; i < this.GAME_MODEL.LINES; i++) {
            for (int j = 0; j < this.GAME_MODEL.COLS; j++) {
                CellButton btn = new CellButton();
                btn.setOnAction(handler);
                this.add(btn, j, i);
                this.buttons[i][j] = btn;

            }
        }
    }


    /**
     * Shows a message stating that the game ended in a draw.
     */
    @Override
    public void draw() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION,
                "Draw!");
        alert.show();
    }



    public void playerWin(int player){
        String s = player % 2 == 0 ? "RED" : "BLUE";
        Alert alert = new Alert(Alert.AlertType.INFORMATION,
                "Player '" + s + "' won with score " + GAME_MODEL.Score() +"!    ");
        alert.show();
    }


    @Override
    public void update(Cell cell, int line, int col) {
        CellButton button = buttons[line][col];

        if (cell == Cell.PLAYER1) {
            button.setRed(); // Se o Place for X, pedimos ao botão para colocar a imagem X (Tic)
            //System.out.print(cell);
        } else {
            button.setBlue(); // Caso contrário, Place será O e pedimos ao botão para colocar a imagem O (Tac)
            //System.out.print(cell);
        }

    }

    class CellButtonHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            CellButton button = (CellButton) event.getSource();

            int line = Board.this.getRowIndex(button);
            int col = Board.this.getColumnIndex(button);


            GAME_MODEL.cellSelected(col); // Se sim, podemos jogar nesta posição
            //System.out.println(line + ", " + col);


            //Cell cell = GAME_MODEL.getCell(line, col); // Obtemos o valor do Place nessa posição (ver enum Place)



        }
    }



}
