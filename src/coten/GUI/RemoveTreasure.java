package coten.GUI;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class RemoveTreasure extends Stage {
    private Controller controller;
    private boolean chamber;
    private int id;
    private Change change;
    private Edit parent;
    private ComboBox<String> selection;

    /**
     * Constructor.
     * @param parentStage Edit menu that called this.
     * @param c GUI controller.
     * @param ch Is this a chamber or no.
     */
    public RemoveTreasure(final Edit parentStage, final Controller c, boolean ch) {
        this.controller = c;
        this.initOwner(parentStage);
        this.chamber = ch;
        this.parent = parentStage;

        initialize();
        this.setTitle("Remove Monster");
    }

    /**
     * Sets index for treasure.
     * @param index index to be removed.
     */
    public void setId(int index) {
        this.id = index;
    }

    private void initialize() {
        this.initModality(Modality.APPLICATION_MODAL);
        BorderPane bp = new BorderPane();
        bp.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        Scene scene = new Scene(bp, 300, 70);

        HBox center = new HBox();
        center.setSpacing(10);
        selection = new ComboBox<String>();
        //TODO set default message

        center.getChildren().add(selection);

        Text title = new Text("Selected the treasure to be removed.");
        bp.setTop(title);

        Text response = new Text("");
        bp.setBottom(response);

        bp.setPadding(new Insets(5, 5, 5, 5));

        Button submit = new Button("Remove Treasure");
        center.getChildren().add(submit);
        submit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (selection.getSelectionModel().getSelectedIndex() >= 0) {
                    if (chamber) {
                        createChange(Change.SPACE.CHAMBER);
                        successRemove(response);
                    } else {
                        createChange(Change.SPACE.PASSAGE);
                        successRemove(response);
                    }
                } else {
                    failedRemove(response);
                }
            }
        });

        bp.setCenter(center);

        this.setScene(scene);
    }

    private void createChange(Change.SPACE space) {
        change = new Change(controller, Change.CHANGE_TYPE.REMOVE_TREASURE, space, selection.getSelectionModel().getSelectedIndex());
        controller.addRemoval(change);
        parent.addChange(change.getDescription());
        change.setSpaceID(id);
    }

    /**
     * Updates the combobox to show all treasure.
     */
    public void updateComboBox() {
        selection.getItems().clear();
        if (chamber) {
            for (int i = 0; i < controller.getChamber(id).getTreasureList().size(); i++) {
                selection.getItems().add(controller.getChamber(id).getTreasureList().get(i).getDescription());
            }
        } else  {
            for (int i = 0; i < controller.getPassage(id).getTreasure().size(); i++) {
                selection.getItems().add(controller.getPassage(id).getTreasure().get(i).getDescription());
            }
        }

    }

    private void successRemove(Text response) {
        response.setFill(Color.BLACK);
        response.setText("Treasure set to be removed!");
    }
    private void failedRemove(Text response) {
        response.setFill(Color.RED);
        response.setText("Invalid Entry for treasure.");
    }
}
