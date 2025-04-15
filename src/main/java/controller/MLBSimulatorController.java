package controller;

import gameEnum.PlayerData;
import gameEnum.Side;
import gameEnum.Teams;
import model.Model;
import model.player.Batter;
import model.player.Pitcher;
import model.simulation.SimulationResult;
import view.TextUI;

import java.util.*;
import java.util.stream.Stream;

/**
 * Controller for the MLB Simulator application
 * Handles user commands and coordinates between view and model
 */
public class MLBSimulatorController {
    private final TextUI view;
    private boolean running;
    private final Model model;
    private List<Batter> filteredBatters;
    private List<Pitcher> filteredPitchers;

    // used for computer remove commands
    private static final String PITCHER = "pitcher";
    private static final String BATTER = "batter";

    public MLBSimulatorController() {
        this.view = new TextUI();
        this.running = true;
        this.model = new Model();
        this.model.setPlayerTeam();
        this.filteredBatters = model.getPlayerTeamBatterLoaderLineup().stream().toList();
        this.filteredPitchers = null; // cannot be instantiated before a team is selected.
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

    /**
     * Handles simulation command
     *
     * @param parts the command string
     */
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
                    outfile = outfile + "_" + (i + 1);
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

        String command = null;
        switch (parts[1].toLowerCase()) {
            case "show":
                handlePlayerShowCommand(parts);
                break;

            case "add":
                if (parts.length < 3) {
                    view.displayError("Invalid command. Use 'player add [name/number] to [number]'.");
                    return;
                }

                command = extractCommand(parts);
                try {
                    model.addBatterToLineup(Side.PLAYER, command, this.filteredBatters.stream());
                } catch (IllegalArgumentException e) {
                    view.displayError(e.getMessage());
                }
                break;

            case "remove":
                if (parts.length < 3) {
                    view.displayError("Invalid command. Use 'player remove [name]'.");
                    return;
                }
                command = extractCommand(parts);
                model.removeFromLineup(Side.PLAYER, BATTER, command);
                break;

            case "clear":
                model.clearLineup(Side.PLAYER, BATTER);
                view.displayMessage("Batter lineup cleared.");
                break;

            case "filter":
                handleFilterCommand(parts, Side.PLAYER);
                break;

            default:
                view.displayError("Invalid player command. Type 'help' for available commands.");
                break;
        }
    }

