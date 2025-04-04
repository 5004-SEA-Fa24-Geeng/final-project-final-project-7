package model.sorter;

import java.util.Comparator;
import model.player.Player;

public interface Sorter {
    Comparator<Player> getBatterSortType(String sortOn, boolean asc);
    Comparator<Player> getPitcherSortType(String sortOn, boolean asc);
}
