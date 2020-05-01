package ro.mpp.spring.Service;

import ro.mpp.spring.Domain.Problem;
import ro.mpp.spring.Domain.Validators.Validator;
import ro.mpp.spring.Domain.Validators.ValidatorException;
import ro.mpp.spring.Repository.Sort;
import ro.mpp.spring.Repository.ProblemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Field;
import java.util.*;

@Service
public class ProblemsService implements IProblemService{
    public static final Logger log = LoggerFactory.getLogger(ProblemsService.class);
    @Autowired
    private ProblemRepository problemRepository;
    @Autowired
    private Validator<Problem> problemValidator;


    /**
     * Validate new problem.
     * Add a new problem to the problem repository if the validation was carried out with no problems.
     * @param newProblem new problem (Problem)
     * @throws ValidatorException custom exception (ValidatorException)
     */
    @Override
    public void add(Problem newProblem) throws ValidatorException {
        log.trace("add Problem - method entered: problem = {}", newProblem);
        this.problemValidator.validate(newProblem);
        this.problemRepository.save(newProblem);
        log.trace("add Problem - method finished");
    }

    /**
     * Remove problem with the given id and remove all the grades for this problem
     * @param idProblemToBeRemoved id of the problem to be removed (int)
     */
    @Override
    public void remove(int idProblemToBeRemoved) {
        log.trace("remove Problem - method entered: problemID = {}", idProblemToBeRemoved);
        this.problemRepository.deleteById(idProblemToBeRemoved);
        log.trace("remove Problem - method finished");
    }

    /**
     * Returns all the problems from the problem repository.
     * @return list of all problems (List)
     */
    @Override
    public Iterable<Problem> get() {
        return this.problemRepository.findAll();
    }

    /**
     * Returns the problem that has the given id.
     * @param id int
     * @return problem if found
     * @throws ValidatorException custom exception
     */
    @Override
    public Problem getById(int id) throws ValidatorException {
        Optional<Problem> checkProblem = this.problemRepository.findById(id);
        if (checkProblem.isPresent()) {
            return checkProblem.get();
        } else {
            throw new ValidatorException("No problem found with the given id!");
        }
    }

    /**
     * Updates a problem on the field present in the type string
     * @param idProblem int
     * @param desc  String
     * @param diff String
     * @throws ValidatorException validator exception
     * @throws IllegalArgumentException illegal argument exception
     */
    @Override
    @Transactional
    public void update (int idProblem, String desc, String diff) throws ValidatorException, IllegalArgumentException {
        log.trace("Update problem - method entered: problemID = {}", idProblem);
        Optional<Problem> checkProblem = this.problemRepository.findById(idProblem);
        if(checkProblem.isPresent()) {
            Problem problem = checkProblem.get();
            problem.setDescription(desc);
            problem.setDifficulty(diff);
        } else {
            throw new ValidatorException("No problem found with the given id!");
        }
        log.trace("Update problem - method finished");
    }

    @Override
    public Iterable<Problem> getSorted(Map<String, Boolean> criteria) {
        Sort sort = new Sort();
        criteria.entrySet().stream().forEach(cr -> sort.and(new Sort(cr.getValue(), cr.getKey())));
        List<Problem> result = new ArrayList<>();
        problemRepository.findAll().forEach(result::add);
        try{
            final Class problemClass;
            final Class baseClass;

            problemClass = Class.forName("ro.mpp.spring.Domain.Problem");
            baseClass = Class.forName("ro.mpp.spring.Domain.BaseEntity");

            Optional<Comparator<Problem>> comparator = sort.getCriteria().entrySet().stream()
                    .map(cr ->{
                        try{
                            final Field field = cr.getKey().equals("id") ?
                                    baseClass.getDeclaredField(cr.getKey()) :
                                    problemClass.getDeclaredField(cr.getKey());
                            field.setAccessible(true);
                            return (Comparator<Problem>) (problem, t1) -> {
                                try{
                                    Comparable c1 = (Comparable)field.get(problem);
                                    Comparable c2 = (Comparable)field.get(t1);
                                    return cr.getValue() ? c2.compareTo(c1) : c1.compareTo(c2);
                                } catch (IllegalAccessException e){
                                    System.out.println(e.getMessage());
                                }
                                return 0;
                            };
                        } catch (NoSuchFieldException e){
                            System.out.println(e.getMessage());
                        }
                        return null;
                    })
                    .reduce((c1, c2) -> c1.thenComparing(c2));
            Collections.sort(result, comparator.get());

        } catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }

        return result;
    }
}
