package model.player;

public class Batter extends Player{
    private int fastballPA;
    private int fastballH;
    private int fastball1B;
    private int fastball2B;
    private int fastball3B;
    private int fastballHR;
    private int breakingPA;
    private int breakingH;
    private int breaking1B;
    private int breaking2B;
    private int breaking3B;
    private int breakingHR;
    private int offspeedPA;
    private int offspeedH;
    private int offspeed1B;
    private int offspeed2B;
    private int offspeed3B;
    private int offspeedHR;
    private int totalPA;
    private int totalH;
    private int total1B;
    private int total2B;
    private int total3B;
    private int totalHR;
    private double zoneSwing;
    private double zoneContact;
    private double chaseSwing;
    private double chaseContact;
    private double AVG;
    private double OBP;
    private double OPS;

    public Batter(String name, int fastballPA, int fastballH, int fastball1B, int fastball2B, int fastball3B,
                  int fastballHR, int breakingPA, int breakingH, int breaking1B, int breaking2B, int breaking3B,
                  int breakingHR, int offspeedPA, int offspeedH, int offspeed1B, int offspeed2B, int offspeed3B,
                  int offspeedHR, int totalPA, int totalH, int total1B, int total2B, int total3B, int totalHR,
                  double zoneSwing, double zoneContact, double chaseSwing, double chaseContact, double AVG,
                  double OBP, double OPS) {
        super(name);

        this.fastballPA = fastballPA;
        this.fastballH = fastballH;
        this.fastball1B = fastball1B;
        this.fastball2B = fastball2B;
        this.fastball3B = fastball3B;
        this.fastballHR = fastballHR;
        this.breakingH = breakingH;
        this.breakingPA = breakingPA;
        this.breaking1B = breaking1B;
        this.breaking2B = breaking2B;
        this.breaking3B = breaking3B;
        this.breakingHR = breakingHR;
        this.offspeedPA = offspeedPA;
        this.offspeedH = offspeedH;
        this.offspeed1B = offspeed1B;
        this.offspeed2B = offspeed2B;
        this.offspeed3B = offspeed3B;
        this.offspeedHR = offspeedHR;
        this.totalPA = totalPA;
        this.totalH = totalH;
        this.total1B = total1B;
        this.total2B = total2B;
        this.total3B = total3B;
        this.totalHR = totalHR;
        this.zoneSwing = zoneSwing;
        this.zoneContact = zoneContact;
        this.chaseSwing = chaseSwing;
        this.chaseContact = chaseContact;
        this.AVG = AVG;
        this.OBP = OBP;
        this.OPS = OPS;
    }

    public int getFastballPA() {
        return this.fastballPA;
    }

    public int getFastballH() {
        return this.fastballH;
    }

    public int getFastball1B() {
        return this.fastball1B;
    }

    public int getFastball2B() {
        return this.fastball2B;
    }

    public int getFastball3B() {
        return this.fastball3B;
    }

    public int getFastballHR() {
        return this.fastballHR;
    }

    public int getBreakingPA() {
        return this.breakingPA;
    }

    public int getBreakingH() {
        return this.breakingH;
    }

    public int getBreaking1B() {
        return this.breaking1B;
    }

    public int getBreaking2B() {
        return this.breaking2B;
    }

    public int getBreaking3B() {
        return this.breaking3B;
    }

    public int getBreakingHR() {
        return this.breakingHR;
    }

    public int getOffspeedPA() {
        return this.offspeedPA;
    }

    public int getOffspeedH() {
        return this.offspeedH;
    }

    public int getOffspeed1B() {
        return this.offspeed1B;
    }

    public int getOffspeed2B() {
        return this.offspeed2B;
    }

    public int getOffspeed3B() {
        return this.offspeed3B;
    }

    public int getOffspeedHR() {
        return this.offspeedHR;
    }

    public int getTotalPA() {
        return this.totalPA;
    }

    public int getTotalH() {
        return this.totalH;
    }

    public int getTotal1B() {
        return this.total1B;
    }

    public int getTotal2B() {
        return this.total2B;
    }

    public int getTotal3B() {
        return this.total3B;
    }

    public int getTotalHR() {
        return this.totalHR;
    }

    public double getZoneSwing() {
        return this.zoneSwing;
    }

    public double getZoneContact() {
        return this.zoneContact;
    }

    public double getChaseSwing() {
        return this.chaseSwing;
    }

    public double getChaseContact() {
        return this.chaseContact;
    }

    public double getAVG() {
        return this.AVG;
    }

    public double getOBP() {
        return this.OBP;
    }

    public double getOPS() {
        return this.OPS;
    }

    @Override
    public String getPosition() {
        return "Batter";
    }
}
