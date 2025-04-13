package model.filter;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gameEnum.PlayerData;
import model.Operations;
import model.player.Batter;
import model.player.Pitcher;

public class PlayerFilterTest {
    
    private PlayerFilter filter;
    private Batter testBatter;
    private Pitcher testPitcher;
    
    @BeforeEach
    public void setUp() {
        filter = new PlayerFilter();
        
        // Create a test batter with sample data
        testBatter = new Batter(
            "John Doe",            // name
            100, 30, 15, 10, 2, 3, // fastball stats (PA, H, 1B, 2B, 3B, HR)
            80, 20, 10, 7, 1, 2,   // breaking stats
            50, 15, 8, 5, 1, 1,    // offspeed stats
            230, 65, 33, 22, 4, 6, // total stats
            0.75, 0.80,           // zone swing, zone contact
            0.30, 0.65,           // chase swing, chase contact
            0.283, 0.350, 0.820   // AVG, OBP, OPS
        );
        
        // Create a test pitcher with sample data
        testPitcher = new Pitcher(
            "Jane Smith",          // name
            1,                     // rotation (1 = starter)
            650, 1000,             // strikes, pitches
            0.65, 0.35,            // strikes rate, balls rate
            0.50, 0.10, 0.05, 0.05, // fastball types (4-seam, 2-seam, cutter, sinker)
            0.15, 0.05, 0.00, 0.05, 0.00, // breaking types (slider, curve, knuckle, sweeper, slurve)
            0.00, 0.05, 0.00, 0.00 // offspeed types (split-finger, changeup, fork, screw)
        );
    }
    
    // Tests for batterFilter method
    
    @Test
    public void testBatterFilterName() {
        // Test equality with exact match
        assertTrue(filter.batterFilter(testBatter, PlayerData.NAME, Operations.EQUALS, "John Doe"));
        
        // Test equality with case insensitivity
        assertTrue(filter.batterFilter(testBatter, PlayerData.NAME, Operations.EQUALS, "john doe"));
        
        // Test equality with no spaces
        assertTrue(filter.batterFilter(testBatter, PlayerData.NAME, Operations.EQUALS, "JohnDoe"));
        
        // Test inequality
        assertTrue(filter.batterFilter(testBatter, PlayerData.NAME, Operations.NOT_EQUALS, "Mike Trout"));
        
        // Test contains
        assertTrue(filter.batterFilter(testBatter, PlayerData.NAME, Operations.CONTAINS, "John"));
        
        // Test doesn't contain
        assertFalse(filter.batterFilter(testBatter, PlayerData.NAME, Operations.CONTAINS, "Smith"));
    }
    
    @Test
    public void testBatterFilterNumericValues() {
        // Test fastball PA equals
        assertTrue(filter.batterFilter(testBatter, PlayerData.F_PA, Operations.EQUALS, "100"));
        
        // Test fastball PA not equals
        assertTrue(filter.batterFilter(testBatter, PlayerData.F_PA, Operations.NOT_EQUALS, "90"));
        
        // Test fastball H greater than
        assertTrue(filter.batterFilter(testBatter, PlayerData.F_H, Operations.GREATER_THAN, "25"));
        
        // Test fastball H less than
        assertTrue(filter.batterFilter(testBatter, PlayerData.F_H, Operations.LESS_THAN, "35"));
        
        // Test fastball 1B greater than or equal
        assertTrue(filter.batterFilter(testBatter, PlayerData.F_1B, Operations.GREATER_THAN_EQUALS, "15"));
        
        // Test fastball 1B less than or equal
        assertTrue(filter.batterFilter(testBatter, PlayerData.F_1B, Operations.LESS_THAN_EQUALS, "15"));
        
        // Test fastball 2B not matching
        assertFalse(filter.batterFilter(testBatter, PlayerData.F_2B, Operations.EQUALS, "11"));
    }
    
    @Test
    public void testBatterFilterBreakingBallStats() {
        // Test breaking ball PA
        assertTrue(filter.batterFilter(testBatter, PlayerData.B_PA, Operations.EQUALS, "80"));
        
        // Test breaking ball hits
        assertTrue(filter.batterFilter(testBatter, PlayerData.B_H, Operations.EQUALS, "20"));
        
        // Test breaking ball singles
        assertTrue(filter.batterFilter(testBatter, PlayerData.B_1B, Operations.EQUALS, "10"));
        
        // Test breaking ball doubles
        assertTrue(filter.batterFilter(testBatter, PlayerData.B_2B, Operations.EQUALS, "7"));
        
        // Test breaking ball triples
        assertTrue(filter.batterFilter(testBatter, PlayerData.B_3B, Operations.EQUALS, "1"));
        
        // Test breaking ball home runs
        assertTrue(filter.batterFilter(testBatter, PlayerData.B_HR, Operations.EQUALS, "2"));
    }
    
