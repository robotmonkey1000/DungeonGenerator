package coten.dungeonGenerator;

import dnd.die.D20;
import dnd.exceptions.NotProtectedException;
import dnd.models.ChamberContents;
import dnd.models.ChamberShape;
import dnd.models.Monster;
import dnd.models.Trap;
import dnd.models.Treasure;

import java.io.Serializable;
import java.util.ArrayList;

public class Chamber extends Space implements Serializable {
    /**
     * Contains chamber contents.
     */
    private ChamberContents myContents;
    /**
     * Contains Chamber shape and size.
     */
    private ChamberShape mySize;
    /**
     * A list of all doors in the chamber.
     */
    private ArrayList<Door> doors;
    /**
     * A list of all Monsters in the current Chamber.
     */
    private ArrayList<Monster> monsters;
    /**
     * A list of all treasure.
     */
    private ArrayList<Treasure> treasures;
    /**
     * the Level that the current chamber belongs to.
     */
    private Level level;

    /**
     * The description of the current chamber.
     */
    private String description;

    /**
     * A monster that will be used when generating the contents of the room.
     */
    private Monster monster;
    /**
     * A treasure to be used when generating the contents of the room.
     */
    private Treasure treasure;
    /**
     * The trap that might be in the room.
     */
    private Trap trap;

    private int id;





    /**
     * Main constructor. Used to initialize the needed lists and such.
     * This one randomly creates the contents and shape of the chamber.
     * @param mId What the id of the chamber is
     */
    public Chamber(int mId) {
        //Added init lists so that recurring code from both constructors was written in one function
        initLists();
        myContents = new ChamberContents();
        int roll;
        roll = (int) (Math.random() * ((17 - 1) + 1)) + 1;
        while (roll == 9 || roll == 10) {
            roll = (int) (Math.random() * ((17 - 1) + 1)) + 1;
        }
        System.out.println("Room id: " + id + " has roll " + (roll));
        mySize = ChamberShape.selectChamberShape(roll);
        generateContents();
        this.id = mId;
    }

    /**
     * A constructor to create the chamber. Used when wanting to pass in already created shape and contents
     * @param theShape A predefined shape.
     * @param theContents A predefined Content.
     */
    public Chamber(ChamberShape theShape, ChamberContents theContents) {
        initLists();
        setShape(theShape);
        setContents(theContents);
        generateContents();
    }

    private void initLists() {
        treasures = new ArrayList<Treasure>();
        monsters = new ArrayList<Monster>();
        doors = new ArrayList<Door>();
    }

    /**
     * Returns the unique id of the chamber.
     * @return the id for the chamber
     */
    public int getId() {
        return this.id;
    }

    /**
     * A function to take the contents description and actually apply it. This will generate treasure, monsters, and traps.
     */
    private void generateTreasureDesc() {
        description += " Treasure: " + treasure.getDescription() + " Container: " + treasure.getContainer();
        try {
            description += " Protection: " + treasure.getProtection();
        } catch (NotProtectedException ex) {

        }
    }
    private void createTrap() {
        trap = new Trap();
        description += " Trap: " + trap.getDescription();
    }
    private void createTreasure() {
        treasure = new Treasure();
        generateTreasureDesc();
    }
    private void createMonster() {
        monster = new Monster();
        monsters.add(monster);
        description += " Monster: " + monster.getDescription();
    }
    private void generateContents() {
        String contents = myContents.getDescription().toLowerCase();
        String[] sepCont = contents.split(" ");

        description = myContents.getDescription();
        description += "\n";
        //Separated each case into functions instead of doing it here
        for (String cont: sepCont) {
            switch (cont) {
                case "monster":
                    createMonster();
                    break;
                case "treasure":
                    createTreasure();
                    break;
                case "trap":
                    createTrap();
                    break;
                default:
            }
        }
    }

