package model.inning;
import model.player.Batter;

import java.util.List;

public interface Inning {
    void resetInning();
    int runInning(List<Batter> lineup);
}
