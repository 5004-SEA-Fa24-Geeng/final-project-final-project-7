package model.loader;

import java.util.Map;
import java.util.Set;

import gameEnum.PlayerData;
import gameEnum.Teams;
import model.player.Batter;
import model.player.Pitcher;

public interface Loader {
    Set<Batter> loadBatters(Teams teamName);
    Set<Pitcher> loadPitchers(Teams teamName);
    Pitcher toPitcher(String line, Map<PlayerData, Integer> columnMap);
    Batter toBatter(String line, Map<PlayerData, Integer> columnMap);
}
