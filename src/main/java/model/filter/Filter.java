package model.filter;

import model.Operations;
import model.PlayerData;
import model.player.Batter;
import model.player.Pitcher;

public interface Filter {

    /**
     * filter batters using batter data, operations, and the value user send in.
     * @param batter batter
     * @param column player data name, ex: name, FastballPA etc
     * @param op operations, ex: equals, greater then etc
     * @param value the value used to compare with the value of a batter
     * @return boolean, true if the batter fits the filter, else false
     */
    boolean batterFilter(Batter batter, PlayerData column, Operations op, String value);

    /**
     * filter board games using game data, operations, and the value user send in.
     * @param pitcher pitcher
     * @param column player data name, ex: name, Strikes etc
     * @param op operations, ex: equals, greater then etc
     * @param value the value used to compare with the value of a pitcher
     * @return boolean, true if the pitcher fits the filter, else false
     */
    boolean pitcherFilter(Pitcher pitcher, PlayerData column, Operations op, String value);

    /**
     * Compares strings.
     * @param playerData the name of the player
     * @param op operations, ex: equals, greater then etc
     * @param value the value used to compare with the value of a player
     * @return boolean, true if the player fits the filter, else false
     */
    boolean filterString(String playerData, Operations op, String value);

    /**
     * Compares integers.
     * @param playerData fields such as PA, Hits etc 
     * @param op operations, ex: equals, greater then etc
     * @param value the value used to compare with the value of a board game
     * @return boolean, true if the game fits the filter, else false
     */
    boolean filterNum(int playerData, Operations op, String value);  

    /**
     * Compares double.
     * @param playerData fields such as pitch usage of a pitch type
     * @param op operations, ex: equals, greater then etc
     * @param value the value used to compare with the value of a board game
     * @return boolean, true if the game fits the filter, else false
     */
    boolean filterDouble(double playerData, Operations op, String value);        
}
