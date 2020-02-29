package Service;

import Domain.Problem;
import Domain.Validators.Validator;
import Domain.Validators.ValidatorException;
import Repository.Repository;
import Repository.RepositoryException;

import java.util.List;

public class ProblemsService<Problem> implements Service<Problem> {
    private Repository<Problem> problemRepository;
    private Validator<Problem> problemValidator;

    public ProblemsService(Repository<Problem> problemRepository, Validator<Problem> problemValidator) {
        this.problemRepository = problemRepository;
        this.problemValidator = problemValidator;
    }

    @Override
    public void add(Problem newProblem) throws ValidatorException, RepositoryException {
        this.problemValidator.validate(newProblem);
        this.problemRepository.add(newProblem);
    }

    @Override
    public void remove(int idProblemToBeRemoved) throws RepositoryException {

        this.problemRepository.remove(idProblemToBeRemoved);
    }

    @Override
    public List<Problem> get() {
        return this.problemRepository.getAll();
    }

    @Override
    public Problem getById(int id) throws RepositoryException{
        return this.problemRepository.getById(id);
    }

    @Override
    public void assignProblem(int studentId, Domain.Problem problem) throws RepositoryException {
        //throw new RepositoryException("");
    }

    @Override
    public void assignGrade(int studentId, Domain.Problem problem, int grade) throws RepositoryException {

    }
}
