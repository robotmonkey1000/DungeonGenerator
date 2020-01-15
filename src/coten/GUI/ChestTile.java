package coten.GUI;

import javafx.scene.image.Image;
public class ChestTile extends Tile {
    private int state;
    private static final Image CHEST = new Image("File:res/Sprites/Chest.png", 64, 64, true, false);

    /**
     * Constructor based off of Tile class.
     * @param floorState The state of the floor below (sideways or upright).
     */
    public ChestTile(int floorState) {
        super(floorState);
        this.state = floorState;
        setImage();

    }

    private void setImage() {
        this.setImage((state == 0) ? CHEST : CHEST);
    }

}

