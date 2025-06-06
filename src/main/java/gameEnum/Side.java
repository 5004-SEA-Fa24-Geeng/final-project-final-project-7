package gameEnum;

public enum Side {
    PLAYER("player"),COMPUTER("computer");

    /** stores the original team name in the enum. */
    private final String cmdName;

    /**
     * Constructor for the enum.
     * 
     * @param cmdName the name that we ask users to put in the terminal.
     */
    Side(String cmdName) {
        this.cmdName = cmdName;
    }

    /**
     * Getter for the team name.
     * 
     * @return the team name.
     */
    public String getCmdName() {
        return this.cmdName;
    }

    /**
     * Get the enum from the column name.
     * 
     * @param cmdName the name that we ask users to put in the terminal.
     * @return the enum that matches the team name.
     */
    public static Side fromCmdName(String cmdName) {
        for (Side col : Side.values()) {
            if (col.getCmdName().equals(cmdName)) {
                return col;
            }
        }
        throw new IllegalArgumentException("No team with name " + cmdName);
    }

    /**
     * Get the enum from the enum name.
     * 
     * Can use the enum name or the column name. Useful for filters and sorts
     * as they can use both.
     * 
     * @param name the name of the enum.
     * @return the enum that matches the name.
     */
    public static Side fromString(String name) {
        for (Side col : Side.values()) {
            if (col.name().equalsIgnoreCase(name) || col.getCmdName().equalsIgnoreCase(name)) {
                return col;
            }
        }
        throw new IllegalArgumentException("No team with name " + name);
    }
}
