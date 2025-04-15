package model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import gameEnum.PlayerData;
import gameEnum.Side;
import gameEnum.Teams;
import model.filter.PlayerFilter;
import model.player.Batter;
import model.player.Pitcher;
import model.player.Player;
import model.simulation.Simulation;
import model.simulation.SimulationResult;
import model.sorter.PlayerSorter;
import model.team.ComTeam;
import model.team.PlayerTeam;
import model.team.Team;

public class Model implements ModelInterface {
    
    /** Default team for Player. */
    private static final Teams DEFAULT_PLAYER_TEAM = Teams.MARINERS;
    /** Default random team for Computer. */
    private static final Teams DEFAULT_RANDOM_COM_TEAM = Teams.randomTeam();
    /** Use Name as the default sort on column. */
    private static final String DEFAULT_SORT_ON_NAME = "Name";
    /** Run SetComTeam() before using this field. */
    private ComTeam comTeam = null;
    /** Run SetPlayerTeam() before using this field. */
    private PlayerTeam playerTeam = null;
    /** Run startSimAndGetResult() before using this field. */
    private SimulationResult gameResult = null;
    /** Filter for filtering batterLoaderLineup and pitcherLoaderLineup. */   
    private PlayerFilter filter = new PlayerFilter(); 
    /** Sorter for sorting batterLoaderLineup and pitcherLoaderLineup. */  
    private PlayerSorter sorter = new PlayerSorter();

