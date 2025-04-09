package model.inning;
import gameEnum.Teams;
import model.player.Batter;
import model.team.Team;

import java.util.List;

public interface Inning {
    void resetInning();
    int runInning(Team team, int currentBatterIndex);
}
