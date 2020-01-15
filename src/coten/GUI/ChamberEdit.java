package coten.GUI;

import javafx.stage.Stage;


public class ChamberEdit extends Edit {
    private int chamberID;
    private Controller controller;
    private AddMonster addMonster;
    private RemoveMonster removeMonster;
    private AddTreasure addTreasure;
    private RemoveTreasure removeTreasure;

    /**
     * Constructor.
     * @param mainStage The stage which called this.
     * @param c The controller the main gui is using.
     */
    public ChamberEdit(final Stage mainStage, final Controller c) {
        super(mainStage, c);
        this.controller = c;
        addMonster = new AddMonster((Edit) this, c, true);
        removeMonster = new RemoveMonster((Edit) this, c, true);
        addTreasure = new AddTreasure((Edit) this, c, true);
        removeTreasure = new RemoveTreasure((Edit) this, c, true);
    }

    /**
     * Sets up which chamber should be edited.
     * @param id the index for the current chamber.
     */
    public void setChamber(int id) {
        chamberID = id;
        setDisplayText("Chamber ID<" + chamberID + ">");
        addMonster.setId(chamberID);
        removeMonster.setId(chamberID);
        addTreasure.setId(chamberID);
        removeTreasure.setId(chamberID);
    }

    /**
     * Function used to add Treasure to the current chamber.
     */
    @Override
    public void addTreasure() {
        addTreasure.show();
    }

    /**
     * Function used to remove treasure from the current chamber. It also updates its ComboBox to show all treasure in the current room.
     */
    @Override
    public void removeTreasure() {
        removeTreasure.updateComboBox();
        removeTreasure.show();
    }

    /**
     * Function used to add monsters to the current room.
     */
    @Override
    public void addMonster() {
        addMonster.show();
    }

    /**
     * Function used to remove monsters from the current room. Updates ComboBox to show all current monsters.
     */
    @Override
    public void removeMonster() {
        removeMonster.updateComboBox();
        removeMonster.show();
    }
}
