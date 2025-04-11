package model.loader;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import gameEnum.PlayerData;
import gameEnum.Teams;
import model.player.Batter;
import model.player.Pitcher;

public class PlayerLoaderTest {
    
    private PlayerLoader loader;
    private String validBatterCsvLine;
    private String validPitcherCsvLine;
    private Map<PlayerData, Integer> batterColumnMap;
    private Map<PlayerData, Integer> pitcherColumnMap;
    
    @BeforeEach
    public void setUp() {
        loader = new PlayerLoader();
        
        // Sample valid batter line from Mariners-batter-convert.csv
        validBatterCsvLine = "Julio Rodriguez,324,94,71,10,0,13,202,47,37,5,0,5,86,14,10,2,0,2,612,155,118,17,0,20,0.746,0.787,0.374,0.532,0.273,0.325,0.734";
        
        // Sample valid pitcher line from angels-pitcher-convert.csv
        validPitcherCsvLine = "Tyler Anderson,1,1863,2915,0.639,0.361,0.396,0.000,0.185,0.033,0.013,0.000,0.000,0.000,0.000,0.000,0.371,0.000,0.000";
        
        // Create column maps for testing
        batterColumnMap = new HashMap<>();
        batterColumnMap.put(PlayerData.NAME, 0);
        batterColumnMap.put(PlayerData.F_PA, 1);
        batterColumnMap.put(PlayerData.F_H, 2);
        batterColumnMap.put(PlayerData.F_1B, 3);
        batterColumnMap.put(PlayerData.F_2B, 4);
        batterColumnMap.put(PlayerData.F_3B, 5);
        batterColumnMap.put(PlayerData.F_HR, 6);
        batterColumnMap.put(PlayerData.B_PA, 7);
        batterColumnMap.put(PlayerData.B_H, 8);
        batterColumnMap.put(PlayerData.B_1B, 9);
        batterColumnMap.put(PlayerData.B_2B, 10);
        batterColumnMap.put(PlayerData.B_3B, 11);
        batterColumnMap.put(PlayerData.B_HR, 12);
        batterColumnMap.put(PlayerData.O_PA, 13);
        batterColumnMap.put(PlayerData.O_H, 14);
        batterColumnMap.put(PlayerData.O_1B, 15);
        batterColumnMap.put(PlayerData.O_2B, 16);
        batterColumnMap.put(PlayerData.O_3B, 17);
        batterColumnMap.put(PlayerData.O_HR, 18);
        batterColumnMap.put(PlayerData.T_PA, 19);
        batterColumnMap.put(PlayerData.T_H, 20);
        batterColumnMap.put(PlayerData.T_1B, 21);
        batterColumnMap.put(PlayerData.T_2B, 22);
        batterColumnMap.put(PlayerData.T_3B, 23);
        batterColumnMap.put(PlayerData.T_HR, 24);
        batterColumnMap.put(PlayerData.Z_SWING, 25);
        batterColumnMap.put(PlayerData.Z_CONTACT, 26);
        batterColumnMap.put(PlayerData.C_SWING, 27);
        batterColumnMap.put(PlayerData.C_CONTACT, 28);
        batterColumnMap.put(PlayerData.AVG, 29);
        batterColumnMap.put(PlayerData.OBP, 30);
        batterColumnMap.put(PlayerData.OPS, 31);
        
        pitcherColumnMap = new HashMap<>();
        pitcherColumnMap.put(PlayerData.NAME, 0);
        pitcherColumnMap.put(PlayerData.ROTATION, 1);
        pitcherColumnMap.put(PlayerData.STRIKES, 2);
        pitcherColumnMap.put(PlayerData.PITCHES, 3);
        pitcherColumnMap.put(PlayerData.S_RATE, 4);
        pitcherColumnMap.put(PlayerData.B_RATE, 5);
        pitcherColumnMap.put(PlayerData.FOURSEAM, 6);
        pitcherColumnMap.put(PlayerData.TWOSEAM, 7);
        pitcherColumnMap.put(PlayerData.CUTTER, 8);
        pitcherColumnMap.put(PlayerData.SINKER, 9);
        pitcherColumnMap.put(PlayerData.SLIDER, 10);
        pitcherColumnMap.put(PlayerData.CURVE, 11);
        pitcherColumnMap.put(PlayerData.KNUCKLE, 12);
        pitcherColumnMap.put(PlayerData.SWEEPER, 13);
        pitcherColumnMap.put(PlayerData.SLURVE, 14);
        pitcherColumnMap.put(PlayerData.SFINGER, 15);
        pitcherColumnMap.put(PlayerData.CHANGEUP, 16);
        pitcherColumnMap.put(PlayerData.FORK, 17);
        pitcherColumnMap.put(PlayerData.SCREW, 18);
    }
    