    /** Public Model constructor. */  
    public Model() { }
    /**
     * playerTeam getter.
     * @return Team
     */
    public Team getPlayerTeam() {
        return this.playerTeam;
    }
    /**
     * comTeam getter.
     * @return Team
     */
    public Team getComTeam() {
        return this.comTeam;
    }
    /**
     * Turns Teams enums into String List and get that list.
     * CMD: computer show all
     * @return List<String>
     */    
    public List<String> getAllTeamName() {
        List<String> enumStringList = new ArrayList<>();
        for (Teams team: Teams.values()) {
            if (team == Teams.MARINERS) {
                continue;
            }
            enumStringList.add(team.getCmdName());
        }
        return enumStringList;
    }
    /**
     * Turns PlayerData enums into String List and get that list.
     * CMD: player/computer show attributes
     * @return List<String>
     */    
    public List<String> getAllColumnName() {
        List<String> enumStringList = new ArrayList<>();
        for (PlayerData col: PlayerData.values()) {
            enumStringList.add(col.getColumnName());
        }
        return enumStringList;
    }    
    /**
     * player's BatterLineup getter.
     * CMD: player show lineup
     * @return List<Batter>
     */     
    public List<Batter> getPlayerTeamBatterLineup() {
        return this.playerTeam.getBatterLineup();
    }
    /**
     * player's PitcherLineup getter.
     * CMD: player show lineup
     * @return List<Pitcher>
     */ 
    public List<Pitcher> getPlayerTeamPitcherLineup() {
        return this.playerTeam.getPitcherLineup();
    }
    /**
     * com's BatterLineup getter.
     * CMD: computer show lineup
     * @return List<Batter>
     */   
    public List<Batter> getComTeamBatterLineup() {
        return this.comTeam.getBatterLineup();
    }
    /**
     * com's PitcherLineup getter.
     * CMD: computer show lineup
     * @return List<Pitcher>
     */ 
    public List<Pitcher> getComTeamPitcherLineup() {
        return this.comTeam.getPitcherLineup();
    }
    /**
     * player's BatterLoaderLineup getter.
     * CMD: player show batter [name]
     * @return Set<Batter>
     */ 
    public Set<Batter> getPlayerTeamBatterLoaderLineup() {
        return this.playerTeam.getBatterLoaderLineup();
    }
    /**
     * player's PitcherLoaderLineup getter.
     * @return Set<Pitcher>
     */ 
    public Set<Pitcher> getPlayerTeamPitcherLoaderLineup() {
        return this.playerTeam.getPitcherLoaderLineup();
    }
    /**
     * com's BatterLoaderLineup getter.
     * CMD: computer show batter [name]
     * @return Set<Batter>
     */ 
    public Set<Batter> getComTeamBatterLoaderLineup() {
        return this.comTeam.getBatterLoaderLineup();
    }
    /**
     * com's PitcherLoaderLineup getter.
     * @return Set<Pitcher>
     */ 
    public Set<Pitcher> getComTeamPitcherLoaderLineup() {
        return this.comTeam.getPitcherLoaderLineup();
    }
    /** Set playerTeam with Teams.MARINERS. */ 
    public void setPlayerTeam() {
        this.setPlayerTeam(DEFAULT_PLAYER_TEAM);
    }
    /**
     * Set playerTeam with any enum in Teams.
     * @param teamName enum in Teams
     */
    public void setPlayerTeam(Teams teamName) {
        this.playerTeam = new PlayerTeam(teamName);
    }
    /** Set comTeam with random enum except Teams.MARINERS in Teams. */ 
    public void setComTeam() {
        this.setComTeam(DEFAULT_RANDOM_COM_TEAM);
    }
    /**
     * Set comTeam with any enum in Teams.
     * CMD: computer select [team]
     * @param teamName enum in Teams
     */
    public void setComTeam(Teams teamName) {
        this.comTeam = new ComTeam(teamName);
    }
    /**
     * Use side to indicate which side's loader should the method look in to.
     * And use the batterName to get the batter user asked for.
     * CMD: player show batter [name]
     * @param side Side enum: PLAYER, COMPUTER
     * @param batterName batter's name
     * @return Batter from loader
     */
    public Batter getBatter(Side side, String batterName) {
        Batter returnBatter = null;
        if (side.equals(Side.PLAYER)) {
            return this.playerTeam.getBatterFromLoader(batterName);
        } else if (side.equals(Side.COMPUTER)) {
            return this.comTeam.getBatterFromLoader(batterName);
        }
        return returnBatter;
    }
    /**
     * Use side to indicate which side's loader should the method look in to.
     * And use the pitcherName to get the pitcher user asked for.
     * CMD: computer show pitcher [name]
     * @param side Side enum: PLAYER, COMPUTER
     * @param batterName pitcher's name
     * @return Pitcher from loader
     */
    public Pitcher getPitcher(Side side, String pitcherName) {
        Pitcher returnPitcher = null;
        if (side.equals(Side.PLAYER)) {
            return this.playerTeam.getPitcherFromLoader(pitcherName);
        } else if (side.equals(Side.COMPUTER)) {
            return this.comTeam.getPitcherFromLoader(pitcherName);
        }
        return returnPitcher;
    }
    /**
     * true if the string only contains one filter, else false.
     * @param filter
     * @return bolean
     */
    private boolean checkFilterNum(String filter) {
        // only one filter
        // "name == Carlos Rodón"        
        if (filter.split(",").length == 1) {
            return true;
        }
        // 2+ filters
        // "name ~= Carlos Rodón, rotation == 1, picthes >= 1000"
        return false;           

    }
    /**
     * Use the filter string and loader linup to get the filtered results.
     * CMD: player filter [attribute] [value] [sort] [attribute]
     * @param filter ex: "TotalPA >= 100"
     * @param batterLoaderLineup batterLoaderLineup that we get from Team class.
     * @return Stream<Batter> filterd batters
     */
    public Stream<Batter> batterFilter(String filter, Set<Batter> batterLoaderLineup) {
        // true: single filter
        // false: multi filter
        try {
            Stream<Batter> filteredStream = (checkFilterNum(filter)) 
            ? filterSingleForBatter(filter,batterLoaderLineup.stream()) 
            : filterMultiForBatter(filter, batterLoaderLineup.stream());
            filteredStream = filteredStream.sorted(sorter.getBatterSortType(DEFAULT_SORT_ON_NAME, true));
            return filteredStream;
        } catch (Exception e) {
            Stream<Batter> filteredStream = Stream.empty();
            return filteredStream;
        }
    }
    /**
     * Use the filter string, column from PlayerData and loader linup to get the filtered results.
     * CMD: player filter [attribute] [value] [sort] [attribute]
     * @param filter ex: "TotalPA >= 100"
     * @param sortOn ex: PlayerData.OPS
     * @param batterLoaderLineup batterLoaderLineup that we get from Team class.
     * @return Stream<Batter> filterd batters
     */
    public Stream<Batter> batterFilter(String filter, PlayerData sortOn, Set<Batter> batterLoaderLineup) {
        // true: single filter
        // false: multi filter
        try {
            Stream<Batter> filteredStream = (checkFilterNum(filter)) 
            ? filterSingleForBatter(filter, batterLoaderLineup.stream()) 
            : filterMultiForBatter(filter, batterLoaderLineup.stream());
            filteredStream = filteredStream.sorted(sorter.getBatterSortType(sortOn.getColumnName(), true));
            return filteredStream;
        } catch (Exception e) {
            Stream<Batter> filteredStream = Stream.empty();
            return filteredStream;
        }        
    }
    /**
     * Use the filter string, column from PlayerData, boolean and loader linup to get the filtered results.
     * CMD: player filter [attribute] [value] [sort] [attribute]
     * @param filter ex: "TotalPA >= 100"
     * @param sortOn ex: PlayerData.OPS
     * @param ascending true if ascending, false if descending
     * @param batterLoaderLineup batterLoaderLineup that we get from Team class.
     * @return Stream<Batter> filterd batters
     */
    public Stream<Batter> batterFilter(String filter, PlayerData sortOn, 
                                        boolean ascending , Set<Batter> batterLoaderLineup) {
        // true: single filter
        // false: multi filter
        try {
            Stream<Batter> filteredStream = (checkFilterNum(filter)) 
            ? filterSingleForBatter(filter, batterLoaderLineup.stream()) 
            : filterMultiForBatter(filter, batterLoaderLineup.stream());
            filteredStream = filteredStream.sorted(sorter.getBatterSortType(sortOn.getColumnName(), ascending));
            return filteredStream;
        } catch (Exception e) {
            Stream<Batter> filteredStream = Stream.empty();
            return filteredStream;
        }         
    }
    /**
     * Use the filter string and loader linup to get the filtered results.
     * CMD: computer filter [attribute] [value] [sort] [attribute]
     * @param filter ex: "Pitches >= 1000"
     * @param pitcherLoaderLineup pitcherLoaderLineup that we get from Team class.
     * @return Stream<Pitcher> filterd pitchers
     */
    public Stream<Pitcher> pitcherFilter(String filter, Set<Pitcher> pitcherLoaderLineup) {
        // true: single filter
        // false: multi filter
        try {
            Stream<Pitcher> filteredStream = (checkFilterNum(filter)) 
            ? filterSingleForPitcher(filter,pitcherLoaderLineup.stream()) 
            : filterMultiForPitcher(filter, pitcherLoaderLineup.stream());
            filteredStream = filteredStream.sorted(sorter.getPitcherSortType(DEFAULT_SORT_ON_NAME, true));
            return filteredStream;
        } catch (Exception e) {
            Stream<Pitcher> filteredStream = Stream.empty();
            return filteredStream;
        }     
    }
    /**
     * Use the filter string, column from PlayerData and loader linup to get the filtered results.
     * CMD: computer filter [attribute] [value] [sort] [attribute]
     * @param filter ex: "Pitches >= 1000"
     * @param sortOn ex: PlayerData.FOURSEAM
     * @param pitcherLoaderLineup pitcherLoaderLineup that we get from Team class.
     * @return Stream<Pitcher> filterd pitchers
     */
    public Stream<Pitcher> pitcherFilter(String filter, PlayerData sortOn, Set<Pitcher> pitcherLoaderLineup) {
        // true: single filter
        // false: multi filter
        try {
            Stream<Pitcher> filteredStream = (checkFilterNum(filter)) 
            ? filterSingleForPitcher(filter, pitcherLoaderLineup.stream()) 
            : filterMultiForPitcher(filter, pitcherLoaderLineup.stream());
            filteredStream = filteredStream.sorted(sorter.getPitcherSortType(sortOn.getColumnName(), true));
            return filteredStream;
        } catch (Exception e) {
            Stream<Pitcher> filteredStream = Stream.empty();
            return filteredStream;
        }           
    }
    /**
     * Use the filter string, column from PlayerData, boolean and loader linup to get the filtered results.
     * CMD: computer filter [attribute] [value] [sort] [attribute]
     * @param filter ex: "Pitches >= 1000"
     * @param sortOn ex: PlayerData.FOURSEAM
     * @param ascending true if ascending, false if descending
     * @param pitcherLoaderLineup pitcherLoaderLineup that we get from Team class.
     * @return Stream<Pitcher> filterd pitchers
     */
    public Stream<Pitcher> pitcherFilter(String filter, PlayerData sortOn, 
                                            boolean ascending, Set<Pitcher> pitcherLoaderLineup) {
        // true: single filter
        // false: multi filter
        try {
            Stream<Pitcher> filteredStream = (checkFilterNum(filter)) 
            ? filterSingleForPitcher(filter, pitcherLoaderLineup.stream()) 
            : filterMultiForPitcher(filter, pitcherLoaderLineup.stream());
            filteredStream = filteredStream.sorted(sorter.getPitcherSortType(sortOn.getColumnName(), ascending));
            return filteredStream;
        } catch (Exception e) {
            Stream<Pitcher> filteredStream = Stream.empty();
            return filteredStream;
        }                  
    }
    /**
     * processes string with one filter.
     * @param filterString string contains one filter
     * @param filteredBatters a stream of batters that user send in
     * @return Stream<Batter> a stream of batters that is filtered
     */
    private Stream<Batter> filterSingleForBatter(String filterString, Stream<Batter> filteredBatters) {
        Operations operator = Operations.getOperatorFromStr(filterString);
        if (operator == null) {
            return filteredBatters;
        }
        // remove spaces
        filterString = filterString.replaceAll(" ", "");

        String[] parts = filterString.split(operator.getOperator());
        if (parts.length != 2) {
            return filteredBatters;
        }
        PlayerData column;
        try {
            column = PlayerData.fromString(parts[0]);
        } catch (IllegalArgumentException e) {
            return filteredBatters;
        }

        String value;
        try {
            value = parts[1].trim();
        } catch (IllegalArgumentException e) {
            return filteredBatters;
        }
        
        filteredBatters = filteredBatters.filter(batter -> this.filter
                                            .batterFilter(batter, column, operator, value));
        
        return filteredBatters;

    }
    /**
     * processes string with 2+ filter.
     * @param filterString string contains 2+ filter
     * @param filteredBatters a stream of batters that user send in
     * @return Stream<Batter> a stream of batters that is filtered
     */
    private Stream<Batter> filterMultiForBatter(String filterString, Stream<Batter> filteredBatters) {
        // remove spaces
        filterString = filterString.replaceAll(" ", ""); 
        // remove commas       
        String[] parts = filterString.split(",");
        // for every part in the filter
        // part breaks into smallPart to get the column, operator, value we need
        for (String part : parts) {
            Operations operator = Operations.getOperatorFromStr(part);
            String[] smallParts = part.split(operator.getOperator());
            if (smallParts.length != 2) {
                return filteredBatters;
            }
            PlayerData column;
            try {
                column = PlayerData.fromString(smallParts[0]);
            } catch (IllegalArgumentException e) {
                return filteredBatters;
            }
            String value;
            try {
                value = smallParts[1].trim();
            } catch (IllegalArgumentException e) {
                return filteredBatters;
            }
            filteredBatters = filteredBatters.filter(batter -> this.filter
                                                .batterFilter(batter, column, operator, value));
        }
        return filteredBatters;
    }
    /**
     * processes string with one filter.
     * @param filterString string contains one filter
     * @param filteredPitchers a stream of pitchers that user send in
     * @return Stream<Pitcher> a stream of pitchers that is filtered
     */
    private Stream<Pitcher> filterSingleForPitcher(String filterString, Stream<Pitcher> filteredPitchers) {
        Operations operator = Operations.getOperatorFromStr(filterString);
        if (operator == null) {
            return filteredPitchers;
        }
        // remove spaces
        filterString = filterString.replaceAll(" ", "");

        String[] parts = filterString.split(operator.getOperator());
        if (parts.length != 2) {
            return filteredPitchers;
        }
        PlayerData column;
        try {
            column = PlayerData.fromString(parts[0]);
        } catch (IllegalArgumentException e) {
            return filteredPitchers;
        }

        String value;
        try {
            value = parts[1].trim();
        } catch (IllegalArgumentException e) {
            return filteredPitchers;
        }
        
        filteredPitchers = filteredPitchers.filter(pitcher -> this.filter
                                            .pitcherFilter(pitcher, column, operator, value));
        
        return filteredPitchers;

    }
    /**
     * processes string with 2+ filter.
     * @param filterString string contains 2+ filter
     * @param filteredPitchers a stream of pitchers that user send in
     * @return Stream<Pitcher> a stream of pitchers that is filtered
     */
    private Stream<Pitcher> filterMultiForPitcher(String filterString, Stream<Pitcher> filteredPitchers) {
        // remove spaces
        filterString = filterString.replaceAll(" ", ""); 
        // remove commas       
        String[] parts = filterString.split(",");
        // for every part in the filter
        // part breaks into smallPart to get the column, operator, value we need
        for (String part : parts) {
            Operations operator = Operations.getOperatorFromStr(part);
            String[] smallParts = part.split(operator.getOperator());
            if (smallParts.length != 2) {
                return filteredPitchers;
            }
            PlayerData column;
            try {
                column = PlayerData.fromString(smallParts[0]);
            } catch (IllegalArgumentException e) {
                return filteredPitchers;
            }
            String value;
            try {
                value = smallParts[1].trim();
            } catch (IllegalArgumentException e) {
                return filteredPitchers;
            }
            filteredPitchers = filteredPitchers.filter(pitcher -> this.filter
                                                .pitcherFilter(pitcher, column, operator, value));
        }
        return filteredPitchers;
    }
    /**
     * Add batter filtered from loader lineup to batter lineup.
     * CMD: player add batter
     * @param side Side enum: PLAYER, COMPUTER
     * @param filterString ex: "Austin Shenton to 3", "1 to 9"
     * @param filteredBatters filteredBatters from batterFilter()
     */
    public void addBatterToLineup(Side side, String filterString, Stream<Batter> filteredBatters) {
        if (!filterString.contains(" to ")) {
            throw new IllegalArgumentException("Needs to assign a position number.");
        }     
        if (side.equals(Side.PLAYER)) {
            this.playerTeam.addBatterToTeam(filterString, filteredBatters);
        } else if (side.equals(Side.COMPUTER)) {
            this.comTeam.addBatterToTeam(filterString, filteredBatters);
        }
    }
    /**
     * Add pitcher filtered from loader lineup to pitcher lineup.
     * CMD: computer add pitcher
     * @param side Side enum: PLAYER, COMPUTER
     * @param filterString ex: "Carlos Rodón to 1", "12 to 1"
     * @param filteredPitchers filteredPitchers from pitcherFilter()
     */
    public void addPitcherToLineup(Side side, String filterString, Stream<Pitcher> filteredPitchers) {
        if (!filterString.contains(" to ")) {
            throw new IllegalArgumentException("Needs to assign a position number.");
        }
        if (side.equals(Side.PLAYER)) {
            this.playerTeam.addPitcherToTeam(filterString, filteredPitchers);
        } else if (side.equals(Side.COMPUTER)) {
            this.comTeam.addPitcherToTeam(filterString, filteredPitchers);
        }
    }
    /**
     * Remove pitcher or batter from pitcher or batter lineup.
     * CMD: player remove batter
     * CMD: computer remove pitcher
     * @param side Side enum: PLAYER, COMPUTER
     * @param position ex: "batter", "pitcher"
     * @param filerString ex: "1-3", "all", "Carlos Rodón", "1"
     */
    public void removeFromLineup(Side side, String position, String filerString) {
        if (side.equals(Side.PLAYER)) {
            this.playerTeam.removeFromTeam(filerString, position);
        } else {
            this.comTeam.removeFromTeam(filerString, position);
        }
    }
    /**
     * Clear pitcher or batter lineup.
     * CMD: player clear
     * CMD: computer clear
     * @param side Side enum: PLAYER, COMPUTER
     * @param position ex: "batter", "pitcher"
     */
    public void clearLineup(Side side, String position) {
        if (side.equals(Side.PLAYER)) {
            if (position.equals("batter")) {
                this.playerTeam.clearBatterLineup();
            } else {
                this.playerTeam.clearPitcherLineup();
            }
        } else {
            if (position.equals("batter")) {
                this.comTeam.clearBatterLineup();
            } else {
                this.comTeam.clearPitcherLineup();
            }
        }
    }
    /**
     * Start simulation.
     * CMD: simulate
     * @return SimulationResult
     */
    public SimulationResult startSimAndGetResult() {
        // if there are null space in pitcher or batter lineup
        // the simulation will not be started
        // return null so that user don't have to start all over again
        // they can finish where they left and procceed to start simulator
        if (this.playerTeam.checkBatterLineupSpace() > 0 ||
            this.comTeam.checkPitcherLineupSpace() > 0) {
            System.out.println("Simulation failed.");
            System.out.println("Lineup is not completed.");
            return null;
        }
        Simulation game = new Simulation(this.playerTeam, this.comTeam);
        this.gameResult = game.runSimulation();
        return this.gameResult;
    }
    /**
     * Convert lineup to a String list.
     * @param lineup pitcher or batter lineup
     * @return List<String>
     */
    public List<String> convertLineupToString(List<? extends Player> lineup) {
        List<String> lineupList = new ArrayList<>();
        if (lineup ==  null) {
            return null;
        }
        for (Player player : lineup) {
            if (player == null) {
                String nullText = "===== (null) =====\n";
                lineupList.add(nullText);
            } else {
                lineupList.add(player.toString());
            }
        }
        return lineupList;
    }
    /**
     * Convert lineup to a String list.
     * CMD: player save batter lineup
     * CMD: computer save pitcher lineup
     * @param side Side enum: PLAYER, COMPUTER
     * @param filename a txt file path
     * @param lineup pitcher or batter lineup
     */
    public void saveLineupAsTXTFile(Side side, String filename, List<? extends Player> lineup) {
        if (!filename.contains(".txt")) {
            System.err.println("Save as txt file only.");
            return;
        }
        try {
            List<String> lineupStringList = convertLineupToString(lineup);
            if (lineupStringList == null) {
                System.err.println("Lineup cannot be null.");
                return;                
            }
            Files.write(Path.of(filename), lineupStringList);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
    /**
     * Save game details to a txt file.
     * CMD: save gameDetails
     * @param filename a txt file path
     */
    public void saveGameDetailsAsTXTFile(String filename) {
        if (!filename.contains(".txt")) {
            System.err.println("Save as txt file only.");
            return;
        }

        if (this.gameResult != null) {
            String gameResultDetails = this.gameResult.getDetails();
            String totalStatistics = this.gameResult.getTotalStatistics();
            String finalScore = this.gameResult.toString();
            List<String> lines = new ArrayList<>();
            lines.add(finalScore);
            lines.add(gameResultDetails);
            lines.add(totalStatistics);
            try {
                Files.write(Path.of(filename), lines);
            } catch (IOException e) {
                System.err.println("Error writing to file: " + e.getMessage());
            }
        }
    }

}
