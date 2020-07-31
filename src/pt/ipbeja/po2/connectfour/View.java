package pt.ipbeja.po2.connectfour;

import pt.ipbeja.po2.connectfour.model.Cell;

/**
 * @author André Dâmaso
 * @number 15307
 */

public interface View {

    void draw();

    void update(Cell cell, int line, int col);


    void playerWin(int player);

}
