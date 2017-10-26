package fr.univtln.maxremvi.database;

import java.sql.SQLException;
import java.util.List;

public abstract class AbstractDao<T> {
    protected AbstractDao instance;
    public abstract AbstractDao getInstance();
    public abstract T get(int id);
    public abstract List<T> getAll();
    public abstract T add(T object) throws SQLException;
    public abstract List<T> addAll(List<T> objects);
    public abstract boolean update(T object);
    public abstract boolean remove(int id);
    public abstract boolean remove(T object);
}