    /**
     * Test for loadBatters method with MARINERS team
     */
    @Test
    public void testLoadBattersWithMariners() {
        Set<Batter> batters = loader.loadBatters(Teams.MARINERS);
        
        // Verify batters are loaded
        assertNotNull(batters);
        assertFalse(batters.isEmpty());
        
        // Check if Julio Rodriguez is in the loaded set
        boolean foundJulio = false;
        for (Batter batter : batters) {
            if ("Julio Rodriguez".equals(batter.getName())) {
                foundJulio = true;
                // Verify some stats for Julio Rodriguez
                assertEquals(612, batter.getTotalPA());
                assertEquals(155, batter.getTotalH());
                assertEquals(20, batter.getTotalHR());
                assertEquals(0.273, batter.getAVG(), 0.001);
                break;
            }
        }
        assertTrue(foundJulio, "Julio Rodriguez should be in the Mariners batters");
    }
    
    /**
     * Test for loadBatters method with a team other than MARINERS
     */
    @Test
    public void testLoadBattersWithNonMariners() {
        Set<Batter> batters = loader.loadBatters(Teams.ANGELS);
        assertNull(batters, "Only Mariners batters should be available");
    }
    
    /**
     * Test for loadPitchers method with ANGELS team
     */
    @Test
    public void testLoadPitchersWithAngels() {
        Set<Pitcher> pitchers = loader.loadPitchers(Teams.ANGELS);
        
        // Verify pitchers are loaded
        assertNotNull(pitchers);
        assertFalse(pitchers.isEmpty());
        
        // Check if Tyler Anderson is in the loaded set
        boolean foundAnderson = false;
        for (Pitcher pitcher : pitchers) {
            if ("Tyler Anderson".equals(pitcher.getName())) {
                foundAnderson = true;
                // Verify some stats for Tyler Anderson
                assertEquals(1, pitcher.getRotation());
                assertEquals(1863, pitcher.getStrikes());
                assertEquals(2915, pitcher.getPitches());
                assertEquals(0.639, pitcher.getStrikesRate(), 0.001);
                break;
            }
        }
        assertTrue(foundAnderson, "Tyler Anderson should be in the Angels pitchers");
    }
    
    /**
     * Test for loadPitchers method with MARINERS team
     */
    @Test
    public void testLoadPitchersWithMariners() {
        Set<Pitcher> pitchers = loader.loadPitchers(Teams.MARINERS);
        assertNull(pitchers, "Mariners pitchers should not be available in this method");
    }
    
    /**
     * Test for toPitcher method with valid line
     */
    @Test
    public void testToPitcherWithValidLine() {
        Pitcher pitcher = loader.toPitcher(validPitcherCsvLine, pitcherColumnMap);
        
        // Verify pitcher is created correctly
        assertNotNull(pitcher);
        assertEquals("Tyler Anderson", pitcher.getName());
        assertEquals(1, pitcher.getRotation());
        assertEquals(1863, pitcher.getStrikes());
        assertEquals(2915, pitcher.getPitches());
        assertEquals(0.639, pitcher.getStrikesRate(), 0.001);
        assertEquals(0.361, pitcher.getBallsRate(), 0.001);
        assertEquals(0.396, pitcher.getFourSeam(), 0.001);
        assertEquals(0.0, pitcher.getTwoSeam(), 0.001);
        assertEquals(0.185, pitcher.getCutter(), 0.001);
        assertEquals(0.033, pitcher.getSinker(), 0.001);
        assertEquals(0.013, pitcher.getSlider(), 0.001);
        assertEquals(0.0, pitcher.getCurve(), 0.001);
        assertEquals(0.0, pitcher.getKnuckle(), 0.001);
        assertEquals(0.0, pitcher.getSweeper(), 0.001);
        assertEquals(0.0, pitcher.getSlurve(), 0.001);
        assertEquals(0.0, pitcher.getSplitFinger(), 0.001);
        assertEquals(0.371, pitcher.getChangeup(), 0.001);
        assertEquals(0.0, pitcher.getFork(), 0.001);
        assertEquals(0.0, pitcher.getScrew(), 0.001);
    }
    
    /**
     * Test for toPitcher method with invalid line (missing data)
     */
    @Test
    public void testToPitcherWithInvalidLine() {
        // Line with not enough columns
        String invalidLine = "Shohei Ohtani,1,772";
        Pitcher pitcher = loader.toPitcher(invalidLine, pitcherColumnMap);
        
        // Should return null for invalid line
        assertNull(pitcher);
    }
    
