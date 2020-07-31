package pt.ipbeja.po2.connectfour.gui;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author André Dâmaso
 * @number 15307
 */

public class CellButton extends Button{

    private static final Image PLAY_EMPTY = new Image("/resources/empty.png");
    private static final Image PLAY_1 = new Image("/resources/red.png");
    private static final Image PLAY_2 = new Image("/resources/blue.png");

    private final ImageView imageView;


    public CellButton() {
        this.imageView = new ImageView(PLAY_EMPTY);
        this.setGraphic(imageView);
    }

    public void setRed(){
        this.imageView.setImage(PLAY_1);
    }

    public void setBlue(){
        this.imageView.setImage(PLAY_2);
    }


}
