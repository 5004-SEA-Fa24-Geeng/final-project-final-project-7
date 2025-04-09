package view.stubs;

/**
 * Stub Interface for objects that can be displayed as players in the UI
 */
public interface PlayerStub {
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
