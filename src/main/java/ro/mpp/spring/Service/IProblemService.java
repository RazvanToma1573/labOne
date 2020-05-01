package ro.mpp.spring.Service;

import ro.mpp.spring.Domain.Problem;
import ro.mpp.spring.Domain.Validators.ValidatorException;

import java.util.Map;

public interface IProblemService {
    void add(Problem problem) throws ValidatorException;
    void remove(int id);
    Iterable<Problem> get();
    Problem getById(int id) throws ValidatorException;
    void update (int idProblem, String desc, String diff) throws ValidatorException, IllegalArgumentException;
    Iterable<Problem> getSorted(Map<String, Boolean> criteria);
}
