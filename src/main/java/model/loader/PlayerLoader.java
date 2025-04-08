package model.loader;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import gameEnum.PlayerData;
import gameEnum.Teams;
import model.player.Batter;
import model.player.Pitcher;


public class PlayerLoader implements Loader{
    /** Standard csv delim. */
    private static final String DELIMITER = ",";
    /** default location of collection - relative to the resources directory. */
    private static final String DEFAULT_BATTER_DIR= "/BatterDataConvert/";
    private static final String DEFAULT_PITCHER_DIR= "/PitcherDataConvert/";
    private static final String DEFAULT_BATTER_POS= "batter";
    private static final String DEFAULT_PITCHER_POS= "pitcher";

    public PlayerLoader() { };

    @Override
    public Set<Batter> loadBatters(Teams teamName) {
        Set<Batter> batters = null;
        if (teamName.equals(Teams.MARINERS)) {
            String filePath = getFilePath(DEFAULT_BATTER_POS, teamName);
            batters = loadBatterFromFile(filePath);
        }
        return batters;
    }
    @Override
    public Set<Pitcher> loadPitchers(Teams teamName) {
        Set<Pitcher> pitchers = null;
        if (!teamName.equals(Teams.MARINERS)) {
            String filePath = getFilePath(DEFAULT_PITCHER_POS, teamName);
            pitchers = loadPitcherFromFile(filePath);
        }
        return pitchers;
    }
    private String getFilePath(String position, Teams teamName) {
        String filePath = null;
        if (position.equals("batter")) {
            if (!teamName.equals(Teams.MARINERS)) {
                throw new IllegalArgumentException("Only Mariners' batter lineup available for now.");
            }
            filePath = DEFAULT_BATTER_DIR + "Mariners-batter-convert.csv";
        } else if (position.equals("pitcher")) {
            filePath = DEFAULT_PITCHER_DIR + teamName.getCmdName() + "-pitcher-convert.csv";
        }
        return filePath;
    }
    private Set<Batter> loadBatterFromFile(String filePath) {
        Set<Batter> batters = new HashSet<>();

        List<String> lines;
        try {
            // this is so we can store the files in the resources folder
            InputStream is = PlayerLoader.class.getResourceAsStream(filePath);
            InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(isr);
            lines = reader.lines().collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("Error reading file: " + e.getMessage());
            return batters;
        }
        if (lines == null || lines.isEmpty()) {
            return batters;
        }

        Map<PlayerData, Integer> columnMap = processHeader(lines.remove(0));
        batters = lines.stream().map(line -> toBatter(line, columnMap))
                        .filter(batter -> batter != null).collect(Collectors.toSet());
        return batters;
    }

    private Set<Pitcher> loadPitcherFromFile(String filePath) {
        Set<Pitcher> pitchers = new HashSet<>();

        List<String> lines;
        try {
            // this is so we can store the files in the resources folder
            InputStream is = PlayerLoader.class.getResourceAsStream(filePath);
            InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(isr);
            lines = reader.lines().collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("Error reading file: " + e.getMessage());
            return pitchers;
        }
        if (lines == null || lines.isEmpty()) {
            return pitchers;
        }

        Map<PlayerData, Integer> columnMap = processHeader(lines.remove(0));
        pitchers = lines.stream().map(line -> toPitcher(line, columnMap))
                        .filter(pitcher -> pitcher != null).collect(Collectors.toSet());
        return pitchers;
    }

    /**
     * Converts a line from the csv file into a BoardGame object.
     * 
     * @param line      the line to convert
     * @param columnMap the map of columns to index
     * @return a BoardGame object
     */
    @Override
    public Pitcher toPitcher(String line, Map<PlayerData, Integer> columnMap) {
        String[] columns = line.split(DELIMITER);
        if (columns.length < columnMap.values().stream().max(Integer::compareTo).get()) {
            return null;
        }
        try {
            Pitcher pitcher = new Pitcher(columns[columnMap.get(PlayerData.NAME)],
                    Integer.parseInt(columns[columnMap.get(PlayerData.ROTATION)]),
                    Integer.parseInt(columns[columnMap.get(PlayerData.STRIKES)]),
                    Integer.parseInt(columns[columnMap.get(PlayerData.PITCHES)]),
                    Double.parseDouble(columns[columnMap.get(PlayerData.S_RATE)]),
                    Double.parseDouble(columns[columnMap.get(PlayerData.B_RATE)]),
                    Double.parseDouble(columns[columnMap.get(PlayerData.FOURSEAM)]),
                    Double.parseDouble(columns[columnMap.get(PlayerData.TWOSEAM)]),
                    Double.parseDouble(columns[columnMap.get(PlayerData.CUTTER)]),
                    Double.parseDouble(columns[columnMap.get(PlayerData.SINKER)]),
                    Double.parseDouble(columns[columnMap.get(PlayerData.SLIDER)]),
                    Double.parseDouble(columns[columnMap.get(PlayerData.CURVE)]),
                    Double.parseDouble(columns[columnMap.get(PlayerData.KNUCKLE)]),
                    Double.parseDouble(columns[columnMap.get(PlayerData.SWEEPER)]),
                    Double.parseDouble(columns[columnMap.get(PlayerData.SLURVE)]),
                    Double.parseDouble(columns[columnMap.get(PlayerData.SFINGER)]),
                    Double.parseDouble(columns[columnMap.get(PlayerData.CHANGEUP)]),
                    Double.parseDouble(columns[columnMap.get(PlayerData.FORK)]),
                    Double.parseDouble(columns[columnMap.get(PlayerData.SCREW)])
                    );
            return pitcher;
        } catch (NumberFormatException e) {
            // skip if there is an issue
            return null;
        }
    }

