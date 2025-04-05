package model.filter;

import gameEnum.PlayerData;
import model.Operations;
import model.player.Batter;
import model.player.Pitcher;


public class PlayerFilter implements Filter{

    /**
     * Private constructor to prevent instantiation.
     */
    private PlayerFilter() { };

    /**
     * filter batters using batter data, operations, and the value user send in.
     * @param batter batter
     * @param column player data name, ex: name, FastballPA etc
     * @param op operations, ex: equals, greater then etc
     * @param value the value used to compare with the value of a batter
     * @return boolean, true if the batter fits the filter, else false
     */
    @Override
    public boolean batterFilter(Batter batter, PlayerData column, Operations op, String value) {

        switch (column) {
            case NAME:
                //filter NAME
                String batterName = batter.getName().toLowerCase().replaceAll(" ", "");
                value = value.toLowerCase().replaceAll(" ", "");    
                return filterString(batterName, op, value);
            case F_PA:
                //filter F_PA
                return filterNum(batter.getFastballPA(), op, value);      
            case F_H:
                //filter F_H
                return filterNum(batter.getFastballH(), op, value);    
            case F_1B:
                //filter F_1B
                return filterNum(batter.getFastball1B(), op, value);    
            case F_2B:
                //filter F_2B
                return filterNum(batter.getFastball2B(), op, value);    
            case F_3B:
                //filter F_3B
                return filterNum(batter.getFastball3B(), op, value); 
            case F_HR:
                //filter F_HR
                return filterNum(batter.getFastballHR(), op, value); 
            case B_PA:
                //filter B_PA
                return filterNum(batter.getBreakingPA(), op, value);      
            case B_H:
                //filter B_H
                return filterNum(batter.getBreakingH(), op, value);    
            case B_1B:
                //filter B_1B
                return filterNum(batter.getBreaking1B(), op, value);    
            case B_2B:
                //filter B_2B
                return filterNum(batter.getBreaking2B(), op, value);    
            case B_3B:
                //filter B_3B
                return filterNum(batter.getBreaking3B(), op, value); 
            case B_HR:
                //filter B_HR
                return filterNum(batter.getBreakingHR(), op, value); 
            case O_PA:
                //filter O_PA
                return filterNum(batter.getOffspeedPA(), op, value);      
            case O_H:
                //filter O_H
                return filterNum(batter.getOffspeedH(), op, value);    
            case O_1B:
                //filter O_1B
                return filterNum(batter.getOffspeed1B(), op, value);    
            case O_2B:
                //filter O_2B
                return filterNum(batter.getOffspeed2B(), op, value);    
            case O_3B:
                //filter O_3B
                return filterNum(batter.getOffspeed3B(), op, value); 
            case O_HR:
                //filter O_HR
                return filterNum(batter.getOffspeedHR(), op, value); 
            case T_PA:
                //filter T_PA
                return filterNum(batter.getTotalPA(), op, value);      
            case T_H:
                //filter T_H
                return filterNum(batter.getTotalH(), op, value);    
            case T_1B:
                //filter T_1B
                return filterNum(batter.getTotal1B(), op, value);    
            case T_2B:
                //filter T_2B
                return filterNum(batter.getTotal2B(), op, value);    
            case T_3B:
                //filter T_3B
                return filterNum(batter.getTotal3B(), op, value); 
            case T_HR:
                //filter T_HR
                return filterNum(batter.getTotalHR(), op, value); 
            case Z_SWING:
                //filter Z_SWING
                return filterDouble(batter.getZoneSwing(), op, value);      
            case Z_CONTACT:
                //filter Z_CONTACT
                return filterDouble(batter.getZoneContact(), op, value);        
            case C_SWING:
                //filter C_SWING
                return filterDouble(batter.getChaseSwing(), op, value);      
            case C_CONTACT:
                //filter C_CONTACT
                return filterDouble(batter.getChaseContact(), op, value);      
            case AVG:
                //filter AVG
                return filterDouble(batter.getAVG(), op, value);      
            case OBP:
                //filter OBP
                return filterDouble(batter.getOBP(), op, value);   
            case OPS:
                //filter OPS
                return filterDouble(batter.getOPS(), op, value);      
            default:
                return false;
        }
    }

