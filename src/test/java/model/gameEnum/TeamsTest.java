package model.gameEnum;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.HashSet;
import java.util.Set;
import gameEnum.Teams;

public class TeamsTest {
    
    @Test
    public void testGetCmdName() {
        // Test all enum values
        assertEquals("diamondbacks", Teams.DIAMONDBACKS.getCmdName());
        assertEquals("athletics", Teams.ATHLETICS.getCmdName());
        assertEquals("braves", Teams.BRAVES.getCmdName());
        assertEquals("orioles", Teams.ORIOLES.getCmdName());
        assertEquals("redsox", Teams.REDSOX.getCmdName());
        assertEquals("cubs", Teams.CUBS.getCmdName());
        assertEquals("whitesox", Teams.WHITESOX.getCmdName());
        assertEquals("reds", Teams.REDS.getCmdName());
        assertEquals("guardians", Teams.GUARDIANS.getCmdName());
        assertEquals("rockies", Teams.ROCKIES.getCmdName());
        assertEquals("tigers", Teams.TIGERS.getCmdName());
        assertEquals("astros", Teams.ASTROS.getCmdName());
        assertEquals("royals", Teams.ROYALS.getCmdName());
        assertEquals("angels", Teams.ANGELS.getCmdName());
        assertEquals("dodgers", Teams.DODGERS.getCmdName());
        assertEquals("marlins", Teams.MARLINS.getCmdName());
        assertEquals("brewers", Teams.BREWERS.getCmdName());
        assertEquals("twins", Teams.TWINS.getCmdName());
        assertEquals("mets", Teams.METS.getCmdName());
        assertEquals("yankees", Teams.YANKEES.getCmdName());
        assertEquals("phillies", Teams.PHILLIES.getCmdName());
        assertEquals("pirates", Teams.PIRATES.getCmdName());
        assertEquals("padres", Teams.PADRES.getCmdName());
        assertEquals("giants", Teams.GIANTS.getCmdName());
        assertEquals("mariners", Teams.MARINERS.getCmdName());
        assertEquals("cardinals", Teams.CARDINALS.getCmdName());
        assertEquals("rays", Teams.RAYS.getCmdName());
        assertEquals("rangers", Teams.RANGERS.getCmdName());
        assertEquals("bluejays", Teams.BLUEJAYS.getCmdName());
        assertEquals("nationals", Teams.NATIONALS.getCmdName());
    }
    
    @Test
    public void testFromCmdName_ValidNames() {
        // Test all command names
        assertEquals(Teams.DIAMONDBACKS, Teams.fromCmdName("diamondbacks"));
        assertEquals(Teams.ATHLETICS, Teams.fromCmdName("athletics"));
        assertEquals(Teams.BRAVES, Teams.fromCmdName("braves"));
        assertEquals(Teams.ORIOLES, Teams.fromCmdName("orioles"));
        assertEquals(Teams.REDSOX, Teams.fromCmdName("redsox"));
        assertEquals(Teams.CUBS, Teams.fromCmdName("cubs"));
        assertEquals(Teams.WHITESOX, Teams.fromCmdName("whitesox"));
        assertEquals(Teams.REDS, Teams.fromCmdName("reds"));
        assertEquals(Teams.GUARDIANS, Teams.fromCmdName("guardians"));
        assertEquals(Teams.ROCKIES, Teams.fromCmdName("rockies"));
        assertEquals(Teams.TIGERS, Teams.fromCmdName("tigers"));
        assertEquals(Teams.ASTROS, Teams.fromCmdName("astros"));
        assertEquals(Teams.ROYALS, Teams.fromCmdName("royals"));
        assertEquals(Teams.ANGELS, Teams.fromCmdName("angels"));
        assertEquals(Teams.DODGERS, Teams.fromCmdName("dodgers"));
        assertEquals(Teams.MARLINS, Teams.fromCmdName("marlins"));
        assertEquals(Teams.BREWERS, Teams.fromCmdName("brewers"));
        assertEquals(Teams.TWINS, Teams.fromCmdName("twins"));
        assertEquals(Teams.METS, Teams.fromCmdName("mets"));
        assertEquals(Teams.YANKEES, Teams.fromCmdName("yankees"));
        assertEquals(Teams.PHILLIES, Teams.fromCmdName("phillies"));
        assertEquals(Teams.PIRATES, Teams.fromCmdName("pirates"));
        assertEquals(Teams.PADRES, Teams.fromCmdName("padres"));
        assertEquals(Teams.GIANTS, Teams.fromCmdName("giants"));
        assertEquals(Teams.MARINERS, Teams.fromCmdName("mariners"));
        assertEquals(Teams.CARDINALS, Teams.fromCmdName("cardinals"));
        assertEquals(Teams.RAYS, Teams.fromCmdName("rays"));
        assertEquals(Teams.RANGERS, Teams.fromCmdName("rangers"));
        assertEquals(Teams.BLUEJAYS, Teams.fromCmdName("bluejays"));
        assertEquals(Teams.NATIONALS, Teams.fromCmdName("nationals"));
    }
    
