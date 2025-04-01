import java.util.Map;

public interface Pitchers {
    String getName();
    int getRotation();
    double getStrikesRate();
    double getBallsRate();
    Map<String, Double> getPitchUsage();
}
