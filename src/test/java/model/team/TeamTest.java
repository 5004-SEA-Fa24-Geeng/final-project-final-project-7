package model.team;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gameEnum.Teams;
import model.player.Batter;
import model.player.Pitcher;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Test class for Team.java using JUnit 5 (JUnit Jupiter)
 * Tests both PlayerTeam and ComTeam implementations
 */
public class TeamTest {
    
    private PlayerTeam playerTeam;
    private ComTeam comTeam;
    
    @BeforeEach
    public void setUp() {
        playerTeam = new PlayerTeam(Teams.MARINERS);
        comTeam = new ComTeam(Teams.ANGELS);
    }
    
    @Test
    public void testTeamConstructor() {
        // Test team names
        assertEquals("mariners", playerTeam.getTeamName());
        assertEquals("angels", comTeam.getTeamName());
        
        // Test initial lineup sizes
        assertEquals(9, playerTeam.getBatterLineup().size());
        assertEquals(3, playerTeam.getPitcherLineup().size());
        assertEquals(9, comTeam.getBatterLineup().size());
        assertEquals(3, comTeam.getPitcherLineup().size());
        
        // Test initial lineup is filled with nulls
        for (Batter batter : playerTeam.getBatterLineup()) {
            assertNull(batter);
        }
        
        for (Pitcher pitcher : comTeam.getPitcherLineup()) {
            assertNull(pitcher);
        }
    }
    
    @Test
    public void testLoadedDataFromCSV() {
        // Test that batters were loaded for Mariners PlayerTeam
        Set<Batter> marinersLoaderBatters = playerTeam.getBatterLoaderLineup();
        assertNotNull(marinersLoaderBatters);
        assertFalse(marinersLoaderBatters.isEmpty());
        
        // Test expected size from CSV (16 batters mentioned in the document)
        assertEquals(16, marinersLoaderBatters.size());
        
        // Test that pitchers were loaded for Angels ComTeam
        Set<Pitcher> angelsLoaderPitchers = comTeam.getPitcherLoaderLineup();
        assertNotNull(angelsLoaderPitchers);
        assertFalse(angelsLoaderPitchers.isEmpty());
        
        // Test expected size from CSV (34 pitchers mentioned in the document)
        assertEquals(34, angelsLoaderPitchers.size());
    }
    
    @Test
    public void testGetPlayerFromLoader() {
        // For this test, we need to know actual player names from the CSVs
        // Since we don't have the actual data, we'll test the functionality but not specific names
        
        // Get a batter from Mariners PlayerTeam
        Batter anyBatter = playerTeam.getBatterLoaderLineup().iterator().next();
        String batterName = anyBatter.getName();
        
        Batter retrievedBatter = playerTeam.getBatterFromLoader(batterName);
        assertNotNull(retrievedBatter);
        assertEquals(batterName, retrievedBatter.getName());
        
        // Get a pitcher from Angels ComTeam
        Pitcher anyPitcher = comTeam.getPitcherLoaderLineup().iterator().next();
        String pitcherName = anyPitcher.getName();
        
        Pitcher retrievedPitcher = comTeam.getPitcherFromLoader(pitcherName);
        assertNotNull(retrievedPitcher);
        assertEquals(pitcherName, retrievedPitcher.getName());
        
        // Test getting non-existent player
        assertNull(playerTeam.getBatterFromLoader("Non Existent Player"));
        assertNull(comTeam.getPitcherFromLoader("Non Existent Player"));
    }
    
    @Test
    public void testClearLineups() {
        // First add some players to lineups
        Batter batter = playerTeam.getBatterLoaderLineup().iterator().next();
        playerTeam.getBatterLineup().set(0, batter);
        
        Pitcher pitcher = comTeam.getPitcherLoaderLineup().iterator().next();
        comTeam.getPitcherLineup().set(0, pitcher);
        
        // Clear lineups
        playerTeam.clearBatterLineup();
        comTeam.clearPitcherLineup();
        
        // Check all positions are null
        for (Batter b : playerTeam.getBatterLineup()) {
            assertNull(b);
        }
        
        for (Pitcher p : comTeam.getPitcherLineup()) {
            assertNull(p);
        }
    }
    
    @Test
    public void testCheckPlayerInLineup() {
        // Get players from loader
        Batter batter = playerTeam.getBatterLoaderLineup().iterator().next();
        Pitcher pitcher = comTeam.getPitcherLoaderLineup().iterator().next();
        
        // Initially they shouldn't be in the lineup
        assertFalse(playerTeam.checkBatterInLineup(batter));
        assertFalse(comTeam.checkPitcherInLineup(pitcher));
        
        // Add them to lineup
        playerTeam.getBatterLineup().set(0, batter);
        comTeam.getPitcherLineup().set(0, pitcher);
        
        // Now they should be in the lineup
        assertTrue(playerTeam.checkBatterInLineup(batter));
        assertTrue(comTeam.checkPitcherInLineup(pitcher));
    }
    
