package controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import model.team.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import gameEnum.PlayerData;
import gameEnum.Side;
import gameEnum.Teams;
import model.Model;
import model.player.Batter;
import model.player.Pitcher;
import model.simulation.SimulationResult;
import view.TextUI;

class MLBSimulatorControllerTest {

    @Mock
    private TextUI mockView;

    @Mock
    private Model mockModel;

    @Mock
    private SimulationResult mockSimulationResult;

    @Mock
    private Batter mockBatter;

    @Mock
    private Pitcher mockPitcher;

    @Mock
    private Team mockTeam;

    @InjectMocks
    private MLBSimulatorController controller;

    // For accessing private methods
    private MLBSimulatorController controllerForPrivateMethods;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        // Setup for common method returns
        Set<Batter> batterSet = new HashSet<>();
        batterSet.add(mockBatter);
        when(mockModel.getPlayerTeamBatterLoaderLineup()).thenReturn(batterSet);
        when(mockBatter.getName()).thenReturn("Test Batter");

        Set<Pitcher> pitcherSet = new HashSet<>();
        pitcherSet.add(mockPitcher);
        when(mockModel.getComTeamPitcherLoaderLineup()).thenReturn(pitcherSet);
        when(mockPitcher.getName()).thenReturn("Test Pitcher");

        List<Batter> batterList = new ArrayList<>();
        batterList.add(mockBatter);

        List<Pitcher> pitcherList = new ArrayList<>();
        pitcherList.add(mockPitcher);

        // Create a controller with mocked model for testing private methods
        controllerForPrivateMethods = new MLBSimulatorController();

        // Use reflection to make private methods accessible and inject mocks
        java.lang.reflect.Field modelField = MLBSimulatorController.class.getDeclaredField("model");
        modelField.setAccessible(true);
        modelField.set(controllerForPrivateMethods, mockModel);

        java.lang.reflect.Field viewField = MLBSimulatorController.class.getDeclaredField("view");
        viewField.setAccessible(true);
        viewField.set(controllerForPrivateMethods, mockView);

        java.lang.reflect.Field filteredBattersField = MLBSimulatorController.class.getDeclaredField("filteredBatters");
        filteredBattersField.setAccessible(true);
        filteredBattersField.set(controllerForPrivateMethods, batterList);

        java.lang.reflect.Field filteredPitchersField = MLBSimulatorController.class.getDeclaredField("filteredPitchers");
        filteredPitchersField.setAccessible(true);
        filteredPitchersField.set(controllerForPrivateMethods, pitcherList);

