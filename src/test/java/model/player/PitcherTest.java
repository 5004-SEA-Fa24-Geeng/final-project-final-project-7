package model.player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PitcherTest {
    private Pitcher pitcher;

    @BeforeEach
    void setUp() {
        pitcher = new Pitcher("Test Pitcher", 1, 100, 150, 0.65, 0.35,
                0.30, 0.20, 0.15, 0.05, 0.10, 0.05, 0.00,
                0.05, 0.00, 0.05, 0.05, 0.00, 0.00);
    }

    @Test
    void testGetters() {
        assertEquals("Test Pitcher", pitcher.getName());
        assertEquals(1, pitcher.getRotation());
        assertEquals(100, pitcher.getStrikes());
        assertEquals(150, pitcher.getPitches());
        assertEquals(0.65, pitcher.getStrikesRate());
        assertEquals(0.35, pitcher.getBallsRate());
        assertEquals(0.30, pitcher.getFourSeam());
        assertEquals(0.20, pitcher.getTwoSeam());
        assertEquals(0.15, pitcher.getCutter());
        assertEquals(0.05, pitcher.getSinker());
        assertEquals(0.10, pitcher.getSlider());
        assertEquals(0.05, pitcher.getCurve());
        assertEquals(0.00, pitcher.getKnuckle());
        assertEquals(0.05, pitcher.getSweeper());
        assertEquals(0.00, pitcher.getSlurve());
        assertEquals(0.05, pitcher.getSplitFinger());
        assertEquals(0.05, pitcher.getChangeup());
        assertEquals(0.00, pitcher.getFork());
        assertEquals(0.00, pitcher.getScrew());
    }

    @Test
    void testEquals() {
        Pitcher samePitcher = new Pitcher(
                "Test Pitcher", 1, 100, 150, 0.65, 0.35,
                0.30, 0.20, 0.15, 0.05, 0.10, 0.05,
                0.00, 0.05, 0.00, 0.05, 0.05, 0.00, 0.00
        );

        Pitcher otherPitcher = new Pitcher(
                "Different Pitcher", 2, 80, 120, 0.70, 0.30,
                0.40, 0.10, 0.20, 0.00, 0.15, 0.05,
                0.00, 0.00, 0.00, 0.00, 0.10, 0.00, 0.00
        );

        assertEquals(pitcher, samePitcher);
        assertNotEquals(pitcher, otherPitcher);
    }

    @Test
    void testToString() {
        String pitcherString = pitcher.toString();

        // Verify the string contains key information
        assertTrue(pitcherString.contains("Test Pitcher"));
        assertTrue(pitcherString.contains("Pitcher"));
        assertTrue(pitcherString.contains("Rotation=1"));
        assertTrue(pitcherString.contains("FourSeam=0.3"));

    }
}