package Service;

import Domain.Validators.ValidatorException;
import Repository.RepositoryException;

import java.util.List;

public interface Service<T> {
    public void add(T newStudent) throws ValidatorException, RepositoryException;
    public void remove(T studentToBeRemoved) throws ValidatorException, RepositoryException;
    public List<T> get();
}
