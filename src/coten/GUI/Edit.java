package coten.GUI;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public abstract class Edit extends Stage {
    private Text displayText;
    private Controller controller;
    private Text changes;

    /**
     * Basis for the edit panel.
     */
    public Edit() {

    }

    /**
     * Constructor.
     * @param mainStage Stage that called this.
     * @param c GUI controller.
     */
    public Edit(final Stage mainStage, final Controller c) {
        this.initOwner(mainStage);
        this.controller = c;
        initialize();
        this.setTitle("Edit Menu");
    }

    private void initialize() {
        this.initModality(Modality.APPLICATION_MODAL);
        BorderPane bp = new BorderPane();
        Scene scene = new Scene(bp, 500, 300);
        VBox vbxLeft = new VBox();
        vbxLeft.setPadding(new Insets(5, 5, 5, 5));
        vbxLeft.setSpacing(5);
        changes = new Text("Changes: ");
        Button addTreasure = new Button("Add Treasure");
        addTreasure.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                addTreasure();
            }
        });
        Button removeTreasure = new Button("Remove Treasure");
        removeTreasure.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                removeTreasure();
            }
        });
        vbxLeft.getChildren().addAll(addTreasure, removeTreasure);

        VBox vbxCenter = new VBox();
        vbxCenter.setPadding(new Insets(5, 5, 5, 5));
        vbxCenter.setSpacing(5);
        Button addMonster = new Button("Add Monster");
        addMonster.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                addMonster();
            }
        });
        Button removeMonster = new Button("Remove Monster");
        removeMonster.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                removeMonster();
            }
        });
        vbxCenter.getChildren().addAll(addMonster, removeMonster);

        Button submit = new Button("Submit Changes");
        submit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                controller.applyChanges();
                clearChanges();
            }
        });

        displayText = new Text();
        bp.setTop(displayText);

        bp.setLeft(vbxLeft);
        bp.setCenter(vbxCenter);
        bp.setRight(changes);

        bp.setBottom(submit);

        this.setScene(scene);
    }

    /**
     * Sets what text should be displayed.
     * @param s String to be displayed.
     */
    public void setDisplayText(String s) {
        displayText.setText(s);
    }

    /**
     * Adds a change to the change menu.
     * @param s Description of Change to be added.
     */
    public void addChange(String s) {
        String change = changes.getText() + "\n" + s;
        changes.setText(change);
    }

    /**
     * Clears out the changes menu.
     */
    private void clearChanges() {
        changes.setText("Changes:");
    }

    /**
     * What to happen when a treasure is added.
     */
    public abstract void addTreasure();

    /**
     * What to happen when treasure is removed.
     */
    public abstract void removeTreasure();

    /**
     * What to happen when a monster is added.
     */
    public abstract void addMonster();

    /**
     * What to happen when a monster is removed.
     */
    public abstract void removeMonster();

}