    @Test
    public void testBatterFilterOffspeedStats() {
        // Test offspeed PA
        assertTrue(filter.batterFilter(testBatter, PlayerData.O_PA, Operations.EQUALS, "50"));
        
        // Test offspeed hits
        assertTrue(filter.batterFilter(testBatter, PlayerData.O_H, Operations.EQUALS, "15"));
        
        // Test offspeed doubles
        assertTrue(filter.batterFilter(testBatter, PlayerData.O_2B, Operations.EQUALS, "5"));
    }
    
    @Test
    public void testBatterFilterTotalStats() {
        // Test total PA
        assertTrue(filter.batterFilter(testBatter, PlayerData.T_PA, Operations.EQUALS, "230"));
        
        // Test total hits
        assertTrue(filter.batterFilter(testBatter, PlayerData.T_H, Operations.EQUALS, "65"));
        
        // Test total home runs
        assertTrue(filter.batterFilter(testBatter, PlayerData.T_HR, Operations.EQUALS, "6"));
    }
    
    @Test
    public void testBatterFilterDoubleValues() {
        // Test zone swing rate
        assertTrue(filter.batterFilter(testBatter, PlayerData.Z_SWING, Operations.EQUALS, "0.75"));
        
        // Test zone contact rate
        assertTrue(filter.batterFilter(testBatter, PlayerData.Z_CONTACT, Operations.GREATER_THAN, "0.7"));
        
        // Test chase swing rate
        assertTrue(filter.batterFilter(testBatter, PlayerData.C_SWING, Operations.LESS_THAN, "0.4"));
        
        // Test chase contact rate
        assertTrue(filter.batterFilter(testBatter, PlayerData.C_CONTACT, Operations.GREATER_THAN_EQUALS, "0.65"));
        
        // Test batting average
        assertTrue(filter.batterFilter(testBatter, PlayerData.AVG, Operations.EQUALS, "0.283"));
        
        // Test on-base percentage
        assertTrue(filter.batterFilter(testBatter, PlayerData.OBP, Operations.EQUALS, "0.350"));
        
        // Test OPS
        assertTrue(filter.batterFilter(testBatter, PlayerData.OPS, Operations.EQUALS, "0.820"));
    }
    
    // Tests for pitcherFilter method
    
    @Test
    public void testPitcherFilterName() {
        // Test equality with exact match
        assertTrue(filter.pitcherFilter(testPitcher, PlayerData.NAME, Operations.EQUALS, "Jane Smith"));
        
        // Test equality with case insensitivity
        assertTrue(filter.pitcherFilter(testPitcher, PlayerData.NAME, Operations.EQUALS, "jane smith"));
        
        // Test equality with no spaces
        assertTrue(filter.pitcherFilter(testPitcher, PlayerData.NAME, Operations.EQUALS, "JaneSmith"));
        
        // Test inequality
        assertTrue(filter.pitcherFilter(testPitcher, PlayerData.NAME, Operations.NOT_EQUALS, "John Doe"));
        
        // Test contains
        assertTrue(filter.pitcherFilter(testPitcher, PlayerData.NAME, Operations.CONTAINS, "Jane"));
        
        // Test doesn't contain
        assertFalse(filter.pitcherFilter(testPitcher, PlayerData.NAME, Operations.CONTAINS, "Doe"));
    }
    
    @Test
    public void testPitcherFilterNumericValues() {
        // Test rotation
        assertTrue(filter.pitcherFilter(testPitcher, PlayerData.ROTATION, Operations.EQUALS, "1"));
        
        // Test strikes
        assertTrue(filter.pitcherFilter(testPitcher, PlayerData.STRIKES, Operations.EQUALS, "650"));
        
        // Test pitches
        assertTrue(filter.pitcherFilter(testPitcher, PlayerData.PITCHES, Operations.EQUALS, "1000"));
        
        // Test greater than
        assertTrue(filter.pitcherFilter(testPitcher, PlayerData.STRIKES, Operations.GREATER_THAN, "600"));
        
        // Test less than
        assertTrue(filter.pitcherFilter(testPitcher, PlayerData.STRIKES, Operations.LESS_THAN, "700"));
    }
    
