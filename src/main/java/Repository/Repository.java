package Repository;

import java.util.List;

public interface Repository<T> {
    void add(T entity) throws RepositoryException;
    void remove(T entity) throws RepositoryException;
    List<T> getAll();
    boolean find(T entity);
}
