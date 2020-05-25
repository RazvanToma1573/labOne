package ro.mpp.core.Service;


import java.lang.reflect.Field;
import java.util.*;

import java.util.HashSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.mpp.core.Domain.Grade;
import ro.mpp.core.Domain.Problem;
import ro.mpp.core.Domain.Student;
import ro.mpp.core.Domain.Validators.Validator;
import ro.mpp.core.Domain.Validators.ValidatorException;
import ro.mpp.core.Repository.GradeRepository;
import ro.mpp.core.Repository.StudentRepository;
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
    public Student add (Student newStudent) throws IllegalArgumentException {
        log.trace("add student - method entered: student={}", newStudent);
        Student student = this.studentRepository.save(newStudent);
        log.trace("add Student - method finished");
        return student;
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
        if(this.studentRepository.findById(id).isEmpty()){
            log.trace("remove student - exception thrown");
            throw new IllegalArgumentException("The is no student with such ID");
        }
        Set<Grade> grades = new HashSet<>();
        this.gradeRepository.findAll().forEach(grades::add);
        grades.stream().filter(grade -> grade.getStudentId() == id)
                .forEach(grade -> this.gradeRepository.deleteById(grade.getId()));
        studentRepository.deleteById(id);
        log.trace("remove Student - method finished");
    }

    @Override
    public void removeGrade(int id) {
        log.trace("Remove grade {} - entered", id);
        this.gradeRepository.deleteById(id);
        log.trace("Remove grade - finished");
    }

    @Override
    public void removeProblem(int id) throws  IllegalArgumentException{
        log.trace("remove problem with the given id - method entered: problemID = {}", id);
        Set<Grade> grades = new HashSet<>();
        this.gradeRepository.findAll().forEach(grades::add);
        grades.stream().filter(grade -> grade.getProblemId() == id)
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
           if(this.gradeRepository.findAll().stream().filter(grade -> grade.getStudentId() == student.getId() && grade.getProblemId() == problemId).count() > 0)
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
    public Page<Student> get(int page) {
        log.trace("Get all students - method entered");
        return this.studentRepository.findAll(PageRequest.of(page, 5));
    }

    @Override
    public List<Student> get() {
        return this.studentRepository.findAll();
    }

    /**
     * Returns all the grades from the repository
     *
     * @return an iterable with all the grades
     */
    @Override
    public Page<Grade> getGrades(int page) {
        log.trace("Get all grades - method entered");
        return this.gradeRepository.findAll(PageRequest.of(page, 5));
    }

    @Override
    public List<Grade> getGrades() {
        log.trace("Get all grades - method entered");
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
        log.trace("Get a student by his ID - method entered");
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
     * @throws IllegalArgumentException if new grade is null
     */
    @Override
    @Transactional
    public Grade assignGrade(int studentId, int problemId, int grade) throws ValidatorException, IllegalArgumentException {
        log.trace("assign grade to a student - method entered: studentId={}, problemID={}, grade={}", studentId, problemId, grade);
        Student student = this.getById(studentId);
        Problem problem = this.problemsService.getById(problemId);
        int id = this.gradeRepository.findAll().stream().filter(gr ->
                gr.getStudentId() == studentId && gr.getProblemId() == problemId)
                .collect(Collectors.toList()).get(0).getId();
        Grade gr = this.gradeRepository.findById(id).get();
        gr.setActualGrade(grade);
        log.trace("assign grade to a student - method finished");
        return gr;
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
    public Page<Student> filterService(int page, String argument, String type) throws ValidatorException {
        log.trace("Filter students - method entered");
        Iterable<Student> students = studentRepository.findAll();
        Iterable<Grade> grades = gradeRepository.findAll();
        switch (type) {
            case "FIRSTNAME": {

                //Set<Student> filteredStudents = new HashSet<>();
                //students.forEach(filteredStudents::add);
                //filteredStudents.removeIf(student -> !student.getFirstName().contains(argument));
                //return filteredStudents;
                return this.studentRepository.findByFirstName(argument, PageRequest.of(page, 5));

            }
            case "LASTNAME": {

                Set<Student> filteredStudents = new HashSet<>();
                students.forEach(filteredStudents::add);
                filteredStudents.removeIf(student -> !student.getLastName().contains(argument));
                return this.studentRepository.findByLastName(argument, PageRequest.of(page, 5));

            }
            case "PROBLEM":
                try {
                    int problemID = Integer.parseInt(argument);

                    Set<Grade> filteredGrades = new HashSet<>();
                    grades.forEach(filteredGrades::add);
                    filteredGrades.removeIf(grade -> !(grade.getProblemId()==problemID));
                    List<Student> stds = filteredGrades.stream().map(grade -> this.studentRepository.findById(grade.getStudentId()).get())
                            .collect(Collectors.toList());

                    //return filteredGrades.stream().map(Grade::getStudentId)
                       //     .map(studentId -> this.studentRepository.findById(studentId).get()).collect(Collectors.toSet());
                    return null;
                } catch (NumberFormatException exception) {
                    throw new ValidatorException("ID not valid");
                }
            case "GRADE":
                try {
                    int wantedGrade = Integer.parseInt(argument);

                    Set<Grade> filteredGrades = new HashSet<>();
                    grades.forEach(filteredGrades::add);
                    filteredGrades.removeIf(grade -> grade.getActualGrade() != (wantedGrade));
                    return null;
                    //return filteredGrades.stream().map(Grade::getStudentId)
                      //      .map(studentId -> this.studentRepository.findById(studentId).get())
                        //    .collect(Collectors.toSet());

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
    public Student update (int idStudent, String firstName, String lastName) throws ValidatorException, IllegalArgumentException {
        log.trace("update student - method entered: studentID = {}", idStudent);
        Student student = studentRepository.findById(idStudent).orElse(new Student(firstName, lastName));
        student.setFirstName(firstName);
        student.setLastName(lastName);
        log.trace("update student - method finished");
        return student;
    }

    /**
     * Finds the student with the maximum average grade for report
     * @return the student with the maximum average grade
     */
    @Override
    public Student getStudentWithMaxGrade() {
        log.trace("Get the student with highest average grade - method entered");
        Set<Grade> grades = new HashSet<>();
        this.gradeRepository.findAll().forEach(grades::add);
        log.trace("Get the student with highest average grade - method finished");
        return this.studentRepository.findById(grades.stream()
                .collect(Collectors.groupingBy(Grade::getStudentId))
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
        log.trace("Get the problem that was assigned most - method entered");
        Iterable<Grade> allGrades = this.gradeRepository.findAll();
        Set<Grade> grades = new HashSet<>();
        allGrades.forEach(grades::add);
        Map<Problem, Integer> gradesFrequency = new HashMap<>();
        grades.stream().forEach(grade -> {
            if(gradesFrequency.containsKey(grade.getProblemId())) {
                try {
                    gradesFrequency.put(this.problemsService.getById(grade.getProblemId()), gradesFrequency.get(grade.getProblemId())+1);
                } catch (ValidatorException e) {
                    e.printStackTrace();
                }
            }
            else {
                try {
                    gradesFrequency.put(this.problemsService.getById(grade.getProblemId()), 1);
                } catch (ValidatorException e) {
                    e.printStackTrace();
                }
            }
        });
        log.trace("Get the problem that was assigned most - method finished");
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
            if(problemsFrequency.containsKey(grade.getStudentId()))
                problemsFrequency.put(this.studentRepository.findById(grade.getStudentId()).get().getId(), problemsFrequency.get(grade.getStudentId())+1);
            else
                problemsFrequency.put(this.studentRepository.findById(grade.getStudentId()).get().getId(), 1);
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
            if(averages.containsKey(grade.getProblemId()))
                averages.put(grade.getProblemId(), new HashMap.SimpleEntry<>(averages.get(grade.getProblemId()).getKey()+grade.getActualGrade(), averages.get(grade.getProblemId()).getValue()+1));
            else
                averages.put(grade.getProblemId(), new HashMap.SimpleEntry<Float, Integer>((float)grade.getActualGrade(), 1));
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
        log.trace("Get the student with the highest avg grade at hard problems - method entered");
        Set<Grade> grades = new HashSet<>();
        this.gradeRepository.findAll().forEach(grades::add);
        log.trace("Get the student with the highest avg grade at hard problems - method entered");
        return this.studentRepository.findById(grades.stream().filter(grade -> {
            try{
                return this.problemsService.getById(grade.getProblemId()).getDifficulty().equals("hard");
            } catch (Exception e){
                e.printStackTrace();
            }
            return false;
        })
                .collect(Collectors.groupingBy(Grade::getStudentId))
                .entrySet().stream()
                .map(entry -> new HashMap.SimpleEntry<Integer, Double>(entry.getKey(), entry.getValue().stream().map(Grade::getActualGrade).collect(Collectors.averagingDouble(grade -> (double)grade)))).
                max(Comparator.comparing(entry -> entry.getValue())).get().getKey()).get();

    }
    @Override
    public Page<Student> getSorted(int page, Map<String, Boolean> criteria) {
        log.trace("Get students, sorted - method entered");
        boolean first = true;
        Sort sort = null;
        for(Map.Entry<String, Boolean> entry : criteria.entrySet()) {
            if(first) {
                if(entry.getValue()) {
                    sort = new Sort(Sort.Direction.DESC, entry.getKey());
                }
                else {
                    sort = new Sort(Sort.Direction.ASC, entry.getKey());
                }
                first = false;
            }
            else {
                if(entry.getValue()) {
                    sort = sort.and(new Sort(Sort.Direction.DESC, entry.getKey()));
                }
                else{
                    sort = sort.and(new Sort(Sort.Direction.ASC, entry.getKey()));
                }
            }
        }
        return this.studentRepository.findAll(PageRequest.of(page, 5, sort));

    }
    @Override
    public Page<Grade> getGradesSorted(int page, Map<String, Boolean> criteria) {
        log.trace("Get grades, sorted - method entered");
        boolean first = true;
        Sort sort = null;
        for(Map.Entry<String, Boolean> entry : criteria.entrySet()) {
            if(first) {
                if(entry.getValue()) {
                    sort = new Sort(Sort.Direction.DESC, entry.getKey());
                }
                else {
                    sort = new Sort(Sort.Direction.ASC, entry.getKey());
                }
                first = false;
            }
            else {
                if(entry.getValue()) {
                    sort = sort.and(new Sort(Sort.Direction.DESC, entry.getKey()));
                }
                else{
                    sort = sort.and(new Sort(Sort.Direction.ASC, entry.getKey()));
                }
            }
        }
        return this.gradeRepository.findAll(PageRequest.of(page, 5, sort));
    }
}
