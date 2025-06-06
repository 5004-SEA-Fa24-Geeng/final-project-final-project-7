package controller;

import gameEnum.PlayerData;
import gameEnum.Side;
import gameEnum.Teams;
import model.Model;
import model.player.Batter;
import model.player.Pitcher;
import model.simulation.SimulationResult;
import view.TextUI;
import view.UIInterface;

import java.util.*;
import java.util.stream.Stream;

/**
 * Controller for the MLB Simulator application
 * Handles user commands and coordinates between view and model
 */
public class MLBSimulatorController implements MLBSimulatorControllerInterface {
    private final UIInterface view; // The view for the application
    private boolean running; // The running flag for the controller
    private final Model model; // The model instance
    private List<Batter> filteredBatters; // The current list of filtered batters
    private List<Pitcher> filteredPitchers; // The current list of filtered pitchers

    // used for computer remove commands
    private static final String PITCHER = "pitcher";
    private static final String BATTER = "batter";

    public MLBSimulatorController(UIInterface view) {
        this.view = new TextUI();
        this.running = true;
        this.model = new Model();
        this.model.setPlayerTeam();
        this.filteredBatters = model.getPlayerTeamBatterLoaderLineup().stream().toList();
        this.filteredPitchers = null; // cannot be instantiated before a team is selected.
    }

    // No-arg constructor that defaults to TextUI
    public MLBSimulatorController() {
        this(new TextUI());
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
     * Stops the controller loop
     */
    @Override
    public void stop() {
        running = false;
        view.displayMessage("Exiting MLB Simulator. Goodbye!");
        view.close();
    }

    /**
     * Processes a user command
     *
     * @param command The command prefix to process
     */
    private void processCommand(String command) {
        // Trim the command first
        command = command.trim();

        // Exit immediately if the command is empty after trimming
        if (command.isEmpty()) {
            return;
        }

        String[] parts = command.split("\\s+");


        // Exit if no command is provided
        if (parts.length == 0) {
            return;
        }

        // Check for illegal characters in command
        for (String part : parts) {
            if (containsIllegalCharacters(part)) {
                view.displayMessage("Illegal character in command: " + part);
                return;
            }
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
     * Checks if the command string contains any illegal characters.
     * Allowed characters include alphanumeric characters, whitespace,
     * and specific operators (<, >, =, !, ~, ., ,)
     *
     * @param command The command string to validate
     * @return true if illegal characters are found, false otherwise
     */
    private boolean containsIllegalCharacters(String command) {
        // Define the pattern of allowed characters:
        // - Alphanumeric characters (a-z, A-Z, 0-9)
        // - Whitespace characters
        // - Operators (<, >, =, !, ~)
        // - Other allowed special characters (., ,, -)
        String allowedPattern = "^[a-zA-Z0-9\\s<>=!~.,-]*$";

        // Return true if the command contains characters NOT in the allowed pattern
        return !command.matches(allowedPattern);
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
                    String formattedFilename = formatOutputFilename(outfile, i + 1);
                    model.saveGameDetailsAsTXTFile(formattedFilename);
                } else {
                    view.displayError("Simulation failed, make sure to set com team, batter lineup, and pitcher lineup");
                }
            }
        }

    }

