# Initial Design

```mermaid
---
Title Initial Design
---
classDiagram
direction TB

    class MLBSimulator {
        + static void main(String[] args)
    }

    %% Model
    class Player {
        -String NAME
        -String PITCH_TYPE
        -int PA
        -int H
        -int SINGLES
        -int DOUBLES
        -int TRIPLES
        -int HOME_RUNS
        -double ZONE_SWING
        -double ZONE_CONTACT
        -double CHASE
        -double CHASE_CONTACT
        -String ROTATION
        -String PITCH_NAME
        -double PITCH_USAGE
        -int STRIKES
        -int PITCHES
        -double STRIKES_RATE
        -double BALLS_RATE

    class Team {
        - String name
        - players
        + getTeamStats(): String
        + getPlayer(name): Player
    }

    class Simulation {
        - Team mariners
        - Team opponent
        + runSimulation(): SimulationResult
    }

    class SimulationResult {
        - int marinersScore
        - int opponentScore
        - String details
        + exportResults(): void
    }

    %% Model: Data Loading
    class PlayerLoader {
        + loadPlayers(filePath): players
    }

    class TeamLoader {
        + loadTeams(filePath): teams
    }

    %% Model: Sorting and Filtering
    class PlayerSorter {
        + sort(players, attribute): Stream
    }

    class PlayerFilter {
        + filter(players, attribute, value): Stream
    }

    %% View
    class TextUI {
        + displayMenu(): void
        + displayTeams(teams): void
        + displaySimulationResult(result): void
        + displaySortedPlayers(players): void
        + displayFilteredPlayers(players): void
    }

    %% Controller
    class GameController {
        - TextUI view
        - Team mariners
        - mlbTeams
        - PlayerSorter sorter
        - PlayerFilter filter
        - PlayerLoader playerLoader
        - TeamLoader teamLoader
        + start(): void
        + selectOpponent(teamName): void
        + buildLineup(players): void
        + runGame(): void
        + sortPlayers(players, attribute): Stream
        + filterPlayers(players, attribute, threshold): Stream
    }

    %% Relationships
    MLBSimulator --> GameController : Initializes
    Player --> Team : belongs to
    Player --> PlayerAttribute : has
    Team --> Simulation : used by
    Simulation --> SimulationResult : produces
    SimulationResult --> TextUI : displayed by
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
