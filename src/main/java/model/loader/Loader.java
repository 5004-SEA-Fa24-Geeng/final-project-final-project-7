package model.loader;

import java.util.Map;
import java.util.Set;

import gameEnum.PlayerData;
import gameEnum.Teams;
import model.player.Batter;
import model.player.Pitcher;

public interface Loader {
    /**
     * Load batters from MARINERS csv.
     * @param teamName enum in Teams
     * @return Set<Batter>
     */
    Set<Batter> loadBatters(Teams teamName);
    /**
     * Load pitchers from any csv other than MARINERS.
     * @param teamName enum in Teams
     * @return Set<Pitcher>
     */
    Set<Pitcher> loadPitchers(Teams teamName);
    /**
     * Converts a line from the csv file into a Pitcher object.
     * @param line      the line to convert
     * @param columnMap the map of columns to index
     * @return Pitcher
     */
    Pitcher toPitcher(String line, Map<PlayerData, Integer> columnMap);
    /**
     * Converts a line from the csv file into a Batter object.
     * @param line      the line to convert
     * @param columnMap the map of columns to index
     * @return Batter
     */
    Batter toBatter(String line, Map<PlayerData, Integer> columnMap);
}
