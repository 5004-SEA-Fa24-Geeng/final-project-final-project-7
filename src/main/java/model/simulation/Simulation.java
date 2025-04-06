package model.simulation;

import model.inning.RegularInning;
import model.player.Batter;
import model.player.Pitcher;
import model.team.ComTeam;
import model.team.PlayerTeam;

import java.util.ArrayList;
import java.util.List;

public class Simulation {
    private PlayerTeam mariners;
    private ComTeam opponent;
    private int[] inningScores;
    private int currentInning;
    private int currentBatterIndex;
    private List<Pitcher> selectedPitchers;


    public Simulation(PlayerTeam mariners, ComTeam opponent) {
        this.mariners = mariners;
        this.opponent = opponent;
        this.inningScores = new int[9];
        this.currentInning = 1;
        this.currentBatterIndex = 0;
        this.selectedPitchers = new ArrayList<>();
    }

    public void selectedPitcher(Pitcher starter, Pitcher reliever1, Pitcher reliever2) {
        if (starter.getRotation() != 1) {
            throw new IllegalArgumentException("First pitcher must be in rotation 1!");
        }

        if (reliever1.getRotation() != 2 || reliever2.getRotation() != 2) {
            throw new IllegalArgumentException("Relieve pitcher must be in rotation 2!");
        }

        selectedPitchers.clear();
        selectedPitchers.add(starter);
        selectedPitchers.add(reliever1);
        selectedPitchers.add(reliever2);
    }

    public SimulationResult runSimulation() {
        if (selectedPitchers.size() != 3) {
            throw new IllegalArgumentException("Must select 3 pitcher!");
        }

        List<Batter> lineup = getMarinersLineup();
        if (lineup.size() != 9) {
            throw new IllegalStateException("Lineup must contain exactly 9 batters");
        }

        SimulationResult result = new SimulationResult();
        StringBuilder details = new StringBuilder();

        // simulate 9 innings
        for (currentInning = 1; currentInning <= 9; currentInning++) {
            Pitcher currentPitcher = getCurrentPitcher();

            details.append("Inning ").append(currentInning).append(":\n");
            details.append("Pitcher: ").append(currentPitcher.getName()).append("\n");

            RegularInning inning = new RegularInning(currentPitcher);

            List<Batter> currentInningLinup = new ArrayList<>();
            for (int i = 0; i < lineup.size(); i++) {
                int index = (currentBatterIndex + i) % lineup.size();
                currentInningLinup.add(lineup.get(index));
            }

            int inningScore = inning.runInning(currentInningLinup);
            inningScores[currentInning - 1] = inningScore;

            // next batter index
            currentBatterIndex = (currentBatterIndex + inning.getBattersFaced()) % lineup.size();

            details.append("Batters faced: ").append(inning.getBattersFaced()).append("\n");
            details.append("Score: ").append(inningScore).append("\n");

        }
        // set result
        result.setMarinersScore(calculateTotalScore());
        result.setDetails(details.toString());
        result.setInningScores(inningScores);

        return result;
    }

    private Pitcher getCurrentPitcher() {
        if (currentInning <= 5) {
            return selectedPitchers.get(0);
        } else if (currentInning <= 7) {
            return selectedPitchers.get(1);
        } else {
            return selectedPitchers.get(2);
        }
    }

    private List<Batter> getMarinersLineup() {
        List<Batter> lineup = new ArrayList<>();
        for (var player : mariners.getPlayers()) {
            if (player instanceof Batter) {
                lineup.add((Batter) player);
                if (lineup.size() == 9) break;
            }
        }
        return lineup;
    }


    private int calculateTotalScore() {
        int total = 0;
        for (int score : inningScores) {
            total += score;
        }
        return total;
    }
}
