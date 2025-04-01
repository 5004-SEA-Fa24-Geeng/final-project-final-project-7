package model.team;

import java.util.List;

import model.player.PlayerInterface;

public interface TeamInterface {
        String getTeamName();
        List<PlayerInterface> getPlayers();
}
