package model.simulation;

import org.junit.jupiter.api.BeforeEach;

import gameEnum.Teams;
import model.player.Batter;
import model.player.Pitcher;
import model.team.ComTeam;
import model.team.PlayerTeam;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SimulationTest {
    private Simulation simulation;
    private PlayerTeam playerTeam;
    private ComTeam comTeam;
    private List<Batter> batters;
    private List<Pitcher> pitchers;

    @BeforeEach
    void setUp() {
        playerTeam = new PlayerTeam(Teams.MARINERS);
        comTeam = new ComTeam(Teams.ANGELS);

        batters = createTestBatters();
        pitchers = createTestPitchers();

        // set player team and computer team
        for (int i = 0; i < 9; i++) {
            playerTeam.getBatterLineup().set(i, batters.get(i));
        }

        for (int i = 0; i < 3; i++) {
            comTeam.getPitcherLineup().set(i, pitchers.get(i));
        }

        simulation = new Simulation(playerTeam, comTeam);
    }


    @Test
    void testSimulation() {
        assertEquals(playerTeam.getTeamName(),simulation.getPlayerTeamName());
    }

    @Test
    void testRunSimulation() {
        SimulationResult result = simulation.runSimulation();

        int sumInningScores = 0;
        for (int score : result.getInningScores()) {
            sumInningScores += score;
        }

        assertNotNull(result.getTotalStatistics());
        assertNotNull(result.getInningScores());
        assertEquals(playerTeam.getTeamName(), result.getPlayerTeamName());
        assertEquals(9, result.getInningScores().length);
        assertEquals(sumInningScores, result.getPlayerTeamScore());
    }

    @Test
    void testIncompleteBatterLineup() {
        playerTeam = new PlayerTeam(Teams.MARINERS);

        // set partial lineup
        for (int i = 0; i < 5; i++) {
            playerTeam.getBatterLineup().set(i, batters.get(i));
        }

        simulation = new Simulation(playerTeam, comTeam);
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            simulation.runSimulation();
        });

        assertTrue(exception.getMessage().contains("Player team must have a complete batting lineup of 9 players"));
    }

    @Test
    void testIncompletePitcherLineup() {
        comTeam = new ComTeam(Teams.ANGELS);
        comTeam.getPitcherLineup().set(0, pitchers.get(0));
        simulation = new Simulation(playerTeam, comTeam);


        Exception exception = assertThrows(IllegalStateException.class, () -> {
            simulation.runSimulation();
        });

        assertTrue(exception.getMessage().contains("Computer team must have a complete pitching lineup of 3 pitchers"));
    }

    @Test
    void testSimulationStatics() {
        SimulationResult result = simulation.runSimulation();

        assertTrue(result.getTotalPitchesThrown() > 0);
        assertFalse(result.getPitchTypeCounts().isEmpty());
        assertFalse(result.getPitchCategoryCounts().isEmpty());

        assertTrue(result.getTotalHits() >= 0);
        assertTrue(result.getTotalSingles() + result.getTotalDoubles() +
                        result.getTotalTriples() + result.getTotalHomeRuns() == result.getTotalHits());

    }

    private List<Batter> createTestBatters() {
        List<Batter> batters = new ArrayList<>();

        for (int i = 1; i <= 9; i++) {
            double contactRate = 0.2 + (i * 0.03);

            Batter batter = new Batter(
                    "Batter " + i,
                    100, 30, 20, 5, 2, 3,
                    100, 25, 15, 5, 2, 3,
                    100, 20, 12, 4, 2, 2,
                    300, 75, 47, 14, 6, 8,
                    0.5 + (i * 0.02), contactRate, 0.3 - (i * 0.01),
                    contactRate * 0.7, 0.250 + (i * 0.01), 0.300 + (i * 0.01),
                    0.700 + (i * 0.03)
            );

            batters.add(batter);
        }

        return batters;
    }

    private List<Pitcher> createTestPitchers() {
        List<Pitcher> pitchers = new ArrayList<>();

        pitchers.add(new Pitcher(
                "Starter", 1, 100, 150, 0.65, 0.35, 0.30,
                0.20, 0.15, 0.05, 0.10, 0.05, 0.00, 0.05,
                0.00, 0.05, 0.05, 0.00, 0.00));

        pitchers.add(new Pitcher("Reliever1", 2, 80, 130, 0.5, 0.5,
                0.30, 0.20, 0.15, 0.05, 0.10, 0.05, 0.00,
                0.05, 0.00, 0.05, 0.05, 0.00, 0.00));

        pitchers.add(new Pitcher("Reliever2", 2, 100, 150, 0.4, 0.6,
                0.30, 0.20, 0.10, 0.00, 0.10, 0.00, 0.00,
                0.00, 0.10, 0.00, 0.00, 0.10, 0.10));

        return pitchers;
    }
}