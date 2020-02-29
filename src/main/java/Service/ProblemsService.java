package Service;

import Domain.Problem;
import Domain.Validators.Validator;
import Domain.Validators.ValidatorException;
import Repository.Repository;
import Repository.RepositoryException;

import java.util.List;

public class ProblemsService{
    private Repository<Problem> problemRepository;
    private Validator<Problem> problemValidator;

    public ProblemsService(Repository<Problem> problemRepository, Validator<Problem> problemValidator) {
        this.problemRepository = problemRepository;
        this.problemValidator = problemValidator;
    }

    public void add(Problem newProblem) throws ValidatorException, RepositoryException {
        this.problemValidator.validate(newProblem);
        this.problemRepository.add(newProblem);
    }

    public void remove(int idProblemToBeRemoved) throws RepositoryException {

        this.problemRepository.remove(idProblemToBeRemoved);
    }

    public List<Problem> get() {
        return this.problemRepository.getAll();
    }

    public Problem getById(int id) throws RepositoryException{
        return this.problemRepository.getById(id);
    }

}