    @Test
    public void testPitcherFilterDoubleValues() {
        // Test strike rate
        assertTrue(filter.pitcherFilter(testPitcher, PlayerData.S_RATE, Operations.EQUALS, "0.65"));
        
        // Test ball rate
        assertTrue(filter.pitcherFilter(testPitcher, PlayerData.B_RATE, Operations.EQUALS, "0.35"));
        
        // Test greater than
        assertTrue(filter.pitcherFilter(testPitcher, PlayerData.S_RATE, Operations.GREATER_THAN, "0.6"));
        
        // Test less than
        assertTrue(filter.pitcherFilter(testPitcher, PlayerData.S_RATE, Operations.LESS_THAN, "0.7"));
    }
    
    @Test
    public void testPitcherFilterPitchTypes() {
        // Test 4-seam fastball usage
        assertTrue(filter.pitcherFilter(testPitcher, PlayerData.FOURSEAM, Operations.EQUALS, "0.5"));
        
        // Test 2-seam fastball usage
        assertTrue(filter.pitcherFilter(testPitcher, PlayerData.TWOSEAM, Operations.EQUALS, "0.1"));
        
        // Test cutter usage
        assertTrue(filter.pitcherFilter(testPitcher, PlayerData.CUTTER, Operations.EQUALS, "0.05"));
        
        // Test sinker usage
        assertTrue(filter.pitcherFilter(testPitcher, PlayerData.SINKER, Operations.EQUALS, "0.05"));
        
        // Test slider usage
        assertTrue(filter.pitcherFilter(testPitcher, PlayerData.SLIDER, Operations.EQUALS, "0.15"));
        
        // Test curveball usage
        assertTrue(filter.pitcherFilter(testPitcher, PlayerData.CURVE, Operations.EQUALS, "0.05"));
        
        // Test knuckle usage (0.0)
        assertTrue(filter.pitcherFilter(testPitcher, PlayerData.KNUCKLE, Operations.EQUALS, "0.0"));
        
        // Test sweeper usage
        assertTrue(filter.pitcherFilter(testPitcher, PlayerData.SWEEPER, Operations.EQUALS, "0.05"));
        
        // Test slurve usage (0.0)
        assertTrue(filter.pitcherFilter(testPitcher, PlayerData.SLURVE, Operations.EQUALS, "0.0"));
        
        // Test split-finger usage (0.0)
        assertTrue(filter.pitcherFilter(testPitcher, PlayerData.SFINGER, Operations.EQUALS, "0.0"));
        
        // Test changeup usage
        assertTrue(filter.pitcherFilter(testPitcher, PlayerData.CHANGEUP, Operations.EQUALS, "0.05"));
        
        // Test fork usage (0.0)
        assertTrue(filter.pitcherFilter(testPitcher, PlayerData.FORK, Operations.EQUALS, "0.0"));
        
        // Test screw usage (0.0)
        assertTrue(filter.pitcherFilter(testPitcher, PlayerData.SCREW, Operations.EQUALS, "0.0"));
    }
    
    // Tests for filterString method
    
    @Test
    public void testFilterString() {
        // Test equality
        assertTrue(filter.filterString("John", Operations.EQUALS, "John"));
        assertFalse(filter.filterString("John", Operations.EQUALS, "john"));
        
        // Test inequality
        assertTrue(filter.filterString("John", Operations.NOT_EQUALS, "Jane"));
        assertFalse(filter.filterString("John", Operations.NOT_EQUALS, "John"));
        
        // Test greater than
        assertTrue(filter.filterString("John", Operations.GREATER_THAN, "Jane"));
        assertFalse(filter.filterString("Jane", Operations.GREATER_THAN, "John"));
        
        // Test less than
        assertTrue(filter.filterString("Jane", Operations.LESS_THAN, "John"));
        assertFalse(filter.filterString("John", Operations.LESS_THAN, "Jane"));
        
        // Test greater than or equal to
        assertTrue(filter.filterString("John", Operations.GREATER_THAN_EQUALS, "John"));
        assertTrue(filter.filterString("John", Operations.GREATER_THAN_EQUALS, "Jane"));
        assertFalse(filter.filterString("Jane", Operations.GREATER_THAN_EQUALS, "John"));
        
        // Test less than or equal to
        assertTrue(filter.filterString("Jane", Operations.LESS_THAN_EQUALS, "Jane"));
        assertTrue(filter.filterString("Jane", Operations.LESS_THAN_EQUALS, "John"));
        assertFalse(filter.filterString("John", Operations.LESS_THAN_EQUALS, "Jane"));
        
        // Test contains
        assertTrue(filter.filterString("Johnny", Operations.CONTAINS, "John"));
        assertFalse(filter.filterString("Johnny", Operations.CONTAINS, "Jane"));
    }
    
    // Tests for filterNum method
    
