package fr.univtln.maxremvi.utils;

import fr.univtln.maxremvi.model.PollDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

/**
 * Created by remi on 16/10/2017.
 */
public class ListUtil {
    public static ObservableList observableListFromList(List list) {
        return FXCollections.observableList(list);
    }
}
