package Service;

import Domain.Problem;
import Domain.Validators.ValidatorException;
import Repository.RepositoryException;

import java.util.List;

public interface Service<T> {
    public void add(T entity) throws ValidatorException, RepositoryException;
    public void remove(int id) throws RepositoryException;
    public List<T> get();
    public T getById(int id) throws RepositoryException;
    public void assignProblem(int studentId, Problem problem) throws RepositoryException;
    public void assignGrade(int studentId, Problem problem, int grade) throws RepositoryException;
}
