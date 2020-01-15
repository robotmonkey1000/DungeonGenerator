package coten.dungeonGenerator;
import dnd.die.D10;
import dnd.die.D6;
import dnd.models.Exit;
import dnd.models.Trap;
import dnd.die.D20;

import java.io.Serializable;
import java.util.ArrayList;

public class Door implements Serializable {
    /**
     * Whether or not the door is open.
     */
    private boolean open;
    /**
     * Whether or not the door is an archway.
     */
    private boolean archway;
    /**
     * Whether or not the door is trapped.
     */
    private boolean trapped;
    /**
     * The trap for the door.
     */
    private Trap trap;

    /**
     * The list of spaces attached to the current door.
     */
    private ArrayList<Space> spaces;
    /**
     * A description of the door.
     */
    private String description;

    private boolean used = false;

    /**
     * Main constructor of a door. Initialized instance variables. Then randomly generates self.
     */
    public Door() {
        //needs to set defaults
        spaces = new ArrayList<Space>();
        generateOptions();
        description = new String();
    }

    /**
     * A secondary Constructor. Used to base door off of a room exit.
     * @param theExit The exit to base the room off of.
     */
    public Door(Exit theExit) {
        //sets up the door based on the Exit from the tables
        spaces = new ArrayList<Space>();
        description = new String();
        description += "Direction: " + theExit.getDirection();
        description += "Location: " + theExit.getLocation();
        generateOptions();
    }

    private void generateTrapped() {
        D20 d20 = new D20();
        int roll = d20.roll();
        if (roll == 20) {
            setTrapped(true);
        }
    }

    private void generateOpen() {
        D6 d6 = new D6();
        int roll = d6.roll();
        if (roll == 6) {
            setOpen(false);
        }
    }
    private void generateArchway() {
        D10 d10 = new D10();
        int roll = d10.roll();
        if (roll == 10) {
            setTrapped(false);
            setOpen(true);
            setArchway(true);
        }
    }

    /**
     * Rolls to generate a bunch of stuff about the door.
     * If the door is open, an arachway, or trapped.
     */
    private void generateOptions() {
        System.out.println("Generating Options!");
        generateTrapped();
        generateOpen();
        generateArchway();
    }

    /**
     * Returns whether or not this door has been used.
     * @return a boolean of whether or not this door is in use
     */
    public boolean getUsed() {
        return used;
    }

    /**
     * Sets whether or not this door is in use.
     * @param flag Whether or not the door is in use
     */
    public void setUsed(boolean flag) {
        used = flag;
    }

    /**
     * A function to set whether or not the door is trapped. Also generates the trap.
     * @param flag Whether or not the door should be trapped.
     */
    public void setTrapped(boolean flag) {
        System.out.println("Setting trapped as: " + flag);
        // true == trapped.  Trap must be rolled if no integer is given
        trapped = flag;
        if (flag) {
            trap = new Trap();
            generateTrap(); // put trap logic into function
        }

    }
    private void generateTrap() {
            D20 d20 = new D20();
            trap.chooseTrap(d20.roll());
    }
    /**
     * This sets the door either open or closed based on flag.
     * If the door is an archway then it cannot be closed.
     * @param flag Whether or not the door is open.
     */
    private void setOpen(boolean flag) {
        System.out.println("Setting open as: " + flag);
        //true == open
        if (archway) {
            open = true;
            return;
        }
        open = flag;

    }

    /**
     * This sets whether or not the door is an archway.
     * @param flag Whether or not the door is an archway.
     */
    private void setArchway(boolean flag) {
        System.out.println("Setting archway as: " + flag);
        //true == is archway
        archway = flag;
        if (flag) {
            setOpen(true);
        }
    }

    /**
     * Returns whether or not the door is trapped.
     * @return boolean of if the door is trapped or not.
     */
    public boolean isTrapped() {
        return trapped;
    }

    /**
     * Returns if the door is open or not.
     * @return if the door is open or not.
     */
    private boolean isOpen() {
        return open;
    }

    /**
     * Returns if the door is an archway or not.
     * @return if the door is an archway or not.
     */
    private boolean isArchway() {
        return archway;
    }

    /**
     * Returns the description of the trap on the door.
     * @return A string that is the description of the trap for the door.
     */
    private String getTrapDescription() {
        return trap.getDescription();
    }

    /**
     * Returns the spaces attached to the current door.
     * @return An array list that contains the two spaces attack to the door.
     */
    public ArrayList<Space> getSpaces() {
        //returns the two spaces that are connected by the door
        return spaces;
    }

    /**
     * Sets the spaces attached to the door.
     * @param spaceOne The first space before the door.
     * @param spaceTwo The space after the door.
     */
    public void setSpaces(Space spaceOne, Space spaceTwo) {
        //identifies the two spaces with the door
        // this method should also call the addDoor method from Space
        spaces.clear();
        if (spaceOne != null) {
            spaces.add(spaceOne);
        }
        if (spaceTwo != null) {
            spaces.add(spaceTwo);
        }
    }

    /**
     * Returns the description of the door.
     * @return A string that describes the door.
     */
    public String getDescription() {
        description = "";
        if (open) {
            description += " The door is open";
        } else {
            description += " The door is closed";
        }

        if (archway) {
            description += " The door is an archway. ";
        }
        if (trapped) {
            description += " The door is trapped!";
            description += " Trap: " + trap.getDescription();
        }

        for (Space s: spaces) {
            if (s instanceof Chamber) {
                description += "\nConnect to Chamber ID<" + ((Chamber) s).getId() + ">";
            }
            if (s instanceof  Passage) {
                description += "\nConnect to Passage ID<" + ((Passage) s).getPassageID() + ">";
            }
        }
        return description;
    }

}
