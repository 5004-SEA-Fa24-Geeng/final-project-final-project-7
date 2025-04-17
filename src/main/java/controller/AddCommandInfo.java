package controller;

/**
 * Class to hold parsed add command information
 */
public class AddCommandInfo {
    boolean isValid = false;    // Whether command is valid
    boolean isIndex = false;    // Whether player identifier is an index
    int playerIndex = -1;       // Player index if numeric
    String playerName = "";     // Player name if not numeric
    int position = -1;          // Position to add player to
}