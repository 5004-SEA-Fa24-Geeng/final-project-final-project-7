package model.sorter;

import java.util.List;
import java.util.stream.Stream;

import model.player.Player;

public interface Sorter {
    Stream<Player> sort(List<Player> players, String attribute);
}