    @Test
    public void testFromCmdName_InvalidName() {
        assertThrows(IllegalArgumentException.class, () -> {
            Teams.fromCmdName("nonexistentteam");
        });
    }
    
    @Test
    public void testFromString_UsingEnumName() {
        // Test all enum names
        assertEquals(Teams.DIAMONDBACKS, Teams.fromString("DIAMONDBACKS"));
        assertEquals(Teams.ATHLETICS, Teams.fromString("ATHLETICS"));
        assertEquals(Teams.BRAVES, Teams.fromString("BRAVES"));
        assertEquals(Teams.ORIOLES, Teams.fromString("ORIOLES"));
        assertEquals(Teams.REDSOX, Teams.fromString("REDSOX"));
        assertEquals(Teams.CUBS, Teams.fromString("CUBS"));
        assertEquals(Teams.WHITESOX, Teams.fromString("WHITESOX"));
        assertEquals(Teams.REDS, Teams.fromString("REDS"));
        assertEquals(Teams.GUARDIANS, Teams.fromString("GUARDIANS"));
        assertEquals(Teams.ROCKIES, Teams.fromString("ROCKIES"));
        assertEquals(Teams.TIGERS, Teams.fromString("TIGERS"));
        assertEquals(Teams.ASTROS, Teams.fromString("ASTROS"));
        assertEquals(Teams.ROYALS, Teams.fromString("ROYALS"));
        assertEquals(Teams.ANGELS, Teams.fromString("ANGELS"));
        assertEquals(Teams.DODGERS, Teams.fromString("DODGERS"));
        assertEquals(Teams.MARLINS, Teams.fromString("MARLINS"));
        assertEquals(Teams.BREWERS, Teams.fromString("BREWERS"));
        assertEquals(Teams.TWINS, Teams.fromString("TWINS"));
        assertEquals(Teams.METS, Teams.fromString("METS"));
        assertEquals(Teams.YANKEES, Teams.fromString("YANKEES"));
        assertEquals(Teams.PHILLIES, Teams.fromString("PHILLIES"));
        assertEquals(Teams.PIRATES, Teams.fromString("PIRATES"));
        assertEquals(Teams.PADRES, Teams.fromString("PADRES"));
        assertEquals(Teams.GIANTS, Teams.fromString("GIANTS"));
        assertEquals(Teams.MARINERS, Teams.fromString("MARINERS"));
        assertEquals(Teams.CARDINALS, Teams.fromString("CARDINALS"));
        assertEquals(Teams.RAYS, Teams.fromString("RAYS"));
        assertEquals(Teams.RANGERS, Teams.fromString("RANGERS"));
        assertEquals(Teams.BLUEJAYS, Teams.fromString("BLUEJAYS"));
        assertEquals(Teams.NATIONALS, Teams.fromString("NATIONALS"));
    }
    
    @Test
    public void testFromString_UsingCmdName() {
        // Test all command names (testing a subset for brevity)
        assertEquals(Teams.DIAMONDBACKS, Teams.fromString("diamondbacks"));
        assertEquals(Teams.ATHLETICS, Teams.fromString("athletics"));
        assertEquals(Teams.BRAVES, Teams.fromString("braves"));
        assertEquals(Teams.YANKEES, Teams.fromString("yankees"));
        assertEquals(Teams.NATIONALS, Teams.fromString("nationals"));
    }
    
    @Test
    public void testFromString_CaseInsensitive() {
        assertEquals(Teams.DIAMONDBACKS, Teams.fromString("DiamondBacks"));
        assertEquals(Teams.REDSOX, Teams.fromString("RedSox"));
        assertEquals(Teams.BLUEJAYS, Teams.fromString("BlueJays"));
    }
    
    @Test
    public void testFromString_InvalidName() {
        assertThrows(IllegalArgumentException.class, () -> {
            Teams.fromString("nonexistentteam");
        });
    }
    
    @Test
    public void testRandomTeam() {
        // Run the randomTeam method multiple times and verify:
        // 1. It always returns a non-null value
        // 2. It never returns MARINERS (as per the method implementation)
        
        Set<Teams> teamsGenerated = new HashSet<>();
        for (int i = 0; i < 100; i++) {
            Teams randomTeam = Teams.randomTeam();
            assertNotNull(randomTeam);
            assertNotEquals(Teams.MARINERS, randomTeam);
            teamsGenerated.add(randomTeam);
        }
        
        // Verify we get a decent distribution (not just one team)
        // We should get at least 10 different teams after 100 attempts
        assertTrue(teamsGenerated.size() > 10, "Random team distribution too narrow");
    }
}
