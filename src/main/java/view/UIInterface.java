package view;

import java.util.List;
import model.player.*;
import model.simulation.*;

/**
 * Interface for the MLB Simulator User Interface
 * Defines the contract for UI implementations that handle
 * input and output for the MLB Simulator application
 */
public interface UIInterface {

    /**
     * Displays the help message showing all available commands
     */
    void displayHelp();

    /**
     * Displays individual player information
     *
     * @param player the player to show
     */
    void displayPlayerInfo(Player player);

    /**
     * Displays list of batter names
     *
     * @param batters the list of batters to display
     */
    void displayBatters(List<Batter> batters);

    /**
     * Displays list of pitcher names
     *
     * @param pitchers the list of pitchers to display
     */
    void displayPitchers(List<Pitcher> pitchers);

    /**
     * Displays a list of strings with numbered indices
     *
     * @param strings the list of strings to display
     */
    void displayListOfStrings(List<String> strings);

    /**
     * Displays a list of teams
     *
     * @param teams List of team names to display
     */
    void displayAllTeams(List<String> teams);

    /**
     * Displays simulation results
     *
     * @param simulationResult Game simulation results
     */
    void displaySimulationResult(SimulationResult simulationResult);

    /**
     * Gets the next command from the user
     *
     * @return The user's command as a string
     */
    String getCommand();

    /**
     * Displays a message to the user
     *
     * @param message The message to display
     */
    void displayMessage(String message);

    /**
     * Displays an error message to the user
     *
     * @param message The error message to display
     */
    void displayError(String message);

    /**
     * Closes any resources held by the UI
     */
    void close();
}