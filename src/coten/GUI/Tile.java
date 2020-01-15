package coten.GUI;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Tile extends ImageView {
    private int doorCount = 0;
    private int state;
    private static final Image FLOORUP = new Image("File:res/Sprites/FloorUp.png", 64, 64, true, false);
    private static final Image FLOORSIDE = new Image("File:res/Sprites/FloorSide.png", 64, 64, true, false);
    //TODO load other images

    /**
     * Constructor.
     * @param s The state of the tile (Sideways or not).
     */
    public Tile(int s) {
        this.state = s;
        this.setImage((state == 0) ? FLOORUP : FLOORSIDE);
        //TODO set door will set if there is a door (change sprite), then create a popup that will show the doors currently in the tile (click those to open the chamber door popup class)
        //TODO possible addDoor function to make it more modular
    }

    /**
     * Default constructor.
     */
    public Tile() {

    }

}
