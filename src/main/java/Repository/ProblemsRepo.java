package Repository;

import Domain.Problem;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public void remove(int problemId) throws RepositoryException {
        if(!this.find(new Problem(problemId,"",""))) throw new RepositoryException("Problem not contained!");
        this.problems.remove(new Problem(problemId, "", ""));
    }

    @Override
    public List<Problem> getAll() {
        return this.problems;
    }

    @Override
    public boolean find(Problem entity) {
        return this.problems.contains(entity);
    }

    public Problem getById(int id) throws RepositoryException{
        List<Problem> problem = this.problems.stream().filter(prob -> prob.getId() == id)
                .collect(Collectors.toList());
        if(problem.isEmpty()) throw new RepositoryException("There is no problem with id "+id);
        return problem.get(0);
    }

    @Override
    public void assignProblem(int studentId, Problem problem) throws RepositoryException {

    }

    @Override
    public void assignGrade(int studentId, Problem problem, int grade) throws RepositoryException {

    }
}
