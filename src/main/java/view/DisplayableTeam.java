package view;

import java.util.List;

/**
 * Interface for objects that can be displayed as teams in the UI
 */
public interface DisplayableTeam {
  /**
   * Gets the name of the team
   * 
   * @return The team's name
   */
  String getName();

  /**
   * Gets the list of players on the team
   * 
   * @return List of players
   */
  List<DisplayablePlayer> getPlayers();

  /**
   * Gets a string representation of the team stats
   * 
   * @return The team stats as a string
   */
  String getStats();
}
