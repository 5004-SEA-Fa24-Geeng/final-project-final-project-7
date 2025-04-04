package model.player;

public class Pitcher extends Player{
    private String rotation;
    private String pitchName;
    private double pitchUsage;
    private int strikes;
    private int pitches;
    private double strikesRate;
    private double ballsRate;

    public Pitcher(String name, String rotation, String pitchName, double pitchUsage,
                   int strikes, int pitches, double strikesRate, double ballsRate) {
        super(name);

        this.rotation = rotation;
        this.pitchName = pitchName;
        this.pitchUsage = pitchUsage;
        this.strikes = strikes;
        this.pitches = pitches;
        this.strikesRate = strikesRate;
        this.ballsRate = ballsRate;

    }

    public String getRotation() {
        return rotation;
    }

    public String getPitchName() {
        return pitchName;
    }

    public double getPitchUsage() {
        return pitchUsage;
    }

    public int getStrikes() {return strikes;
    }

    public int getPitches() {
        return pitches;
    }

    public double getStrikesRate() {
        return strikesRate;
    }

    public double getBallsRate() {
        return ballsRate;
    }

    @Override
    public String getPosition() {
        return "Pitcher";
    }

}
