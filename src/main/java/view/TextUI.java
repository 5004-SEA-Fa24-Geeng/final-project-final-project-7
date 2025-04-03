package view;

import java.util.List;
import java.util.Scanner;

/**
 * Text-based user interface for the MLB Simulator application
 * Uses a command-based approach for interacting with the system
 */
public class TextUI {
  private Scanner scanner;

  public TextUI() {
    this.scanner = new Scanner(System.in);
  }

  /**
   * Displays the help message showing all available commands
   */
  public void displayHelp() {
    System.out.println("\n===== MLB SIMULATOR COMMANDS =====");
    System.out.println("PLAYER COMMANDS:");
    System.out.println("  player show batter [name] - Show batter information");
    System.out.println("  player show lineup        - Show current lineup");
    System.out.println("  player show all           - Show all available batters");
    System.out.println("  player add batter         - Add a batter to your team");
    System.out.println("  player remove batter      - Remove a batter to your team");
    System.out.println("  player clear              - Clears current batter lineup");
    System.out.println("  player filter [attribute] [value] [sort] [attribute] - Filter and sort player roster");

    System.out.println("\nCOMPUTER COMMANDS:");
    System.out.println("  computer show [team]    - Show opponent team information");
    System.out.println("  computer show all       - Show all opponent team information");
    System.out.println("  computer select [team]  - Select a team to run simulation against");

    System.out.println("\nOTHER COMMANDS:");
    System.out.println("  help                  - Show this help message");
    System.out.println("  simulate              - Run a game simulation");
    System.out.println("  exit                  - Exit the program");
  }

  /**
   * Displays individual player information
   *
   * @param player
   */
  public void displayPlayerInfo(DisplayablePlayer player) {
    System.out.println(player.toString());
  }

  /**
   * Displays list of player names. Can be
   * used for current lineup or all available players.
   *
   * @param players
   */
  public void displayPlayers(List<DisplayablePlayer> players) {
    for (int i = 0; i < players.size(); i++) {
      System.out.println((i + 1) + ". " + players.get(i).getName());
    }
  }

  /**
   * Displays a list of teams
   * 
   * @param teams List of team names to display
   */
  public void displayAllTeams(List<DisplayableTeam> teams) {
    System.out.println("\n===== MLB TEAMS =====");
    for (int i = 0; i < teams.size(); i++) {
      System.out.println((i + 1) + ". " + teams.get(i).getName());
    }
  }

  /**
   * Displays team information
   * 
   * @param teamName The name of the team
   * @param players  List of players on the team
   * @param stats    Team statistics
   */
  public void displayTeamInfo(DisplayableTeam team) {
    String teamName = team.getName();
    List<DisplayablePlayer> players = team.getPlayers();
    String stats = team.getStats();

    System.out.println("\n===== TEAM: " + teamName + " =====");
    System.out.println("\nROSTER:");
    if (players.isEmpty()) {
      System.out.println("No players on roster.");
    } else {
      for (DisplayablePlayer player : players) {
        System.out.println("- " + player);
      }
    }

    System.out.println("\nTEAM STATS:");
    System.out.println(stats);
  }

  /**
   * Displays simulation results
   * 
   * @param homeTeam  Home team name
   * @param awayTeam  Away team name
   * @param homeScore Home team score
   * @param awayScore Away team score
   * @param details   Game details
   */
  public void displaySimulationResult(DisplayableSimulationResult simulationResult) {
    int homeScore = simulationResult.getHomeScore();
    int awayScore = simulationResult.getAwayScore();
    String details = simulationResult.getDetails();

    System.out.println("\n===== SIMULATION RESULTS =====");
    System.out.println("Mariners : " + homeScore);
    System.out.println("Away Team : " + awayScore);

    System.out.println("\nGame Details:");
    System.out.println(details);
  }

  /**
   * Gets the next command from the user
   * 
   * @return The user's command as a string
   */
  public String getCommand() {
    System.out.print("\nEnter command: ");
    return scanner.nextLine().trim();
  }

  /**
   * Gets user input as a string
   * 
   * @param prompt The prompt to display to the user
   * @return User's input as a string
   */
  public String getInput(String prompt) {
    System.out.print(prompt + " ");
    return scanner.nextLine().trim();
  }

  /**
   * Displays a message to the user
   * 
   * @param message The message to display
   */
  public void displayMessage(String message) {
    System.out.println(message);
  }

  /**
   * Displays an error message to the user
   * 
   * @param message The error message to display
   */
  public void displayError(String message) {
    System.out.println("ERROR: " + message);
  }

  /**
   * Closes the scanner
   */
  public void close() {
    if (scanner != null) {
      scanner.close();
    }
  }
}
