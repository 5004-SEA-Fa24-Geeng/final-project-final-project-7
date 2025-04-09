package view;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import view.stubs.PlayerStub;
import view.stubs.SimulationResultStub;
import view.stubs.TeamStub;

public class TestTextUI {

  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;
  private final java.io.InputStream originalIn = System.in;

  private TextUI textUI;

  // Manual mocks instead of Mockito
  private PlayerStub playerStub1;
  private PlayerStub playerStub2;
  private TeamStub teamStub;
  private SimulationResultStub simulationResultStub;

  @BeforeEach
  public void setUp() {
    // Redirect System.out to capture output
    System.setOut(new PrintStream(outContent));

    // Set up manual mocks
    playerStub1 = new MockPlayer("Mike Trout", "Mike Trout | AVG: .312 | HR: 42 | RBI: 104");
    playerStub2 = new MockPlayer("Julio Rodriguez", "Julio Rodriguez | AVG: .289 | HR: 32 | RBI: 91");

    List<PlayerStub> players = Arrays.asList(playerStub1, playerStub2);
    teamStub = new MockTeam("Mariners", players, "Wins: 92\nLosses: 70\nTeam AVG: .258\nTeam ERA: 3.75");

    simulationResultStub = new MockSimulationResult(5, 3, "Game highlights...");

    // Initialize TextUI
    textUI = new TextUI();
  }

  @AfterEach
  public void tearDown() {
    System.setOut(originalOut);
    System.setIn(originalIn);
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
    teams.add(new MockTeam("Mariners", new ArrayList<>(), ""));
    teams.add(new MockTeam("Yankees", new ArrayList<>(), ""));

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
    TeamStub emptyTeam = new MockTeam("Empty Team", new ArrayList<>(), "No stats available");

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
    assertTrue(output.contains("===== SIMULATION RESULTS ====="));
    assertTrue(output.contains("Mariners : 5"));
    assertTrue(output.contains("Away Team : 3"));
    assertTrue(output.contains("Game Details:"));
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

  // Simple mock implementations

  private static class MockPlayer implements PlayerStub {
    private String name;
    private String stringRepresentation;

    public MockPlayer(String name, String stringRepresentation) {
      this.name = name;
      this.stringRepresentation = stringRepresentation;
    }

    @Override
    public String getName() {
      return name;
    }

    @Override
    public String toString() {
      return stringRepresentation;
    }
  }

  private static class MockTeam implements TeamStub {
    private String name;
    private List<PlayerStub> players;
    private String stats;

    public MockTeam(String name, List<PlayerStub> players, String stats) {
      this.name = name;
      this.players = players;
      this.stats = stats;
    }

    @Override
    public String getName() {
      return name;
    }

    @Override
    public List<PlayerStub> getPlayers() {
      return players;
    }

    @Override
    public String getStats() {
      return stats;
    }
  }

  private static class MockSimulationResult implements SimulationResultStub {
    private int marinersScore;
    private int opponentScore;
    private String details;

    public MockSimulationResult(int marinersScore, int opponentScore, String details) {
      this.marinersScore = marinersScore;
      this.opponentScore = opponentScore;
      this.details = details;
    }

    @Override
    public int getMarinersScore() {
      return marinersScore;
    }

    @Override
    public int getOpponentScore() {
      return opponentScore;
    }

    @Override
    public String getDetails() {
      return details;
    }
  }
}
