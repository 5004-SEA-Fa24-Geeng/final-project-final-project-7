import java.util.Map;

public class PitcherImpl implements Pitchers {
    private String name;
    private int rotation;
    private double strikesRate;
    private double ballsRate;
    private Map<String, Double> pitchUsage;

    public PitcherImpl(String name, int rotation, double strikesRate, double ballsRate,
                       Map<String, Double> pitchUsage) {
        this.name = name;
        this.rotation = rotation;
        this.strikesRate = strikesRate;
        this.ballsRate = ballsRate;
        this.pitchUsage = pitchUsage;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getRotation() {
        return rotation;
    }

    @Override
    public double getStrikesRate() {
        return strikesRate;
    }

    @Override
    public double getBallsRate() {
        return ballsRate;
    }

    @Override
    public Map<String, Double> getPitchUsage() {
        return pitchUsage;
    }

}
