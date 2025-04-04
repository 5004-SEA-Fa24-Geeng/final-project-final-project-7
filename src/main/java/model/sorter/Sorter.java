package model.sorter;

import java.util.Comparator;
import model.player.Batter;
import model.player.Pitcher;

public interface Sorter {
    Comparator<Batter> getBatterSortType(String sortOn, boolean asc);
    Comparator<Pitcher> getPitcherSortType(String sortOn, boolean asc);
}
