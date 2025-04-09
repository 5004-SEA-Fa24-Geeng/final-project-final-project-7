package model.inning;

import gameEnum.Balls;
import gameEnum.Hits;
import gameEnum.Outs;
import gameEnum.Strikes;
import model.player.Pitcher;
import model.player.Batter;
import model.team.Team;

import java.util.*;


public class RegularInning implements Inning{
    private int outs;
    private int strikes;
    private int balls;
    private int[] bases;
    private Random random = new Random();

    private Pitcher currentPitcher;
    private int currentBatterIndex;
    private int battersFaced;

    private int pitchesThrown = 0;
    private int hits = 0;
    private int singles = 0;
    private int doubles = 0;
    private int triples = 0;
    private int homeRuns = 0;
    private int strikeouts = 0;
    private int walks = 0;
    private Map<String, Integer> pitchTypeCounts = new HashMap<>();
    private Map<String, Integer> pitchCategoryCounts = new HashMap<>();


    private final Map<String, List<String>> pitchTypeMap = Map.of(
            "Fastball", Arrays.asList("fourSeam", "twoSeam", "cutter", "sinker"),
            "Breaking", Arrays.asList("slider", "curve", "knuckle", "sweeper", "slurve"),
            "Offspeed", Arrays.asList("splitFinger", "changeup", "fork", "screw")
    );

    /**
     * Start a clean inning with certain pitcher.
     * @param pitcher The pitcher face batters in the innings
     */
    public RegularInning(Pitcher pitcher) {
        this.currentPitcher = pitcher;
        this.bases = new int[]{0, 0, 0, 0};
        this.battersFaced = 0;
    }

    /**
     * Reset the inning data for next inning.
     */
    @Override
    public void resetInning() {
        this.outs = 0;
        this.strikes = 0;
        this.balls = 0;
        this.bases = new int[]{0, 0, 0, 0};
        this.battersFaced = 0;
        this.pitchesThrown = 0;
        this.pitchTypeCounts.clear();
        this.pitchCategoryCounts.clear();
        this.hits = 0;
        this.singles = 0;
        this.doubles = 0;
        this.triples = 0;
        this.homeRuns = 0;
        this.strikeouts = 0;
        this.walks = 0;
    }

    /**
     * Runs a full inning with batter lineup, ends the inning when 3 outs.
     * @param playerTeam The team contains batter lineup
     * @param currentBatterIndex The current batter index in lineup
     * @return The run score in the inning
     */
    public int runInning(Team playerTeam, int currentBatterIndex) {
        resetInning();
        List<Batter> lineup = playerTeam.getBatterLineup();
        this.currentBatterIndex = currentBatterIndex;
        int score = 0;

        if (lineup.size() != 9) {
            throw new IllegalStateException("Batting lineup should be 9 batters!");
        }

        while (outs < Outs.THREE.ordinal() + 1) {
            if (currentBatterIndex >= lineup.size()) {
                currentBatterIndex = 0;
            }
            Batter batter = lineup.get(currentBatterIndex);
            score += inningAtBat(batter);
            currentBatterIndex++;
            battersFaced++;
        }
        return score;
    }

    /**
     * Simulate a single at-bat between pitcher and batter
     * @param batter The current batter at plate
     * @return The number of scores during this bat
     */
    private int inningAtBat(Batter batter) {
        this.strikes = 0;
        this.balls = 0;

        while (true) {
            String pitchName = determinePitchType();
            String pitchCategory = getPitchCategory(pitchName);

            // Strike or ball
            boolean isStrike = random.nextDouble() < currentPitcher.getStrikesRate();

            if (isStrike) {
                if (random.nextDouble() < batter.getZoneSwing()) {
                    if (random.nextDouble() < batter.getZoneContact()) {
                        return determineHitOutcome(batter, pitchCategory);
                    } else {
                        // Swing and miss
                        strikes++;
                    }
                } else {
                    // Take strike
                    strikes++;
                }
            } else {
                if (random.nextDouble() < batter.getChaseSwing()) {
                    if (random.nextDouble() < batter.getChaseContact()) {
                        return determineHitOutcome(batter, pitchCategory);
                    } else {
                        // Swing and miss
                        strikes++;
                    }
                } else {
                    // Take ball
                    balls++;
                }
            }

            // Check for strikeout or walk
            if (strikes >= Strikes.THREE.ordinal() + 1) {
                outs++;
                strikeouts++;
                return 0;
            } else if (balls >= Balls.FOUR.ordinal() + 1) {
                // Walk
                walks++;
                return advanceRunnersOnWalk();
            }
        }
    }

