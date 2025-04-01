package model.filter;

import java.util.List;
import java.util.stream.Stream;

import model.player.Player;

public interface Filter {
    Stream<Player> filter(List<Player> players, String attribute, Object value);
}
