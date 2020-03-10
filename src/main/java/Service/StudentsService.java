package Service;

import Domain.Grade;
import Domain.Problem;
import Domain.Student;
import Domain.Validators.Validator;
import Domain.Validators.ValidatorException;
import Repository.Repository;

import java.util.*;

import java.util.HashSet;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class StudentsService {

    private Repository<Integer, Student> studentRepository;
    private Repository<Integer, Grade> gradeRepository;
    private Validator<Student> studentValidator;
    private Validator<Grade> gradeValidator;
    private ProblemsService problemsService;

    /**
     * Creates a new Student service
     * @param studentRepository student repository
     * @param gradeRepository grade repository
     * @param studentValidator student validator
     * @param gradeValidator grade validator
     * @param problemService problem service
     */
    public StudentsService(Repository<Integer, Student> studentRepository, Repository<Integer, Grade> gradeRepository, Validator<Student> studentValidator, Validator<Grade> gradeValidator, ProblemsService problemService) {
        this.studentRepository = studentRepository;
        this.gradeRepository = gradeRepository;
        this.studentValidator = studentValidator;
        this.gradeValidator = gradeValidator;
        this.problemsService = problemService;
    }

    /**
     * Adds a new student to the repository
     *
     * @param newStudent is the new student to be added
     * @throws ValidatorException       if the data of the new student is invalid
     * @throws IllegalArgumentException if newStudent is null
     */
    public void add (Student newStudent) throws ValidatorException, IllegalArgumentException {
        this.studentValidator.validate(newStudent);
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
     * @param problemId   is the id of the new problem to be assigned
     * @throws ValidatorException       Custom exception
     * @throws IllegalArgumentException if the new grade is null
     */
    public void assignProblem(int studentId, int problemId) throws ValidatorException, IllegalArgumentException{
        Optional<Student> checkStudent = this.studentRepository.findOne(studentId);
        if (checkStudent.isPresent()) {
            Student student = checkStudent.get();
            this.studentValidator.validate(student);

           Problem problem = this.problemsService.getById(problemId);

            this.gradeRepository.save(new Grade(student.getId(), problem.getId(), 0));
        } else {
            throw new ValidatorException("No student found with the given id!");
        }
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
     * @throws ValidatorException custom exception
     * @throws IllegalArgumentException if id is null
     */
    public Student getById(int id) throws ValidatorException, IllegalArgumentException {
        Optional<Student> checkStudent = this.studentRepository.findOne(id);
        if (checkStudent.isPresent()) {
            return checkStudent.get();
        } else {
            throw new ValidatorException("No student found with the given id!");
        }
    }

    /**
     * Assigns a grade to a student from repository for a given problem
     *
     * @param studentId is the ID of the stundet
     * @param problemId   is the problem to be graded
     * @param grade     is the grade (0..10)
     * @throws ValidatorException       custom exception
     * @throws IllegalArgumentException if new grade is null
     */
    public void assignGrade(int studentId, int problemId, int grade) throws ValidatorException, IllegalArgumentException {
        Optional<Student> checkStudent = this.studentRepository.findOne(studentId);
        if (checkStudent.isPresent()) {
            Student student = checkStudent.get();
            this.studentValidator.validate(student);

            Problem problem = this.problemsService.getById(problemId);

            Iterable<Grade> grades = this.gradeRepository.findAll();
            Set<Grade> gradeSet = new HashSet<>();
            grades.forEach(gradeSet::add);
            try {
                Grade gradeToBeUpdated = gradeSet.stream().filter(grade1 -> grade1.getStudent()==student.getId() && grade1.getProblem()==problem.getId()).collect(Collectors.toList()).get(0);
                Grade gradeToValidate = new Grade(student.getId(), problem.getId(), grade);
                this.gradeValidator.validate(gradeToValidate);

                gradeToBeUpdated.setActualGrade(grade);
                this.gradeRepository.update(gradeToBeUpdated);
            } catch (IndexOutOfBoundsException exception) {
                throw new ValidatorException("Student does not have to do the given problem!");
            }

        } else {
            throw new ValidatorException("No student found with the given id!");
        }
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
        switch (type) {
            case "FIRSTNAME": {

                Set<Student> filteredStudents = new HashSet<>();
                students.forEach(filteredStudents::add);
                filteredStudents.removeIf(student -> !student.getFirstName().contains(argument));
                return filteredStudents;

            }
            case "LASTNAME": {

                Set<Student> filteredStudents = new HashSet<>();
                students.forEach(filteredStudents::add);
                filteredStudents.removeIf(student -> !student.getLastName().contains(argument));
                return filteredStudents;

            }
            case "PROBLEM":
                try {
                    int problemID = Integer.parseInt(argument);

                    Set<Grade> filteredGrades = new HashSet<>();
                    grades.forEach(filteredGrades::add);
                    filteredGrades.removeIf(grade -> !(grade.getProblem()==problemID));

                    return filteredGrades.stream().map(Grade::getStudent)
                            .map(studentId -> this.studentRepository.findOne(studentId).get()).collect(Collectors.toSet());

                } catch (NumberFormatException exception) {
                    throw new ValidatorException("ID not valid");
                }
            case "GRADE":
                try {
                    int wantedGrade = Integer.parseInt(argument);

                    Set<Grade> filteredGrades = new HashSet<>();
                    grades.forEach(filteredGrades::add);
                    filteredGrades.removeIf(grade -> grade.getActualGrade() != (wantedGrade));

                    return filteredGrades.stream().map(Grade::getStudent)
                            .map(studentId -> this.studentRepository.findOne(studentId).get())
                            .collect(Collectors.toSet());

                } catch (NumberFormatException exception) {
                    throw new ValidatorException("Grade not valid");
                }
        }
        return null;
    }

    /**
     * Updates a student on the field present in the type
     * @param idStudent int
     * @param type String
     * @param update  string
     * @throws ValidatorException validator exception
     * @throws IllegalArgumentException illegal argument exception
     */
    public void update (int idStudent, String type, String update) throws ValidatorException, IllegalArgumentException {
        Optional<Student> checkStudent = this.studentRepository.findOne(idStudent);
        if (checkStudent.isPresent()){
            Student student = checkStudent.get();
            if (type.equals("FIRST")) {
                student.setFirstName(update);
            } else if (type.equals("LAST")) {
                student.setLastName(update);
            }
            this.studentRepository.update(student);
        } else {
            throw new ValidatorException("No student found with the given id!");
        }
    }

    /**
     * Finds the student with the maximum average grade for report
     * @return the student with the maximum average grade
     */
    public Student getStudentWithMaxGrade() {
        Iterable<Grade> grades = this.gradeRepository.findAll();
        Set<Grade> gradesSet = new HashSet<>();
        grades.forEach(gradesSet::add);
        Map<Student, Map.Entry<Float, Integer>> averages = new HashMap<>();
        gradesSet.stream().forEach(grade -> {
            if (averages.containsKey(grade.getStudent()))
                averages.put(this.studentRepository.findOne(grade.getStudent()).get(), new HashMap.SimpleEntry<>(averages.get(grade.getStudent()).getKey() + grade.getActualGrade(), averages.get(grade.getStudent()).getValue() + 1));
            else
                averages.put(this.studentRepository.findOne(grade.getStudent()).get(), new HashMap.SimpleEntry<>((float) grade.getActualGrade(), 1));
        });
        return averages.entrySet().stream().map(entry -> new HashMap.SimpleEntry<Student, Float>(entry.getKey(), entry.getValue().getKey() / entry.getValue().getValue()))
                .max(Comparator.comparing(entry -> entry.getValue())).get().getKey();

    }

    /**
     * Finds the problem which was assigned most times for report
     * @return the problem which was assigned most times
     */
    public Problem getMaxAssignedProblem() throws ValidatorException {
        Iterable<Grade> allGrades = this.gradeRepository.findAll();
        Set<Grade> grades = new HashSet<>();
        allGrades.forEach(grades::add);
        Map<Problem, Integer> gradesFrequency = new HashMap<>();
        grades.stream().forEach(grade -> {
            if(gradesFrequency.containsKey(grade.getProblem()))
                gradesFrequency.put(this.problemsService.getById(grade.getProblem()), gradesFrequency.get(grade.getProblem())+1);
            else
                gradesFrequency.put(this.problemsService.getById(grade.getProblem()), 1);
        });
        return gradesFrequency.entrySet().stream().max(Comparator.comparing(entry -> entry.getValue())).get().getKey();
    }

    /**
     * Finds the student who has most assigned problem for report
     * @return the student with the most number of assigned problems
     */
    public Student getMostAssignedStudent(){
        Iterable<Grade> allGrades = this.gradeRepository.findAll();
        Set<Grade> grades = new HashSet<>();
        allGrades.forEach(grades::add);
        Map<Student, Integer> problemsFrequency = new HashMap<>();
        grades.stream().forEach(grade -> {
            if(problemsFrequency.containsKey(grade.getStudent()))
                problemsFrequency.put(this.studentRepository.findOne(grade.getStudent()).get(), problemsFrequency.get(grade.getStudent())+1);
            else
                problemsFrequency.put(this.studentRepository.findOne(grade.getStudent()).get(), 1);
        });
        return problemsFrequency.entrySet().stream().max(Comparator.comparing(entry -> entry.getValue())).get().getKey();
    }
}
