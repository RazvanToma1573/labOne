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

    /**
     * Creates a new problmes service.
     * @param problemRepository problem repository (Repository)
     * @param problemValidator problem validator (Validator)
     */
    public ProblemsService(Repository<Problem> problemRepository, Validator<Problem> problemValidator) {
        this.problemRepository = problemRepository;
        this.problemValidator = problemValidator;
    }

    /**
     * Validate new problem.
     * Add a new problem to the problem repository if the validation was carried out with no problems.
     * @param newProblem new problem (Problem)
     * @throws ValidatorException custom exception (ValidatorException)
     * @throws RepositoryException custom exception (RepositoryException)
     */
    public void add(Problem newProblem) throws ValidatorException, RepositoryException {
        this.problemValidator.validate(newProblem);
        this.problemRepository.add(newProblem);
    }

    /**
     * Remove problem with the given id.
     * @param idProblemToBeRemoved id of the problem to be removed (int)
     * @throws RepositoryException custom exception (RepositoryException)
     */
    public void remove(int idProblemToBeRemoved) throws RepositoryException {
        this.problemRepository.remove(idProblemToBeRemoved);
    }

    /**
     * Returns all the problems from the problem repository.
     * @return list of all problems (List)
     */
    public List<Problem> get() {
        return this.problemRepository.getAll();
    }

    /**
     * Returns the problem that has the given id.
     * @param id id of the problem we want to return (int)
     * @return problem with the given id if it was found (Problem)
     * @throws RepositoryException custom exception (RepositoryException)
     */
    public Problem getById(int id) throws RepositoryException{
        return this.problemRepository.getById(id);
    }

}
