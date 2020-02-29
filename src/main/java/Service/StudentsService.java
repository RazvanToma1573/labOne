package Service;

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

    @Override
    public List<Student> get(){
        return this.studentRepository.getAll();
    }
}
