package mpp.socket.common;

import com.sun.tools.javac.util.Pair;
import mpp.socket.common.Domain.Problem;
import mpp.socket.common.Domain.Validators.ValidatorException;

import java.util.List;

public interface IServiceProblems {
    void add(Problem problem) throws ValidatorException;
    void remove(int id);
    Iterable<Problem> get();
    Iterable<Problem> getSorted(List<Pair<Boolean, String>> criteria);
    Problem getById(int id) throws ValidatorException;
    void update(int id, String desc, String diff) throws ValidatorException, IllegalArgumentException;
}
