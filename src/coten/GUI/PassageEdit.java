package coten.GUI;

import javafx.stage.Stage;

public class PassageEdit extends Edit {
    private int passageID;
    private Controller controller;
    private AddMonster addMonster;
    private RemoveMonster removeMonster;
    private AddTreasure addTreasure;
    private RemoveTreasure removeTreasure;

    /**
     * Constructor.
     * @param mainStage The stage that called this.
     * @param c GUI controller.
     */
    public PassageEdit(final Stage mainStage, final Controller c) {
        super(mainStage, c);
        this.controller = c;
        addMonster = new AddMonster((Edit) this, c, false);
        removeMonster = new RemoveMonster((Edit) this, c, false);
        addTreasure = new AddTreasure((Edit) this, c, false);
        removeTreasure = new RemoveTreasure((Edit) this, c, false);
    }

    /**
     * Sets the passage id to be edited.
     * @param id The index of the passage.
     */
    public void setPassgage(int id) {
        passageID = id;
        setDisplayText("Passage");
        addMonster.setId(passageID);
        removeMonster.setId(passageID);
        addTreasure.setId(passageID);
        removeTreasure.setId(passageID);
    }

    /**
     * Shows the add treasure menu when clicked.
     */
    @Override
    public void addTreasure() {
        addTreasure.show();
    }

    /**
     * Shows the remove treasure menu when clicked.
     */
    @Override
    public void removeTreasure() {
        removeTreasure.updateComboBox();
        removeTreasure.show();
    }

    /**
     * shows the add monster menu when clicked.
     */
    @Override
    public void addMonster() {
        addMonster.show();
    }

    /**
     * Shows the remove monster menu when clicked.
     */
    @Override
    public void removeMonster() {
        removeMonster.updateComboBox();
        removeMonster.show();
    }
}
