package controller;

import java.util.*;
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
  private List<Batter> filteredBatters;
  private List<Pitcher> filteredPitchers;

  // used for remove commands
  private static final String PITCHER = "pitcher";
  private static final String BATTER = "batter";

  public MLBSimulatorController() {
    this.view = new TextUI();
    this.running = true;
    this.model = new Model();
    this.model.setPlayerTeam();
    this.filteredBatters = model.getPlayerTeamBatterLoaderLineup().stream().toList();
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

    // Exit if no command is provided
    if (parts.length == 1 && parts[0].isEmpty()) {
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
        runSimulation(parts);
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

  // TODO: Add controller options to run multiple simulations
  // TODO: Add controller options to save to file.

  private void runSimulation(String[] parts) {
    Map<String, String> simulateOptions = parseSimulateOptions(parts);

    // Get number of simulations and outfile. Default 1 and null if not provided
    int numberOfSimulations;
    String outfile = simulateOptions.get("outfile");

    // Safe type casting of integer
    try {
      numberOfSimulations = Integer.parseInt(simulateOptions.get("number"));
    } catch (NumberFormatException e) {
      view.displayError("Invalid number of simulations: " + simulateOptions.get("number"));
      return;
    }

    SimulationResult simulationResult = null;

    if (outfile == null) {
      for (int i = 0; i < numberOfSimulations; i++) {
        simulationResult = model.startSimAndGetResult();
        if (simulationResult != null) {
          view.displaySimulationResult(simulationResult);
        } else {
          view.displayError("Simulation failed, make sure to set com team, batter lineup, and pitcher lineup");
        }
      }

    } else {
      for (int i = 0; i < numberOfSimulations; i++) {
        simulationResult = model.startSimAndGetResult();
        if (simulationResult != null) {
          view.displaySimulationResult(simulationResult);
          outfile = outfile + "_" + String.valueOf(i + 1);
          model.saveGameDetailsAsTXTFile(outfile);
        } else {
          view.displayError("Simulation failed, make sure to set com team, batter lineup, and pitcher lineup");
        }
      }
    }

  }

  /**
   * Parses the simulate command options
   * Handles: simulate -n [number] -o [outfile]
   * 
   * @param commandParts The command split into parts
   * @return A map containing the extracted option values
   */
  private Map<String, String> parseSimulateOptions(String[] commandParts) {
    Map<String, String> options = new HashMap<>();

    // Default values
    options.put("number", "1"); // Default to 1 simulation
    options.put("outfile", null); // Default to no outfile (print to console)

    // Skip the first part ("simulate")
    for (int i = 1; i < commandParts.length; i++) {
      String part = commandParts[i];

      if (part.equals("-n") && i + 1 < commandParts.length) {
        // Extract number value
        options.put("number", commandParts[i + 1]);
        i++; // Skip the next part since we've processed it
      } else if (part.equals("-o") && i + 1 < commandParts.length) {
        // Extract outfile value
        options.put("outfile", commandParts[i + 1]);
        i++; // Skip the next part since we've processed it
      }
    }

    return options;
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

    String batterName = null;
    switch (parts[1].toLowerCase()) {
      case "show":
        handlePlayerShowCommand(parts);
        break;

      case "add":
        if (parts.length < 3) {
          view.displayError("Invalid command. Use 'player add [name]'.");
          return;
        }

        batterName = extractCommand(parts);
        model.addBatterToLineup(Side.PLAYER, batterName, this.filteredBatters.stream());
        break;

      case "remove":
        if (parts.length < 3) {
          view.displayError("Invalid command. Use 'player remove [name]'.");
          return;
        }
        batterName = extractCommand(parts);
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
      view.displayBatters(filteredBatters);
      return;
    }

    // Reset the filtered batters
    if (Objects.equals(parts[2], "reset")) {
      filteredBatters = model.getPlayerTeamBatterLoaderLineup().stream().toList();
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
              new HashSet<>(filteredBatters));
      // Update filtered batters
      filteredBatters = batters.toList();

      // Display the filtered batters
      view.displayBatters(filteredBatters);
    } else {
      // Call the method with just filter
      Stream<Batter> batters = model.batterFilter(filterCriteria, new HashSet<>(filteredBatters));
      // Update filtered batters
      filteredBatters = batters.toList();

      // Display the filtered batters
      view.displayBatters(filteredBatters);
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

    String command = null;

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

          // Add pitchers for team to filteredPitchers
          filteredPitchers = model.getComTeamPitcherLoaderLineup().stream().toList();

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
        command = extractCommand(parts);
        model.addBatterToLineup(Side.COMPUTER, command, filteredBatters.stream());

        break;

      case "remove":
        if (parts.length < 3) {
          view.displayError("Please specify a pitcher and postion.");
          return;
        }

        command = extractCommand(parts);
        model.removeFromLineup(Side.COMPUTER, PITCHER, command);
        break;

      default:
        view.displayError("Invalid computer command. Type 'help' for available commands.");
        break;
    }
  }
}
