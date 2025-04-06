package model.inning;

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
    private int[] bases = new int[4];
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

    public RegularInning(Pitcher pitcher) {
        this.currentPitcher = pitcher;
        this.bases = new int[]{0, 0, 0, 0};
        this.battersFaced = 0;
    }

    @Override
    public void resetInning() {
        this.outs = 0;
        this.strikes = 0;
        this.balls = 0;
        this.bases = new int[]{0, 0, 0, 0};
    }

    @Override
    public int runInning(List<Batter> lineup) {
        resetInning();
        this.lineup = lineup;
        this.currentBatterIndex = 0;
        int score = 0;

        while (outs < 3) {
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
            if (strikes >= 3) {
                outs++;
                return 0;
            } else if (balls >= 4) {
                // Walk
                return advanceRunners("BB");
            }
        }
    }


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

    private String getPitchCategory(String pitchName) {
        for (Map.Entry<String, List<String>> entry : pitchTypeMap.entrySet()) {
            if (entry.getValue().contains(pitchName)) {
                return entry.getKey();
            }
        }
        return "Fastball";
    }

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
            return advanceRunners("1B");
        } else if (rand < singleProb + doubleProb) {
            return advanceRunners("2B");
        } else if (rand < singleProb + doubleProb + tripleProb) {
            return advanceRunners("3B");
        } else {
            return advanceRunners("HR");
        }
    }

    private int advanceRunners(String hitType) {
        int runs = 0;

        switch (hitType) {
            case "HR":
                runs = bases[1] + bases[2] + bases[3] + 1;
                bases[1] = bases[2] = bases[3] = 0;
                break;
            case "3B":
                runs = bases[1] + bases[2] + bases[3];
                bases[3] = 1; // Batter on third
                bases[1] = bases[2] = 0;
                break;
            case "2B":
                runs = bases[2] + bases[3]; // 2nd, 3rd score
                bases[3] = bases[1]; // 1st go 3rd
                bases[2] = 1;
                bases[1] = 0;
                break;
            case "1B":
                runs = bases[3]; // batter on 3rd base score
                bases[3] = bases[2]; // 2nd go 3rd
                bases[2] = bases[1]; // 1st go 2nd
                bases[1] = 1;
                break;
            case "BB":
                if (bases[1] == 1 && bases[2] == 1 && bases[3] == 1) {
                    runs = 1;
                } else {
                    if (bases[1] == 1) {
                        if (bases[2] == 1) {
                            if (bases[3] == 1) {
                                runs = 1; // Force run in
                            } else {
                                bases[3] = 1;
                            }
                        } else {
                            bases[2] = 1;
                        }
                    }
                }
                bases[1] = 1; // Batter to first on walk
                break;
                }
        return runs;
    }

    public int getBattersFaced() {
        return battersFaced;
    }

    public int getCurrentBatterIndex() {
        return currentBatterIndex;
    }

}
