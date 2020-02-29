package Service;

import Domain.Problem;
import Domain.Validators.Validator;
import Domain.Validators.ValidatorException;
import Repository.Repository;
import Repository.RepositoryException;

import java.util.List;

public class ProblemsService<Problem> implements Service<Problem> {
    private Repository<Problem> problemRepository;
    private Validator<Problem> problemValidator;

    public ProblemsService(Repository<Problem> problemRepository, Validator<Problem> problemValidator) {
        this.problemRepository = problemRepository;
        this.problemValidator = problemValidator;
    }

    @Override
    public void add(Problem newProblem) throws ValidatorException, RepositoryException {
        this.problemValidator.validate(newProblem);
        this.problemRepository.add(newProblem);
    }

    @Override
    public void remove(Problem problemToBeRemoved) throws ValidatorException, RepositoryException {
        this.problemValidator.validate(problemToBeRemoved);
        this.problemRepository.remove(problemToBeRemoved);
    }

    @Override
    public List<Problem> get() {
        return this.problemRepository.getAll();
    }
}
