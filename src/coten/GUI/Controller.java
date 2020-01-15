package coten.GUI;
import coten.dungeonGenerator.Chamber;
import coten.dungeonGenerator.Door;
import coten.dungeonGenerator.Level;
import coten.dungeonGenerator.Passage;
import dnd.models.Monster;
import dnd.models.Treasure;

import java.io.*;
import java.util.*;

public class Controller {

    private Level level;
    private MainGUI gui;
    private ArrayList<Change> removes;
    private ArrayList<Change> additions;
    private FileOutputStream fileOut;
    private FileInputStream fileIn;
    private ObjectOutputStream objectOut;
    private ObjectInputStream objectIn;
    private static final HashMap<String, Integer> MONSTERS = new HashMap<String, Integer>();
    private static final HashMap<String, Integer> TREASURES = new HashMap<String, Integer>();
    static {
        MONSTERS.put("Giant Ant", 1);
        MONSTERS.put("Badger", 3);
        MONSTERS.put("Fire beetle", 5);
        MONSTERS.put("Manes Demon", 15);
        MONSTERS.put("Dwarf", 16);
        MONSTERS.put("Ear Seeker", 18);
        MONSTERS.put("Elf", 19);
        MONSTERS.put("Gnome", 20);
        MONSTERS.put("Goblin", 22);
        MONSTERS.put("Hafling", 27);
        MONSTERS.put("Hobgoblin", 29);
        MONSTERS.put("Human Bandit", 34);
        MONSTERS.put("Kobold", 49);
        MONSTERS.put("Orc", 55);
        MONSTERS.put("Piercer", 67);
        MONSTERS.put("Giant Rat", 71);
        MONSTERS.put("Rot Grub", 84);
        MONSTERS.put("Shrieker", 86);
        MONSTERS.put("Skeleton", 97);
        MONSTERS.put("Zombie", 99);

        TREASURES.put("1000 copper pieces/level", 1);
        TREASURES.put("1000 silver pieces/level", 26);
        TREASURES.put("750 electrum pieces/level", 51);
        TREASURES.put("250 gold pieces/level", 66);
        TREASURES.put("100 platinum pieces/level", 81);
        TREASURES.put("1-4 gems/level", 91);
        TREASURES.put("1 piece jewellery/level", 95);
        TREASURES.put("1 magic item", 98);

    }

    /**
     * Constructor to bind the GUI to the Controller.
     * @param someGUI The main gui to be connected.
     */
    public Controller(MainGUI someGUI) {
        this.level = new Level();
        gui = someGUI;
        removes = new ArrayList<Change>();
        additions = new ArrayList<Change>();
    }

    /**
     * Generates the level for the current object.
     */
    public void createLevel() {
        level.generateLevel();
    }

    /**
     * Returns a list of all chambers from the level.
     * @return An arraylist of chambers.
     */
    public ArrayList<Chamber> getChambers() {
        return level.getChambers();
    }

    /**
     * Returns a list of all passages from the level.
     * @return An arraylist of passages.
     */
    public ArrayList<Passage> getPassages() {
        return level.getPassages();
    }

    /**
     * Gets a specific chamber.
     * @param i The index of the chamber.
     * @return Returns the specific chamber.
     */
    public Chamber getChamber(int i) {
        return level.getChambers().get(i);
    }

    /**
     * Gets a specific passage.
     * @param i The index of the passage.
     * @return Returns the specific passage.
     */
    public Passage getPassage(int i) {
        return level.getPassages().get(i);
    }

    /**
     * Returns all doors in a specific chamber.
     * @param i The index of the chamber
     * @return An arraylist of doors.
     */
    public ArrayList<Door> getChamberDoors(int i) {
        return getChamber(i).getDoors();
    }
    /**
     * Returns all doors in a specific passage.
     * @param i The index of the passage
     * @return An arraylist of passage.
     */
    public ArrayList<Door> getPassageDoors(int i) {
        return getPassage(i).getDoors();
    }

