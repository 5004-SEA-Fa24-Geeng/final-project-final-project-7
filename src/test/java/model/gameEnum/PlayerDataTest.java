package model.gameEnum;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import gameEnum.PlayerData;

public class PlayerDataTest {
    
    @Test
    public void testGetColumnName() {
        assertEquals("Name", PlayerData.NAME.getColumnName());
        
        // Batters
        assertEquals("FastballPA", PlayerData.F_PA.getColumnName());
        assertEquals("FastballH", PlayerData.F_H.getColumnName());
        assertEquals("Fastball1B", PlayerData.F_1B.getColumnName());
        assertEquals("Fastball2B", PlayerData.F_2B.getColumnName());
        assertEquals("Fastball3B", PlayerData.F_3B.getColumnName());
        assertEquals("FastballHR", PlayerData.F_HR.getColumnName());
        
        assertEquals("BreakingPA", PlayerData.B_PA.getColumnName());
        assertEquals("BreakingH", PlayerData.B_H.getColumnName());
        assertEquals("Breaking1B", PlayerData.B_1B.getColumnName());
        assertEquals("Breaking2B", PlayerData.B_2B.getColumnName());
        assertEquals("Breaking3B", PlayerData.B_3B.getColumnName());
        assertEquals("BreakingHR", PlayerData.B_HR.getColumnName());
        
        assertEquals("OffspeedPA", PlayerData.O_PA.getColumnName());
        assertEquals("OffspeedH", PlayerData.O_H.getColumnName());
        assertEquals("Offspeed1B", PlayerData.O_1B.getColumnName());
        assertEquals("Offspeed2B", PlayerData.O_2B.getColumnName());
        assertEquals("Offspeed3B", PlayerData.O_3B.getColumnName());
        assertEquals("OffspeedHR", PlayerData.O_HR.getColumnName());
        
        assertEquals("TotalPA", PlayerData.T_PA.getColumnName());
        assertEquals("TotalH", PlayerData.T_H.getColumnName());
        assertEquals("Total1B", PlayerData.T_1B.getColumnName());
        assertEquals("Total2B", PlayerData.T_2B.getColumnName());
        assertEquals("Total3B", PlayerData.T_3B.getColumnName());
        assertEquals("TotalHR", PlayerData.T_HR.getColumnName());
        
        assertEquals("ZoneSwing", PlayerData.Z_SWING.getColumnName());
        assertEquals("ZoneContact", PlayerData.Z_CONTACT.getColumnName());
        assertEquals("ChaseSwing", PlayerData.C_SWING.getColumnName());
        assertEquals("ChaseContact", PlayerData.C_CONTACT.getColumnName());
        
        assertEquals("AVG", PlayerData.AVG.getColumnName());
        assertEquals("OBP", PlayerData.OBP.getColumnName());
        assertEquals("OPS", PlayerData.OPS.getColumnName());
        
        // Pitchers
        assertEquals("Rotation", PlayerData.ROTATION.getColumnName());
        assertEquals("Strikes", PlayerData.STRIKES.getColumnName());
        assertEquals("Pitches", PlayerData.PITCHES.getColumnName());
        assertEquals("StrikesRate", PlayerData.S_RATE.getColumnName());
        assertEquals("BallsRate", PlayerData.B_RATE.getColumnName());
        
        assertEquals("4-SeamFastball", PlayerData.FOURSEAM.getColumnName());
        assertEquals("2-SeamFastball", PlayerData.TWOSEAM.getColumnName());
        assertEquals("Cutter", PlayerData.CUTTER.getColumnName());
        assertEquals("Sinker", PlayerData.SINKER.getColumnName());
        assertEquals("Slider", PlayerData.SLIDER.getColumnName());
        assertEquals("Curveball", PlayerData.CURVE.getColumnName());
        assertEquals("Knuckle", PlayerData.KNUCKLE.getColumnName());
        assertEquals("Sweeper", PlayerData.SWEEPER.getColumnName());
        assertEquals("Slurve", PlayerData.SLURVE.getColumnName());
        assertEquals("Split-Finger", PlayerData.SFINGER.getColumnName());
        assertEquals("Changeup", PlayerData.CHANGEUP.getColumnName());
        assertEquals("Fork", PlayerData.FORK.getColumnName());
        assertEquals("Screw", PlayerData.SCREW.getColumnName());
    }
    
