package controller.stubs;

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

  private static final Teams DEFAULT_PLAYER_TEAM = Teams.MARINERS;
  private static final Teams DEFAULT_RANDOM_COM_TEAM = Teams.randomTeam();
  private static final String DEFAULT_SORT_ON_NAME = "Name";
  private ComTeam comTeam = null;
  private PlayerTeam playerTeam = null;
  private SimulationResult gameResult = null;
  private PlayerSorter sorter = new PlayerSorter();
  private PlayerFilter filter = new PlayerFilter();

  public Model() {
  }

  public Team getPlayerTeam() {
    return this.playerTeam;
  }

  public Team getComTeam() {
    return this.comTeam;
  }

  public Set<Batter> getPlayerTeamBatterLoaderLineup() {
    return this.playerTeam.getBatterLoaderLineup();
  }

  public Set<Pitcher> getPlayerTeamPitcherLoaderLineup() {
    return this.playerTeam.getPitcherLoaderLineup();
  }

  public Set<Batter> getComTeamBatterLoaderLineup() {
    return this.comTeam.getBatterLoaderLineup();
  }

  public Set<Pitcher> getComTeamPitcherLoaderLineup() {
    return this.comTeam.getPitcherLoaderLineup();
  }

  public void setPlayerTeam() {
    this.setPlayerTeam(DEFAULT_PLAYER_TEAM);
  }

  public void setPlayerTeam(Teams teamName) {
    this.playerTeam = new PlayerTeam(teamName);
  }

  public void setComTeam() {
    this.setComTeam(DEFAULT_RANDOM_COM_TEAM);
  }

  public void setComTeam(Teams teamName) {
    this.comTeam = new ComTeam(teamName);
  }

  public Player getBatter(Side side, String batterName) {
    Player returnBatter = null;
    if (side.equals(Side.PLAYER)) {
      return this.playerTeam.getBatterFromLoader(batterName);
    } else if (side.equals(Side.COMPUTER)) {
      return this.comTeam.getBatterFromLoader(batterName);
    }
    return returnBatter;
  }

  public Player getPitcher(Side side, String pitcherName) {
    Player returnPitcher = null;
    if (side.equals(Side.PLAYER)) {
      return this.playerTeam.getPitcherFromLoader(pitcherName);
    } else if (side.equals(Side.COMPUTER)) {
      return this.comTeam.getPitcherFromLoader(pitcherName);
    }
    return returnPitcher;
  }

  /**
   * true if the string only contains one filter, else false.
   * 
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

  public Stream<Batter> batterFilter(String filter, Set<Batter> batterLoaderLineup) {
    // true: single filter
    // false: multi filter
    Stream<Batter> filteredStream = (checkFilterNum(filter))
        ? filterSingleForBatter(filter, batterLoaderLineup.stream())
        : filterMultiForBatter(filter, batterLoaderLineup.stream());
    filteredStream = filteredStream.sorted(sorter.getBatterSortType(DEFAULT_SORT_ON_NAME, true));
    return filteredStream;
  }

  public Stream<Batter> batterFilter(String filter, PlayerData sortOn, Set<Batter> batterLoaderLineup) {
    // true: single filter
    // false: multi filter
    Stream<Batter> filteredStream = (checkFilterNum(filter))
        ? filterSingleForBatter(filter, batterLoaderLineup.stream())
        : filterMultiForBatter(filter, batterLoaderLineup.stream());
    filteredStream = filteredStream.sorted(sorter.getBatterSortType(sortOn.getColumnName(), true));
    return filteredStream;
  }

  public Stream<Batter> batterFilter(String filter, PlayerData sortOn,
      boolean ascending, Set<Batter> batterLoaderLineup) {
    // true: single filter
    // false: multi filter
    Stream<Batter> filteredStream = (checkFilterNum(filter))
        ? filterSingleForBatter(filter, batterLoaderLineup.stream())
        : filterMultiForBatter(filter, batterLoaderLineup.stream());
    filteredStream = filteredStream.sorted(sorter.getBatterSortType(sortOn.getColumnName(), ascending));
    return filteredStream;
  }

  public Stream<Pitcher> pitcherFilter(String filter, Set<Pitcher> pitcherLoaderLineup) {
    // true: single filter
    // false: multi filter
    Stream<Pitcher> filteredStream = (checkFilterNum(filter))
        ? filterSingleForPitcher(filter, pitcherLoaderLineup.stream())
        : filterMultiForPitcher(filter, pitcherLoaderLineup.stream());
    filteredStream = filteredStream.sorted(sorter.getPitcherSortType(DEFAULT_SORT_ON_NAME, true));
    return filteredStream;
  }

  public Stream<Pitcher> pitcherFilter(String filter, PlayerData sortOn, Set<Pitcher> pitcherLoaderLineup) {
    // true: single filter
    // false: multi filter
    Stream<Pitcher> filteredStream = (checkFilterNum(filter))
        ? filterSingleForPitcher(filter, pitcherLoaderLineup.stream())
        : filterMultiForPitcher(filter, pitcherLoaderLineup.stream());
    filteredStream = filteredStream.sorted(sorter.getPitcherSortType(sortOn.getColumnName(), true));
    return filteredStream;
  }

  public Stream<Pitcher> pitcherFilter(String filter, PlayerData sortOn,
      boolean ascending, Set<Pitcher> pitcherLoaderLineup) {
    // true: single filter
    // false: multi filter
    Stream<Pitcher> filteredStream = (checkFilterNum(filter))
        ? filterSingleForPitcher(filter, pitcherLoaderLineup.stream())
        : filterMultiForPitcher(filter, pitcherLoaderLineup.stream());
    filteredStream = filteredStream.sorted(sorter.getPitcherSortType(sortOn.getColumnName(), ascending));
    return filteredStream;
  }

  /**
   * processes string with one filter.
   * 
   * @param filterString    string contains one filter
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
   * 
   * @param filterString    string contains 2+ filter
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
   * 
   * @param filterString     string contains one filter
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
   * 
   * @param filterString     string contains 2+ filter
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

  public void addBatterToLineup(Side side, String filterString, Stream<Batter> filteredBatters) {
    if (side.equals(Side.PLAYER)) {
      this.playerTeam.addBatterToTeam(filterString, filteredBatters);
    } else if (side.equals(Side.COMPUTER)) {
      this.comTeam.addBatterToTeam(filterString, filteredBatters);
    }
  }

  public void addPitcherToLineup(Side side, String filterString, Stream<Pitcher> filteredPitchers) {
    if (side.equals(Side.PLAYER)) {
      this.playerTeam.addPitcherToTeam(filterString, filteredPitchers);
    } else if (side.equals(Side.COMPUTER)) {
      this.comTeam.addPitcherToTeam(filterString, filteredPitchers);
    }
  }

  public void removeFromLineup(Side side, String position, String filerString) {
    if (side.equals(Side.PLAYER)) {
      this.playerTeam.removeFromTeam(filerString, position);
    } else {
      this.comTeam.removeFromTeam(filerString, position);
    }
  }

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

  public SimulationResult startSimAndGetResult() {
    Simulation game = new Simulation(this.playerTeam, this.comTeam);
    this.gameResult = game.runSimulation();
    return this.gameResult;
  }

  public void saveLineupAsCSVFile(Side side, String filename, List<String> lines) {
    if (!filename.contains(".csv")) {
      throw new IllegalArgumentException("Save as txt file only.");
    }
    try {
      if (lines == null) {
        throw new IllegalArgumentException("Lineup cannot be null.");
      }
      Files.write(Path.of(filename), lines);
    } catch (IOException e) {
      System.err.println("Error writing to file: " + e.getMessage());
    }
  }

  public void saveGameDetailsAsTXTFile(String filename) {
    if (!filename.contains(".txt")) {
      throw new IllegalArgumentException("Save as txt file only.");
    }

    if (this.gameResult != null) {
      String gameResultDetails = this.gameResult.getDetails();
      List<String> lines = new ArrayList<>();
      lines.add(gameResultDetails);
      try {
        Files.write(Path.of(filename), lines);
      } catch (IOException e) {
        System.err.println("Error writing to file: " + e.getMessage());
      }
    }
  }

  public static void main(String[] args) {
    Team playerTeam = new PlayerTeam(Teams.MARINERS);
    Team comTeam = new ComTeam(Teams.DODGERS);

  }

}
