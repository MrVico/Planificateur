package fr.univtln.maxremvi.utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class ListManager {
    /***
     * Transforms a List into an ObservableList
     *
     * @param list The List to transform
     * @return the ObservableList
     */
    public static ObservableList observableListFromList(List list) {
        return FXCollections.observableList(list);
    }
}
