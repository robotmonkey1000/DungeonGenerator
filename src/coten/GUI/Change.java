package coten.GUI;

public class Change {
    private Controller controller;
    public static enum CHANGE_TYPE { ADD_MONSTER, ADD_TREASURE, REMOVE_MONSTER, REMOVE_TREASURE }
    public static enum SPACE { CHAMBER, PASSAGE }
    private CHANGE_TYPE type;
    private SPACE space;
    private int id;
    private int spaceID;

    /**
     * Constructor for Change Class. Used to represent different types of changes.
     * @param c The controller used for the main GUI.
     * @param cType Which type of change will happen.
     * @param m_space Which type of space to affect.
     * @param index The index or roll of the thing to affect.
     */
    public Change(Controller c, CHANGE_TYPE cType, SPACE m_space, int index) {
        this.controller = c;
        this.type = cType;
        this.space = m_space;
        this.id = index;
    }

    /**
     * Sets the id for the current space.
     * @param idx The index of the space.
     */
    public void setSpaceID(int idx) {
        this.spaceID = idx;
    }

    /**
     * Returns the description of which change will happen.
     * @return A string representing the description of the current change
     */
    public String getDescription() {
        String desc = "";
        String spaceDesc = (space == SPACE.CHAMBER ? "Chamber" : "Passage");
        switch (type) {
            case ADD_MONSTER:
                desc += "Adding a monster(Roll " + id + ") to current " + spaceDesc;
                break;
            case REMOVE_MONSTER:
                desc += "Removing a monster(Index " + (id + 1) + ") from current " + spaceDesc;
                break;
            case ADD_TREASURE:
                desc += "Adding treasure(Roll " + id + ") to current " + spaceDesc;
                break;
            case REMOVE_TREASURE:
                desc += "Removing treasure(Index " + (id + 1) + ") from current " + spaceDesc;
                break;
            default:
                break;
        }

        return desc;
    }

    /**
     * Used when you want to provoke the change instilled in the current object.
     */
    public void doChange() {
        switch (type) {
            case ADD_MONSTER:
                addMonster();
                break;
            case REMOVE_MONSTER:
                removeMonster();
                break;
            case ADD_TREASURE:
                addTreasure();
                break;
            case REMOVE_TREASURE:
                removeTreasure();
                break;
            default:
                break;
        }
    }

    private void addMonster() {
        switch (space) {
            case CHAMBER:
                controller.addChamberMonster(spaceID, id);
                break;
            case PASSAGE:
                controller.addPassageMonster(spaceID, id);
                break;
            default:
                break;
        }
    }
    private void removeMonster() {
        switch (space) {
            case CHAMBER:
                controller.removeChamberMonster(spaceID, id);
                break;
            case PASSAGE:
                controller.removePassageMonster(spaceID, id);
                break;
            default:
                break;
        }
    }

    /**
     * Returns the id of the current change.
     * @return An int representing the roll of index of the current change.
     */
    public int getId() {
        return id;
    }

    private void addTreasure() {
        switch (space) {
            case CHAMBER:
                controller.addChamberTreasure(spaceID, id);
                break;
            case PASSAGE:
                controller.addPassageTreasure(spaceID, id);
                break;
            default:
                break;
        }
    }
    private void removeTreasure() {
        switch (space) {
            case CHAMBER:
                controller.removeChamberTreasure(spaceID, id);
                break;
            case PASSAGE:
                controller.removePassageTreasure(spaceID, id);
                break;
            default:
                break;
        }
    }
}
