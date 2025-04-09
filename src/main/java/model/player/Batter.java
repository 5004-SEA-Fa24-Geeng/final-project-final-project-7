package model.player;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import java.util.List;

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

    /**
     *
     * @param name
     * @param fastballPA
     * @param fastballH
     * @param fastball1B
     * @param fastball2B
     * @param fastball3B
     * @param fastballHR
     * @param breakingPA
     * @param breakingH
     * @param breaking1B
     * @param breaking2B
     * @param breaking3B
     * @param breakingHR
     * @param offspeedPA
     * @param offspeedH
     * @param offspeed1B
     * @param offspeed2B
     * @param offspeed3B
     * @param offspeedHR
     * @param totalPA
     * @param totalH
     * @param total1B
     * @param total2B
     * @param total3B
     * @param totalHR
     * @param zoneSwing
     * @param zoneContact
     * @param chaseSwing
     * @param chaseContact
     * @param AVG
     * @param OBP
     * @param OPS
     */
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

    /**
     * Get the numbers of PA against fastball.
     * @return The numbers of PA against fastball
     */
    public int getFastballPA() {
        return this.fastballPA;
    }

    /**
     * Get the numbers of hits against fastball.
     * @return The numbers of hits against fastball
     */
    public int getFastballH() {
        return this.fastballH;
    }

    /**
     * Get the numbers of singles against fastball.
     * @return The numbers of singles against fastball
     */
    public int getFastball1B() {
        return this.fastball1B;
    }

    /**
     * Get the numbers of doubles against fastball.
     * @return The numbers of doubles against fastball
     */
    public int getFastball2B() {
        return this.fastball2B;
    }

    /**
     * Get the numbers of triples against fastball.
     * @return The numbers of triples against fastball
     */
    public int getFastball3B() {
        return this.fastball3B;
    }

    /**
     * Get the numbers of homers against fastball.
     * @return The numbers of homers against fastball.
     */
    public int getFastballHR() {
        return this.fastballHR;
    }

    /**
     * Get the numbers of PA against breaking ball.
     * @return The numbers of PA against breaking ball
     */
    public int getBreakingPA() {
        return this.breakingPA;
    }

    /**
     * Get the numbers of hits against breaking ball.
     * @return The numbers of hits against breaking ball
     */
    public int getBreakingH() {
        return this.breakingH;
    }

    /**
     * Get the numbers of singles against breaking ball.
     * @return The numbers of singles against breaking ball
     */
    public int getBreaking1B() {
        return this.breaking1B;
    }

    /**
     * Get the numbers of doubles against breaking ball.
     * @return The numbers of doubles against breaking ball
     */
    public int getBreaking2B() {
        return this.breaking2B;
    }

    /**
     * Get the numbers of triples against breaking ball.
     * @return The numbers of triples against breaking ball
     */
    public int getBreaking3B() {
        return this.breaking3B;
    }

    /**
     * Get the numbers of homers against breaking ball.
     * @return The numbers of homers against breaking ball
     */
    public int getBreakingHR() {
        return this.breakingHR;
    }

    /**
     * Get the numbers of PA against offspeed ball.
     * @return The numbers of PA against off-peed ball
     */
    public int getOffspeedPA() {
        return this.offspeedPA;
    }

    /**
     * Get the numbers of hits against offspeed ball.
     * @return The numbers of hits against offspeed ball.
     */
    public int getOffspeedH() {
        return this.offspeedH;
    }

    /**
     * Get the numbers of singles against offspeed ball.
     * @return The numbers of singles against offspeed ball
     */
    public int getOffspeed1B() {
        return this.offspeed1B;
    }

    /**
     * Get the numbers of doubles against offspeed ball.
     * @return The numbers of doubles against offspeed ball
     */
    public int getOffspeed2B() {
        return this.offspeed2B;
    }

    /**
     * Get the numbers of triples against offspeed ball.
     * @return The numbers of triples against offspeed ball
     */
    public int getOffspeed3B() {
        return this.offspeed3B;
    }

    /**
     * Get the numbers of homers against offspeed ball.
     * @return The numbers of homers against offspeed ball
     */
    public int getOffspeedHR() {
        return this.offspeedHR;
    }

    /**
     * Get the total numbers of PA.
     * @return The total numbers of PA
     */
    public int getTotalPA() {
        return this.totalPA;
    }

    /**
     * Get the total numbers of hits.
     * @return The total numbers of hits
     */
    public int getTotalH() {
        return this.totalH;
    }

    /**
     * Get the total numbers of singles.
     * @return The total numbers of singles
     */
    public int getTotal1B() {
        return this.total1B;
    }

    /**
     * Get the total numbers of doubles.
     * @return The total numbers of doubles
     */
    public int getTotal2B() {
        return this.total2B;
    }

    /**
     * Get the total numbers of triples.
     * @return The total numbers of triples
     */
    public int getTotal3B() {
        return this.total3B;
    }

    /**
     * Get the total numbers of homers.
     * @return The total numbers of homers
     */
    public int getTotalHR() {
        return this.totalHR;
    }

    /**
     * Get the rate of swinging at pitches in the strike zone.
     * @return The zone swing rate
     */
    public double getZoneSwing() {
        return this.zoneSwing;
    }

    /**
     * Gets the rate of making contact on pitches in the strike zone.
     * @return The zone contact rate
     */
    public double getZoneContact() {
        return this.zoneContact;
    }

    /**
     * Get the rate of swinging at pitches outside the strike zone.
     * @return The chase swing rate
     */
    public double getChaseSwing() {
        return this.chaseSwing;
    }

    /**
     * Gets the rate of making contact on pitches outside the strike zone.
     * @return The chase contact rate
     */
    public double getChaseContact() {
        return this.chaseContact;
    }

    /**
     * Get the AVG of the batter.
     * @return The AVG of the batter
     */
    public double getAVG() {
        return this.AVG;
    }

    /**
     * Get the OBP of the batter.
     * @return The OBP of the batter
     */
    public double getOBP() {
        return this.OBP;
    }

    /**
     * Get the OPS of the batter.
     * @return The OPS of the batter
     */
    public double getOPS() {
        return this.OPS;
    }

    /**
     * Get the player type.
     * @return The player type
     */
    @Override
    public String getPosition() {
        return "Batter";
    }

    /**
     * Check if two batters are equal.
     * @param obj Object to compare
     * @return true if the objects are equal
     */
    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj,
                List.of("fastballPA", "fastballH", "fastball1B", "fastball2B", "fastball13B", "fastballHR",
                        "breakingPA", "breakingH", "breaking1B","breaking2B", "breaking3B", "breakingHR",
                        "offspeedPA", "offspeedH", "offspeed1B", "offspeed2B", "offspeed3B", "offspeedHR",
                        "totalPA", "totalH", "total1B", "total2B", "total3B", "totalHR", "zoneSwing", "zoneContact",
                        "chaseSwing", "chaseContact", "AVG", "OBP", "OPS"));
    }

    /**
     * Get the hash code of the object
     * @return The hash code of the object
     */
    public int hashcode() {
        return HashCodeBuilder.reflectionHashCode(this,
                List.of("fastballPA", "fastballH", "fastball1B", "fastball2B", "fastball13B", "fastballHR",
                        "breakingPA", "breakingH", "breaking1B","breaking2B", "breaking3B", "breakingHR",
                        "offspeedPA", "offspeedH", "offspeed1B", "offspeed2B", "offspeed3B", "offspeedHR",
                        "totalPA", "totalH", "total1B", "total2B", "total3B", "totalHR", "zoneSwing", "zoneContact",
                        "chaseSwing", "chaseContact", "AVG", "OBP", "OPS"));
    }

    /**
     * Info of the batter.
     * @return A string contains info of the batter
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("===== ").append(getName()).append(" (").append(getPosition()).append(") =====\n");

        sb.append("Fastball Stats: PA=").append(fastballPA)
                .append(", H=").append(fastballH)
                .append(", 1B=").append(fastball1B)
                .append(", 2B=").append(fastball2B)
                .append(", 3B=").append(fastball3B)
                .append(", HR=").append(fastballHR).append("\n");

        sb.append("Breaking Stats: PA=").append(breakingPA)
                .append(", H=").append(breakingH)
                .append(", 1B=").append(breaking1B)
                .append(", 2B=").append(breaking2B)
                .append(", 3B=").append(breaking3B)
                .append(", HR=").append(breakingHR).append("\n");

        sb.append("Offspeed Stats: PA=").append(offspeedPA)
                .append(", H=").append(offspeedH)
                .append(", 1B=").append(offspeed1B)
                .append(", 2B=").append(offspeed2B)
                .append(", 3B=").append(offspeed3B)
                .append(", HR=").append(offspeedHR).append("\n");

        sb.append("Total Stats: PA=").append(totalPA)
                .append(", H=").append(totalH)
                .append(", 1B=").append(total1B)
                .append(", 2B=").append(total2B)
                .append(", 3B=").append(total3B)
                .append(", HR=").append(totalHR).append("\n");

        sb.append("Swing Stats: ZoneSwing=").append(zoneSwing)
                .append(", ZoneContact=").append(zoneContact)
                .append(", ChaseSwing=").append(chaseSwing)
                .append(", ChaseContact=").append(chaseContact).append("\n");

        sb.append("Overall: AVG=").append(AVG)
                .append(", OBP=").append(OBP)
                .append(", OPS=").append(OPS);

        return sb.toString();
    }

}
