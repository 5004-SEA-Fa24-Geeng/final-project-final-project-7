package view;

import java.util.List;
import java.util.Scanner;

import model.simulation.*;
import model.player.*;
import model.team.*;

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
    System.out.println("  player show [name] - Show batter information for [name]");
    System.out.println("  player show lineup        - Show current lineup");
    System.out.println("  player show all           - Show all available batters");
    System.out.println("  player add [name]         - Add a batter to your team");
    System.out.println("  player remove [name]      - Remove a batter to your team");
    System.out.println("  player clear              - Clears current batter lineup");
    System.out.println(
            "  player filter [filters] sort [attribute] - Filter and sort player roster. Will show current filter if no arguments are provided. ex: player filter TotalPA >= 100, AVG >= 0.25 sort AVG");
    System.out.println("  player filter reset       - Clear current filter");

    System.out.println("\nCOMPUTER COMMANDS:");
    System.out.println("  computer show [team]    - Show available pitchers for [team]");
    System.out.println("  computer show [name]    - Show pitcher information for [name]");
    System.out.println("  computer show lineup    - Show current lineup for computer");
    System.out.println("  computer show teams     - Show selectable MLB teams");
    System.out.println("  computer select [team]  - Select a team to run simulation against");
    System.out.println(
            "  computer add [name/number] to [pos]  - Add pitcher [name/number] to position [pos]. ex: 'computer add Carlos Rodón to 1' or 'computer add 12 to 1'");
    System.out.println(
            "  computer remove [name/number/range]  - Remove pitcher [name/number/range]. ex: '1-3', 'all', 'Carlos Rodón', '1'");
    System.out.println("  computer filter [filters] sort [attribute] - Filter and sort player roster. Will show current filter if no arguments are provided. ex: player filter TotalPA >= 100, AVG >= 0.25 sort AVG");
    System.out.println("  computer filter reset      - Clear current filter");

    System.out.println("\nOTHER COMMANDS:");
    System.out.println("  help                  - Show this help message");
    System.out.println(
            "  simulate -n [number] -o [outfile] - Run [number] game simulations and write to file [outfile]. If no options are provided, 1 simulation will be run and printed to console.");
    System.out.println("  exit                  - Exit the program");
  }

  /**
   * Displays individual player information
   *
   * @param player the player to show
   */
  public void displayPlayerInfo(Player player) {
    System.out.println(player.toString());
  }

  /**
   * Displays list of player names. Can be
   * used for current lineup or all available players.
   *
   * @param players the list
   */
  public void displayBatters(List<Batter> players) {
    for (int i = 0; i < players.size(); i++) {
      // show null in lineup
      if (players.get(i) == null) {
        System.out.println((i + 1) + ". Null");
        continue;
      }
      System.out.println((i + 1) + ". " + players.get(i).getName());
    }
  }

  /**
   * Displays list of player names. Can be
   * used for current lineup or all available players.
   *
   * @param players the list of players to display
   */
  public void displayPitchers(List<Pitcher> players) {
    for (int i = 0; i < players.size(); i++) {
      // show null in lineup
      if (players.get(i) == null) {
        System.out.println((i + 1) + ". Null");
        continue;
      }
      System.out.println((i + 1) + ". " + players.get(i).getName());
    }
  }

  /**
   * Displays a list of teams
   *
   * @param teams List of team names to display
   */
  public void displayAllTeams(List<String> teams) {
    System.out.println("\n===== MLB TEAMS =====");
    for (int i = 0; i < teams.size(); i++) {
      System.out.println((i + 1) + ". " + teams.get(i));
    }
  }

  /**
   * Displays simulation results
   *
   * @param details Game details
   */
  public void displaySimulationResult(SimulationResult simulationResult) {
    String details = simulationResult.getDetails();
    String totalStatistics = simulationResult.getTotalStatistics();
    String toString = simulationResult.toString();

    System.out.println(toString);
    System.out.println(details);
    System.out.println(totalStatistics);
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
