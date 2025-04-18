package model.inning;
import gameEnum.Teams;
import model.player.Batter;
import model.team.Team;

import java.util.List;

/**
 * Interface for baseball inning implementations.
 * Defines methods required for simulating a complete inning in a baseball game.
 */
public interface Inning {
    /**
     * Resets all inning state variables to prepare for a new inning.
     * This includes clearing bases, resetting outs, strikes, balls, and statistics.
     */
    void resetInning();

    /**
     * Runs a complete inning simulation using the provided team and starting batter.
     * @param team The batting team for this inning
     * @param currentBatterIndex The index of the batter in the lineup who will bat first
     * @return The number of runs scored during the inning
     */
    int runInning(Team team, int currentBatterIndex);
}
