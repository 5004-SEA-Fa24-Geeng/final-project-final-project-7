package view;

/**
 * Interface for objects that can be displayed as simulation results in the UI
 */
public interface DisplayableSimulationResult {
  /**
   * Gets the home team score
   * 
   * @return The home team score
   */
  int getHomeScore();

  /**
   * Gets the away team score
   * 
   * @return The away team score
   */
  int getAwayScore();

  /**
   * Gets the detailed game information
   * 
   * @return The game details as a string
   */
  String getDetails();
}