        // Setup Teams enum mocking
        when(mockModel.getComTeam()).thenReturn(mockTeam);
    }

    @Test
    void testProcessCommandHelp() throws Exception {
        // Setup
        java.lang.reflect.Method processCommand = MLBSimulatorController.class.getDeclaredMethod("processCommand", String.class);
        processCommand.setAccessible(true);

        // Execute
        processCommand.invoke(controllerForPrivateMethods, "help");

        // Verify
        verify(mockView).displayHelp();
    }

    @Test
    void testProcessCommandPlayer() throws Exception {
        // Setup
        java.lang.reflect.Method processCommand = MLBSimulatorController.class.getDeclaredMethod("processCommand", String.class);
        processCommand.setAccessible(true);

        // Execute player command
        processCommand.invoke(controllerForPrivateMethods, "player show lineup");

        // Verify
        verify(mockModel).getPlayerTeamBatterLineup();
    }

    @Test
    void testProcessCommandComputer() throws Exception {
        // Setup
        java.lang.reflect.Method processCommand = MLBSimulatorController.class.getDeclaredMethod("processCommand", String.class);
        processCommand.setAccessible(true);

        List<String> teamNames = new ArrayList<>();
        teamNames.add("Mariners");
        when(mockModel.getAllTeamName()).thenReturn(teamNames);

        // Execute computer command
        processCommand.invoke(controllerForPrivateMethods, "computer show teams");

        // Verify
        verify(mockModel).getAllTeamName();
    }

    @Test
    void testProcessCommandSimulate() throws Exception {
        // Setup
        java.lang.reflect.Method processCommand = MLBSimulatorController.class.getDeclaredMethod("processCommand", String.class);
        processCommand.setAccessible(true);

        when(mockModel.startSimAndGetResult()).thenReturn(mockSimulationResult);

        // Execute simulate command
        processCommand.invoke(controllerForPrivateMethods, "simulate");

        // Verify
        verify(mockModel).startSimAndGetResult();
    }

    @Test
    void testProcessCommandExit() throws Exception {
        // Setup
        java.lang.reflect.Method processCommand = MLBSimulatorController.class.getDeclaredMethod("processCommand", String.class);
        processCommand.setAccessible(true);

        // Execute
        processCommand.invoke(controllerForPrivateMethods, "exit");

        // Verify
        verify(mockView).displayMessage("Exiting MLB Simulator. Goodbye!");

        // Verify running is set to false
        java.lang.reflect.Field runningField = MLBSimulatorController.class.getDeclaredField("running");
        runningField.setAccessible(true);
        boolean running = (boolean) runningField.get(controllerForPrivateMethods);
        assertFalse(running);
    }

    @Test
    void testProcessCommandEmpty() throws Exception {
        // Setup
        java.lang.reflect.Method processCommand = MLBSimulatorController.class.getDeclaredMethod("processCommand", String.class);
        processCommand.setAccessible(true);

        // Execute with empty string
        processCommand.invoke(controllerForPrivateMethods, "");

        // Verify no interactions with view or model
        verifyNoMoreInteractions(mockView);
    }

    @Test
    void testProcessCommandInvalid() throws Exception {
        // Setup
        java.lang.reflect.Method processCommand = MLBSimulatorController.class.getDeclaredMethod("processCommand", String.class);
        processCommand.setAccessible(true);

        // Execute
        processCommand.invoke(controllerForPrivateMethods, "invalid");

        // Verify
        verify(mockView).displayError("Unknown command. Type 'help' for available commands.");
    }

    @Test
    void testProcessCommandIllegalCharacter() throws Exception {
        // Setup
        java.lang.reflect.Method processCommand = MLBSimulatorController.class.getDeclaredMethod("processCommand", String.class);
        processCommand.setAccessible(true);

        // Execute command with illegal character
        processCommand.invoke(controllerForPrivateMethods, "player@ show lineup");

        // Verify error message is displayed
        verify(mockView).displayMessage(contains("Illegal character in command"));
    }

    @Test
    void testContainsIllegalCharacters() throws Exception {
        // Setup
        java.lang.reflect.Method containsIllegalCharacters = MLBSimulatorController.class.getDeclaredMethod("containsIllegalCharacters", String.class);
        containsIllegalCharacters.setAccessible(true);

        // Test valid characters
        assertFalse((boolean) containsIllegalCharacters.invoke(controllerForPrivateMethods, "valid"));
        assertFalse((boolean) containsIllegalCharacters.invoke(controllerForPrivateMethods, "valid123"));
        assertFalse((boolean) containsIllegalCharacters.invoke(controllerForPrivateMethods, "valid-with-dash"));
        assertFalse((boolean) containsIllegalCharacters.invoke(controllerForPrivateMethods, "operators<>=!~"));
        assertFalse((boolean) containsIllegalCharacters.invoke(controllerForPrivateMethods, "dot.and,comma"));

        // Test invalid characters
        assertTrue((boolean) containsIllegalCharacters.invoke(controllerForPrivateMethods, "invalid@"));
        assertTrue((boolean) containsIllegalCharacters.invoke(controllerForPrivateMethods, "invalid#"));
        assertTrue((boolean) containsIllegalCharacters.invoke(controllerForPrivateMethods, "invalid$"));
        assertTrue((boolean) containsIllegalCharacters.invoke(controllerForPrivateMethods, "invalid%"));
    }

    @Test
    void testParseSimulateOptions() throws Exception {
        // Setup
        java.lang.reflect.Method parseSimulateOptions = MLBSimulatorController.class.getDeclaredMethod("parseSimulateOptions", String[].class);
        parseSimulateOptions.setAccessible(true);

        // Test with no options
        String[] command1 = new String[]{"simulate"};
        Map<String, String> result1 = (Map<String, String>) parseSimulateOptions.invoke(controllerForPrivateMethods, (Object) command1);

        assertEquals("1", result1.get("number"));
        assertNull(result1.get("outfile"));

        // Test with -n option
        String[] command2 = new String[]{"simulate", "-n", "5"};
        Map<String, String> result2 = (Map<String, String>) parseSimulateOptions.invoke(controllerForPrivateMethods, (Object) command2);

        assertEquals("5", result2.get("number"));
        assertNull(result2.get("outfile"));

        // Test with -o option
        String[] command3 = new String[]{"simulate", "-o", "results.txt"};
        Map<String, String> result3 = (Map<String, String>) parseSimulateOptions.invoke(controllerForPrivateMethods, (Object) command3);

        assertEquals("1", result3.get("number"));
        assertEquals("results.txt", result3.get("outfile"));

        // Test with both options
        String[] command4 = new String[]{"simulate", "-n", "10", "-o", "sim_results.txt"};
        Map<String, String> result4 = (Map<String, String>) parseSimulateOptions.invoke(controllerForPrivateMethods, (Object) command4);

        assertEquals("10", result4.get("number"));
        assertEquals("sim_results.txt", result4.get("outfile"));

        // Test with incomplete options
        String[] command5 = new String[]{"simulate", "-n"};
        Map<String, String> result5 = (Map<String, String>) parseSimulateOptions.invoke(controllerForPrivateMethods, (Object) command5);

        assertEquals("1", result5.get("number")); // Should retain default

        String[] command6 = new String[]{"simulate", "-o"};
        Map<String, String> result6 = (Map<String, String>) parseSimulateOptions.invoke(controllerForPrivateMethods, (Object) command6);

        assertNull(result6.get("outfile")); // Should retain default
    }

    @Test
    void testFormatOutputFilename() throws Exception {
        // Setup
        java.lang.reflect.Method formatOutputFilename = MLBSimulatorController.class.getDeclaredMethod("formatOutputFilename", String.class, int.class);
        formatOutputFilename.setAccessible(true);

        // Test with no extension
        String result1 = (String) formatOutputFilename.invoke(controllerForPrivateMethods, "results", 1);
        assertEquals("results_1.txt", result1);

        // Test with .txt extension
        String result2 = (String) formatOutputFilename.invoke(controllerForPrivateMethods, "results.txt", 2);
        assertEquals("results_2.txt", result2);

        // Test with .csv extension
        String result3 = (String) formatOutputFilename.invoke(controllerForPrivateMethods, "results.csv", 3);
        assertEquals("results_3.txt", result3);

        // Test with multiple dots
        String result4 = (String) formatOutputFilename.invoke(controllerForPrivateMethods, "results.with.dots.txt", 4);
        assertEquals("results.with.dots_4.txt", result4);
    }

    @Test
    void testRunSimulation() throws Exception {
        // Setup
        java.lang.reflect.Method runSimulation = MLBSimulatorController.class.getDeclaredMethod("runSimulation", String[].class);
        runSimulation.setAccessible(true);

        when(mockModel.startSimAndGetResult()).thenReturn(mockSimulationResult);

        // Test with single simulation
        String[] command1 = new String[]{"simulate"};
        runSimulation.invoke(controllerForPrivateMethods, (Object) command1);

        verify(mockModel).startSimAndGetResult();
        verify(mockView).displaySimulationResult(mockSimulationResult);

        // Test with multiple simulations
        reset(mockModel, mockView);
        when(mockModel.startSimAndGetResult()).thenReturn(mockSimulationResult);

        String[] command2 = new String[]{"simulate", "-n", "3"};
        runSimulation.invoke(controllerForPrivateMethods, (Object) command2);

        verify(mockModel, times(3)).startSimAndGetResult();
        verify(mockView, times(3)).displaySimulationResult(mockSimulationResult);

        // Test with simulation that fails
        reset(mockModel, mockView);
        when(mockModel.startSimAndGetResult()).thenReturn(null);

        String[] command3 = new String[]{"simulate"};
        runSimulation.invoke(controllerForPrivateMethods, (Object) command3);

        verify(mockView).displayError(anyString());
    }

    @Test
    void testRunSimulationWithInvalidNumber() throws Exception {
        // Setup
        java.lang.reflect.Method runSimulation = MLBSimulatorController.class.getDeclaredMethod("runSimulation", String[].class);
        runSimulation.setAccessible(true);

        // Test with invalid number
        String[] command = new String[]{"simulate", "-n", "notanumber"};
        runSimulation.invoke(controllerForPrivateMethods, (Object) command);

        // Verify error message displayed
        verify(mockView).displayError(contains("Invalid number of simulations"));
    }

    @Test
    void testRunSimulationWithOutfile() throws Exception {
        // Setup
        java.lang.reflect.Method runSimulation = MLBSimulatorController.class.getDeclaredMethod("runSimulation", String[].class);
        runSimulation.setAccessible(true);

        when(mockModel.startSimAndGetResult()).thenReturn(mockSimulationResult);

        // Test with outfile
        String[] command = new String[]{"simulate", "-o", "results.txt"};
        runSimulation.invoke(controllerForPrivateMethods, (Object) command);

        // Verify simulation runs and results displayed
        verify(mockModel).startSimAndGetResult();
        verify(mockView).displaySimulationResult(mockSimulationResult);
        verify(mockModel).saveGameDetailsAsTXTFile(anyString());
    }

    @Test
    void testRunSimulationWithMultipleSimulationsAndOutfile() throws Exception {
        // Setup
        java.lang.reflect.Method runSimulation = MLBSimulatorController.class.getDeclaredMethod("runSimulation", String[].class);
        runSimulation.setAccessible(true);

        when(mockModel.startSimAndGetResult()).thenReturn(mockSimulationResult);

        // Test with multiple simulations and outfile
        String[] command = new String[]{"simulate", "-n", "3", "-o", "results.txt"};
        runSimulation.invoke(controllerForPrivateMethods, (Object) command);

        // Verify simulation runs 3 times and results displayed
        verify(mockModel, times(3)).startSimAndGetResult();
        verify(mockView, times(3)).displaySimulationResult(mockSimulationResult);
        verify(mockModel, times(3)).saveGameDetailsAsTXTFile(anyString());
    }

    @Test
    void testRunSimulationWithNullResultsAndOutfile() throws Exception {
        // Setup
        java.lang.reflect.Method runSimulation = MLBSimulatorController.class.getDeclaredMethod("runSimulation", String[].class);
        runSimulation.setAccessible(true);

        when(mockModel.startSimAndGetResult()).thenReturn(null);

        // Test with outfile but null simulation result
        String[] command = new String[]{"simulate", "-o", "results.txt"};
        runSimulation.invoke(controllerForPrivateMethods, (Object) command);

        // Verify error message displayed
        verify(mockView).displayError(anyString());
        // Verify saveGameDetailsAsTXTFile is not called
        verify(mockModel, never()).saveGameDetailsAsTXTFile(anyString());
    }

    @Test
    void testHandlePlayerCommand() throws Exception {
        // Setup
        java.lang.reflect.Method handlePlayerCommand = MLBSimulatorController.class.getDeclaredMethod("handlePlayerCommand", String[].class);
        handlePlayerCommand.setAccessible(true);

        // Test with show command
        String[] command1 = new String[]{"player", "show", "lineup"};
        handlePlayerCommand.invoke(controllerForPrivateMethods, (Object) command1);

        verify(mockModel).getPlayerTeamBatterLineup();

        // Test with add command
        reset(mockModel, mockView);
        when(mockModel.getPlayerTeamBatterLoaderLineup()).thenReturn(Set.of(mockBatter));
        when(mockBatter.getName()).thenReturn("Test Batter");

        String[] command2 = new String[]{"player", "add", "1", "to", "1"};
        handlePlayerCommand.invoke(controllerForPrivateMethods, (Object) command2);

        verify(mockModel).addBatterToLineup(eq(Side.PLAYER), anyString(), any());

        // Test with remove command
        reset(mockModel, mockView);

        String[] command3 = new String[]{"player", "remove", "Test Batter"};
        handlePlayerCommand.invoke(controllerForPrivateMethods, (Object) command3);

        verify(mockModel).removeFromLineup(Side.PLAYER, "batter", "Test Batter");

        // Test with clear command
        reset(mockModel, mockView);

        String[] command4 = new String[]{"player", "clear"};
        handlePlayerCommand.invoke(controllerForPrivateMethods, (Object) command4);

        verify(mockModel).clearLineup(Side.PLAYER, "batter");
        verify(mockView).displayMessage("Batter lineup cleared.");
    }

    @Test
    void testHandlePlayerCommandTooShort() throws Exception {
        // Setup
        java.lang.reflect.Method handlePlayerCommand = MLBSimulatorController.class.getDeclaredMethod("handlePlayerCommand", String[].class);
        handlePlayerCommand.setAccessible(true);

        // Test with command that's too short
        String[] command = new String[]{"player"};
        handlePlayerCommand.invoke(controllerForPrivateMethods, (Object) command);

        // Verify error message displayed
        verify(mockView).displayError("Invalid player command. Type 'help' for available commands.");
    }

    @Test
    void testHandlePlayerCommandWithInvalidSubcommand() throws Exception {
        // Setup
        java.lang.reflect.Method handlePlayerCommand = MLBSimulatorController.class.getDeclaredMethod("handlePlayerCommand", String[].class);
        handlePlayerCommand.setAccessible(true);

        // Test with invalid subcommand
        String[] command = new String[]{"player", "invalid", "argument"};
        handlePlayerCommand.invoke(controllerForPrivateMethods, (Object) command);

        // Verify error message displayed
        verify(mockView).displayError("Invalid player command. Type 'help' for available commands.");
    }

    @Test
    void testHandlePlayerCommandAddTooShort() throws Exception {
        // Setup
        java.lang.reflect.Method handlePlayerCommand = MLBSimulatorController.class.getDeclaredMethod("handlePlayerCommand", String[].class);
        handlePlayerCommand.setAccessible(true);

        // Test with add command that's too short
        String[] command = new String[]{"player", "add"};
        handlePlayerCommand.invoke(controllerForPrivateMethods, (Object) command);

        // Verify error message displayed
        verify(mockView).displayError("Invalid command. Use 'player add [name/number] to [number]'.");
    }

    @Test
    void testHandlePlayerCommandRemoveTooShort() throws Exception {
        // Setup
        java.lang.reflect.Method handlePlayerCommand = MLBSimulatorController.class.getDeclaredMethod("handlePlayerCommand", String[].class);
        handlePlayerCommand.setAccessible(true);

        // Test with remove command that's too short
        String[] command = new String[]{"player", "remove"};
        handlePlayerCommand.invoke(controllerForPrivateMethods, (Object) command);

        // Verify error message displayed
        verify(mockView).displayError("Invalid command. Use 'player remove [name]'.");
    }

    @Test
    void testHandlePlayerShowCommand() throws Exception {
        // Setup
        java.lang.reflect.Method handlePlayerShowCommand = MLBSimulatorController.class.getDeclaredMethod("handlePlayerShowCommand", String[].class);
        handlePlayerShowCommand.setAccessible(true);

        // Test with lineup option
        String[] command1 = new String[]{"player", "show", "lineup"};
        handlePlayerShowCommand.invoke(controllerForPrivateMethods, (Object) command1);

        verify(mockModel).getPlayerTeamBatterLineup();

        // Test with attributes option
        reset(mockModel, mockView);
        List<String> attributeList = List.of("AVG", "HR", "RBI");
        when(mockModel.getAllColumnName()).thenReturn(attributeList);

        String[] command2 = new String[]{"player", "show", "attributes"};
        handlePlayerShowCommand.invoke(controllerForPrivateMethods, (Object) command2);

        verify(mockModel).getAllColumnName();
        verify(mockView).displayListOfStrings(attributeList);

        // Test with all option
        reset(mockModel, mockView);
        Set<Batter> batterSet = new HashSet<>();
        batterSet.add(mockBatter);
        when(mockModel.getPlayerTeamBatterLoaderLineup()).thenReturn(batterSet);

        String[] command3 = new String[]{"player", "show", "all"};
        handlePlayerShowCommand.invoke(controllerForPrivateMethods, (Object) command3);

        verify(mockModel).getPlayerTeamBatterLoaderLineup();

        // Test with batter name
        reset(mockModel, mockView);
        when(mockModel.getBatter(Side.PLAYER, "Test Batter")).thenReturn(mockBatter);

        String[] command4 = new String[]{"player", "show", "Test", "Batter"};
        handlePlayerShowCommand.invoke(controllerForPrivateMethods, (Object) command4);

        verify(mockModel).getBatter(Side.PLAYER, "Test Batter");
        verify(mockView).displayPlayerInfo(mockBatter);
    }

    @Test
    void testHandlePlayerShowCommandTooShort() throws Exception {
        // Setup
        java.lang.reflect.Method handlePlayerShowCommand = MLBSimulatorController.class.getDeclaredMethod("handlePlayerShowCommand", String[].class);
        handlePlayerShowCommand.setAccessible(true);

        // Test with command that's too short
        String[] command = new String[]{"player", "show"};
        handlePlayerShowCommand.invoke(controllerForPrivateMethods, (Object) command);

        // Verify error message displayed
        verify(mockView).displayError("Invalid player show command. Type 'help' for available commands.");
    }

    @Test
    void testHandlePlayerShowCommandBatterNotFound() throws Exception {
        // Setup
        java.lang.reflect.Method handlePlayerShowCommand = MLBSimulatorController.class.getDeclaredMethod("handlePlayerShowCommand", String[].class);
        handlePlayerShowCommand.setAccessible(true);

        // Return null for batter
        when(mockModel.getBatter(eq(Side.PLAYER), anyString())).thenReturn(null);

        // Test with batter name that doesn't exist
        String[] command = new String[]{"player", "show", "NonExistent", "Batter"};
        handlePlayerShowCommand.invoke(controllerForPrivateMethods, (Object) command);

        // Verify error message displayed
        verify(mockView).displayError(contains("Batter not found"));
    }

    @Test
    void testExtractCommand() throws Exception {
        // Setup
        java.lang.reflect.Method extractCommand = MLBSimulatorController.class.getDeclaredMethod("extractCommand", String[].class);
        extractCommand.setAccessible(true);

        // Test with simple command
        String[] command1 = new String[]{"player", "add", "Test"};
        String result1 = (String) extractCommand.invoke(controllerForPrivateMethods, (Object) command1);

        assertEquals("Test", result1);

        // Test with command containing spaces
        String[] command2 = new String[]{"player", "add", "Test", "Batter"};
        String result2 = (String) extractCommand.invoke(controllerForPrivateMethods, (Object) command2);

        assertEquals("Test Batter", result2);

        // Test with command that has no arguments after index 2
        String[] command3 = new String[]{"player", "show"};
        String result3 = (String) extractCommand.invoke(controllerForPrivateMethods, (Object) command3);

        assertEquals("", result3);

        // Test with command that has multiple words
        String[] command4 = new String[]{"player", "show", "Mike", "Trout", "Jr."};
        String result4 = (String) extractCommand.invoke(controllerForPrivateMethods, (Object) command4);

        assertEquals("Mike Trout Jr.", result4);
    }

    @Test
    void testValidateAddToCommand() throws Exception {
        // Setup
        java.lang.reflect.Method validateAddToCommand = MLBSimulatorController.class.getDeclaredMethod("validateAddToCommand", String.class, boolean.class);
        validateAddToCommand.setAccessible(true);

        // Test valid index to position pattern
        AddCommandInfo result1 = (AddCommandInfo) validateAddToCommand.invoke(controllerForPrivateMethods, "1 to 1", true);

        assertTrue(result1.isValid);
        assertTrue(result1.isIndex);
        assertEquals(0, result1.playerIndex); // 0-based index
        assertEquals(1, result1.position);

        // Test valid name to position pattern
        AddCommandInfo result2 = (AddCommandInfo) validateAddToCommand.invoke(controllerForPrivateMethods, "Test Batter to 2", true);

        assertTrue(result2.isValid);
        assertFalse(result2.isIndex);
        assertEquals("test batter", result2.playerName); // Converted to lowercase
        assertEquals(2, result2.position);

        // Test invalid - no 'to' pattern
        AddCommandInfo result3 = (AddCommandInfo) validateAddToCommand.invoke(controllerForPrivateMethods, "Test Batter", true);

        assertFalse(result3.isValid);

        // Test invalid position
        AddCommandInfo result4 = (AddCommandInfo) validateAddToCommand.invoke(controllerForPrivateMethods, "1 to abc", true);

        assertFalse(result4.isValid);

        // Test position out of range
        AddCommandInfo result5 = (AddCommandInfo) validateAddToCommand.invoke(controllerForPrivateMethods, "1 to 10", true);

        assertFalse(result5.isValid);

        // Test player index out of range
        AddCommandInfo result6 = (AddCommandInfo) validateAddToCommand.invoke(controllerForPrivateMethods, "100 to 1", true);

        assertFalse(result6.isValid);
    }

    @Test
    void testGetBatterFromCommand() throws Exception {
        // Setup
        java.lang.reflect.Method getBatterFromCommand = MLBSimulatorController.class.getDeclaredMethod("getBatterFromCommand", AddCommandInfo.class);
        getBatterFromCommand.setAccessible(true);

        // Test get by index
        AddCommandInfo cmdInfo1 = new AddCommandInfo();
        cmdInfo1.isIndex = true;
        cmdInfo1.playerIndex = 0;

        Batter result1 = (Batter) getBatterFromCommand.invoke(controllerForPrivateMethods, cmdInfo1);

        assertEquals(mockBatter, result1);

        // Test get by exact name
        AddCommandInfo cmdInfo2 = new AddCommandInfo();
        cmdInfo2.isIndex = false;
        cmdInfo2.playerName = "Test Batter";

        Batter result2 = (Batter) getBatterFromCommand.invoke(controllerForPrivateMethods, cmdInfo2);

        assertEquals(mockBatter, result2);

        // Test get by partial name
        AddCommandInfo cmdInfo3 = new AddCommandInfo();
        cmdInfo3.isIndex = false;
        cmdInfo3.playerName = "Batter";

        Batter result3 = (Batter) getBatterFromCommand.invoke(controllerForPrivateMethods, cmdInfo3);

        assertEquals(mockBatter, result3);

        // Test player not found
        AddCommandInfo cmdInfo4 = new AddCommandInfo();
        cmdInfo4.isIndex = false;
        cmdInfo4.playerName = "Nonexistent";

        Batter result4 = (Batter) getBatterFromCommand.invoke(controllerForPrivateMethods, cmdInfo4);

        assertNull(result4);
        verify(mockView).displayError(contains("Batter not found"));
    }

    @Test
    void testGetPitcherFromCommand() throws Exception {
        // Setup
        java.lang.reflect.Method getPitcherFromCommand = MLBSimulatorController.class.getDeclaredMethod("getPitcherFromCommand", AddCommandInfo.class);
        getPitcherFromCommand.setAccessible(true);

        // Test get by index
        AddCommandInfo cmdInfo1 = new AddCommandInfo();
        cmdInfo1.isIndex = true;
        cmdInfo1.playerIndex = 0;

        Pitcher result1 = (Pitcher) getPitcherFromCommand.invoke(controllerForPrivateMethods, cmdInfo1);

        assertEquals(mockPitcher, result1);

        // Test get by exact name
        AddCommandInfo cmdInfo2 = new AddCommandInfo();
        cmdInfo2.isIndex = false;
        cmdInfo2.playerName = "Test Pitcher";

        Pitcher result2 = (Pitcher) getPitcherFromCommand.invoke(controllerForPrivateMethods, cmdInfo2);

        assertEquals(mockPitcher, result2);

        // Test get by partial name
        AddCommandInfo cmdInfo3 = new AddCommandInfo();
        cmdInfo3.isIndex = false;
        cmdInfo3.playerName = "Pitcher";

        Pitcher result3 = (Pitcher) getPitcherFromCommand.invoke(controllerForPrivateMethods, cmdInfo3);

        assertEquals(mockPitcher, result3);

        // Test player not found
        AddCommandInfo cmdInfo4 = new AddCommandInfo();
        cmdInfo4.isIndex = false;
        cmdInfo4.playerName = "Nonexistent";

        Pitcher result4 = (Pitcher) getPitcherFromCommand.invoke(controllerForPrivateMethods, cmdInfo4);

        assertNull(result4);
        verify(mockView).displayError(contains("Pitcher not found"));
    }

    @Test
    void testAddPlayer() throws Exception {
        // Setup
        java.lang.reflect.Method addPlayer = MLBSimulatorController.class.getDeclaredMethod("addPlayer", AddCommandInfo.class, boolean.class, String.class);
        addPlayer.setAccessible(true);

        // Test add batter - valid index
        AddCommandInfo cmdInfo1 = new AddCommandInfo();
        cmdInfo1.isValid = true;
        cmdInfo1.isIndex = true;
        cmdInfo1.playerIndex = 0;
        cmdInfo1.position = 1;

        addPlayer.invoke(controllerForPrivateMethods, cmdInfo1, true, "1 to 1");

        verify(mockModel).addBatterToLineup(eq(Side.PLAYER), eq("1 to 1"), any());

        // Test add pitcher - valid index
        reset(mockModel, mockView);

        AddCommandInfo cmdInfo2 = new AddCommandInfo();
        cmdInfo2.isValid = true;
        cmdInfo2.isIndex = true;
        cmdInfo2.playerIndex = 0;
        cmdInfo2.position = 1;

        addPlayer.invoke(controllerForPrivateMethods, cmdInfo2, false, "1 to 1");

        verify(mockModel).addPitcherToLineup(eq(Side.COMPUTER), eq("1 to 1"), any());

        // Test add player - invalid command
        reset(mockModel, mockView);

        AddCommandInfo cmdInfo3 = new AddCommandInfo();
        cmdInfo3.isValid = false;

        addPlayer.invoke(controllerForPrivateMethods, cmdInfo3, true, "invalid");

        verify(mockModel, never()).addBatterToLineup(any(), any(), any());
        verify(mockModel, never()).addPitcherToLineup(any(), any(), any());

        // Test add player with exception
        reset(mockModel, mockView);

        AddCommandInfo cmdInfo4 = new AddCommandInfo();
        cmdInfo4.isValid = true;
        cmdInfo4.isIndex = true;
        cmdInfo4.playerIndex = 0;
        cmdInfo4.position = 1;

        doThrow(new IllegalArgumentException("Error adding player")).when(mockModel).addBatterToLineup(any(), any(), any());

        addPlayer.invoke(controllerForPrivateMethods, cmdInfo4, true, "1 to 1");

        verify(mockView).displayError("Error adding player");
    }

    @Test
    void testHandleComputerCommand() throws Exception {
        // Setup
        java.lang.reflect.Method handleComputerCommand = MLBSimulatorController.class.getDeclaredMethod("handleComputerCommand", String[].class);
        handleComputerCommand.setAccessible(true);

        // Test with show command
        List<String> teamNames = new ArrayList<>();
        teamNames.add("Mariners");
        when(mockModel.getAllTeamName()).thenReturn(teamNames);

        String[] command1 = new String[]{"computer", "show", "teams"};
        handleComputerCommand.invoke(controllerForPrivateMethods, (Object) command1);

        verify(mockModel).getAllTeamName();

        // Test with select command
        reset(mockModel, mockView);
        Set<Pitcher> pitcherSet = new HashSet<>();
        pitcherSet.add(mockPitcher);
        when(mockModel.getComTeamPitcherLoaderLineup()).thenReturn(pitcherSet);

        String[] command2 = new String[]{"computer", "select", "mariners"};
        handleComputerCommand.invoke(controllerForPrivateMethods, (Object) command2);

        verify(mockModel).setComTeam(any(Teams.class));

        // Test with add command
        reset(mockModel, mockView);
        when(mockModel.getComTeam()).thenReturn(mockTeam);

        String[] command3 = new String[]{"computer", "add", "1", "to", "1"};
        handleComputerCommand.invoke(controllerForPrivateMethods, (Object) command3);

        verify(mockModel).addPitcherToLineup(eq(Side.COMPUTER), anyString(), any());

        // Test with remove command
        reset(mockModel, mockView);

        String[] command4 = new String[]{"computer", "remove", "Test Pitcher"};
        handleComputerCommand.invoke(controllerForPrivateMethods, (Object) command4);

        verify(mockModel).removeFromLineup(Side.COMPUTER, "pitcher", "Test Pitcher");
    }

    @Test
    void testHandleComputerShowCommand() throws Exception {
        // Setup
        java.lang.reflect.Method handleComputerShowCommand = MLBSimulatorController.class.getDeclaredMethod("handleComputerShowCommand", String[].class);
        handleComputerShowCommand.setAccessible(true);

        // Test with teams option
        List<String> teamNames = new ArrayList<>();
        teamNames.add("Mariners");
        when(mockModel.getAllTeamName()).thenReturn(teamNames);

        String[] command1 = new String[]{"computer", "show", "teams"};
        handleComputerShowCommand.invoke(controllerForPrivateMethods, (Object) command1);

        verify(mockModel).getAllTeamName();

        // Test with attributes option
        reset(mockModel, mockView);
        List<String> attributeList = List.of("ERA", "SO", "WHIP");
        when(mockModel.getAllColumnName()).thenReturn(attributeList);

        String[] command2 = new String[]{"computer", "show", "attributes"};
        handleComputerShowCommand.invoke(controllerForPrivateMethods, (Object) command2);

        verify(mockModel).getAllColumnName();

        // Test with lineup option
        reset(mockModel, mockView);
        when(mockModel.getComTeam()).thenReturn(mockTeam);
        List<Pitcher> pitcherLineup = List.of(mockPitcher);
        when(mockModel.getComTeamPitcherLineup()).thenReturn(pitcherLineup);

        String[] command3 = new String[]{"computer", "show", "lineup"};
        handleComputerShowCommand.invoke(controllerForPrivateMethods, (Object) command3);

        verify(mockModel).getComTeamPitcherLineup();

        // Test with pitcher name
        reset(mockModel, mockView);
        when(mockModel.getPitcher(Side.COMPUTER, "Test Pitcher")).thenReturn(mockPitcher);

        String[] command4 = new String[]{"computer", "show", "Test", "Pitcher"};
        handleComputerShowCommand.invoke(controllerForPrivateMethods, (Object) command4);

        verify(mockModel).getPitcher(Side.COMPUTER, "Test Pitcher");

        // Test with team name
        reset(mockModel, mockView);
        Teams mockTeam = Teams.MARINERS;
        when(mockModel.getComTeam()).thenReturn(null); // No team selected yet
        Set<Pitcher> pitcherSet = Set.of(mockPitcher);
        when(mockModel.getComTeamPitcherLoaderLineup()).thenReturn(pitcherSet);

        String[] command5 = new String[]{"computer", "show", "mariners"};
        handleComputerShowCommand.invoke(controllerForPrivateMethods, (Object) command5);

        verify(mockModel).setComTeam(any(Teams.class));
    }

    @Test
    void testHandleComputerCommandTooShort() throws Exception {
        // Setup
        java.lang.reflect.Method handleComputerCommand = MLBSimulatorController.class.getDeclaredMethod("handleComputerCommand", String[].class);
        handleComputerCommand.setAccessible(true);

        // Test with command that's too short
        String[] command = new String[]{"computer"};
        handleComputerCommand.invoke(controllerForPrivateMethods, (Object) command);

        // Verify error message displayed
        verify(mockView).displayError("Invalid computer command. Type 'help' for available commands.");
    }

    @Test
    void testHandleComputerShowCommandTooShort() throws Exception {
        // Setup
        java.lang.reflect.Method handleComputerShowCommand = MLBSimulatorController.class.getDeclaredMethod("handleComputerShowCommand", String[].class);
        handleComputerShowCommand.setAccessible(true);

        // Test with command that's too short
        String[] command = new String[]{"computer", "show"};
        handleComputerShowCommand.invoke(controllerForPrivateMethods, (Object) command);

        // Verify error message displayed
        verify(mockView).displayError("Incorrect computer command. Type 'help' for available commands.");
    }

    @Test
    void testHandleFilterCommand() throws Exception {
        // Setup
        java.lang.reflect.Method handleFilterCommand = MLBSimulatorController.class.getDeclaredMethod("handleFilterCommand", String[].class, Side.class);
        handleFilterCommand.setAccessible(true);

        // Test with no args (show current filter) - player side
        String[] command1 = new String[]{"player", "filter"};
        handleFilterCommand.invoke(controllerForPrivateMethods, command1, Side.PLAYER);

        verify(mockView).displayBatters(any());

        // Test with reset - player side
        reset(mockModel, mockView);
        Set<Batter> batterSet = new HashSet<>();
        batterSet.add(mockBatter);
        when(mockModel.getPlayerTeamBatterLoaderLineup()).thenReturn(batterSet);

        String[] command2 = new String[]{"player", "filter", "reset"};
        handleFilterCommand.invoke(controllerForPrivateMethods, command2, Side.PLAYER);

        verify(mockView).displayMessage("Filter reset.");

        // Test with filter only - player side
        reset(mockModel, mockView);
        when(mockModel.batterFilter(anyString(), any(HashSet.class))).thenReturn(Stream.of(mockBatter));

        String[] command3 = new String[]{"player", "filter", "AVG>0.300"};
        handleFilterCommand.invoke(controllerForPrivateMethods, command3, Side.PLAYER);

        verify(mockModel).batterFilter(anyString(), any(HashSet.class));
    }

    @Test
    void testHandleFilterCommandWithSort() throws Exception {
        // Setup
        java.lang.reflect.Method parseFilterCriteria = MLBSimulatorController.class.getDeclaredMethod("parseFilterCriteria", String[].class);
        parseFilterCriteria.setAccessible(true);

        // Create a filter criteria object to test applyFilterAndSort directly
        FilterCriteria testCriteria = new FilterCriteria();
        testCriteria.filterString = "AVG>0.300";
        testCriteria.hasSort = true;
        testCriteria.sortAttribute = PlayerData.T_H;  // Use a known enum value
        testCriteria.sortAsc = true;

        // Setup the model mock
        when(mockModel.batterFilter(anyString(), eq(PlayerData.T_H), eq(true), any(HashSet.class)))
                .thenReturn(Stream.of(mockBatter));

        // Test applyFilterAndSort directly
        Method applyFilterAndSort = MLBSimulatorController.class.getDeclaredMethod(
                "applyFilterAndSort", boolean.class, String.class, PlayerData.class, boolean.class);
        applyFilterAndSort.setAccessible(true);

        applyFilterAndSort.invoke(controllerForPrivateMethods, true, "AVG>0.300", PlayerData.T_H, true);

        // Verify the correct model method was called
        verify(mockModel).batterFilter(eq("AVG>0.300"), eq(PlayerData.T_H), eq(true), any(HashSet.class));
    }

    @Test
    void testHandleFilterCommandWithComputer() throws Exception {
        // Setup
        java.lang.reflect.Method handleFilterCommand = MLBSimulatorController.class.getDeclaredMethod("handleFilterCommand", String[].class, Side.class);
        handleFilterCommand.setAccessible(true);

        // Test with computer side - filter only
        reset(mockModel, mockView);
        Set<Pitcher> pitcherSet = new HashSet<>();
        pitcherSet.add(mockPitcher);
        when(mockModel.getComTeamPitcherLoaderLineup()).thenReturn(pitcherSet);
        when(mockModel.pitcherFilter(anyString(), any(HashSet.class))).thenReturn(Stream.of(mockPitcher));

        String[] command5 = new String[]{"computer", "filter", "ERA<3.00"};
        handleFilterCommand.invoke(controllerForPrivateMethods, command5, Side.COMPUTER);

        verify(mockModel).pitcherFilter(anyString(), any(HashSet.class));
    }

    @Test
    void testApplyFilterOnly() throws Exception {
        // Setup
        java.lang.reflect.Method applyFilterOnly = MLBSimulatorController.class.getDeclaredMethod("applyFilterOnly", boolean.class, String.class);
        applyFilterOnly.setAccessible(true);

        // Test with player side
        when(mockModel.batterFilter(anyString(), any(HashSet.class))).thenReturn(Stream.of(mockBatter));

        applyFilterOnly.invoke(controllerForPrivateMethods, true, "AVG>0.300");

        verify(mockModel).batterFilter(eq("AVG>0.300"), any(HashSet.class));
        verify(mockView).displayBatters(any());

        // Test with computer side
        reset(mockModel, mockView);
        when(mockModel.pitcherFilter(anyString(), any(HashSet.class))).thenReturn(Stream.of(mockPitcher));

        applyFilterOnly.invoke(controllerForPrivateMethods, false, "ERA<3.00");

        verify(mockModel).pitcherFilter(eq("ERA<3.00"), any(HashSet.class));
        verify(mockView).displayPitchers(any());

        // Test with empty result - player side
        reset(mockModel, mockView);
        when(mockModel.batterFilter(anyString(), any(HashSet.class))).thenReturn(Stream.empty());
        when(mockModel.getPlayerTeamBatterLoaderLineup()).thenReturn(Set.of(mockBatter));

        applyFilterOnly.invoke(controllerForPrivateMethods, true, "AVG>0.500");

        verify(mockModel).getPlayerTeamBatterLoaderLineup(); // Should reset to full roster
    }

    @Test
    void testDisplayCurrentFilter() throws Exception {
        // Setup
        java.lang.reflect.Method displayCurrentFilter = MLBSimulatorController.class.getDeclaredMethod("displayCurrentFilter", boolean.class);
        displayCurrentFilter.setAccessible(true);

        // Test with player side
        displayCurrentFilter.invoke(controllerForPrivateMethods, true);

        verify(mockView).displayBatters(any());

        // Test with computer side
        reset(mockView);

        displayCurrentFilter.invoke(controllerForPrivateMethods, false);

        verify(mockView).displayPitchers(any());
    }

    @Test
    void testIsEmpty() throws Exception {
        // Setup
        java.lang.reflect.Method isEmpty = MLBSimulatorController.class.getDeclaredMethod("isEmpty", List.class);
        isEmpty.setAccessible(true);

        // Test with null list
        boolean result1 = (boolean) isEmpty.invoke(controllerForPrivateMethods, (List<?>) null);

        assertTrue(result1);
        verify(mockView).displayMessage("No items to display.");

        // Test with empty list
        reset(mockView);

        boolean result2 = (boolean) isEmpty.invoke(controllerForPrivateMethods, new ArrayList<>());

        assertTrue(result2);
        verify(mockView).displayMessage("No items to display.");

        // Test with non-empty list
        reset(mockView);

        List<String> nonEmptyList = List.of("item1", "item2");
        boolean result3 = (boolean) isEmpty.invoke(controllerForPrivateMethods, nonEmptyList);

        assertFalse(result3);
        verifyNoInteractions(mockView);
    }

    @Test
    void testResetFilter() throws Exception {
        // Setup
        java.lang.reflect.Method resetFilter = MLBSimulatorController.class.getDeclaredMethod("resetFilter", boolean.class);
        resetFilter.setAccessible(true);

        // Test with player side
        Set<Batter> batterSet = new HashSet<>();
        batterSet.add(mockBatter);
        when(mockModel.getPlayerTeamBatterLoaderLineup()).thenReturn(batterSet);

        resetFilter.invoke(controllerForPrivateMethods, true);

        verify(mockModel).getPlayerTeamBatterLoaderLineup();
        verify(mockView).displayMessage("Filter reset.");

        // Test with computer side
        reset(mockModel, mockView);
        Set<Pitcher> pitcherSet = new HashSet<>();
        pitcherSet.add(mockPitcher);
        when(mockModel.getComTeamPitcherLoaderLineup()).thenReturn(pitcherSet);

        resetFilter.invoke(controllerForPrivateMethods, false);

        verify(mockModel).getComTeamPitcherLoaderLineup();
        verify(mockView).displayMessage("Filter reset.");
    }

    @Test
    void testParseFilterCriteria() throws Exception {
        // Setup
        java.lang.reflect.Method parseFilterCriteria = MLBSimulatorController.class.getDeclaredMethod("parseFilterCriteria", String[].class);
        parseFilterCriteria.setAccessible(true);

        // Test with simple filter string
        String[] command1 = new String[]{"player", "filter", "AVG>0.300"};
        FilterCriteria result1 = (FilterCriteria) parseFilterCriteria.invoke(controllerForPrivateMethods, (Object) command1);

        assertEquals("AVG>0.300", result1.filterString);
        assertFalse(result1.hasSort);

        // Reset mocks for the next test
        reset(mockView);

        // Test with filter and sort - test with known valid enum values
        String[] command2 = new String[]{"player", "filter", "AVG>0.300", "sort", "TotalHR"};
        FilterCriteria result2 = (FilterCriteria) parseFilterCriteria.invoke(controllerForPrivateMethods, (Object) command2);

        assertEquals("AVG>0.300", result2.filterString);
        // We can't guarantee which enum value will be returned since we're using the real method,
        // so we just check that hasSort is true and sortAttribute is not null
        assertTrue(result2.hasSort);
        assertNotNull(result2.sortAttribute);
        assertTrue(result2.sortAsc); // Default to ascending

        // Reset mocks
        reset(mockView);

        // Test with filter, sort and descending direction
        String[] command3 = new String[]{"player", "filter", "AVG>0.300", "sort", "TotalHR", "desc"};
        FilterCriteria result3 = (FilterCriteria) parseFilterCriteria.invoke(controllerForPrivateMethods, (Object) command3);

        assertEquals("AVG>0.300", result3.filterString);
        assertTrue(result3.hasSort);
        assertNotNull(result3.sortAttribute);
        assertFalse(result3.sortAsc); // Descending specified

        // Reset mocks
        reset(mockView);

        // Test with filter, sort and ascending direction
        String[] command4 = new String[]{"player", "filter", "AVG>0.300", "sort", "AVG", "asc"};
        FilterCriteria result4 = (FilterCriteria) parseFilterCriteria.invoke(controllerForPrivateMethods, (Object) command4);

        assertEquals("AVG>0.300", result4.filterString);
        assertTrue(result4.hasSort);
        assertNotNull(result4.sortAttribute);
        assertTrue(result4.sortAsc); // Ascending specified

        // Reset mocks
        reset(mockView);

        // Test with whitespace normalization
        String[] command5 = new String[]{"player", "filter", "AVG", ">", "0.300"};
        FilterCriteria result5 = (FilterCriteria) parseFilterCriteria.invoke(controllerForPrivateMethods, (Object) command5);

        assertEquals("AVG>0.300", result5.filterString); // Spaces around '>' removed

        // Reset mocks
        reset(mockView);

        // Test with invalid sort attribute
        String[] command6 = new String[]{"player", "filter", "AVG>0.300", "sort", "INVALID_ATTRIBUTE"};
        FilterCriteria result6 = (FilterCriteria) parseFilterCriteria.invoke(controllerForPrivateMethods, (Object) command6);

        assertFalse(result6.hasSort); // Should reset hasSort flag
        verify(mockView).displayMessage(anyString()); // Should display error message
    }

    // Add these methods to your existing test class to increase branch coverage

    /**
     * Test addPlayer with null batter/pitcher result
     */
    @Test
    void testAddPlayerWithNullPlayer() throws Exception {
        // Use a simpler approach - test that the model method isn't called when player is null

        // First, test the batter path directly with the existing getBatterFromCommand method
        java.lang.reflect.Method getBatterMethod = MLBSimulatorController.class.getDeclaredMethod("getBatterFromCommand", AddCommandInfo.class);
        getBatterMethod.setAccessible(true);

        // Setup a case where getBatterFromCommand would return null (name doesn't match)
        AddCommandInfo cmdInfoBatter = new AddCommandInfo();
        cmdInfoBatter.isValid = true;
        cmdInfoBatter.isIndex = false;
        cmdInfoBatter.playerName = "NonExistentPlayer";

        // Verify it returns null (this confirms one condition for the branch we want to test)
        assertNull(getBatterMethod.invoke(controllerForPrivateMethods, cmdInfoBatter));

        // Now directly test addPlayer with this same input
        java.lang.reflect.Method addPlayer = MLBSimulatorController.class.getDeclaredMethod("addPlayer", AddCommandInfo.class, boolean.class, String.class);
        addPlayer.setAccessible(true);

        reset(mockModel, mockView);
        addPlayer.invoke(controllerForPrivateMethods, cmdInfoBatter, true, "NonExistentPlayer to 1");

        // Since getBatterFromCommand would return null, addBatterToLineup should never be called
        verify(mockModel, never()).addBatterToLineup(any(), any(), any());

        // Similarly for the pitcher case
        java.lang.reflect.Method getPitcherMethod = MLBSimulatorController.class.getDeclaredMethod("getPitcherFromCommand", AddCommandInfo.class);
        getPitcherMethod.setAccessible(true);

        AddCommandInfo cmdInfoPitcher = new AddCommandInfo();
        cmdInfoPitcher.isValid = true;
        cmdInfoPitcher.isIndex = false;
        cmdInfoPitcher.playerName = "NonExistentPlayer";

        // Verify it returns null
        assertNull(getPitcherMethod.invoke(controllerForPrivateMethods, cmdInfoPitcher));

        reset(mockModel, mockView);
        addPlayer.invoke(controllerForPrivateMethods, cmdInfoPitcher, false, "NonExistentPlayer to 1");

        // Since getPitcherFromCommand would return null, addPitcherToLineup should never be called
        verify(mockModel, never()).addPitcherToLineup(any(), any(), any());
    }

    /**
     * Test applyFilterAndSort with empty results
     */
    @Test
    void testApplyFilterAndSortWithEmptyResults() throws Exception {
        // Setup
        java.lang.reflect.Method applyFilterAndSort = MLBSimulatorController.class.getDeclaredMethod(
                "applyFilterAndSort", boolean.class, String.class, PlayerData.class, boolean.class);
        applyFilterAndSort.setAccessible(true);

        // Test batter side with empty results
        when(mockModel.batterFilter(anyString(), any(PlayerData.class), anyBoolean(), any(HashSet.class)))
                .thenReturn(Stream.empty());
        when(mockModel.getPlayerTeamBatterLoaderLineup()).thenReturn(Set.of(mockBatter));

        applyFilterAndSort.invoke(controllerForPrivateMethods, true, "AVG>0.500", PlayerData.T_H, true);

        // Verify reset to full roster happens
        verify(mockModel).getPlayerTeamBatterLoaderLineup();

        // Test pitcher side with empty results
        reset(mockModel, mockView);
        when(mockModel.pitcherFilter(anyString(), any(PlayerData.class), anyBoolean(), any(HashSet.class)))
                .thenReturn(Stream.empty());
        when(mockModel.getComTeamPitcherLoaderLineup()).thenReturn(Set.of(mockPitcher));

        applyFilterAndSort.invoke(controllerForPrivateMethods, false, "TotalHR<1.00", PlayerData.T_HR, true);

        verify(mockModel).getComTeamPitcherLoaderLineup();
    }

    /**
     * Test handleComputerCommand with various edge cases
     */
    @Test
    void testHandleComputerCommandEdgeCases() throws Exception {
        // Setup
        java.lang.reflect.Method handleComputerCommand = MLBSimulatorController.class.getDeclaredMethod("handleComputerCommand", String[].class);
        handleComputerCommand.setAccessible(true);

        // Test select with invalid team (exception handling)
        reset(mockModel, mockView);
        doThrow(new IllegalArgumentException("Invalid team name")).when(mockModel).setComTeam(any(Teams.class));

        String[] command1 = new String[]{"computer", "select", "invalid_team"};
        handleComputerCommand.invoke(controllerForPrivateMethods, (Object) command1);

        verify(mockView).displayMessage(anyString());

        // Test add with no team selected
        reset(mockModel, mockView);
        when(mockModel.getComTeam()).thenReturn(null);

        String[] command2 = new String[]{"computer", "add", "Test Pitcher"};
        handleComputerCommand.invoke(controllerForPrivateMethods, (Object) command2);

        verify(mockView).displayError("Please select a team first.");

        // Test filter with no team selected
        reset(mockModel, mockView);
        when(mockModel.getComTeam()).thenReturn(null);

        String[] command3 = new String[]{"computer", "filter", "ERA<3.00"};
        handleComputerCommand.invoke(controllerForPrivateMethods, (Object) command3);

        verify(mockView).displayError("Please select a team first.");
    }

    /**
     * Test handleComputerShowCommand edge cases
     */
    @Test
    void testHandleComputerShowCommandEdgeCases() throws Exception {
        // Setup
        java.lang.reflect.Method handleComputerShowCommand = MLBSimulatorController.class.getDeclaredMethod("handleComputerShowCommand", String[].class);
        handleComputerShowCommand.setAccessible(true);

        // Test show lineup with no team selected
        reset(mockModel, mockView);
        when(mockModel.getComTeam()).thenReturn(null);

        String[] command1 = new String[]{"computer", "show", "lineup"};
        handleComputerShowCommand.invoke(controllerForPrivateMethods, (Object) command1);

        verify(mockView).displayError("Please select a team first.");

        // Test with pitcher name not found
        reset(mockModel, mockView);
        when(mockModel.getPitcher(eq(Side.COMPUTER), anyString())).thenReturn(null);

        String[] command2 = new String[]{"computer", "show", "NonExistent", "Pitcher"};
        handleComputerShowCommand.invoke(controllerForPrivateMethods, (Object) command2);

        verify(mockView).displayError(contains("Pitcher not found"));

        // Test with invalid team name (exception handling)
        reset(mockModel, mockView);
        doThrow(new IllegalArgumentException("Invalid team name")).when(mockTeam).getTeamName();

        String[] command3 = new String[]{"computer", "show", "invalid_team"};
        handleComputerShowCommand.invoke(controllerForPrivateMethods, (Object) command3);

        verify(mockView).displayMessage(anyString());
    }

    /**
     * Test empty display current filter
     */
    @Test
    void testDisplayCurrentFilterWithNullOrEmpty() throws Exception {
        // Setup
        java.lang.reflect.Method displayCurrentFilter = MLBSimulatorController.class.getDeclaredMethod("displayCurrentFilter", boolean.class);
        displayCurrentFilter.setAccessible(true);

        // Set filtered batters to null
        Field filteredBattersField = MLBSimulatorController.class.getDeclaredField("filteredBatters");
        filteredBattersField.setAccessible(true);
        filteredBattersField.set(controllerForPrivateMethods, null);

        // Test with null batters list
        displayCurrentFilter.invoke(controllerForPrivateMethods, true);

        verify(mockView).displayMessage("No items to display.");

        // Set filtered pitchers to empty list
        reset(mockView);
        Field filteredPitchersField = MLBSimulatorController.class.getDeclaredField("filteredPitchers");
        filteredPitchersField.setAccessible(true);
        filteredPitchersField.set(controllerForPrivateMethods, new ArrayList<>());

        // Test with empty pitchers list
        displayCurrentFilter.invoke(controllerForPrivateMethods, false);

        verify(mockView).displayMessage("No items to display.");
    }

    /**
     * Test process command with white space only command
     */
    @Test
    void testProcessCommandWhitespace() throws Exception {
        // Setup
        java.lang.reflect.Method processCommand = MLBSimulatorController.class.getDeclaredMethod("processCommand", String.class);
        processCommand.setAccessible(true);

        // Execute with whitespace only
        processCommand.invoke(controllerForPrivateMethods, "   ");

        // Verify no interactions
        verifyNoMoreInteractions(mockView);
    }

    /**
     * Test validation for add to command with null lists
     */
    @Test
    void testValidateAddToCommandWithNullLists() throws Exception {
        // Setup
        java.lang.reflect.Method validateAddToCommand = MLBSimulatorController.class.getDeclaredMethod("validateAddToCommand", String.class, boolean.class);
        validateAddToCommand.setAccessible(true);

        // Set filtered batters to null
        Field filteredBattersField = MLBSimulatorController.class.getDeclaredField("filteredBatters");
        filteredBattersField.setAccessible(true);
        filteredBattersField.set(controllerForPrivateMethods, null);

        // Test with null batters list
        AddCommandInfo result = (AddCommandInfo) validateAddToCommand.invoke(controllerForPrivateMethods, "1 to 1", true);

        assertFalse(result.isValid);
        verify(mockView).displayError(contains("No batters available"));

        // Set filtered pitchers to null
        reset(mockView);
        Field filteredPitchersField = MLBSimulatorController.class.getDeclaredField("filteredPitchers");
        filteredPitchersField.setAccessible(true);
        filteredPitchersField.set(controllerForPrivateMethods, null);

        // Test with null pitchers list
        result = (AddCommandInfo) validateAddToCommand.invoke(controllerForPrivateMethods, "1 to 1", false);

        assertFalse(result.isValid);
        verify(mockView).displayError(contains("No pitchers available"));
    }

    /**
     * Test parse filter criteria with asc/desc keywords only
     */
    @Test
    void testParseFilterCriteriaWithDirectionKeywordsOnly() throws Exception {
        // Setup
        java.lang.reflect.Method parseFilterCriteria = MLBSimulatorController.class.getDeclaredMethod("parseFilterCriteria", String[].class);
        parseFilterCriteria.setAccessible(true);

        // Test with asc keyword only (no sort attribute)
        String[] command1 = new String[]{"player", "filter", "AVG>0.300", "sort", "asc"};
        FilterCriteria result1 = (FilterCriteria) parseFilterCriteria.invoke(controllerForPrivateMethods, (Object) command1);

        assertEquals("AVG>0.300", result1.filterString);
        assertFalse(result1.hasSort); // Should not have sort since attribute is missing

        // Test with desc keyword only (no sort attribute)
        String[] command2 = new String[]{"player", "filter", "AVG>0.300", "sort", "desc"};
        FilterCriteria result2 = (FilterCriteria) parseFilterCriteria.invoke(controllerForPrivateMethods, (Object) command2);

        assertEquals("AVG>0.300", result2.filterString);
        assertFalse(result2.hasSort); // Should not have sort since attribute is missing
    }

    /**
     * Test negative player index in validateAddToCommand
     */
    @Test
    void testValidateAddToCommandWithNegativeIndex() throws Exception {
        // Setup
        java.lang.reflect.Method validateAddToCommand = MLBSimulatorController.class.getDeclaredMethod("validateAddToCommand", String.class, boolean.class);
        validateAddToCommand.setAccessible(true);

        // Test with negative index
        AddCommandInfo result = (AddCommandInfo) validateAddToCommand.invoke(controllerForPrivateMethods, "-1 to 1", true);

        assertFalse(result.isValid);
        verify(mockView).displayError(contains("Player index out of range"));
    }

    /**
     * Test handlePlayerShowCommand with different subcommands
     */
    @Test
    void testHandlePlayerShowCommandWithDifferentSubcommands() throws Exception {
        // Setup
        java.lang.reflect.Method handlePlayerShowCommand = MLBSimulatorController.class.getDeclaredMethod("handlePlayerShowCommand", String[].class);
        handlePlayerShowCommand.setAccessible(true);

        // Test with default case (batter name) but length < 3
        reset(mockModel, mockView);
        String[] command = new String[]{"player", "show", "unknown"};
        handlePlayerShowCommand.invoke(controllerForPrivateMethods, (Object) command);

        // Should just try to get the batter by name "unknown"
        verify(mockModel).getBatter(Side.PLAYER, "unknown");
    }

    /**
     * Test handleFilterCommand with empty filter
     */
    @Test
    void testHandleFilterCommandWithEmptyFilter() throws Exception {
        // Setup
        java.lang.reflect.Method handleFilterCommand = MLBSimulatorController.class.getDeclaredMethod("handleFilterCommand", String[].class, Side.class);
        handleFilterCommand.setAccessible(true);

        // Empty filter string
        String[] command = new String[]{"player", "filter", ""};
        handleFilterCommand.invoke(controllerForPrivateMethods, command, Side.PLAYER);

        // Should still call batterFilter with empty string
        verify(mockModel).batterFilter(eq(""), any(HashSet.class));
    }

    /**
     * Test run simulation with outfile appending
     */
    @Test
    void testRunSimulationWithOutfileAppending() throws Exception {
        // Setup
        java.lang.reflect.Method runSimulation = MLBSimulatorController.class.getDeclaredMethod("runSimulation", String[].class);
        runSimulation.setAccessible(true);

        when(mockModel.startSimAndGetResult()).thenReturn(mockSimulationResult);

        // Capture the outfile names
        ArgumentCaptor<String> outfileCaptor = ArgumentCaptor.forClass(String.class);

        // Test with multiple simulations and outfile
        String[] command = new String[]{"simulate", "-n", "2", "-o", "test_results"};
        runSimulation.invoke(controllerForPrivateMethods, (Object) command);

        // Verify simulations run and results displayed
        verify(mockModel, times(2)).startSimAndGetResult();
        verify(mockView, times(2)).displaySimulationResult(mockSimulationResult);

        // Verify the outfile names are correct with sequence numbers
        verify(mockModel, times(2)).saveGameDetailsAsTXTFile(outfileCaptor.capture());

        List<String> outfileNames = outfileCaptor.getAllValues();
        assertEquals(2, outfileNames.size());
        assertTrue(outfileNames.get(0).matches("test_results_1\\.txt"));
        assertTrue(outfileNames.get(1).matches("test_results_2\\.txt"));
    }
}