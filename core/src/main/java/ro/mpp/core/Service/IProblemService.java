package ro.mpp.core.Service;


import ro.mpp.core.Domain.Problem;
import ro.mpp.core.Domain.Validators.ValidatorException;

import java.util.List;
import java.util.Map;

public interface IProblemService {
    Problem add(Problem problem) throws ValidatorException;
    void remove(int id);
    List<Problem> get();
    Problem getById(int id) throws ValidatorException;
    Problem update (int idProblem, String desc, String diff) throws ValidatorException, IllegalArgumentException;
    List<Problem> getSorted(Map<String, Boolean> criteria);
}
