classDiagram
direction BT
class AddCommandInfo {
  + AddCommandInfo() 
}
class Balls {
<<enumeration>>
  + Balls() 
  + values() Balls[]
  + valueOf(String) Balls
}
class Batter {
  + Batter(String, int, int, int, int, int, int, int, int, int, int, int, int, int, int, int, int, int, int, int, int, int, int, int, int, double, double, double, double, double, double, double) 
  - int totalPA
  - int breaking3B
  - int offspeed2B
  - int breakingH
  - int total2B
  - int fastball1B
  - int fastballH
  - int fastballHR
  - int fastball3B
  - int breakingHR
  - int total1B
  - double chaseSwing
  - double zoneSwing
  - int offspeed3B
  - int offspeedPA
  - int offspeedHR
  - double chaseContact
  - int totalH
  - double OPS
  - double AVG
  - int offspeed1B
  - int breakingPA
  - int fastball2B
  - int breaking2B
  - int breaking1B
  - int totalHR
  - int fastballPA
  - int offspeedH
  - double zoneContact
  - double OBP
  - int total3B
  + equals(Object) boolean
  + hashcode() int
  + toString() String
   int fastballHR
   double OBP
   int breakingH
   int fastball3B
   int fastball1B
   int fastball2B
   int fastballPA
   String position
   int fastballH
   double OPS
   int totalH
   int totalHR
   int breakingHR
   double zoneContact
   int breaking1B
   int offspeedHR
   int total1B
   int breakingPA
   int breaking2B
   int breaking3B
   int offspeedH
   int offspeed3B
   int offspeed2B
   int total3B
   int total2B
   double AVG
   int totalPA
   int offspeedPA
   double chaseSwing
   int offspeed1B
   double chaseContact
   double zoneSwing
}
class BatterTest {
  + BatterTest() 
  ~ testGetters() void
  ~ testConstructorAndBasicGetters() void
  ~ setUp() void
  ~ testEquals() void
  ~ testToString() void
}
class ComTeam {
  + ComTeam(Teams) 
}
class Filter {
<<Interface>>
  + batterFilter(Batter, PlayerData, Operations, String) boolean
  + filterDouble(double, Operations, String) boolean
  + pitcherFilter(Pitcher, PlayerData, Operations, String) boolean
  + filterString(String, Operations, String) boolean
  + filterNum(int, Operations, String) boolean
}
class FilterCriteria {
  + FilterCriteria() 
  ~ hasSort() boolean
}
class Hits {
<<enumeration>>
  + Hits() 
  + values() Hits[]
  + valueOf(String) Hits
}
class Inning {
<<Interface>>
  + resetInning() void
  + runInning(Team, int) int
}
class Loader {
<<Interface>>
  + toPitcher(String, Map~PlayerData, Integer~) Pitcher
  + loadBatters(Teams) Set~Batter~
  + toBatter(String, Map~PlayerData, Integer~) Batter
  + loadPitchers(Teams) Set~Pitcher~
}
class MLBSimulator {
  + MLBSimulator() 
  + main(String[]) void
}
class MLBSimulatorController {
  + MLBSimulatorController() 
  + MLBSimulatorController(UIInterface) 
  - extractCommand(String[]) String
  - handlePlayerCommand(String[]) void
  - resetFilter(boolean) void
  - handleFilterCommand(String[], Side) void
  - getBatterFromCommand(AddCommandInfo) Batter?
  - parseSimulateOptions(String[]) Map~String, String~
  - handleComputerShowCommand(String[]) void
  - handleComputerCommand(String[]) void
  - validateAddToCommand(String, boolean) AddCommandInfo
  - processCommand(String) void
  - handlePlayerShowCommand(String[]) void
  - getPitcherFromCommand(AddCommandInfo) Pitcher?
  + start() void
  - applyFilterOnly(boolean, String) void
  - formatOutputFilename(String, int) String
  - runSimulation(String[]) void
  - applyFilterAndSort(boolean, String, PlayerData, boolean) void
  + stop() void
  - displayCurrentFilter(boolean) void
  - isEmpty(List~T~) boolean
  - parseFilterCriteria(String[]) FilterCriteria
  - containsIllegalCharacters(String) boolean
  - addPlayer(AddCommandInfo, boolean, String) void
}
class MLBSimulatorControllerInterface {
<<Interface>>
  + stop() void
  + start() void
}
class MLBSimulatorControllerTest {
  + MLBSimulatorControllerTest() 
  ~ testProcessCommandEmpty() void
  ~ testExtractCommand() void
  ~ testValidateAddToCommandWithNullLists() void
  ~ testHandlePlayerCommandAddTooShort() void
  ~ testProcessCommandSimulate() void
  ~ testHandlePlayerCommandRemoveTooShort() void
  ~ testHandlePlayerCommandWithInvalidSubcommand() void
  ~ testHandleComputerShowCommandEdgeCases() void
  ~ testProcessCommandExit() void
  ~ testHandleFilterCommandWithComputer() void
  ~ testParseFilterCriteria() void
  ~ testHandleComputerCommandTooShort() void
  ~ testResetFilter() void
  ~ testRunSimulationWithOutfileAppending() void
  ~ testHandlePlayerShowCommandWithDifferentSubcommands() void
  ~ testGetPitcherFromCommand() void
  ~ testValidateAddToCommandWithNegativeIndex() void
  ~ testHandlePlayerShowCommand() void
  ~ testValidateAddToCommand() void
  ~ testHandleFilterCommand() void
  ~ testRunSimulation() void
  ~ testAddPlayerWithNullPlayer() void
  ~ testHandleFilterCommandWithSort() void
  ~ testParseSimulateOptions() void
  ~ testHandleFilterCommandWithEmptyFilter() void
  ~ testIsEmpty() void
  ~ testHandleComputerCommandEdgeCases() void
  ~ testHandleComputerCommand() void
  ~ setUp() void
  ~ testHandlePlayerCommandTooShort() void
  ~ testProcessCommandComputer() void
  ~ testProcessCommandIllegalCharacter() void
  ~ testAddPlayer() void
  ~ testProcessCommandWhitespace() void
  ~ testProcessCommandHelp() void
  ~ testProcessCommandPlayer() void
  ~ testProcessCommandInvalid() void
  ~ testContainsIllegalCharacters() void
  ~ testRunSimulationWithNullResultsAndOutfile() void
  ~ testRunSimulationWithInvalidNumber() void
  ~ testApplyFilterAndSortWithEmptyResults() void
  ~ testFormatOutputFilename() void
  ~ testGetBatterFromCommand() void
  ~ testRunSimulationWithMultipleSimulationsAndOutfile() void
  ~ testRunSimulationWithOutfile() void
  ~ testDisplayCurrentFilterWithNullOrEmpty() void
  ~ testHandlePlayerCommand() void
  ~ testHandlePlayerShowCommandTooShort() void
  ~ testHandlePlayerShowCommandBatterNotFound() void
  ~ testDisplayCurrentFilter() void
  ~ testHandleComputerShowCommand() void
  ~ testHandleComputerShowCommandTooShort() void
  ~ testParseFilterCriteriaWithDirectionKeywordsOnly() void
  ~ testApplyFilterOnly() void
}
class Model {
  + Model() 
  - ComTeam comTeam
  - PlayerTeam playerTeam
  - checkFilterNum(String) boolean
  + convertLineupToString(List~Player~) List~String~
  + batterFilter(String, PlayerData, boolean, Set~Batter~) Stream~Batter~
  + removeFromLineup(Side, String, String) void
  + batterFilter(String, Set~Batter~) Stream~Batter~
  + addBatterToLineup(Side, String, Stream~Batter~) void
  + batterFilter(String, PlayerData, Set~Batter~) Stream~Batter~
  + pitcherFilter(String, Set~Pitcher~) Stream~Pitcher~
  + getBatter(Side, String) Batter
  + saveGameDetailsAsTXTFile(String) void
  + addPitcherToLineup(Side, String, Stream~Pitcher~) void
  + pitcherFilter(String, PlayerData, Set~Pitcher~) Stream~Pitcher~
  - filterSingleForPitcher(String, Stream~Pitcher~) Stream~Pitcher~
  + startSimAndGetResult() SimulationResult
  + setPlayerTeam() void
  + pitcherFilter(String, PlayerData, boolean, Set~Pitcher~) Stream~Pitcher~
  - filterMultiForPitcher(String, Stream~Pitcher~) Stream~Pitcher~
  - filterSingleForBatter(String, Stream~Batter~) Stream~Batter~
  + clearLineup(Side, String) void
  + saveLineupAsTXTFile(Side, String, List~Player~) void
  - filterMultiForBatter(String, Stream~Batter~) Stream~Batter~
  + setComTeam() void
  + getPitcher(Side, String) Pitcher
   Team comTeam
   List~Pitcher~ playerTeamPitcherLineup
   Set~Batter~ comTeamBatterLoaderLineup
   List~Pitcher~ comTeamPitcherLineup
   Set~Batter~ playerTeamBatterLoaderLineup
   Team playerTeam
   List~Batter~ comTeamBatterLineup
   List~String~ allColumnName
   Set~Pitcher~ playerTeamPitcherLoaderLineup
   List~Batter~ playerTeamBatterLineup
   Set~Pitcher~ comTeamPitcherLoaderLineup
   List~String~ allTeamName
}
class ModelInterface {
<<Interface>>
  + addPitcherToLineup(Side, String, Stream~Pitcher~) void
  + getBatter(Side, String) Batter
  + saveGameDetailsAsTXTFile(String) void
  + clearLineup(Side, String) void
  + addBatterToLineup(Side, String, Stream~Batter~) void
  + convertLineupToString(List~Player~) List~String~
  + saveLineupAsTXTFile(Side, String, List~Player~) void
  + removeFromLineup(Side, String, String) void
  + getPitcher(Side, String) Pitcher
  + startSimAndGetResult() SimulationResult
   Team comTeam
   List~Pitcher~ playerTeamPitcherLineup
   Set~Batter~ comTeamBatterLoaderLineup
   List~Pitcher~ comTeamPitcherLineup
   Set~Batter~ playerTeamBatterLoaderLineup
   Team playerTeam
   List~Batter~ comTeamBatterLineup
   Set~Pitcher~ playerTeamPitcherLoaderLineup
   List~Batter~ playerTeamBatterLineup
   Set~Pitcher~ comTeamPitcherLoaderLineup
   List~String~ allTeamName
}
class Operations {
<<enumeration>>
  - Operations(String) 
  - String operator
  + fromOperator(String) Operations
  + getOperatorFromStr(String) Operations?
  + valueOf(String) Operations
  + values() Operations[]
   String operator
}
class Outs {
<<enumeration>>
  + Outs() 
  + values() Outs[]
  + valueOf(String) Outs
}
class Pitcher {
  + Pitcher(String, int, int, int, double, double, double, double, double, double, double, double, double, double, double, double, double, double, double) 
  - double sinker
  - double fourSeam
  - double splitFinger
  - double changeup
  - double knuckle
  - double slider
  - double curve
  - double ballsRate
  - double strikesRate
  - double twoSeam
  - double screw
  - int pitches
  - double cutter
  - double fork
  - int strikes
  - int rotation
  - double sweeper
  - double slurve
  + equals(Object) boolean
  + toString() String
  + hashcode() int
   double slider
   double cutter
   double curve
   int strikes
   double ballsRate
   int pitches
   double fourSeam
   double knuckle
   double screw
   double sinker
   String position
   double fork
   double splitFinger
   double twoSeam
   double slurve
   double sweeper
   double changeup
   double strikesRate
   int rotation
}
class PitcherTest {
  + PitcherTest() 
  ~ testEquals() void
  ~ setUp() void
  ~ testToString() void
  ~ testGetters() void
}
class Player {
  + Player(String) 
  - String name
   String name
   String position
}
class PlayerData {
<<enumeration>>
  - PlayerData(String) 
  - String columnName
  + values() PlayerData[]
  + valueOf(String) PlayerData
  + fromString(String) PlayerData
  + fromColumnName(String) PlayerData
   String columnName
}
class PlayerFilter {
  + PlayerFilter() 
  + filterNum(int, Operations, String) boolean
  + filterString(String, Operations, String) boolean
  + filterDouble(double, Operations, String) boolean
  + batterFilter(Batter, PlayerData, Operations, String) boolean
  + pitcherFilter(Pitcher, PlayerData, Operations, String) boolean
}
class PlayerInterface {
<<Interface>>
   String name
   String position
}
class PlayerLoader {
  + PlayerLoader() 
  + toBatter(String, Map~PlayerData, Integer~) Batter
  - processHeader(String) Map~PlayerData, Integer~
  - getFilePath(String, Teams) String
  + toPitcher(String, Map~PlayerData, Integer~) Pitcher
  - loadBatterFromFile(String) Set~Batter~
  + loadBatters(Teams) Set~Batter~
  - loadPitcherFromFile(String) Set~Pitcher~
  + loadPitchers(Teams) Set~Pitcher~
}
class PlayerSorter {
  + PlayerSorter() 
  + getBatterSortType(String, boolean) Comparator~Batter~
  + getPitcherSortType(String, boolean) Comparator~Pitcher~
}
class PlayerTeam {
  + PlayerTeam(Teams) 
}
class RegularInning {
  + RegularInning(Pitcher) 
  - int singles
  - int hits
  - int strikeouts
  - int homeRuns
  - Map~String, Integer~ pitchCategoryCounts
  - int pitchesThrown
  - int triples
  - int battersFaced
  - int walks
  - int doubles
  - int currentBatterIndex
  - getPitchCategory(String) String
  - determinePitchType() String
  - determineHitOutcome(Batter, String) int
  + getPitchTypeCount(String) int
  + resetInning() void
  # advanceRunners(Hits) int
  # advanceRunnersOnWalk() int
  - inningAtBat(Batter) int
  + runInning(Team, int) int
   int battersFaced
   int doubles
   int currentBatterIndex
   int triples
   int homeRuns
   Map~String, Integer~ pitchCategoryCounts
   Map~String, Integer~ allPitchTypeCounts
   int singles
   int walks
   int strikeouts
   int hits
   int pitchesThrown
}
class Side {
<<enumeration>>
  - Side(String) 
  - String cmdName
  + values() Side[]
  + fromString(String) Side
  + fromCmdName(String) Side
  + valueOf(String) Side
   String cmdName
}
class Simulation {
  + Simulation(PlayerTeam, ComTeam) 
  + runSimulation() SimulationResult
  - calculateTotalScore() int
  - validateTeamsBeforeSimulation() void
  - getCurrentPitcher(int) Pitcher
   String playerTeamName
}
class SimulationResult {
  + SimulationResult(String, String) 
  - int totalPitchesThrown
  - int totalTriples
  - int[] inningScores
  - int totalWalks
  - int totalHits
  - String details
  - int playerTeamScore
  - String playerTeamName
  - int totalStrikeouts
  - int totalHomeRuns
  - int totalSingles
  - Map~String, Integer~ pitchCategoryCounts
  - int totalDoubles
  - String comTeamName
  - Map~String, Integer~ pitchTypeCounts
  + addPitchCategoryCounts(Map~String, Integer~) void
  + addSingles(int) void
  + toString() String
  + addPitchTypeCounts(Map~String, Integer~) void
  + addWalks(int) void
  + addHits(int) void
  + addStrikeouts(int) void
  + addPitchesThrown(int) void
  + addTriples(int) void
  + addDoubles(int) void
  + addHR(int) void
   Map~String, Integer~ pitchTypeCounts
   String totalStatistics
   Map~String, Integer~ pitchCategoryCounts
   int totalSingles
   int totalTriples
   String details
   String totalScore
   int totalDoubles
   String comTeamName
   int totalStrikeouts
   int totalHomeRuns
   int totalWalks
   int[] inningScores
   int playerTeamScore
   int totalHits
   String playerTeamName
   int totalPitchesThrown
}
class SortByAVG {
  + SortByAVG() 
  + compare(Batter, Batter) int
}
class SortByAVGDesc {
  + SortByAVGDesc() 
  + compare(Batter, Batter) int
}
class SortByBallsRate {
  + SortByBallsRate() 
  + compare(Pitcher, Pitcher) int
}
class SortByBallsRateDesc {
  + SortByBallsRateDesc() 
  + compare(Pitcher, Pitcher) int
}
class SortByBatterName {
  + SortByBatterName() 
  + compare(Batter, Batter) int
}
class SortByBatterNameDesc {
  + SortByBatterNameDesc() 
  + compare(Batter, Batter) int
}
class SortByBreaking1B {
  + SortByBreaking1B() 
  + compare(Batter, Batter) int
}
class SortByBreaking1BDesc {
  + SortByBreaking1BDesc() 
  + compare(Batter, Batter) int
}
class SortByBreaking2B {
  + SortByBreaking2B() 
  + compare(Batter, Batter) int
}
class SortByBreaking2BDesc {
  + SortByBreaking2BDesc() 
  + compare(Batter, Batter) int
}
class SortByBreaking3B {
  + SortByBreaking3B() 
  + compare(Batter, Batter) int
}
class SortByBreaking3BDesc {
  + SortByBreaking3BDesc() 
  + compare(Batter, Batter) int
}
class SortByBreakingH {
  + SortByBreakingH() 
  + compare(Batter, Batter) int
}
class SortByBreakingHDesc {
  + SortByBreakingHDesc() 
  + compare(Batter, Batter) int
}
class SortByBreakingHR {
  + SortByBreakingHR() 
  + compare(Batter, Batter) int
}
class SortByBreakingHRDesc {
  + SortByBreakingHRDesc() 
  + compare(Batter, Batter) int
}
class SortByBreakingPA {
  + SortByBreakingPA() 
  + compare(Batter, Batter) int
}
class SortByBreakingPADesc {
  + SortByBreakingPADesc() 
  + compare(Batter, Batter) int
}
class SortByChangeup {
  + SortByChangeup() 
  + compare(Pitcher, Pitcher) int
}
class SortByChangeupDesc {
  + SortByChangeupDesc() 
  + compare(Pitcher, Pitcher) int
}
class SortByChaseContact {
  + SortByChaseContact() 
  + compare(Batter, Batter) int
}
class SortByChaseContactDesc {
  + SortByChaseContactDesc() 
  + compare(Batter, Batter) int
}
class SortByChaseSwing {
  + SortByChaseSwing() 
  + compare(Batter, Batter) int
}
class SortByChaseSwingDesc {
  + SortByChaseSwingDesc() 
  + compare(Batter, Batter) int
}
class SortByCurveball {
  + SortByCurveball() 
  + compare(Pitcher, Pitcher) int
}
class SortByCurveballDesc {
  + SortByCurveballDesc() 
  + compare(Pitcher, Pitcher) int
}
class SortByCutter {
  + SortByCutter() 
  + compare(Pitcher, Pitcher) int
}
class SortByCutterDesc {
  + SortByCutterDesc() 
  + compare(Pitcher, Pitcher) int
}
class SortByFastball1B {
  + SortByFastball1B() 
  + compare(Batter, Batter) int
}
class SortByFastball1BDesc {
  + SortByFastball1BDesc() 
  + compare(Batter, Batter) int
}
class SortByFastball2B {
  + SortByFastball2B() 
  + compare(Batter, Batter) int
}
class SortByFastball2BDesc {
  + SortByFastball2BDesc() 
  + compare(Batter, Batter) int
}
class SortByFastball3B {
  + SortByFastball3B() 
  + compare(Batter, Batter) int
}
class SortByFastball3BDesc {
  + SortByFastball3BDesc() 
  + compare(Batter, Batter) int
}
class SortByFastballH {
  + SortByFastballH() 
  + compare(Batter, Batter) int
}
class SortByFastballHDesc {
  + SortByFastballHDesc() 
  + compare(Batter, Batter) int
}
class SortByFastballHR {
  + SortByFastballHR() 
  + compare(Batter, Batter) int
}
class SortByFastballHRDesc {
  + SortByFastballHRDesc() 
  + compare(Batter, Batter) int
}
class SortByFastballPA {
  + SortByFastballPA() 
  + compare(Batter, Batter) int
}
class SortByFastballPADesc {
  + SortByFastballPADesc() 
  + compare(Batter, Batter) int
}
class SortByFork {
  + SortByFork() 
  + compare(Pitcher, Pitcher) int
}
class SortByForkDesc {
  + SortByForkDesc() 
  + compare(Pitcher, Pitcher) int
}
class SortByFourSeam {
  + SortByFourSeam() 
  + compare(Pitcher, Pitcher) int
}
class SortByFourSeamDesc {
  + SortByFourSeamDesc() 
  + compare(Pitcher, Pitcher) int
}
class SortByKnuckle {
  + SortByKnuckle() 
  + compare(Pitcher, Pitcher) int
}
class SortByKnuckleDesc {
  + SortByKnuckleDesc() 
  + compare(Pitcher, Pitcher) int
}
class SortByOBP {
  + SortByOBP() 
  + compare(Batter, Batter) int
}
class SortByOBPDesc {
  + SortByOBPDesc() 
  + compare(Batter, Batter) int
}
class SortByOPS {
  + SortByOPS() 
  + compare(Batter, Batter) int
}
class SortByOPSDesc {
  + SortByOPSDesc() 
  + compare(Batter, Batter) int
}
class SortByOffspeed1B {
  + SortByOffspeed1B() 
  + compare(Batter, Batter) int
}
class SortByOffspeed1BDesc {
  + SortByOffspeed1BDesc() 
  + compare(Batter, Batter) int
}
class SortByOffspeed2B {
  + SortByOffspeed2B() 
  + compare(Batter, Batter) int
}
class SortByOffspeed2BDesc {
  + SortByOffspeed2BDesc() 
  + compare(Batter, Batter) int
}
class SortByOffspeed3B {
  + SortByOffspeed3B() 
  + compare(Batter, Batter) int
}
class SortByOffspeed3BDesc {
  + SortByOffspeed3BDesc() 
  + compare(Batter, Batter) int
}
class SortByOffspeedH {
  + SortByOffspeedH() 
  + compare(Batter, Batter) int
}
class SortByOffspeedHDesc {
  + SortByOffspeedHDesc() 
  + compare(Batter, Batter) int
}
class SortByOffspeedHR {
  + SortByOffspeedHR() 
  + compare(Batter, Batter) int
}
class SortByOffspeedHRDesc {
  + SortByOffspeedHRDesc() 
  + compare(Batter, Batter) int
}
class SortByOffspeedPA {
  + SortByOffspeedPA() 
  + compare(Batter, Batter) int
}
class SortByOffspeedPADesc {
  + SortByOffspeedPADesc() 
  + compare(Batter, Batter) int
}
class SortByPitcherName {
  + SortByPitcherName() 
  + compare(Pitcher, Pitcher) int
}
class SortByPitcherNameDesc {
  + SortByPitcherNameDesc() 
  + compare(Pitcher, Pitcher) int
}
class SortByPitches {
  + SortByPitches() 
  + compare(Pitcher, Pitcher) int
}
class SortByPitchesDesc {
  + SortByPitchesDesc() 
  + compare(Pitcher, Pitcher) int
}
class SortByRotation {
  + SortByRotation() 
  + compare(Pitcher, Pitcher) int
}
class SortByRotationDesc {
  + SortByRotationDesc() 
  + compare(Pitcher, Pitcher) int
}
class SortBySFinger {
  + SortBySFinger() 
  + compare(Pitcher, Pitcher) int
}
class SortBySFingerDesc {
  + SortBySFingerDesc() 
  + compare(Pitcher, Pitcher) int
}
class SortByScrew {
  + SortByScrew() 
  + compare(Pitcher, Pitcher) int
}
class SortByScrewDesc {
  + SortByScrewDesc() 
  + compare(Pitcher, Pitcher) int
}
class SortBySinker {
  + SortBySinker() 
  + compare(Pitcher, Pitcher) int
}
class SortBySinkerDesc {
  + SortBySinkerDesc() 
  + compare(Pitcher, Pitcher) int
}
class SortBySlider {
  + SortBySlider() 
  + compare(Pitcher, Pitcher) int
}
class SortBySliderDesc {
  + SortBySliderDesc() 
  + compare(Pitcher, Pitcher) int
}
class SortBySlurve {
  + SortBySlurve() 
  + compare(Pitcher, Pitcher) int
}
class SortBySlurveDesc {
  + SortBySlurveDesc() 
  + compare(Pitcher, Pitcher) int
}
class SortByStrikes {
  + SortByStrikes() 
  + compare(Pitcher, Pitcher) int
}
class SortByStrikesDesc {
  + SortByStrikesDesc() 
  + compare(Pitcher, Pitcher) int
}
class SortByStrikesRate {
  + SortByStrikesRate() 
  + compare(Pitcher, Pitcher) int
}
class SortByStrikesRateDesc {
  + SortByStrikesRateDesc() 
  + compare(Pitcher, Pitcher) int
}
class SortBySweeper {
  + SortBySweeper() 
  + compare(Pitcher, Pitcher) int
}
class SortBySweeperDesc {
  + SortBySweeperDesc() 
  + compare(Pitcher, Pitcher) int
}
class SortByTotal1B {
  + SortByTotal1B() 
  + compare(Batter, Batter) int
}
class SortByTotal1BDesc {
  + SortByTotal1BDesc() 
  + compare(Batter, Batter) int
}
class SortByTotal2B {
  + SortByTotal2B() 
  + compare(Batter, Batter) int
}
class SortByTotal2BDesc {
  + SortByTotal2BDesc() 
  + compare(Batter, Batter) int
}
class SortByTotal3B {
  + SortByTotal3B() 
  + compare(Batter, Batter) int
}
class SortByTotal3BDesc {
  + SortByTotal3BDesc() 
  + compare(Batter, Batter) int
}
class SortByTotalH {
  + SortByTotalH() 
  + compare(Batter, Batter) int
}
class SortByTotalHDesc {
  + SortByTotalHDesc() 
  + compare(Batter, Batter) int
}
class SortByTotalHR {
  + SortByTotalHR() 
  + compare(Batter, Batter) int
}
class SortByTotalHRDesc {
  + SortByTotalHRDesc() 
  + compare(Batter, Batter) int
}
class SortByTotalPA {
  + SortByTotalPA() 
  + compare(Batter, Batter) int
}
class SortByTotalPADesc {
  + SortByTotalPADesc() 
  + compare(Batter, Batter) int
}
class SortByTwoSeam {
  + SortByTwoSeam() 
  + compare(Pitcher, Pitcher) int
}
class SortByTwoSeamDesc {
  + SortByTwoSeamDesc() 
  + compare(Pitcher, Pitcher) int
}
class SortByZoneContact {
  + SortByZoneContact() 
  + compare(Batter, Batter) int
}
class SortByZoneContactDesc {
  + SortByZoneContactDesc() 
  + compare(Batter, Batter) int
}
class SortByZoneSwing {
  + SortByZoneSwing() 
  + compare(Batter, Batter) int
}
class SortByZoneSwingDesc {
  + SortByZoneSwingDesc() 
  + compare(Batter, Batter) int
}
class Sorter {
<<Interface>>
  + getBatterSortType(String, boolean) Comparator~Batter~
  + getPitcherSortType(String, boolean) Comparator~Pitcher~
}
class Strikes {
<<enumeration>>
  + Strikes() 
  + values() Strikes[]
  + valueOf(String) Strikes
}
class Team {
  + Team(Teams) 
  - Set~Pitcher~ pitcherLoaderLineup
  - List~Pitcher~ pitcherLineup
  - List~Batter~ batterLineup
  - Set~Batter~ batterLoaderLineup
  + checkPitcherInLineup(Pitcher) boolean
  + getBatterFromLoader(String) Batter
  + addBatterToTeam(String, Stream~Batter~) void
  + checkPitcherLineupSpace() int
  + clearPitcherLineup() void
  + addPitcherToTeam(String, Stream~Pitcher~) void
  + checkBatterInLineup(Batter) boolean
  + getPitcherFromLoader(String) Pitcher
  + clearBatterLineup() void
  + checkPitcherInTheRightPlace(int, Pitcher) boolean
  + removeFromTeam(String, String) void
  + checkBatterLineupSpace() int
   List~Pitcher~ pitcherLineup
   String teamName
   Set~Batter~ batterLoaderLineup
   Set~Pitcher~ pitcherLoaderLineup
   List~Batter~ batterLineup
}
class TeamInterface {
<<Interface>>
  + getPitcherFromLoader(String) Pitcher
  + getBatterFromLoader(String) Batter
  + clearBatterLineup() void
  + removeFromTeam(String, String) void
  + addBatterToTeam(String, Stream~Batter~) void
  + clearPitcherLineup() void
  + addPitcherToTeam(String, Stream~Pitcher~) void
   List~Pitcher~ pitcherLineup
   String teamName
   Set~Batter~ batterLoaderLineup
   Set~Pitcher~ pitcherLoaderLineup
   List~Batter~ batterLineup
}
class Teams {
<<enumeration>>
  - Teams(String) 
  - String cmdName
  + values() Teams[]
  + fromCmdName(String) Teams
  + fromString(String) Teams
  + valueOf(String) Teams
  + randomTeam() Teams
   String cmdName
}
class TextUI {
  + TextUI() 
  + displayBatters(List~Batter~) void
  + displayPitchers(List~Pitcher~) void
  + displayHelp() void
  + displayError(String) void
  + displayMessage(String) void
  + displayListOfStrings(List~String~) void
  + close() void
  + displayAllTeams(List~String~) void
  + displaySimulationResult(SimulationResult) void
  + displayPlayerInfo(Player) void
   String command
}
class TextUITest {
  + TextUITest() 
  ~ testDisplayPitchers() void
  ~ testDisplayAllTeams() void
  ~ testGetCommand() void
  ~ testDisplayBatters() void
  ~ testDisplayPitchersEmpty() void
  ~ testDisplayListOfStrings() void
  ~ testDisplaySimulationResult() void
  ~ testClose() void
  ~ testDisplayError() void
  ~ testDisplayBattersEmpty() void
  ~ testDisplayHelp() void
  ~ testDisplayMessage() void
  ~ testDisplayAllTeamsEmpty() void
  ~ setUp() void
  ~ testDisplayListOfStringsEmpty() void
  ~ tearDown() void
  ~ testDisplayPlayerInfo() void
}
class UIInterface {
<<Interface>>
  + displaySimulationResult(SimulationResult) void
  + displayBatters(List~Batter~) void
  + close() void
  + displayError(String) void
  + displayHelp() void
  + displayPlayerInfo(Player) void
  + displayMessage(String) void
  + displayPitchers(List~Pitcher~) void
  + displayListOfStrings(List~String~) void
  + displayAllTeams(List~String~) void
   String command
}