    /**
     * Test for toPitcher method with invalid data (number format exception)
     */
    @Test
    public void testToPitcherWithInvalidData() {
        // Line with invalid number format
        String invalidLine = "Tyler Anderson,1,NotANumber,1280,0.621,0.379,0.231,0.000,0.000,0.230,0.000,0.000,0.000,0.000,0.000,0.000,0.538,0.000,0.000";
        Pitcher pitcher = loader.toPitcher(invalidLine, pitcherColumnMap);
        
        // Should return null for invalid data
        assertNull(pitcher);
    }
    
    /**
     * Test for toBatter method with valid line
     */
    @Test
    public void testToBatterWithValidLine() {
        Batter batter = loader.toBatter(validBatterCsvLine, batterColumnMap);
        
        // Verify batter is created correctly
        assertNotNull(batter);
        assertEquals("Julio Rodriguez", batter.getName());
        assertEquals(324, batter.getFastballPA());
        assertEquals(94, batter.getFastballH());
        assertEquals(71, batter.getFastball1B());
        assertEquals(10, batter.getFastball2B());
        assertEquals(0, batter.getFastball3B());
        assertEquals(13, batter.getFastballHR());
        assertEquals(202, batter.getBreakingPA());
        assertEquals(47, batter.getBreakingH());
        assertEquals(37, batter.getBreaking1B());
        assertEquals(5, batter.getBreaking2B());
        assertEquals(0, batter.getBreaking3B());
        assertEquals(5, batter.getBreakingHR());
        assertEquals(86, batter.getOffspeedPA());
        assertEquals(14, batter.getOffspeedH());
        assertEquals(10, batter.getOffspeed1B());
        assertEquals(2, batter.getOffspeed2B());
        assertEquals(0, batter.getOffspeed3B());
        assertEquals(2, batter.getOffspeedHR());
        assertEquals(612, batter.getTotalPA());
        assertEquals(155, batter.getTotalH());
        assertEquals(118, batter.getTotal1B());
        assertEquals(17, batter.getTotal2B());
        assertEquals(0, batter.getTotal3B());
        assertEquals(20, batter.getTotalHR());
        assertEquals(0.746, batter.getZoneSwing(), 0.001);
        assertEquals(0.787, batter.getZoneContact(), 0.001);
        assertEquals(0.374, batter.getChaseSwing(), 0.001);
        assertEquals(0.532, batter.getChaseContact(), 0.001);
        assertEquals(0.273, batter.getAVG(), 0.001);
        assertEquals(0.325, batter.getOBP(), 0.001);
        assertEquals(0.734, batter.getOPS(), 0.001);
    }
    
    /**
     * Test for toBatter method with invalid line (missing data)
     */
    @Test
    public void testToBatterWithInvalidLine() {
        // Line with not enough columns
        String invalidLine = "Julio Rodriguez,254,78,44";
        Batter batter = loader.toBatter(invalidLine, batterColumnMap);
        
        // Should return null for invalid line
        assertNull(batter);
    }
    
    /**
     * Test for toBatter method with invalid data (number format exception)
     */
    @Test
    public void testToBatterWithInvalidData() {
        // Line with invalid number format
        String invalidLine = "Julio Rodriguez,NotANumber,78,44,20,3,11,149,48,26,12,1,9,102,32,21,7,1,3,505,158,91,39,5,23,0.655,0.836,0.381,0.635,0.313,0.366,0.853";
        Batter batter = loader.toBatter(invalidLine, batterColumnMap);
        
        // Should return null for invalid data
        assertNull(batter);
    }
    
    /**
     * Test for processHeader private method via reflection
     * @throws Exception if reflection fails
     */
    @Test
    public void testProcessHeader() throws Exception {
        // Access private method using reflection
        Method processHeaderMethod = PlayerLoader.class.getDeclaredMethod("processHeader", String.class);
        processHeaderMethod.setAccessible(true);
        
        // Test with batter header
        String batterHeader = "Name,FastballPA,FastballH,Fastball1B,Fastball2B,Fastball3B,FastballHR,BreakingPA,BreakingH,Breaking1B,Breaking2B,Breaking3B,BreakingHR,OffspeedPA,OffspeedH,Offspeed1B,Offspeed2B,Offspeed3B,OffspeedHR,TotalPA,TotalH,Total1B,Total2B,Total3B,TotalHR,ZoneSwing,ZoneContact,ChaseSwing,ChaseContact,AVG,OBP,OPS";
        @SuppressWarnings("unchecked")
        Map<PlayerData, Integer> result = (Map<PlayerData, Integer>) processHeaderMethod.invoke(loader, batterHeader);
        
        // Verify the mapping is correct
        assertNotNull(result);
        assertEquals(32, result.size()); // 31 valid columns in the header
        assertEquals(0, result.get(PlayerData.NAME).intValue());
        assertEquals(1, result.get(PlayerData.F_PA).intValue());
        assertEquals(19, result.get(PlayerData.T_PA).intValue());
        assertEquals(29, result.get(PlayerData.AVG).intValue());
        
        // Test with pitcher header
        String pitcherHeader = "Name,Rotation,Strikes,Pitches,StrikesRate,BallsRate,4-SeamFastball,2-SeamFastball,Cutter,Sinker,Slider,Curveball,Knuckle,Sweeper,Slurve,Split-Finger,Changeup,Fork,Screw";
        @SuppressWarnings("unchecked")
        Map<PlayerData, Integer> pitcherResult = (Map<PlayerData, Integer>) processHeaderMethod.invoke(loader, pitcherHeader);
        
        // Verify the mapping is correct
        assertNotNull(pitcherResult);
        assertEquals(19, pitcherResult.size()); // 19 valid columns in the header
        assertEquals(0, pitcherResult.get(PlayerData.NAME).intValue());
        assertEquals(1, pitcherResult.get(PlayerData.ROTATION).intValue());
        assertEquals(2, pitcherResult.get(PlayerData.STRIKES).intValue());
        assertEquals(6, pitcherResult.get(PlayerData.FOURSEAM).intValue());
    }
    
