package model.player;

public abstract class Player implements PlayerInterface{
    private String name;
    private String pitchType;
    private int plateAppearances;
    private int hits;
    private int singles;
    private int doubles;
    private int triples;
    private int homeRuns;
    private double zoneSwing;
    private double zoneContact;
    private double chase;
    private double chaseContact;
    private String rotation;
    private String pitchName;
    private double pitchUsage;
    private int strikes;
    private int pitches;
    private double strikesRate;
    private double ballsRate;
    
    public String getName() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public String getPosition() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
