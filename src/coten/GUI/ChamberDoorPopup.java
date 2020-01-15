package coten.GUI;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ChamberDoorPopup extends DoorPopup {
    private Controller controller;
    private int chamberId = 0;
    private int doorId;
    private MainGUI main;

    /**
     * Main constructor for ChamberDoorPopup.
     * @param mainStage The stage that called this.
     * @param c The controller used by the GUI.
     */
    public ChamberDoorPopup(final Stage mainStage, final Controller c) {
        super(mainStage, c);
        this.controller = c;
        this.setTitle("Chamber Door");
    }

    /**
     * Sets which chamber this should be interacting with.
     * @param id The specific chamber index.
     */
    public void setChamberId(int id) {
        chamberId = id;
        createButton();
    }

    private void createButton() {
        Button select = new Button("Select Passage");
        this.setButton(select);
        select.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                controller.selectPassage(controller.getPassageIDFromDoor(controller.getChamberDoor(chamberId, doorId)));
            }
        });
    }

    /**
     * Sets the id for which door should be used as the description in the popup.
     * @param id The index of the door in the current chamber.
     */
    @Override
    public void setDoorId(int id) {
        super.setDoorId(id);
        this.doorId = id;
        super.setDoorDescription(controller.getChamberDoor(chamberId, doorId).getDescription());
    }

    /**
     * Sets chamber id and door id.
     * @param cID The chamber which to interact with.
     * @param dID The Index of the door which to use.
     */
    public void setIds(int cID, int dID) {
        setChamberId(cID);
        setDoorId(dID);
    }


}
