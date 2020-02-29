package Service;

import Domain.Problem;
import Domain.Student;
import Domain.Validators.Validator;
import Domain.Validators.ValidatorException;
import Repository.Repository;
import Repository.RepositoryException;

import java.util.List;

public class StudentsService<Student> implements Service<Student> {

    private Repository<Student> studentRepository;
    private Validator<Student> studentValidator;

    public StudentsService(Repository<Student> studentRepository, Validator<Student> studentValidator) {
        this.studentRepository = studentRepository;
        this.studentValidator = studentValidator;
    }

    @Override
    public void add (Student newStudent) throws ValidatorException, RepositoryException {
        studentValidator.validate(newStudent);
        this.studentRepository.add(newStudent);
    }

    @Override
    public void remove (int id) throws RepositoryException {
        this.studentRepository.remove(id);
    }

    public void assignProblem(int studentId, Problem problem) throws RepositoryException{
        this.studentRepository.assignProblem(studentId, problem);
    }

    @Override
    public List<Student> get(){
        return this.studentRepository.getAll();
    }

    @Override
    public Student getById(int id) throws RepositoryException {
        return this.studentRepository.getById(id);
    }

    @Override
    public void assignGrade(int studentId, Problem problem, int grade) throws RepositoryException {
        this.studentRepository.assignGrade(studentId, problem, grade);
    }
}
