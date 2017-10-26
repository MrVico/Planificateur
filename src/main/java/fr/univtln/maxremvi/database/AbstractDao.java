package fr.univtln.maxremvi.database;

import java.util.List;

public abstract class AbstractDao<T> {
    protected AbstractDao instance;

    /***
     * @return The instance of the current implementation of this class
     */
    public abstract AbstractDao getInstance();

    /***
     * Query the database in order to retrieve the object with the given id
     *
     * @param id The object id
     * @return The recreated object
     */
    public abstract T get(int id);

    /***
     * Query the database in order to retrieve all the objects
     *
     * @return The List of objects
     */
    public abstract List<T> getAll();

    /***
     * Stores the given object into the database
     *
     * @param object The object to store
     * @return the stored object (with his id)
     */
    public abstract T add(T object);

    /***
     * Stores a List of objects into the database
     *
     * @param objects The List of objects to store
     * @return The List of inserted objects (with ids)
     */
    public abstract List<T> addAll(List<T> objects);

    /***
     * Updates the given object into the database
     *
     * @param object The object to update
     * @return true if the update happened successfully, false if not
     */
    public abstract boolean update(T object);

    /***
     * Removes the object with the given id
     *
     * @param id The object id
     * @return true if the delete happened successfully, false if not
     */
    public abstract boolean remove(int id);

    /***
     * Remove the given object from the database
     *
     * @param object The object to remove
     * @return true if the delete happened successfully, false if not
     */
    public abstract boolean remove(T object);
}
