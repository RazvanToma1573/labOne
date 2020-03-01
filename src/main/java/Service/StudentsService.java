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

    /**
     * Creates a new Service for the students
     * @param studentRepository is the repository for the students
     * @param studentValidator is the validator for students
     */
    public StudentsService(Repository<Student> studentRepository, Validator<Student> studentValidator) {
        this.studentRepository = studentRepository;
        this.studentValidator = studentValidator;
    }

    /**
     * Adds a new student to the repository
     * @param newStudent is the new student to be added
     * @throws ValidatorException if the data of the new student is invalid
     * @throws RepositoryException if the student is already in the repository
     */
    public void add (Student newStudent) throws ValidatorException, RepositoryException {
        studentValidator.validate(newStudent);
        this.studentRepository.add(newStudent);
    }

    /**
     * Removes a student from the repository
     * @param id is the ID of the student to be removed
     * @throws RepositoryException if the student is not in the repository
     */
    public void remove (int id) throws RepositoryException {
        this.studentRepository.remove(id);
    }

    /**
     * Assigns a problem to a student from repository
     * @param studentId is the ID of the student
     * @param problem is the new problem to be assigned
     * @throws RepositoryException if the student is not in the repository
     */
    public void assignProblem(int studentId, Problem problem) throws RepositoryException{
        this.studentRepository.assignProblem(studentId, problem);
    }

    /**
     * Returns all the students from the repository
     * @return a list with all the students
     */
    public List<Student> get(){
        return this.studentRepository.getAll();
    }

    /**
     * Returns a student with the given ID from the repository
     * @param id is the ID of the student
     * @return student with the given ID
     * @throws RepositoryException if there is no student with such ID in the repository
     */
    public Student getById(int id) throws RepositoryException {
        return this.studentRepository.getById(id);
    }

    /**
     * Assigns a grade to a student from repository for a given problem
     * @param studentId is the ID of the stundet
     * @param problem is the problem to be graded
     * @param grade is the grade (0..10)
     * @throws RepositoryException if grade is invalid or if the student
     * is not in the repository
     */
    public void assignGrade(int studentId, Problem problem, int grade) throws RepositoryException {
        this.studentRepository.assignGrade(studentId, problem, grade);
    }

    /**
     * Filters all students from repository by a given type and parameter
     * Type is in {FIRSTNAME, LASTNAME, PROBLEM, GRADE}
     * @param argument is the filter argument for the students
     * @param type is the type of filtering
     * @return a list of filtere students
     * @throws ValidatorException if the argument is invalid
     */
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
