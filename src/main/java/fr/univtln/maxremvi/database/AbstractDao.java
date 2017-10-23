package fr.univtln.maxremvi.database;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by remi on 15/10/2017.
 */
public abstract class AbstractDao<T> {
    protected AbstractDao instance;
    // TODO : Clean le code ! passer en boolean...
    public abstract AbstractDao getInstance();
    // TODO : Sert à rien dans le cas d'une clé primaire composée
    public abstract T get(int id);
    public abstract List<T> getAll()throws SQLException;
    public abstract T add(T object) throws SQLException;
    public abstract List<T> addAll(List<T> objects) throws SQLException;
    public abstract boolean update(T object);
    public abstract boolean remove(int id) throws SQLException;
    // TODO : Jamais utilisé...
    public abstract boolean remove(T object) throws SQLException;
}
