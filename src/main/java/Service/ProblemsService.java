package Service;

import Domain.Problem;
import Domain.Student;
import Domain.Validators.Validator;
import Domain.Validators.ValidatorException;
import Repository.Repository;

import java.util.List;

public class ProblemsService{
    private Repository<Integer, Problem> problemRepository;
    private Validator<Problem> problemValidator;

    /**
     * Creates a new problem service
     * @param problemRepository problem repository
     * @param problemValidator problem validator
     */
    public ProblemsService(Repository<Integer, Problem> problemRepository, Validator<Problem> problemValidator) {
        this.problemRepository = problemRepository;
        this.problemValidator = problemValidator;
    }

    /**
     * Validate new problem.
     * Add a new problem to the problem repository if the validation was carried out with no problems.
     * @param newProblem new problem (Problem)
     * @throws ValidatorException custom exception (ValidatorException)
     */
    public void add(Problem newProblem) throws ValidatorException {
        this.problemValidator.validate(newProblem);
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

    /**
     * Updates a problem on the fields present in the type list of strings
     * - The list may contain (DESCRIPTION, DIFFICULTY)
     * @param updatedProblem student
     * @param type  types (Strings)
     * @throws ValidatorException validator exception
     * @throws IllegalArgumentException illegal argument exception
     */
    public void update (Problem updatedProblem, List<String> type) throws ValidatorException, IllegalArgumentException {
        this.problemRepository.update(updatedProblem);
    }
}
