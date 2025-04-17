package controller;

import gameEnum.PlayerData;

/**
 * Class to hold parsed filter string data
 */
public class FilterCriteria {
    String filterString; // The total filter string
    PlayerData sortAttribute; // The PlayerData attribute to sort on
    boolean hasSort; // Whether the filter string has a sort attribute
    boolean sortAsc; // Sort ascending or descending

    /**
     * Checks whether the filter string has a sort attribute
     * @return boolean of whether object has sort attribute
     */
    boolean hasSort() {
        return hasSort;
    }
}