    /**
     * Formats the output filename by removing any existing file extension,
     * appending a sequence number, and then adding the .txt extension
     *
     * @param filename       The original filename provided by the user
     * @param sequenceNumber The simulation sequence number to append
     * @return The properly formatted filename (e.g., "filename_1.txt")
     */
    private String formatOutputFilename(String filename, int sequenceNumber) {
        // Remove any existing file extension (like .txt, .csv, etc.)
        String baseFilename = filename.replaceAll("\\.[^.]*$", "");

        // Append the sequence number and the .txt extension
        return baseFilename + "_" + sequenceNumber + ".txt";
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

                // Validate and extract add command
                AddCommandInfo cmdInfo = validateAddToCommand(command, true);

                // Add player
                addPlayer(cmdInfo, true, command);

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
     * Validates a command that follows either "[name] to [number]" or "[number] to [number]" patterns
     *
     * @param command    The command string to validate
     * @param playerSide True if checking batter list, false if checking pitcher list
     * @return An object containing parsed command information
     */
    private AddCommandInfo validateAddToCommand(String command, boolean playerSide) {
        AddCommandInfo result = new AddCommandInfo();

        // Check if command follows "X to Y" pattern
        if (!command.toLowerCase().contains(" to ")) {
            // This is not a pattern with "to" in it
            result.isValid = false;
            return result;
        }

        String[] parts = command.toLowerCase().split(" to ", 2);
        String identifier = parts[0].trim();
        String positionStr = parts[1].trim();

        // Try to parse position - must be a number
        try {
            int position = Integer.parseInt(positionStr);

            // Validate position range (assuming positions are 1-based)
            if (position < 1 || position > 9) { // Adjust max if needed
                view.displayError("Position out of range. Please enter a number between 1 and 9.");
                result.isValid = false;
                return result;
            }

            result.position = position;
        } catch (NumberFormatException e) {
            view.displayError("Invalid position: " + positionStr + ". Please enter a number.");
            result.isValid = false;
            return result;
        }

        // Try to parse the identifier as a number
        try {
            int playerIndex = Integer.parseInt(identifier);

            // It's a number, so validate the index
            int adjustedIndex = playerIndex - 1; // Convert from 1-based to 0-based

            // Validate the player index is within range
            List<?> currentList = playerSide ? filteredBatters : filteredPitchers;
            if (currentList == null) {
                view.displayError(playerSide ?
                        "No batters available." :
                        "No pitchers available. Please select a team first.");
                result.isValid = false;
                return result;
            }

            if (adjustedIndex < 0 || adjustedIndex >= currentList.size()) {
                view.displayError("Player index out of range. Please enter a number between 1 and " +
                        currentList.size() + ".");
                result.isValid = false;
                return result;
            }

            // Store parsed info
            result.isIndex = true;
            result.playerIndex = adjustedIndex;
            result.isValid = true;

        } catch (NumberFormatException e) {
            // Not a number, assume it's a name
            result.isIndex = false;
            result.playerName = identifier;
            result.isValid = true;
        }

        return result;
    }

    /**
     * Conditionally adds players to the team with validation
     *
     * @param cmdInfo    the validated command object
     * @param playerSide flag to control player or computer team
     * @param command    the add command
     */
    private void addPlayer(AddCommandInfo cmdInfo, boolean playerSide, String command) {
        if (cmdInfo.isValid) {
            // Only add pitcher if command is validated
            if (playerSide) {
                Batter batter = getBatterFromCommand(cmdInfo);
                if (batter != null) {
                    try {
                        model.addBatterToLineup(Side.PLAYER, command, this.filteredBatters.stream());
                        view.displayMessage("Added " + batter.getName() + " to position " + cmdInfo.position);
                    } catch (IllegalArgumentException e) {
                        view.displayError(e.getMessage());
                    }
                }
            } else {
                Pitcher pitcher = getPitcherFromCommand(cmdInfo);
                if (pitcher != null) {
                    try {
                        model.addPitcherToLineup(Side.COMPUTER, command, this.filteredPitchers.stream());
                        view.displayMessage("Added " + pitcher.getName() + " to position " + cmdInfo.position);
                    } catch (IllegalArgumentException e) {
                        view.displayError(e.getMessage());
                    }
                }
            }
        } else {
            view.displayError("Invalid player command. Type 'help' for available commands.");
        }
    }

    /**
     * Gets a batter by either index or name depending on the command info
     *
     * @param cmdInfo The parsed command information
     * @return The batter if found, null otherwise
     */
    private Batter getBatterFromCommand(AddCommandInfo cmdInfo) {
        if (cmdInfo.isIndex) {
            // Get by index
            return filteredBatters.get(cmdInfo.playerIndex);
        } else {
            // Get by name
            for (Batter batter : filteredBatters) {
                if (batter.getName().equalsIgnoreCase(cmdInfo.playerName)) {
                    return batter;
                }
            }
            // If not found, try to find a partial match
            for (Batter batter : filteredBatters) {
                if (batter.getName().toLowerCase().contains(cmdInfo.playerName.toLowerCase())) {
                    return batter;
                }
            }
            // Not found
            view.displayError("Batter not found: " + cmdInfo.playerName);
            return null;
        }
    }

    /**
     * Gets a pitcher by either index or name depending on the command info,
     * validating that the pitcher's rotation position matches the requested position
     *
     * @param cmdInfo The parsed command information
     * @return The pitcher if found with matching position, null otherwise
     */
    private Pitcher getPitcherFromCommand(AddCommandInfo cmdInfo) {
        Pitcher pitcher = null;

        if (cmdInfo.isIndex) {
            // Get by index
            pitcher = filteredPitchers.get(cmdInfo.playerIndex);
        } else {
            // Get by name
            for (Pitcher p : filteredPitchers) {
                if (p.getName().equalsIgnoreCase(cmdInfo.playerName)) {
                    pitcher = p;
                    break;
                }
            }

            // If not found, try to find a partial match
            if (pitcher == null) {
                for (Pitcher p : filteredPitchers) {
                    if (p.getName().toLowerCase().contains(cmdInfo.playerName.toLowerCase())) {
                        pitcher = p;
                        break;
                    }
                }
            }
        }

        // If pitcher is found, validate position
        if (pitcher != null) {
            if (pitcher.getRotation() != cmdInfo.position) {
                view.displayError("Pitcher " + pitcher.getName() + " is in rotation position " +
                        pitcher.getRotation() + " but you requested position " + cmdInfo.position +
                        ". Please select a pitcher with the correct rotation position.");
                return null;
            }
        } else {
            // Not found
            view.displayError("Pitcher not found: " + cmdInfo.playerName);
        }

        return pitcher;
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
                handleComputerShowCommand(parts);
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

                    view.displayMessage("Selected team: " + comTeam);

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

                // Validate and extract add command
                AddCommandInfo cmdInfo = validateAddToCommand(command, false);

                // Add player
                addPlayer(cmdInfo, false, command);


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
     * Handles computer show command
     *
     * @param parts the command array
     */
    private void handleComputerShowCommand(String[] parts) {
        if (parts.length < 3) {
            view.displayError("Incorrect computer command. Type 'help' for available commands.");
            return;
        }

        switch (parts[2].toLowerCase()) {
            case "teams":
                view.displayAllTeams(model.getAllTeamName());
                return;
            case "attributes":
                view.displayListOfStrings(model.getAllColumnName());
                return;
            case "lineup":
                if (model.getComTeam() == null) {
                    view.displayError("Please select a team first.");
                    return;
                }
                view.displayPitchers(model.getComTeamPitcherLineup());
                return;
        }

        if (parts.length > 3) { // If length is greater than three we assume pitcher name was provided
            String pitcherName = extractCommand(parts);

            Pitcher pitcher = model.getPitcher(Side.COMPUTER, pitcherName);
            if (pitcher != null) {
                view.displayPlayerInfo(pitcher);
            } else {
                view.displayError("Pitcher not found: " + pitcherName);
            }
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

        // Trim the trailing space
        String rawFilterString = filterBuilder.toString().trim();

        // Normalize whitespace around operators and assign to result
        result.filterString = rawFilterString
                .replaceAll("\\s*([<>=!~])\\s*", "$1") // Remove spaces around operators
                .replaceAll("\\s*,\\s*", ","); // Remove spaces around commas

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