    /**
     * Handles player show commands
     *
     * @param parts The command parts
     */
    private void handlePlayerShowCommand(String[] parts) {
        if (parts.length < 3) {
            view.displayError("Invalid player show command. Type 'help' for available commands.");
            return;
        }

        switch (parts[2].toLowerCase()) {
            case "lineup":
                view.displayBatters(model.getPlayerTeamBatterLineup());
                break;

            case "attributes":
                view.displayListOfStrings(model.getAllColumnName());
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
     * @param parts command string array
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
            // TODO: extract show into separate method
            case "show":
                if (parts.length < 3) {
                    view.displayError("Please specify a team name, pitcher name or 'all'.");
                    return;
                }

                if (parts[2].equalsIgnoreCase("teams")) {
                    view.displayAllTeams(model.getAllTeamName());
                    return;
                } else if (parts[2].equalsIgnoreCase("attributes")) {
                    view.displayListOfStrings(model.getAllColumnName());
                } else if (parts[2].equalsIgnoreCase("lineup")) { // command: computer show lineup
                    if (model.getComTeam() == null) {
                        view.displayError("Please select a team first.");
                        return;
                    }
                    view.displayPitchers(model.getComTeamPitcherLineup());
                } else if (parts.length > 3) { // If length is greater than three we assume pitcher name was provided
                    String pitcherName = extractCommand(parts);

                    Pitcher pitcher = model.getPitcher(Side.COMPUTER, pitcherName);
                    if (pitcher != null) {
                        view.displayPlayerInfo(pitcher);
                    } else {
                        view.displayError("Pitcher not found: " + pitcherName);
                    }
                    break;
                } else { // Otherwise we assume a team name was provided
                    String teamName = parts[2];
                    Teams teamEnum = null;
                    try {
                        teamEnum = Teams.fromCmdName(teamName);
                    } catch (IllegalArgumentException e) {
                        view.displayMessage(e.getMessage());
                        return;
                    }
                    // Show pitcher loader lineup for team
                    if (model.getComTeam() == null) { // if user hasn't selected a team
                        model.setComTeam(teamEnum);
                        this.filteredPitchers = model.getComTeamPitcherLoaderLineup().stream().toList();
                        view.displayPitchers(this.filteredPitchers);
                    } else { // if user has selected a team, set back to original loader lineup after showing what they requested
                        Teams previousComTeam = Teams.fromCmdName(model.getComTeam().getTeamName());
                        model.setComTeam(teamEnum); // Temporarily set com team
                        view.displayPitchers(model.getComTeamPitcherLoaderLineup().stream().toList());
                        model.setComTeam(previousComTeam); // Reset com team to what it was
                    }
                }
                break;
            // NOTE: print a message for this
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
                    view.displayError("Please specify a pitcher and position.");
                    return;
                }
                if (model.getComTeam() == null) {
                    view.displayError("Please select a team first.");
                    return;
                }
                command = extractCommand(parts);

                try {
                    model.addPitcherToLineup(Side.COMPUTER, command, this.filteredPitchers.stream());
                } catch (IllegalArgumentException e) {
                    view.displayMessage(e.getMessage());
                }

                break;

            case "remove":
                if (parts.length < 3) {
                    view.displayError("Please specify a pitcher and postion.");
                    return;
                }

                command = extractCommand(parts);
                model.removeFromLineup(Side.COMPUTER, PITCHER, command);
                break;

            case "filter":
                if (model.getComTeam() == null) {
                    view.displayError("Please select a team first.");
                    return;
                }
                handleFilterCommand(parts, Side.COMPUTER);
                break;

            default:
                view.displayError("Invalid computer command. Type 'help' for available commands.");
                break;
        }
    }

    /**
     * Handles filter commands for both player and computer
     *
     * @param parts the command string array
     */
    private void handleFilterCommand(String[] parts, Side side) {
        // Set local player type flag
        boolean playerSide = side == Side.PLAYER;

        // If no args, show current filter
        if (parts.length < 3) {
            displayCurrentFilter(playerSide);
            return;
        }

        // Reset the filtered players if reset command
        if (Objects.equals(parts[2], "reset")) {
            resetFilter(playerSide);
            return;
        }

        // Parse filter and sort criteria
        FilterCriteria criteria = parseFilterCriteria(parts);

        if (criteria.hasSort() && criteria.sortAttribute != null) {
            applyFilterAndSort(playerSide, criteria.filterString, criteria.sortAttribute, criteria.sortAsc);
        } else {
            applyFilterOnly(playerSide, criteria.filterString);
        }
    }

    /**
     * Applies filter to batters or pitchers
     *
     * @param playerSide   indicates batters or pitchers
     * @param filterString the filter string being passed to the model
     */
    private void applyFilterOnly(boolean playerSide, String filterString) {
        if (playerSide) {
            // NOTE: additional error handling occurs in model
            Stream<Batter> batters = model.batterFilter(filterString, new HashSet<>(filteredBatters));

            // Update filtered batters
            filteredBatters = batters.toList();

            // Reset filter if it resulted in empty list
            if (isEmpty(filteredBatters)) {
                filteredBatters = model.getPlayerTeamBatterLoaderLineup().stream().toList();
                return;
            }

            // Display the filtered batters
            view.displayBatters(filteredBatters);
        } else {
            // NOTE: additional error handling occurs in model
            Stream<Pitcher> pitchers = model.pitcherFilter(filterString, new HashSet<>(filteredPitchers));

            // Update filtered pitchers
            filteredPitchers = pitchers.toList();

            // Reset filter if it resulted in empty list
            if (isEmpty(filteredPitchers)) {
                filteredPitchers = model.getComTeamPitcherLoaderLineup().stream().toList();
                return;
            }
            // Display the filtered pitchers
            view.displayPitchers(filteredPitchers);
        }
    }

    /**
     * Filters and sorts batters or pitchers
     *
     * @param playerSide    indicates batters or pitchers
     * @param filterString  the filter string that gets passed to the model
     * @param sortAttribute the player attribute to sort on
     */
    private void applyFilterAndSort(boolean playerSide, String filterString, PlayerData sortAttribute, boolean sortAsc) {
        if (playerSide) {
            // NOTE: additional error handling occurs in model
            Stream<Batter> batters = model.batterFilter(filterString, sortAttribute, sortAsc,
                    new HashSet<>(filteredBatters));

            // Update filtered batters
            filteredBatters = batters.toList();

            // Reset filter if it resulted in empty list
            // TODO: extract this Reset Filter to method
            if (isEmpty(filteredBatters)) {
                filteredBatters = model.getPlayerTeamBatterLoaderLineup().stream().toList();
                return;
            }

            // Display the filtered batters
            view.displayBatters(filteredBatters);
        } else {
            // NOTE: additional error handling occurs in model
            Stream<Pitcher> pitchers = model.pitcherFilter(filterString, sortAttribute, sortAsc,
                    new HashSet<>(filteredPitchers));

            // Update filtered pitchers
            filteredPitchers = pitchers.toList();

            // Reset filter if it resulted in empty list
            if (isEmpty(filteredPitchers)) {
                filteredPitchers = model.getComTeamPitcherLoaderLineup().stream().toList();
                return;
            }

            // Display the filtered pitchers
            view.displayPitchers(filteredPitchers);
        }
    }

    /**
     * Displays currently filtered pitchers or batters
     *
     * @param playerSide indicates batters or pitchers
     */
    private void displayCurrentFilter(boolean playerSide) {
        if (playerSide) {
            if (isEmpty(filteredBatters)) return;
            view.displayBatters(filteredBatters);
        } else {
            if (isEmpty(filteredPitchers)) return;
            view.displayPitchers(filteredPitchers);
        }
    }

    /**
     * Checks if a list is empty and displays an appropriate message if it is
     *
     * @param <T>  the type of elements in the list
     * @param list the list to check
     * @return true if the list is empty, false otherwise
     */
    private <T> boolean isEmpty(List<T> list) {
        if (list == null || list.isEmpty()) {
            view.displayMessage("No items to display.");
            return true;
        }
        return false;
    }

    /**
     * Resets the filtered batters or pitchers to the full roster
     *
     * @param playerSide indicates batters or pitchers
     */
    private void resetFilter(boolean playerSide) {
        if (playerSide) {
            filteredBatters = model.getPlayerTeamBatterLoaderLineup().stream().toList();
        } else {
            filteredPitchers = model.getComTeamPitcherLoaderLineup().stream().toList();
        }
        view.displayMessage("Filter reset.");
    }

    /**
     * Parses filter strings
     *
     * @param parts the command parts
     * @return filter and sort criteria in FilterCriteria object
     */
    private FilterCriteria parseFilterCriteria(String[] parts) {
        FilterCriteria result = new FilterCriteria();

        // Start by reassembling the entire filter string (everything after "player/computer
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

        // Trim the trailing space and set in result
        result.filterString = filterBuilder.toString().trim();

        // Check if we have sort criteria
        if (foundSort && sortIndex < parts.length - 1) {
            result.hasSort = true;
            result.sortAsc = true; // Default to sort ascending

            // Collect the sort attribute - everything after "sort"
            StringBuilder sortBuilder = new StringBuilder();
            for (int j = sortIndex + 1; j < parts.length; j++) {
                if (parts[j].equalsIgnoreCase("desc")) {
                    result.sortAsc = false;
                    continue;
                } else if (parts[j].equalsIgnoreCase("asc")) {
                    continue;
                }
                sortBuilder.append(parts[j]).append(" ");
            }
            String sortAttribute = sortBuilder.toString().trim();

            // Try to get the sortOn attribute from PlayerData
            try {
                // use fromString instead of fromColumnName
                // cause sortOn needs to be case insensitive
                // ex: "totalh" == "TotalH"
                result.sortAttribute = PlayerData.fromString(sortAttribute);
            } catch (IllegalArgumentException e) {
                view.displayMessage(e.getMessage());
                result.hasSort = false; // Reset this since we couldn't parse the sort attribute
            }
        }
        return result;
    }
}
