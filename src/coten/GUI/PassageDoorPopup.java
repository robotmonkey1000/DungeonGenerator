package coten.GUI;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class PassageDoorPopup extends DoorPopup {
    private Controller controller;
    private int passageId = 0;
    private int doorId;

    /**
     * Contructor.
     * @param mainStage the stage that called this.
     * @param c GUI controller.
     */
    public PassageDoorPopup(final Stage mainStage, final Controller c) {
        super(mainStage, c);
        this.controller = c;
        this.setTitle("Passage Door");
    }

    /**
     * Sets the passage by id.
     * @param id The passage index.
     */
    public void setPassageId(int id) {
        passageId = id;
        createButton();
    }
    private void createButton() {
        Button select = new Button("Select Chamber");
        this.setButton(select);
        select.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                controller.selectChamber(controller.getChamberIDFromDoor(controller.getPassageDoor(passageId, doorId)));
            }
        });
    }

    /**
     * Sets the specfic door index.
     * @param id The id of the door.
     */
    @Override
    public void setDoorId(int id) {
        super.setDoorId(id);
        this.doorId = id;
        super.setDoorDescription(controller.getPassageDoor(passageId, doorId).getDescription());
    }

    /**
     * Sets the id of both things.
     * @param cID passage id.
     * @param dID door index.
     */
    public void setIds(int cID, int dID) {
        setPassageId(cID);
        setDoorId(dID);
    }
}
