package coten.dungeonGenerator;

import dnd.models.Monster;
import dnd.models.Treasure;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Passage extends Space implements Serializable {
    /**
     * A list of all passage sections in the current passge.
     */
    private ArrayList<PassageSection> thePassage;
    /**
     * A list of all the doors in the current passge.
     */
    private ArrayList<Door> doors;
    /**
     * A hashmap between a Door and A connected passage section.
     */
    private HashMap<Door, PassageSection> doorMap;
    /**
     * the current passage section. Used for setting doors connecting to it.
     */
    private PassageSection currentSection;
    /**
     * the level that the passage will belong to.
     */
    private Level level;
    /**
     * A variable to judge if there is a need for door backtracking.
     * If the hallway dead ends without a door then we can go back in another passage to generate another chamber.
     */
    private int doorDisplacement;

    private ArrayList<Monster> monsters;
    private  ArrayList<Treasure> treasures;
    private int passageID;

    /**
     * A constructor for passage. This one allows for a level to be assigned at creation.
     * @param mLevel The level that the passge belongs to.
     */
    public Passage(Level mLevel) {
        initLists();
        setLevel(mLevel);
        doorDisplacement = 1;
    }
    /**
     * A constructor for a passage.
     * This initialized some instance variables.
     */
    public Passage() {
        initLists();
        doorDisplacement = 1;
    }
    private void initLists() {
        thePassage =  new ArrayList<PassageSection>();
        doorMap = new HashMap<Door, PassageSection>();
        doors = new ArrayList<Door>();
        monsters = new ArrayList<Monster>();
        treasures = new ArrayList<Treasure>();
    }

    /**
     * Returns the id of the current passage.
     * @return Index of current passage.
     */
    public int getPassageID() {
        return passageID;
    }

    /**
     * Sets the index of the current passage.
     * @param id the index of the passage.
     */
    public void setPassageID(int id) {
        this.passageID = id;
    }

    /**
     * Sets which level the current passage is on.
     * @param mLevel The level which the passage should be set in.
     */
    public void setLevel(Level mLevel) {
        this.level = mLevel;
    }

    /**
     * A function to get the current level that the passage belongs to.
     * @return The level of the current passage
     */
    public Level getLevel() {
        return this.level;
    }
    /**
     * Returns a list of all the doors in the current passage.
     * @return An array list of doors.
     */
    public ArrayList<Door> getDoors() {
        //gets all of the doors in the entire passage
        ArrayList<Door> sectionDoors = new ArrayList<Door>();
        for (PassageSection ps: thePassage) {
            if (ps.getDoor() != null && !doors.contains(ps.getDoor())) {
               doors.add(ps.getDoor());
            }
        }

        return doors;
    }

    /**
     * Checks the specific passage section for a door and returns it if there is one.
     * @param i Which passage section to be checked.
     * @return The door in the passage section or null if there is not one.
     */
    public Door getDoor(int i) {
        //returns the door in section 'i'. If there is no door, returns null
        if (i < thePassage.size() && i >= 0) {
            if (thePassage.get(i).getDoor() == null) {
                return null;
            }
            return thePassage.get(i).getDoor();
        }
        return null;
    }

    /**
     * A method to set the door of a specific passage section in the current passage.
     * @param i The section number to add the door to.
     * @param d The door to add to the section.
     */
    public void setDoor(int i, Door d) {
        if (i < thePassage.size() && i >= 0) {
            thePassage.get(i).setDoor(d);
            return;
        }
    }

    /**
     * Adds a monster to the passage section specified.
     * @param theMonster A monster to be added.
     * @param i Which passage section it should be added to.
     */
    public void addMonster(Monster theMonster, int i) {
        // adds a monster to section 'i' of the passage
        if (i < thePassage.size() && i >= 0) {
            thePassage.get(i).setMonster(theMonster);
        }
    }

    /**
     * Adds a monster to the passage.
     * @param m The monster to be added.
     */
    public void addMonster(Monster m) {
        if (!monsters.contains(m)) {
            monsters.add(m);
        }
    }

    /**
     * Gets all the monsters ni the passage.
     * @return ArrayList of monsters.
     */
    public ArrayList<Monster> getMonsters() {
        for (PassageSection ps: thePassage) {
            if (!monsters.contains(ps.getMonster())) {
                if (ps.getMonster() != null) {
                    monsters.add(ps.getMonster());
                }
            }
        }
        return monsters;
    }

    /**
     * Adds treasure to the current passage.
     * @param t The treasure to be added.
     */
    public void addTreasure(Treasure t) {
        if (!treasures.contains(t)) {
            treasures.add(t);
        }
    }

    /**
     * Gets all the treasures in the passage.
     * @return ArrayList treasure.
     */
    public ArrayList<Treasure> getTreasure() {
        return treasures;
    }

    /**
     * This returns a monster at a specific index in the list of passage sections.
     * @param i Which passage section to be getting the monster from.
     * @return A monster or null if there is not one.
     */
    private Monster getMonster(int i) {
        //returns Monster door in section 'i'. If there is no Monster, returns null
        //System.out.println("Adding Monster");
        if (i < thePassage.size() && i >= 0) {
            //System.out.println("Success");
            return (thePassage.get(i).getMonster());
        }

        return null;
    }

    /**
     * A function to return the list of passage sections contained in this passage.
     * @return An array list of passage sections.
     */
    public ArrayList<PassageSection> getPassageSections() {
        return thePassage;
    }
    /**
     * This functions adds a specificed section to the list of passage sections.
     * @param toAdd The passage to be added to the list.
     */
    public void addPassageSection(PassageSection toAdd) {
        //adds the passage section to the passageway
        currentSection = toAdd;
        thePassage.add(toAdd);
    }

    /**
     * This will generate a bunch of passage sections (until there is 10 of them or it meets an end requirement).
     * After generating the section it will proceed to generate the connected chamber.
     */
    private void generatePassage() {
        if (level.getChamberCount() != 5) {
           // level.addPassage(this);
            while (thePassage.size() < 10) {
                currentSection = new PassageSection(this);
             //   currentSection.initPassageSection();
                thePassage.add(currentSection);
                if (currentSection.isPassageEnded()) {
                    break;
                }
            }
            generateChamber();
        }
    }

    /**
     * This will add a door connected to the current passage section then add it to the list of doors in the current passage.
     * @param newDoor
     */
    @Override
    public void setDoor(Door newDoor) {
        //should add a door connection to the current Passage Section
        doorMap.put(newDoor, currentSection);
        if (!doors.contains(newDoor)) {
            doors.add(newDoor);
        }
    }

    /**
     * Makes sure there is at least one door and that there is not more than 5 chambers.
     * Than it will pick a door and generate a chamber.
     * This will than attach the chamber to the door.
     */
    private void generateChamber() {
        if (doors.size() != 0 && level.getChamberCount() < 5) {
            Door currDoor = doors.get(doors.size() - doorDisplacement);
            if (currDoor != null) {
                Chamber chamber = new Chamber(0);
                chamber.setLevel(this.level);
                currentSection = doorMap.get(currDoor);
                currDoor.setSpaces(this, chamber);
                //chamber.addDoor(currDoor);
               // chamber.generateDoors();
            }
        }
    }
    /**
     * Returns the description of the current passage. This contains the passage sections, the doors, and the connected chambers.
     * @return A string with a lot of information about the passage.
     */
    @Override
    public String getDescription() {
        String desc = "";
        desc += getSectionsDescription();
        desc += "\nTreasure:\n" + getTreasureDesc();
        desc += "\nMonsters:\n" + getMonsterDesc();
        //desc += getDoorsDescription();
        return desc;
    }

    private String getTreasureDesc() {
        String desc = "";
        for (Treasure t: getTreasure()) {
            desc += t.getWholeDescription() + "\n";
        }
        return desc;
    }
    private String getMonsterDesc() {
        String desc = "";
        for (Monster m: getMonsters()) {
            desc += m.getDescription() + "\n";
        }

        return desc;
    }

    private String getSectionsDescription() {
        String desc = "";
        for (PassageSection ps: thePassage) {
            desc += ps.getDescription() + "->";
        }
        return desc;
    }
    private String getDoorsDescription() {
        String desc = "";
        for (Door d: doors) {
            if (d.getSpaces().size() > 0) {
                desc += "\nThe Door leading to the chamber: " + d.getDescription();
                desc += "\nChamber Contains: " + d.getSpaces().get(1).getDescription() + " ";
            }
        }

        return desc;
    }
}
