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

    Team getPlayerTeam();
    Team getComTeam();
    List<Batter> getPlayerTeamBatterLineup();
    List<Pitcher> getPlayerTeamPitcherLineup();
    List<Batter> getComTeamBatterLineup();
    List<Pitcher> getComTeamPitcherLineup();
    Set<Batter> getPlayerTeamBatterLoaderLineup();
    Set<Pitcher> getPlayerTeamPitcherLoaderLineup();
    Set<Batter> getComTeamBatterLoaderLineup();
    Set<Pitcher> getComTeamPitcherLoaderLineup();
    void setPlayerTeam(Teams teamName);
    void setComTeam(Teams teamName);
    Player getBatter(Side side, String batterName);
    Player getPitcher(Side side, String pitcherName);
    void addBatterToLineup(Side side, String filterString, Stream<Batter> filteredBatters);
    void addPitcherToLineup(Side side, String filterString, Stream<Pitcher> filteredPitchers);
    void removeFromLineup(Side side, String position, String filerString);
    void clearLineup(Side side, String position);
    SimulationResult startSimAndGetResult();
    List<String> convertLineupToString(List<? extends Player> lineup);
    void saveLineupAsTXTFile(Side side, String filename, List<? extends Player> lineup);
    void saveGameDetailsAsTXTFile(String filename);


}
