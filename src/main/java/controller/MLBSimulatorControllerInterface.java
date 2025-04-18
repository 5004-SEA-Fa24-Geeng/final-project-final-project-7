package controller;

/**
 * Interface for the MLB Simulator Controller
 * Defines the contract for controller implementations that coordinate
 * between the view and model components of the MLB Simulator application
 */
public interface MLBSimulatorControllerInterface {

    /**
     * Starts the application and handles user interaction
     * This method should initialize the application, display welcome information,
     * and begin processing user commands until the user chooses to exit
     */
    void start();

    /**
     * Stops the application and performs any necessary cleanup
     * This method should set the running flag to false and close any open resources
     */
    void stop();
}