    @Test
    public void testFilterNum() {
        // Test equality
        assertTrue(filter.filterNum(100, Operations.EQUALS, "100"));
        assertFalse(filter.filterNum(100, Operations.EQUALS, "101"));
        
        // Test inequality
        assertTrue(filter.filterNum(100, Operations.NOT_EQUALS, "101"));
        assertFalse(filter.filterNum(100, Operations.NOT_EQUALS, "100"));
        
        // Test greater than
        assertTrue(filter.filterNum(100, Operations.GREATER_THAN, "99"));
        assertFalse(filter.filterNum(100, Operations.GREATER_THAN, "100"));
        assertFalse(filter.filterNum(100, Operations.GREATER_THAN, "101"));
        
        // Test less than
        assertTrue(filter.filterNum(100, Operations.LESS_THAN, "101"));
        assertFalse(filter.filterNum(100, Operations.LESS_THAN, "100"));
        assertFalse(filter.filterNum(100, Operations.LESS_THAN, "99"));
        
        // Test greater than or equal to
        assertTrue(filter.filterNum(100, Operations.GREATER_THAN_EQUALS, "100"));
        assertTrue(filter.filterNum(100, Operations.GREATER_THAN_EQUALS, "99"));
        assertFalse(filter.filterNum(100, Operations.GREATER_THAN_EQUALS, "101"));
        
        // Test less than or equal to
        assertTrue(filter.filterNum(100, Operations.LESS_THAN_EQUALS, "100"));
        assertTrue(filter.filterNum(100, Operations.LESS_THAN_EQUALS, "101"));
        assertFalse(filter.filterNum(100, Operations.LESS_THAN_EQUALS, "99"));
        
        // Test contains (should always return false for numbers)
        assertFalse(filter.filterNum(100, Operations.CONTAINS, "10"));
    }
    
    // Tests for filterDouble method
    
    @Test
    public void testFilterDouble() {
        // Test equality
        assertTrue(filter.filterDouble(0.75, Operations.EQUALS, "0.75"));
        assertFalse(filter.filterDouble(0.75, Operations.EQUALS, "0.76"));
        
        // Test inequality
        assertTrue(filter.filterDouble(0.75, Operations.NOT_EQUALS, "0.76"));
        assertFalse(filter.filterDouble(0.75, Operations.NOT_EQUALS, "0.75"));
        
        // Test greater than
        assertTrue(filter.filterDouble(0.75, Operations.GREATER_THAN, "0.74"));
        assertFalse(filter.filterDouble(0.75, Operations.GREATER_THAN, "0.75"));
        assertFalse(filter.filterDouble(0.75, Operations.GREATER_THAN, "0.76"));
        
        // Test less than
        assertTrue(filter.filterDouble(0.75, Operations.LESS_THAN, "0.76"));
        assertFalse(filter.filterDouble(0.75, Operations.LESS_THAN, "0.75"));
        assertFalse(filter.filterDouble(0.75, Operations.LESS_THAN, "0.74"));
        
        // Test greater than or equal to
        assertTrue(filter.filterDouble(0.75, Operations.GREATER_THAN_EQUALS, "0.75"));
        assertTrue(filter.filterDouble(0.75, Operations.GREATER_THAN_EQUALS, "0.74"));
        assertFalse(filter.filterDouble(0.75, Operations.GREATER_THAN_EQUALS, "0.76"));
        
        // Test less than or equal to
        assertTrue(filter.filterDouble(0.75, Operations.LESS_THAN_EQUALS, "0.75"));
        assertTrue(filter.filterDouble(0.75, Operations.LESS_THAN_EQUALS, "0.76"));
        assertFalse(filter.filterDouble(0.75, Operations.LESS_THAN_EQUALS, "0.74"));
        
        // Test contains (should always return false for doubles)
        assertFalse(filter.filterDouble(0.75, Operations.CONTAINS, "0.7"));
    }
    
    // Test invalid operations or edge cases
    
    @Test
    public void testInvalidColumn() {
        // Using a pitcher column for a batter
        assertFalse(filter.batterFilter(testBatter, PlayerData.ROTATION, Operations.EQUALS, "1"));
        
        // Using a batter column for a pitcher
        assertFalse(filter.pitcherFilter(testPitcher, PlayerData.F_PA, Operations.EQUALS, "100"));
    }
    
    @Test
    public void testFilterNumWithInvalidInput() {
        assertThrows(NumberFormatException.class, () -> {
            filter.filterNum(100, Operations.EQUALS, "not a number");
        });
    }
    
    @Test
    public void testFilterDoubleWithInvalidInput() {
        assertThrows(NumberFormatException.class, () -> {
            filter.filterDouble(0.75, Operations.EQUALS, "not a number");
        });
    }
}