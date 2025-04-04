package model.player;

public class Pitcher extends Player{
    private String rotation;
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

    public Pitcher(String name, String rotation, int strikes, int pitches, double strikesRate, double ballsRate,
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
    }

    public String getRotation() {
        return this.rotation;
    }

    public int getStrikes() {
        return this.strikes;
    }

    public int getPitches() {
        return this.pitches;
    }

    public double getStrikesRate() {
        return this.strikesRate;
    }

    public double getBallsRate() {
        return this.ballsRate;
    }

    public double getFourSeam() {
        return this.fourSeam;
    }

    public double getTwoSeam() {
        return this.twoSeam;
    }


    public double getCutter() {
        return this.cutter;
    }

    public double getSinker() {
        return this.sinker;
    }

    public double getSlider() {
        return this.slider;
    }

    public double getCurve() {
        return this.curve;
    }

    public double getKnuckle() {
        return this.knuckle;
    }

    public double getSweeper() {
        return this.sweeper;
    }

    public double getSlurve() {
        return this.slurve;
    }

    public double getSplitFinger() {
        return this.splitFinger;
    }

    public double getChangeup() {
        return this.changeup;
    }

    public double getFork() {
        return this.fork;
    }

    public double getScrew() {
        return this.screw;
    }

    @Override
    public String getPosition() {
        return "Pitcher";
    }

}
