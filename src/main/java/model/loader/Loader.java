package model.loader;

import java.util.Map;
import java.util.Set;

import gameEnum.PlayerData;
import gameEnum.Teams;
import model.player.Batter;
import model.player.Pitcher;
import model.player.Player;

public interface Loader {
    Set<Player> loadPlayers(String position, Teams teamName);
    String getFilePath(Teams teamName);
    Set<Player> load(String position, String filePath);
    Pitcher toPitcher(String line, Map<PlayerData, Integer> columnMap);
    Batter toBatter(String line, Map<PlayerData, Integer> columnMap);
    Map<PlayerData, Integer> processHeader(String header);
}
