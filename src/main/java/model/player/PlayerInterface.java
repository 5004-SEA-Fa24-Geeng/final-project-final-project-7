package model.player;

/**
 * Interface for all player objects in the baseball simulation system.
 * Defines the common methods that both batters and pitchers must implement.
 */
public interface PlayerInterface {

    /**
     * Gets the name of the player.
     * @return The name of the player
     */
    String getName();

    /**
     * Gets the player's position.
     * @return The player's position
     */
    String getPosition();
}
