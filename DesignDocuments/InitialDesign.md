# Initial Design

````mermaid
---
Title Initial Design
---
classDiagram
direction TB

    class MLBSimulator {
        + static void main(String[] args)
    }

    %% Interfaces
    class PlayerInterface {
        + String getName()
        + String getPosition()
    }

    class TeamInterface {
        + String getTeamName()
        + List<PlayerInterface> getPlayers()
    }

    class Inning {
        + void playInning()
    }

    class Filter {
        + Stream<Player> filter(List<Player> players, String attribute, Object value)
    }

    class Sorter {
        + Stream<Player> sort(List<Player> players, String attribute)
    }

    class Loader {
        + List<Player> loadPlayers(String filePath)
    }

    %% Enums
    class Side {
        <<enumeration>>
        PLAYER
        COMPUTER
    }

    class Teams {
        <<enumeration>>
        ALL
        MARINERS
        /* Other MLB Teams */
    }

    class Outs {
        <<enumeration>>
        ONE
        TWO
        THREE
    }

    class Hits {
        <<enumeration>>
        SINGLE
        DOUBLE
        TRIPLE
        HR
    }

    class Strikes {
        <<enumeration>>
        ONE
        TWO
        THREE
    }

    class Balls {
        <<enumeration>>
        ONE
        TWO
        THREE
        FOUR
    }

    %% Model
    class Player {
        - String name
        - String pitchType
        - int plateAppearances
        - int hits
        - int singles
        - int doubles
        - int triples
        - int homeRuns
        - double zoneSwing
        - double zoneContact
        - double chase
        - double chaseContact
        - String rotation
        - String pitchName
        - double pitchUsage
        - int strikes
        - int pitches
        - double strikesRate
        - double ballsRate
    }

    class Batter {
        + void swing()
    }

    class Pitcher {
        + void pitch()
    }

    class Team {
        - String name
        - List<Player> players
        + String getTeamStats()
        + Player getPlayer(String name)
    }

    class PlayerTeam {
        + void draftPlayer(Player player)
    }

    class ComTeam {
        + void generatePitcherRoster()
    }

    class Simulation {
        - Team mariners
        - Team opponent
        + SimulationResult runSimulation()
    }

    class SimulationResult {
        - int marinersScore
        - int opponentScore
        - String details
        + void exportResults()
    }

    class RegularInning {
        + void playInning()
    }

    %% Model: Data Loading
    class PlayerLoader {
        + List<Player> loadPlayers(String filePath)
    }

    class TeamLoader {
        + List<Team> loadTeams(String filePath)
    }

    %% Model: Sorting and Filtering
    class PlayerSorter {
        + Stream<Player> sort(List<Player> players, String attribute)
    }

    class PlayerFilter {
        + Stream<Player> filter(List<Player> players, String attribute, Object value)
    }

    %% View
    class TextUI {
        + void displayMenu()
        + void displayTeams(List<Team> teams)
        + void displaySimulationResult(SimulationResult result)
        + void displaySortedPlayers(List<Player> players)
        + void displayFilteredPlayers(List<Player> players)
    }

    %% Controller
    class GameController {
        - TextUI view
        - Team mariners
        - List<Team> mlbTeams
        - PlayerSorter sorter
        - PlayerFilter filter
        - PlayerLoader playerLoader
        - TeamLoader teamLoader
        + void start()
        + void selectOpponent(String teamName)
        + void buildLineup(List<Player> players)
        + void runGame()
        + Stream<Player> sortPlayers(List<Player> players, String attribute)
        + Stream<Player> filterPlayers(List<Player> players, String attribute, Object threshold)
    }

    %% Relationships
    MLBSimulator --> GameController : Initializes
    PlayerInterface <|.. Player
    Player <|-- Batter
    Player <|-- Pitcher
    TeamInterface <|.. Team
    Team <|-- PlayerTeam
    Team <|-- ComTeam
    Inning <|.. RegularInning
    Filter <|.. PlayerFilter
    Sorter <|.. PlayerSorter
    Loader <|.. PlayerLoader
    Player --> Team : belongs to
    Team --> Simulation : used by
    Simulation --> SimulationResult : produces
    Simulation --> RegularInning : uses
    Simulation --> Side : uses
    Simulation --> Hits : uses
    Simulation --> Strikes : uses
    Simulation --> Outs : uses
    Simulation --> Balls : uses
    Simulation --> Teams : uses
    Simulation --> PlayerTeam : has
    Simulation --> ComTeam : has
    SimulationResult --> TextUI : displayed in
    GameController --> TextUI : controls
    GameController --> Team : manages
    GameController --> Simulation : runs
    GameController --> PlayerSorter : uses
    GameController --> PlayerFilter : uses
    PlayerSorter --> Player : sorts
    PlayerFilter --> Player : filters
    PlayerLoader --> Player : loads
    TeamLoader --> Team : loads
    TeamLoader --> PlayerLoader : uses
    GameController --> PlayerLoader : uses
    GameController --> TeamLoader : uses
```
````
