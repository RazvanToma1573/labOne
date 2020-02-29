package Service;

import Domain.Validators.ValidatorException;
import Repository.RepositoryException;

import java.util.List;

public interface Service<T> {
    public void add(T entity) throws ValidatorException, RepositoryException;
    public void remove(int id) throws RepositoryException;
    public List<T> get();
}