    /**
     * Adds a monster with a specific roll to the specific passage.
     * @param i The passage index
     * @param roll The roll of the monster.
     */
    public void addPassageMonster(int i, int roll) {
        Monster m = new Monster();
        m.setType(roll);
        getPassage(i).addMonster(m);
        gui.updatePassageDescription(i);
        gui.updatePassageDrawing();
    }

    /**
     * removes a monster with a specific roll to the specific passage.
     * @param i The passage index
     * @param index The index of the monster.
     */
    public void removePassageMonster(int i, int index) {
        if (getPassage(i).getMonsters().size() > 0 && getPassage(i).getMonsters().size() > index) {
            getPassage(i).getMonsters().remove(index);
        }
        gui.updatePassageDescription(i);
        gui.updatePassageDrawing();
    }
    /**
     * Adds a treasure with a specific roll to the specific passage.
     * @param i The passage index
     * @param roll The roll of the treasure.
     */
    public void addPassageTreasure(int i, int roll) {
        Treasure t = new Treasure();
        t.chooseTreasure(roll);
        getPassage(i).addTreasure(t);
        gui.updatePassageDescription(i);
        gui.updatePassageDrawing();
    }
    /**
     * removes a treasure with a specific roll to the specific passage.
     * @param i The passage index
     * @param index The index of the treasure.
     */
    public void removePassageTreasure(int i, int index) {
        if (getPassage(i).getTreasure().size() > 0 && getPassage(i).getTreasure().size() > index) {
            getPassage(i).getTreasure().remove(index);
        }
        gui.updatePassageDescription(i);
        gui.updatePassageDrawing();
    }
    /**
     * Adds a monster with a specific roll to the specific chamber.
     * @param i The chamber index
     * @param roll The roll of the monster.
     */
    public void addChamberMonster(int i, int roll) {
        Monster m = new Monster();
        m.setType(roll);
        getChamber(i).addMonster(m);
        gui.updateChamberDescription(i);
        gui.updateChamberDrawing();
    }

    /**
     * Removes a monster from a chamber.
     * @param i the chamber index
     * @param index the moncer index
     */
    public void removeChamberMonster(int i, int index) {
        if (getChamber(i).getMonsters().size() > 0 && getChamber(i).getMonsters().size() > index) {
            getChamber(i).getMonsters().remove(index);
        }
        gui.updateChamberDescription(i);
        gui.updateChamberDrawing();
    }

    /**
     * Adds treasure to a chamber.
     * @param i Chamber index
     * @param roll Treasure roll.
     */
    public void addChamberTreasure(int i, int roll) {
        Treasure t = new Treasure();
        t.chooseTreasure(roll);
        getChamber(i).addTreasure(t);
        gui.updateChamberDescription(i);
        gui.updateChamberDrawing();
    }

    /**
     * Removes treasure from chamber.
     * @param i Chamber index.
     * @param index Treasure index.
     */
    public void removeChamberTreasure(int i, int index) {
        if (getChamber(i).getTreasureList().size() > 0 && getChamber(i).getTreasureList().size() > index) {
            getChamber(i).getTreasureList().remove(index);
        }
        gui.updateChamberDescription(i);
        gui.updateChamberDrawing();
    }

    /**
     * Gets a door from a specific chamber.
     * @param chamberId The chamber id.
     * @param doorId The door id.
     * @return A door.
     */
    public Door getChamberDoor(int chamberId, int doorId) {
        return getChamber(chamberId).getDoors().get(doorId);
    }

    /**
     * Returns a door in a passage.
     * @param passageId Passage Index.
     * @param doorId Door Index.
     * @return A door.
     */
    public Door getPassageDoor(int passageId, int doorId) {
        return getPassage(passageId).getDoors().get(doorId);
    }

    /**
     * Adds a change to the group of additions.
     * @param change The change to be added.
     */
    public void addAddition(Change change) {
        additions.add(change);
    }

