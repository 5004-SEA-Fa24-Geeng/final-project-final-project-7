package model.team;

import java.util.List;

import model.player.Player;
import model.player.PlayerInterface;

public abstract class Team implements TeamInterface{
    private String name;
    private List<Player> players;
    public String getTeamName() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    public List<PlayerInterface> getPlayers() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    public String getTeamStats() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    public Player getPlayer(String name) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
