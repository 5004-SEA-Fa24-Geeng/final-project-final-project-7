package view;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import model.player.*;
import model.simulation.*;
import model.team.*;

class TextUITest {

    private TextUI textUI;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;

    @BeforeEach
    void setUp() {
        // Redirect System.out to capture output
        System.setOut(new PrintStream(outContent));
        // Create new TextUI for each test
        textUI = new TextUI();
    }

    @AfterEach
    void tearDown() {
        // Restore original System.out and System.in
        System.setOut(originalOut);
        System.setIn(originalIn);
        // Close TextUI
        textUI.close();
    }

    @Test
    void testDisplayHelp() {
        // Call the method
        textUI.displayHelp();

        // Verify output contains expected headings and commands
        String output = outContent.toString();
        assertTrue(output.contains("===== MLB SIMULATOR COMMANDS ====="));
        assertTrue(output.contains("PLAYER COMMANDS:"));
        assertTrue(output.contains("COMPUTER COMMANDS:"));
        assertTrue(output.contains("OTHER COMMANDS:"));
        assertTrue(output.contains("player show"));
        assertTrue(output.contains("computer select"));
        assertTrue(output.contains("simulate -n"));
        assertTrue(output.contains("exit"));
    }

    @Test
    void testDisplayListOfStrings() {
        // Create a list of strings to display
        List<String> strings = Arrays.asList("First", "Second", "Third");

        // Call the method
        textUI.displayListOfStrings(strings);

        // Verify output
        String output = outContent.toString();
        assertTrue(output.contains("1. First"));
        assertTrue(output.contains("2. Second"));
        assertTrue(output.contains("3. Third"));
    }

    @Test
    void testDisplayListOfStringsEmpty() {
        // Create an empty list
        List<String> strings = Collections.emptyList();

        // Call the method
        textUI.displayListOfStrings(strings);

        // Verify output (should be empty)
        assertEquals("", outContent.toString());
    }

    @Test
    void testDisplayPlayerInfo() {
        // Create a mock player using Mockito
        Player player = Mockito.mock(Player.class);
        Mockito.when(player.toString()).thenReturn("Test Player Stats");

        // Call the method
        textUI.displayPlayerInfo(player);

        // Verify output
        String output = outContent.toString();
        assertEquals("Test Player Stats" + System.lineSeparator(), output);
    }

    @Test
    void testDisplayBatters() {
        // Create a list with mocked batters
        List<Batter> batters = new ArrayList<>();

        Batter batter1 = Mockito.mock(Batter.class);
        Mockito.when(batter1.getName()).thenReturn("Batter 1");

        Batter batter2 = Mockito.mock(Batter.class);
        Mockito.when(batter2.getName()).thenReturn("Batter 2");

        batters.add(batter1);
        batters.add(batter2);
        batters.add(null); // Add a null batter to test handling

        // Call the method
        textUI.displayBatters(batters);

        // Verify output
        String output = outContent.toString();
        assertTrue(output.contains("1. Batter 1"));
        assertTrue(output.contains("2. Batter 2"));
        assertTrue(output.contains("3. Null"));
    }

    @Test
    void testDisplayBattersEmpty() {
        // Create an empty list
        List<Batter> batters = Collections.emptyList();

        // Call the method
        textUI.displayBatters(batters);

        // Verify output (should be empty)
        assertEquals("", outContent.toString());
    }

    @Test
    void testDisplayPitchers() {
        // Create a list with mocked pitchers
        List<Pitcher> pitchers = new ArrayList<>();

        Pitcher pitcher1 = Mockito.mock(Pitcher.class);
        Mockito.when(pitcher1.getName()).thenReturn("Pitcher 1");

        Pitcher pitcher2 = Mockito.mock(Pitcher.class);
        Mockito.when(pitcher2.getName()).thenReturn("Pitcher 2");

        pitchers.add(pitcher1);
        pitchers.add(pitcher2);
        pitchers.add(null); // Add a null pitcher to test handling

        // Call the method
        textUI.displayPitchers(pitchers);

        // Verify output
        String output = outContent.toString();
        assertTrue(output.contains("1. Pitcher 1"));
        assertTrue(output.contains("2. Pitcher 2"));
        assertTrue(output.contains("3. Null"));
    }

    @Test
    void testDisplayPitchersEmpty() {
        // Create an empty list
        List<Pitcher> pitchers = Collections.emptyList();

        // Call the method
        textUI.displayPitchers(pitchers);

        // Verify output (should be empty)
        assertEquals("", outContent.toString());
    }

    @Test
    void testDisplayAllTeams() {
        // Create a list of team names
        List<String> teams = Arrays.asList("Yankees", "Red Sox", "Dodgers");

        // Call the method
        textUI.displayAllTeams(teams);

        // Verify output
        String output = outContent.toString();
        assertTrue(output.contains("===== MLB TEAMS ====="));
        assertTrue(output.contains("1. Yankees"));
        assertTrue(output.contains("2. Red Sox"));
        assertTrue(output.contains("3. Dodgers"));
    }

    @Test
    void testDisplayAllTeamsEmpty() {
        // Create an empty list
        List<String> teams = Collections.emptyList();

        // Call the method
        textUI.displayAllTeams(teams);

        // Verify output (should show header but no teams)
        String output = outContent.toString();
        assertTrue(output.contains("===== MLB TEAMS ====="));
    }

    @Test
    void testDisplaySimulationResult() {
        // Create a mock simulation result
        SimulationResult result = Mockito.mock(SimulationResult.class);
        Mockito.when(result.toString()).thenReturn("Game Overview");
        Mockito.when(result.getDetails()).thenReturn("Game Details");
        Mockito.when(result.getTotalStatistics()).thenReturn("Game Statistics");

        // Call the method
        textUI.displaySimulationResult(result);

        // Verify output
        String output = outContent.toString();
        assertTrue(output.contains("Game Overview"));
        assertTrue(output.contains("Game Details"));
        assertTrue(output.contains("Game Statistics"));
    }

    @Test
    void testGetCommand() {
        // Set up input stream with a command
        String input = "help\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        // Create a new TextUI with the new input stream
        TextUI localTextUI = new TextUI();

        // Get command
        String result = localTextUI.getCommand();

        // Verify output and result
        String output = outContent.toString();
        assertTrue(output.contains("Enter command:"));
        assertEquals("help", result);

        // Clean up
        localTextUI.close();
    }

    @Test
    void testDisplayMessage() {
        // Call the method
        textUI.displayMessage("Test message");

        // Verify output
        String output = outContent.toString();
        assertEquals("Test message" + System.lineSeparator(), output);
    }

    @Test
    void testDisplayError() {
        // Call the method
        textUI.displayError("Test error");

        // Verify output
        String output = outContent.toString();
        assertEquals("ERROR: Test error" + System.lineSeparator(), output);
    }

    @Test
    void testClose() {
        // This method doesn't produce output, but we can test it doesn't throw exceptions
        textUI.close();

        // Also test double-close doesn't cause issues
        textUI.close();

        // No assertions needed if no exceptions
    }
}