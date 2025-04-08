package model.simulation;

import gameEnum.Teams;

import java.util.HashMap;
import java.util.Map;

public class SimulationResult {
    private int playerTeamScore;
    private String playerTeamName;
    private String details;
    private int[] inningScores;
    private Teams opponentTeam;


    private int totalPitchesThrown = 0;
    private Map<String, Integer> pitchTypeCounts = new HashMap<>();
    private Map<String, Integer> pitchCategoryCounts = new HashMap<>();
    private int totalHits = 0;
    private int totalSingles = 0;
    private int totalDoubles = 0;
    private int totalTriples = 0;
    private int totalHomeRuns = 0;
    private int totalStrikeouts = 0;
    private int totalWalks = 0;

    /**
     * Default constructor for SimulationResult.
     */
    public SimulationResult() {
        this.playerTeamScore = 0;
        this.details = "";
        this.inningScores = new int[9];
        this.opponentTeam = null;
    }

    /**
     * Constructor with opponent team
     * @param opponentTeam The opponent team enum
     */
    public SimulationResult(Teams opponentTeam) {
        this.opponentTeam = opponentTeam;
    }

    public void setPlayerTeamName(String name) {
        this.playerTeamName = name;
    }

    /**
     * Set the Mariners total score.
     * @param score The total score for all innings
     */
    public void setPlayerTeamScore(int score) {
        this.playerTeamScore = score;
    }

    /**
     * Get the Mariners total score.
     * @return The total score for all innings
     */
    public int getPlayerTeamScore() {
        return playerTeamScore;
    }

    /**
     * Set the details of the game
     * @param details The game detail string
     */
    public void setDetails(String details) {
        this.details = details;
    }

    /**
     * Get the details of the game
     * @return The game detail string
     */
    public String getDetails() {
        return details;
    }

    /**
     * Set the inning score.
     * @param scores Arrays of score for innings
     */
    public void setInningScores(int[] scores) {
        this.inningScores = scores;
    }

    /**
     * Get the inning score.
     * @return Arrays of score for innings
     */
    public int[] getInningScores() {
        return inningScores;
    }

    /**
     * Set the opponent team.
     * @param team The opponent team enum
     */
    public void setOpponentTeam(Teams team) {
        this.opponentTeam = team;
    }

    /**
     * Get the opponent team.
     * @return The opponent team enum
     */
    public Teams getOpponentTeam() {
        return opponentTeam;
    }

