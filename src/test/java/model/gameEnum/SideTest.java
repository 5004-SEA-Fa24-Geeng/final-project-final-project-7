package model.gameEnum;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import gameEnum.Side;

public class SideTest {
    
    @Test
    public void testGetCmdName() {
        // Test all enum values
        assertEquals("player", Side.PLAYER.getCmdName());
        assertEquals("computer", Side.COMPUTER.getCmdName());
    }

    @Test
    public void testFromCmdName_ValidNames() {
        assertEquals(Side.PLAYER, Side.fromCmdName("player"));
        assertEquals(Side.COMPUTER, Side.fromCmdName("computer"));
    }
    
    @Test
    public void testFromCmdName_InvalidName() {
        assertThrows(IllegalArgumentException.class, () -> {
            Side.fromCmdName("nonexistentside");
        });
    }
    
    @Test
    public void testFromString_UsingEnumName() {
        assertEquals(Side.PLAYER, Side.fromString("PLAYER"));
        assertEquals(Side.COMPUTER, Side.fromString("COMPUTER"));
    }
    
    @Test
    public void testFromString_UsingCmdName() {
        assertEquals(Side.PLAYER, Side.fromString("player"));
        assertEquals(Side.COMPUTER, Side.fromString("computer"));
    }
    
    @Test
    public void testFromString_CaseInsensitive() {
        assertEquals(Side.PLAYER, Side.fromString("Player"));
        assertEquals(Side.COMPUTER, Side.fromString("CompUTER"));
    }
    
    @Test
    public void testFromString_InvalidName() {
        assertThrows(IllegalArgumentException.class, () -> {
            Side.fromString("nonexistentside");
        });
    }
}
