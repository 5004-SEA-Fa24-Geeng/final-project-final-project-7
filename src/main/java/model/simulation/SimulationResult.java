package model.simulation;

public class SimulationResult {
    private int marinersScore;
    private int opponentScore;
    private String details;
    private int[] inningScores;

    public SimulationResult() {
        this.marinersScore = 0;
        this.opponentScore = 0;
        this.details = "";
        this.inningScores = new int[9];
    }

    public void setMarinersScore(int score) {
        this.marinersScore = marinersScore;
    }

    public int getMarinersScore() {
        return marinersScore;
    }

    public void setOpponentScore(int score) {
        this.opponentScore = score;
    }

    public int getOpponentScore() {
        return opponentScore;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDetails() {
        return details;
    }

    public void setInningScores(int[] scores) {
        this.inningScores = scores;
    }

    public int[] getInningScores() {
        return inningScores;
    }

    public String getTotalScore() {
        StringBuilder sb = new StringBuilder();
        sb.append("Inning: ");
        for (int i = 1; i <= 9; i++) {
            sb.append(i).append("  ");
        }
        sb.append("Runs\n");

        sb.append("Mariners: ");
        for (int score : inningScores) {
            sb.append(score).append("  ");
        }
        sb.append(marinersScore).append("\n");

        return sb.toString();
    }

    @Override
    public String toString() {
        return "Mariners score: " + marinersScore + "\n" + getTotalScore();
    }
}
