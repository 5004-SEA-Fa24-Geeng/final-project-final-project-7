package model.player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BatterTest {
    private Batter batter;

    @BeforeEach
    void setUp() {
        batter = new Batter("Test Batter", 100, 30, 20, 5, 2,
                3, 100, 25, 15, 5, 2, 3,
                100, 20, 10, 5, 3, 2,
                300, 75, 45, 15, 7, 8, 0.65, 0.80,
                0.30, 0.40, 0.250, 0.320, 0.780);
    }

    @Test
    void testConstructorAndBasicGetters() {
        assertEquals("Test Batter", batter.getName());
        assertEquals("Batter", batter.getPosition());
    }

    @Test
    void testGetters() {
        assertEquals(100, batter.getFastballPA());
        assertEquals(30, batter.getFastballH());
        assertEquals(20, batter.getFastball1B());
        assertEquals(5, batter.getFastball2B());
        assertEquals(2, batter.getFastball3B());
        assertEquals(3, batter.getFastballHR());
        assertEquals(100, batter.getBreakingPA());
        assertEquals(25, batter.getBreakingH());
        assertEquals(15, batter.getBreaking1B());
        assertEquals(5, batter.getBreaking2B());
        assertEquals(2, batter.getBreaking3B());
        assertEquals(3, batter.getBreakingHR());
        assertEquals(100, batter.getOffspeedPA());
        assertEquals(20, batter.getOffspeedH());
        assertEquals(10, batter.getOffspeed1B());
        assertEquals(5, batter.getOffspeed2B());
        assertEquals(3, batter.getOffspeed3B());
        assertEquals(2, batter.getOffspeedHR());
        assertEquals(300, batter.getTotalPA());
        assertEquals(75, batter.getTotalH());
        assertEquals(45, batter.getTotal1B());
        assertEquals(15, batter.getTotal2B());
        assertEquals(7, batter.getTotal3B());
        assertEquals(8, batter.getTotalHR());
        assertEquals(0.65, batter.getZoneSwing());
        assertEquals(0.80, batter.getZoneContact());
        assertEquals(0.30, batter.getChaseSwing());
        assertEquals(0.40, batter.getChaseContact());
        assertEquals(0.250, batter.getAVG());
        assertEquals(0.320, batter.getOBP());
        assertEquals(0.780, batter.getOPS());
    }

    @Test
    void testEquals() {
        Batter sameBatter = new Batter("Test Batter",100, 30, 20, 5, 2,
                3, 100, 25, 15, 5, 2, 3,
                100, 20, 10, 5, 3, 2,
                300, 75, 45, 15, 7, 8, 0.65, 0.80,
                0.30, 0.40, 0.250, 0.320, 0.780);

        Batter otherBatter = new Batter("Other Batter", 80, 20, 10, 5, 2,
                3, 90, 20, 10, 5, 2, 3,
                100, 20, 10, 5, 3, 2,
                300, 70, 40, 15, 7, 8, 0.65, 0.80,
                0.30, 0.40, 0.420, 0.450, 0.890);

        assertEquals(batter, sameBatter);
        assertNotEquals(batter, otherBatter);
    }

    @Test
    void testToString() {
        String batterString = batter.toString();
        assertTrue(batterString.contains("Test Batter"));
        assertTrue(batterString.contains("Batter"));
        assertTrue(batterString.contains("AVG=0.25"));
        assertTrue(batterString.contains("OBP=0.32"));
        assertTrue(batterString.contains("OPS=0.78"));
    }
}