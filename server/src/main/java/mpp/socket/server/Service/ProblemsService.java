package mpp.socket.server.Service;


import com.sun.tools.javac.util.Pair;
import mpp.socket.common.Domain.Problem;
import mpp.socket.common.Domain.Validators.Validator;
import mpp.socket.common.Domain.Validators.ValidatorException;
import mpp.socket.common.Repository.Sort;
import mpp.socket.common.Repository.SortedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ProblemsService{
    private SortedRepository<Integer, Problem> problemRepository;
    private Validator<Problem> problemValidator;

    /**
     * Creates a new problem service
     * @param problemRepository problem repository
     * @param problemValidator problem validator
     */
    @Autowired
    public ProblemsService(SortedRepository<Integer, Problem> problemRepository, Validator<Problem> problemValidator) {
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
     * Remove problem with the given id and remove all the grades for this problem
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
     * @param id int
     * @return problem if found
     * @throws ValidatorException custom exception
     */
    public Problem getById(int id) throws ValidatorException {
        Optional<Problem> checkProblem = this.problemRepository.findOne(id);
        if (checkProblem.isPresent()) {
            return checkProblem.get();
        } else {
            throw new ValidatorException("No problem found with the given id!");
        }
    }

    /**
     * Updates a problem on the field present in the type string
     * @param idProblem int
     * @param type  String
     * @param update String
     * @throws ValidatorException validator exception
     * @throws IllegalArgumentException illegal argument exception
     */
    public void update (int idProblem, String type, String update) throws ValidatorException, IllegalArgumentException {
        Optional<Problem> checkProblem = this.problemRepository.findOne(idProblem);
        if(checkProblem.isPresent()) {
            Problem problem = checkProblem.get();
            if (type.equals("DESCRIPTION")) {
                problem.setDescription(update);
                this.problemRepository.update(problem);
            } else if (type.equals("DIFFICULTY")) {
                problem.setDifficulty(update);
                this.problemRepository.update(problem);
            }
        } else {
            throw new ValidatorException("No problem found with the given id!");
        }
    }

    public Iterable<Problem> getSorted(List<Pair<Boolean, String>> criteria) {
        Sort sort = new Sort(criteria.get(0).fst, criteria.get(0).snd);
        criteria.remove(0);
        criteria.stream().forEach(cr -> sort.and(new Sort(cr.fst, cr.snd)));
        return this.problemRepository.findAll(sort);
    }
}
