package fr.univtln.maxremvi.utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

/**
 * Created by remi on 16/10/2017.
 */
public class ListManager {
    public static ObservableList observableListFromList(List list) {
        return FXCollections.observableList(list);
    }
}