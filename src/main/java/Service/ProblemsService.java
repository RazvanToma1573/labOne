package Service;

import Domain.Problem;
import Domain.Validators.Validator;
import Domain.Validators.ValidatorException;
import Repository.Repository;
import Domain.Validators.RepositoryException;

import java.util.List;

public class ProblemsService{
    private Repository<Integer, Problem> problemRepository;


    /**
     * Creates a new problmes service.
     * @param problemRepository problem repository (Repository)
     */
    public ProblemsService(Repository<Integer, Problem> problemRepository) {
        this.problemRepository = problemRepository;
    }

    /**
     * Validate new problem.
     * Add a new problem to the problem repository if the validation was carried out with no problems.
     * @param newProblem new problem (Problem)
     * @throws ValidatorException custom exception (ValidatorException)
     */
    public void add(Problem newProblem) throws ValidatorException {
        this.problemRepository.save(newProblem);
    }

    /**
     * Remove problem with the given id.
     * @param idProblemToBeRemoved id of the problem to be removed (int)
     */
    public void remove(int idProblemToBeRemoved) {
        this.problemRepository.delete(idProblemToBeRemoved);
    }

    /**
     * Returns all the problems from the problem repository.
     * @return list of all problems (List)
     */
    public Iterable<Problem> get() {
        return this.problemRepository.findAll();
    }

    /**
     * Returns the problem that has the given id.
     * @param id id of the problem we want to return (int)
     * @return problem with the given id if it was found (Problem)
     */
    public Problem getById(int id) {
        return this.problemRepository.findOne(id).get();
    }

}
