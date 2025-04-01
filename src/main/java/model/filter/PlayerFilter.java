package model.filter;

import java.util.List;
import java.util.stream.Stream;

import model.player.Player;

public class PlayerFilter implements Filter{
    public Stream<Player> filter(List<Player> players, String attribute, Object value) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
