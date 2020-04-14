package mpp.socket.client.Service;

import com.sun.tools.javac.util.Pair;
import mpp.socket.common.Domain.Problem;
import mpp.socket.common.Domain.Validators.ValidatorException;
import mpp.socket.common.IServiceProblems;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProblemsServiceClient implements IServiceProblems {

    private IServiceProblems service;

    public ProblemsServiceClient(IServiceProblems service) {
        this.service = service;
    }

    @Override
    public void add(Problem problem) throws ValidatorException {
        service.add(problem);
    }

    @Override
    public void remove(int id) {
        service.remove(id);
    }

    @Override
    public Iterable<Problem> get() {
        return service.get();
    }

    @Override
    public Iterable<Problem> getSorted(List<Pair<Boolean, String>> criteria) {
        return service.getSorted(criteria);
    }

    @Override
    public Problem getById(int id) throws ValidatorException {
        return service.getById(id);
    }

    @Override
    public void update(int id, String desc, String diff) throws ValidatorException, IllegalArgumentException {
        service.update(id, desc, diff);
    }
}