    /**
     * filter board games using game data, operations, and the value user send in.
     * @param pitcher pitcher
     * @param column player data name, ex: name, Strikes etc
     * @param op operations, ex: equals, greater then etc
     * @param value the value used to compare with the value of a pitcher
     * @return boolean, true if the pitcher fits the filter, else false
     */
    @Override
    public boolean pitcherFilter(Pitcher pitcher, PlayerData column, Operations op, String value) {

        switch (column) {
            case NAME:
                //filter NAME
                String pitcherName = pitcher.getName().toLowerCase().replaceAll(" ", "");
                value = value.toLowerCase().replaceAll(" ", "");    
                return filterString(pitcherName, op, value);
            case ROTATION:
                //filter ROTATION
                return filterNum(pitcher.getRotation(), op, value);      
            case STRIKES:
                //filter STRIKES
                return filterNum(pitcher.getStrikes(), op, value);    
            case PITCHES:
                //filter PITCHES
                return filterNum(pitcher.getPitches(), op, value);    
            case S_RATE:
                //filter S_RATE
                return filterDouble(pitcher.getStrikesRate(), op, value);     
            case B_RATE:
                //filter B_RATE
                return filterDouble(pitcher.getBallsRate(), op, value);     
            case FOURSEAM:
                //filter FOURSEAM
                return filterDouble(pitcher.getFourSeam(), op, value);   
            case TWOSEAM:
                //filter TWOSEAM
                return filterDouble(pitcher.getTwoSeam(), op, value); 
            case CUTTER:
                //filter CUTTER
                return filterDouble(pitcher.getCutter(), op, value); 
            case SINKER:
                //filter SINKER
                return filterDouble(pitcher.getSinker(), op, value); 
            case SLIDER:
                //filter SLIDER
                return filterDouble(pitcher.getSlider(), op, value); 
            case CURVE:
                //filter CURVE
                return filterDouble(pitcher.getCurve(), op, value); 
            case KNUCKLE:
                //filter KNUCKLE
                return filterDouble(pitcher.getKnuckle(), op, value); 
            case SWEEPER:
                //filter SWEEPER
                return filterDouble(pitcher.getSweeper(), op, value); 
            case SLURVE:
                //filter SLURVE
                return filterDouble(pitcher.getSlurve(), op, value); 
            case SFINGER:
                //filter SFINGER
                return filterDouble(pitcher.getSplitFinger(), op, value); 
            case CHANGEUP:
                //filter CHANGEUP
                return filterDouble(pitcher.getChangeup(), op, value); 
            case FORK:
                //filter FORK
                return filterDouble(pitcher.getFork(), op, value); 
            case SCREW:
                //filter SCREW
                return filterDouble(pitcher.getScrew(), op, value); 
            default:
                return false;
        }
    }

    /**
     * Compares strings.
     * @param playerData the name of the player
     * @param op operations, ex: equals, greater then etc
     * @param value the value used to compare with the value of a player
     * @return boolean, true if the player fits the filter, else false
     */
    @Override
    public boolean filterString(String playerData, Operations op, String value) {

        switch (op) {
            case EQUALS:
                return playerData.equals(value);
            case NOT_EQUALS:
                return !(playerData.equals(value));
            case GREATER_THAN:
                return playerData.compareTo(value) > 0;
            case LESS_THAN:
                return playerData.compareTo(value) < 0;
            case GREATER_THAN_EQUALS:
                return playerData.compareTo(value) >= 0;
            case LESS_THAN_EQUALS:
            return playerData.compareTo(value) <= 0;
            case CONTAINS:
                return playerData.contains(value);
            default:
                return false;
        }
    }

    /**
     * Compares integers.
     * @param playerData fields such as PA, Hits etc 
     * @param op operations, ex: equals, greater then etc
     * @param value the value used to compare with the value of a board game
     * @return boolean, true if the game fits the filter, else false
     */
    @Override
    public boolean filterNum(int playerData, Operations op, String value) {
        int valueInt = Integer.parseInt(value);

        switch (op) {
            case EQUALS:
                return (playerData == valueInt);
            case NOT_EQUALS:
                return (playerData != valueInt);
            case GREATER_THAN:
                return (playerData > valueInt);
            case LESS_THAN:
                return (playerData < valueInt);
            case GREATER_THAN_EQUALS:
                return (playerData >= valueInt);
            case LESS_THAN_EQUALS:
                return (playerData <= valueInt);
            case CONTAINS:
                return false;
            default:
                return false;
        }
    }    

    /**
     * Compares double.
     * @param playerData fields such as pitch usage of a pitch type
     * @param op operations, ex: equals, greater then etc
     * @param value the value used to compare with the value of a board game
     * @return boolean, true if the game fits the filter, else false
     */
    @Override
    public boolean filterDouble(double playerData, Operations op, String value) {
        double valueDouble = Double.parseDouble(value);

        switch (op) {
            case EQUALS:
                return (playerData == valueDouble);
            case NOT_EQUALS:
                return (playerData != valueDouble);
            case GREATER_THAN:
                return (playerData > valueDouble);
            case LESS_THAN:
                return (playerData < valueDouble);
            case GREATER_THAN_EQUALS:
                return (playerData >= valueDouble);
            case LESS_THAN_EQUALS:
                return (playerData <= valueDouble);
            case CONTAINS:
                return false;
            default:
                return false;
        }
    }
}