    /**
     * Adds a change to the group of removals.
     * @param change The change to be added.
     */
    public void addRemoval(Change change) {
        removes.add(change);
    }

    /**
     * Saves the dungeon to a file.
     * @param file The location of the file to be saved to.
     */
    public void saveDungeon(File file) {
        if (file != null) {
            try {
                fileOut = new FileOutputStream(file.getAbsolutePath());
                objectOut = new ObjectOutputStream(fileOut);
                objectOut.writeObject(level);
                objectOut.close();
                fileOut.close();
                System.out.println("Serialized data is saved in " + file.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Loades a dungeon from a file.
     * @param file The file to be loaded from.
     */
    public void loadDungeon(File file) {
        if (file != null) {
            try {
                fileIn = new FileInputStream(file.getAbsolutePath());
                objectIn = new ObjectInputStream(fileIn);

                level = (Level) objectIn.readObject();

                objectIn.close();
                fileIn.close();

                gui.updateChamberList();
                gui.updatePassageList();

            } catch (IOException e) {
                e.printStackTrace();
                return;
            } catch (ClassNotFoundException cnf) {
                System.out.println("Level class not found");
                cnf.printStackTrace();
                return;
            }
        }
    }

    /**
     * Apply all changes that have been saved Does removes first.
     */
    public void applyChanges() {

        //Sorts list so that the the ones with the greatest index are done first
        Collections.sort(removes, new Comparator<Change>() {
            @Override
            public int compare(Change o1, Change o2) {
                return o2.getId() - o1.getId();
            }
        });

        for (Change c: removes) {
            c.doChange();
        }

        for (Change c: additions) {
            c.doChange();
        }
        removes.clear();
        additions.clear();
    }

    /**
     * Returns an id of a connected passage to a door.
     * @param d The door to be checked.
     * @return An int representing the passage id.
     */
    public int getPassageIDFromDoor(Door d) {
        return ((Passage) d.getSpaces().get(1)).getPassageID();
    }

    /**
     * Returns an id of a connected chamber to a door.
     * @param d The door to be checked.
     * @return An int representing the chamber id.
     */
    public int getChamberIDFromDoor(Door d) {
        return ((Chamber) d.getSpaces().get(0)).getId();
    }

    /**
     * Tells the GUI to select a specific passage.
     * @param index The index of the passage.
     */
    public void selectPassage(int index) {
        gui.selectPassage(index);
        gui.updatePassageDescription(index);
        gui.updatePassageDoorList();
    }

    /**
     * Tells toe GUI to select a specific chamber.
     * @param index The index of the chamber.
     */
    public void selectChamber(int index) {
        gui.selectChamber(index);
        gui.updateChamberDescription(index);
        gui.updateChamberDoorList();
    }

    /**
     * Gets the width of a chamber.
     * @param index The chamber index.
     * @return An integer of width.
     */
    public int getWidth(int index) {
        return getChamber(index).getWidth();

    }

    /**
     * Gets the length of a chamber.
     * @param index The chamber index.
     * @return An integer of length.
     */
    public int getLength(int index) {
        return getChamber(index).getLength();
    }

    /**
     * Returns the list of all monsters possible to be made.
     * @return A hashmap of string to int (Monster to roll).
     */
    public HashMap<String, Integer> getMonsters() {
        return MONSTERS;
    }

    /**
     * Returns the list of all treasure possible to be made.
     * @return A hashmap of string to int (treasure to roll).
     */
    public HashMap<String, Integer> getTreasures() {
        return TREASURES;
    }

    /**
     * returns the roll for a specific monster.
     * @param key String of monster name.
     * @return A int that is the roll of the monster.
     */
    public int getMonsterRoll(String key) {
        return MONSTERS.get(key);
    }

    /**
     * Returns roll for reasure.
     * @param key The treasure to be gotten.
     * @return The roll.
     */
    public int getTreasureRoll(String key) {
        return TREASURES.get(key);
    }





}
