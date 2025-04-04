package model.player;

public class Batter extends Player{
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

    public Batter(String name, String pitchType, int plateAppearances, int hits, int singles, int doubles,
                  int triples, int homeRuns, double zoneSwing, double zoneContact, double chase, double chaseContact) {
        super(name);

        this.pitchType = pitchType;
        this.plateAppearances = plateAppearances;
        this.hits = hits;
        this.singles = singles;
        this.doubles = doubles;
        this.triples = triples;
        this.homeRuns = homeRuns;
        this.zoneSwing = zoneSwing;
        this.zoneContact = zoneContact;
        this.chase = chase;
        this.chaseContact = chaseContact;
    }

    public String getPitchType() {
        return pitchType;
    }

    public int getPlateAppearances() {
        return plateAppearances;
    }

    public int getHits() {
        return hits;
    }

    public int getSingles() {
        return singles;
    }

    public int getDoubles() {
        return doubles;
    }

    public int getTriples() {
        return triples;
    }

    public int getHomeRuns() {
        return homeRuns;
    }

    public double getZoneSwing() {
        return zoneSwing;
    }
    public double getZoneContact() {
        return zoneContact;
    }
    public double getChase() {
        return chase;
    }

    public double getChaseContact() {
        return chaseContact;
    }

    @Override
    public String getPosition() {
        return "Batter";
    }
}
