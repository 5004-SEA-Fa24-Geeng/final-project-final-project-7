package model.sorter;

import java.util.Comparator;
import model.player.Batter;
import model.player.Pitcher;

public interface Sorter {
    /**
     * Use sortOn to decide which sort class to use, and asc to decide which order.
     * @param sortOn batter csv column name
     * @param asc true = asc, false = desc
     * @return Comparator<Batter>
     */
    Comparator<Batter> getBatterSortType(String sortOn, boolean asc);
    /**
     * Use sortOn to decide which sort class to use, and asc to decide which order.
     * @param sortOn pitcher csv column name
     * @param asc true = asc, false = desc
     * @return Comparator<Pitcher>
     */
    Comparator<Pitcher> getPitcherSortType(String sortOn, boolean asc);
}
