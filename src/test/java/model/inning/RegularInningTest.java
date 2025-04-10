package model.inning;

import static org.junit.jupiter.api.Assertions.*;

import gameEnum.Hits;
import gameEnum.Teams;
import model.team.PlayerTeam;
import model.team.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.player.Batter;
import model.player.Pitcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class RegularInningTest {
    private RegularInning inning;
    private Pitcher pitcher;
    private Team team;
    private List<Batter> batters;

    @BeforeEach
    void setUp() {
        pitcher = new Pitcher("Test Pitcher", 1, 100, 150, 0.65, 0.35,
                0.30, 0.20, 0.15, 0.05, 0.10, 0.05,
                0.00, 0.05, 0.00, 0.05, 0.05, 0.00, 0.00);

        inning = new RegularInning(pitcher);
        team = new PlayerTeam(Teams.MARINERS);

        // Create a lineup of 9 test batters
        batters = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            Batter batter = new Batter(
                    "Batter" + i, 80, 20, 10, 5, 2,
                    3, 90, 20, 10, 5, 2, 3,
                    100, 20, 10, 5, 3, 2,
                    300, 70, 40, 15, 7, 8, 0.65, 0.80,
                    0.30, 0.40, 0.420, 0.450, 0.890);
            batters.add(batter);
        }

        for (int i = 0; i < 9; i++) {
            team.getBatterLineup().set(i, batters.get(i));
        }
    }

    @Test
    void testResetInning() {
        inning.runInning(team, 0);
        inning.resetInning();

        assertEquals(0, inning.getPitchesThrown());
        assertEquals(0, inning.getHits());
        assertEquals(0, inning.getSingles());
        assertEquals(0, inning.getDoubles());
        assertEquals(0, inning.getTriples());
        assertEquals(0, inning.getHomeRuns());
        assertEquals(0, inning.getStrikeouts());
        assertEquals(0, inning.getWalks());
        assertEquals(0, inning.getBattersFaced());

        // Verify that pitch type counts are reset
        assertTrue(inning.getAllPitchTypeCounts().isEmpty());
        assertTrue(inning.getPitchCategoryCounts().isEmpty());
    }

    @Test
    void testRunInning() {
        int score = inning.runInning(team, 0);

        // Verify that the inning stats will be calculated
        assertTrue(inning.getBattersFaced() >= 3);
        assertTrue(inning.getPitchesThrown() > 0);
        assertTrue(score >= 0);
    }

    @Test
    void testPitchTypeDetermination() {
        inning.runInning(team, 0);

        // Get pitch type counts
        Map<String, Integer> pitchTypeCounts = inning.getAllPitchTypeCounts();
        Map<String, Integer> pitchCategoryCounts = inning.getPitchCategoryCounts();

        // Verify that some pitches were thrown
        assertTrue(inning.getPitchesThrown() > 0);

        // Verify that the sum of all pitch type counts equals total pitches thrown
        int totalPitchTypeCount = pitchTypeCounts.values().stream().mapToInt(Integer::intValue).sum();
        assertEquals(inning.getPitchesThrown(), totalPitchTypeCount);

        // Verify that the sum of all pitch category counts equals total pitches thrown
        int totalPitchCategoryCount = pitchCategoryCounts.values().stream().mapToInt(Integer::intValue).sum();
        assertEquals(inning.getPitchesThrown(), totalPitchCategoryCount);
    }

    @Test
    void testHitOutcome() {
        // Run full game
        int totalScore = 0;
        for (int i = 0; i < 9; i++) {
            totalScore += inning.runInning(team, 0);
            inning.resetInning();
        }

        assertTrue(inning.getHits() >= 0);
        int hitTypeSum = inning.getSingles() + inning.getDoubles() + inning.getTriples() + inning.getHomeRuns();
        assertEquals(inning.getHits(), hitTypeSum);

        assertTrue(totalScore >= 0);
    }

    @Test
    void testBaseWithSingles() {
        setPrivateField(inning, "bases", new int[]{0, 0, 0, 1});

        int score = inning.advanceRunners(Hits.SINGLE);
        assertEquals(1, score);

        int[] bases = getPrivateField(inning, "bases");
        assertEquals(1, bases[1]);
        assertEquals(0, bases[2]);
        assertEquals(0, bases[3]);
    }

    @Test
    void testBaseWithDoubles() {
        setPrivateField(inning, "bases", new int[]{0, 1, 0, 1});

        int score = inning.advanceRunners(Hits.DOUBLE);
        assertEquals(1, score);

        int[] bases = getPrivateField(inning, "bases");
        assertEquals(0, bases[1]);
        assertEquals(1, bases[2]);
        assertEquals(1, bases[3]);
    }

    @Test
    void testBaseWithTriples() {
        setPrivateField(inning, "bases", new int[]{0, 1, 0, 1});

        int score = inning.advanceRunners(Hits.TRIPLE);
        assertEquals(2, score);

        int[] bases = getPrivateField(inning, "bases");
        assertEquals(0, bases[1]);
        assertEquals(0, bases[2]);
        assertEquals(1, bases[3]);
    }

    @Test
    void testBaseWithHR() {
        setPrivateField(inning, "bases", new int[]{0, 1, 1, 1});

        int score = inning.advanceRunners(Hits.HR);
        assertEquals(4, score);

        int[] bases = getPrivateField(inning, "bases");
        assertEquals(0, bases[1]);
        assertEquals(0, bases[2]);
        assertEquals(0, bases[3]);
    }

    @Test
    void testAdvanceRunnersOnWalkWithBaseLoad() {
        setPrivateField(inning, "bases", new int[]{0, 1, 1, 1});

        int score = inning.advanceRunnersOnWalk();
        assertEquals(1, score);

        int[] bases = getPrivateField(inning, "bases");
        assertEquals(1, bases[1]);
        assertEquals(1, bases[2]);
        assertEquals(1, bases[3]);
    }

    @Test
    void testAdvanceRunnersOnWalkWithFirstSecondLoad() {
        setPrivateField(inning, "bases", new int[]{0, 1, 1, 0});

        int score = inning.advanceRunnersOnWalk();
        assertEquals(0, score);

        int[] bases = getPrivateField(inning, "bases");
        assertEquals(1, bases[1]);
        assertEquals(1, bases[2]);
        assertEquals(1, bases[3]);
    }

    @Test
    void testAdvanceRunnersOnWalk() {
        setPrivateField(inning, "bases", new int[]{0, 0, 0, 1});

        int score = inning.advanceRunnersOnWalk();
        assertEquals(0, score);

        int[] bases = getPrivateField(inning, "bases");
        assertEquals(1, bases[1]);
        assertEquals(0, bases[2]);
        assertEquals(1, bases[3]);
    }

    @SuppressWarnings("unchecked")
    private <T> void setPrivateField(Object object, String fieldName, T value) {
        try {
            java.lang.reflect.Field field = RegularInning.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(object, value);
        } catch (Exception e) {
            fail(fieldName + ": " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private <T> T getPrivateField(Object object, String fieldName) {
        try {
            java.lang.reflect.Field field = RegularInning.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            return (T) field.get(object);
        } catch (Exception e) {
            fail(fieldName + ": " + e.getMessage());
            return null;
        }
    }
}