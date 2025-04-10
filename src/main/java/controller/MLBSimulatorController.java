package controller;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import gameEnum.PlayerData;
import gameEnum.Side;
import gameEnum.Teams;
import model.Model;
import model.player.Batter;
import model.player.Pitcher;
import model.simulation.SimulationResult;
import view.TextUI;

/**
 * Controller for the MLB Simulator application
 * Handles user commands and coordinates between view and model
 */
public class MLBSimulatorController {
  private TextUI view;
  private boolean running;
  private Model model;
  private Stream<Batter> filteredBatters;
  private Stream<Pitcher> filteredPitchers;

  // used for remove commands
  private static final String PITCHER = "pitcher";
  private static final String BATTER = "batter";

  public MLBSimulatorController() {
    this.view = new TextUI();
    this.running = true;
    this.model = new Model();
    this.filteredBatters = model.getPlayerTeamBatterLoaderLineup().stream();
  }

  /**
   * Starts the application and begins handling user commands
   */
  public void start() {
    view.displayMessage("Welcome to MLB Simulator!");
    view.displayHelp();

    while (running) {
      String command = view.getCommand();
      processCommand(command);
    }

    view.close();
  }

  /**
   * Processes a user command
   * 
   * @param command The command prefix to process
   */
  private void processCommand(String command) {
    String[] parts = command.split("\\s+");

    if (parts.length == 0) {
      return;
    }

    switch (parts[0].toLowerCase()) {
      case "help":
        view.displayHelp();
        break;

      case "player":
        handlePlayerCommand(parts);
        break;

      case "computer":
        handleComputerCommand(parts);
        break;

      case "simulate":
        runSimulation();
        break;

      case "exit":
        running = false;
        view.displayMessage("Exiting MLB Simulator. Goodbye!");
        break;

      default:
        view.displayError("Unknown command. Type 'help' for available commands.");
        break;
    }
  }

  private void runSimulation() {
    SimulationResult simulationResult = model.startSimAndGetResult();
    if (simulationResult != null) {
      view.displaySimulationResult(simulationResult);
    } else {
      view.displayError("Simulation failed, make sure to set com team, batter lineup, and pitcher lineup");
    }
  }

  /**
   * Handles player-related commands
   * 
   * @param parts The command parts
   */
  private void handlePlayerCommand(String[] parts) {
    if (parts.length < 2) {
      view.displayError("Invalid player command. Type 'help' for available commands.");
      return;
    }

    switch (parts[1].toLowerCase()) {
      case "show":
        handlePlayerShowCommand(parts);
        break;

      case "add":
        if (parts.length < 3) {
          view.displayError("Invalid command. Use 'player add [name]'.");
          return;
        }

        String batterName = extractCommand(parts);
        model.addBatterToLineup(Side.PLAYER, batterName, this.filteredBatters);
        break;

      case "remove":
        if (parts.length < 3) {
          view.displayError("Invalid command. Use 'player remove [name]'.");
          return;
        }
        String batterName = extractCommand(parts);
        model.removeFromLineup(Side.PLAYER, BATTER, batterName);
        break;

      case "clear":
        model.clearLineup(Side.PLAYER, BATTER);
        view.displayMessage("Batter lineup cleared.");
        break;

      case "filter":
        handlePlayerFilterCommand(parts);
        break;

      default:
        view.displayError("Invalid player command. Type 'help' for available commands.");
        break;
    }
  }

  private void handlePlayerFilterCommand(String[] parts) {
    if (parts.length < 3) { // if no args, show current filter
      view.displayBatters(filteredBatters.toList());
      return;
    }

    // Reset the filtered batters
    if (parts[2] == "reset") {
      filteredBatters = model.getPlayerTeamBatterLoaderLineup().stream();
      view.displayMessage("Filter reset.");
      return;
    }

    // Start by reassembling the entire filter string (everything after "player
    // filter")
    StringBuilder filterBuilder = new StringBuilder();
    int i = 2; // Skip "player" and "filter"

    // Flags to track when we hit the sort keyword
    boolean foundSort = false;
    int sortIndex = -1;

    // Collect all parts until the end or until we hit "sort"
    while (i < parts.length) {
      if (parts[i].equalsIgnoreCase("sort")) {
        foundSort = true;
        sortIndex = i;
        break;
      }
      filterBuilder.append(parts[i]).append(" ");
      i++;
    }

    // Trim the trailing space
    String filterCriteria = filterBuilder.toString().trim();

    // Check if we have sort criteria
    if (foundSort && sortIndex < parts.length - 1) {
      // Collect the sort attribute - everything after "sort"
      StringBuilder sortBuilder = new StringBuilder();
      for (int j = sortIndex + 1; j < parts.length; j++) {
        sortBuilder.append(parts[j]).append(" ");
      }
      String sortAttribute = sortBuilder.toString().trim();

      // Try to get the sortOn attribute from PlayerData
      PlayerData sortOn = null;
      try {
        sortOn = PlayerData.fromColumnName(sortAttribute);
      } catch (IllegalArgumentException e) {
        view.displayMessage(e.getMessage());
        return;
      }
      // Call the method with both filter and sort
      Stream<Batter> batters = model.batterFilter(filterCriteria, sortOn,
          filteredBatters.collect(Collectors.toSet()));
      // Update filtered batters
      filteredBatters = batters;

      // Display the filtered batters
      view.displayBatters(batters.toList());
    } else {
      // Call the method with just filter
      Stream<Batter> batters = model.batterFilter(filterCriteria, model.getPlayerTeamBatterLoaderLineup());
      // Update filtered batters
      filteredBatters = batters;

      // Display the filtered batters
      view.displayBatters(batters.toList());
    }
  }

