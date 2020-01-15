package coten.GUI;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AddMonster extends Stage {
    private Controller controller;
    private boolean chamber;
    private int id;
    private Change change;
    private Edit parent;
    private TextField textField;
    private ComboBox<String> selection;

    /**
     * Main constructor.
     * @param parentStage The stage that called this popup.
     * @param c The controller so it can communicate with the gui.
     * @param ch Whether or not this is for chamber or passage.
     */
    public AddMonster(final Edit parentStage, final Controller c, boolean ch) {
        this.controller = c;
        this.initOwner(parentStage);
        this.chamber = ch;
        this.parent = parentStage;

        initialize();
        this.setTitle("Add Monster");
    }

    /**
     * Sets the roll for the monster to be added.
     * @param index The specfic roll to be used to the monster.
     */
    public void setId(int index) {
        this.id = index;
    }

    private void initialize() {
        this.initModality(Modality.APPLICATION_MODAL);
        BorderPane bp = new BorderPane();
        bp.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        Scene scene = new Scene(bp, 310, 70);
        HBox center = new HBox();
        center.setSpacing(10);
        selection = new ComboBox<String>();
        setUpMonsters();
        //textField = new TextField();
        //textField.setText("1");

        //center.getChildren().add(textField);
        center.getChildren().add(selection);

        Text title = new Text("Pick your monster.");
        bp.setTop(title);

        Text response = new Text("");
        bp.setBottom(response);

        bp.setPadding(new Insets(5, 5, 5, 5));

        Button submit = new Button("Add Monster");
        center.getChildren().add(submit);
        submit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (selection.getSelectionModel().getSelectedIndex() > -1) {

                    if (chamber) {
                        //controller.addChamberMonster(id, Integer.parseInt(textField.getText()));
                        createChange(Change.SPACE.CHAMBER);

                    } else {
                        createChange(Change.SPACE.PASSAGE);
                        //controller.addPassageMonster(id, Integer.parseInt(textField.getText()));
                    }
                    successResponse(response);
                } else {
                    failedResponse(response);
                }
            }
        });

        bp.setCenter(center);

        this.setScene(scene);
    }

    private void createChange(Change.SPACE space) {
        change = new Change(controller, Change.CHANGE_TYPE.ADD_MONSTER, space, controller.getMonsterRoll(selection.getSelectionModel().getSelectedItem()));
        controller.addAddition(change);
        parent.addChange(change.getDescription());
        change.setSpaceID(id);
    }

    private void successResponse(Text response) {
        response.setFill(Color.BLACK);
        response.setText("Monster set to be added! (Remember to submit!)");
    }

    private void setUpMonsters() {
        for (String key: controller.getMonsters().keySet()) {
            selection.getItems().add(key);
        }
    }

    private void failedResponse(Text response) {
        response.setFill(Color.RED);
        response.setText("Invalid Roll Entry.");
    }

}
