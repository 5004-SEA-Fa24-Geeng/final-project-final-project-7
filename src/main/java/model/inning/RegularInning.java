package model.inning;

import gameEnum.Balls;
import gameEnum.Hits;
import gameEnum.Outs;
import gameEnum.Strikes;
import model.player.Pitcher;
import model.player.Batter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class RegularInning implements Inning{
    private int outs;
    private int strikes;
    private int balls;
    private int[] bases;
    private Random random = new Random();

    private Pitcher currentPitcher;
    private List<Batter> lineup;
    private int currentBatterIndex;
    private int battersFaced;

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
    }

    /**
     * Runs a full inning with batter lineup, ends the inning when 3 outs.
     * @param lineup List of batters
     * @return The score of this inning
     */
    @Override
    public int runInning(List<Batter> lineup) {
        resetInning();
        this.lineup = lineup;
        this.currentBatterIndex = 0;
        int score = 0;

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
                return 0;
            } else if (balls >= Balls.FOUR.ordinal() + 1) {
                // Walk
                return advanceRunnersOnWalk();
            }
        }
    }

    /**
     * Determine the pitch type of the ball thrown by pitcher.
     * @return The name of the pitch type
     */
    private String determinePitchType() {
        double rand = random.nextDouble();
        double cumProb = 0.0;

        // Check fastball pitches
        if (currentPitcher.getFourSeam() > 0) {
            cumProb += currentPitcher.getFourSeam();
            if (rand <= cumProb) return "fourSeam";
        }
        if (currentPitcher.getTwoSeam() > 0) {
            cumProb += currentPitcher.getTwoSeam();
            if (rand <= cumProb) return "twoSeam";
        }
        if (currentPitcher.getCutter() > 0) {
            cumProb += currentPitcher.getCutter();
            if (rand <= cumProb) return "cutter";
        }
        if (currentPitcher.getSinker() > 0) {
            cumProb += currentPitcher.getSinker();
            if (rand <= cumProb) return "sinker";
        }

        // Check breaking pitches
        if (currentPitcher.getSlider() > 0) {
            cumProb += currentPitcher.getSlider();
            if (rand <= cumProb) return "slider";
        }
        if (currentPitcher.getCurve() > 0) {
            cumProb += currentPitcher.getCurve();
            if (rand <= cumProb) return "curve";
        }
        if (currentPitcher.getKnuckle() > 0) {
            cumProb += currentPitcher.getKnuckle();
            if (rand <= cumProb) return "knuckle";
        }
        if (currentPitcher.getSweeper() > 0) {
            cumProb += currentPitcher.getSweeper();
            if (rand <= cumProb) return "sweeper";
        }
        if (currentPitcher.getSlurve() > 0) {
            cumProb += currentPitcher.getSlurve();
            if (rand <= cumProb) return "slurve";
        }

        // Check offspeed pitches
        if (currentPitcher.getSplitFinger() > 0) {
            cumProb += currentPitcher.getSplitFinger();
            if (rand <= cumProb) return "splitFinger";
        }
        if (currentPitcher.getChangeup() > 0) {
            cumProb += currentPitcher.getChangeup();
            if (rand <= cumProb) return "changeup";
        }
        if (currentPitcher.getFork() > 0) {
            cumProb += currentPitcher.getFork();
            if (rand <= cumProb) return "fork";
        }
        if (currentPitcher.getScrew() > 0) {
            cumProb += currentPitcher.getScrew();
            if (rand <= cumProb) return "screw";
        }

        return "fourSeam";
    }

    /**
     * Identify the thrown ball's pitch category based on pitch type name.
     * @param pitchName The specific type of name of the pitch
     * @return The category of the pitch belongs
     */
    private String getPitchCategory(String pitchName) {
        for (Map.Entry<String, List<String>> entry : pitchTypeMap.entrySet()) {
            if (entry.getValue().contains(pitchName)) {
                return entry.getKey();
            }
        }
        return "Fastball";
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

        double rand = random.nextDouble();
        double singleProb = (double) singles / hits;
        double doubleProb = (double) doubles / hits;
        double tripleProb = (double) triples / hits;

        if (rand < singleProb) {
            return advanceRunners(Hits.SINGLE);
        } else if (rand < singleProb + doubleProb) {
            return advanceRunners(Hits.DOUBLE);
        } else if (rand < singleProb + doubleProb + tripleProb) {
            return advanceRunners(Hits.TRIPLE);
        } else {
            return advanceRunners(Hits.HR);
        }
    }

    /**
     * Calculate and update the bases result based on the hit type.
     * @param hitType The type of hit
     * @return The number of scores made by the hit
     */
    private int advanceRunners(Hits hitType) {
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
    private int advanceRunnersOnWalk() {
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

}
