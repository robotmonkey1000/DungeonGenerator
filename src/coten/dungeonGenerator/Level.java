package coten.dungeonGenerator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Level implements Serializable {
    /**
     * The list of all passages generated.
     */
    private ArrayList<Passage> passages;

    /**
     * A list of chambers that will be generated.
     */
    private ArrayList<Chamber> chambers;
    /**
     * The count of chambers used to judge if generation was successful or not.
     */
    private int chamberCount;

    /**
     * An integer to generate this many rooms.
     */
    private int chamberMax = 5;

    /**
     * Main Constructor of Level class. This class is used to handle the creation of a level.
     * A level consists of 5 chambers connected by passages.
     */
    public Level() {
        chamberCount = 0;
        passages = new ArrayList<Passage>();
        chambers = new ArrayList<Chamber>();
    }

    /**
     * A secondary Constructor of a level. Used if I wanted to pass in a list of already generated passages
     * @param somePassages A list of already generated passages.
     */
    public Level(ArrayList<Passage> somePassages) {
        chamberCount = 0;
        setPassages(somePassages);
        chambers = new ArrayList<Chamber>();
    }

    /**
     * Helper function if I would like to set a list of already created passages into the level.
     * @param somePassages A list of already generated passages.
     */
    private void setPassages(ArrayList<Passage> somePassages) {
        passages = new ArrayList<Passage>();
        passages = somePassages;
    }

    /**
     * Adds a passage into the list of passages.
     * @param somePassage The passage that should be added.
     */
    private void addPassage(Passage somePassage) {
        passages.add(somePassage);
    }

    /**
     * Clears the passage list in case of a needed restart.
     */
    private void clearPassages() {
        passages.clear();
    }

    /**
     * The main function to be called. This will generate the level making sure that there is 5 chambers restarting until successful.
     */
    public void generateLevel() {
        /*
        while (chamberCount != 5) {
            clearPassages();
            chamberCount = 0;
            Passage newPassage = new Passage(this);
            newPassage.generatePassage();
        } Old Generation
        printPassages(); */
        generateChambers();
        sortChambers();
        HashMap<Door, ArrayList<Chamber>> connectionMap = generateConnectionMap();

        for (Chamber c: chambers) {
            System.out.println("Chamber: " + c.getId() + " has " + c.getDoors().size() + " doors");
            int i = 0;
            for (Door d: c.getDoors()) {
                ArrayList<Chamber> connection = connectionMap.get(d);
                for (Chamber cc : connection) {
                    System.out.println("Door " + i + " Connects to chamber " + cc.getId());
                }
                i++;
            }

        }

        ArrayList<Passage> dPassages = generateSections(connectionMap);
        setPassageIDs();
        System.out.println("There are " + dPassages.size() + " passages.");



    }
    private ArrayList<Passage> generateSections(HashMap<Door, ArrayList<Chamber>> map) {
        ArrayList<Door> linked = new ArrayList<Door>();
        ArrayList<Passage> doorPassages = new ArrayList<Passage>();
        passages = doorPassages;
        for (int i = chambers.size() - 1; i > 0; i--) {
            Chamber c = chambers.get(i);
            for (Door d: c.getDoors()) {
                if (!linked.contains(d)) {

                    Passage newPassage = new Passage();
                    PassageSection ps1 = new PassageSection();
                    ps1.setDoor(d);
                    newPassage.addPassageSection(ps1);


                    for (Chamber cc : map.get(d)) {
                        for (Door d2 : cc.getDoors()) {
                            if (!linked.contains(d2)) {

                                if (map.get(d2).contains(c) && map.get(d).contains(cc)) {
                                    linked.add(d2);
                                    linked.add(d);
                                    PassageSection ps2 = new PassageSection();
                                    ps2.setDoor(d2);
                                    newPassage.addPassageSection(ps2);
                                    d.setSpaces(c, newPassage);
                                    d2.setSpaces(cc, newPassage);
                                }
                            }
                        }
                    }
                    doorPassages.add(newPassage);

                }
            }
        }
        return doorPassages;
    }

    private Passage createPassage(Door d1, Door d2) {
        Passage newPassage = new Passage();

        PassageSection ps1 = new PassageSection();
        ps1.setDoor(d1);
        PassageSection ps2 = new PassageSection();
        ps2.setDoor(d2);
        newPassage.addPassageSection(ps1);
        newPassage.addPassageSection(ps2);

        return newPassage;
    }

    private HashMap<Door, ArrayList<Chamber>> generateConnectionMap() {
        HashMap<Door, ArrayList<Chamber>> connectionMap = new HashMap<Door, ArrayList<Chamber>>();
        for (Chamber c: chambers) {
            Door d = c.getFreeDoor();
            ArrayList<Chamber> collection;
            int nextChamber = chambers.indexOf(c) + 1;
            if (nextChamber > chambers.size() - 1) {
                break;
            }
            while (d != null) {
                collection = new ArrayList<Chamber>();
                Chamber c2 = chambers.get(nextChamber);
                ArrayList<Chamber> d2Collection;
                if (c2.equals(c)) {
                    nextChamber++;
                    if (nextChamber > chambers.size() - 1) {
                        break;
                    }
                    c2 = chambers.get(nextChamber);
                }
                if (c2.getAvailDoorCount() > 0) {
                   d2Collection = new ArrayList<Chamber>();
                   Door d2 = c2.getFreeDoor();
                   d2.setUsed(true);
                   d.setUsed(true);
                   collection.add(c2);
                   d2Collection.add(c);
                   connectionMap.put(d, collection);
                   connectionMap.put(d2, d2Collection);
                } else {
                    int i = 0;
                    Door d2 = c2.getDoors().get(i);
                    while (connectionMap.get(d2).contains(c)) {
                        i++;
                        if (i > c2.getDoors().size() - 1) {
                            d2 = null;
                            break;
                        }
                        d2 = c2.getDoors().get(i);
                    }
                    if (d2 == null) {
                        nextChamber++;
                        continue;
                    }

                    d2Collection = connectionMap.get(d2);
                    d2Collection.add(c);
                    collection.add(c2);
                    d.setUsed(true);
                    connectionMap.put(d, collection);
                }
                nextChamber++;
                if (nextChamber > chambers.size() - 1) {
                    break;
                }
                d = c.getFreeDoor();
            }

        }
        for (Chamber c: chambers) {
            Door d1 = c.getFreeDoor();
            while (d1 != null) {
                for (Chamber cc: chambers) {
                    if (cc.equals(c)) {
                      continue;
                    }
                    Door d2 = null;
                    if (cc.getFreeDoor() != null) {
                        d2 = cc.getFreeDoor();
                        ArrayList<Chamber> d2Connections = new ArrayList<Chamber>();
                        d2Connections.add(c);


                        ArrayList<Chamber> connections = new ArrayList<Chamber>();
                        connections.add(cc);

                        d2.setUsed(true);
                        d1.setUsed(true);

                        connectionMap.put(d2, d2Connections);
                        connectionMap.put(d1, connections);
                        break;
                    } else {
                        for (int i = 0; i < cc.getDoors().size(); i++) {
                          if (!connectionMap.get(cc.getDoors().get(i)).contains(c)) {
                              d2 = cc.getDoors().get(i);
                          }
                        }
                        if (d2 == null) {
                         continue;
                        }

                        connectionMap.get(d2).add(c);
                        d1.setUsed(true);
                        ArrayList<Chamber> connections = new ArrayList<Chamber>();
                        connections.add(cc);
                        connectionMap.put(d1, connections);
                        break;
                    }
                  //On success break out of loop
                }
                d1 = c.getFreeDoor();
            }
        }

        /*
        for (Chamber c: chambers) {
            int doorCount = 0;
            for (Door d: c.getDoors()) {
                if (d.getUsed()) {
                    if (connectionMap.get(d).contains(c)) {
                        doorCount++;
                    }
                    continue;
                }
                if (chambers.get(doorCount).equals(c)) {
                    doorCount++;
                }
                ArrayList<Chamber> connections = new ArrayList<Chamber>();
                if (chambers.get(doorCount).getAvailDoorCount() > 0) {
                    ArrayList<Chamber> otherConnection = new ArrayList<Chamber>();
                    Door door = chambers.get(doorCount).getFreeDoor();
                    connections.add(chambers.get(doorCount));
                    door.setUsed(true);
                    otherConnection.add(c);
                    connectionMap.put(door, otherConnection);

                    d.setUsed(true);
                    connections.add(chambers.get(doorCount));
                    connectionMap.put(d, connections);
                } else {
                    Door door = chambers.get(doorCount).getDoors().get(0);
                    ArrayList<Chamber> otherConnection = connectionMap.get(door);
                    otherConnection.add(c);


                    d.setUsed(true);
                    connections.add(chambers.get(doorCount));
                    connectionMap.put(d, connections);
                }
                doorCount++;
                doorCount = doorCount % (chambers.size());
            }
        }

         */
        return connectionMap;
    }
    private void generateChambers() {
        for (int i = 0; i < chamberMax; i++) {
            Chamber chamber = new Chamber(chambers.size());
            chambers.add(chamber);
            chamber.createDoorsFromExits();
        }
    }

    private void setPassageIDs() {
        int counter = 0;
        for (Passage p: passages) {
            p.setPassageID(counter);
            counter++;
        }
    }

    private void sortChambers() {
        Chamber temp;
        Chamber outer;
        int tempIndex;
        for (int i = 0; i < chambers.size(); i++) {
            outer = chambers.get(i);
            tempIndex = i;
            temp = outer;
            for (int j = i + 1; j < chambers.size(); j++) {
                if (temp.getNumExits() < chambers.get(j).getNumExits()) {
                    temp = chambers.get(j);
                    tempIndex = j;
                }
            }
            chambers.set(i, temp);
            chambers.set(tempIndex, outer);
        }
    }

    /**
     * A function to print the descriptions of each passage and the chambers that are connected.
     */
    private void printPassages() {
        int passageNum = 0;
        for (Passage p: passages) {
            System.out.println(p.getDescription());
            System.out.println();
            if (p != passages.get(passages.size() - 1)) {
                System.out.println("Passage continuing from chamber: ");
            }
        }
        //System.out.println(passages.size());
        System.out.println("Chamber Count: " + chamberCount);
    }

    /**
     * This function increases the chamber count.
     */
    public void increaseChamberCount() {
        chamberCount++;
    }

    /**
     * this function returns how many chambers are currently existing.
     * @return An int. How many chambers currently exist.
     */
    public int getChamberCount() {
        return chamberCount;
    }

    /**
     * Returns all chambers in the current level.
     * @return ArrayList of chambers.
     */
    public ArrayList<Chamber> getChambers() {
        return chambers;
    }

    /**
     * Returns all passages in the current level.
     * @return ArrayList of passage.
     */
    public ArrayList<Passage> getPassages() {
        return passages;
    }
}
