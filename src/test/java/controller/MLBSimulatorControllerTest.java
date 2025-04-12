package controller;

import gameEnum.PlayerData;
import gameEnum.Side;
import gameEnum.Teams;
import model.Model;
import model.player.Batter;
import model.player.Pitcher;
import model.simulation.SimulationResult;
import model.team.PlayerTeam;
import model.team.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import view.TextUI;

import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.*;

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

        List<Batter> batterList = new ArrayList<>();
        batterList.add(mockBatter);

        // Create a controller with real model for testing private methods
        controllerForPrivateMethods = new MLBSimulatorController();

        // Use reflection to make private methods accessible
        java.lang.reflect.Field modelField = MLBSimulatorController.class.getDeclaredField("model");
        modelField.setAccessible(true);
        modelField.set(controllerForPrivateMethods, mockModel);

        java.lang.reflect.Field viewField = MLBSimulatorController.class.getDeclaredField("view");
        viewField.setAccessible(true);
        viewField.set(controllerForPrivateMethods, mockView);

        java.lang.reflect.Field filteredBattersField = MLBSimulatorController.class.getDeclaredField("filteredBatters");
        filteredBattersField.setAccessible(true);
        filteredBattersField.set(controllerForPrivateMethods, batterList);

        List<Pitcher> pitcherList = new ArrayList<>();
        pitcherList.add(mockPitcher);
        java.lang.reflect.Field filteredPitchersField = MLBSimulatorController.class.getDeclaredField("filteredPitchers");
        filteredPitchersField.setAccessible(true);
        filteredPitchersField.set(controllerForPrivateMethods, pitcherList);
    }

    @Test
    void testStart() {
        // Setup
        when(mockView.getCommand())
                .thenReturn("help")
                .thenReturn("exit");

        // Execute
        controller.start();

        // Verify
        verify(mockView, times(2)).displayHelp(); // Once at start, once for help command
        verify(mockView).displayMessage("Welcome to MLB Simulator!");
        verify(mockView).displayMessage("Exiting MLB Simulator. Goodbye!");
        verify(mockView).close();
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
    void testProcessCommandPlayer() throws Exception {
        // Setup
        java.lang.reflect.Method processCommand = MLBSimulatorController.class.getDeclaredMethod("processCommand", String.class);
        processCommand.setAccessible(true);

        // Execute
        processCommand.invoke(controllerForPrivateMethods, "player show lineup");

        // Verify player command was processed
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

        // Execute
        processCommand.invoke(controllerForPrivateMethods, "computer show teams");

        // Verify computer command was processed
        verify(mockModel).getAllTeamName();
    }

    @Test
    void testProcessCommandSimulate() throws Exception {
        // Setup
        java.lang.reflect.Method processCommand = MLBSimulatorController.class.getDeclaredMethod("processCommand", String.class);
        processCommand.setAccessible(true);

        when(mockModel.startSimAndGetResult()).thenReturn(mockSimulationResult);

        // Execute
        processCommand.invoke(controllerForPrivateMethods, "simulate");

        // Verify simulate command was processed
        verify(mockModel).startSimAndGetResult();
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
    }

    @Test
    void testParseSimulateOptionsWithIncompleteOptions() throws Exception {
        // Setup
        java.lang.reflect.Method parseSimulateOptions = MLBSimulatorController.class.getDeclaredMethod("parseSimulateOptions", String[].class);
        parseSimulateOptions.setAccessible(true);

        // Test with incomplete -n option (no value)
        String[] command1 = new String[]{"simulate", "-n"};
        Map<String, String> result1 = (Map<String, String>) parseSimulateOptions.invoke(controllerForPrivateMethods, (Object) command1);

        assertEquals("1", result1.get("number"));
        assertNull(result1.get("outfile"));

        // Test with incomplete -o option (no value)
        String[] command2 = new String[]{"simulate", "-o"};
        Map<String, String> result2 = (Map<String, String>) parseSimulateOptions.invoke(controllerForPrivateMethods, (Object) command2);

        assertEquals("1", result2.get("number"));
        assertNull(result2.get("outfile"));
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
        verify(mockModel).saveGameDetailsAsTXTFile(contains("results.txt"));
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
    void testRunSimulationWithOutfileAndMultipleFailedSimulations() throws Exception {
        // Setup
        java.lang.reflect.Method runSimulation = MLBSimulatorController.class.getDeclaredMethod("runSimulation", String[].class);
        runSimulation.setAccessible(true);

        when(mockModel.startSimAndGetResult()).thenReturn(null);

        // Test with multiple simulations that all fail
        String[] command = new String[]{"simulate", "-n", "3", "-o", "results.txt"};
        runSimulation.invoke(controllerForPrivateMethods, (Object) command);

        // Verify error message displayed multiple times
        verify(mockView, times(3)).displayError(anyString());
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
        String[] command2 = new String[]{"player", "add", "Test Batter"};
        handlePlayerCommand.invoke(controllerForPrivateMethods, (Object) command2);

        verify(mockModel).addBatterToLineup(eq(Side.PLAYER), eq("Test Batter"), any());

        // Test with remove command
        String[] command3 = new String[]{"player", "remove", "Test Batter"};
        handlePlayerCommand.invoke(controllerForPrivateMethods, (Object) command3);

        verify(mockModel).removeFromLineup(Side.PLAYER, "batter", "Test Batter");

        // Test with clear command
        String[] command4 = new String[]{"player", "clear"};
        handlePlayerCommand.invoke(controllerForPrivateMethods, (Object) command4);

        verify(mockModel).clearLineup(Side.PLAYER, "batter");
        verify(mockView).displayMessage("Batter lineup cleared.");
    }

    @Test
    void testHandlePlayerCommandWithFilter() throws Exception {
        // Setup
        java.lang.reflect.Method handlePlayerCommand = MLBSimulatorController.class.getDeclaredMethod("handlePlayerCommand", String[].class);
        handlePlayerCommand.setAccessible(true);

        // Test filter command
        String[] command = new String[]{"player", "filter", "AVG>0.300"};
        handlePlayerCommand.invoke(controllerForPrivateMethods, (Object) command);

        // This test is to verify the filter branch is taken
        verify(mockModel).batterFilter(anyString(), any(HashSet.class));
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

        // Test with all option
        reset(mockModel, mockView);
        Set<Batter> batterSet = new HashSet<>();
        batterSet.add(mockBatter);
        when(mockModel.getPlayerTeamBatterLoaderLineup()).thenReturn(batterSet);

        String[] command2 = new String[]{"player", "show", "all"};
        handlePlayerShowCommand.invoke(controllerForPrivateMethods, (Object) command2);

        verify(mockModel).getPlayerTeamBatterLoaderLineup();

        // Test with batter name
        reset(mockModel, mockView);
        when(mockModel.getBatter(Side.PLAYER, "Test Batter")).thenReturn(mockBatter);

        String[] command3 = new String[]{"player", "show", "Test", "Batter"};
        handlePlayerShowCommand.invoke(controllerForPrivateMethods, (Object) command3);

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
    void testHandlePlayerFilterCommand() throws Exception {
        // Setup
        java.lang.reflect.Method handlePlayerFilterCommand = MLBSimulatorController.class.getDeclaredMethod("handlePlayerFilterCommand", String[].class);
        handlePlayerFilterCommand.setAccessible(true);

        // Test with no args (show current filter)
        String[] command1 = new String[]{"player", "filter"};
        handlePlayerFilterCommand.invoke(controllerForPrivateMethods, (Object) command1);

        verify(mockView).displayBatters(any(List.class));

        // Test reset functionality (uses Objects.equals now)
        reset(mockView, mockModel);
        Set<Batter> batterSet = new HashSet<>();
        batterSet.add(mockBatter);
        when(mockModel.getPlayerTeamBatterLoaderLineup()).thenReturn(batterSet);

        String[] resetCommand = new String[]{"player", "filter", "reset"};
        handlePlayerFilterCommand.invoke(controllerForPrivateMethods, (Object) resetCommand);

        verify(mockModel).getPlayerTeamBatterLoaderLineup();
        verify(mockView).displayMessage("Filter reset.");

        // Test with filter criteria
        reset(mockView, mockModel);
        when(mockModel.batterFilter(anyString(), any(HashSet.class))).thenReturn(Stream.of(mockBatter));

        String[] command2 = new String[]{"player", "filter", "AVG>0.300"};
        handlePlayerFilterCommand.invoke(controllerForPrivateMethods, (Object) command2);

        verify(mockModel).batterFilter(eq("AVG>0.300"), any(HashSet.class));

        // Test with filter and sort
        reset(mockView, mockModel);
        when(mockModel.batterFilter(anyString(), any(PlayerData.class), any(HashSet.class))).thenReturn(Stream.of(mockBatter));

        String[] command3 = new String[]{"player", "filter", "AVG>0.300", "sort", "TotalHR"};
        handlePlayerFilterCommand.invoke(controllerForPrivateMethods, (Object) command3);

        verify(mockModel).batterFilter(eq("AVG>0.300"), any(PlayerData.class), any(HashSet.class));
    }

    @Test
    void testHandlePlayerFilterCommandInvalidSortAttribute() throws Exception {
        // Setup
        java.lang.reflect.Method handlePlayerFilterCommand = MLBSimulatorController.class.getDeclaredMethod("handlePlayerFilterCommand", String[].class);
        handlePlayerFilterCommand.setAccessible(true);

        // Mock the PlayerData.fromColumnName to throw an exception
        doThrow(new IllegalArgumentException("Invalid sort attribute")).when(mockModel)
                .batterFilter(anyString(), any(PlayerData.class), any(HashSet.class));

        // Test with invalid sort attribute
        String[] command = new String[]{"player", "filter", "AVG>0.300", "sort", "InvalidAttribute"};
        handlePlayerFilterCommand.invoke(controllerForPrivateMethods, (Object) command);

        // Verify message displayed
        verify(mockView).displayMessage(anyString());
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
    void testHandlePlayerCommandAddTooShort() throws Exception {
        // Setup
        java.lang.reflect.Method handlePlayerCommand = MLBSimulatorController.class.getDeclaredMethod("handlePlayerCommand", String[].class);
        handlePlayerCommand.setAccessible(true);

        // Test with add command that's too short
        String[] command = new String[]{"player", "add"};
        handlePlayerCommand.invoke(controllerForPrivateMethods, (Object) command);

        // Verify error message displayed
        verify(mockView).displayError("Invalid command. Use 'player add [name]'.");
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
    void testHandleComputerCommand() throws Exception {
        // Setup
        java.lang.reflect.Method handleComputerCommand = MLBSimulatorController.class.getDeclaredMethod("handleComputerCommand", String[].class);
        handleComputerCommand.setAccessible(true);

        // Test with show teams
        List<String> teamNames = new ArrayList<>();
        teamNames.add("Mariners");
        when(mockModel.getAllTeamName()).thenReturn(teamNames);

        String[] command1 = new String[]{"computer", "show", "teams"};
        handleComputerCommand.invoke(controllerForPrivateMethods, (Object) command1);

        verify(mockModel).getAllTeamName();
        verify(mockView).displayAllTeams(teamNames);

        // Test computer select team
        reset(mockModel, mockView);
        Set<Pitcher> pitcherSet = new HashSet<>();
        pitcherSet.add(mockPitcher);
        when(mockModel.getComTeamPitcherLoaderLineup()).thenReturn(pitcherSet);

        String[] command2 = new String[]{"computer", "select", "mariners"};
        handleComputerCommand.invoke(controllerForPrivateMethods, (Object) command2);

        verify(mockModel).setComTeam(any(Teams.class));
        verify(mockModel).getComTeamPitcherLoaderLineup();

        // Test computer add
        reset(mockModel, mockView);

        String[] command3 = new String[]{"computer", "add", "Test Pitcher"};
        handleComputerCommand.invoke(controllerForPrivateMethods, (Object) command3);

        verify(mockModel).addBatterToLineup(eq(Side.COMPUTER), eq("Test Pitcher"), any());

        // Test computer remove
        reset(mockModel, mockView);

        String[] command4 = new String[]{"computer", "remove", "Test Pitcher"};
        handleComputerCommand.invoke(controllerForPrivateMethods, (Object) command4);

        verify(mockModel).removeFromLineup(Side.COMPUTER, "pitcher", "Test Pitcher");
    }

    @Test
    void testHandleComputerCommandShowNonTeamsOption() throws Exception {
        // Setup
        java.lang.reflect.Method handleComputerCommand = MLBSimulatorController.class.getDeclaredMethod("handleComputerCommand", String[].class);
        handleComputerCommand.setAccessible(true);

        // Create team to return
        Team mockTeam = new PlayerTeam(Teams.MARINERS);
        when(mockModel.getComTeam()).thenReturn(mockTeam);

        Set<Pitcher> pitcherSet = new HashSet<>();
        pitcherSet.add(mockPitcher);
        when(mockModel.getComTeamPitcherLoaderLineup()).thenReturn(pitcherSet);

        // Test with show and a team name
        String[] command = new String[]{"computer", "show", "mariners"};
        handleComputerCommand.invoke(controllerForPrivateMethods, (Object) command);

        // Verify correct methods called
        verify(mockModel, times(2)).setComTeam(any(Teams.class));
        verify(mockModel).getComTeamPitcherLoaderLineup();
    }

    @Test
    void testHandleComputerCommandWithInvalidSubcommand() throws Exception {
        // Setup
        java.lang.reflect.Method handleComputerCommand = MLBSimulatorController.class.getDeclaredMethod("handleComputerCommand", String[].class);
        handleComputerCommand.setAccessible(true);

        // Test with invalid subcommand
        String[] command = new String[]{"computer", "invalid", "argument"};
        handleComputerCommand.invoke(controllerForPrivateMethods, (Object) command);

        // Verify error message displayed
        verify(mockView).displayError("Invalid computer command. Type 'help' for available commands.");
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
        java.lang.reflect.Method handleComputerCommand = MLBSimulatorController.class.getDeclaredMethod("handleComputerCommand", String[].class);
        handleComputerCommand.setAccessible(true);

        // Test with show command that's too short
        String[] command = new String[]{"computer", "show"};
        handleComputerCommand.invoke(controllerForPrivateMethods, (Object) command);

        // Verify error message displayed
        verify(mockView).displayError("Please specify a team name or 'all'.");
    }

    @Test
    void testHandleComputerSelectCommandTooShort() throws Exception {
        // Setup
        java.lang.reflect.Method handleComputerCommand = MLBSimulatorController.class.getDeclaredMethod("handleComputerCommand", String[].class);
        handleComputerCommand.setAccessible(true);

        // Test with select command that's too short
        String[] command = new String[]{"computer", "select"};
        handleComputerCommand.invoke(controllerForPrivateMethods, (Object) command);

        // Verify error message displayed
        verify(mockView).displayError("Please specify a team name.");
    }

    @Test
    void testHandleComputerAddCommandTooShort() throws Exception {
        // Setup
        java.lang.reflect.Method handleComputerCommand = MLBSimulatorController.class.getDeclaredMethod("handleComputerCommand", String[].class);
        handleComputerCommand.setAccessible(true);

        // Test with add command that's too short
        String[] command = new String[]{"computer", "add"};
        handleComputerCommand.invoke(controllerForPrivateMethods, (Object) command);

        // Verify error message displayed
        verify(mockView).displayError("Please specify a pitcher and postion.");
    }

    @Test
    void testHandleComputerRemoveCommandTooShort() throws Exception {
        // Setup
        java.lang.reflect.Method handleComputerCommand = MLBSimulatorController.class.getDeclaredMethod("handleComputerCommand", String[].class);
        handleComputerCommand.setAccessible(true);

        // Test with remove command that's too short
        String[] command = new String[]{"computer", "remove"};
        handleComputerCommand.invoke(controllerForPrivateMethods, (Object) command);

        // Verify error message displayed
        verify(mockView).displayError("Please specify a pitcher and postion.");
    }

    @Test
    void testHandleComputerSelectTeamWithException() throws Exception {
        // Setup
        java.lang.reflect.Method handleComputerCommand = MLBSimulatorController.class.getDeclaredMethod("handleComputerCommand", String[].class);
        handleComputerCommand.setAccessible(true);

        // Mock the Teams.fromCmdName to throw an exception
        doThrow(new IllegalArgumentException("Invalid team name")).when(mockModel).setComTeam(any(Teams.class));

        // Test with invalid team name
        String[] command = new String[]{"computer", "select", "invalidteam"};
        handleComputerCommand.invoke(controllerForPrivateMethods, (Object) command);

        // Verify message displayed
        verify(mockView).displayMessage(anyString());
    }

    @Test
    void testHandleComputerShowTeamWithException() throws Exception {
        // Setup
        java.lang.reflect.Method handleComputerCommand = MLBSimulatorController.class.getDeclaredMethod("handleComputerCommand", String[].class);
        handleComputerCommand.setAccessible(true);

        // Set up the model to throw exception for invalid team
        doThrow(new IllegalArgumentException("Invalid team name")).when(mockModel).setComTeam(any(Teams.class));

        // Test with invalid team name
        String[] command = new String[]{"computer", "show", "invalidteam"};
        handleComputerCommand.invoke(controllerForPrivateMethods, (Object) command);

        // Verify message displayed
        verify(mockView).displayMessage(anyString());
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
    }

    @Test
    void testExtractCommandWithEmptyCommand() throws Exception {
        // Setup
        java.lang.reflect.Method extractCommand = MLBSimulatorController.class.getDeclaredMethod("extractCommand", String[].class);
        extractCommand.setAccessible(true);

        // Test with command that has no arguments after index 2
        String[] command = new String[]{"player", "show"};
        String result = (String) extractCommand.invoke(controllerForPrivateMethods, (Object) command);

        assertEquals("", result);
    }

    @Test
    void testExtractCommandWithMultipleWords() throws Exception {
        // Setup
        java.lang.reflect.Method extractCommand = MLBSimulatorController.class.getDeclaredMethod("extractCommand", String[].class);
        extractCommand.setAccessible(true);

        // Test with command that has multiple words
        String[] command = new String[]{"player", "show", "Mike", "Trout", "Jr."};
        String result = (String) extractCommand.invoke(controllerForPrivateMethods, (Object) command);

        assertEquals("Mike Trout Jr.", result);
    }
}

