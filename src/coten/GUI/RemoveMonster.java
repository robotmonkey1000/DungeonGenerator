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

public class RemoveMonster extends Stage {
    private Controller controller;
    private boolean chamber;
    private int id;
    private Change change;
    private Edit parent;
    private ComboBox<String> selection;

    /**
     * Constructor.
     * @param parentStage Edit panel that called this.
     * @param c The controller.
     * @param ch Is this for a chamber or passage.
     */
    public RemoveMonster(final Edit parentStage, final Controller c, boolean ch) {
        this.controller = c;
        this.initOwner(parentStage);
        this.chamber = ch;
        this.parent = parentStage;

        initialize();
        this.setTitle("Remove Monster");
    }

    /**
     * Sets the index or roll for the monster.
     * @param index The index for the monster.
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

        Text title = new Text("Selected the monster to be removed.");
        bp.setTop(title);

        Text response = new Text("");
        bp.setBottom(response);

        bp.setPadding(new Insets(5, 5, 5, 5));

        Button submit = new Button("Remove Monster");
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
        change = new Change(controller, Change.CHANGE_TYPE.REMOVE_MONSTER, space, selection.getSelectionModel().getSelectedIndex());
        controller.addRemoval(change);
        parent.addChange(change.getDescription());
        change.setSpaceID(id);
    }

    /**
     * Updates the box to display all monsters in the chamber or passage.
     */
    public void updateComboBox() {
        selection.getItems().clear();
        if (chamber) {
            for (int i = 0; i < controller.getChamber(id).getMonsters().size(); i++) {
                selection.getItems().add(controller.getChamber(id).getMonsters().get(i).getDescription());
            }
        } else  {
            for (int i = 0; i < controller.getPassage(id).getMonsters().size(); i++) {
                selection.getItems().add(controller.getPassage(id).getMonsters().get(i).getDescription());
            }
        }

    }

    private void successRemove(Text response) {
        response.setFill(Color.BLACK);
        response.setText("Monster set to be removed!");
    }
    private void failedRemove(Text response) {
        response.setFill(Color.RED);
        response.setText("Invalid Entry for monster.");
    }

}
