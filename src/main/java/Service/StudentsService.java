package Service;

import Domain.Grade;
import Domain.Problem;
import Domain.Student;
import Domain.Validators.ValidatorException;
import Repository.Repository;
import Domain.Validators.RepositoryException;
import javafx.util.Pair;


import java.util.*;

import java.util.stream.Collectors;

public class StudentsService {

    private Repository<Integer, Student> studentRepository;
    private Repository<Integer, Grade> gradeRepository;

    /**
     * Creates a new Service for the students
     *
     * @param studentRepository is the repository for the students
     * @param gradeRepository   is the repository for the grades
     */
    public StudentsService(Repository<Integer, Student> studentRepository, Repository<Integer, Grade> gradeRepository) {
        this.studentRepository = studentRepository;
        this.gradeRepository = gradeRepository;
    }

    /**
     * Adds a new student to the repository
     *
     * @param newStudent is the new student to be added
     * @throws ValidatorException       if the data of the new student is invalid
     * @throws IllegalArgumentException if newStudent is null
     */
    public void add(Student newStudent) throws ValidatorException, IllegalArgumentException {
        studentRepository.save(newStudent);
    }

    /**
     * Removes a student from the repository
     *
     * @param id is the ID of the student to be removed
     * @throws IllegalArgumentException if id is null
     */
    public void remove(int id) throws IllegalArgumentException {
        studentRepository.delete(id);
    }

    /**
     * Assigns a problem to a student from repository
     *
     * @param studentId is the ID of the student
     * @param problem   is the new problem to be assigned
     * @throws ValidatorException       Custom exception
     * @throws IllegalArgumentException if the new grade is null
     */
    public void assignProblem(int studentId, Problem problem) throws ValidatorException, IllegalArgumentException {
        Student student = this.studentRepository.findOne(studentId).get();
        this.gradeRepository.save(new Grade(student, problem, 0));
    }

    /**
     * Returns all the students from the repository
     *
     * @return an iterable with all the students
     */
    public Iterable<Student> get() {
        return this.studentRepository.findAll();
    }

    /**
     * Returns all the grades from the repository
     *
     * @return an iterable with all the grades
     */
    public Iterable<Grade> getGrades() {
        return this.gradeRepository.findAll();
    }

    /**
     * Returns a student with the given ID from the repository
     *
     * @param id is the ID of the student
     * @return student with the given ID
     * @throws IllegalArgumentException if id is null
     */
    public Student getById(int id) throws IllegalArgumentException {
        return this.studentRepository.findOne(id).get();
    }

    /**
     * Assigns a grade to a student from repository for a given problem
     *
     * @param studentId is the ID of the stundet
     * @param problem   is the problem to be graded
     * @param grade     is the grade (0..10)
     * @throws ValidatorException       custom exception
     * @throws IllegalArgumentException if new grade is null
     */
    public void assignGrade(int studentId, Problem problem, int grade) throws ValidatorException, IllegalArgumentException {
        Student student = this.studentRepository.findOne(studentId).get();
        this.gradeRepository.update(new Grade(student, problem, grade));
    }

    /**
     * Filters all students from repository by a given type and parameter
     * Type is in {FIRSTNAME, LASTNAME, PROBLEM, GRADE}
     *
     * @param argument is the filter argument for the students
     * @param type     is the type of filtering
     * @return a list of filtere students
     * @throws ValidatorException if the argument is invalid
     */
    public Set<Student> filterService(String argument, String type) throws ValidatorException {
        Iterable<Student> students = studentRepository.findAll();
        Iterable<Grade> grades = gradeRepository.findAll();
        if (type.equals("FIRSTNAME")) {

            Set<Student> filteredStudents = new HashSet<>();
            students.forEach(filteredStudents::add);
            filteredStudents.removeIf(student -> !student.getFirstName().contains(argument));
            return filteredStudents;

        } else if (type.equals("LASTNAME")) {

            Set<Student> filteredStudents = new HashSet<>();
            students.forEach(filteredStudents::add);
            filteredStudents.removeIf(student -> !student.getLastName().contains(argument));
            return filteredStudents;

        } else if (type.equals("PROBLEM")) {
            try {
                int problemID = Integer.parseInt(argument);

                Set<Grade> filteredGrades = new HashSet<>();
                grades.forEach(filteredGrades::add);
                filteredGrades.removeIf(grade -> !grade.getProblem().getId().equals(problemID));
                Set<Student> filteredStudents = filteredGrades.stream().map(grade -> grade.getStudent()).collect(Collectors.toSet());

                return filteredStudents;

            } catch (NumberFormatException exception) {
                throw new ValidatorException("ID not valid");
            }
        } else if (type.equals("GRADE")) {
            try {
                int wantedGrade = Integer.parseInt(argument);

                Set<Grade> filteredGrades = new HashSet<>();
                grades.forEach(filteredGrades::add);
                filteredGrades.removeIf(grade -> grade.getActualGrade() != (wantedGrade));
                Set<Student> filteredStudents = filteredGrades.stream().map(grade -> grade.getStudent()).collect(Collectors.toSet());


                return filteredStudents;

            } catch (NumberFormatException exception) {
                throw new ValidatorException("Grade not valid");
            }
        }
        return null;
    }

    public Student getStudentWithMaxGrade() {
        Iterable<Grade> grades = this.gradeRepository.findAll();
        Set<Grade> gradesSet = new HashSet<>();
        grades.forEach(gradesSet::add);
        Map<Student, Pair<Float, Integer>> averages = new HashMap<>();
        gradesSet.stream().forEach(grade -> {
            if (averages.containsKey(grade.getStudent()))
                averages.put(grade.getStudent(), new Pair<>(averages.get(grade.getStudent()).getKey() + grade.getActualGrade(), averages.get(grade.getStudent()).getValue() + 1));
            else
                averages.put(grade.getStudent(), new Pair<>((float) grade.getActualGrade(), 1));
        });
        return averages.entrySet().stream().map(entry -> new HashMap.SimpleEntry<Student, Float>(entry.getKey(), entry.getValue().getKey() / entry.getValue().getValue()))
                .max(Comparator.comparing(entry -> entry.getValue())).get().getKey();

    }

    public Problem getMaxAssignedProblem(){
        Iterable<Grade> allGrades = this.gradeRepository.findAll();
        Set<Grade> grades = new HashSet<>();
        allGrades.forEach(grades::add);
        Map<Problem, Integer> gradesFrequency = new HashMap<>();
        grades.stream().forEach(grade -> {
            if(gradesFrequency.containsKey(grade.getProblem()))
                gradesFrequency.put(grade.getProblem(), gradesFrequency.get(grade.getProblem())+1);
            else
                gradesFrequency.put(grade.getProblem(), 1);
        });
        return gradesFrequency.entrySet().stream().max(Comparator.comparing(entry -> entry.getValue())).get().getKey();
    }

    public Student getMostAssignedStudent(){
        Iterable<Grade> allGrades = this.gradeRepository.findAll();
        Set<Grade> grades = new HashSet<>();
        allGrades.forEach(grades::add);
        Map<Student, Integer> problemsFrequency = new HashMap<>();
        grades.stream().forEach(grade -> {
            if(problemsFrequency.containsKey(grade.getStudent()))
                problemsFrequency.put(grade.getStudent(), problemsFrequency.get(grade.getStudent())+1);
            else
                problemsFrequency.put(grade.getStudent(), 1);
        });
        return problemsFrequency.entrySet().stream().max(Comparator.comparing(entry -> entry.getValue())).get().getKey();
    }
}