    @Test
    public void testCheckLineupSpace() {
        // Initial lineup should have 9 null spots for batters and 3 for pitchers
        assertEquals(9, playerTeam.checkBatterLineupSpace());
        assertEquals(3, comTeam.checkPitcherLineupSpace());
        
        // Add one player to each lineup
        Batter batter = playerTeam.getBatterLoaderLineup().iterator().next();
        playerTeam.getBatterLineup().set(0, batter);
        
        Pitcher pitcher = comTeam.getPitcherLoaderLineup().iterator().next();
        comTeam.getPitcherLineup().set(0, pitcher);
        
        // Check space reduced by 1
        assertEquals(8, playerTeam.checkBatterLineupSpace());
        assertEquals(2, comTeam.checkPitcherLineupSpace());
    }
    
    @Test
    public void testCheckPitcherInTheRightPlace() {
        // Get pitchers with different rotation values
        Pitcher rotation1Pitcher = null;
        Pitcher rotation2Pitcher = null;
        
        for (Pitcher p : comTeam.getPitcherLoaderLineup()) {
            if (p.getRotation() == 1 && rotation1Pitcher == null) {
                rotation1Pitcher = p;
            } else if (p.getRotation() == 2 && rotation2Pitcher == null) {
                rotation2Pitcher = p;
            }
            
            if (rotation1Pitcher != null && rotation2Pitcher != null) {
                break;
            }
        }
        
        // Test that rotation 1 pitcher can only be in position 1
        assertTrue(comTeam.checkPitcherInTheRightPlace(0, rotation1Pitcher));  // index 0 = position 1
        assertFalse(comTeam.checkPitcherInTheRightPlace(1, rotation1Pitcher)); // index 1 = position 2
        assertFalse(comTeam.checkPitcherInTheRightPlace(2, rotation1Pitcher)); // index 2 = position 3
        
        // Test that rotation 2 pitcher cannot be in position 1
        assertFalse(comTeam.checkPitcherInTheRightPlace(0, rotation2Pitcher)); // index 0 = position 1
        assertTrue(comTeam.checkPitcherInTheRightPlace(1, rotation2Pitcher));  // index 1 = position 2
        assertTrue(comTeam.checkPitcherInTheRightPlace(2, rotation2Pitcher));  // index 2 = position 3
    }
    
    @Test
    public void testAddBatterToTeam() {
        // Get a batter
        Batter batter = playerTeam.getBatterLoaderLineup().iterator().next();
        String batterName = batter.getName();
        
        // Test adding by name
        Stream<Batter> filteredBatters = playerTeam.getBatterLoaderLineup().stream();
        playerTeam.addBatterToTeam(batterName + " to 1", filteredBatters);
        
        // Check batter was added to position 1 (index 0)
        assertEquals(batter.getName(), playerTeam.getBatterLineup().get(0).getName());
        
        // Test adding by index
        playerTeam.clearBatterLineup();
        filteredBatters = playerTeam.getBatterLoaderLineup().stream()
                                        .sorted((b1, b2) -> b1.getName().compareTo(b2.getName()));
        List<Batter> sortedBatters = filteredBatters.toList();
        int index = sortedBatters.indexOf(batter) + 1; // +1 because command uses 1-indexed
        
        filteredBatters = sortedBatters.stream();
        playerTeam.addBatterToTeam(index + " to 2", filteredBatters);
        
        // Check batter was added to position 2 (index 1)
        assertNotNull(playerTeam.getBatterLineup().get(1));
        assertEquals(batter.getName(), playerTeam.getBatterLineup().get(1).getName());
        
        // Test adding to full lineup
        for (int i = 0; i < 9; i++) {
            if (playerTeam.getBatterLineup().get(i) == null) {
                playerTeam.getBatterLineup().set(i, batter);
            }
        }
        
        // Try to add to full lineup
        // This should print an error but not throw exception
        filteredBatters = playerTeam.getBatterLoaderLineup().stream();
        playerTeam.addBatterToTeam(batterName + " to 3", filteredBatters);
    }
    
    @Test
    public void testAddPitcherToTeam() {
        // Get pitchers with different rotation values
        Pitcher rotation1Pitcher = null;
        Pitcher rotation2Pitcher = null;
        
        for (Pitcher p : comTeam.getPitcherLoaderLineup()) {
            if (p.getRotation() == 1 && rotation1Pitcher == null) {
                rotation1Pitcher = p;
            } else if (p.getRotation() == 2 && rotation2Pitcher == null) {
                rotation2Pitcher = p;
            }
            
            if (rotation1Pitcher != null && rotation2Pitcher != null) {
                break;
            }
        }
        
        // Test adding rotation 1 pitcher to position 1
        Stream<Pitcher> filteredPitchers = comTeam.getPitcherLoaderLineup().stream();
        comTeam.addPitcherToTeam(rotation1Pitcher.getName() + " to 1", filteredPitchers);
        
        // Check pitcher was added to position 1 (index 0)
        assertEquals(rotation1Pitcher.getName(), comTeam.getPitcherLineup().get(0).getName());
        
        // Test adding rotation 2 pitcher to position 2
        filteredPitchers = comTeam.getPitcherLoaderLineup().stream();
        comTeam.addPitcherToTeam(rotation2Pitcher.getName() + " to 2", filteredPitchers);
        
        // Check pitcher was added to position 2 (index 1)
        assertEquals(rotation2Pitcher.getName(), comTeam.getPitcherLineup().get(1).getName());
        
        // Test adding rotation 1 pitcher to position 2 (should fail)
        comTeam.clearPitcherLineup();
        filteredPitchers = comTeam.getPitcherLoaderLineup().stream();
        comTeam.addPitcherToTeam(rotation1Pitcher.getName() + " to 2", filteredPitchers);
        
        // Should not be added due to rotation restriction
        assertNull(comTeam.getPitcherLineup().get(1));
        
        // Test adding rotation 2 pitcher to position 1 (should fail)
        filteredPitchers = comTeam.getPitcherLoaderLineup().stream();
        comTeam.addPitcherToTeam(rotation2Pitcher.getName() + " to 1", filteredPitchers);
        
        // Should not be added due to rotation restriction
        assertNull(comTeam.getPitcherLineup().get(0));
    }
    
