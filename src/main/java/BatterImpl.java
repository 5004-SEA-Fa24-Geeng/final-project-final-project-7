import java.util.Map;

public class BatterImpl implements Batters{
    private String name;
    private int pa;
    private int hits;
    private int fistBase;
    private int secondBase;
    private int thirdBase;
    private int homeRun;
    private double avg;
    private double obp;
    private double ops;

    public BatterImpl(String name, int pa, int hits, int fistBase, int secondBase, int thirdBase, int homeRun,
                  double avg, double obp, double ops) {
        this.name = name;
        this.pa = pa;
        this.hits = hits;
        this.fistBase = fistBase;
        this.secondBase = secondBase;
        this.thirdBase = thirdBase;
        this.homeRun = homeRun;
        this.avg = avg;
        this.obp = obp;
        this.ops = ops;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getPA() {
        return pa;
    }

    @Override
    public int getHits() {
        return hits;
    }

    @Override
    public double getAVG() {
        return avg;
    }

    @Override
    public double getOBP() {
        return obp;
    }

    @Override
    public double getOPS() {
        return ops;
    }

    public Map<String, Double> getHitDistribution() {
        double total = fistBase + secondBase + thirdBase + homeRun;
        return Map.of(
                "1B", fistBase / total,
                "2B", secondBase / total,
                "3B", thirdBase / total,
                "HR", homeRun / total
        );
}