    @Test
    public void testFromColumnName_ValidNames() {
        assertEquals(PlayerData.NAME, PlayerData.fromColumnName("Name"));
        
        // Batters
        assertEquals(PlayerData.F_PA, PlayerData.fromColumnName("FastballPA"));
        assertEquals(PlayerData.F_H, PlayerData.fromColumnName("FastballH"));
        assertEquals(PlayerData.F_1B, PlayerData.fromColumnName("Fastball1B"));
        assertEquals(PlayerData.F_2B, PlayerData.fromColumnName("Fastball2B"));
        assertEquals(PlayerData.F_3B, PlayerData.fromColumnName("Fastball3B"));
        assertEquals(PlayerData.F_HR, PlayerData.fromColumnName("FastballHR"));
        
        assertEquals(PlayerData.B_PA, PlayerData.fromColumnName("BreakingPA"));
        assertEquals(PlayerData.B_H, PlayerData.fromColumnName("BreakingH"));
        assertEquals(PlayerData.B_1B, PlayerData.fromColumnName("Breaking1B"));
        assertEquals(PlayerData.B_2B, PlayerData.fromColumnName("Breaking2B"));
        assertEquals(PlayerData.B_3B, PlayerData.fromColumnName("Breaking3B"));
        assertEquals(PlayerData.B_HR, PlayerData.fromColumnName("BreakingHR"));
        
        assertEquals(PlayerData.O_PA, PlayerData.fromColumnName("OffspeedPA"));
        assertEquals(PlayerData.O_H, PlayerData.fromColumnName("OffspeedH"));
        assertEquals(PlayerData.O_1B, PlayerData.fromColumnName("Offspeed1B"));
        assertEquals(PlayerData.O_2B, PlayerData.fromColumnName("Offspeed2B"));
        assertEquals(PlayerData.O_3B, PlayerData.fromColumnName("Offspeed3B"));
        assertEquals(PlayerData.O_HR, PlayerData.fromColumnName("OffspeedHR"));
        
        assertEquals(PlayerData.T_PA, PlayerData.fromColumnName("TotalPA"));
        assertEquals(PlayerData.T_H, PlayerData.fromColumnName("TotalH"));
        assertEquals(PlayerData.T_1B, PlayerData.fromColumnName("Total1B"));
        assertEquals(PlayerData.T_2B, PlayerData.fromColumnName("Total2B"));
        assertEquals(PlayerData.T_3B, PlayerData.fromColumnName("Total3B"));
        assertEquals(PlayerData.T_HR, PlayerData.fromColumnName("TotalHR"));
        
        assertEquals(PlayerData.Z_SWING, PlayerData.fromColumnName("ZoneSwing"));
        assertEquals(PlayerData.Z_CONTACT, PlayerData.fromColumnName("ZoneContact"));
        assertEquals(PlayerData.C_SWING, PlayerData.fromColumnName("ChaseSwing"));
        assertEquals(PlayerData.C_CONTACT, PlayerData.fromColumnName("ChaseContact"));
        
        assertEquals(PlayerData.AVG, PlayerData.fromColumnName("AVG"));
        assertEquals(PlayerData.OBP, PlayerData.fromColumnName("OBP"));
        assertEquals(PlayerData.OPS, PlayerData.fromColumnName("OPS"));
        
        // Pitchers
        assertEquals(PlayerData.ROTATION, PlayerData.fromColumnName("Rotation"));
        assertEquals(PlayerData.STRIKES, PlayerData.fromColumnName("Strikes"));
        assertEquals(PlayerData.PITCHES, PlayerData.fromColumnName("Pitches"));
        assertEquals(PlayerData.S_RATE, PlayerData.fromColumnName("StrikesRate"));
        assertEquals(PlayerData.B_RATE, PlayerData.fromColumnName("BallsRate"));
        
        assertEquals(PlayerData.FOURSEAM, PlayerData.fromColumnName("4-SeamFastball"));
        assertEquals(PlayerData.TWOSEAM, PlayerData.fromColumnName("2-SeamFastball"));
        assertEquals(PlayerData.CUTTER, PlayerData.fromColumnName("Cutter"));
        assertEquals(PlayerData.SINKER, PlayerData.fromColumnName("Sinker"));
        assertEquals(PlayerData.SLIDER, PlayerData.fromColumnName("Slider"));
        assertEquals(PlayerData.CURVE, PlayerData.fromColumnName("Curveball"));
        assertEquals(PlayerData.KNUCKLE, PlayerData.fromColumnName("Knuckle"));
        assertEquals(PlayerData.SWEEPER, PlayerData.fromColumnName("Sweeper"));
        assertEquals(PlayerData.SLURVE, PlayerData.fromColumnName("Slurve"));
        assertEquals(PlayerData.SFINGER, PlayerData.fromColumnName("Split-Finger"));
        assertEquals(PlayerData.CHANGEUP, PlayerData.fromColumnName("Changeup"));
        assertEquals(PlayerData.FORK, PlayerData.fromColumnName("Fork"));
        assertEquals(PlayerData.SCREW, PlayerData.fromColumnName("Screw"));
    }
    
