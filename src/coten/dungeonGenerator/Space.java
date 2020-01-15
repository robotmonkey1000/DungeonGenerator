package coten.dungeonGenerator;

import java.io.Serializable;

public abstract class Space implements Serializable {

    /**
     * Returns the description of the space.
     * @return A string to be returned
     */
    public abstract  String getDescription();

    /**
     * Sets a door connected to the current space.
     * @param theDoor Door to be part of the current space.
     */
    public abstract void setDoor(Door theDoor);

}
