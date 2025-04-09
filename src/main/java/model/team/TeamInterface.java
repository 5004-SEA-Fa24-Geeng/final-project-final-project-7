package model.team;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import model.player.Batter;
import model.player.Pitcher;

public interface TeamInterface {
    /**
     * Get team name.
     * @return String
     */
    String getTeamName();
    /**
     * Get batter lineup.
     * @return List<Batter>
     */
    List<Batter> getBatterLineup();
    /**
     * Get pitcher lineup.
     * @return List<Pitcher>
     */
    List<Pitcher> getPitcherLineup();
    /**
     * Get batter loader lineup.
     * @return Set<Batter>
     */
    Set<Batter> getBatterLoaderLineup();
    /**
     * Get pitcher loader lineup.
     * @return Set<Pitcher>
     */
    Set<Pitcher> getPitcherLoaderLineup();
    /**
     * Get batter from batter loader lineup.
     * @return Batter
     */
    Batter getBatterFromLoader(String batterName);
    /**
     * Get pitcher from pitcher loader lineup.
     * @return Pitcher
     */
    Pitcher getPitcherFromLoader(String pitcherName);
    /** Clear batter lineup. */
    void clearBatterLineup();
    /** Clear pitcher lineup. */
    void clearPitcherLineup();
    /**
     * Add batter to the batter lineup.
     * @param str ex: "Austin Shenton to 3", "1 to 9"
     * @param filteredBatters filteredBatters from Model.batterFilter()
     */
    void addBatterToTeam(String str, Stream<Batter> filteredBatters);
    /**
     * Add pitcher to the pitcher lineup.
     * @param str ex: "Carlos Rodón to 1", "12 to 1"
     * @param filteredPitchers filteredPitchers from Model.pitcherFilter()
     */
    void addPitcherToTeam(String str, Stream<Pitcher> filteredPitchers);
    /**
     * remove batter or pitcher from the lineup.
     * @param str "1-3", "all", "Carlos Rodón", "1"
     * @param position ex: "batter", "pitcher"
     */
    void removeFromTeam(String str, String position);
        
}
