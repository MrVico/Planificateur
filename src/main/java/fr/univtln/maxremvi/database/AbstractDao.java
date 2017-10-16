package fr.univtln.maxremvi.database;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by remi on 15/10/2017.
 */
public abstract class AbstractDao<T> {
    protected AbstractDao instance;

    public abstract AbstractDao getInstance();
    public abstract T get(int id);
    public abstract List<T> getAll();
    public abstract T add(T object) throws SQLException;
    public abstract List<T> addAll(List<T> objects) throws SQLException;
    public abstract T update(T object) throws SQLException;
    public abstract boolean remove(int id) throws SQLException;
    public abstract boolean remove(T object) throws SQLException;
}
