package model;

public enum PlayerData {

    /**
     * Enums matching CODE(cvsname) pattern.
     */
    NAME("Name"),
    //Batters
    F_PA("FastballPA"),
    F_H("FastballH"), F_1B("Fastball1B"),
    F_2B("Fastball2B"), F_3B("Fastball3B"),
    F_HR("FastballHR"), B_PA("BreakingPA"),
    B_H("BreakingH"), B_1B("Breaking1B"),
    B_2B("Breaking2B"), B_3B("Breaking3B"),
    B_HR("BreakingHR"), O_PA("OffspeedPA"),
    O_H("OffspeedH"), O_1B("Offspeed1B"),
    O_2B("Offspeed2B"), O_3B("Offspeed3B"),
    O_HR("OffspeedHR"), T_PA("TotalPA"),
    T_H("TotalH"), T_1B("Total1B"),
    T_2B("Total2B"), T_3B("Total3B"),
    T_HR("TotalHR"), Z_SWING("ZoneSwing"),
    Z_CONTACT("ZoneContact"), C_SWING("ChaseSwing"),
    C_CONTACT("ChaseContact"), AVG("AVG"),
    OBP("OBP"), OPS("OPS"),
    //Pitchers
    ROTATION("Rotation"), STRIKES("Strikes"),
    PITCHES("Pitches"), S_RATE("StrikesRate"),
    B_RATE("BallsRate"), FOURSEAM("4-SeamFastball"),
    TWOSEAM("2-SeamFastball"), CUTTER("Cutter"),
    SINKER("Sinker"), SLIDER("Slider"),
    CURVE("Curveball"), KNUCKLE("Knuckle"),
    SWEEPER("Sweeper"), SLURVE("Slurve"),
    SFINGER("Split-Finger"), CHANGEUP("Changeup"),
    FORK("Fork"), SCREW("Screw");

    /** stores the original csv name in the enum. */
    private final String columnName;

    /**
     * Constructor for the enum.
     * 
     * @param columnName the name of the column in the CSV file.
     */
    PlayerData(String columnName) {
        this.columnName = columnName;
    }

    /**
     * Getter for the column name.
     * 
     * @return the name of the column in the CSV file.
     */
    public String getColumnName() {
        return columnName;
    }

    /**
     * Get the enum from the column name.
     * 
     * @param columnName the name of the column in the CSV file.
     * @return the enum that matches the column name.
     */
    public static PlayerData fromColumnName(String columnName) {
        for (PlayerData col : PlayerData.values()) {
            if (col.getColumnName().equals(columnName)) {
                return col;
            }
        }
        throw new IllegalArgumentException("No column with name " + columnName);
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
    public static PlayerData fromString(String name) {
        for (PlayerData col : PlayerData.values()) {
            if (col.name().equalsIgnoreCase(name) || col.getColumnName().equalsIgnoreCase(name)) {
                return col;
            }
        }
        throw new IllegalArgumentException("No column with name " + name);
    }

}
