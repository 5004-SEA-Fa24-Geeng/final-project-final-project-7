[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/IE0ITl4j)

# Final Project for CS 5004 - (APPLICATION NAME/Update)

(remove this and add your sections/elements)
This readme should contain the following information:

## Group members

- Derek Marion | [GitHub](https://github.com/derekmarion)
- Huang Shou Liu (Vincent Liu) | [GitHub](https://github.com/VincentLiuGit)
- Shang Ru Yu (Max Yu) | [GitHub](https://github.com/ShangRuYu)

## MLB Simulator

- This application simulates baseball games by allowing the user to select a batting lineup for the Seattle Mariners to compete against other MLB teams.
- The simulation is based on the statistical profiles of the players involved, creating realistic game outcomes.


## Documentation

- [UML Diagram](./DesignDocuments/InitialDesign.md)

## Instructions

To run the application, simply execute the MLBSimulator file. You'll be presented with a command-line interface where you can enter various commands to build your team and run simulations. You need to pick 9 batters and 3 pitchers to play the simulation. 
To start building lineup for player team, you can explore the available batters using filtering options. Filter players can base on key statistics like AVG, OPS and other metrics. Next, add your preferred batters to create a lineup.

For the computer team, you can browse all the opponent teams, and select one team to see available pitchers. Now, you can choose three pitchers from the selected team to face your lineup!

### Example Workflow

1. Browse available batters with command line: `player show all`
2. Filter for high OBP hitters and sort with OPS with command line: `player filter OBP >= 0.380 sort OPS`
3. Add players to your lineup with command line: `player add Juan Soto`
4. View your current lineup with command line: `player show lineup`
5. Exploring all computer team with command line: `computer show teams`
6. Select a computer team with command line: `computer select dodgers`
7. View available pitchers with command line: `computer show dodgers`
8. Add specific pitchers with command line: `computer add Clayton Kershaw to 1`
9. Run a simulation with command line: `simulate`
10. Run multiple simulations to a file with command line: `simulate -n 5-o results.txt`


### Player Team Commands
The following commands allow you to manage your batter lineup:

`player show [name]` - Display detail information for a specific batter

`player show lineup` - View your current batting lineup

`player show all` - Browse all available batters in the player pool

`player add [name]` - Add a batter to your team by typing the batter name

`player remove [name]` - Remove a batter from your team

`player clear` - Reset your batter lineup

`player filter [filters] sort [attribute]` - Filter and sort the player roster based on specific criteria. Example: player filter TotalPA >= 100 sort AVG >= 0.25

### Computer Team Commands

`computer show [team]` - Display available pitchers for a specific MLB team

`computer show teams` - List all selectable MLB teams

`computer select [team]` - Choose a team to select the computer team

`computer add [name/number] to [pos]` - Add a pitcher to a specific position in the rotation. Example: computer add Carlos Rodon to 1 or computer add 12 to 1

`computer remove [name/number/range]` - Remove pitchers from the rotation

### Other Commands

`help` - Display the help message with all available commands

`simulate -n [number] -o [outfile]` - Run [number] game simulations and write to file [outfile]. If no options are provided, 1 simulation will be run and printed to console.

`-n [number]`: Specify the number of simulations to run (default: 1)

`-o [outfile]`: Write results to a txt file

`exit` - Exit the application

### Pitch Type Performance Metrics
Fastball Statistics

- fastballPA: Plate appearances against fastball pitches
- fastballH: Hits against fastball pitches
- fastball1B: Singles against fastball pitches
- fastball2B: Doubles against fastball pitches
- fastball3B: Triples against fastball pitches
- fastballHR: Home runs against fastball pitches

Breaking Statistics

- breakingPA: Plate appearances against breaking pitches (sliders, curveballs, etc.)
- breakingH: Hits against breaking pitches
- breaking1B: Singles against breaking pitches
- breaking2B: Doubles against breaking pitches
- breaking3B: Triples against breaking pitches
- breakingHR: Home runs against breaking pitches

Offspeed Statistics

- offspeedPA: Plate appearances against off-speed pitches (changeups, splitters, etc.)
- offspeedH: Hits against off-speed pitches
- offspeed1B: Singles against off-speed pitches
- offspeed2B: Doubles against off-speed pitches
- offspeed3B: Triples against off-speed pitches
- offspeedHR: Home runs against off-speed pitches

Total Performance Metrics

- totalPA: Total plate appearances across all pitch types
- totalH: Total hits across all pitch types
- total1B: Total singles across all pitch types
- total2B: Total doubles across all pitch types
- total3B: Total triples across all pitch types
- totalHR: Total home runs across all pitch types

Plate Discipline Metrics

- zoneSwing: Percentage of swings on pitches in the strike zone
- zoneContact: Percentage of contact made on swings at pitches in the strike zone
- chaseSwing: Percentage of swings on pitches outside the strike zone (chase rate)
- chaseContact: Percentage of contact made on swings at pitches outside the strike zone

Traditional Statistics

- AVG: Batting average (hits divided by at-bats)
- OBP: On-base percentage (times reached base divided by plate appearances)
- OPS: On-base plus slugging (combines OBP and slugging percentage)

These statistics allow for detailed analysis of a batter's performance against different pitch types and their overall hitting approach, which can be valuable for simulation models and player evaluation.RetryClaude can make mistakes. Please double-check responses.
