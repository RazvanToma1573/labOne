package Service;

import Domain.Problem;
import Domain.Student;
import Domain.Validators.Validator;
import Domain.Validators.ValidatorException;
import Repository.Repository;
import Repository.RepositoryException;
import javafx.util.Pair;

import java.util.List;
import java.util.stream.Collectors;

public class StudentsService {

    private Repository<Student> studentRepository;
    private Validator<Student> studentValidator;

    public StudentsService(Repository<Student> studentRepository, Validator<Student> studentValidator) {
        this.studentRepository = studentRepository;
        this.studentValidator = studentValidator;
    }

    public void add (Student newStudent) throws ValidatorException, RepositoryException {
        studentValidator.validate(newStudent);
        this.studentRepository.add(newStudent);
    }

    public void remove (int id) throws RepositoryException {
        this.studentRepository.remove(id);
    }

    public void assignProblem(int studentId, Problem problem) throws RepositoryException{
        this.studentRepository.assignProblem(studentId, problem);
    }

    public List<Student> get(){
        return this.studentRepository.getAll();
    }

    public Student getById(int id) throws RepositoryException {
        return this.studentRepository.getById(id);
    }

    public void assignGrade(int studentId, Problem problem, int grade) throws RepositoryException {
        this.studentRepository.assignGrade(studentId, problem, grade);
    }

    public List<Student> filterService(String argument, String type) throws ValidatorException {
        List<Student> students = this.studentRepository.getAll();
        if (type.equals("FIRSTNAME")) {
            return students.stream().filter(student -> argument.equals(student.getFirstName())).collect(Collectors.toList());
        } else if (type.equals("LASTNAME")) {
            return students.stream().filter(student -> argument.equals(student.getLastName())).collect(Collectors.toList());
        } else if (type.equals("PROBLEM")) {
            try {
                int problemID = Integer.parseInt(argument);
                return students.stream().filter(student ->  { List<Pair<Problem,Integer>> problemsAndGrades = student.getProblems();
                                                                return problemsAndGrades.stream().filter(problemIntegerPair -> problemIntegerPair.getKey().getId() == problemID).count() > 0;
                                                                }).collect(Collectors.toList());
            } catch (NumberFormatException exception) {
                throw new ValidatorException("ID not valid");
            }
        } else if (type.equals("GRADE")) {
            try{
                int grade = Integer.parseInt(argument);
                return students.stream().filter(student -> { List<Pair<Problem,Integer>> problemsAndGrades = student.getProblems();
                                                                return problemsAndGrades.stream().filter(problemIntegerPair -> problemIntegerPair.getValue() == grade).count() > 0;
                                                                }).collect(Collectors.toList());
            } catch (NumberFormatException exception) {
                throw new ValidatorException("Grade not valid");
            }
        }
        return null;
    }
}
