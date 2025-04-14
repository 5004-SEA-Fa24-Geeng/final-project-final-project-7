package controller;

import gameEnum.PlayerData;

public class FilterCriteria {
    String filterString;
    PlayerData sortAttribute;
    boolean hasSort;
    boolean sortAsc;

    boolean hasSort() {
        return hasSort;
    }
}

