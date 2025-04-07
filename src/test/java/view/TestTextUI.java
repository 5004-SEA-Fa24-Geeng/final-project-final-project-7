package view;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import view.stubs.PlayerStub;
import view.stubs.SimulationResultStub;
import view.stubs.TeamStub;

public class TestTextUI {

  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;
  private final ByteArrayInputStream inContent = new ByteArrayInputStream("".getBytes());
  private final java.io.InputStream originalIn = System.in;

  private TextUI textUI;
  private AutoCloseable closeable;

  @Mock
  private PlayerStub playerStub1;

  @Mock
  private PlayerStub playerStub2;

  @Mock
  private TeamStub teamStub;

  @Mock
  private SimulationResultStub simulationResultStub;

  @BeforeEach
  public void setUp() {
    closeable = MockitoAnnotations.openMocks(this);

    // Redirect System.out to capture output
    System.setOut(new PrintStream(outContent));

    // Set up mocks
    when(playerStub1.getName()).thenReturn("Mike Trout");
    when(playerStub1.toString()).thenReturn("Mike Trout | AVG: .312 | HR: 42 | RBI: 104");

    when(playerStub2.getName()).thenReturn("Julio Rodriguez");
    when(playerStub2.toString()).thenReturn("Julio Rodriguez | AVG: .289 | HR: 32 | RBI: 91");

    List<PlayerStub> players = Arrays.asList(playerStub1, playerStub2);

    when(teamStub.getName()).thenReturn("Mariners");
    when(teamStub.getPlayers()).thenReturn(players);
    when(teamStub.getStats()).thenReturn("Wins: 92\nLosses: 70\nTeam AVG: .258\nTeam ERA: 3.75");

    when(simulationResultStub.getMarinersScore()).thenReturn(5);
    when(simulationResultStub.getOpponentScore()).thenReturn(3);
    when(simulationResultStub.getDetails()).thenReturn("Game highlights...");

    // Initialize TextUI
    textUI = new TextUI();
  }

  @AfterEach
  public void tearDown() throws Exception {
    System.setOut(originalOut);
    System.setIn(originalIn);
    closeable.close();
  }

  @Test
  public void testDisplayHelp() {
    // Act
    textUI.displayHelp();

    // Assert
    String output = outContent.toString();
    assertTrue(output.contains("MLB SIMULATOR COMMANDS"));
    assertTrue(output.contains("player show batter"));
    assertTrue(output.contains("simulate"));
    assertTrue(output.contains("exit"));
  }

  @Test
  public void testDisplayPlayerInfo() {
    // Act
    textUI.displayPlayerInfo(playerStub1);

    // Assert
    assertEquals("Mike Trout | AVG: .312 | HR: 42 | RBI: 104" + System.lineSeparator(),
        outContent.toString());
  }

  @Test
  public void testDisplayPlayers() {
    // Arrange
    List<PlayerStub> players = Arrays.asList(playerStub1, playerStub2);

    // Act
    textUI.displayPlayers(players);

    // Assert
    String output = outContent.toString();
    assertTrue(output.contains("1. Mike Trout"));
    assertTrue(output.contains("2. Julio Rodriguez"));
  }

  @Test
  public void testDisplayAllTeams() {
    // Arrange
    List<TeamStub> teams = new ArrayList<>();
    TeamStub team1 = mock(TeamStub.class);
    TeamStub team2 = mock(TeamStub.class);
    when(team1.getName()).thenReturn("Mariners");
    when(team2.getName()).thenReturn("Yankees");
    teams.add(team1);
    teams.add(team2);

    // Act
    textUI.displayAllTeams(teams);

    // Assert
    String output = outContent.toString();
    assertTrue(output.contains("MLB TEAMS"));
    assertTrue(output.contains("1. Mariners"));
    assertTrue(output.contains("2. Yankees"));
  }

  @Test
  public void testDisplayTeamInfo() {
    // Act
    textUI.displayTeamInfo(teamStub);

    // Assert
    String output = outContent.toString();
    assertTrue(output.contains("TEAM: Mariners"));
    assertTrue(output.contains("Mike Trout"));
    assertTrue(output.contains("Julio Rodriguez"));
    assertTrue(output.contains("Wins: 92"));
  }

  @Test
  public void testDisplayTeamInfoEmptyRoster() {
    // Arrange
    TeamStub emptyTeam = mock(TeamStub.class);
    when(emptyTeam.getName()).thenReturn("Empty Team");
    when(emptyTeam.getPlayers()).thenReturn(new ArrayList<>());
    when(emptyTeam.getStats()).thenReturn("No stats available");

    // Act
    textUI.displayTeamInfo(emptyTeam);

    // Assert
    String output = outContent.toString();
    assertTrue(output.contains("TEAM: Empty Team"));
    assertTrue(output.contains("No players on roster."));
  }

  @Test
  public void testDisplaySimulationResult() {
    // Act
    textUI.displaySimulationResult(simulationResultStub);

    // Assert
    String output = outContent.toString();
    assertTrue(output.contains("SIMULATION RESULTS"));
    assertTrue(output.contains("Mariners : 5"));
    assertTrue(output.contains("Away Team : 3"));
    assertTrue(output.contains("Game highlights..."));
  }

  @Test
  public void testGetCommand() {
    // Arrange
    String testInput = "help\n";
    System.setIn(new ByteArrayInputStream(testInput.getBytes()));
    TextUI localTextUI = new TextUI(); // Need a new instance with the new System.in

    // Act
    String result = localTextUI.getCommand();

    // Assert
    assertEquals("help", result);
  }

  @Test
  public void testGetInput() {
    // Arrange
    String testInput = "Yankees\n";
    System.setIn(new ByteArrayInputStream(testInput.getBytes()));
    TextUI localTextUI = new TextUI(); // Need a new instance with the new System.in

    // Act
    String result = localTextUI.getInput("Enter team name:");

    // Assert
    assertEquals("Yankees", result);
    assertTrue(outContent.toString().contains("Enter team name:"));
  }

  @Test
  public void testDisplayMessage() {
    // Act
    textUI.displayMessage("Test message");

    // Assert
    assertEquals("Test message" + System.lineSeparator(), outContent.toString());
  }

  @Test
  public void testDisplayError() {
    // Act
    textUI.displayError("Invalid command");

    // Assert
    assertEquals("ERROR: Invalid command" + System.lineSeparator(), outContent.toString());
  }
}
