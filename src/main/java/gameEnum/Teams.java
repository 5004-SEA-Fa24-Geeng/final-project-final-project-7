package gameEnum;


public enum Teams {
    DIAMONDBACKS("diamondbacks"),ATHLETICS("athletics"),
    BRAVES("braves"),ORIOLES("orioles"),REDSOX("redsox"),
    CUBS("cubs"),WHITESOX("whitesox"),REDS("reds"),
    GUARDIANS("guardians"),ROCKIES("rockies"),TIGERS("tigers"),
    ASTROS("astros"),ROYALS("royals"),ANGELS("angels"),
    DODGERS("dodgers"),MARLINS("marlins"),BREWERS("brewers"),
    TWINS("twins"),METS("mets"),YANKEES("yankees"),
    PHILLIES("phillies"),PIRATES("pirates"),PADRES("padres"),
    GIANTS("giants"),MARINERS("mariners"),CARDINALS("cardinals"),
    RAYS("rays"),RANGERS("rangers"),BLUEJAYS("bluejays"),
    NATIONALS("nationals");

    /** stores the original team name in the enum. */
    private final String cmdName;

    /**
     * Constructor for the enum.
     * 
     * @param cmdName the name that we ask users to put in the terminal.
     */
    Teams(String cmdName) {
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
    public static Teams fromCmdName(String cmdName) {
        for (Teams col : Teams.values()) {
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
    public static Teams fromString(String name) {
        for (Teams col : Teams.values()) {
            if (col.name().equalsIgnoreCase(name) || col.getCmdName().equalsIgnoreCase(name)) {
                return col;
            }
        }
        throw new IllegalArgumentException("No team with name " + name);
    }
}
