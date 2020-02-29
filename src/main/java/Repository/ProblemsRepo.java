package Repository;

import Domain.Problem;

import java.util.ArrayList;
import java.util.List;

public class ProblemsRepo implements Repository<Problem> {

    private List<Problem> problems;

    public ProblemsRepo() {
        problems = new ArrayList<>();
    }

    @Override
    public void add(Problem entity) throws RepositoryException {
        if (this.find(entity)) throw new RepositoryException("Problem already contained!");
        this.problems.add(entity);
    }

    @Override
    public void remove(Problem entity) throws RepositoryException {
        if(!this.find(entity)) throw new RepositoryException("Problem not contained!");
        this.problems.remove(entity);
    }

    @Override
    public List<Problem> getAll() {
        return this.problems;
    }

    @Override
    public boolean find(Problem entity) {
        return this.problems.contains(entity);
    }
}
