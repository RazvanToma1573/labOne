package ro.mpp.core.Service;


import org.springframework.data.domain.Page;
import ro.mpp.core.Domain.Problem;
import ro.mpp.core.Domain.Validators.ValidatorException;

import java.util.List;
import java.util.Map;

public interface IProblemService {
    /*
    Problem add(Problem problem) throws ValidatorException;
    void remove(int id);
    List<Problem> get();
    Page<Problem> get(int page);
    Problem getById(int id) throws ValidatorException;
    Problem update (int idProblem, String desc, String diff) throws ValidatorException, IllegalArgumentException;
    Page<Problem> getSorted(int page, Map<String, Boolean> criteria);

     */
    Problem add(int id, String description, String difficulty);
    Problem remove(int id);
    List<Problem> findAll();
    Problem getById(int id);
    Problem update(int id, String description, String difficulty);
    List<Problem> findAllByDescription(String description);
}
