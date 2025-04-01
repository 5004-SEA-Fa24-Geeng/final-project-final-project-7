import java.util.Map;

public interface Batters {
    String getName();
    int getPA();
    int getHits();
    double getAVG();
    double getOBP();
    double getOPS();
    Map<String, Double> getHitDistribution();
}
