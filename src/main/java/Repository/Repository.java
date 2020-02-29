package Repository;

import Domain.Problem;

import java.util.List;

public interface Repository<T> {
    void add(T entity) throws RepositoryException;
    void remove(int id) throws RepositoryException;
    List<T> getAll();
    boolean find(T entity);
    T getById(int id) throws RepositoryException;
    public void assignProblem(int studentId, Problem problem) throws RepositoryException;
    public void assignGrade(int studentId, Problem problem, int grade) throws RepositoryException;
}