Batter  -->  Player 
ComTeam  -->  Team 
MLBSimulatorController  ..>  MLBSimulatorControllerInterface 
Model  ..>  ModelInterface 
Pitcher  -->  Player 
Player  ..>  PlayerInterface 
PlayerFilter  ..>  Filter 
PlayerLoader  ..>  Loader 
PlayerSorter  ..>  Sorter 
PlayerTeam  -->  Team 
RegularInning  ..>  Inning 
PlayerSorter  -->  SortByAVG 
PlayerSorter  -->  SortByAVGDesc 
PlayerSorter  -->  SortByBallsRate 
PlayerSorter  -->  SortByBallsRateDesc 
PlayerSorter  -->  SortByBatterName 
PlayerSorter  -->  SortByBatterNameDesc 
PlayerSorter  -->  SortByBreaking1B 
PlayerSorter  -->  SortByBreaking1BDesc 
PlayerSorter  -->  SortByBreaking2B 
PlayerSorter  -->  SortByBreaking2BDesc 
PlayerSorter  -->  SortByBreaking3B 
PlayerSorter  -->  SortByBreaking3BDesc 
PlayerSorter  -->  SortByBreakingH 
PlayerSorter  -->  SortByBreakingHDesc 
PlayerSorter  -->  SortByBreakingHR 
PlayerSorter  -->  SortByBreakingHRDesc 
PlayerSorter  -->  SortByBreakingPA 
PlayerSorter  -->  SortByBreakingPADesc 
PlayerSorter  -->  SortByChangeup 
PlayerSorter  -->  SortByChangeupDesc 
PlayerSorter  -->  SortByChaseContact 
PlayerSorter  -->  SortByChaseContactDesc 
PlayerSorter  -->  SortByChaseSwing 
PlayerSorter  -->  SortByChaseSwingDesc 
PlayerSorter  -->  SortByCurveball 
PlayerSorter  -->  SortByCurveballDesc 
PlayerSorter  -->  SortByCutter 
PlayerSorter  -->  SortByCutterDesc 
PlayerSorter  -->  SortByFastball1B 
PlayerSorter  -->  SortByFastball1BDesc 
PlayerSorter  -->  SortByFastball2B 
PlayerSorter  -->  SortByFastball2BDesc 
PlayerSorter  -->  SortByFastball3B 
PlayerSorter  -->  SortByFastball3BDesc 
PlayerSorter  -->  SortByFastballH 
PlayerSorter  -->  SortByFastballHDesc 
PlayerSorter  -->  SortByFastballHR 
PlayerSorter  -->  SortByFastballHRDesc 
PlayerSorter  -->  SortByFastballPA 
PlayerSorter  -->  SortByFastballPADesc 
PlayerSorter  -->  SortByFork 
PlayerSorter  -->  SortByForkDesc 
PlayerSorter  -->  SortByFourSeam 
PlayerSorter  -->  SortByFourSeamDesc 
PlayerSorter  -->  SortByKnuckle 
PlayerSorter  -->  SortByKnuckleDesc 
PlayerSorter  -->  SortByOBP 
PlayerSorter  -->  SortByOBPDesc 
PlayerSorter  -->  SortByOPS 
PlayerSorter  -->  SortByOPSDesc 
PlayerSorter  -->  SortByOffspeed1B 
PlayerSorter  -->  SortByOffspeed1BDesc 
PlayerSorter  -->  SortByOffspeed2B 
PlayerSorter  -->  SortByOffspeed2BDesc 
PlayerSorter  -->  SortByOffspeed3B 
PlayerSorter  -->  SortByOffspeed3BDesc 
PlayerSorter  -->  SortByOffspeedH 
PlayerSorter  -->  SortByOffspeedHDesc 
PlayerSorter  -->  SortByOffspeedHR 
PlayerSorter  -->  SortByOffspeedHRDesc 
PlayerSorter  -->  SortByOffspeedPA 
PlayerSorter  -->  SortByOffspeedPADesc 
PlayerSorter  -->  SortByPitcherName 
PlayerSorter  -->  SortByPitcherNameDesc 
PlayerSorter  -->  SortByPitches 
PlayerSorter  -->  SortByPitchesDesc 
PlayerSorter  -->  SortByRotation 
PlayerSorter  -->  SortByRotationDesc 
PlayerSorter  -->  SortBySFinger 
PlayerSorter  -->  SortBySFingerDesc 
PlayerSorter  -->  SortByScrew 
PlayerSorter  -->  SortByScrewDesc 
PlayerSorter  -->  SortBySinker 
PlayerSorter  -->  SortBySinkerDesc 
PlayerSorter  -->  SortBySlider 
PlayerSorter  -->  SortBySliderDesc 
PlayerSorter  -->  SortBySlurve 
PlayerSorter  -->  SortBySlurveDesc 
PlayerSorter  -->  SortByStrikes 
PlayerSorter  -->  SortByStrikesDesc 
PlayerSorter  -->  SortByStrikesRate 
PlayerSorter  -->  SortByStrikesRateDesc 
PlayerSorter  -->  SortBySweeper 
PlayerSorter  -->  SortBySweeperDesc 
PlayerSorter  -->  SortByTotal1B 
PlayerSorter  -->  SortByTotal1BDesc 
PlayerSorter  -->  SortByTotal2B 
PlayerSorter  -->  SortByTotal2BDesc 
PlayerSorter  -->  SortByTotal3B 
PlayerSorter  -->  SortByTotal3BDesc 
PlayerSorter  -->  SortByTotalH 
PlayerSorter  -->  SortByTotalHDesc 
PlayerSorter  -->  SortByTotalHR 
PlayerSorter  -->  SortByTotalHRDesc 
PlayerSorter  -->  SortByTotalPA 
PlayerSorter  -->  SortByTotalPADesc 
PlayerSorter  -->  SortByTwoSeam 
PlayerSorter  -->  SortByTwoSeamDesc 
PlayerSorter  -->  SortByZoneContact 
PlayerSorter  -->  SortByZoneContactDesc 
PlayerSorter  -->  SortByZoneSwing 
PlayerSorter  -->  SortByZoneSwingDesc 
Team  ..>  TeamInterface 
TextUI  ..>  UIInterface 
