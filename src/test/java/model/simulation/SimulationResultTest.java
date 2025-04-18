package model.simulation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SimulationResultTest {
    private SimulationResult result;
    private String playerTeamName = "Mariners";
    private String comTeamName = "Angles";

    @BeforeEach
    void setUp() {
        result = new SimulationResult(playerTeamName, comTeamName);
    }

    @Test
    void testSimulationResult() {
        assertEquals(playerTeamName, result.getPlayerTeamName());
        assertEquals(comTeamName, result.getComTeamName());
        assertEquals("", result.getDetails());
        assertEquals(0, result.getPlayerTeamScore());
        assertEquals(9, result.getInningScores().length);
    }

    @Test
    void testPlayerTeamScore() {
        int score = 5;
        result.setPlayerTeamScore(score);
        assertEquals(score, result.getPlayerTeamScore());
    }

    @Test
    void testDetails() {
        String details = "Game Result";
        result.setDetails(details);
        assertEquals(details, result.getDetails());
    }

    @Test
    void testInningScores() {
        int[] scores = {1, 0, 3, 0, 0, 0, 1, 0, 0};
        result.setInningScores(scores);
        assertEquals(scores, result.getInningScores());
    }

    @Test
    void testPitcherThrown() {
        result.addPitchesThrown(10);
        result.addPitchesThrown(15);
        assertEquals(25, result.getTotalPitchesThrown());
    }

    @Test
    void testAddPitchTypeCounts() {
        Map<String, Integer> counts1 = new HashMap<>();
        counts1.put("fourSeam", 5);
        counts1.put("slider", 3);

        Map<String, Integer> counts2 = new HashMap<>();
        counts2.put("fourSeam", 2);
        counts2.put("curve", 4);

        result.addPitchTypeCounts(counts1);
        result.addPitchTypeCounts(counts2);

        Map<String, Integer> pitchTypeCount = result.getPitchTypeCounts();
        Integer fourSeamCount = pitchTypeCount.get("fourSeam");
        Integer sliderCount = pitchTypeCount.get("slider");
        Integer curveCount = pitchTypeCount.get("curve");

        assertEquals(7, fourSeamCount);
        assertEquals(3, sliderCount);
        assertEquals(4, curveCount);
    }

    @Test
    void testAddPitchCategoryCounts() {
        Map<String, Integer> counts1 = new HashMap<>();
        counts1.put("Fastball", 8);
        counts1.put("Breaking", 4);

        Map<String, Integer> counts2 = new HashMap<>();
        counts2.put("Fastball", 5);
        counts2.put("Offspeed", 6);

        result.addPitchCategoryCounts(counts1);
        result.addPitchCategoryCounts(counts2);

        Map<String, Integer> pitchCategoryCount = result.getPitchCategoryCounts();
        Integer fastballCount = pitchCategoryCount.get("Fastball");
        Integer breakingCount = pitchCategoryCount.get("Breaking");
        Integer offspeedCount = pitchCategoryCount.get("Offspeed");

        assertEquals(13, fastballCount);
        assertEquals(4, breakingCount);
        assertEquals(6, offspeedCount);
    }

    @Test
    void testAddHitStatistics() {
        result.addHits(10);
        result.addSingles(4);
        result.addDoubles(3);
        result.addTriples(2);
        result.addHR(1);

        assertEquals(10, result.getTotalHits());
        assertEquals(4, result.getTotalSingles());
        assertEquals(3, result.getTotalDoubles());
        assertEquals(2, result.getTotalTriples());
        assertEquals(1, result.getTotalHomeRuns());
    }

    @Test
    void testAddStrikesWalks() {
        result.addStrikeouts(10);
        result.addWalks(2);

        assertEquals(10, result.getTotalStrikeouts());
        assertEquals(2, result.getTotalWalks());
    }

    @Test
    void testTotalStatistics() {
        result.addPitchesThrown(40);
        result.addHits(10);
        result.addSingles(4);
        result.addDoubles(3);
        result.addTriples(2);
        result.addHR(1);
        result.addStrikeouts(10);
        result.addWalks(2);

        Map<String, Integer> pitchType = new HashMap<>();
        pitchType.put("fourSeam", 20);
        pitchType.put("twoSeam", 10);
        pitchType.put("slider", 8);
        pitchType.put("screw", 2);
        result.addPitchTypeCounts(pitchType);

        Map<String, Integer> pitchCategory = new HashMap<>();
        pitchCategory.put("Fastball", 30);
        pitchCategory.put("Breaking", 8);
        pitchCategory.put("Offspeed", 2);
        result.addPitchCategoryCounts(pitchCategory);

        String stats = result.getTotalStatistics();
        assertTrue(stats.contains("Total Pitches: 40"));
        assertTrue(stats.contains("Total Hits: 10"));
        assertTrue(stats.contains("Singles: 4"));
        assertTrue(stats.contains("Doubles: 3"));
        assertTrue(stats.contains("Triples: 2"));
        assertTrue(stats.contains("Home Runs: 1"));
        assertTrue(stats.contains("Strikeouts: 10"));
        assertTrue(stats.contains("Walks: 2"));
        assertTrue(stats.contains("fourSeam"));
        assertTrue(stats.contains("twoSeam"));
        assertTrue(stats.contains("slider"));
        assertTrue(stats.contains("screw"));
        assertTrue(stats.contains("Fastball"));
        assertTrue(stats.contains("Breaking"));
        assertTrue(stats.contains("Offspeed"));
    }

    @Test
    void testGetTotalScore() {
        int[] scores = {1, 0, 2, 0, 0, 3, 0, 1, 0};
        result.setInningScores(scores);
        result.setPlayerTeamScore(7);

        String scoreStr = result.getTotalScore();

        assertTrue(scoreStr.contains("Inning:"));
        assertTrue(scoreStr.contains(playerTeamName));
        assertTrue(scoreStr.contains("7"));

        for (int score : scores) {
            assertTrue(scoreStr.contains(String.valueOf(score)));
        }
    }

    @Test
    void testToString() {
        result.setPlayerTeamScore(7);
        String str = result.toString();

        assertTrue(str.contains(playerTeamName));
        assertTrue(str.contains("Final Score: 7"));
    }
}