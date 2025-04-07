package model.simulation;

import gameEnum.Teams;

public class SimulationResult {
    private int marinersScore;
    private int opponentScore;
    private String details;
    private int[] inningScores;
    private Teams opponentTeam;

    /**
     * Default constructor for SimulationResult.
     */
    public SimulationResult() {
        this.marinersScore = 0;
        this.opponentScore = 0;
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

    /**
     * Set the Mariners total score.
     * @param score The total score for all innings
     */
    public void setMarinersScore(int score) {
        this.marinersScore = marinersScore;
    }

    /**
     * Get the Mariners total score.
     * @return The total score for all innings
     */
    public int getMarinersScore() {
        return marinersScore;
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
     * Get the total score format.
     * @return The total score string
     */
    public String getTotalScore() {
        StringBuilder sb = new StringBuilder();
        sb.append("Inning: ");
        for (int i = 1; i <= 9; i++) {
            sb.append(i).append("  ");
        }
        sb.append("Runs\n");

        sb.append(Teams.MARINERS.name()).append(": ");
        for (int score : inningScores) {
            sb.append(score).append("  ");
        }
        sb.append(marinersScore).append("\n");

        return sb.toString();
    }

    /**
     * Info of the game result.
     * @return A string of game result
     */
    @Override
    public String toString() {
        return "Mariners score: " + marinersScore + "\n" + getTotalScore();
    }
}
