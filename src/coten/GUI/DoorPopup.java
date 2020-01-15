package coten.GUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public abstract class DoorPopup extends Stage {
    private Controller controller;
    private int doorID = 0;
    private Label door;
    private Label doorDescription;
    private BorderPane borderPane;
    /**
     * Empty so it can be extended.
     */
    public DoorPopup() {

    }

    /**
     * Main constructor.
     * @param mainStage The stage that called this.
     * @param c The controller to affect the GUI.
     */
    public DoorPopup(final Stage mainStage, final Controller c) {
        this.controller = c;
        this.initOwner(mainStage);
        this.setTitle("Door Popup");
        initialize();
    }

    private void initialize() {
        this.initModality(Modality.APPLICATION_MODAL);

        borderPane = new BorderPane();
        door = new Label("Door #" + doorID);
        doorDescription = new Label("Door Description");
        borderPane.setTop(door);
        borderPane.setCenter(doorDescription);

        Scene scene = new Scene(borderPane, 300, 300);
        this.setScene(scene);
    }

    /**
     * Sets the button at the bottom for going to chamber or passage.
     * @param button The button to be added.
     */
    public void setButton(Button button) {
        HBox bottom = new HBox();
        bottom.setPadding(new Insets(5, 5, 5, 5));
        bottom.setAlignment(Pos.CENTER);
        bottom.getChildren().add(button);
        borderPane.setBottom(bottom);
    }

    /**
     * Sets the id for the specific door and updates the title.
     * @param id The id of the door.
     */
    public void setDoorId(int id) {
        this.doorID = id;
        setDoorTitle("Door #" + (doorID + 1));
    }

    /**
     * Sets the title for the door popup.
     * @param title The title to be set.
     */
    public void setDoorTitle(String title) {
        door.setText(title);
    }

    /**
     * Sets the description area for the specific door.
     * @param desc The description to be set.
     */
    public void setDoorDescription(String desc) {
        doorDescription.setText("");
        doorDescription.setText(desc);
    }

}
