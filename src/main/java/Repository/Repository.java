package Repository;

public interface Repository<T> {
    void add(T entity);
    void remove(T entity);
}
