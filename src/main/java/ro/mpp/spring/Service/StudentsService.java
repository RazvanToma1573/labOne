package ro.mpp.spring.Service;

import ro.mpp.spring.Domain.Grade;
import ro.mpp.spring.Domain.Problem;
import ro.mpp.spring.Domain.Student;
import ro.mpp.spring.Domain.Validators.Validator;
import ro.mpp.spring.Domain.Validators.ValidatorException;
import ro.mpp.spring.Repository.StudentRepository;
import ro.mpp.spring.Repository.GradeRepository;
import ro.mpp.spring.Repository.Sort;

import java.lang.reflect.Field;
import java.util.*;

import java.util.HashSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StudentsService implements IStudentService{
    public static final Logger log = LoggerFactory.getLogger(StudentsService.class);
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private GradeRepository gradeRepository;
    @Autowired
    private Validator<Student> studentValidator;
    @Autowired
    private Validator<Grade> gradeValidator;
    @Autowired
    private IProblemService problemsService;

    /**
     * Adds a new student to the repository
     *
     * @param newStudent is the new student to be added
     * @throws ValidatorException       if the data of the new student is invalid
     * @throws IllegalArgumentException if newStudent is null
     */
    @Override
    public void add (Student newStudent) throws ValidatorException, IllegalArgumentException {
        log.trace("add student - method entered: student={}", newStudent);
        this.studentValidator.validate(newStudent);
        studentRepository.save(newStudent);
        log.trace("add Student - method finished");
    }

    /**
     * Removes a student from the repository and remove all the grades of this stuedent
     *
     * @param id is the ID of the student to be removed
     * @throws IllegalArgumentException if id is null
     */
    @Override
    public void remove(int id) throws IllegalArgumentException {
        log.trace("remove Student - method entered: studentID = {}", id);
        Set<Grade> grades = new HashSet<>();
        this.gradeRepository.findAll().forEach(grades::add);
        grades.stream().filter(grade -> grade.getStudent() == id)
                .forEach(grade -> this.gradeRepository.deleteById(grade.getId()));
        studentRepository.deleteById(id);
        log.trace("remove Student - method finished");
    }

    @Override
    public void removeProblem(int id) throws  IllegalArgumentException{
        log.trace("remove problem with the given id - method entered: problemID = {}", id);
        Set<Grade> grades = new HashSet<>();
        this.gradeRepository.findAll().forEach(grades::add);
        grades.stream().filter(grade -> grade.getProblem() == id)
                .forEach(grade -> this.gradeRepository.deleteById(grade.getId()));
        this.problemsService.remove(id);
        log.trace("remove Problem with the given id - method finished");
    }

    /**
     * Assigns a problem to a student from repository
     *
     * @param studentId is the ID of the student
     * @param problemId   is the id of the new problem to be assigned
     * @throws ValidatorException       Custom exception
     * @throws IllegalArgumentException if the new grade is null
     */
    @Override
    public void assignProblem(int studentId, int problemId) throws ValidatorException, IllegalArgumentException{
        log.trace("assign a problem to a student - method entered: studentID={}, problemId={}",studentId, problemId);
        Optional<Student> checkStudent = this.studentRepository.findById(studentId);
        if (checkStudent.isPresent()) {
            Student student = checkStudent.get();
            this.studentValidator.validate(student);

           Problem problem = this.problemsService.getById(problemId);
           if(this.gradeRepository.findAll().stream().filter(grade -> grade.getStudent() == student.getId() && grade.getProblem() == problemId)
                .collect(Collectors.toList()).size() > 0)
               throw new ValidatorException("This student is already assigned with this problem!");

            this.gradeRepository.save(new Grade(student.getId(), problem.getId(), 0));
        } else {
            throw new ValidatorException("No student found with the given id!");
        }
        log.trace("assign problem to a student - method finished");
    }

    /**
     * Returns all the students from the repository
     *
     * @return an iterable with all the students
     */
    @Override
    public Iterable<Student> get() {
        return this.studentRepository.findAll();
    }

    /**
     * Returns all the grades from the repository
     *
     * @return an iterable with all the grades
     */
    @Override
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
    @Override
    public Student getById(int id) throws ValidatorException, IllegalArgumentException {
        Optional<Student> checkStudent = this.studentRepository.findById(id);
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
    @Override
    public void assignGrade(int studentId, int problemId, int grade) throws ValidatorException, IllegalArgumentException {
        log.trace("assign grade to a student - method entered: studentId={}, problemID={}, grade={}", studentId, problemId, grade);
        Optional<Student> checkStudent = this.studentRepository.findById(studentId);
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
                this.gradeRepository.save(gradeToBeUpdated);
            } catch (IndexOutOfBoundsException exception) {
                throw new ValidatorException("Student does not have to do the given problem!");
            }

        } else {
            throw new ValidatorException("No student found with the given id!");
        }
        log.trace("assign grade to a student - method finished");
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
    @Override
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
                            .map(studentId -> this.studentRepository.findById(studentId).get()).collect(Collectors.toSet());

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
                            .map(studentId -> this.studentRepository.findById(studentId).get())
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
     * @param firstName String
     * @param lastName  string
     * @throws ValidatorException validator exception
     * @throws IllegalArgumentException illegal argument exception
     */
    @Override
    @Transactional
    public void update (int idStudent, String firstName, String lastName) throws ValidatorException, IllegalArgumentException {
        log.trace("update student - method entered: studentID = {}", idStudent);
        Optional<Student> checkStudent = this.studentRepository.findById(idStudent);
        if (checkStudent.isPresent()){
            Student student = checkStudent.get();
            student.setFirstName(firstName);
            student.setLastName(lastName);
        } else {
            throw new ValidatorException("No student found with the given id!");
        }
        log.trace("update student - method finished");
    }

    /**
     * Finds the student with the maximum average grade for report
     * @return the student with the maximum average grade
     */
    @Override
    public Student getStudentWithMaxGrade() {
        Set<Grade> grades = new HashSet<>();
        this.gradeRepository.findAll().forEach(grades::add);
        return this.studentRepository.findById(grades.stream()
                .collect(Collectors.groupingBy(Grade::getStudent))
                .entrySet().stream()
                .map(entry -> new HashMap.SimpleEntry<Integer, Double>(entry.getKey(), entry.getValue().stream().map(Grade::getActualGrade).collect(Collectors.averagingDouble(grade -> (double)grade)))).
                        max(Comparator.comparing(entry -> entry.getValue())).get().getKey()).get();

    }

    /**
     * Finds the problem which was assigned most times for report
     * @return the problem which was assigned most times
     */
    @Override
    public Problem getMaxAssignedProblem() throws ValidatorException {
        Iterable<Grade> allGrades = this.gradeRepository.findAll();
        Set<Grade> grades = new HashSet<>();
        allGrades.forEach(grades::add);
        Map<Problem, Integer> gradesFrequency = new HashMap<>();
        grades.stream().forEach(grade -> {
            if(gradesFrequency.containsKey(grade.getProblem())) {
                try {
                    gradesFrequency.put(this.problemsService.getById(grade.getProblem()), gradesFrequency.get(grade.getProblem())+1);
                } catch (ValidatorException e) {
                    e.printStackTrace();
                }
            }
            else {
                try {
                    gradesFrequency.put(this.problemsService.getById(grade.getProblem()), 1);
                } catch (ValidatorException e) {
                    e.printStackTrace();
                }
            }
        });
        return gradesFrequency.entrySet().stream().max(Comparator.comparing(entry -> entry.getValue())).get().getKey();
    }

    /**
     * Finds the student who has most assigned problem for report
     * @return the student with the most number of assigned problems
     */
    @Override
    public Student getMostAssignedStudent(){
        Iterable<Grade> allGrades = this.gradeRepository.findAll();
        Set<Grade> grades = new HashSet<>();
        allGrades.forEach(grades::add);
        Map<Integer, Integer> problemsFrequency = new HashMap<>();
        grades.stream().forEach(grade -> {
            if(problemsFrequency.containsKey(grade.getStudent()))
                problemsFrequency.put(this.studentRepository.findById(grade.getStudent()).get().getId(), problemsFrequency.get(grade.getStudent())+1);
            else
                problemsFrequency.put(this.studentRepository.findById(grade.getStudent()).get().getId(), 1);
        });
        return this.studentRepository.findById(problemsFrequency.entrySet().stream().max(Comparator.comparing(entry -> entry.getValue())).get().getKey()).get();
    }

    /**
     * Returns the problem with the highest average grade for all students
     * @return Problem with highest average grade
     */
    @Override
    public Problem getProblemHighestAverage() throws ValidatorException {
        Iterable<Grade> allGrades = this.gradeRepository.findAll();
        Set<Grade> grades = new HashSet<>();
        allGrades.forEach(grades::add);
        Map<Integer, Map.Entry<Float, Integer>> averages = new HashMap<>();
        grades.stream().forEach(grade ->{
            if(averages.containsKey(grade.getProblem()))
                averages.put(grade.getProblem(), new HashMap.SimpleEntry<>(averages.get(grade.getProblem()).getKey()+grade.getActualGrade(), averages.get(grade.getProblem()).getValue()+1));
            else
                averages.put(grade.getProblem(), new HashMap.SimpleEntry<Float, Integer>((float)grade.getActualGrade(), 1));
        });
        int problemId = averages.entrySet().stream()
                .map(entry -> new HashMap.SimpleEntry<>(entry.getKey(), entry.getValue().getKey()/entry.getValue().getValue()))
                .max(Comparator.comparing(entry -> entry.getValue())).get().getKey();
        return this.problemsService.getById(problemId);
    }

    /**
     * Return the student with highest average grade at hard problems
     * @return the student with max Grade
     */
    @Override
    public Student getStudentHighestAverageHard(){
        Set<Grade> grades = new HashSet<>();
        this.gradeRepository.findAll().forEach(grades::add);
        return this.studentRepository.findById(grades.stream().filter(grade -> {
            try{
                return this.problemsService.getById(grade.getProblem()).getDifficulty().equals("hard");
            } catch (Exception e){
                e.printStackTrace();
            }
            return false;
        })
                .collect(Collectors.groupingBy(Grade::getStudent))
                .entrySet().stream()
                .map(entry -> new HashMap.SimpleEntry<Integer, Double>(entry.getKey(), entry.getValue().stream().map(Grade::getActualGrade).collect(Collectors.averagingDouble(grade -> (double)grade)))).
                max(Comparator.comparing(entry -> entry.getValue())).get().getKey()).get();

    }
    @Override
    public Iterable<Student> getSorted(Map<String, Boolean> criteria) {
        Sort sort = new Sort();
        criteria.entrySet().stream().forEach(cr -> sort.and(new Sort(cr.getValue(), cr.getKey())));
        List<Student> result = new ArrayList<>();
        studentRepository.findAll().forEach(result::add);
        try{
            final Class studentClass;
            final Class baseClass;

            studentClass = Class.forName("ro.mpp.spring.Domain.Student");
            baseClass = Class.forName("ro.mpp.spring.Domain.BaseEntity");

            Optional<Comparator<Student>> comparator = sort.getCriteria().entrySet().stream()
                    .map(cr ->{
                        try{
                            final Field field = cr.getKey().equals("id") ?
                                    baseClass.getDeclaredField(cr.getKey()) :
                                    studentClass.getDeclaredField(cr.getKey());
                            field.setAccessible(true);
                            return (Comparator<Student>) (student, t1) -> {
                                try{
                                    Comparable c1 = (Comparable)field.get(student);
                                    Comparable c2 = (Comparable)field.get(t1);
                                    return cr.getValue() ? c2.compareTo(c1) : c1.compareTo(c2);
                                } catch (IllegalAccessException e){
                                    System.out.println(e.getMessage());
                                }
                                return 0;
                            };
                        } catch (NoSuchFieldException e){
                            System.out.println(e.getMessage());
                        }
                        return null;
                    })
                    .reduce((c1, c2) -> c1.thenComparing(c2));

            Collections.sort(result, comparator.get());

        } catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }

        return result;
    }
    @Override
    public Iterable<Grade> getGradesSorted(Map<String, Boolean> criteria) {
        Sort sort = new Sort();
        criteria.entrySet().stream().forEach(cr -> sort.and(new Sort(cr.getValue(), cr.getKey())));
        List<Grade> result = new ArrayList<>();
        gradeRepository.findAll().forEach(result::add);

        try{
            final Class gradeClass;

            gradeClass = Class.forName("ro.mpp.spring.Domain.Grade");

            Optional<Comparator<Grade>> comparator = sort.getCriteria().entrySet().stream()
                    .map(cr ->{
                        try{
                            final Field field = gradeClass.getDeclaredField(cr.getKey());
                            field.setAccessible(true);
                            return (Comparator<Grade>) (grade, t1) -> {
                                try{
                                    Comparable c1 = (Comparable)field.get(grade);
                                    Comparable c2 = (Comparable)field.get(t1);
                                    return cr.getValue() ? c2.compareTo(c1) : c1.compareTo(c2);
                                } catch (IllegalAccessException e){
                                    System.out.println(e.getMessage());
                                }
                                return 0;
                            };
                        } catch (NoSuchFieldException e){
                            System.out.println(e.getMessage());
                        }
                        return null;
                    })
                    .reduce((c1, c2) -> c1.thenComparing(c2));
            Collections.sort(result, comparator.get());

        } catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        return result;
    }
}