    /**
     * Determine the pitch type of the ball thrown by pitcher.
     * @return The name of the pitch type
     */
    private String determinePitchType() {
        List<Map.Entry<String, Double>> pitches = new ArrayList<>();

        if (currentPitcher.getFourSeam() > 0) {
            pitches.add(Map.entry("fourSeam", currentPitcher.getFourSeam()));
        }

        if (currentPitcher.getTwoSeam() > 0) {
            pitches.add(Map.entry("twoSeam", currentPitcher.getTwoSeam()));
        }

        if (currentPitcher.getCutter() > 0) {
            pitches.add(Map.entry("cutter", currentPitcher.getCutter()));
        }

        if (currentPitcher.getSinker() > 0) {
            pitches.add(Map.entry("sinker", currentPitcher.getSinker()));
        }

        if (currentPitcher.getSlider() > 0) {
            pitches.add(Map.entry("slider", currentPitcher.getSlider()));
        }

        if (currentPitcher.getCurve() > 0) {
            pitches.add(Map.entry("curve", currentPitcher.getCurve()));
        }

        if (currentPitcher.getKnuckle() > 0) {
            pitches.add(Map.entry("knuckle", currentPitcher.getKnuckle()));
        }

        if (currentPitcher.getSweeper() > 0) {
            pitches.add(Map.entry("sweeper", currentPitcher.getSweeper()));
        }

        if (currentPitcher.getSlurve() > 0) {
            pitches.add(Map.entry("slurve", currentPitcher.getSlurve()));
        }

        if (currentPitcher.getSplitFinger() > 0) {
            pitches.add(Map.entry("splitFinger", currentPitcher.getSplitFinger()));
        }

        if (currentPitcher.getChangeup() > 0) {
            pitches.add(Map.entry("changeup", currentPitcher.getChangeup()));
        }

        if (currentPitcher.getFork() > 0) {
            pitches.add(Map.entry("fork", currentPitcher.getFork()));
        }

        if (currentPitcher.getScrew() > 0) {
            pitches.add(Map.entry("screw", currentPitcher.getScrew()));
        }

        double totalProb = pitches.stream().mapToDouble(Map.Entry::getValue).sum();
        double rand = random.nextDouble() * totalProb;

        String pitchType = pitches.get(0).getKey();

        double sumProb = 0.0;
        for (Map.Entry<String, Double> pitch : pitches) {
            sumProb += pitch.getValue();
            if (rand < sumProb) {
                pitchType = pitch.getKey();
                break;
            }
        }
        pitchesThrown++;
        pitchTypeCounts.put(pitchType, pitchTypeCounts.getOrDefault(pitchType, 0) + 1);
        return pitchType;
    }

    /**
     * Identify the thrown ball's pitch category based on pitch type name.
     * @param pitchName The specific type of name of the pitch
     * @return The category of the pitch belongs
     */
    private String getPitchCategory(String pitchName) {
        String category = "Fastball";

        for (Map.Entry<String, List<String>> entry : pitchTypeMap.entrySet()) {
            if (entry.getValue().contains(pitchName)) {
                category = entry.getKey();
                break;
            }
        }
        pitchCategoryCounts.put(category, pitchCategoryCounts.getOrDefault(category, 0) + 1);
        return category;
    }

    /**
     * Determine the outcome of the hit by batter. Use batter's statistics to see the hit result.
     * @param batter The current batter
     * @param pitchCategory The category of the pitch
     * @return The number of scores from this bat
     */
    private int determineHitOutcome(Batter batter, String pitchCategory) {
        int plateAppearances;
        int hits;
        int singles;
        int doubles;
        int triples;
        int homeRuns;

        switch (pitchCategory) {
            case "Fastball":
                plateAppearances = batter.getFastballPA();
                hits = batter.getFastballH();
                singles = batter.getFastball1B();
                doubles = batter.getFastball2B();
                triples = batter.getFastball3B();
                homeRuns = batter.getFastballHR();
                break;
            case "Breaking":
                plateAppearances = batter.getBreakingPA();
                hits = batter.getBreakingH();
                singles = batter.getBreaking1B();
                doubles = batter.getBreaking2B();
                triples = batter.getBreaking3B();
                homeRuns = batter.getBreakingHR();
                break;
            case "Offspeed":
                plateAppearances = batter.getOffspeedPA();
                hits = batter.getOffspeedH();
                singles = batter.getOffspeed1B();
                doubles = batter.getOffspeed2B();
                triples = batter.getOffspeed3B();
                homeRuns = batter.getOffspeedHR();
                break;
            default:
                plateAppearances = batter.getTotalPA();
                hits = batter.getTotalH();
                singles = batter.getTotal1B();
                doubles = batter.getTotal2B();
                triples = batter.getTotal3B();
                homeRuns = batter.getTotalHR();
        }

        if (plateAppearances <= 0){
            outs++;
            return 0;
        }

        double hitRate = (double) hits / plateAppearances;
        if (random.nextDouble() >= hitRate) {
            outs++;
            return 0;
        }

        if (hits < 0) {
            return 0;
        }

        this.hits++;

        double rand = random.nextDouble();
        double singleProb = (double) singles / hits;
        double doubleProb = (double) doubles / hits;
        double tripleProb = (double) triples / hits;

        if (rand < singleProb) {
            this.singles++;
            return advanceRunners(Hits.SINGLE);
        } else if (rand < singleProb + doubleProb) {
            this.doubles++;
            return advanceRunners(Hits.DOUBLE);
        } else if (rand < singleProb + doubleProb + tripleProb) {
            this.triples++;
            return advanceRunners(Hits.TRIPLE);
        } else {
            this.homeRuns++;
            return advanceRunners(Hits.HR);
        }
    }

