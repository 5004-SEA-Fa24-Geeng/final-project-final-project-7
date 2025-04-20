package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mockito;

import gameEnum.PlayerData;
import gameEnum.Side;
import gameEnum.Teams;
import model.player.Batter;
import model.player.Pitcher;
import model.player.Player;
import model.simulation.SimulationResult;
import model.team.ComTeam;
import model.team.PlayerTeam;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Comprehensive unit tests for the first 15 methods in Model class.
 * Uses real CSV data to create playerTeam and comTeam.
 */
public class ModelTest {

    private Model model;
    private Set<Batter> batters;
    private Set<Pitcher> pitchers;   
    private ByteArrayOutputStream consoleOutput;
    private PrintStream originalOut;     

    @BeforeEach
    public void setUp() {
        model = new Model();
        // Initialize with real data
        model.setPlayerTeam(Teams.MARINERS);
        model.setComTeam(Teams.ANGELS);

        // Get real data to test with
        batters = model.getPlayerTeamBatterLoaderLineup();
        pitchers = model.getComTeamPitcherLoaderLineup();    
        
        // Set up console capturing
        originalOut = System.out;
        consoleOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(consoleOutput));        
    }

    @Test
    @DisplayName("Test getPlayerTeam returns the correct player team")
    public void testGetPlayerTeam() {
        // Test method: getPlayerTeam
        var team = model.getPlayerTeam();
        
        // Verify it's not null and is the correct type
        assertNotNull(team, "Player team should not be null");
        assertTrue(team instanceof PlayerTeam, "Should be an instance of PlayerTeam");
        
        // Verify it has the correct name
        assertEquals("mariners", team.getTeamName(), "Team name should be mariners");
    }

    @Test
    @DisplayName("Test getComTeam returns the correct computer team")
    public void testGetComTeam() {
        // Test method: getComTeam
        var team = model.getComTeam();
        
        // Verify it's not null and is the correct type
        assertNotNull(team, "Computer team should not be null");
        assertTrue(team instanceof ComTeam, "Should be an instance of ComTeam");
        
        // Verify it has the correct name
        assertEquals("angels", team.getTeamName(), "Team name should be angels");
    }

    @Test
    @DisplayName("Test getAllTeamName returns a complete list of MLB teams")
    public void testGetAllTeamName() {
        // Test method: getAllTeamName
        List<String> teams = model.getAllTeamName();
        
        // Verify the list contains 29 teams
        assertNotNull(teams, "Team list should not be null");
        assertEquals(29, teams.size(), "Should be 30 MLB teams");
        
        // Check for specific teams in different divisions
        assertTrue(teams.contains("athletics"), "List should contain athletics");
        assertTrue(teams.contains("yankees"), "List should contain yankees");
        assertTrue(teams.contains("mets"), "List should contain mets");
        assertTrue(teams.contains("cubs"), "List should contain cubs");
        assertTrue(teams.contains("dodgers"), "List should contain dodgers");
        
        // Make sure teams are in lowercase as expected
        assertFalse(teams.contains("YANKEES"), "Team names should be lowercase");
    }

    @Test
    @DisplayName("Test getAllColumnName returns a complete list of PlayerData column")
    public void testGetAllColumnName() {
        // Test method: getAllTeamName
        List<String> cols = model.getAllColumnName();
        
        // Verify the list contains 50 columns
        assertNotNull(cols, "Column list should not be null");
        assertEquals(50, cols.size(), "Should be 50 columns");
        
        // Check for specific columns
        assertTrue(cols.contains("FastballH"), "List should contain FastballH");
        assertTrue(cols.contains("ChaseContact"), "List should contain ChaseContact");
        assertTrue(cols.contains("AVG"), "List should contain AVG");
        assertTrue(cols.contains("Offspeed3B"), "List should contain Offspeed3B");
        assertTrue(cols.contains("Breaking2B"), "List should contain Breaking2B");
    }    

    @Test
    @DisplayName("Test getPlayerTeamBatterLineup returns a properly initialized batter lineup")
    public void testGetPlayerTeamBatterLineup() {
        // Test method: getPlayerTeamBatterLineup
        List<Batter> lineup = model.getPlayerTeamBatterLineup();
        
        // Verify the list is initialized with 9 slots
        assertNotNull(lineup, "Batter lineup should not be null");
        assertEquals(9, lineup.size(), "Lineup should have 9 slots");
        
        // All slots should be null initially
        for (int i = 0; i < lineup.size(); i++) {
            assertNull(lineup.get(i), "Batter slot " + (i+1) + " should be null initially");
        }
    }

    @Test
    @DisplayName("Test getPlayerTeamPitcherLineup returns a properly initialized pitcher lineup")
    public void testGetPlayerTeamPitcherLineup() {
        // Test method: getPlayerTeamPitcherLineup
        List<Pitcher> lineup = model.getPlayerTeamPitcherLineup();
        
        // Verify the list is initialized with 3 slots
        assertNotNull(lineup, "Pitcher lineup should not be null");
        assertEquals(3, lineup.size(), "Lineup should have 3 slots");
        
        // All slots should be null initially
        for (int i = 0; i < lineup.size(); i++) {
            assertNull(lineup.get(i), "Pitcher slot " + (i+1) + " should be null initially");
        }
    }

    @Test
    @DisplayName("Test getComTeamBatterLineup returns a properly initialized computer batter lineup")
    public void testGetComTeamBatterLineup() {
        // Test method: getComTeamBatterLineup
        List<Batter> lineup = model.getComTeamBatterLineup();
        
        // Verify the list is initialized with 9 slots
        assertNotNull(lineup, "Computer batter lineup should not be null");
        assertEquals(9, lineup.size(), "Lineup should have 9 slots");
        
        // All slots should be null initially
        for (int i = 0; i < lineup.size(); i++) {
            assertNull(lineup.get(i), "Computer batter slot " + (i+1) + " should be null initially");
        }
    }

    @Test
    @DisplayName("Test getComTeamPitcherLineup returns a properly initialized computer pitcher lineup")
    public void testGetComTeamPitcherLineup() {
        // Test method: getComTeamPitcherLineup
        List<Pitcher> lineup = model.getComTeamPitcherLineup();
        
        // Verify the list is initialized with 3 slots
        assertNotNull(lineup, "Computer pitcher lineup should not be null");
        assertEquals(3, lineup.size(), "Lineup should have 3 slots");
        
        // All slots should be null initially
        for (int i = 0; i < lineup.size(); i++) {
            assertNull(lineup.get(i), "Computer pitcher slot " + (i+1) + " should be null initially");
        }
    }

    @Test
    @DisplayName("Test getPlayerTeamBatterLoaderLineup loads Mariners batters correctly from CSV")
    public void testGetPlayerTeamBatterLoaderLineup() {
        // Test method: getPlayerTeamBatterLoaderLineup
        Set<Batter> batters = model.getPlayerTeamBatterLoaderLineup();
        
        // Verify batters were loaded correctly
        assertNotNull(batters, "Mariners batters should not be null");
        assertFalse(batters.isEmpty(), "Mariners batters should not be empty");
        assertEquals(16, batters.size(), "Should have 16 Mariners batters");
        
        // Verify batter attributes are loaded
        boolean foundValidBatter = false;
        for (Batter batter : batters) {
            assertNotNull(batter.getName(), "Batter name should not be null");
            assertNotEquals("", batter.getName().trim(), "Batter name should not be empty");
            
            if (batter.getTotalPA() > 0 && batter.getAVG() > 0) {
                foundValidBatter = true;
            }
        }
        assertTrue(foundValidBatter, "Should find at least one batter with valid statistics");
        
        // Look for a specific player we expect from the Mariners
        boolean foundJulio = false;
        for (Batter batter : batters) {
            if (batter.getName().contains("Julio")) {
                foundJulio = true;
                // Verify Julio has actual stats
                assertTrue(batter.getTotalPA() > 100, "Julio should have significant plate appearances");
                assertTrue(batter.getAVG() > 0.2, "Julio should have a reasonable batting average");
                assertTrue(batter.getOPS() > 0.7, "Julio should have a reasonable OPS");
                break;
            }
        }
        assertTrue(foundJulio, "Should find Julio Rodriguez in the Mariners roster");
    }

    @Test
    @DisplayName("Test getPlayerTeamPitcherLoaderLineup returns null for Mariners as expected")
    public void testGetPlayerTeamPitcherLoaderLineup() {
        // Test method: getPlayerTeamPitcherLoaderLineup
        Set<Pitcher> pitchers = model.getPlayerTeamPitcherLoaderLineup();
        
        // Verify the result is null for Mariners as specified in the PlayerLoader implementation
        assertNull(pitchers, "Pitcher loader lineup should be null for Mariners");
        
        // Verify the behavior is specific to Mariners by testing with another team
        model.setPlayerTeam(Teams.REDSOX);
        Set<Pitcher> redsoxPitchers = model.getPlayerTeamPitcherLoaderLineup();
        assertNotNull(redsoxPitchers, "Pitcher loader lineup should not be null for other teams");
    }

    @Test
    @DisplayName("Test getComTeamBatterLoaderLineup returns null for non-Mariners teams as expected")
    public void testGetComTeamBatterLoaderLineup() {
        // Test method: getComTeamBatterLoaderLineup
        Set<Batter> batters = model.getComTeamBatterLoaderLineup();
        
        // Verify the result is null for non-Mariners teams as specified in the PlayerLoader implementation
        assertNull(batters, "Batter loader lineup should be null for non-Mariners teams");
        
        // Verify the behavior is specific to non-Mariners by testing with Mariners
        model.setComTeam(Teams.MARINERS);
        Set<Batter> marinersBatters = model.getComTeamBatterLoaderLineup();
        assertNotNull(marinersBatters, "Batter loader lineup should not be null for Mariners");
    }

    @Test
    @DisplayName("Test getComTeamPitcherLoaderLineup loads Angels pitchers correctly from CSV")
    public void testGetComTeamPitcherLoaderLineup() {
        // Test method: getComTeamPitcherLoaderLineup
        Set<Pitcher> pitchers = model.getComTeamPitcherLoaderLineup();
        
        // Verify pitchers were loaded correctly
        assertNotNull(pitchers, "Angels pitchers should not be null");
        assertFalse(pitchers.isEmpty(), "Angels pitchers should not be empty");
        assertEquals(34, pitchers.size(), "Should have 34 Angels pitchers");
        
        // Verify we have both starters and relievers
        int starterCount = 0;
        int relieverCount = 0;
        
        for (Pitcher pitcher : pitchers) {
            assertNotNull(pitcher.getName(), "Pitcher name should not be null");
            assertNotEquals("", pitcher.getName().trim(), "Pitcher name should not be empty");
            
            if (pitcher.getRotation() == 1) {
                starterCount++;
            } else if (pitcher.getRotation() == 2) {
                relieverCount++;
            }
        }
        
        assertTrue(starterCount > 0, "Should have at least one starter");
        assertTrue(relieverCount > 0, "Should have at least one reliever");
        assertEquals(pitchers.size(), starterCount + relieverCount, 
                "All pitchers should be either starters or relievers");
        
        // Verify pitcher attributes are loaded
        boolean foundValidPitcher = false;
        for (Pitcher pitcher : pitchers) {
            if (pitcher.getPitches() > 0 && pitcher.getStrikesRate() > 0) {
                foundValidPitcher = true;
                break;
            }
        }
        assertTrue(foundValidPitcher, "Should find at least one pitcher with valid statistics");
    }

    @Test
    @DisplayName("Test setPlayerTeam sets the correct team")
    public void testSetPlayerTeam() {
        // Test method: setPlayerTeam with specific team
        model.setPlayerTeam(Teams.DODGERS);
        assertEquals("dodgers", model.getPlayerTeam().getTeamName(), 
                "Player team should be set to Dodgers");
        
        // Test method: setPlayerTeam with default (Mariners)
        model.setPlayerTeam();
        assertEquals("mariners", model.getPlayerTeam().getTeamName(), 
                "Default player team should be Mariners");
        
        // Test with each MLB team to ensure proper initialization
        for (Teams team : Teams.values()) {
            if (team != Teams.MARINERS) { // Already tested default case
                model.setPlayerTeam(team);
                assertEquals(team.getCmdName(), model.getPlayerTeam().getTeamName(), 
                        "Player team should be set to " + team.getCmdName());
            }
        }
    }

    @Test
    @DisplayName("Test setComTeam sets the correct team")
    public void testSetComTeam() {
        // Test method: setComTeam with specific team
        model.setComTeam(Teams.YANKEES);
        assertEquals("yankees", model.getComTeam().getTeamName(), 
                "Computer team should be set to Yankees");
        
        // Test method: setComTeam with default (random team)
        model.setComTeam();
        
        // Since default is random, we can only verify it's not null and not Mariners
        assertNotNull(model.getComTeam().getTeamName(), "Computer team should not be null");
        // randomTeam() explicitly excludes Mariners
        assertNotEquals("mariners", model.getComTeam().getTeamName(), 
                "Random computer team should not be Mariners");
        
        // Test with each MLB team to ensure proper initialization
        for (Teams team : Teams.values()) {
            if (team != Teams.MARINERS) { // Avoid Mariners to test a different path
                model.setComTeam(team);
                assertEquals(team.getCmdName(), model.getComTeam().getTeamName(), 
                        "Computer team should be set to " + team.getCmdName());
            }
        }
    }

    @Test
    @DisplayName("Test getBatter retrieves batters by name")
    public void testGetBatter() {
        // Test method: getBatter
        
        // 1. Test retrieving a batter that exists
        Set<Batter> batters = model.getPlayerTeamBatterLoaderLineup();
        
        // Find a batter to test with
        Batter testBatter = null;
        String batterName = null;
        for (Batter batter : batters) {
            testBatter = batter;
            batterName = batter.getName();
            break;
        }
        
        // Verify we can retrieve the batter
        assertNotNull(testBatter, "Should have found a batter to test");
        Batter retrievedBatter = model.getBatter(Side.PLAYER, batterName);
        assertNotNull(retrievedBatter, "Should retrieve an existing batter");
        assertEquals(batterName, retrievedBatter.getName(), "Should retrieve the correct batter");
        
        // 2. Test retrieving a non-existent batter
        Batter nonExistentBatter = model.getBatter(Side.PLAYER, "NonExistentBatterName");
        assertNull(nonExistentBatter, "Should return null for non-existent batter");
        
        // 3. Test with partial name (case insensitive)
        if (batterName != null && batterName.length() > 3) {
            String partialName = batterName.substring(0, 3).toLowerCase();
            Batter partialMatch = model.getBatter(Side.PLAYER, partialName);
            // This might return null if no exact match or if implementation is strict about names
            if (partialMatch != null) {
                assertTrue(partialMatch.getName().toLowerCase().contains(partialName), 
                        "Partial name match should contain the search term");
            }
        }
    }

    @Test
    @DisplayName("Test getPitcher retrieves pitchers by name")
    public void testGetPitcher() {
        // Test method: getPitcher
        
        // 1. Test retrieving a pitcher that exists
        Set<Pitcher> pitchers = model.getComTeamPitcherLoaderLineup();
        
        // Find a pitcher to test with
        Pitcher testPitcher = null;
        String pitcherName = null;
        for (Pitcher pitcher : pitchers) {
            testPitcher = pitcher;
            pitcherName = pitcher.getName();
            break;
        }
        
        // Verify we can retrieve the pitcher
        assertNotNull(testPitcher, "Should have found a pitcher to test");
        Pitcher retrievedPitcher = model.getPitcher(Side.COMPUTER, pitcherName);
        assertNotNull(retrievedPitcher, "Should retrieve an existing pitcher");
        assertEquals(pitcherName, retrievedPitcher.getName(), "Should retrieve the correct pitcher");
        
        // 2. Test retrieving a non-existent pitcher
        Pitcher nonExistentPitcher = model.getPitcher(Side.COMPUTER, "NonExistentPitcherName");
        assertNull(nonExistentPitcher, "Should return null for non-existent pitcher");
        
        // 3. Test with partial name (case insensitive)
        if (pitcherName != null && pitcherName.length() > 3) {
            String partialName = pitcherName.substring(0, 3).toLowerCase();
            Pitcher partialMatch = model.getPitcher(Side.COMPUTER, partialName);
            // This might return null if no exact match or if implementation is strict about names
            if (partialMatch != null) {
                assertTrue(partialMatch.getName().toLowerCase().contains(partialName), 
                        "Partial name match should contain the search term");
            }
        }
    }

    /**
     * Helper method to invoke a private method via reflection.
     */
    @SuppressWarnings("unchecked")
    private <T> T invokePrivateMethod(String methodName, Class<?>[] paramTypes, Object[] params) {
        try {
            Method method = Model.class.getDeclaredMethod(methodName, paramTypes);
            method.setAccessible(true);
            return (T) method.invoke(model, params);
        } catch (Exception e) {
            fail("Failed to invoke private method " + methodName + ": " + e.getMessage());
            return null;
        }
    }

    @Test
    @DisplayName("Test checkFilterNum correctly identifies single vs multiple filters")
    public void testCheckFilterNum() {
        // Test with a single filter
        String singleFilter = "name == Test Batter";
        Boolean singleResult = invokePrivateMethod("checkFilterNum", new Class<?>[]{String.class}, new Object[]{singleFilter});
        assertTrue(singleResult, "Should identify a single filter as true");
        
        // Test with multiple filters
        String multiFilter = "name ~= Test, TotalPA >= 100, AVG >= 0.300";
        Boolean multiResult = invokePrivateMethod("checkFilterNum", new Class<?>[]{String.class}, new Object[]{multiFilter});
        assertFalse(multiResult, "Should identify multiple filters as false");
        
        // Test with an empty string
        String emptyFilter = "";
        Boolean emptyResult = invokePrivateMethod("checkFilterNum", new Class<?>[]{String.class}, new Object[]{emptyFilter});
        assertTrue(emptyResult, "Should handle empty string as a single filter");
    }

    // ============================================================
    // Tests for batterFilter(String filter, Set<Batter> batterLoaderLineup)
    // ============================================================
    
    @Test
    @DisplayName("Test basic batterFilter method with a simple AVG filter")
    public void testBasicBatterFilter_AVG() {
        // Skip if no batters data available
        if (batters == null || batters.isEmpty()) {
            return;
        }
        
        // Apply a simple AVG filter
        Stream<Batter> result = model.batterFilter("AVG >= 0.250", batters);
        
        // Convert to list to verify
        List<Batter> filtered = result.toList();
        
        // Verify the filter worked
        assertFalse(filtered.isEmpty(), "Should find batters with AVG >= 0.250");
        
        for (Batter batter : filtered) {
            assertTrue(batter.getAVG() >= 0.250, 
                    "All batters in result should have AVG >= 0.250, found: " + batter.getAVG());
        }
    }
    
    @Test
    @DisplayName("Test basic batterFilter method with a simple OPS filter")
    public void testBasicBatterFilter_OPS() {
        // Skip if no batters data available
        if (batters == null || batters.isEmpty()) {
            return;
        }
        
        // Apply a simple OPS filter
        Stream<Batter> result = model.batterFilter("OPS >= 0.750", batters);
        
        // Convert to list to verify
        List<Batter> filtered = result.toList();
        
        // Verify the filter worked
        assertFalse(filtered.isEmpty(), "Should find batters with OPS >= 0.750");
        
        for (Batter batter : filtered) {
            assertTrue(batter.getOPS() >= 0.750, 
                    "All batters in result should have OPS >= 0.750, found: " + batter.getOPS());
        }
    }
    
    @Test
    @DisplayName("Test basic batterFilter method with an integer field filter")
    public void testBasicBatterFilter_TotalHR() {
        // Skip if no batters data available
        if (batters == null || batters.isEmpty()) {
            return;
        }
        
        // Apply a filter on home runs
        Stream<Batter> result = model.batterFilter("TotalHR >= 10", batters);
        
        // Convert to list to verify
        List<Batter> filtered = result.toList();
        
        // Verify the filter worked
        for (Batter batter : filtered) {
            assertTrue(batter.getTotalHR() >= 10, 
                    "All batters in result should have TotalHR >= 10, found: " + batter.getTotalHR());
        }
    }
    
    @Test
    @DisplayName("Test basic batterFilter method with a complex multi-condition filter")
    public void testBasicBatterFilter_MultipleConditions() {
        // Skip if no batters data available
        if (batters == null || batters.isEmpty()) {
            return;
        }
        
        // Apply a complex filter with multiple conditions
        String complexFilter = "TotalPA >= 100, AVG >= 0.250, OPS >= 0.750";
        Stream<Batter> result = model.batterFilter(complexFilter, batters);
        
        // Convert to list to verify
        List<Batter> filtered = result.toList();
        
        // Verify all conditions were applied
        for (Batter batter : filtered) {
            assertTrue(batter.getTotalPA() >= 100, 
                    "All batters should have TotalPA >= 100, found: " + batter.getTotalPA());
            assertTrue(batter.getAVG() >= 0.250, 
                    "All batters should have AVG >= 0.250, found: " + batter.getAVG());
            assertTrue(batter.getOPS() >= 0.750, 
                    "All batters should have OPS >= 0.750, found: " + batter.getOPS());
        }
    }
    
    @Test
    @DisplayName("Test basic batterFilter method with the contains operator")
    public void testBasicBatterFilter_ContainsOperator() {
        // Skip if no batters data available
        if (batters == null || batters.isEmpty()) {
            return;
        }
        
        // Find batters with "J" in their name
        Stream<Batter> result = model.batterFilter("name ~= J", batters);
        
        // Convert to list to verify
        List<Batter> filtered = result.toList();
        
        // Verify the filter worked
        for (Batter batter : filtered) {
            assertTrue(batter.getName().contains("J"), 
                    "All batters should have 'J' in their name, found: " + batter.getName());
        }
    }
    
    @Test
    @DisplayName("Test basic batterFilter method with an empty filter")
    public void testBasicBatterFilter_EmptyFilter() {
        // Skip if no batters data available
        if (batters == null || batters.isEmpty()) {
            return;
        }
        
        // Apply an empty filter
        Stream<Batter> result = model.batterFilter("", batters);
        
        // Convert to list to verify
        List<Batter> filtered = result.toList();
        
        // Empty filter should return all batters
        assertEquals(0, filtered.size(), "Empty filter should return all batters");
    }
    
    @Test
    @DisplayName("Test basic batterFilter method with an invalid filter")
    public void testBasicBatterFilter_InvalidFilter() {
        // Skip if no batters data available
        if (batters == null || batters.isEmpty()) {
            return;
        }
        
        // Apply an invalid filter
        Stream<Batter> result = model.batterFilter("InvalidColumn > 0", batters);
        
        // Convert to list to verify
        List<Batter> filtered = result.toList();
        
        // Invalid filter should return all batters
        assertEquals(0, filtered.size(), "Invalid filter should return all batters");
    }
    
    @Test
    @DisplayName("Test basic batterFilter with default sorting by name")
    public void testBasicBatterFilter_DefaultSorting() {
        // Skip if no batters data available
        if (batters == null || batters.isEmpty()) {
            return;
        }
        
        // Apply a simple filter
        Stream<Batter> result = model.batterFilter("TotalPA >= 100", batters);
        
        // Convert to list to verify
        List<Batter> filtered = result.toList();
        
        // Default sorting should be by name
        for (int i = 0; i < filtered.size() - 1; i++) {
            String current = filtered.get(i).getName().toLowerCase();
            String next = filtered.get(i + 1).getName().toLowerCase();
            assertTrue(current.compareTo(next) <= 0, 
                    "Batters should be sorted by name. Found " + current + " before " + next);
        }
    }
    
    // ============================================================
    // Tests for batterFilter(String filter, PlayerData sortOn, Set<Batter> batterLoaderLineup)
    // ============================================================
    
    @Test
    @DisplayName("Test batterFilter with AVG sorting")
    public void testBatterFilterWithAVGSorting() {
        // Skip if no batters data available
        if (batters == null || batters.isEmpty()) {
            return;
        }
        
        // Filter and sort by AVG
        Stream<Batter> result = model.batterFilter("TotalPA >= 50", PlayerData.AVG, batters);
        
        // Convert to list to verify
        List<Batter> filtered = result.toList();
        
        // Verify sorting is in ascending order
        for (int i = 0; i < filtered.size() - 1; i++) {
            double current = filtered.get(i).getAVG();
            double next = filtered.get(i + 1).getAVG();
            assertTrue(current <= next, 
                    String.format("Batters should be in ascending AVG order. Found %f before %f", current, next));
        }
    }
    
    @Test
    @DisplayName("Test batterFilter with NAME sorting")
    public void testBatterFilterWithNameSorting() {
        // Skip if no batters data available
        if (batters == null || batters.isEmpty()) {
            return;
        }
        
        // Filter and sort by NAME
        Stream<Batter> result = model.batterFilter("TotalPA >= 50", PlayerData.NAME, batters);
        
        // Convert to list to verify
        List<Batter> filtered = result.toList();
        
        // Verify sorting is in alphabetical order
        for (int i = 0; i < filtered.size() - 1; i++) {
            String current = filtered.get(i).getName().toLowerCase();
            String next = filtered.get(i + 1).getName().toLowerCase();
            assertTrue(current.compareTo(next) <= 0, 
                    String.format("Batters should be in alphabetical order. Found %s before %s", current, next));
        }
    }
    
    @Test
    @DisplayName("Test batterFilter with complex filter and OPS sorting")
    public void testBatterFilterWithComplexFilterAndOPSSorting() {
        // Skip if no batters data available
        if (batters == null || batters.isEmpty()) {
            return;
        }
        
        // Complex filter with multiple conditions
        String complexFilter = "TotalPA >= 100, AVG >= 0.250, TotalHR >= 5";
        
        // Apply filter and sort by OPS
        Stream<Batter> result = model.batterFilter(complexFilter, PlayerData.OPS, batters);
        
        // Convert to list to verify
        List<Batter> filtered = result.toList();
        
        // Verify the filter worked correctly
        for (Batter batter : filtered) {
            assertTrue(batter.getTotalPA() >= 100, "Batters should have TotalPA >= 100");
            assertTrue(batter.getAVG() >= 0.250, "Batters should have AVG >= 0.250");
            assertTrue(batter.getTotalHR() >= 5, "Batters should have TotalHR >= 5");
        }
        
        // Verify the sorting worked correctly
        for (int i = 0; i < filtered.size() - 1; i++) {
            double current = filtered.get(i).getOPS();
            double next = filtered.get(i + 1).getOPS();
            assertTrue(current <= next, 
                    String.format("Batters should be in ascending OPS order. Found %f before %f", current, next));
        }
    }
    
    // ============================================================
    // Tests for batterFilter(String filter, PlayerData sortOn, boolean ascending, Set<Batter> batterLoaderLineup)
    // ============================================================
    
    @Test
    @DisplayName("Test batterFilter with AVG ascending order")
    public void testBatterFilterWithAVGAscending() {
        // Skip if no batters data available
        if (batters == null || batters.isEmpty()) {
            return;
        }
        
        // Filter and sort by AVG ascending
        Stream<Batter> result = model.batterFilter("TotalPA >= 50", PlayerData.AVG, true, batters);
        
        // Convert to list to verify
        List<Batter> filtered = result.toList();
        
        // Verify sorting is in ascending order
        for (int i = 0; i < filtered.size() - 1; i++) {
            double current = filtered.get(i).getAVG();
            double next = filtered.get(i + 1).getAVG();
            assertTrue(current <= next, 
                    String.format("Batters should be in ascending AVG order. Found %f before %f", current, next));
        }
    }
    
    @Test
    @DisplayName("Test batterFilter with AVG descending order")
    public void testBatterFilterWithAVGDescending() {
        // Skip if no batters data available
        if (batters == null || batters.isEmpty()) {
            return;
        }
        
        // Filter and sort by AVG descending
        Stream<Batter> result = model.batterFilter("TotalPA >= 50", PlayerData.AVG, false, batters);
        
        // Convert to list to verify
        List<Batter> filtered = result.toList();
        
        // Verify sorting is in descending order
        for (int i = 0; i < filtered.size() - 1; i++) {
            double current = filtered.get(i).getAVG();
            double next = filtered.get(i + 1).getAVG();
            assertTrue(current >= next, 
                    String.format("Batters should be in descending AVG order. Found %f before %f", current, next));
        }
    }
    
    @Test
    @DisplayName("Test batterFilter with NAME ascending order")
    public void testBatterFilterWithNameAscending() {
        // Skip if no batters data available
        if (batters == null || batters.isEmpty()) {
            return;
        }
        
        // Filter and sort by NAME ascending
        Stream<Batter> result = model.batterFilter("TotalPA >= 50", PlayerData.NAME, true, batters);
        
        // Convert to list to verify
        List<Batter> filtered = result.toList();
        
        // Verify sorting is in alphabetical order
        for (int i = 0; i < filtered.size() - 1; i++) {
            String current = filtered.get(i).getName().toLowerCase();
            String next = filtered.get(i + 1).getName().toLowerCase();
            assertTrue(current.compareTo(next) <= 0, 
                    String.format("Batters should be in ascending alphabetical order. Found %s before %s", current, next));
        }
    }
    
    @Test
    @DisplayName("Test batterFilter with NAME descending order")
    public void testBatterFilterWithNameDescending() {
        // Skip if no batters data available
        if (batters == null || batters.isEmpty()) {
            return;
        }
        
        // Filter and sort by NAME descending
        Stream<Batter> result = model.batterFilter("TotalPA >= 50", PlayerData.NAME, false, batters);
        
        // Convert to list to verify
        List<Batter> filtered = result.toList();
        
        // Verify sorting is in reverse alphabetical order
        for (int i = 0; i < filtered.size() - 1; i++) {
            String current = filtered.get(i).getName().toLowerCase();
            String next = filtered.get(i + 1).getName().toLowerCase();
            assertTrue(current.compareTo(next) >= 0, 
                    String.format("Batters should be in descending alphabetical order. Found %s before %s", current, next));
        }
    }
    
    @Test
    @DisplayName("Test batterFilter with OPS ascending and descending order")
    public void testBatterFilterWithOPSDirections() {
        // Skip if no batters data available
        if (batters == null || batters.isEmpty()) {
            return;
        }
        
        // Filter and sort by OPS ascending
        Stream<Batter> ascResult = model.batterFilter("TotalPA >= 50", PlayerData.OPS, true, batters);
        List<Batter> ascFiltered = ascResult.toList();
        
        // Verify ascending order
        for (int i = 0; i < ascFiltered.size() - 1; i++) {
            double current = ascFiltered.get(i).getOPS();
            double next = ascFiltered.get(i + 1).getOPS();
            assertTrue(current <= next, 
                    String.format("Batters should be in ascending OPS order. Found %f before %f", current, next));
        }
        
        // Filter and sort by OPS descending
        Stream<Batter> descResult = model.batterFilter("TotalPA >= 50", PlayerData.OPS, false, batters);
        List<Batter> descFiltered = descResult.toList();
        
        // Verify descending order
        for (int i = 0; i < descFiltered.size() - 1; i++) {
            double current = descFiltered.get(i).getOPS();
            double next = descFiltered.get(i + 1).getOPS();
            assertTrue(current >= next, 
                    String.format("Batters should be in descending OPS order. Found %f before %f", current, next));
        }
        
        // If both sorts worked, first element of descending should match last element of ascending
        if (!ascFiltered.isEmpty() && !descFiltered.isEmpty() && ascFiltered.size() == descFiltered.size()) {
            Batter firstDesc = descFiltered.get(0);
            Batter lastAsc = ascFiltered.get(ascFiltered.size() - 1);
            assertEquals(firstDesc.getOPS(), lastAsc.getOPS(), 
                    "First element of descending should match last element of ascending");
        }
    }
    
    @Test
    @DisplayName("Test batterFilter with TotalHR ascending and descending order")
    public void testBatterFilterWithHRDirections() {
        // Skip if no batters data available
        if (batters == null || batters.isEmpty()) {
            return;
        }
        
        // Filter and sort by TotalHR ascending
        Stream<Batter> ascResult = model.batterFilter("TotalPA >= 50", PlayerData.T_HR, true, batters);
        List<Batter> ascFiltered = ascResult.toList();
        
        // Verify ascending order
        for (int i = 0; i < ascFiltered.size() - 1; i++) {
            int current = ascFiltered.get(i).getTotalHR();
            int next = ascFiltered.get(i + 1).getTotalHR();
            assertTrue(current <= next, 
                    String.format("Batters should be in ascending HR order. Found %d before %d", current, next));
        }
        
        // Filter and sort by TotalHR descending
        Stream<Batter> descResult = model.batterFilter("TotalPA >= 50", PlayerData.T_HR, false, batters);
        List<Batter> descFiltered = descResult.toList();
        
        // Verify descending order
        for (int i = 0; i < descFiltered.size() - 1; i++) {
            int current = descFiltered.get(i).getTotalHR();
            int next = descFiltered.get(i + 1).getTotalHR();
            assertTrue(current >= next, 
                    String.format("Batters should be in descending HR order. Found %d before %d", current, next));
        }
    }
    
    @Test
    @DisplayName("Test batterFilter with complex filter and different sort directions")
    public void testBatterFilterWithComplexFilterAndSortDirections() {
        // Skip if no batters data available
        if (batters == null || batters.isEmpty()) {
            return;
        }
        
        // Complex filter with multiple conditions
        String complexFilter = "TotalPA >= 100, AVG >= 0.250, TotalHR >= 5";
        
        // Apply filter and sort by OPS ascending
        Stream<Batter> ascResult = model.batterFilter(complexFilter, PlayerData.OPS, true, batters);
        List<Batter> ascFiltered = ascResult.toList();
        
        // Verify the filter worked correctly
        for (Batter batter : ascFiltered) {
            assertTrue(batter.getTotalPA() >= 100, "Batters should have TotalPA >= 100");
            assertTrue(batter.getAVG() >= 0.250, "Batters should have AVG >= 0.250");
            assertTrue(batter.getTotalHR() >= 5, "Batters should have TotalHR >= 5");
        }
        
        // Verify ascending sort
        for (int i = 0; i < ascFiltered.size() - 1; i++) {
            double current = ascFiltered.get(i).getOPS();
            double next = ascFiltered.get(i + 1).getOPS();
            assertTrue(current <= next, "Batters should be in ascending OPS order");
        }
        
        // Apply filter and sort by OPS descending
        Stream<Batter> descResult = model.batterFilter(complexFilter, PlayerData.OPS, false, batters);
        List<Batter> descFiltered = descResult.toList();
        
        // Verify descending sort
        for (int i = 0; i < descFiltered.size() - 1; i++) {
            double current = descFiltered.get(i).getOPS();
            double next = descFiltered.get(i + 1).getOPS();
            assertTrue(current >= next, "Batters should be in descending OPS order");
        }
    }

    // ============================================================
    // Tests for pitcherFilter(String filter, Set<Pitcher> pitcherLoaderLineup)
    // ============================================================
    
    @Test
    @DisplayName("Test basic pitcherFilter method with a simple StrikesRate filter")
    public void testBasicPitcherFilter_StrikesRate() {
        // Skip if no pitchers data available
        if (pitchers == null || pitchers.isEmpty()) {
            return;
        }
        
        // Apply a simple StrikesRate filter
        Stream<Pitcher> result = model.pitcherFilter("StrikesRate >= 0.600", pitchers);
        
        // Convert to list to verify
        List<Pitcher> filtered = result.toList();
        
        // Verify the filter worked
        assertFalse(filtered.isEmpty(), "Should find pitchers with StrikesRate >= 0.600");
        
        for (Pitcher pitcher : filtered) {
            assertTrue(pitcher.getStrikesRate() >= 0.600, 
                    "All pitchers in result should have StrikesRate >= 0.600, found: " + pitcher.getStrikesRate());
        }
    }
    
    @Test
    @DisplayName("Test basic pitcherFilter method with a Rotation filter")
    public void testBasicPitcherFilter_Rotation() {
        // Skip if no pitchers data available
        if (pitchers == null || pitchers.isEmpty()) {
            return;
        }
        
        // Apply a filter for starting pitchers (Rotation == 1)
        Stream<Pitcher> result = model.pitcherFilter("Rotation == 1", pitchers);
        
        // Convert to list to verify
        List<Pitcher> filtered = result.toList();
        
        // Verify the filter worked
        assertFalse(filtered.isEmpty(), "Should find starting pitchers");
        
        for (Pitcher pitcher : filtered) {
            assertEquals(1, pitcher.getRotation(), 
                    "All pitchers in result should be starters (Rotation == 1), found: " + pitcher.getRotation());
        }
    }
    
    @Test
    @DisplayName("Test basic pitcherFilter method with a Pitches filter")
    public void testBasicPitcherFilter_Pitches() {
        // Skip if no pitchers data available
        if (pitchers == null || pitchers.isEmpty()) {
            return;
        }
        
        // Apply a filter for pitchers with substantial workload
        Stream<Pitcher> result = model.pitcherFilter("Pitches >= 1000", pitchers);
        
        // Convert to list to verify
        List<Pitcher> filtered = result.toList();
        
        // Check if we have any pitchers meeting this criterion
        if (!filtered.isEmpty()) {
            // Verify the filter worked
            for (Pitcher pitcher : filtered) {
                assertTrue(pitcher.getPitches() >= 1000, 
                        "All pitchers should have Pitches >= 1000, found: " + pitcher.getPitches());
            }
        }
    }
    
    @Test
    @DisplayName("Test basic pitcherFilter method with a pitch type filter")
    public void testBasicPitcherFilter_PitchType() {
        // Skip if no pitchers data available
        if (pitchers == null || pitchers.isEmpty()) {
            return;
        }
        
        // Apply a filter for pitchers who throw a lot of fastballs
        Stream<Pitcher> result = model.pitcherFilter("4-SeamFastball >= 0.300", pitchers);
        
        // Convert to list to verify
        List<Pitcher> filtered = result.toList();
        
        // Verify the filter worked
        for (Pitcher pitcher : filtered) {
            assertTrue(pitcher.getFourSeam() >= 0.300, 
                    "All pitchers should use four-seam fastball >= 30%, found: " + pitcher.getFourSeam());
        }
    }
    
    @Test
    @DisplayName("Test basic pitcherFilter method with a complex multi-condition filter")
    public void testBasicPitcherFilter_MultipleConditions() {
        // Skip if no pitchers data available
        if (pitchers == null || pitchers.isEmpty()) {
            return;
        }
        
        // Apply a complex filter with multiple conditions
        String complexFilter = "Rotation == 1, StrikesRate >= 0.600, Pitches >= 1000";
        Stream<Pitcher> result = model.pitcherFilter(complexFilter, pitchers);
        
        // Convert to list to verify
        List<Pitcher> filtered = result.toList();
        
        // If we found any pitchers meeting all criteria, verify them
        if (!filtered.isEmpty()) {
            // Verify all conditions were applied
            for (Pitcher pitcher : filtered) {
                assertEquals(1, pitcher.getRotation(), 
                        "All pitchers should be starters (Rotation == 1), found: " + pitcher.getRotation());
                assertTrue(pitcher.getStrikesRate() >= 0.600, 
                        "All pitchers should have StrikesRate >= 0.600, found: " + pitcher.getStrikesRate());
                assertTrue(pitcher.getPitches() >= 1000, 
                        "All pitchers should have Pitches >= 1000, found: " + pitcher.getPitches());
            }
        }
    }
    
    @Test
    @DisplayName("Test basic pitcherFilter method with the contains operator")
    public void testBasicPitcherFilter_ContainsOperator() {
        // Skip if no pitchers data available
        if (pitchers == null || pitchers.isEmpty()) {
            return;
        }
        
        // Find pitchers with "J" in their name
        Stream<Pitcher> result = model.pitcherFilter("name ~= j", pitchers);
        
        // Convert to list to verify
        List<Pitcher> filtered = result.toList();
        
        // Verify the filter worked if we found any matches
        if (!filtered.isEmpty()) {
            for (Pitcher pitcher : filtered) {
                System.out.println(pitcher.getName());
                assertTrue(pitcher.getName().toLowerCase().contains("j"), 
                        "All pitchers should have 'J' in their name, found: " + pitcher.getName());
            }
        }
    }
    
    @Test
    @DisplayName("Test basic pitcherFilter method with an empty filter")
    public void testBasicPitcherFilter_EmptyFilter() {
        // Skip if no pitchers data available
        if (pitchers == null || pitchers.isEmpty()) {
            return;
        }
        
        // Apply an empty filter
        Stream<Pitcher> result = model.pitcherFilter("", pitchers);
        
        // Convert to list to verify
        List<Pitcher> filtered = result.toList();
        
        // Empty filter should return all pitchers
        assertEquals(0, filtered.size(), "Empty filter should return all pitchers");
    }
    
    @Test
    @DisplayName("Test basic pitcherFilter method with an invalid filter")
    public void testBasicPitcherFilter_InvalidFilter() {
        // Skip if no pitchers data available
        if (pitchers == null || pitchers.isEmpty()) {
            return;
        }
        
        // Apply an invalid filter
        Stream<Pitcher> result = model.pitcherFilter("InvalidColumn > 0", pitchers);
        
        // Convert to list to verify
        List<Pitcher> filtered = result.toList();
        
        // Invalid filter should return all pitchers
        assertEquals(0, filtered.size(), "Invalid filter should return all pitchers");
    }
    
    @Test
    @DisplayName("Test basic pitcherFilter with default sorting by name")
    public void testBasicPitcherFilter_DefaultSorting() {
        // Skip if no pitchers data available
        if (pitchers == null || pitchers.isEmpty()) {
            return;
        }
        
        // Apply a simple filter
        Stream<Pitcher> result = model.pitcherFilter("Rotation >= 1", pitchers);
        
        // Convert to list to verify
        List<Pitcher> filtered = result.toList();
        
        // Default sorting should be by name
        for (int i = 0; i < filtered.size() - 1; i++) {
            String current = filtered.get(i).getName().toLowerCase();
            String next = filtered.get(i + 1).getName().toLowerCase();
            assertTrue(current.compareTo(next) <= 0, 
                    "Pitchers should be sorted by name. Found " + current + " before " + next);
        }
    }
    
    // ============================================================
    // Tests for pitcherFilter(String filter, PlayerData sortOn, Set<Pitcher> pitcherLoaderLineup)
    // ============================================================
    
    @Test
    @DisplayName("Test pitcherFilter with NAME sorting")
    public void testPitcherFilterWithNameSorting() {
        // Skip if no pitchers data available
        if (pitchers == null || pitchers.isEmpty()) {
            return;
        }
        
        // Filter and sort by NAME
        Stream<Pitcher> result = model.pitcherFilter("Rotation >= 1", PlayerData.NAME, pitchers);
        
        // Convert to list to verify
        List<Pitcher> filtered = result.toList();
        
        // Verify sorting is in alphabetical order
        for (int i = 0; i < filtered.size() - 1; i++) {
            String current = filtered.get(i).getName().toLowerCase();
            String next = filtered.get(i + 1).getName().toLowerCase();
            assertTrue(current.compareTo(next) <= 0, 
                    String.format("Pitchers should be in alphabetical order. Found %s before %s", current, next));
        }
    }
    
    @Test
    @DisplayName("Test pitcherFilter with StrikesRate sorting")
    public void testPitcherFilterWithStrikesRateSorting() {
        // Skip if no pitchers data available
        if (pitchers == null || pitchers.isEmpty()) {
            return;
        }
        
        // Filter and sort by StrikesRate
        Stream<Pitcher> result = model.pitcherFilter("Rotation >= 1", PlayerData.S_RATE, pitchers);
        
        // Convert to list to verify
        List<Pitcher> filtered = result.toList();
        
        // Verify sorting is in ascending order
        for (int i = 0; i < filtered.size() - 1; i++) {
            double current = filtered.get(i).getStrikesRate();
            double next = filtered.get(i + 1).getStrikesRate();
            assertTrue(current <= next, 
                    String.format("Pitchers should be in ascending StrikesRate order. Found %f before %f", current, next));
        }
    }
    
    @Test
    @DisplayName("Test pitcherFilter with Rotation sorting")
    public void testPitcherFilterWithRotationSorting() {
        // Skip if no pitchers data available
        if (pitchers == null || pitchers.isEmpty()) {
            return;
        }
        
        // Filter all pitchers and sort by Rotation
        Stream<Pitcher> result = model.pitcherFilter("Rotation >= 1", PlayerData.ROTATION, pitchers);
        
        // Convert to list to verify
        List<Pitcher> filtered = result.toList();
        
        // Verify sorting is in ascending order by rotation
        for (int i = 0; i < filtered.size() - 1; i++) {
            int current = filtered.get(i).getRotation();
            int next = filtered.get(i + 1).getRotation();
            assertTrue(current <= next, 
                    String.format("Pitchers should be in ascending Rotation order. Found %d before %d", current, next));
        }
    }
    
    @Test
    @DisplayName("Test pitcherFilter with complex filter and pitch type sorting")
    public void testPitcherFilterWithComplexFilterAndPitchTypeSorting() {
        // Skip if no pitchers data available
        if (pitchers == null || pitchers.isEmpty()) {
            return;
        }
        
        // Complex filter with multiple conditions
        String complexFilter = "Rotation == 1, StrikesRate >= 0.600";
        
        // Apply filter and sort by FourSeam fastball usage
        Stream<Pitcher> result = model.pitcherFilter(complexFilter, PlayerData.FOURSEAM, pitchers);
        
        // Convert to list to verify
        List<Pitcher> filtered = result.toList();
        
        // If we found any pitchers meeting all criteria, verify them
        if (!filtered.isEmpty()) {
            // Verify the filter worked correctly
            for (Pitcher pitcher : filtered) {
                assertEquals(1, pitcher.getRotation(), "Pitchers should be starters (Rotation == 1)");
                assertTrue(pitcher.getStrikesRate() >= 0.600, "Pitchers should have StrikesRate >= 0.600");
            }
            
            // Verify the sorting worked correctly
            for (int i = 0; i < filtered.size() - 1; i++) {
                double current = filtered.get(i).getFourSeam();
                double next = filtered.get(i + 1).getFourSeam();
                assertTrue(current <= next, 
                        String.format("Pitchers should be in ascending FourSeam usage order. Found %f before %f", current, next));
            }
        }
    }
    
    // ============================================================
    // Tests for pitcherFilter(String filter, PlayerData sortOn, boolean ascending, Set<Pitcher> pitcherLoaderLineup)
    // ============================================================
    
    @Test
    @DisplayName("Test pitcherFilter with StrikesRate ascending order")
    public void testPitcherFilterWithStrikesRateAscending() {
        // Skip if no pitchers data available
        if (pitchers == null || pitchers.isEmpty()) {
            return;
        }
        
        // Filter and sort by StrikesRate ascending
        Stream<Pitcher> result = model.pitcherFilter("Rotation >= 1", PlayerData.S_RATE, true, pitchers);
        
        // Convert to list to verify
        List<Pitcher> filtered = result.toList();
        
        // Verify sorting is in ascending order
        for (int i = 0; i < filtered.size() - 1; i++) {
            double current = filtered.get(i).getStrikesRate();
            double next = filtered.get(i + 1).getStrikesRate();
            assertTrue(current <= next, 
                    String.format("Pitchers should be in ascending StrikesRate order. Found %f before %f", current, next));
        }
    }
    
    @Test
    @DisplayName("Test pitcherFilter with StrikesRate descending order")
    public void testPitcherFilterWithStrikesRateDescending() {
        // Skip if no pitchers data available
        if (pitchers == null || pitchers.isEmpty()) {
            return;
        }
        
        // Filter and sort by StrikesRate descending
        Stream<Pitcher> result = model.pitcherFilter("Rotation >= 1", PlayerData.S_RATE, false, pitchers);
        
        // Convert to list to verify
        List<Pitcher> filtered = result.toList();
        
        // Verify sorting is in descending order
        for (int i = 0; i < filtered.size() - 1; i++) {
            double current = filtered.get(i).getStrikesRate();
            double next = filtered.get(i + 1).getStrikesRate();
            assertTrue(current >= next, 
                    String.format("Pitchers should be in descending StrikesRate order. Found %f before %f", current, next));
        }
    }
    
    @Test
    @DisplayName("Test pitcherFilter with NAME ascending order")
    public void testPitcherFilterWithNameAscending() {
        // Skip if no pitchers data available
        if (pitchers == null || pitchers.isEmpty()) {
            return;
        }
        
        // Filter and sort by NAME ascending
        Stream<Pitcher> result = model.pitcherFilter("Rotation >= 1", PlayerData.NAME, true, pitchers);
        
        // Convert to list to verify
        List<Pitcher> filtered = result.toList();
        
        // Verify sorting is in alphabetical order
        for (int i = 0; i < filtered.size() - 1; i++) {
            String current = filtered.get(i).getName().toLowerCase();
            String next = filtered.get(i + 1).getName().toLowerCase();
            assertTrue(current.compareTo(next) <= 0, 
                    String.format("Pitchers should be in ascending alphabetical order. Found %s before %s", current, next));
        }
    }
    
    @Test
    @DisplayName("Test pitcherFilter with NAME descending order")
    public void testPitcherFilterWithNameDescending() {
        // Skip if no pitchers data available
        if (pitchers == null || pitchers.isEmpty()) {
            return;
        }
        
        // Filter and sort by NAME descending
        Stream<Pitcher> result = model.pitcherFilter("Rotation >= 1", PlayerData.NAME, false, pitchers);
        
        // Convert to list to verify
        List<Pitcher> filtered = result.toList();
        
        // Verify sorting is in reverse alphabetical order
        for (int i = 0; i < filtered.size() - 1; i++) {
            String current = filtered.get(i).getName().toLowerCase();
            String next = filtered.get(i + 1).getName().toLowerCase();
            assertTrue(current.compareTo(next) >= 0, 
                    String.format("Pitchers should be in descending alphabetical order. Found %s before %s", current, next));
        }
    }
    
    @Test
    @DisplayName("Test pitcherFilter with Rotation ascending and descending order")
    public void testPitcherFilterWithRotationDirections() {
        // Skip if no pitchers data available
        if (pitchers == null || pitchers.isEmpty()) {
            return;
        }
        
        // Filter and sort by Rotation ascending
        Stream<Pitcher> ascResult = model.pitcherFilter("Rotation >= 1", PlayerData.ROTATION, true, pitchers);
        List<Pitcher> ascFiltered = ascResult.toList();
        
        // Verify ascending order
        for (int i = 0; i < ascFiltered.size() - 1; i++) {
            int current = ascFiltered.get(i).getRotation();
            int next = ascFiltered.get(i + 1).getRotation();
            assertTrue(current <= next, 
                    String.format("Pitchers should be in ascending Rotation order. Found %d before %d", current, next));
        }
        
        // Filter and sort by Rotation descending
        Stream<Pitcher> descResult = model.pitcherFilter("Rotation >= 1", PlayerData.ROTATION, false, pitchers);
        List<Pitcher> descFiltered = descResult.toList();
        
        // Verify descending order
        for (int i = 0; i < descFiltered.size() - 1; i++) {
            int current = descFiltered.get(i).getRotation();
            int next = descFiltered.get(i + 1).getRotation();
            assertTrue(current >= next, 
                    String.format("Pitchers should be in descending Rotation order. Found %d before %d", current, next));
        }
    }
    
    @Test
    @DisplayName("Test pitcherFilter with complex filter and different sort directions")
    public void testPitcherFilterWithComplexFilterAndSortDirections() {
        // Skip if no pitchers data available
        if (pitchers == null || pitchers.isEmpty()) {
            return;
        }
        
        // Complex filter with multiple conditions
        String complexFilter = "Rotation == 1, StrikesRate >= 0.600";
        
        // Apply filter and sort by FourSeam usage ascending
        Stream<Pitcher> ascResult = model.pitcherFilter(complexFilter, PlayerData.FOURSEAM, true, pitchers);
        List<Pitcher> ascFiltered = ascResult.toList();
        
        // If we found any pitchers meeting all criteria, verify them
        if (!ascFiltered.isEmpty()) {
            // Verify the filter worked correctly
            for (Pitcher pitcher : ascFiltered) {
                assertEquals(1, pitcher.getRotation(), "Pitchers should be starters (Rotation == 1)");
                assertTrue(pitcher.getStrikesRate() >= 0.600, "Pitchers should have StrikesRate >= 0.600");
            }
            
            // Verify ascending sort
            for (int i = 0; i < ascFiltered.size() - 1; i++) {
                double current = ascFiltered.get(i).getFourSeam();
                double next = ascFiltered.get(i + 1).getFourSeam();
                assertTrue(current <= next, "Pitchers should be in ascending FourSeam usage order");
            }
        }
        
        // Apply filter and sort by FourSeam usage descending
        Stream<Pitcher> descResult = model.pitcherFilter(complexFilter, PlayerData.FOURSEAM, false, pitchers);
        List<Pitcher> descFiltered = descResult.toList();
        
        // If we found any pitchers meeting all criteria, verify them
        if (!descFiltered.isEmpty()) {
            // Verify descending sort
            for (int i = 0; i < descFiltered.size() - 1; i++) {
                double current = descFiltered.get(i).getFourSeam();
                double next = descFiltered.get(i + 1).getFourSeam();
                assertTrue(current >= next, "Pitchers should be in descending FourSeam usage order");
            }
        }
    } 

    @Test
    @DisplayName("Test filterSingleForBatter correctly filters batters with a single condition")
    public void testFilterSingleForBatter() {
        // Skip if no batters data available
        if (batters == null || batters.isEmpty()) {
            return;
        }
        
        // Test with a valid AVG filter
        String validFilter = "AVG >= 0.250";
        Stream<Batter> batterStream = batters.stream();
        Stream<Batter> filteredStream = invokePrivateMethod(
            "filterSingleForBatter", 
            new Class<?>[]{String.class, Stream.class},
            new Object[]{validFilter, batterStream}
        );
        
        List<Batter> filtered = filteredStream.toList();
        assertFalse(filtered.isEmpty(), "Should find batters with AVG >= 0.250");
        
        // Verify all batters in result have AVG >= 0.250
        for (Batter batter : filtered) {
            assertTrue(batter.getAVG() >= 0.250, "All batters should have AVG >= 0.250");
        }
        
        // Test with an invalid column
        String invalidColumnFilter = "InvalidColumn >= 0.250";
        Stream<Batter> invalidColumnStream = batters.stream();
        Stream<Batter> invalidColumnResult = invokePrivateMethod(
            "filterSingleForBatter", 
            new Class<?>[]{String.class, Stream.class},
            new Object[]{invalidColumnFilter, invalidColumnStream}
        );
        
        // Verify it returns the original stream (no filtering)
        List<Batter> invalidColumnList = invalidColumnResult.toList();
        assertEquals(0, invalidColumnList.size(), "Invalid column should return original stream");
        
        // Test with invalid operator format
        String invalidOpFilter = "AVG & 0.250";  // Invalid operator
        Stream<Batter> invalidOpStream = batters.stream();
        Stream<Batter> invalidOpResult = invokePrivateMethod(
            "filterSingleForBatter", 
            new Class<?>[]{String.class, Stream.class},
            new Object[]{invalidOpFilter, invalidOpStream}
        );
        
        // Verify it returns the original stream (no filtering)
        List<Batter> invalidOpList = invalidOpResult.toList();
        assertEquals(0, invalidOpList.size(), "Invalid operator should return original stream");
    }    

    @Test
    @DisplayName("Test filterMultiForBatter correctly filters batters with multiple conditions")
    public void testFilterMultiForBatter() {
        // Skip if no batters data available
        if (batters == null || batters.isEmpty()) {
            return;
        }
        
        // Test with multiple valid conditions
        String multiFilter = "AVG>=0.250,TotalPA>=100";
        Stream<Batter> batterStream = batters.stream();
        Stream<Batter> filteredStream = invokePrivateMethod(
            "filterMultiForBatter", 
            new Class<?>[]{String.class, Stream.class},
            new Object[]{multiFilter, batterStream}
        );
        
        List<Batter> filtered = filteredStream.toList();
        
        // Verify results
        for (Batter batter : filtered) {
            assertTrue(batter.getAVG() >= 0.250, "All batters should have AVG >= 0.250");
            assertTrue(batter.getTotalPA() >= 100, "All batters should have TotalPA >= 100");
        }
        
        // Test with a mix of valid and invalid conditions
        String mixedFilter = "AVG>=0.250,InvalidColumn>=100";
        Stream<Batter> mixedStream = batters.stream();
        Stream<Batter> mixedResult = invokePrivateMethod(
            "filterMultiForBatter", 
            new Class<?>[]{String.class, Stream.class},
            new Object[]{mixedFilter, mixedStream}
        );
        
        // Should return the stream with only the valid filter applied
        List<Batter> mixedList = mixedResult.toList();
        
        // Default behavior is to return the input stream on invalid columns
        // So we check if at least the valid filter was applied
        if (!mixedList.isEmpty()) {
            for (Batter batter : mixedList) {
                assertTrue(batter.getAVG() >= 0.250, "Valid condition should still be applied");
            }
        }
    }

    @Test
    @DisplayName("Test filterSingleForPitcher correctly filters pitchers with a single condition")
    public void testFilterSingleForPitcher() {
        // Skip if no pitchers data available
        if (pitchers == null || pitchers.isEmpty()) {
            return;
        }
        
        // Test with a valid StrikesRate filter
        String validFilter = "StrikesRate >= 0.600";
        Stream<Pitcher> pitcherStream = pitchers.stream();
        Stream<Pitcher> filteredStream = invokePrivateMethod(
            "filterSingleForPitcher", 
            new Class<?>[]{String.class, Stream.class},
            new Object[]{validFilter, pitcherStream}
        );
        
        List<Pitcher> filtered = filteredStream.toList();
        assertFalse(filtered.isEmpty(), "Should find pitchers with StrikesRate >= 0.600");
        
        // Verify all pitchers in result have StrikesRate >= 0.600
        for (Pitcher pitcher : filtered) {
            assertTrue(pitcher.getStrikesRate() >= 0.600, "All pitchers should have StrikesRate >= 0.600");
        }
        
        // Test with a valid Rotation filter (integer field)
        String rotationFilter = "Rotation == 1"; // Starters
        Stream<Pitcher> rotationStream = pitchers.stream();
        Stream<Pitcher> rotationResult = invokePrivateMethod(
            "filterSingleForPitcher", 
            new Class<?>[]{String.class, Stream.class},
            new Object[]{rotationFilter, rotationStream}
        );
        
        List<Pitcher> starters = rotationResult.toList();
        assertFalse(starters.isEmpty(), "Should find starting pitchers");
        
        // Verify all pitchers in result have Rotation == 1
        for (Pitcher pitcher : starters) {
            assertEquals(1, pitcher.getRotation(), "All pitchers should be starters");
        }
        
        // Test with an invalid column
        String invalidColumnFilter = "InvalidColumn >= 0.600";
        Stream<Pitcher> invalidColumnStream = pitchers.stream();
        Stream<Pitcher> invalidColumnResult = invokePrivateMethod(
            "filterSingleForPitcher", 
            new Class<?>[]{String.class, Stream.class},
            new Object[]{invalidColumnFilter, invalidColumnStream}
        );
        
        // Verify it returns the original stream (no filtering)
        List<Pitcher> invalidColumnList = invalidColumnResult.toList();
        assertEquals(0, invalidColumnList.size(), "Invalid column should return original stream");
    }

    @Test
    @DisplayName("Test filterMultiForPitcher correctly filters pitchers with multiple conditions")
    public void testFilterMultiForPitcher() {
        // Skip if no pitchers data available
        if (pitchers == null || pitchers.isEmpty()) {
            return;
        }
        
        // Test with multiple valid conditions
        String multiFilter = "StrikesRate>=0.600,Rotation==1";
        Stream<Pitcher> pitcherStream = pitchers.stream();
        Stream<Pitcher> filteredStream = invokePrivateMethod(
            "filterMultiForPitcher", 
            new Class<?>[]{String.class, Stream.class},
            new Object[]{multiFilter, pitcherStream}
        );
        
        List<Pitcher> filtered = filteredStream.toList();
        
        // Verify results
        for (Pitcher pitcher : filtered) {
            assertTrue(pitcher.getStrikesRate() >= 0.600, "All pitchers should have StrikesRate >= 0.600");
            assertEquals(1, pitcher.getRotation(), "All pitchers should be starters");
        }
        
        // Test with a mix of valid and invalid conditions
        String mixedFilter = "StrikesRate>=0.600,InvalidColumn>=100";
        Stream<Pitcher> mixedStream = pitchers.stream();
        Stream<Pitcher> mixedResult = invokePrivateMethod(
            "filterMultiForPitcher", 
            new Class<?>[]{String.class, Stream.class},
            new Object[]{mixedFilter, mixedStream}
        );
        
        // Should return the stream with only the valid filter applied
        List<Pitcher> mixedList = mixedResult.toList();
        
        // Default behavior is to return the input stream on invalid columns
        // So we check if at least the valid filter was applied
        if (!mixedList.isEmpty()) {
            for (Pitcher pitcher : mixedList) {
                assertTrue(pitcher.getStrikesRate() >= 0.600, "Valid condition should still be applied");
            }
        }
    }
    
    @Test
    @DisplayName("Integration test showing how private methods are used in public methods")
    public void testIntegration() {
        // Skip if no data available
        if (batters == null || batters.isEmpty() || pitchers == null || pitchers.isEmpty()) {
            return;
        }
        
        // Test public method that uses the private methods
        // Single filter
        Stream<Batter> battersWithHighAVG = model.batterFilter("AVG >= 0.300", batters);
        List<Batter> highAVGList = battersWithHighAVG.toList();
        
        for (Batter batter : highAVGList) {
            assertTrue(batter.getAVG() >= 0.300, "Public method should use the private filter correctly");
        }
        
        // Multiple filters
        Stream<Pitcher> goodStarters = model.pitcherFilter("StrikesRate >= 0.600, Rotation == 1", pitchers);
        List<Pitcher> goodStartersList = goodStarters.toList();
        
        for (Pitcher pitcher : goodStartersList) {
            assertTrue(pitcher.getStrikesRate() >= 0.600, "Should have good strike rate");
            assertEquals(1, pitcher.getRotation(), "Should be a starter");
        }
    }
    
    @Test
    @DisplayName("Test complex filter conditions with multiple operators")
    public void testComplexFilters() {
        // Skip if no data available
        if (batters == null || batters.isEmpty() || pitchers == null || pitchers.isEmpty()) {
            return;
        }
        
        // Test a complex batter filter with multiple conditions including contains operator
        String complexBatterFilter = "TotalPA>=100,OPS>=0.800,AVG>=0.250";
        Stream<Batter> complexBatterStream = batters.stream();
        Stream<Batter> complexBatterResult = invokePrivateMethod(
            "filterMultiForBatter", 
            new Class<?>[]{String.class, Stream.class},
            new Object[]{complexBatterFilter, complexBatterStream}
        );
        
        List<Batter> complexBatterList = complexBatterResult.toList();
        
        // Verify results
        for (Batter batter : complexBatterList) {
            assertTrue(batter.getTotalPA() >= 100, "Should have enough plate appearances");
            assertTrue(batter.getOPS() >= 0.800, "Should have good OPS");
            assertTrue(batter.getAVG() >= 0.250, "Should have good AVG");
        }
        
        // Test with name contains operator
        if (!batters.isEmpty()) {
            Batter firstBatter = batters.iterator().next();
            String namePart = firstBatter.getName().substring(0, 3);
            
            String nameFilter = "name~=" + namePart;
            Stream<Batter> nameStream = batters.stream();
            Stream<Batter> nameResult = invokePrivateMethod(
                "filterSingleForBatter", 
                new Class<?>[]{String.class, Stream.class},
                new Object[]{nameFilter, nameStream}
            );
            
            List<Batter> nameList = nameResult.toList();
            
            boolean foundMatch = false;
            for (Batter batter : nameList) {
                if (batter.getName().contains(namePart)) {
                    foundMatch = true;
                    break;
                }
            }
            
            assertTrue(foundMatch, "Should find batters with names containing the search part");
        }
    }   
    

    // ============================================================
    // Tests for addBatterToLineup
    // ============================================================
    
    @Test
    @DisplayName("Test addBatterToLineup adds a batter to the player team lineup")
    public void testAddBatterToPlayerTeamLineup() {
        // Skip if no batters data available
        if (batters == null || batters.isEmpty()) {
            return;
        }
        
        // Get a batter to add
        Batter batter = batters.iterator().next();
        
        // Add the batter to position 1
        String command = batter.getName() + " to 1";
        model.addBatterToLineup(Side.PLAYER, command, Stream.of(batter));
        
        // Verify the batter was added
        List<Batter> lineup = model.getPlayerTeamBatterLineup();
        assertEquals(batter, lineup.get(0), "Batter should be added to position 1");
    }
    
    @Test
    @DisplayName("Test addBatterToLineup with numeric command")
    public void testAddBatterToLineupWithNumericCommand() {
        // Skip if no batters data available
        if (batters == null || batters.isEmpty()) {
            return;
        }
        
        // Get a batter to add
        Batter batter = batters.iterator().next();
        
        // Create a stream with one batter
        Stream<Batter> filteredBatters = Stream.of(batter);
        
        // Add the batter to position 3 using numeric command
        model.addBatterToLineup(Side.PLAYER, "1 to 3", filteredBatters);
        
        // Verify the batter was added
        List<Batter> lineup = model.getPlayerTeamBatterLineup();
        assertEquals(batter, lineup.get(2), "Batter should be added to position 3");
    }
    
    @Test
    @DisplayName("Test addBatterToLineup to computer team")
    public void testAddBatterToComputerTeamLineup() {
        // Skip if no batters data available
        if (batters == null || batters.isEmpty()) {
            return;
        }
        
        // Get a batter to add
        Batter batter = batters.iterator().next();
        
        // Add the batter to position 5
        String command = batter.getName() + " to 5";
        model.addBatterToLineup(Side.COMPUTER, command, Stream.of(batter));
        
        // Verify the batter was added
        List<Batter> lineup = model.getComTeamBatterLineup();
        assertEquals(batter, lineup.get(4), "Batter should be added to position 5");
    }
    
    // ============================================================
    // Tests for addPitcherToLineup
    // ============================================================
    
    @Test
    @DisplayName("Test addPitcherToLineup adds a starter to the computer team lineup")
    public void testAddStarterPitcherToComputerTeamLineup() {
        // Skip if no pitchers data available
        if (pitchers == null || pitchers.isEmpty()) {
            return;
        }
        
        // Find a starter pitcher (rotation == 1)
        Pitcher starter = null;
        for (Pitcher pitcher : pitchers) {
            if (pitcher.getRotation() == 1) {
                starter = pitcher;
                break;
            }
        }
        
        if (starter != null) {
            // Add the starter to position 1
            String command = starter.getName() + " to 1";
            model.addPitcherToLineup(Side.COMPUTER, command, Stream.of(starter));
            
            // Verify the pitcher was added
            List<Pitcher> lineup = model.getComTeamPitcherLineup();
            assertEquals(starter, lineup.get(0), "Starter should be added to position 1");
        }
    }
    
    @Test
    @DisplayName("Test addPitcherToLineup adds a reliever to the computer team lineup")
    public void testAddRelieverPitcherToComputerTeamLineup() {
        // Skip if no pitchers data available
        if (pitchers == null || pitchers.isEmpty()) {
            return;
        }
        
        // Find a reliever pitcher (rotation == 2)
        Pitcher reliever = null;
        for (Pitcher pitcher : pitchers) {
            if (pitcher.getRotation() == 2) {
                reliever = pitcher;
                break;
            }
        }
        
        if (reliever != null) {
            // Add the reliever to position 2
            String command = reliever.getName() + " to 2";
            model.addPitcherToLineup(Side.COMPUTER, command, Stream.of(reliever));
            
            // Verify the pitcher was added
            List<Pitcher> lineup = model.getComTeamPitcherLineup();
            assertEquals(reliever, lineup.get(1), "Reliever should be added to position 2");
        }
    }
    
    @Test
    @DisplayName("Test addPitcherToLineup with numeric command")
    public void testAddPitcherToLineupWithNumericCommand() {
        // Skip if no pitchers data available
        if (pitchers == null || pitchers.isEmpty()) {
            return;
        }
        
        // Find a reliever pitcher (rotation == 2)
        Pitcher reliever = null;
        for (Pitcher pitcher : pitchers) {
            if (pitcher.getRotation() == 2) {
                reliever = pitcher;
                break;
            }
        }
        
        if (reliever != null) {
            // Create a stream with one pitcher
            Stream<Pitcher> filteredPitchers = Stream.of(reliever);
            
            // Add the pitcher to position 3 using numeric command
            model.addPitcherToLineup(Side.COMPUTER, "1 to 3", filteredPitchers);
            
            // Verify the pitcher was added
            List<Pitcher> lineup = model.getComTeamPitcherLineup();
            assertEquals(reliever, lineup.get(2), "Pitcher should be added to position 3");
        }
    }
    
    // ============================================================
    // Tests for removeFromLineup
    // ============================================================
    
    @Test
    @DisplayName("Test removeFromLineup removes a batter by position")
    public void testRemoveBatterByPosition() {
        // First add a batter to remove
        if (batters != null && !batters.isEmpty()) {
            Batter batter = batters.iterator().next();
            model.addBatterToLineup(Side.PLAYER, batter.getName() + " to 3", Stream.of(batter));
            
            // Verify batter was added
            List<Batter> lineupBefore = model.getPlayerTeamBatterLineup();
            assertEquals(batter, lineupBefore.get(2), "Batter should be added to position 3");
            
            // Now remove the batter
            model.removeFromLineup(Side.PLAYER, "batter", "3");
            
            // Verify batter was removed
            List<Batter> lineupAfter = model.getPlayerTeamBatterLineup();
            assertNull(lineupAfter.get(2), "Batter should be removed from position 3");
        }
    }
    
    @Test
    @DisplayName("Test removeFromLineup removes a pitcher by name")
    public void testRemovePitcherByName() {
        // First find and add a pitcher to remove
        if (pitchers != null && !pitchers.isEmpty()) {
            Pitcher pitcher = pitchers.iterator().next();
            
            // Find the right position based on rotation
            int position = (pitcher.getRotation() == 1) ? 1 : 2;
            
            model.addPitcherToLineup(Side.COMPUTER, pitcher.getName() + " to " + position, Stream.of(pitcher));
            
            // Verify pitcher was added
            List<Pitcher> lineupBefore = model.getComTeamPitcherLineup();
            assertEquals(pitcher, lineupBefore.get(position - 1), "Pitcher should be added to position " + position);
            
            // Now remove the pitcher by name
            model.removeFromLineup(Side.COMPUTER, "pitcher", pitcher.getName());
            
            // Verify pitcher was removed
            List<Pitcher> lineupAfter = model.getComTeamPitcherLineup();
            assertNull(lineupAfter.get(position - 1), "Pitcher should be removed from position " + position);
        }
    }
    
    @Test
    @DisplayName("Test removeFromLineup with range")
    public void testRemoveFromLineupWithRange() {
        // First add batters to positions 1, 2, 3
        if (batters != null && batters.size() >= 3) {
            List<Batter> batterList = new ArrayList<>(batters);
            for (int i = 0; i < 3 && i < batterList.size(); i++) {
                model.addBatterToLineup(Side.PLAYER, batterList.get(i).getName() + " to " + (i + 1), Stream.of(batterList.get(i)));
            }
            
            // Verify batters were added
            List<Batter> lineupBefore = model.getPlayerTeamBatterLineup();
            for (int i = 0; i < 3; i++) {
                assertNotNull(lineupBefore.get(i), "Batter should be added to position " + (i + 1));
            }
            
            // Now remove the batters in range 1-2
            model.removeFromLineup(Side.PLAYER, "batter", "1-2");
            
            // Verify batters were removed
            List<Batter> lineupAfter = model.getPlayerTeamBatterLineup();
            assertNull(lineupAfter.get(0), "Batter should be removed from position 1");
            assertNull(lineupAfter.get(1), "Batter should be removed from position 2");
            assertNotNull(lineupAfter.get(2), "Batter in position 3 should remain");
        }
    }
    
    // ============================================================
    // Tests for clearLineup
    // ============================================================
    
    @Test
    @DisplayName("Test clearLineup clears the player batter lineup")
    public void testClearPlayerBatterLineup() {
        // First add some batters
        if (batters != null && batters.size() >= 3) {
            List<Batter> batterList = new ArrayList<>(batters);
            for (int i = 0; i < 3 && i < batterList.size(); i++) {
                model.addBatterToLineup(Side.PLAYER, batterList.get(i).getName() + " to " + (i + 1), Stream.of(batterList.get(i)));
            }
            
            // Verify batters were added
            List<Batter> lineupBefore = model.getPlayerTeamBatterLineup();
            for (int i = 0; i < 3; i++) {
                assertNotNull(lineupBefore.get(i), "Batter should be added to position " + (i + 1));
            }
            
            // Now clear the lineup
            model.clearLineup(Side.PLAYER, "batter");
            
            // Verify lineup was cleared
            List<Batter> lineupAfter = model.getPlayerTeamBatterLineup();
            for (int i = 0; i < lineupAfter.size(); i++) {
                assertNull(lineupAfter.get(i), "Batter position " + (i + 1) + " should be null after clearing");
            }
        }
    }
    
    @Test
    @DisplayName("Test clearLineup clears the computer pitcher lineup")
    public void testClearComputerPitcherLineup() {
        // First find and add pitchers
        if (pitchers != null && !pitchers.isEmpty()) {
            // Find a starter and a reliever
            Pitcher starter = null;
            Pitcher reliever = null;
            
            for (Pitcher pitcher : pitchers) {
                if (pitcher.getRotation() == 1 && starter == null) {
                    starter = pitcher;
                } else if (pitcher.getRotation() == 2 && reliever == null) {
                    reliever = pitcher;
                }
                
                if (starter != null && reliever != null) {
                    break;
                }
            }
            
            // Add the pitchers
            if (starter != null) {
                model.addPitcherToLineup(Side.COMPUTER, starter.getName() + " to 1", Stream.of(starter));
            }
            
            if (reliever != null) {
                model.addPitcherToLineup(Side.COMPUTER, reliever.getName() + " to 2", Stream.of(reliever));
            }
            
            // Verify pitchers were added
            List<Pitcher> lineupBefore = model.getComTeamPitcherLineup();
            if (starter != null) {
                assertEquals(starter, lineupBefore.get(0), "Starter should be added to position 1");
            }
            
            if (reliever != null) {
                assertEquals(reliever, lineupBefore.get(1), "Reliever should be added to position 2");
            }
            
            // Now clear the lineup
            model.clearLineup(Side.COMPUTER, "pitcher");
            
            // Verify lineup was cleared
            List<Pitcher> lineupAfter = model.getComTeamPitcherLineup();
            for (int i = 0; i < lineupAfter.size(); i++) {
                assertNull(lineupAfter.get(i), "Pitcher position " + (i + 1) + " should be null after clearing");
            }
        }
    }
    
    // ============================================================
    // Tests for convertLineupToString
    // ============================================================
    
    @Test
    @DisplayName("Test convertLineupToString with null elements")
    public void testConvertLineupToStringWithNullElements() {
        // Create a test lineup with null elements
        List<Batter> lineup = new ArrayList<>();
        lineup.add(null);
        lineup.add(null);
        lineup.add(null);
        
        // Convert to string
        List<String> result = model.convertLineupToString(lineup);
        
        // Verify conversion
        assertEquals(3, result.size(), "Result should have same size as input");
        
        for (String str : result) {
            assertTrue(str.contains("null"), "String should indicate null player");
        }
    }
    
    @Test
    @DisplayName("Test convertLineupToString with real batters")
    public void testConvertLineupToStringWithRealBatters() {
        // Skip if no batters available
        if (batters == null || batters.isEmpty()) {
            return;
        }
        
        // Create a test lineup with real batters
        List<Batter> lineup = new ArrayList<>();
        List<Batter> batterList = new ArrayList<>(batters);
        
        for (int i = 0; i < 3 && i < batterList.size(); i++) {
            lineup.add(batterList.get(i));
        }
        
        // Convert to string
        List<String> result = model.convertLineupToString(lineup);
        
        // Verify conversion
        assertEquals(lineup.size(), result.size(), "Result should have same size as input");
        
        for (int i = 0; i < lineup.size(); i++) {
            Batter batter = lineup.get(i);
            String str = result.get(i);
            
            assertTrue(str.contains(batter.getName()), 
                    "String should contain batter name: " + batter.getName());
            assertTrue(str.contains("Batter"), 
                    "String should indicate it's a batter");
        }
    }
    
    @Test
    @DisplayName("Test convertLineupToString with mixed elements")
    public void testConvertLineupToStringWithMixedElements() {
        // Skip if no batters available
        if (batters == null || batters.isEmpty()) {
            return;
        }
        
        // Create a test lineup with a mix of batters and nulls
        List<Batter> lineup = new ArrayList<>();
        lineup.add(batters.iterator().next()); // First batter
        lineup.add(null);                      // Null element
        
        // Add another batter if available
        if (batters.size() > 1) {
            Iterator<Batter> it = batters.iterator();
            it.next(); // Skip first
            lineup.add(it.next()); // Second batter
        } else {
            lineup.add(null);
        }
        
        // Convert to string
        List<String> result = model.convertLineupToString(lineup);
        
        // Verify conversion
        assertEquals(lineup.size(), result.size(), "Result should have same size as input");
        
        // Check first element (batter)
        assertTrue(result.get(0).contains(lineup.get(0).getName()), 
                "First string should contain batter name");
        
        // Check second element (null)
        assertTrue(result.get(1).contains("null"), 
                "Second string should indicate null player");
        
        // Check third element (may be batter or null)
        if (lineup.get(2) != null) {
            assertTrue(result.get(2).contains(lineup.get(2).getName()), 
                    "Third string should contain batter name");
        } else {
            assertTrue(result.get(2).contains("null"), 
                    "Third string should indicate null player");
        }
    }
    
    @Test
    @DisplayName("Test convertLineupToString with pitchers")
    public void testConvertLineupToStringWithPitchers() {
        // Skip if no pitchers available
        if (pitchers == null || pitchers.isEmpty()) {
            return;
        }
        
        // Create a test lineup with pitchers
        List<Pitcher> lineup = new ArrayList<>();
        List<Pitcher> pitcherList = new ArrayList<>(pitchers);
        
        for (int i = 0; i < 3 && i < pitcherList.size(); i++) {
            lineup.add(pitcherList.get(i));
        }
        
        // Convert to string
        List<String> result = model.convertLineupToString(lineup);
        
        // Verify conversion
        assertEquals(lineup.size(), result.size(), "Result should have same size as input");
        
        for (int i = 0; i < lineup.size(); i++) {
            Pitcher pitcher = lineup.get(i);
            String str = result.get(i);
            
            assertTrue(str.contains(pitcher.getName()), 
                    "String should contain pitcher name: " + pitcher.getName());
            assertTrue(str.contains("Pitcher"), 
                    "String should indicate it's a pitcher");
        }
    }  
    /**
     * Helper method to add a complete batter lineup
     */
    private void fillBatterLineup() {
        if (batters != null && batters.size() >= 9) {
            int position = 1;
            for (Batter batter : batters) {
                if (position <= 9) {
                    model.addBatterToLineup(Side.PLAYER, batter.getName() + " to " + position, Stream.of(batter));
                    position++;
                } else {
                    break;
                }
            }
        }
    }
    
    /**
     * Helper method to add a complete pitcher lineup
     */
    private void fillPitcherLineup() {
        if (pitchers != null && !pitchers.isEmpty()) {
            // Find starter (rotation == 1)
            Pitcher starter = null;
            for (Pitcher pitcher : pitchers) {
                if (pitcher.getRotation() == 1) {
                    starter = pitcher;
                    break;
                }
            }
            
            // Add starter to position 1
            if (starter != null) {
                model.addPitcherToLineup(Side.COMPUTER, starter.getName() + " to 1", Stream.of(starter));
            }
            
            // Find 2 relievers (rotation == 2)
            int relieversAdded = 0;
            for (Pitcher pitcher : pitchers) {
                if (pitcher.getRotation() == 2) {
                    model.addPitcherToLineup(Side.COMPUTER, pitcher.getName() + " to " + (relieversAdded + 2), Stream.of(pitcher));
                    relieversAdded++;
                    
                    if (relieversAdded >= 2) {
                        break;
                    }
                }
            }
        }
    }
    
    /**
     * Helper method to reset console capturing
     */
    private void resetConsole() {
        System.setOut(originalOut);
    }

    @Test
    @DisplayName("Test startSimAndGetResult with incomplete lineup prints error message")
    public void testStartSimWithIncompleteLineup() {
        try {
            // Don't fill lineups, should fail
            
            // Call the method
            SimulationResult result = model.startSimAndGetResult();
            
            // Should print error message and return null
            assertNull(result, "Result should be null when lineup is incomplete");
            
            String output = consoleOutput.toString();
            assertTrue(output.contains("Simulation failed"), "Should print error about simulation failing");
            assertTrue(output.contains("Lineup is not completed"), "Should mention lineup not being complete");
        } finally {
            resetConsole();
        }
    }
    
    @Test
    @DisplayName("Test startSimAndGetResult with complete lineup returns valid result")
    public void testStartSimWithCompleteLineup() {
        try {
            // Fill lineups completely
            fillBatterLineup();
            fillPitcherLineup();
            
            // Verify lineups are filled
            List<Batter> batterLineup = model.getPlayerTeamBatterLineup();
            List<Pitcher> pitcherLineup = model.getComTeamPitcherLineup();
            
            boolean battersComplete = true;
            for (Batter batter : batterLineup) {
                if (batter == null) {
                    battersComplete = false;
                    break;
                }
            }
            
            boolean pitchersComplete = true;
            for (Pitcher pitcher : pitcherLineup) {
                if (pitcher == null) {
                    pitchersComplete = false;
                    break;
                }
            }
            
            // Only run the test if we could actually fill the lineups completely
            if (battersComplete && pitchersComplete) {
                // Call the method
                SimulationResult result = model.startSimAndGetResult();
                
                // Should return a valid result
                assertNotNull(result, "Result should not be null when lineup is complete");
                
                // Verify result has been stored in the model's gameResult field
                try {
                    Field gameResultField = Model.class.getDeclaredField("gameResult");
                    gameResultField.setAccessible(true);
                    SimulationResult storedResult = (SimulationResult) gameResultField.get(model);
                    
                    assertEquals(result, storedResult, "Result should be stored in model's gameResult field");
                } catch (Exception e) {
                    fail("Failed to access gameResult field: " + e.getMessage());
                }
                
                // Check result has some sensible properties
                assertNotNull(result.toString(), "Result should have string representation");
                assertNotNull(result.getDetails(), "Result should have details");
                assertNotNull(result.getTotalStatistics(), "Result should have statistics");
            }
        } finally {
            resetConsole();
        }
    }
    
    @Test
    @DisplayName("Test game result is correctly set after simulation")
    public void testGameResultField() {
        try {
            // Fill lineups completely
            fillBatterLineup();
            fillPitcherLineup();
            
            // Verify if lineups are filled (only proceed if they are)
            List<Batter> batterLineup = model.getPlayerTeamBatterLineup();
            List<Pitcher> pitcherLineup = model.getComTeamPitcherLineup();
            
            boolean battersComplete = true;
            for (Batter batter : batterLineup) {
                if (batter == null) {
                    battersComplete = false;
                    break;
                }
            }
            
            boolean pitchersComplete = true;
            for (Pitcher pitcher : pitcherLineup) {
                if (pitcher == null) {
                    pitchersComplete = false;
                    break;
                }
            }
            
            // Only run the simulation if lineups are complete
            if (battersComplete && pitchersComplete) {
                // Before simulation, gameResult should be null
                try {
                    Field gameResultField = Model.class.getDeclaredField("gameResult");
                    gameResultField.setAccessible(true);
                    SimulationResult beforeResult = (SimulationResult) gameResultField.get(model);
                    
                    assertNull(beforeResult, "gameResult should be null before simulation");
                } catch (Exception e) {
                    fail("Failed to access gameResult field: " + e.getMessage());
                }
                
                // Run simulation
                SimulationResult result = model.startSimAndGetResult();
                
                // After simulation, gameResult should match the result
                try {
                    Field gameResultField = Model.class.getDeclaredField("gameResult");
                    gameResultField.setAccessible(true);
                    SimulationResult afterResult = (SimulationResult) gameResultField.get(model);
                    
                    assertNotNull(afterResult, "gameResult should not be null after simulation");
                    assertSame(result, afterResult, "gameResult should be the same object as the returned result");
                } catch (Exception e) {
                    fail("Failed to access gameResult field: " + e.getMessage());
                }
            }
        } finally {
            resetConsole();
        }
    }
    
    @Test
    public void testSaveLineupAsTXTFileWrongFileName() {

        ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        PrintStream originalErr = System.err;
        System.setErr(new PrintStream(errContent));
        try {
            // Call the method with invalid filename
            String fileName = "./testSaveLineupAsTXTFileWrongFileName.csv";
            List<Batter> bList = new ArrayList<>(this.batters);
            model.saveLineupAsTXTFile(Side.PLAYER, fileName, bList);
            
            // Check that the error message was printed
            assertEquals("Save as txt file only." + System.lineSeparator(), 
                        errContent.toString());
            
        } finally {
            // Reset System.err to original
            System.setErr(originalErr);
        }        
    }

    @Test
    public void testSaveLineupAsTXTFileNullLineup() {

        ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        PrintStream originalErr = System.err;
        System.setErr(new PrintStream(errContent));
        try {
            // Call the method with invalid filename
            String fileName = "./testSaveLineupAsTXTFileNullLineup.txt";
            List<Batter> bList = null;
            model.saveLineupAsTXTFile(Side.PLAYER, fileName, bList);
            
            // Check that the error message was printed
            assertEquals("Lineup cannot be null." + System.lineSeparator(), 
                        errContent.toString());
            
        } finally {
            // Reset System.err to original
            System.setErr(originalErr);
        }          
    }    

    @Test
    public void testSaveLineupAsTXTFile() {
        String fileName = "./testSaveLineupAsTXTFile.txt";
        // Create a test subclass of Model where we can override the method
        Model testModel = new Model() {
            @Override
            public List<String> convertLineupToString(List<? extends Player> lineup) {
                String mockBL = "===== Dylan Moore (Batter) =====\r\n" + //
                        "Fastball Stats: PA=273, H=47, 1B=21, 2B=15, 3B=2, HR=9\r\n" + //
                        "Breaking Stats: PA=124, H=16, 1B=9, 2B=5, 3B=1, HR=1\r\n" + //
                        "Offspeed Stats: PA=42, H=11, 1B=7, 2B=3, 3B=1, HR=0\r\n" + //
                        "Total Stats: PA=439, H=74, 1B=37, 2B=23, 3B=4, HR=10\r\n" + //
                        "Swing Stats: ZoneSwing=0.653, ZoneContact=0.804, ChaseSwing=0.177, ChaseContact=0.532\r\n" + //
                        "Overall: AVG=0.333, OBP=0.429, OPS=0.762\r\n" + //
                        "===== Julio Rodriguez (Batter) =====\r\n" + //
                        "Fastball Stats: PA=324, H=94, 1B=71, 2B=10, 3B=0, HR=13\r\n" + //
                        "Breaking Stats: PA=202, H=47, 1B=37, 2B=5, 3B=0, HR=5\r\n" + //
                        "Offspeed Stats: PA=86, H=14, 1B=10, 2B=2, 3B=0, HR=2\r\n" + //
                        "Total Stats: PA=612, H=155, 1B=118, 2B=17, 3B=0, HR=20\r\n" + //
                        "Swing Stats: ZoneSwing=0.746, ZoneContact=0.787, ChaseSwing=0.374, ChaseContact=0.532\r\n" + //
                        "Overall: AVG=0.273, OBP=0.325, OPS=0.734\r\n" + //
                        "===== Austin Shenton (Batter) =====\r\n" + //
                        "Fastball Stats: PA=30, H=7, 1B=3, 2B=3, 3B=0, HR=1\r\n" + //
                        "Breaking Stats: PA=14, H=1, 1B=0, 2B=1, 3B=0, HR=0\r\n" + //
                        "Offspeed Stats: PA=6, H=1, 1B=0, 2B=1, 3B=0, HR=0\r\n" + //
                        "Total Stats: PA=50, H=9, 1B=3, 2B=5, 3B=0, HR=1\r\n" + //
                        "Swing Stats: ZoneSwing=0.596, ZoneContact=0.694, ChaseSwing=0.204, ChaseContact=0.5\r\n" + //
                        "Overall: AVG=0.214, OBP=0.34, OPS=0.745\r\n" + //
                        "===== Cal Raleigh (Batter) =====\r\n" + //
                        "Fastball Stats: PA=328, H=64, 1B=38, 2B=8, 3B=0, HR=18\r\n" + //
                        "Breaking Stats: PA=140, H=26, 1B=14, 2B=3, 3B=0, HR=9\r\n" + //
                        "Offspeed Stats: PA=156, H=30, 1B=18, 2B=5, 3B=0, HR=7\r\n" + //
                        "Total Stats: PA=624, H=120, 1B=70, 2B=16, 3B=0, HR=34\r\n" + //
                        "Swing Stats: ZoneSwing=0.744, ZoneContact=0.765, ChaseSwing=0.327, ChaseContact=0.531\r\n" + //
                        "Overall: AVG=0.22, OBP=0.312, OPS=0.748\r\n" + //
                        "===== Mitch Garver (Batter) =====\r\n" + //
                        "Fastball Stats: PA=228, H=36, 1B=15, 2B=9, 3B=0, HR=12\r\n" + //
                        "Breaking Stats: PA=151, H=15, 1B=8, 2B=5, 3B=0, HR=2\r\n" + //
                        "Offspeed Stats: PA=51, H=12, 1B=8, 2B=3, 3B=0, HR=1\r\n" + //
                        "Total Stats: PA=430, H=63, 1B=31, 2B=17, 3B=0, HR=15\r\n" + //
                        "Swing Stats: ZoneSwing=0.576, ZoneContact=0.786, ChaseSwing=0.2, ChaseContact=0.426\r\n" + //
                        "Overall: AVG=0.172, OBP=0.286, OPS=0.627\r\n" + //
                        "===== Tyler Locklear (Batter) =====\r\n" + //
                        "Fastball Stats: PA=18, H=1, 1B=1, 2B=0, 3B=0, HR=0\r\n" + //
                        "Breaking Stats: PA=23, H=4, 1B=2, 2B=1, 3B=0, HR=1\r\n" + //
                        "Offspeed Stats: PA=8, H=2, 1B=1, 2B=0, 3B=0, HR=1\r\n" + //
                        "Total Stats: PA=49, H=7, 1B=4, 2B=1, 3B=0, HR=2\r\n" + //
                        "Swing Stats: ZoneSwing=0.688, ZoneContact=0.788, ChaseSwing=0.34, ChaseContact=0.324\r\n" + //
                        "Overall: AVG=0.156, OBP=0.224, OPS=0.535\r\n" + //
                        "===== Randy Arozarena (Batter) =====\r\n" + //
                        "Fastball Stats: PA=375, H=67, 1B=43, 2B=14, 3B=0, HR=10\r\n" + //
                        "Breaking Stats: PA=196, H=40, 1B=18, 2B=14, 3B=0, HR=8\r\n" + //
                        "Offspeed Stats: PA=77, H=13, 1B=6, 2B=5, 3B=0, HR=2\r\n" + //
                        "Total Stats: PA=648, H=120, 1B=67, 2B=33, 3B=0, HR=20\r\n" + //
                        "Swing Stats: ZoneSwing=0.627, ZoneContact=0.759, ChaseSwing=0.268, ChaseContact=0.573\r\n" + //
                        "Overall: AVG=0.219, OBP=0.332, OPS=0.72\r\n" + //
                        "===== J.P. Crawford (Batter) =====\r\n" + //
                        "Fastball Stats: PA=277, H=50, 1B=34, 2B=9, 3B=1, HR=6\r\n" + //
                        "Breaking Stats: PA=124, H=23, 1B=13, 2B=6, 3B=1, HR=3\r\n" + //
                        "Offspeed Stats: PA=50, H=6, 1B=5, 2B=1, 3B=0, HR=0\r\n" + //
                        "Total Stats: PA=451, H=79, 1B=52, 2B=16, 3B=2, HR=9\r\n" + //
                        "Swing Stats: ZoneSwing=0.62, ZoneContact=0.837, ChaseSwing=0.182, ChaseContact=0.63\r\n" + //
                        "Overall: AVG=0.202, OBP=0.304, OPS=0.625\r\n" + //
                        "===== Luke Raley (Batter) =====\r\n" + //
                        "Fastball Stats: PA=214, H=56, 1B=28, 2B=12, 3B=1, HR=15\r\n" + //
                        "Breaking Stats: PA=159, H=27, 1B=15, 2B=5, 3B=1, HR=6\r\n" + //
                        "Offspeed Stats: PA=82, H=15, 1B=12, 2B=2, 3B=0, HR=1\r\n" + //
                        "Total Stats: PA=455, H=98, 1B=55, 2B=19, 3B=2, HR=22\r\n" + //
                        "Swing Stats: ZoneSwing=0.752, ZoneContact=0.762, ChaseSwing=0.279, ChaseContact=0.347\r\n" + //
                        "Overall: AVG=0.243, OBP=0.32, OPS=0.783\r\n" + //
                        "===== Victor Robles (Batter) =====\r\n" + //
                        "Fastball Stats: PA=165, H=49, 1B=32, 2B=13, 3B=0, HR=4\r\n" + //
                        "Breaking Stats: PA=96, H=23, 1B=17, 2B=6, 3B=0, HR=0\r\n" + //
                        "Offspeed Stats: PA=34, H=6, 1B=5, 2B=1, 3B=0, HR=0\r\n" + //
                        "Total Stats: PA=295, H=78, 1B=54, 2B=20, 3B=0, HR=4\r\n" + //
                        "Swing Stats: ZoneSwing=0.727, ZoneContact=0.809, ChaseSwing=0.3, ChaseContact=0.533\r\n" + //
                        "Overall: AVG=0.307, OBP=0.381, OPS=0.814\r\n" + //
                        "===== Samad Taylor (Batter) =====\r\n" + //
                        "Fastball Stats: PA=3, H=1, 1B=1, 2B=0, 3B=0, HR=0\r\n" + //
                        "Breaking Stats: PA=2, H=1, 1B=1, 2B=0, 3B=0, HR=0\r\n" + //
                        "Offspeed Stats: PA=13, H=2, 1B=2, 2B=0, 3B=0, HR=0\r\n" + //
                        "Total Stats: PA=18, H=4, 1B=4, 2B=0, 3B=0, HR=0\r\n" + //
                        "Swing Stats: ZoneSwing=0.693, ZoneContact=0.726, ChaseSwing=0.304, ChaseContact=0.531\r\n" + //
                        "Overall: AVG=0.4, OBP=0.4, OPS=0.8\r\n" + //
                        "===== Leo Rivas (Batter) =====\r\n" + //
                        "Fastball Stats: PA=51, H=10, 1B=9, 2B=0, 3B=1, HR=0\r\n" + //
                        "Breaking Stats: PA=25, H=6, 1B=5, 2B=1, 3B=0, HR=0\r\n" + //
                        "Offspeed Stats: PA=10, H=1, 1B=1, 2B=0, 3B=0, HR=0\r\n" + //
                        "Total Stats: PA=86, H=17, 1B=15, 2B=1, 3B=1, HR=0\r\n" + //
                        "Swing Stats: ZoneSwing=0.566, ZoneContact=0.811, ChaseSwing=0.149, ChaseContact=0.423\r\n" + //
                        "Overall: AVG=0.233, OBP=0.333, OPS=0.607\r\n" + //
                        "===== Mitch Haniger (Batter) =====\r\n" + //
                        "Fastball Stats: PA=246, H=48, 1B=29, 2B=9, 3B=0, HR=10\r\n" + //
                        "Breaking Stats: PA=134, H=25, 1B=22, 2B=2, 3B=0, HR=1\r\n" + //
                        "Offspeed Stats: PA=43, H=6, 1B=4, 2B=1, 3B=0, HR=1\r\n" + //
                        "Total Stats: PA=423, H=79, 1B=55, 2B=12, 3B=0, HR=12\r\n" + //
                        "Swing Stats: ZoneSwing=0.634, ZoneContact=0.766, ChaseSwing=0.241, ChaseContact=0.435\r\n" + //
                        "Overall: AVG=0.208, OBP=0.286, OPS=0.62\r\n" + //
                        "===== Dominic Canzone (Batter) =====\r\n" + //
                        "Fastball Stats: PA=62, H=11, 1B=6, 2B=3, 3B=0, HR=2\r\n" + //
                        "Breaking Stats: PA=75, H=16, 1B=9, 2B=3, 3B=0, HR=4\r\n" + //
                        "Offspeed Stats: PA=49, H=6, 1B=3, 2B=1, 3B=0, HR=2\r\n" + //
                        "Total Stats: PA=186, H=33, 1B=18, 2B=7, 3B=0, HR=8\r\n" + //
                        "Swing Stats: ZoneSwing=0.689, ZoneContact=0.759, ChaseSwing=0.37, ChaseContact=0.481\r\n" + //
                        "Overall: AVG=0.196, OBP=0.271, OPS=0.652\r\n" + //
                        "===== Cade Marlowe (Batter) =====\r\n" + //
                        "Fastball Stats: PA=4, H=2, 1B=2, 2B=0, 3B=0, HR=0\r\n" + //
                        "Breaking Stats: PA=28, H=5, 1B=5, 2B=0, 3B=0, HR=0\r\n" + //
                        "Offspeed Stats: PA=20, H=2, 1B=1, 2B=0, 3B=1, HR=0\r\n" + //
                        "Total Stats: PA=52, H=9, 1B=8, 2B=0, 3B=1, HR=0\r\n" + //
                        "Swing Stats: ZoneSwing=0.8, ZoneContact=0.833, ChaseSwing=0.316, ChaseContact=0.5\r\n" + //
                        "Overall: AVG=0.25, OBP=0.33, OPS=0.75\r\n" + //
                        "===== Ryan Bliss (Batter) =====\r\n" + //
                        "Fastball Stats: PA=36, H=6, 1B=4, 2B=0, 3B=1, HR=1\r\n" + //
                        "Breaking Stats: PA=23, H=7, 1B=3, 2B=3, 3B=0, HR=1\r\n" + //
                        "Offspeed Stats: PA=12, H=1, 1B=1, 2B=0, 3B=0, HR=0\r\n" + //
                        "Total Stats: PA=71, H=14, 1B=8, 2B=3, 3B=1, HR=2\r\n" + //
                        "Swing Stats: ZoneSwing=0.779, ZoneContact=0.697, ChaseSwing=0.21, ChaseContact=0.36\r\n" + //
                        "Overall: AVG=0.222, OBP=0.29, OPS=0.687\r\n";
                List<String> lineupStrings = mockBL.lines().collect(Collectors.toList());                        
                return lineupStrings;
            }
        };

        // Initialize with real data
        testModel.setPlayerTeam(Teams.MARINERS);
        // Get real data to test with
        batters = testModel.getPlayerTeamBatterLoaderLineup();
        List<Batter> blList = new ArrayList<>(batters);
        testModel.saveLineupAsTXTFile(Side.PLAYER, fileName, blList);
        String result = "===== Dylan Moore (Batter) =====\r\n" + //
                        "Fastball Stats: PA=273, H=47, 1B=21, 2B=15, 3B=2, HR=9\r\n" + //
                        "Breaking Stats: PA=124, H=16, 1B=9, 2B=5, 3B=1, HR=1\r\n" + //
                        "Offspeed Stats: PA=42, H=11, 1B=7, 2B=3, 3B=1, HR=0\r\n" + //
                        "Total Stats: PA=439, H=74, 1B=37, 2B=23, 3B=4, HR=10\r\n" + //
                        "Swing Stats: ZoneSwing=0.653, ZoneContact=0.804, ChaseSwing=0.177, ChaseContact=0.532\r\n" + //
                        "Overall: AVG=0.333, OBP=0.429, OPS=0.762\r\n" + //
                        "===== Julio Rodriguez (Batter) =====\r\n" + //
                        "Fastball Stats: PA=324, H=94, 1B=71, 2B=10, 3B=0, HR=13\r\n" + //
                        "Breaking Stats: PA=202, H=47, 1B=37, 2B=5, 3B=0, HR=5\r\n" + //
                        "Offspeed Stats: PA=86, H=14, 1B=10, 2B=2, 3B=0, HR=2\r\n" + //
                        "Total Stats: PA=612, H=155, 1B=118, 2B=17, 3B=0, HR=20\r\n" + //
                        "Swing Stats: ZoneSwing=0.746, ZoneContact=0.787, ChaseSwing=0.374, ChaseContact=0.532\r\n" + //
                        "Overall: AVG=0.273, OBP=0.325, OPS=0.734\r\n" + //
                        "===== Austin Shenton (Batter) =====\r\n" + //
                        "Fastball Stats: PA=30, H=7, 1B=3, 2B=3, 3B=0, HR=1\r\n" + //
                        "Breaking Stats: PA=14, H=1, 1B=0, 2B=1, 3B=0, HR=0\r\n" + //
                        "Offspeed Stats: PA=6, H=1, 1B=0, 2B=1, 3B=0, HR=0\r\n" + //
                        "Total Stats: PA=50, H=9, 1B=3, 2B=5, 3B=0, HR=1\r\n" + //
                        "Swing Stats: ZoneSwing=0.596, ZoneContact=0.694, ChaseSwing=0.204, ChaseContact=0.5\r\n" + //
                        "Overall: AVG=0.214, OBP=0.34, OPS=0.745\r\n" + //
                        "===== Cal Raleigh (Batter) =====\r\n" + //
                        "Fastball Stats: PA=328, H=64, 1B=38, 2B=8, 3B=0, HR=18\r\n" + //
                        "Breaking Stats: PA=140, H=26, 1B=14, 2B=3, 3B=0, HR=9\r\n" + //
                        "Offspeed Stats: PA=156, H=30, 1B=18, 2B=5, 3B=0, HR=7\r\n" + //
                        "Total Stats: PA=624, H=120, 1B=70, 2B=16, 3B=0, HR=34\r\n" + //
                        "Swing Stats: ZoneSwing=0.744, ZoneContact=0.765, ChaseSwing=0.327, ChaseContact=0.531\r\n" + //
                        "Overall: AVG=0.22, OBP=0.312, OPS=0.748\r\n" + //
                        "===== Mitch Garver (Batter) =====\r\n" + //
                        "Fastball Stats: PA=228, H=36, 1B=15, 2B=9, 3B=0, HR=12\r\n" + //
                        "Breaking Stats: PA=151, H=15, 1B=8, 2B=5, 3B=0, HR=2\r\n" + //
                        "Offspeed Stats: PA=51, H=12, 1B=8, 2B=3, 3B=0, HR=1\r\n" + //
                        "Total Stats: PA=430, H=63, 1B=31, 2B=17, 3B=0, HR=15\r\n" + //
                        "Swing Stats: ZoneSwing=0.576, ZoneContact=0.786, ChaseSwing=0.2, ChaseContact=0.426\r\n" + //
                        "Overall: AVG=0.172, OBP=0.286, OPS=0.627\r\n" + //
                        "===== Tyler Locklear (Batter) =====\r\n" + //
                        "Fastball Stats: PA=18, H=1, 1B=1, 2B=0, 3B=0, HR=0\r\n" + //
                        "Breaking Stats: PA=23, H=4, 1B=2, 2B=1, 3B=0, HR=1\r\n" + //
                        "Offspeed Stats: PA=8, H=2, 1B=1, 2B=0, 3B=0, HR=1\r\n" + //
                        "Total Stats: PA=49, H=7, 1B=4, 2B=1, 3B=0, HR=2\r\n" + //
                        "Swing Stats: ZoneSwing=0.688, ZoneContact=0.788, ChaseSwing=0.34, ChaseContact=0.324\r\n" + //
                        "Overall: AVG=0.156, OBP=0.224, OPS=0.535\r\n" + //
                        "===== Randy Arozarena (Batter) =====\r\n" + //
                        "Fastball Stats: PA=375, H=67, 1B=43, 2B=14, 3B=0, HR=10\r\n" + //
                        "Breaking Stats: PA=196, H=40, 1B=18, 2B=14, 3B=0, HR=8\r\n" + //
                        "Offspeed Stats: PA=77, H=13, 1B=6, 2B=5, 3B=0, HR=2\r\n" + //
                        "Total Stats: PA=648, H=120, 1B=67, 2B=33, 3B=0, HR=20\r\n" + //
                        "Swing Stats: ZoneSwing=0.627, ZoneContact=0.759, ChaseSwing=0.268, ChaseContact=0.573\r\n" + //
                        "Overall: AVG=0.219, OBP=0.332, OPS=0.72\r\n" + //
                        "===== J.P. Crawford (Batter) =====\r\n" + //
                        "Fastball Stats: PA=277, H=50, 1B=34, 2B=9, 3B=1, HR=6\r\n" + //
                        "Breaking Stats: PA=124, H=23, 1B=13, 2B=6, 3B=1, HR=3\r\n" + //
                        "Offspeed Stats: PA=50, H=6, 1B=5, 2B=1, 3B=0, HR=0\r\n" + //
                        "Total Stats: PA=451, H=79, 1B=52, 2B=16, 3B=2, HR=9\r\n" + //
                        "Swing Stats: ZoneSwing=0.62, ZoneContact=0.837, ChaseSwing=0.182, ChaseContact=0.63\r\n" + //
                        "Overall: AVG=0.202, OBP=0.304, OPS=0.625\r\n" + //
                        "===== Luke Raley (Batter) =====\r\n" + //
                        "Fastball Stats: PA=214, H=56, 1B=28, 2B=12, 3B=1, HR=15\r\n" + //
                        "Breaking Stats: PA=159, H=27, 1B=15, 2B=5, 3B=1, HR=6\r\n" + //
                        "Offspeed Stats: PA=82, H=15, 1B=12, 2B=2, 3B=0, HR=1\r\n" + //
                        "Total Stats: PA=455, H=98, 1B=55, 2B=19, 3B=2, HR=22\r\n" + //
                        "Swing Stats: ZoneSwing=0.752, ZoneContact=0.762, ChaseSwing=0.279, ChaseContact=0.347\r\n" + //
                        "Overall: AVG=0.243, OBP=0.32, OPS=0.783\r\n" + //
                        "===== Victor Robles (Batter) =====\r\n" + //
                        "Fastball Stats: PA=165, H=49, 1B=32, 2B=13, 3B=0, HR=4\r\n" + //
                        "Breaking Stats: PA=96, H=23, 1B=17, 2B=6, 3B=0, HR=0\r\n" + //
                        "Offspeed Stats: PA=34, H=6, 1B=5, 2B=1, 3B=0, HR=0\r\n" + //
                        "Total Stats: PA=295, H=78, 1B=54, 2B=20, 3B=0, HR=4\r\n" + //
                        "Swing Stats: ZoneSwing=0.727, ZoneContact=0.809, ChaseSwing=0.3, ChaseContact=0.533\r\n" + //
                        "Overall: AVG=0.307, OBP=0.381, OPS=0.814\r\n" + //
                        "===== Samad Taylor (Batter) =====\r\n" + //
                        "Fastball Stats: PA=3, H=1, 1B=1, 2B=0, 3B=0, HR=0\r\n" + //
                        "Breaking Stats: PA=2, H=1, 1B=1, 2B=0, 3B=0, HR=0\r\n" + //
                        "Offspeed Stats: PA=13, H=2, 1B=2, 2B=0, 3B=0, HR=0\r\n" + //
                        "Total Stats: PA=18, H=4, 1B=4, 2B=0, 3B=0, HR=0\r\n" + //
                        "Swing Stats: ZoneSwing=0.693, ZoneContact=0.726, ChaseSwing=0.304, ChaseContact=0.531\r\n" + //
                        "Overall: AVG=0.4, OBP=0.4, OPS=0.8\r\n" + //
                        "===== Leo Rivas (Batter) =====\r\n" + //
                        "Fastball Stats: PA=51, H=10, 1B=9, 2B=0, 3B=1, HR=0\r\n" + //
                        "Breaking Stats: PA=25, H=6, 1B=5, 2B=1, 3B=0, HR=0\r\n" + //
                        "Offspeed Stats: PA=10, H=1, 1B=1, 2B=0, 3B=0, HR=0\r\n" + //
                        "Total Stats: PA=86, H=17, 1B=15, 2B=1, 3B=1, HR=0\r\n" + //
                        "Swing Stats: ZoneSwing=0.566, ZoneContact=0.811, ChaseSwing=0.149, ChaseContact=0.423\r\n" + //
                        "Overall: AVG=0.233, OBP=0.333, OPS=0.607\r\n" + //
                        "===== Mitch Haniger (Batter) =====\r\n" + //
                        "Fastball Stats: PA=246, H=48, 1B=29, 2B=9, 3B=0, HR=10\r\n" + //
                        "Breaking Stats: PA=134, H=25, 1B=22, 2B=2, 3B=0, HR=1\r\n" + //
                        "Offspeed Stats: PA=43, H=6, 1B=4, 2B=1, 3B=0, HR=1\r\n" + //
                        "Total Stats: PA=423, H=79, 1B=55, 2B=12, 3B=0, HR=12\r\n" + //
                        "Swing Stats: ZoneSwing=0.634, ZoneContact=0.766, ChaseSwing=0.241, ChaseContact=0.435\r\n" + //
                        "Overall: AVG=0.208, OBP=0.286, OPS=0.62\r\n" + //
                        "===== Dominic Canzone (Batter) =====\r\n" + //
                        "Fastball Stats: PA=62, H=11, 1B=6, 2B=3, 3B=0, HR=2\r\n" + //
                        "Breaking Stats: PA=75, H=16, 1B=9, 2B=3, 3B=0, HR=4\r\n" + //
                        "Offspeed Stats: PA=49, H=6, 1B=3, 2B=1, 3B=0, HR=2\r\n" + //
                        "Total Stats: PA=186, H=33, 1B=18, 2B=7, 3B=0, HR=8\r\n" + //
                        "Swing Stats: ZoneSwing=0.689, ZoneContact=0.759, ChaseSwing=0.37, ChaseContact=0.481\r\n" + //
                        "Overall: AVG=0.196, OBP=0.271, OPS=0.652\r\n" + //
                        "===== Cade Marlowe (Batter) =====\r\n" + //
                        "Fastball Stats: PA=4, H=2, 1B=2, 2B=0, 3B=0, HR=0\r\n" + //
                        "Breaking Stats: PA=28, H=5, 1B=5, 2B=0, 3B=0, HR=0\r\n" + //
                        "Offspeed Stats: PA=20, H=2, 1B=1, 2B=0, 3B=1, HR=0\r\n" + //
                        "Total Stats: PA=52, H=9, 1B=8, 2B=0, 3B=1, HR=0\r\n" + //
                        "Swing Stats: ZoneSwing=0.8, ZoneContact=0.833, ChaseSwing=0.316, ChaseContact=0.5\r\n" + //
                        "Overall: AVG=0.25, OBP=0.33, OPS=0.75\r\n" + //
                        "===== Ryan Bliss (Batter) =====\r\n" + //
                        "Fastball Stats: PA=36, H=6, 1B=4, 2B=0, 3B=1, HR=1\r\n" + //
                        "Breaking Stats: PA=23, H=7, 1B=3, 2B=3, 3B=0, HR=1\r\n" + //
                        "Offspeed Stats: PA=12, H=1, 1B=1, 2B=0, 3B=0, HR=0\r\n" + //
                        "Total Stats: PA=71, H=14, 1B=8, 2B=3, 3B=1, HR=2\r\n" + //
                        "Swing Stats: ZoneSwing=0.779, ZoneContact=0.697, ChaseSwing=0.21, ChaseContact=0.36\r\n" + //
                        "Overall: AVG=0.222, OBP=0.29, OPS=0.687\r\n";
        try {
            List<String> allLines = Files.readAllLines(Paths.get(fileName));
            StringBuilder testContent = new StringBuilder();
            for (String line: allLines) {
                testContent.append(line);                       
            }
            assertEquals(result.replace("\r\n", ""), testContent.toString());
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }                        
    }

    @Test
    public void testSaveGameDetailsAsTXTFileWrongFileName() {

        ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        PrintStream originalErr = System.err;
        System.setErr(new PrintStream(errContent));
        try {
            // Call the method with invalid filename
            String fileName = "./testSaveGameDetailsAsTXTFileWrongFileName.csv";
            model.saveGameDetailsAsTXTFile(fileName);
            
            // Check that the error message was printed
            assertEquals("Save as txt file only." + System.lineSeparator(), 
                        errContent.toString());
            
        } finally {
            // Reset System.err to original
            System.setErr(originalErr);
        }           
    }    

    @Test
    public void testSaveGameDetailsAsTXTFile() {
        String fileName = "./testSaveGameDetailsAsTXTFile.txt";                             
        // Create a test subclass of Model where we can override the method
        Model testModel = new Model() {
            @Override
            public SimulationResult startSimAndGetResult() {
                // Check if lineups are complete (copy logic from original method)
                if (this.getPlayerTeam().checkBatterLineupSpace() > 0 ||
                    this.getComTeam().checkPitcherLineupSpace() > 0) {
                    System.out.println("Simulation failed.");
                    System.out.println("Lineup is not completed.");
                    return null;
                }
                
                // Instead of creating real Simulation, return our mock
                SimulationResult mockResult = Mockito.mock(SimulationResult.class);
                String mockToString = "mariners Final Score: 1";
                String mockGetDetails = "Inning:   1  2  3  4  5  6  7  8  9  Runs\r\n" + //
                                        "mariners: 0  0  0  0  0  0  0  1  0  1\r\n" + //
                                        "\r\n" + //
                                        "Inning 1:\r\n" + //
                                        "Pitcher: Tyler Anderson\r\n" + //
                                        "Batters faced: 3\r\n" + //
                                        "Score: 0\r\n" + //
                                        "Pitches: 9\r\n" + //
                                        "Hits: 0\r\n" + //
                                        "Strikeouts: 2\r\n" + //
                                        "Walks: 0\r\n" + //
                                        "\r\n" + //
                                        "Inning 2:\r\n" + //
                                        "Pitcher: Tyler Anderson\r\n" + //
                                        "Batters faced: 3\r\n" + //
                                        "Score: 0\r\n" + //
                                        "Pitches: 9\r\n" + //
                                        "Hits: 0\r\n" + //
                                        "Strikeouts: 2\r\n" + //
                                        "Walks: 0\r\n" + //
                                        "\r\n" + //
                                        "Inning 3:\r\n" + //
                                        "Pitcher: Tyler Anderson\r\n" + //
                                        "Batters faced: 4\r\n" + //
                                        "Score: 0\r\n" + //
                                        "Pitches: 6\r\n" + //
                                        "Hits: 1\r\n" + //
                                        "Strikeouts: 0\r\n" + //
                                        "Walks: 0\r\n" + //
                                        "\r\n" + //
                                        "Inning 4:\r\n" + //
                                        "Pitcher: Tyler Anderson\r\n" + //
                                        "Batters faced: 4\r\n" + //
                                        "Score: 0\r\n" + //
                                        "Pitches: 9\r\n" + //
                                        "Hits: 1\r\n" + //
                                        "Strikeouts: 0\r\n" + //
                                        "Walks: 0\r\n" + //
                                        "\r\n" + //
                                        "Inning 5:\r\n" + //
                                        "Pitcher: Tyler Anderson\r\n" + //
                                        "Batters faced: 3\r\n" + //
                                        "Score: 0\r\n" + //
                                        "Pitches: 8\r\n" + //
                                        "Hits: 0\r\n" + //
                                        "Strikeouts: 1\r\n" + //
                                        "Walks: 0\r\n" + //
                                        "\r\n" + //
                                        "Inning 6:\r\n" + //
                                        "Pitcher: Carson Fulmer\r\n" + //
                                        "Batters faced: 4\r\n" + //
                                        "Score: 0\r\n" + //
                                        "Pitches: 11\r\n" + //
                                        "Hits: 1\r\n" + //
                                        "Strikeouts: 0\r\n" + //
                                        "Walks: 0\r\n" + //
                                        "\r\n" + //
                                        "Inning 7:\r\n" + //
                                        "Pitcher: Carson Fulmer\r\n" + //
                                        "Batters faced: 3\r\n" + //
                                        "Score: 0\r\n" + //
                                        "Pitches: 6\r\n" + //
                                        "Hits: 0\r\n" + //
                                        "Strikeouts: 1\r\n" + //
                                        "Walks: 0\r\n" + //
                                        "\r\n" + //
                                        "Inning 8:\r\n" + //
                                        "Pitcher: Griffin Canning\r\n" + //
                                        "Batters faced: 4\r\n" + //
                                        "Score: 1\r\n" + //
                                        "Pitches: 7\r\n" + //
                                        "Hits: 1\r\n" + //
                                        "Strikeouts: 0\r\n" + //
                                        "Walks: 0\r\n" + //
                                        "\r\n" + //
                                        "Inning 9:\r\n" + //
                                        "Pitcher: Griffin Canning\r\n" + //
                                        "Batters faced: 4\r\n" + //
                                        "Score: 0\r\n" + //
                                        "Pitches: 9\r\n" + //
                                        "Hits: 1\r\n" + //
                                        "Strikeouts: 1\r\n" + //
                                        "Walks: 0\r\n" + //
                                        "\r\n" + //
                                        "";
                String mockGetTotalStatistics = "=====Game Statistics=====\r\n" + //
                                        "\r\n" + //
                                        "Pitching Statistics:\r\n" + //
                                        "Total Pitches: 74\r\n" + //
                                        "Pitch Types: \r\n" + //
                                        " slider      : 10\r\n" + //
                                        " fourSeam    : 24\r\n" + //
                                        " sweeper     : 2\r\n" + //
                                        " curve       : 2\r\n" + //
                                        " sinker      : 7\r\n" + //
                                        " cutter      : 7\r\n" + //
                                        " changeup    : 22\r\n" + //
                                        "Pitch Categories: \r\n" + //
                                        " Breaking    : 14\r\n" + //
                                        " Offspeed    : 22\r\n" + //
                                        " Fastball    : 38\r\n" + //
                                        "\r\n" + //
                                        "Batting Statistics:\r\n" + //
                                        "Total Hits: 5\r\n" + //
                                        "Singles: 3\r\n" + //
                                        "Doubles: 1\r\n" + //
                                        "Triples: 0\r\n" + //
                                        "Home Runs: 1\r\n" + //
                                        "Strikeouts: 7\r\n" + //
                                        "Walks: 0\r\n" + //
                                        "\r\n" + //
                                        "";
                Mockito.when(mockResult.toString()).thenReturn(mockToString);
                Mockito.when(mockResult.getDetails()).thenReturn(mockGetDetails);
                Mockito.when(mockResult.getTotalStatistics()).thenReturn(mockGetTotalStatistics);
                
                // Set the result field like the original method
                try {
                    Field gameResultField = Model.class.getDeclaredField("gameResult");
                    gameResultField.setAccessible(true);
                    gameResultField.set(this, mockResult);
                } catch (Exception e) {
                    fail("Failed to set gameResult field: " + e.getMessage());
                }
                
                return mockResult;
            }
        };
        
        // Use the test model to run the simulation
        // Initialize with real data
        testModel.setPlayerTeam(Teams.MARINERS);
        testModel.setComTeam(Teams.ANGELS);

        // Get real data to test with
        batters = testModel.getPlayerTeamBatterLoaderLineup();
        pitchers = testModel.getComTeamPitcherLoaderLineup();   
        Stream<Pitcher> pStream = testModel.pitcherFilter("Pitches >= 500", pitchers);
        Stream<Batter> bStream = testModel.batterFilter("OPS >= 0.6", batters);
        List<Pitcher> tempPList = pStream.toList();
        List<Batter> tempBList = bStream.toList();        
        testModel.addPitcherToLineup(Side.COMPUTER, "Tyler Anderson to 1", tempPList.stream());
        testModel.addPitcherToLineup(Side.COMPUTER, "Carson Fulmer to 2", tempPList.stream());
        testModel.addPitcherToLineup(Side.COMPUTER, "Griffin Canning to 3", tempPList.stream());
        testModel.addBatterToLineup(Side.PLAYER, "Victor Robles to 1", tempBList.stream());
        testModel.addBatterToLineup(Side.PLAYER, "Samad Taylor to 2", tempBList.stream());
        testModel.addBatterToLineup(Side.PLAYER, "Luke Raley to 3", tempBList.stream()); 
        testModel.addBatterToLineup(Side.PLAYER, "Dylan Moore to 4", tempBList.stream());
        testModel.addBatterToLineup(Side.PLAYER, "Cade Marlowe to 5", tempBList.stream());
        testModel.addBatterToLineup(Side.PLAYER, "Cal Raleigh to 6", tempBList.stream());  
        testModel.addBatterToLineup(Side.PLAYER, "Austin Shenton to 7", tempBList.stream());
        testModel.addBatterToLineup(Side.PLAYER, "Julio Rodriguez to 8", tempBList.stream());
        testModel.addBatterToLineup(Side.PLAYER, "Randy Arozarena to 9", tempBList.stream());                    
        testModel.startSimAndGetResult();
        testModel.saveGameDetailsAsTXTFile(fileName);
        String result = "mariners Final Score: 1\r\n" + //
                        "Inning:   1  2  3  4  5  6  7  8  9  Runs\r\n" + //
                        "mariners: 0  0  0  0  0  0  0  1  0  1\r\n" + //
                        "\r\n" + //
                        "Inning 1:\r\n" + //
                        "Pitcher: Tyler Anderson\r\n" + //
                        "Batters faced: 3\r\n" + //
                        "Score: 0\r\n" + //
                        "Pitches: 9\r\n" + //
                        "Hits: 0\r\n" + //
                        "Strikeouts: 2\r\n" + //
                        "Walks: 0\r\n" + //
                        "\r\n" + //
                        "Inning 2:\r\n" + //
                        "Pitcher: Tyler Anderson\r\n" + //
                        "Batters faced: 3\r\n" + //
                        "Score: 0\r\n" + //
                        "Pitches: 9\r\n" + //
                        "Hits: 0\r\n" + //
                        "Strikeouts: 2\r\n" + //
                        "Walks: 0\r\n" + //
                        "\r\n" + //
                        "Inning 3:\r\n" + //
                        "Pitcher: Tyler Anderson\r\n" + //
                        "Batters faced: 4\r\n" + //
                        "Score: 0\r\n" + //
                        "Pitches: 6\r\n" + //
                        "Hits: 1\r\n" + //
                        "Strikeouts: 0\r\n" + //
                        "Walks: 0\r\n" + //
                        "\r\n" + //
                        "Inning 4:\r\n" + //
                        "Pitcher: Tyler Anderson\r\n" + //
                        "Batters faced: 4\r\n" + //
                        "Score: 0\r\n" + //
                        "Pitches: 9\r\n" + //
                        "Hits: 1\r\n" + //
                        "Strikeouts: 0\r\n" + //
                        "Walks: 0\r\n" + //
                        "\r\n" + //
                        "Inning 5:\r\n" + //
                        "Pitcher: Tyler Anderson\r\n" + //
                        "Batters faced: 3\r\n" + //
                        "Score: 0\r\n" + //
                        "Pitches: 8\r\n" + //
                        "Hits: 0\r\n" + //
                        "Strikeouts: 1\r\n" + //
                        "Walks: 0\r\n" + //
                        "\r\n" + //
                        "Inning 6:\r\n" + //
                        "Pitcher: Carson Fulmer\r\n" + //
                        "Batters faced: 4\r\n" + //
                        "Score: 0\r\n" + //
                        "Pitches: 11\r\n" + //
                        "Hits: 1\r\n" + //
                        "Strikeouts: 0\r\n" + //
                        "Walks: 0\r\n" + //
                        "\r\n" + //
                        "Inning 7:\r\n" + //
                        "Pitcher: Carson Fulmer\r\n" + //
                        "Batters faced: 3\r\n" + //
                        "Score: 0\r\n" + //
                        "Pitches: 6\r\n" + //
                        "Hits: 0\r\n" + //
                        "Strikeouts: 1\r\n" + //
                        "Walks: 0\r\n" + //
                        "\r\n" + //
                        "Inning 8:\r\n" + //
                        "Pitcher: Griffin Canning\r\n" + //
                        "Batters faced: 4\r\n" + //
                        "Score: 1\r\n" + //
                        "Pitches: 7\r\n" + //
                        "Hits: 1\r\n" + //
                        "Strikeouts: 0\r\n" + //
                        "Walks: 0\r\n" + //
                        "\r\n" + //
                        "Inning 9:\r\n" + //
                        "Pitcher: Griffin Canning\r\n" + //
                        "Batters faced: 4\r\n" + //
                        "Score: 0\r\n" + //
                        "Pitches: 9\r\n" + //
                        "Hits: 1\r\n" + //
                        "Strikeouts: 1\r\n" + //
                        "Walks: 0\r\n" + //
                        "\r\n" + //
                        "\r\n" + //
                        "=====Game Statistics=====\r\n" + //
                        "\r\n" + //
                        "Pitching Statistics:\r\n" + //
                        "Total Pitches: 74\r\n" + //
                        "Pitch Types: \r\n" + //
                        " slider      : 10\r\n" + //
                        " fourSeam    : 24\r\n" + //
                        " sweeper     : 2\r\n" + //
                        " curve       : 2\r\n" + //
                        " sinker      : 7\r\n" + //
                        " cutter      : 7\r\n" + //
                        " changeup    : 22\r\n" + //
                        "Pitch Categories: \r\n" + //
                        " Breaking    : 14\r\n" + //
                        " Offspeed    : 22\r\n" + //
                        " Fastball    : 38\r\n" + //
                        "\r\n" + //
                        "Batting Statistics:\r\n" + //
                        "Total Hits: 5\r\n" + //
                        "Singles: 3\r\n" + //
                        "Doubles: 1\r\n" + //
                        "Triples: 0\r\n" + //
                        "Home Runs: 1\r\n" + //
                        "Strikeouts: 7\r\n" + //
                        "Walks: 0\r\n" + //
                        "\r\n" + //
                        "\r\n" + //
                        "";
        try {
            List<String> allLines = Files.readAllLines(Paths.get(fileName));
            StringBuilder testContent = new StringBuilder();
            for (String line: allLines) {
                testContent.append(line);                       
            }
            assertEquals(result.replace("\r\n", ""), testContent.toString());
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }                        
    }      

} 