package model.loader;

import java.util.List;

import model.player.Player;
import model.team.Team;

public interface Loader {
    List<Player> loadPlayers(String filePath);
}