    /**
     * Calculate and update the bases result based on the hit type.
     * @param hitType The type of hit
     * @return The number of scores made by the hit
     */
    protected int advanceRunners(Hits hitType) {
        int runs = 0;

        switch (hitType) {
            case HR:
                runs = bases[1] + bases[2] + bases[3] + 1;
                bases[1] = bases[2] = bases[3] = 0;
                break;
            case TRIPLE:
                runs = bases[1] + bases[2] + bases[3];
                bases[3] = 1; // Batter on third
                bases[1] = bases[2] = 0;
                break;
            case DOUBLE:
                runs = bases[2] + bases[3]; // 2nd, 3rd score
                bases[3] = bases[1]; // 1st go 3rd
                bases[2] = 1;
                bases[1] = 0;
                break;
            case SINGLE:
                runs = bases[3]; // batter on 3rd base score
                bases[3] = bases[2]; // 2nd go 3rd
                bases[2] = bases[1]; // 1st go 2nd
                bases[1] = 1;
                break;
        }
        return runs;
    }

    /**
     * Simulate the batter gets the walk. Update and calculate the bases.
     * @return The number of scores made by the walk
     */
    protected int advanceRunnersOnWalk() {
        int runs = 0;

        if (bases[1] == 1 && bases[2] == 1 && bases[3] == 1) {
            runs = 1;
        } else if (bases[2] == 1 && bases [1] == 1) {
            bases[3] = 1;
        } else if (bases[1] == 1){
            bases[2] = 1;
        }

        bases[1] = 1;
        return runs;
    }

    /**
     * Get the number of batters that the pitcher faced in this inning.
     * @return The number of batters
     */
    public int getBattersFaced() {
        return battersFaced;
    }

    /**
     * Get the current batter index of the lineup.
     * @return The current batter index
     */
    public int getCurrentBatterIndex() {
        return currentBatterIndex;
    }

    /**
     * Get the pitches thrown in the inning.
     * @return The number of pitches
     */
    public int getPitchesThrown() {
        return pitchesThrown;
    }

    /**
     * Get the count of pitch type thrown in the inning.
     * @param pitchType The type of pitches to count
     * @return The number of times the specified pitch type was thrown
     */
    public int getPitchTypeCount(String pitchType) {
        return pitchTypeCounts.getOrDefault(pitchType, 0);
    }

    /**
     * Get the counts of pitch type thrown in the inning.
     * @return A map of pitch types to their counts
     */
    public Map<String, Integer> getAllPitchTypeCounts() {
        return new HashMap<>(pitchTypeCounts);
    }

    /**
     * Get the counts of pitch categories thrown in the inning.
     * @return A map of pitch categories to their counts
     */
    public Map<String, Integer> getPitchCategoryCounts() {
        return new HashMap<>(pitchCategoryCounts);
    }

    /**
     * Get the number of hits.
     * @return The number of hits
     */
    public int getHits() {
        return hits;
    }

    /**
     * Get the number of singles.
     * @return The number of singles
     */
    public int getSingles() {
        return singles;
    }

    /**
     * Get the number of doubles.
     * @return The number of doubles
     */
    public int getDoubles() {
        return doubles;
    }

    /**
     * Get the number of triples.
     * @return The number of triples
     */
    public int getTriples() {
        return triples;
    }

    /**
     * Get the number of home runs.
     * @return The number of home runs
     */
    public int getHomeRuns() {
        return homeRuns;
    }

    /**
     * Get the number of strikeouts.
     * @return The number of strikeouts
     */
    public int getStrikeouts() {
        return strikeouts;
    }

    /**
     * Get the batter walk on base count.
     * @return The counts of walks
     */
    public int getWalks() {
        return walks;
    }
}
