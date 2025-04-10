package model.inning;

import static org.junit.jupiter.api.Assertions.*;

import gameEnum.Teams;
import model.team.PlayerTeam;
import model.team.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

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
        List<Batter> batters = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            Batter batter = new Batter(
                    "Batter" + i, 80, 20, 10, 5, 2,
                    3, 90, 20, 10, 5, 2, 3,
                    100, 20, 10, 5, 3, 2,
                    300, 70, 40, 15, 7, 8, 0.65, 0.80,
                    0.30, 0.40, 0.420, 0.450, 0.890);
            batters.add(batter);
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

        // Verify that the next batter index is properly updated
        int expectedNextBatterIndex = inning.getBattersFaced() % batters.size();
        assertEquals(expectedNextBatterIndex, inning.getCurrentBatterIndex());

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
        for (int i = 0; i < 10; i++) {
            totalScore += inning.runInning(team, 0);
            inning.resetInning();
        }

        assertTrue(inning.getHits() >= 0);
        int hitTypeSum = inning.getSingles() + inning.getDoubles() + inning.getTriples() + inning.getHomeRuns();
        assertEquals(inning.getHits(), hitTypeSum);

        assertTrue(totalScore >= 0);
    }

//    @Test
//    void testBaseRunnerAdvancement() {
//        RegularInning testInning = new RegularInning(pitcher) {
//
//            @Override
//            public int runInning(List<Batter> lineup) {
//                resetInning();
//                int totalRuns = 0;
//
//                // Test single with bases empty
//                totalRuns += advanceRunners(gameEnum.Hits.SINGLE);
//
//                // Test single with runner on first
//                totalRuns += advanceRunners(gameEnum.Hits.SINGLE);
//
//                // Test double with runners on first and second
//                totalRuns += advanceRunners(gameEnum.Hits.DOUBLE);
//
//                // Test triple with runner on first
//                totalRuns += advanceRunners(gameEnum.Hits.TRIPLE);
//
//                // Test home run with bases loaded
//                // First load the bases
//                advanceRunnersOnWalk(); // Put runner on first
//                advanceRunnersOnWalk(); // Put runners on first and second
//                advanceRunnersOnWalk(); // Load the bases
//                totalRuns += advanceRunners(gameEnum.Hits.HR);
//
//                return totalRuns;
//            }
//        };
//
//        // Run the test inning
//        int score = testInning.runInning(lineup);
//
//        // Validate the score based on expected runner advancement
//        // We expect:
//        // - Single with bases empty: 0 runs
//        // - Single with runner on first: 0 runs (runner to 2nd)
//        // - Double with runners on 1st and 2nd: 1 run (runner from 2nd scores)
//        // - Triple with runner on first: 1 run (runner from 1st scores)
//        // - Home run with bases loaded: 4 runs (all runners + batter score)
//        assertEquals(6, score, "Base advancement should score the correct number of runs");
//    }
//
//    @Test
//    @DisplayName("Test walks and base advancement on walks")
//    void testWalksAndBaseAdvancement() {
//        // Create a test inning that forces walk outcomes
//        RegularInning testInning = new RegularInning(pitcher) {
//            // Mock method to force walk outcomes for testing
//            @Override
//            public int runInning(List<Batter> lineup) {
//                resetInning();
//
//                // Simulate different walk scenarios and count the runs scored
//                int totalRuns = 0;
//
//                // Test walk with bases empty
//                totalRuns += advanceRunnersOnWalk();
//
//                // Test walk with runner on first
//                totalRuns += advanceRunnersOnWalk();
//
//                // Test walk with runners on first and second
//                totalRuns += advanceRunnersOnWalk();
//
//                // Test walk with bases loaded (should force in a run)
//                totalRuns += advanceRunnersOnWalk();
//
//                // Record the walks
//                for (int i = 0; i < 4; i++) {
//                    addWalks(1);
//                }
//
//                return totalRuns;
//            }
//
//            // Helper method to add walks to the counter
//            private void addWalks(int count) {
//                for (int i = 0; i < count; i++) {
//                    this.walks++;
//                }
//            }
//        };
//
//        // Run the test inning
//        int score = testInning.runInning(lineup);
//
//        // Validate the score based on expected runner advancement on walks
//        // We expect only 1 run from the bases-loaded walk
//        assertEquals(1, score, "Walk with bases loaded should force in one run");
//
//        // Verify walk count
//        assertEquals(4, testInning.getWalks(), "Walk count should be updated correctly");
//    }
//
//    @Test
//    @DisplayName("Test getter methods")
//    void testGetters() {
//        // Run a full inning
//        inning.runInning(lineup);
//
//        // Verify that getter methods return non-negative values
//        assertTrue(inning.getBattersFaced() >= 0, "Batters faced should be non-negative");
//        assertTrue(inning.getCurrentBatterIndex() >= 0, "Current batter index should be non-negative");
//        assertTrue(inning.getPitchesThrown() >= 0, "Pitches thrown should be non-negative");
//        assertTrue(inning.getHits() >= 0, "Hits should be non-negative");
//        assertTrue(inning.getSingles() >= 0, "Singles should be non-negative");
//        assertTrue(inning.getDoubles() >= 0, "Doubles should be non-negative");
//        assertTrue(inning.getTriples() >= 0, "Triples should be non-negative");
//        assertTrue(inning.getHomeRuns() >= 0, "Home runs should be non-negative");
//        assertTrue(inning.getStrikeouts() >= 0, "Strikeouts should be non-negative");
//        assertTrue(inning.getWalks() >= 0, "Walks should be non-negative");
//
//        // Test getting pitch type count for specific pitch
//        int fourSeamCount = inning.getPitchTypeCount("fourSeam");
//        assertTrue(fourSeamCount >= 0, "Four-seam count should be non-negative");
//
//        // Test getting all pitch type counts
//        Map<String, Integer> allPitchCounts = inning.getAllPitchTypeCounts();
//        assertNotNull(allPitchCounts, "All pitch counts map should not be null");
//
//        // Test getting pitch category counts
//        Map<String, Integer> categoryCounts = inning.getPitchCategoryCounts();
//        assertNotNull(categoryCounts, "Pitch category counts map should not be null");
//    }
//
//    @Test
//    @DisplayName("Test multiple innings with the same lineup")
//    void testMultipleInnings() {
//        // Run multiple innings and track statistics
//        int totalScore = 0;
//        int totalHits = 0;
//        int totalStrikeouts = 0;
//        int totalWalks = 0;
//        int totalPitches = 0;
//
//        final int INNINGS_TO_RUN = 9;
//
//        for (int i = 0; i < INNINGS_TO_RUN; i++) {
//            int inningScore = inning.runInning(lineup);
//            totalScore += inningScore;
//            totalHits += inning.getHits();
//            totalStrikeouts += inning.getStrikeouts();
//            totalWalks += inning.getWalks();
//            totalPitches += inning.getPitchesThrown();
//
//            // Reset for next inning
//            inning.resetInning();
//        }
//
//        // Verify that totals make sense
//        assertTrue(totalScore >= 0, "Total score should be non-negative");
//        assertTrue(totalHits >= 0, "Total hits should be non-negative");
//        assertTrue(totalStrikeouts >= 0, "Total strikeouts should be non-negative");
//        assertTrue(totalWalks >= 0, "Total walks should be non-negative");
//        assertTrue(totalPitches >= totalHits + totalStrikeouts + totalWalks,
//                "Total pitches should be at least as many as outcomes");
//
//        // On average, we should see reasonable baseball statistics
//        // These are very loose bounds for probabilistic outcomes
//        double avgHitsPerInning = (double) totalHits / INNINGS_TO_RUN;
//        double avgStrikeoutsPerInning = (double) totalStrikeouts / INNINGS_TO_RUN;
//        double avgWalksPerInning = (double) totalWalks / INNINGS_TO_RUN;
//
//        assertTrue(avgHitsPerInning < 5.0, "Average hits per inning should be reasonable");
//        assertTrue(avgStrikeoutsPerInning < 5.0, "Average strikeouts per inning should be reasonable");
//        assertTrue(avgWalksPerInning < 5.0, "Average walks per inning should be reasonable");
//    }
}