    @Test
    public void testFromColumnName_InvalidName() {
        assertThrows(IllegalArgumentException.class, () -> {
            PlayerData.fromColumnName("NonExistentColumn");
        });
    }
    
    @Test
    public void testFromString_UsingEnumName() {
        // Test all enum names
        assertEquals(PlayerData.NAME, PlayerData.fromString("NAME"));
        
        // Batters (testing a subset for brevity)
        assertEquals(PlayerData.F_PA, PlayerData.fromString("F_PA"));
        assertEquals(PlayerData.F_HR, PlayerData.fromString("F_HR"));
        assertEquals(PlayerData.B_PA, PlayerData.fromString("B_PA"));
        assertEquals(PlayerData.B_HR, PlayerData.fromString("B_HR"));
        assertEquals(PlayerData.O_PA, PlayerData.fromString("O_PA"));
        assertEquals(PlayerData.O_HR, PlayerData.fromString("O_HR"));
        assertEquals(PlayerData.T_PA, PlayerData.fromString("T_PA"));
        assertEquals(PlayerData.T_HR, PlayerData.fromString("T_HR"));
    }
    
    @Test
    public void testFromString_UsingColumnName() {
        // Test all column names (testing a subset for brevity)
        assertEquals(PlayerData.NAME, PlayerData.fromString("Name"));
        assertEquals(PlayerData.F_PA, PlayerData.fromString("FastballPA"));
        assertEquals(PlayerData.B_HR, PlayerData.fromString("BreakingHR"));
        assertEquals(PlayerData.O_1B, PlayerData.fromString("Offspeed1B"));
    }
    
    @Test
    public void testFromString_CaseInsensitive() {
        assertEquals(PlayerData.NAME, PlayerData.fromString("name"));
        assertEquals(PlayerData.F_PA, PlayerData.fromString("fastballpa"));
        assertEquals(PlayerData.FOURSEAM, PlayerData.fromString("4-seamfastball"));
    }
    
    @Test
    public void testFromString_InvalidName() {
        assertThrows(IllegalArgumentException.class, () -> {
            PlayerData.fromString("NonExistentColumn");
        });
    }
}
