package model.simulation;


import model.inning.RegularInning;
import model.player.Batter;
import model.player.Pitcher;
import model.team.ComTeam;
import model.team.PlayerTeam;

import java.util.ArrayList;
import java.util.List;

public class Simulation {
    private PlayerTeam playerTeam;
    private ComTeam comTeam;
    private int[] inningScores;
    private int currentInning;
    private int currentBatterIndex;

    /**
     * Constructor of simulation class.
     * @param playerTeam The player team
     * @param comTeam The computer team
     */
    public Simulation(PlayerTeam playerTeam, ComTeam comTeam) {
        this.playerTeam = playerTeam;
        this.comTeam = comTeam;
        this.inningScores = new int[9];
        this.currentInning = 1;
        this.currentBatterIndex = 0;
    }

    /**
     * Get the player team name.
     * @return The player team name
     */
    public String getPlayerTeamName() {
        return playerTeam.getTeamName();
    }

    /**
     * Run the simulation and return result.
     * @return SimulationResult contains result
     */
    public SimulationResult runSimulation() {
        validateTeamsBeforeSimulation();
        SimulationResult result = new SimulationResult(playerTeam.getTeamName(), comTeam.getTeamName());
        StringBuilder details = new StringBuilder();

        // simulate 9 innings
        for (currentInning = 1; currentInning <= 9; currentInning++) {
            Pitcher currentPitcher = getCurrentPitcher(currentInning);
            RegularInning inning = new RegularInning(currentPitcher);

            details.append("Inning ").append(currentInning).append(":\n");
            details.append("Pitcher: ").append(currentPitcher.getName()).append("\n");

            // Pass current batter index and get inning score
            int inningScore = inning.runInning(playerTeam, currentBatterIndex);
            inningScores[currentInning - 1] = inningScore;

            // Track batter index for next inning
            currentBatterIndex = inning.getCurrentBatterIndex();

            details.append("Batters faced: ").append(inning.getBattersFaced()).append("\n");
            details.append("Score: ").append(inningScore).append("\n");

            details.append("Pitches: ").append(inning.getPitchesThrown()).append("\n");
            details.append("Hits: ").append(inning.getHits()).append("\n");
            details.append("Strikeouts: ").append(inning.getStrikeouts()).append("\n");
            details.append("Walks: ").append(inning.getWalks()).append("\n\n");

            result.addPitchesThrown(inning.getPitchesThrown());
            result.addPitchTypeCounts(inning.getAllPitchTypeCounts());
            result.addPitchCategoryCounts(inning.getPitchCategoryCounts());
            result.addHits(inning.getHits());
            result.addSingles(inning.getSingles());
            result.addDoubles(inning.getDoubles());
            result.addTriples(inning.getTriples());
            result.addHR(inning.getHomeRuns());
            result.addStrikeouts(inning.getStrikeouts());
            result.addWalks(inning.getWalks());

        }
        // set result
        result.setPlayerTeamScore(calculateTotalScore());
        result.setDetails(details.toString());
        result.setInningScores(inningScores);

        return result;
    }

    /**
     * Set the pitcher based on innings.
     * @return The pitcher for current inning
     */
    private Pitcher getCurrentPitcher(int inning) {
        List<Pitcher> pitcherLineup = comTeam.getPitcherLineup();

        if (inning <= 5) {
            return pitcherLineup.get(0);
        } else if (inning <= 7) {
            return pitcherLineup.get(1);
        } else {
            return pitcherLineup.get(2);
        }
    }

    private void validateTeamsBeforeSimulation() {
        // Check if batting lineup is complete
        if (playerTeam.getBatterLineup().contains(null)) {
            throw new IllegalStateException("Player team must have a complete batting lineup of 9 players");
        }

        // Check if pitching lineup is complete
        if (comTeam.getPitcherLineup().contains(null)) {
            throw new IllegalStateException("Computer team must have a complete pitching lineup of 3 pitchers");
        }
    }

    /**
     * Calculate all innings score.
     * @return The Mariners' total score
     */
    private int calculateTotalScore() {
        int total = 0;
        for (int score : inningScores) {
            total += score;
        }
        return total;
    }
}
