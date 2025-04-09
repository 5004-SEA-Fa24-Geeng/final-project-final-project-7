package model;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import gameEnum.Side;
import gameEnum.Teams;
import model.player.Batter;
import model.player.Pitcher;
import model.player.Player;
import model.simulation.SimulationResult;
import model.team.Team;

public interface ModelInterface {
    /**
     * playerTeam getter.
     * @return Team
     */
    Team getPlayerTeam();
    /**
     * comTeam getter.
     * @return Team
     */    
    Team getComTeam();
    /**
     * Turns Teams enums into String List and get that list.
     * @return List<String>
     */    
    List<String> getAllTeamName();
    /**
     * player's BatterLineup getter.
     * @return List<Batter>
     */     
    List<Batter> getPlayerTeamBatterLineup();
    /**
     * player's PitcherLineup getter.
     * @return List<Pitcher>
     */ 
    List<Pitcher> getPlayerTeamPitcherLineup();
    /**
     * com's BatterLineup getter.
     * @return List<Batter>
     */  
    List<Batter> getComTeamBatterLineup();
    /**
     * com's PitcherLineup getter.
     * @return List<Pitcher>
     */ 
    List<Pitcher> getComTeamPitcherLineup();
    /**
     * player's BatterLoaderLineup getter.
     * @return Set<Batter>
     */ 
    Set<Batter> getPlayerTeamBatterLoaderLineup();
    /**
     * player's PitcherLoaderLineup getter.
     * @return Set<Pitcher>
     */ 
    Set<Pitcher> getPlayerTeamPitcherLoaderLineup();
    /**
     * com's BatterLoaderLineup getter.
     * @return Set<Batter>
     */ 
    Set<Batter> getComTeamBatterLoaderLineup();
    /**
     * com's PitcherLoaderLineup getter.
     * @return Set<Pitcher>
     */ 
    Set<Pitcher> getComTeamPitcherLoaderLineup();
    /**
     * Set playerTeam with any enum in Teams.
     * @param teamName enum in Teams
     */
    void setPlayerTeam(Teams teamName);
    /**
     * Set comTeam with any enum in Teams.
     * @param teamName enum in Teams
     */
    void setComTeam(Teams teamName);
    /**
     * Use side to indicate which side's loader should the method look in to.
     * And use the batterName to get the batter user asked for.
     * @param side Side enum: PLAYER, COMPUTER
     * @param batterName batter's name
     * @return Batter from loader
     */
    Batter getBatter(Side side, String batterName);
    /**
     * Use side to indicate which side's loader should the method look in to.
     * And use the pitcherName to get the pitcher user asked for.
     * @param side Side enum: PLAYER, COMPUTER
     * @param batterName pitcher's name
     * @return Pitcher from loader
     */
    Pitcher getPitcher(Side side, String pitcherName);
    /**
     * Add batter filtered from loader lineup to batter lineup.
     * @param side Side enum: PLAYER, COMPUTER
     * @param filterString ex: "Austin Shenton to 3", "1 to 9"
     * @param filteredBatters filteredBatters from batterFilter()
     */
    void addBatterToLineup(Side side, String filterString, Stream<Batter> filteredBatters);
    /**
     * Add pitcher filtered from loader lineup to pitcher lineup.
     * @param side Side enum: PLAYER, COMPUTER
     * @param filterString ex: "Carlos Rodón to 1", "12 to 1"
     * @param filteredPitchers filteredPitchers from pitcherFilter()
     */
    void addPitcherToLineup(Side side, String filterString, Stream<Pitcher> filteredPitchers);
    /**
     * Remove pitcher or batter from pitcher or batter lineup.
     * @param side Side enum: PLAYER, COMPUTER
     * @param position ex: "batter", "pitcher"
     * @param filerString ex: "1-3", "all", "Carlos Rodón", "1"
     */
    void removeFromLineup(Side side, String position, String filerString);
    /**
     * Clear pitcher or batter lineup.
     * @param side Side enum: PLAYER, COMPUTER
     * @param position ex: "batter", "pitcher"
     */
    void clearLineup(Side side, String position);
    /**
     * Start simulation.
     * @return SimulationResult
     */
    SimulationResult startSimAndGetResult();
    /**
     * Convert lineup to a String list.
     * @param lineup pitcher or batter lineup
     * @return List<String>
     */
    List<String> convertLineupToString(List<? extends Player> lineup);
    /**
     * Convert lineup to a String list.
     * @param side Side enum: PLAYER, COMPUTER
     * @param filename a txt file path
     * @param lineup pitcher or batter lineup
     */
    void saveLineupAsTXTFile(Side side, String filename, List<? extends Player> lineup);
    /**
     * Save game details to a txt file.
     * @param filename a txt file path
     */
    void saveGameDetailsAsTXTFile(String filename);


}
