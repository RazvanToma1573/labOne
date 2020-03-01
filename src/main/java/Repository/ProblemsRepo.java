package Repository;

import Domain.Problem;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProblemsRepo implements Repository<Problem> {

    private List<Problem> problems;

    /**
     * Creates a new in memory repository with an empty list of problems.
     */
    public ProblemsRepo() {
        problems = new ArrayList<>();
    }

    /**
     * Check if the problem is already in our repository.
     * If it is not, add it.
     * If it is, throw a custom exception.
     * @param entity Problem to be added.
     * @throws RepositoryException Custom exception.
     */
    @Override
    public void add(Problem entity) throws RepositoryException {
        if (this.find(entity)) throw new RepositoryException("Problem already contained!");
        this.problems.add(entity);
    }

    /**
     * Check if the problem is in our repository.
     * If it is, remove it.
     * If it is not, throw a custom exception.
     * @param problemId id of the problem we want to remove (int)
     * @throws RepositoryException custom exception (RepositoryException)
     */
    @Override
    public void remove(int problemId) throws RepositoryException {
        if(!this.find(new Problem(problemId,"",""))) throw new RepositoryException("Problem not contained!");
        this.problems.remove(new Problem(problemId, "", ""));
    }

    /**
     * Returns all the stored problems.
     * @return all the stored problems (List)
     */
    @Override
    public List<Problem> getAll() {
        return this.problems;
    }

    /**
     * Check if a problem is stored in the repository.
     * @param entity problem to be checked
     * @return true if the problem is stored in the repository and false otherwise
     */
    @Override
    public boolean find(Problem entity) {
        return this.problems.contains(entity);
    }

    /**
     * Returns the problem with the given id.
     * @param id id of the problem we want to return
     * @return the problem with the given id if it exists(Problem)
     * @throws RepositoryException Custom exception
     */
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
