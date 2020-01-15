package coten.GUI;

import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.ArrayList;

public class DoorTile extends Tile {
    private int state;
    private int doorState;
    private ArrayList<ChamberDoorPopup> cDoorPops;
    private Stage stage;
    private Controller controller;
    private static final Image FLOORUPDOORSIDE = new Image("File:res/Sprites/FloorUpDoorSide.png", 64, 64, true, false);
    private static final Image FLOORSIDEDOORSIDE = new Image("File:res/Sprites/FloorSideDoorSide.png", 64, 64, true, false);

    /**
     * Constructor.
     * @param floorState Direction of floor.
     * @param dState State of door.
     * @param mainStage Main stage.
     * @param c GUI controller.
     */
    public DoorTile(int floorState, int dState, Stage mainStage, Controller c) {
        super(floorState);
        this.state = floorState;
        this.doorState = dState;
        setImage();

        this.stage = mainStage;
        this.controller = c;
    }

    private void setImage() {
        if (state == 0 && doorState == 0) {
            this.setImage(FLOORUPDOORSIDE);
            this.setRotate(180);
        } else if (state == 1 && doorState == 1) {
            this.setImage(FLOORSIDEDOORSIDE);
        } else if (state == 0 && doorState == 1) {
            this.setImage(FLOORUPDOORSIDE);
        } else if (state == 1 && doorState == 0) {
            this.setImage(FLOORSIDEDOORSIDE);
            this.setRotate(180);
        } else {
            this.setImage(FLOORSIDEDOORSIDE);
            this.setRotate(90);
        }
    }

}
