package model.team;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import gameEnum.Teams;
import model.loader.PlayerLoader;
import model.player.Batter;
import model.player.Pitcher;
import model.player.Player;

public abstract class Team implements TeamInterface{
    /** Default batter position. */
    private static final String DEFAULT_BATTER = "batter";
    /** Default pitcher position. */
    private static final String DEFAULT_PITCHER = "pitcher";
    /** Default all string for remomve method. */
    private static final String ALL = "all";
    /** Team name. */
    private String name;
    /** Batter loader lineup. */
    private Set<Batter> batterLoaderLineup;
    /** Pitcher loader lineup. */
    private Set<Pitcher> pitcherLoaderLineup;
    /** Batter lineup. */
    private List<Batter> batterLineup;
    /** Pitcher lineup. */
    private List<Pitcher> pitcherLineup;
    /** Loader to load team roster from csv. */
    private PlayerLoader loader = new PlayerLoader();

    /**
     * Team constructor which sets up team name, batter and pitcher lineup, batter and pitcher loader lineup.
     * @param teamName ex: Teams.MARINERS
     */
    public Team(Teams teamName) {
        this.name = teamName.getCmdName();
        this.batterLoaderLineup = this.loader.loadBatters(teamName);
        this.pitcherLoaderLineup = this.loader.loadPitchers(teamName);
        this.batterLineup = new ArrayList<>(Collections.nCopies(9, null));
        this.pitcherLineup = new ArrayList<>(Collections.nCopies(3, null));
    }
    /**
     * Get team name.
     * @return String
     */
    public String getTeamName() {
        return this.name;
    }
    /**
     * Get batter lineup.
     * @return List<Batter>
     */
    public List<Batter> getBatterLineup() {
        return this.batterLineup;
    }
    /**
     * Get pitcher lineup.
     * @return List<Pitcher>
     */
    public List<Pitcher> getPitcherLineup() {
        return this.pitcherLineup;
    }
    /**
     * Get batter loader lineup.
     * @return Set<Batter>
     */
    public Set<Batter> getBatterLoaderLineup() {
        return this.batterLoaderLineup;
    }
    /**
     * Get pitcher loader lineup.
     * @return Set<Pitcher>
     */
    public Set<Pitcher> getPitcherLoaderLineup() {
        return this.pitcherLoaderLineup;
    }
    /**
     * Get batter from batter loader lineup.
     * @return Batter
     */
    public Batter getBatterFromLoader(String batterName) {
        Batter returnBatter = null;
        for (Batter batter : this.batterLoaderLineup) {
            if (batter.getName().toLowerCase().equals(batterName.toLowerCase())) {
                returnBatter = batter;
                return returnBatter;
            }
        }
        return returnBatter;
    }
    /**
     * Get pitcher from pitcher loader lineup.
     * @return Pitcher
     */
    public Pitcher getPitcherFromLoader(String pitcherName) {
        Pitcher returnPitcher = null;
        for (Pitcher pitcher : this.pitcherLoaderLineup) {
            if (pitcher.getName().toLowerCase().equals(pitcherName.toLowerCase())) {
                returnPitcher = pitcher;
                return returnPitcher;
            }
        }
        return returnPitcher;
    }
    /** Clear batter lineup. */
    public void clearBatterLineup() {
        this.batterLineup.clear();
        this.batterLineup = new ArrayList<>(Collections.nCopies(9, null));
    }
    /** Clear pitcher lineup. */
    public void clearPitcherLineup() {
        this.pitcherLineup.clear();
        this.pitcherLineup = new ArrayList<>(Collections.nCopies(3, null));
    }
    /**
     * Check if the batter is in the batter lineup.
     * @param batter
     * @return boolean true if the batter is in the lineup, false if not
     */
    public boolean checkBatterInLineup(Batter batter) {
        if (this.batterLineup.contains(batter)) {
            return true;
        }
        return false;
    }
    /**
     * Check if the pitcher is in the pitcher lineup.
     * @param pitcher
     * @return boolean true if the pitcher is in the lineup, false if not
     */
    public boolean checkPitcherInLineup(Pitcher pitcher) {
        if (this.pitcherLineup.contains(pitcher)) {
            return true;
        }
        return false;
    }
    /**
     * Check null space in the batter lineup.
     * @return int null space count
     */
    public int checkBatterLineupSpace() {
        int cnt = 0;
        for (Player batter: this.batterLineup) {
            if (batter == null) {
                cnt+=1;
            }
        }
        return cnt;
    }
    /**
     * Check null space in the pitcher lineup.
     * @return int null space count
     */
    public int checkPitcherLineupSpace() {
        int cnt = 0;
        for (Player pitcher: this.pitcherLineup) {
            if (pitcher == null) {
                cnt+=1;
            }
        }
        return cnt;
    }
    /**
     * Check if the added pitcher is in the right spot of the pitcher lineup.
     * Pitchers' with rotation 1 cannot be added to any spot of the lineup other than the first spot.
     * Pitchers' with rotation 2 cannot be added to any spot of the lineup other than the second and third spot.
     * @param lineupPos index for the pitcher lineup
     * @param pitcher the pitcher added to the pitcher lineup
     * @return boolean
     */
    public boolean checkPitcherInTheRightPlace(int lineupPos, Pitcher pitcher) {
        int rotation = pitcher.getRotation();
        // lineupPos is an index number, so we need to add 1 to match rotation number
        lineupPos = lineupPos + 1;
        // pitcher with rotation number 1 needs to be in the first spot of the list
        if (rotation == 1 && lineupPos != 1) {
            return false;
        // pitcher with rotation number 2 can be in anywhere in the list except the first spot
        } else if (rotation == 2 && lineupPos == 1) {
            return false;
        }
        return true;
    }
    /**
     * Add batter to the batter lineup.
     * @param str ex: "Austin Shenton to 3", "1 to 9"
     * @param filteredBatters filteredBatters from Model.batterFilter()
     */
    public void addBatterToTeam(String str, Stream<Batter> filteredBatters) {

        if (checkBatterLineupSpace() == 0) {
            System.out.println("No more space to add!");
            return;
        }
        str = str.trim();
        String[] parts = str.split(" to ");
        String addedPlayer = parts[0];
        // lineupPos must minus 1 to match the list index
        int lineupPos = Integer.parseInt(parts[1]) - 1;
        int listCount = this.batterLineup.size(); 
        if (lineupPos >= listCount) {
            System.out.println("Invalid position number!");
            return;
        }
        Batter newBatter;
        // add single name or single number to a specific position in lineup
        // "Austin Shenton to 3"   
        // "1 to 9"       
        // single number 
        if (addedPlayer.matches("[0-9]+")) {
            List<Batter> filteredList = filteredBatters.toList();
            // index must minus 1 to match the list index
            int index = Integer.parseInt(addedPlayer) - 1;
            newBatter = filteredList.get(index);
        // single name                
        } else {
            String name = addedPlayer.toLowerCase();
            newBatter = filteredBatters.filter(batter -> batter.getName()
                                            .toLowerCase().contains(name))
                                            .findFirst()
                                            .orElse(null);
        }
        if (checkBatterInLineup(newBatter)) {
            System.out.println(String.format("Cannot add %s.", newBatter.getName()));
            System.out.println("Batter is already in the lineup.");
            return;
        }
        this.batterLineup.set(lineupPos, newBatter);
    }
    /**
     * Add pitcher to the pitcher lineup.
     * @param str ex: "Carlos Rodón to 1", "12 to 1"
     * @param filteredPitchers filteredPitchers from Model.pitcherFilter()
     */
    public void addPitcherToTeam(String str, Stream<Pitcher> filteredPitchers) {

        if (checkPitcherLineupSpace() == 0) {
            System.out.println("No more space to add!");
            return;
        }
        str = str.trim();
        String[] parts = str.split(" to ");
        String addedPlayer = parts[0];
        // lineupPos must minus 1 to match the list index
        int lineupPos = Integer.parseInt(parts[1]) - 1;
        int listCount = this.pitcherLineup.size(); 
        if (lineupPos >= listCount) {
            System.out.println("Invalid position number!");
            return;
        }
        Pitcher newPitcher;
        // add single name or single number to a specific position in lineup
        // "Carlos Rodón to 1"   
        // "12 to 2"       
        // single number 
        if (addedPlayer.matches("[0-9]+")) {
            List<Pitcher> filteredList = filteredPitchers.toList();
            // index must minus 1 to match the list index
            int index = Integer.parseInt(addedPlayer) - 1;
            newPitcher = filteredList.get(index);
        // single name                
        } else {
            String name = addedPlayer.toLowerCase();
            newPitcher = filteredPitchers.filter(pitcher -> pitcher.getName()
                                            .toLowerCase().contains(name))
                                            .findFirst()
                                            .orElse(null);
        }
        if (checkPitcherInLineup(newPitcher)) {
            System.out.println(String.format("Cannot add %s.", newPitcher.getName()));
            System.out.println("Pitcher is already in the lineup.");
            return;
        }
        if (!checkPitcherInTheRightPlace(lineupPos, newPitcher)) {
            System.out.println("Pitcher is in the wrong position.");
            System.out.println(String.format("Cannot add %s to %d.", newPitcher.getName()
                                                                            , Integer.parseInt(parts[1])));
            System.out.println(String.format("Cause his rotation num is %d.", newPitcher.getRotation()));
            return;
        }
        this.pitcherLineup.set(lineupPos, newPitcher);
    }
    /**
     * remove batter or pitcher from the lineup.
     * @param str "1-3", "all", "Carlos Rodón", "1"
     * @param position ex: "batter", "pitcher"
     */
    public void removeFromTeam(String str, String position) {
        
        // List is empty then do nothing
        // the index fo the last element is size - 1;
        int listCount = 0; 
        if (position.equals(DEFAULT_BATTER)) {
            if (this.batterLineup.isEmpty()) {
                return;
            }
            listCount = this.batterLineup.size() - 1; 
        } else if (position.equals(DEFAULT_PITCHER)) {
            if (this.pitcherLineup.isEmpty()) {
                return;
            }
            listCount = this.pitcherLineup.size() - 1; 
        }

        str = str.trim();
        // remove range
        // "1-3"
        if (str.contains("-")) {
            String[] rangeNum = str.split("-");
            // turns num into index
            // index starts from 0
            int firstNum = Integer.parseInt(rangeNum[0]) - 1;
            int secondNum = Integer.parseInt(rangeNum[1]) - 1;  
            // if secondNum > the size of this.gameList, then we remove all the way to the last element
            secondNum = Math.min(secondNum, listCount);
            if (firstNum < 0 || secondNum < 0 || firstNum > secondNum || firstNum > listCount) {
                throw new IllegalArgumentException("Invalid range number!");
            } else {
                // +1 cuz the second parameter of sublist is exclusive
                for (int i = firstNum; i < secondNum + 1; i++ ) {
                    if (position.equals(DEFAULT_BATTER)) {
                        this.batterLineup.set(i, null);
                    } else if (position.equals(DEFAULT_PITCHER)) {
                        this.pitcherLineup.set(i, null);
                    }
                }
            }
        // remove all 
        // "all"
        } else if (str.contains(ALL)) {
            if (position.equals(DEFAULT_BATTER)) {
                this.clearBatterLineup();
            } else if (position.equals(DEFAULT_PITCHER)) {
                this.clearPitcherLineup();
            }
        // remove single name or single number             
        } else {
            // single number 
            // "9"
            if (str.matches("[0-9]+")) {
                // index must minus 1 to match the list index
                int index = Integer.parseInt(str) - 1;
                if (index > listCount) {
                    System.out.println("Invalid position number!");
                    return;
                }
                if (position.equals(DEFAULT_BATTER)) {
                    this.batterLineup.set(index, null);
                } else if (position.equals(DEFAULT_PITCHER)) {
                    this.pitcherLineup.set(index, null);
                }
            // single name   
            // "Austin Shenton"                             
            } else {
                String name = str.toLowerCase();
                if (position.equals(DEFAULT_BATTER)) {
                    for (Batter batter : this.batterLineup) {
                        if (batter.getName().toLowerCase().contains(name)) {
                            int index = this.batterLineup.indexOf(batter);
                            this.batterLineup.set(index, null);
                        }
                    }
                } else if (position.equals(DEFAULT_PITCHER)) {
                    for (Pitcher pitcher : this.pitcherLineup) {
                        if (pitcher.getName().toLowerCase().contains(name)) {
                            int index = this.pitcherLineup.indexOf(pitcher);
                            this.pitcherLineup.set(index, null);
                        }
                    }
                }

            }
        }        
    }
}
