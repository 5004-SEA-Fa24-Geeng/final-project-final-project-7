package view;

/**
 * Interface for objects that can be displayed as players in the UI
 */
public interface DisplayablePlayer {
  /**
   * Gets the name of the player
   * 
   * @return The player's name
   */
  String getName();

  /**
   * Gets a string representation of the player with stats
   * 
   * @return The string representation
   */
  String toString();
}