    /**
     * Add pitch type counts from an inning to the total counts.
     * @param counts A map of pitch types to their counts
     */
    public void addPitchTypeCounts(Map<String, Integer> counts) {
        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
            String pitchType = entry.getKey();
            int count = entry.getValue();
            pitchTypeCounts.put(pitchType, pitchTypeCounts.getOrDefault(pitchType, 0) + count);
        }
    }

    /**
     * Add pitch category counts from an inning to the total counts.
     * @param counts A map of pitch categories to their counts
     */
    public void addPitchCategoryCounts(Map<String, Integer> counts) {
        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
            String category = entry.getKey();
            int count = entry.getValue();
            pitchCategoryCounts.put(category, pitchCategoryCounts.getOrDefault(category, 0) + count);
        }
    }

    /**
     * Calculate the thrown pitches.
     * @return The thrown pitches
     */
    public void addPitchesThrown(int pitches) {
        this.totalPitchesThrown += pitches;
    }

    /**
     * Calculate the total hits.
     * @param hits The total hits
     */
    public void addHits(int hits) {
        this.totalHits += hits;
    }

    /**
     * Calculate the total singles.
     * @param singles The total singles
     */
    public void addSingles(int singles) {
        this.totalSingles += singles;
    }

    /**
     * Calculate the total doubles.
     * @param doubles The total doubles
     */
    public void addDoubles(int doubles) {
        this.totalDoubles += doubles;
    }

    /**
     * Calculate the total triples.
     * @param triples The total triples
     */
    public void addTriples(int triples) {
        this.totalTriples += triples;
    }

    /**
     * Calculate the total homers.
     * @param homeRuns The total homers
     */
    public void addHR(int homeRuns) {
        this.totalHomeRuns += homeRuns;
    }

    /**
     * Calculate the total strikeouts.
     * @param strikeOuts The total strikeouts
     */
    public void addStrikeouts(int strikeOuts) {
        this.totalStrikeouts += strikeOuts;
    }

    /**
     * Calculate the total walks.
     * @param walks The total walks
     */
    public void addWalks(int walks) {
        this.totalWalks += walks;
    }

    /**
     * Getter of total pitches thrown.
     * @return The total of pitches has thrown
     */
    public int getTotalPitchesThrown() {
        return totalPitchesThrown;
    }

    /**
     * Get a map of pitch types and their counts for the entire game.
     * @return A map of pitch type and counts
     */
    public Map<String, Integer> getPitchTypeCounts() {
        return pitchTypeCounts;
    }

    /**
     * Get a map of pitch categories and their counts for the entire game.
     * @return A map of pitch category and counts
     */
    public Map<String, Integer> getPitchCategoryCounts() {
        return pitchCategoryCounts;
    }

    /**
     * Getter of the total hits made by batters.
     * @return The total hits
     */
    public int getTotalHits() {
        return totalHits;
    }

    /**
     * Getter of the total singles.
     * @return The total singles
     */
    public int getTotalSingles() {
        return totalSingles;
    }

    /**
     * Getter of the total doubles
     * @return The total doubles
     */
    public int getTotalDoubles() {
        return totalDoubles;
    }

    /**
     * Getter of the total triples
     * @return The total triples
     */
    public int getTotalTriples() {
        return totalTriples;
    }

    /**
     * Getter of the total homers.
     * @return The total homers
     */
    public int getTotalHomeRuns() {
        return totalHomeRuns;
    }

    /**
     * Getter of the total strikeouts.
     * @return The total strikeouts
     */
    public int getTotalStrikeouts() {
        return totalStrikeouts;
    }

    /**
     * Getter of the total walks.
     * @return The total walks
     */
    public int getTotalWalks() {
        return totalWalks;
    }

    /**
     * Generate a detailed statistics report for the entire game.
     * @return A formatted string containing all game statistics
     */
    public String getTotalStatistics() {
        StringBuilder sb = new StringBuilder();
        sb.append("=====Game Statistics=====\n\n");

        sb.append("Pitching Statistics:\n");
        sb.append("Total Pitches: ").append(totalPitchesThrown).append("\n");
        sb.append("Pitch Types: \n");
        pitchTypeCounts.forEach((type, counts) -> {
            sb.append((String.format(" %-12s: %d\n",
                    type, counts)));
        });
        sb.append("Pitch Categories: \n");
        pitchCategoryCounts.forEach((category, counts) -> {
            sb.append((String.format(" %-12s: %d\n",
                    category, counts)));
        });
        sb.append("\n");

        sb.append("Batting Statistics:\n");
        sb.append("Total Hits: ").append(totalHits).append("\n");
        sb.append("Singles: ").append(totalSingles).append("\n");
        sb.append("Doubles: ").append(totalDoubles).append("\n");
        sb.append("Triples: ").append(totalTriples).append("\n");
        sb.append("Home Runs: ").append(totalHomeRuns).append("\n");
        sb.append("Strikeouts: ").append(totalStrikeouts).append("\n");
        sb.append("Walks: ").append(totalWalks).append("\n");

        return sb.toString();
    }

    /**
     * Get the total score format.
     * @return The total score string
     */
    public String getTotalScore() {
        StringBuilder sb = new StringBuilder();
        sb.append("Inning:   ");
        for (int i = 1; i <= 9; i++) {
            sb.append(i).append("  ");
        }
        sb.append("Runs\n");

        sb.append(playerTeamName).append(": ");
        for (int score : inningScores) {
            sb.append(score).append("  ");
        }
        sb.append(playerTeamScore).append("\n");

        return sb.toString();
    }

    /**
     * Info of the game result.
     * @return A string of game result
     */
    @Override
    public String toString() {
        return String.format("%s Final Score: %d\n%s", playerTeamName, playerTeamScore, getTotalScore());
    }
}