    /**
     * Test for getFilePath private method via reflection
     * @throws Exception if reflection fails
     */
    @Test
    public void testGetFilePath() throws Exception {
        // Access private method using reflection
        Method getFilePathMethod = PlayerLoader.class.getDeclaredMethod("getFilePath", String.class, Teams.class);
        getFilePathMethod.setAccessible(true);
        
        // Test for Mariners batter file path
        String batterPath = (String) getFilePathMethod.invoke(loader, "batter", Teams.MARINERS);
        assertEquals("/BatterDataConvert/Mariners-batter-convert.csv", batterPath);
        
        // Test for Angels pitcher file path
        String pitcherPath = (String) getFilePathMethod.invoke(loader, "pitcher", Teams.ANGELS);
        assertEquals("/PitcherDataConvert/angels-pitcher-convert.csv", pitcherPath);
        
        // Test for invalid team for batter
        assertThrows(Exception.class, () -> {
            getFilePathMethod.invoke(loader, "batter", Teams.ANGELS);
        });
    }
    
    /**
     * Test for loadBatterFromFile private method via reflection and using mocking frameworks
     * Note: This test is commented out because it requires additional mocking libraries
     * For actual implementation, you would need to add PowerMock/Mockito to mock static methods
     */
    @Test
    public void testLoadBatterFromFile() {
        // In a real test implementation with a mocking framework like PowerMock,
        // you would mock the static method calls to ResourceLoader.class.getResourceAsStream
        // Since we can't use that here, we'll verify the method indirectly via loadBatters
        
        // Use the public loadBatters method which internally calls loadBatterFromFile
        Set<Batter> batters = loader.loadBatters(Teams.MARINERS);
        
        // Verify batters are loaded correctly
        assertNotNull(batters);
        assertFalse(batters.isEmpty());
        
        // Check if a known batter is in the loaded set
        boolean foundKnownBatter = false;
        for (Batter batter : batters) {
            if ("Julio Rodriguez".equals(batter.getName())) {
                foundKnownBatter = true;
                // Some basic validation
                assertTrue(batter.getTotalPA() > 0);
                assertTrue(batter.getTotalH() > 0);
                break;
            }
        }
        assertTrue(foundKnownBatter, "Known batter should be in the dataset");
    }
    
    /**
     * Test for loadPitcherFromFile private method via reflection and using mocking frameworks
     * Note: This test is commented out because it requires additional mocking libraries
     * For actual implementation, you would need to add PowerMock/Mockito to mock static methods
     */
    @Test
    public void testLoadPitcherFromFile() {
        // In a real test implementation with a mocking framework like PowerMock,
        // you would mock the static method calls to ResourceLoader.class.getResourceAsStream
        // Since we can't use that here, we'll verify the method indirectly via loadPitchers
        
        // Use the public loadPitchers method which internally calls loadPitcherFromFile
        Set<Pitcher> pitchers = loader.loadPitchers(Teams.ANGELS);
        
        // Verify pitchers are loaded correctly
        assertNotNull(pitchers);
        assertFalse(pitchers.isEmpty());
        
        // Check if a known pitcher is in the loaded set
        boolean foundKnownPitcher = false;
        for (Pitcher pitcher : pitchers) {
            if ("Tyler Anderson".equals(pitcher.getName())) {
                foundKnownPitcher = true;
                // Some basic validation
                assertEquals(1, pitcher.getRotation());
                assertTrue(pitcher.getStrikes() > 0);
                assertTrue(pitcher.getPitches() > 0);
                break;
            }
        }
        assertTrue(foundKnownPitcher, "Known pitcher should be in the dataset");
    }
    

}