    @Test
    public void testPitcherRemoveFromTeam() {
        // Add some batters
        Batter batter1 = null;
        Batter batter2 = null;
        Batter batter3 = null;
        
        // Get 3 different batters
        for (Batter b : playerTeam.getBatterLoaderLineup()) {
            if (batter1 == null) {
                batter1 = b;
            } else if (batter2 == null && !b.equals(batter1)) {
                batter2 = b;
            } else if (batter3 == null && !b.equals(batter1) && !b.equals(batter2)) {
                batter3 = b;
                break;
            }
        }
        
        // Add batters to lineup
        playerTeam.getBatterLineup().set(0, batter1);
        playerTeam.getBatterLineup().set(1, batter2);
        playerTeam.getBatterLineup().set(2, batter3);
        
        // Test removing by index
        playerTeam.removeFromTeam("1", "batter");
        assertNull(playerTeam.getBatterLineup().get(0));
        assertNotNull(playerTeam.getBatterLineup().get(1));
        assertNotNull(playerTeam.getBatterLineup().get(2));
        
        // Test removing by range
        playerTeam.getBatterLineup().set(0, batter1); // Add back first batter
        playerTeam.removeFromTeam("1-2", "batter");
        assertNull(playerTeam.getBatterLineup().get(0));
        assertNull(playerTeam.getBatterLineup().get(1));
        assertNotNull(playerTeam.getBatterLineup().get(2));

        // Test removing by name
        playerTeam.removeFromTeam(batter3.getName(), "batter");
        assertNull(playerTeam.getBatterLineup().get(2));
        
        // Test removing all
        playerTeam.getBatterLineup().set(0, batter1);
        playerTeam.getBatterLineup().set(1, batter2);
        playerTeam.getBatterLineup().set(2, batter3);
        
        playerTeam.removeFromTeam("all", "batter");
        for (Batter b : playerTeam.getBatterLineup()) {
            assertNull(b);
        }
    }

    @Test
    public void testBatterRemoveFromTeam() {
        // Add some batters
        Pitcher pitcher1 = null;
        Pitcher pitcher2 = null;
        Pitcher pitcher3 = null;
        
        // Get 3 different batters
        for (Pitcher p : comTeam.getPitcherLoaderLineup()) {
            if (pitcher1 == null) {
                pitcher1 = p;
            } else if (pitcher2 == null && !p.equals(pitcher1)) {
                pitcher2 = p;
            } else if (pitcher3 == null && !p.equals(pitcher1) && !p.equals(pitcher2)) {
                pitcher3 = p;
                break;
            }
        }
        
        // Add batters to lineup
        comTeam.getPitcherLineup().set(0, pitcher1);
        comTeam.getPitcherLineup().set(1, pitcher2);
        comTeam.getPitcherLineup().set(2, pitcher3);
        
        // Test removing by index
        comTeam.removeFromTeam("1", "pitcher");
        assertNull(comTeam.getPitcherLineup().get(0));
        assertNotNull(comTeam.getPitcherLineup().get(1));
        assertNotNull(comTeam.getPitcherLineup().get(2));
        
        // Test removing by range
        comTeam.getPitcherLineup().set(0, pitcher1); // Add back first batter
        comTeam.removeFromTeam("1-2", "pitcher");
        assertNull(comTeam.getPitcherLineup().get(0));
        assertNull(comTeam.getPitcherLineup().get(1));
        assertNotNull(comTeam.getPitcherLineup().get(2));

        // Test removing by name
        comTeam.removeFromTeam(pitcher3.getName(), "pitcher");
        assertNull(comTeam.getPitcherLineup().get(2));
        
        // Test removing all
        comTeam.getPitcherLineup().set(0, pitcher1);
        comTeam.getPitcherLineup().set(1, pitcher2);
        comTeam.getPitcherLineup().set(2, pitcher3);
        
        comTeam.removeFromTeam("all", "pitcher");
        for (Pitcher p : comTeam.getPitcherLineup()) {
            assertNull(p);
        }
    }
}