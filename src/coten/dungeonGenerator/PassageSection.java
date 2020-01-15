package coten.dungeonGenerator;

import dnd.die.D20;
import dnd.models.Monster;

import java.io.Serializable;

/* Represents a 10 ft section of passageway */

public class PassageSection implements Serializable {
    /**
     * A interger to represent the description of the current passage section.
     */
    private int intDesc;
    /**
     * The monster that might exist in the current passage section.
     */
    private Monster monster;
    /**
     * The door that might exist in the passage section.
     */
    private Door door;
    /**
     * The description of the current passage section.
     */
    private String description;
    /**
     * the passage that the passage section belongs to.
     */
    private Passage passage;
    /**
     * A boolean of whether or not the passage should end.
     */
    private boolean endPassage;


    /**
     * Intializes the basic needs for a passage section.
     */
    public PassageSection() {
        //sets up the 10 foot section with default settings
        initPassageSection();
    }

    /**
     * Initializes the basic needs for a passage section and sets the passage that it belongs to.
     * @param mPassage The passage that the passage section belongs to.
     */
    public PassageSection(Passage mPassage) {
        setPassage(mPassage);
        initPassageSection();
    }


    /**
     * A function to initialize a passage sections basic things.
     */
    private void initPassageSection() {
        intDesc = 0;
        endPassage = false;
        monster = null;
        D20 d20 = new D20();
        intDesc = 1;
        description = getDescription();
        processDescription();
    }

    /**
     * A constructor that sets the passage sections information based on a string description passed in.
     * @param mDescription The string representation of the passage section.
     */
    public PassageSection(String mDescription) {
        //sets up a specific passage based on the values sent in from
        //modified table 1
        initPassageSection();
        intDesc = -1;
        this.description = mDescription;
        processIntDesc(this.description);
        processDescription();
    }

    /**
     * Gets the interger representation of the description.
     * @return An int to represent the description roll.
     */
    private int getIntDesc() {
        return intDesc;
    }

    /**
     * Sets the passage that the section is part of.
     * @param mPassage The Passage the section belongs to.
     */
    public void setPassage(Passage mPassage) {
        this.passage = mPassage;
        mPassage.addPassageSection(this);
    }

    /**
     * Creates a door in the current passage section. Then adds it to the passage to be connected to this section.
     * @param open Whether or not it is open
     * @param trapped Whether or not it is trapped
     * @param archway Whether or not it is an archway.
     */
    private void createDoor(boolean open, boolean trapped, boolean archway) {
        door = new Door();
        //door.setArchway(archway);
        //passage.addDoor(door);
        passage.setDoor(door);
    }

    /**
     * Checks to see if the passage should be ended.
     * @return a boolean representation of if the passage should end.
     */
    public boolean isPassageEnded() {
        return endPassage;
    }

    /**
     * A function to take the roll number and process what it should door (Create a door, end the passage, create a monster).
     */
    private void processDescription() {
        //Compressed down from full else if to only needed case (removed empty cases)
        if (intDesc > 2 && intDesc < 6) {
            endPassage = true;
            createDoor(true, false, false);
        } else if (intDesc > 5 && intDesc < 8) {
            createDoor(true, false, true);
        } else if (intDesc > 7 && intDesc < 10) {
            createDoor(true, false, true);
        } else if (intDesc > 13 && intDesc < 17) {
            endPassage = true;
            createDoor(true, false, true);
        } else if (intDesc > 17 && intDesc < 20) {
            endPassage = true;
        } else if (intDesc == 20) {
            monster = new Monster();
        }
    }

    /**
     * Sets the monster in the current passage section.
     * @param theMonster The monster to be set.
     */
    public void setMonster(Monster theMonster) {
        monster = theMonster;
    }

    /**
     * Gets the door that is part of this section.
     * @return A door in this section.
     */
    public Door getDoor() {
        //returns the door that is in the passage section, if there is one
        return door;
    }

    /**
     * Gets the monster currently part of the section.
     * @return A monster currently in this section or null.
     */
    public Monster getMonster() {
        //returns the monster that is in the passage section, if there is one
        if (monster == null) {
            return null;
        }
        return monster;
    }

    /**
     * Adds a door to this passage section.
     * @param d The door to be added.
     */
    public void setDoor(Door d) {
        this.door = d;
    }

    /**
     * Takes the current string based description and gets a numeric representation of it.
     * @param mDescription The description in string form to be processed.
     */
    private void processIntDesc(String mDescription) {
        switch (mDescription) { //TODO possible setup hashmap<String, int> so when I plug in the string it gives me the int;
            case "Passage goes straight for 10ft":
                intDesc = 2;
                break;
            case "Passage ends in a Door to a Chamber":
                intDesc = 5;
                break;
            case "archway (door) to right (main passage continues straight for 10 ft)":
                intDesc = 7;
                break;
            case "archway (door) to left (main passage continues straight for 10 ft)":
                intDesc = 9;
                break;
            case "passage turns to left and continues for 10 ft":
                intDesc = 11;
                break;
            case "passage turns to right and continues for 10 ft":
                intDesc = 13;
                break;
            case "passage ends in archway (door) to chamber":
                intDesc = 16;
                break;
            case "Stairs, (passage continues straight for 10 ft)":
                intDesc = 17;
                break;
            case "Dead End":
                intDesc = 19;
                break;
            case "Wandering Monster (passage continues straight for 10 ft)":
                intDesc = 20;
                break;
            default:
                intDesc = -1;
                break;
        }
    }

    /**
     * Takes a numeric representation of the description and returns and the string version of it.
     * @return A string based on the roll for the section.
     */
    public String getDescription() {
        if (intDesc < 0) {
            return description;
        } else if (intDesc < 3) {
            return "Passage goes straight for 10ft";
        } else if (intDesc < 6) {
            return "Passage ends in a Door to a Chamber";
        } else if (intDesc < 8) {
            return "archway (door) to right (main passage continues straight for 10 ft)";
        } else if (intDesc < 10) {
            return "archway (door) to left (main passage continues straight for 10 ft)";
        } else if (intDesc < 12) {
            return "passage turns to left and continues for 10 ft";
        } else if (intDesc < 14) {
            return "passage turns to right and continues for 10 ft";
        } else if (intDesc < 17) {
            return "passage ends in archway (door) to chamber";
        } else if (intDesc < 18) {
            return "Stairs, (passage continues straight for 10 ft)";
        } else if (intDesc < 20) {
            return "Dead End";
        } else if (intDesc < 21) {
            return "Wandering Monster (passage continues straight for 10 ft)";
        }

        return description;
    }

}