    /**
     * A function that will generate doors from the list of exits within the chambersize of the chamber.
     */
    public void createDoorsFromExits() {
        for (int i = 0; i < mySize.getNumExits(); i++) {
            Door door = new Door();
            doors.add(door);
        }
        if (doors.size() == 5) {
            doors.remove(4);
        }

    }

    /**
     * Gets the amount of the doors left to be connected to in the chamber.
     * @return An int to represent how many doors are left to be connected to.
     */
    public int getAvailDoorCount() {
        int count = 0;
        for (Door d: getDoors()) {
            if (!d.getUsed()) {
               count++;
            }
        }
        return count;
    }

    /**
     * Gets the next free door.
     * @return A door that is not currently connected to anything
     */
    public Door getFreeDoor() {
        for (Door d: doors) {
            if (!d.getUsed()) {
                return d;
            }
        }
        return null;
    }

    /**
     * A function to get the number of exits from the shape of the chamber.
     * @return an integer representing the number of exits in the chamber
     */
    public int getNumExits() {
        return mySize.getNumExits();
    }

    /**
     * A function that allows the setting of the shape for the current chamber.
     * @param theShape The shape to be applied to the chamber object.
     */
    private void setShape(ChamberShape theShape) {
        mySize = theShape;
        createDoorsFromExits();
    }

    /**
     * A function that allows the setting of the contents for the currently chamber object.
     * @param theContents The contents to be applied to the current chamber object.
     */
    private void setContents(ChamberContents theContents) {
        myContents = theContents;
    }

    /**
     * A function that sets the level that the chamber belongs to.
     * @param mLevel The level to attack the chamber to.
     */
    public void setLevel(Level mLevel) {
        this.level = mLevel;
        level.increaseChamberCount();
    }

    /**
     * A function to return the list of all doors in the current chamber.
     * @return ArrayList<Door> A list of all the doors in the current chamber.
     */
    public ArrayList<Door> getDoors() {
        return doors;
    }

    /**
     * A function used to add a monster to the current list of monsters.
     * @param theMonster The monster to be added into the list of the current chamber.
     */
    public void addMonster(Monster theMonster) {
        monster = theMonster;
        monsters.add(theMonster);
    }

    /**
     * Returns the list of monsters in the current room.
     * @return ArrayList<Monster> The list of monsters in the current chamber.
     */
    public ArrayList<Monster> getMonsters() {
        return monsters;
    }

    /**
     * A function to add treasure to the current list of treasure.
     * @param theTreasure The treasure to be added.
     */
    public void addTreasure(Treasure theTreasure) {
        treasure = theTreasure;
        treasures.add(theTreasure);
    }

    /**
     * returns the list of all the treasure in the current chamber.
     * @return The list of treasure.
     */
    public ArrayList<Treasure> getTreasureList() {

        return treasures;
    }

    /**
     * a function to add a door to the current chamber.
     * @param door the door to be added to the list of doors.
     */
    private void addDoor(Door door) {
        doors.add(door);
    }

    /**
     * A function to get the description of the current chamber.
     * @return String the description of the current chamber.
     */
    @Override
    public String getDescription() {
        String desc = "";
        desc += "Width: " + getWidth() + " Length: " + getLength() + "\n";
        desc += "Monsters:\n";
        for (Monster m: monsters) {
            desc += m.getDescription() + "\n";
        }
        desc += "Treasures:\n";
        for (Treasure t: treasures) {
            desc += t.getDescription() + "\n";
        }

        return desc;
    }

    /**
     * Returns the width of the room.
     * @return Integer representing width of room.
     */
    public int getWidth() {
        return mySize.getWidth();
    }

    /**
     * Returns the length of the room.
     * @return Integer representing length of room.
     */
    public int getLength() {
        return mySize.getLength();
    }

    /**
     * A function to set the door attached to another space.
     * @param newDoor The door to be connected to this room.
     */
    @Override
    public void setDoor(Door newDoor) {
        //should add a door connection to this room
        doors.add(newDoor);
        newDoor.setSpaces(null, this);
    }

}