  /**
   * Handles player show commands
   * 
   * @param parts The command parts
   */
  private void handlePlayerShowCommand(String[] parts) {
    // TODO: extract the below check to method
    if (parts.length < 3) {
      view.displayError("Invalid player show command. Type 'help' for available commands.");
      return;
    }

    switch (parts[2].toLowerCase()) {
      case "lineup":
        view.displayBatters(model.getPlayerTeamBatterLineup());
        break;

      case "all":
        view.displayBatters(model.getPlayerTeamBatterLoaderLineup().stream().toList());
        break;

      default: // Assume a batter name was provided
        if (parts.length < 3) {
          view.displayMessage("Invalid player show command. Type 'help' for available commands");
          return;
        }
        String batterName = extractCommand(parts);

        Batter batter = model.getBatter(Side.PLAYER, batterName);
        if (batter != null) {
          view.displayPlayerInfo(batter);
        } else {
          view.displayError("Batter not found: " + batterName);
        }
        break;
    }
  }

  /**
   * Reconstruct the remainder of the command as a single string
   * Starts at index 2, so extracts [command] from player/computer show/add/remove
   * [command]
   * 
   * @param parts
   * @return String of batter name
   */
  private String extractCommand(String[] parts) {
    StringBuilder command = new StringBuilder();
    for (int i = 2; i < parts.length; i++) {
      command.append(parts[i]);
      if (i < parts.length - 1) {
        command.append(" ");
      }
    }
    return command.toString();

  }

  /**
   * Handles computer-related commands
   * 
   * @param parts The command parts
   */
  private void handleComputerCommand(String[] parts) {
    if (parts.length < 2) {
      view.displayError("Invalid computer command. Type 'help' for available commands.");
      return;
    }

    switch (parts[1].toLowerCase()) {
      case "show":
        if (parts.length < 3) {
          view.displayError("Please specify a team name or 'all'.");
          return;
        }

        if (parts[2].equalsIgnoreCase("teams")) {
          view.displayAllTeams(model.getAllTeamName());
          return;
        } else { // Assume a team name was provided
          String teamName = parts[2];
          Teams teamEnum = null;
          try {
            teamEnum = Teams.fromCmdName(teamName);
          } catch (IllegalArgumentException e) {
            view.displayMessage(e.getMessage());
            return;
          }
          // Show pitcher loader lineup for team
          Teams previousComTeam = Teams.fromCmdName(model.getComTeam().getTeamName());
          model.setComTeam(teamEnum); // Temporarily set com team
          view.displayPitchers(model.getComTeamPitcherLoaderLineup().stream().toList());
          model.setComTeam(previousComTeam); // Reset com team to what it was
        }
        break;

      case "select":
        if (parts.length < 3) {
          view.displayError("Please specify a team name.");
          return;
        }
        String teamName = parts[2];
        try {
          Teams comTeam = Teams.fromCmdName(teamName);
          model.setComTeam(comTeam);

          // Add pitchers for team to filterePitchers
          filteredPitchers = model.getComTeamPitcherLoaderLineup().stream();

        } catch (IllegalArgumentException e) {
          view.displayMessage(e.getMessage());
        }

        break;

      case "add":
        if (parts.length < 3) {
          view.displayError("Please specify a pitcher and postion.");
          return;
        }
        // NOTE:: Do we need error handling here?
        String command = extractCommand(parts);
        model.addBatterToLineup(Side.COMPUTER, command, filteredBatters);

      case "remove":

      default:
        view.displayError("Invalid computer command. Type 'help' for available commands.");
        break;
    }
  }
  // TODO: Add controller options to run multiple simulations
  // TODO: Add controller options to save to file.
  // TODO: Add controller options for selecting/removing pitcher lineup
}
