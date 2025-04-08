package model.player;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import java.util.List;

public class Pitcher extends Player{
    private int rotation;
    private int strikes;
    private int pitches;
    private double strikesRate;
    private double ballsRate;
    private double fourSeam;
    private double twoSeam;
    private double cutter;
    private double sinker;
    private double slider;
    private double curve;
    private double knuckle;
    private double sweeper;
    private double slurve;
    private double splitFinger;
    private double changeup;
    private double fork;
    private double screw;

    /**
     * Construct a new Pitcher with attributes.
     * @param name The name of pitcher
     * @param rotation The rotation of pitcher, 1 as starter, 2 as reliever
     * @param strikes The total thrown strikes
     * @param pitches The total thrown pitches
     * @param strikesRate The rate of strikes
     * @param ballsRate The rate of balls
     * @param fourSeam Probability of 4-seam type
     * @param twoSeam Probability of 2-seam type
     * @param cutter Probability of cutter type
     * @param sinker Probability of sinker type
     * @param slider Probability of slider type
     * @param curve Probability of curve type
     * @param knuckle Probability of knuckle type
     * @param sweeper Probability of sweeper type
     * @param slurve Probability of slurve type
     * @param splitFinger Probability of split finger type
     * @param changeup Probability of change-up type
     * @param fork Probability of fork type
     * @param screw Probability of screw type
     */
    public Pitcher(String name, int rotation, int strikes, int pitches, double strikesRate, double ballsRate,
                   double fourSeam, double twoSeam, double cutter, double sinker, double slider, double curve,
                   double knuckle, double sweeper, double slurve, double splitFinger, double changeup, double fork,
                   double screw) {
        super(name);

        this.rotation = rotation;
        this.strikes = strikes;
        this.pitches = pitches;
        this.strikesRate = strikesRate;
        this.ballsRate = ballsRate;
        this.fourSeam = fourSeam;
        this.twoSeam = twoSeam;
        this.cutter = cutter;
        this.sinker = sinker;
        this.slider = slider;
        this.curve = curve;
        this.knuckle = knuckle;
        this.sweeper =sweeper;
        this.slurve = slurve;
        this.splitFinger = splitFinger;
        this.changeup = changeup;
        this.fork = fork;
        this.screw = screw;
    }

    /**
     * Get the rotation of the pitcher.
     * @return The rotation of the pitcher
     */
    public int getRotation() {
        return this.rotation;
    }

    /**
     * Get the total of strikes.
     * @return The total of strikes.
     */
    public int getStrikes() {
        return this.strikes;
    }

    /**
     * Get the total of pitches.
     * @return The total of pitches
     */
    public int getPitches() {
        return this.pitches;
    }

    /**
     * Get the strike rate.
     * @return The strike rate.
     */
    public double getStrikesRate() {
        return this.strikesRate;
    }

    /**
     * Get the ball rate.
     * @return The ball rate.
     */
    public double getBallsRate() {
        return this.ballsRate;
    }

    /**
     * Get the probability of throwing 4-seam.
     * @return The probability of throwing 4-seam.
     */
    public double getFourSeam() {
        return this.fourSeam;
    }

    /**
     * Get the probability of throwing 2-seam.
     * @return The probability of throwing 2-seam.
     */
    public double getTwoSeam() {
        return this.twoSeam;
    }

    /**
     * Get the probability of throwing cutter.
     * @return The probability of throwing cutter.
     */
    public double getCutter() {
        return this.cutter;
    }

    /**
     * Get the probability of throwing sinker.
     * @return The probability of throwing sinker.
     */
    public double getSinker() {
        return this.sinker;
    }

    /**
     * Get the probability of throwing slider.
     * @return The probability of throwing slider.
     */
    public double getSlider() {
        return this.slider;
    }

    /**
     * Get the probability of throwing curve.
     * @return The probability of throwing curve.
     */
    public double getCurve() {
        return this.curve;
    }

    /**
     * Get the probability of throwing knuckle.
     * @return The probability of throwing knuckle.
     */
    public double getKnuckle() {
        return this.knuckle;
    }

    /**
     * Get the probability of throwing sweeper.
     * @return The probability of throwing sweeper.
     */
    public double getSweeper() {
        return this.sweeper;
    }

    /**
     * Get the probability of throwing slurve.
     * @return The probability of throwing slurve.
     */
    public double getSlurve() {
        return this.slurve;
    }

    /**
     * Get the probability of throwing split finger.
     * @return The probability of throwing split finger.
     */
    public double getSplitFinger() {
        return this.splitFinger;
    }

    /**
     * Get the probability of throwing chang-up.
     * @return The probability of throwing change-up.
     */
    public double getChangeup() {
        return this.changeup;
    }

    /**
     * Get the probability of throwing fork.
     * @return The probability of throwing fork.
     */
    public double getFork() {
        return this.fork;
    }

    /**
     * Get the probability of throwing screw.
     * @return The probability of throwing screw.
     */
    public double getScrew() {
        return this.screw;
    }

    /**
     * Get the player type.
     * @return The player type
     */
    @Override
    public String getPosition() {
        return "Pitcher";
    }

    /**
     * Check if two pitchers are equal.
     * @param obj Object to compare
     * @return true if the objects are equal
     */
    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj,
                List.of("rotation", "strikes", "pitches", "strikesRate", "ballsRate", "fourSeam", "twoSeam",
                        "cutter", "sinker", "slider", "curve", "knuckle", "sweeper", "slurve", "splitFinger",
                        "changeup", "fork", "screw"));
    }

    /**
     * Get the hash code of the object
     * @return The hash code of the object
     */
    public int hashcode() {
        return HashCodeBuilder.reflectionHashCode(this,
                List.of("rotation", "strikes", "pitches", "strikesRate", "ballsRate", "fourSeam", "twoSeam",
                        "cutter", "sinker", "slider", "curve", "knuckle", "sweeper", "slurve", "splitFinger",
                        "changeup", "fork", "screw"));
    }

    /**
     * Info of the pitcher.
     * @return A string contains info of the pitcher
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("===== ").append(getName()).append(" (").append(getPosition()).append(") =====\n");

        sb.append("Role: Rotation=").append(rotation).append("\n");

        sb.append("Performance: Strikes=").append(strikes)
                .append(", Pitches=").append(pitches)
                .append(", StrikesRate=").append(strikesRate)
                .append(", BallsRate=").append(ballsRate).append("\n");

        sb.append("Fastball Types: FourSeam=").append(fourSeam)
                .append(", TwoSeam=").append(twoSeam)
                .append(", Cutter=").append(cutter)
                .append(", Sinker=").append(sinker).append("\n");

        sb.append("Breaking Types: Slider=").append(slider)
                .append(", Curve=").append(curve)
                .append(", Knuckle=").append(knuckle)
                .append(", Sweeper=").append(sweeper)
                .append(", Slurve=").append(slurve).append("\n");

        sb.append("Offspeed Types: SplitFinger=").append(splitFinger)
                .append(", Changeup=").append(changeup)
                .append(", Fork=").append(fork)
                .append(", Screw=").append(screw);

        return sb.toString();
    }
}
