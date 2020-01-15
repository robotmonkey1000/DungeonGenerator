package coten.GUI;

import javafx.scene.image.Image;
import javafx.stage.Stage;


public class MonsterTile extends Tile {
    private int state;
    private Stage stage;
    private Controller controller;
    private static final Image MONSTER = new Image("File:res/Sprites/Monster.png", 64,  64, true, false);

    /**
     * Constructor.
     * @param floorState State of the floor.
     */
    public MonsterTile(int floorState) {
        super(floorState);
        setImage();

    }

    private void setImage() {
        this.setImage(MONSTER);
    }

}