    /**
     * Converts a line from the csv file into a BoardGame object.
     * 
     * @param line      the line to convert
     * @param columnMap the map of columns to index
     * @return a BoardGame object
     */
    @Override
    public Batter toBatter(String line, Map<PlayerData, Integer> columnMap) {
        String[] columns = line.split(DELIMITER);
        if (columns.length < columnMap.values().stream().max(Integer::compareTo).get()) {
            return null;
        }
        try {
            Batter batter = new Batter(columns[columnMap.get(PlayerData.NAME)],
                    Integer.parseInt(columns[columnMap.get(PlayerData.F_PA)]),
                    Integer.parseInt(columns[columnMap.get(PlayerData.F_H)]),
                    Integer.parseInt(columns[columnMap.get(PlayerData.F_1B)]),
                    Integer.parseInt(columns[columnMap.get(PlayerData.F_2B)]),
                    Integer.parseInt(columns[columnMap.get(PlayerData.F_3B)]),
                    Integer.parseInt(columns[columnMap.get(PlayerData.F_HR)]),
                    Integer.parseInt(columns[columnMap.get(PlayerData.B_PA)]),
                    Integer.parseInt(columns[columnMap.get(PlayerData.B_H)]),
                    Integer.parseInt(columns[columnMap.get(PlayerData.B_1B)]),
                    Integer.parseInt(columns[columnMap.get(PlayerData.B_2B)]),
                    Integer.parseInt(columns[columnMap.get(PlayerData.B_3B)]),
                    Integer.parseInt(columns[columnMap.get(PlayerData.B_HR)]),
                    Integer.parseInt(columns[columnMap.get(PlayerData.O_PA)]),
                    Integer.parseInt(columns[columnMap.get(PlayerData.O_H)]),
                    Integer.parseInt(columns[columnMap.get(PlayerData.O_1B)]),
                    Integer.parseInt(columns[columnMap.get(PlayerData.O_2B)]),
                    Integer.parseInt(columns[columnMap.get(PlayerData.O_3B)]),
                    Integer.parseInt(columns[columnMap.get(PlayerData.O_HR)]),
                    Integer.parseInt(columns[columnMap.get(PlayerData.T_PA)]),
                    Integer.parseInt(columns[columnMap.get(PlayerData.T_H)]),
                    Integer.parseInt(columns[columnMap.get(PlayerData.T_1B)]),
                    Integer.parseInt(columns[columnMap.get(PlayerData.T_2B)]),
                    Integer.parseInt(columns[columnMap.get(PlayerData.T_3B)]),
                    Integer.parseInt(columns[columnMap.get(PlayerData.T_HR)]),
                    Double.parseDouble(columns[columnMap.get(PlayerData.Z_SWING)]),
                    Double.parseDouble(columns[columnMap.get(PlayerData.Z_CONTACT)]),
                    Double.parseDouble(columns[columnMap.get(PlayerData.C_SWING)]),
                    Double.parseDouble(columns[columnMap.get(PlayerData.C_CONTACT)]),
                    Double.parseDouble(columns[columnMap.get(PlayerData.AVG)]),
                    Double.parseDouble(columns[columnMap.get(PlayerData.OBP)]),
                    Double.parseDouble(columns[columnMap.get(PlayerData.OPS)])
                    );
            return batter;
        } catch (NumberFormatException e) {
            // skip if there is an issue
            return null;
        }
    }

    /**
     * Processes the header line to determine the column mapping.
     * 
     * It is common to do this for csv files as the columns can be in any order.
     * This makes it order independent by taking a moment to link the columns
     * with their actual index in the file.
     * 
     * @param header the header line
     * @return a map of column to index
     */
    private Map<PlayerData, Integer> processHeader(String header) {
        Map<PlayerData, Integer> columnMap = new HashMap<>();
        String[] columns = header.split(DELIMITER);
        for (int i = 0; i < columns.length; i++) {
            try {
                PlayerData col = PlayerData.fromColumnName(columns[i]);
                columnMap.put(col, i);
            } catch (IllegalArgumentException e) {
                // System.out.println("Ignoring column: " + columns[i]);
            }
        }
        return columnMap;
    }

}
