package ro.mpp.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ro.mpp.core.Domain.Grade;
import ro.mpp.core.Domain.Student;
import ro.mpp.core.Domain.Validators.ValidatorException;
import ro.mpp.core.Service.IStudentService;
import ro.mpp.core.Service.StudentsService;
import ro.mpp.web.converter.GradeConverter;
import ro.mpp.web.converter.StudentConverter;
import ro.mpp.web.dto.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class StudentController {
    public static final Logger log = LoggerFactory.getLogger(StudentController.class);
    private boolean studentsSorted = false;
    private boolean gradesSorted = false;
    private boolean filtered = false;
    private String argument = "";
    private String type = "";
    private Map<String, Boolean> studentSortCriteria;
    private Map<String, Boolean> gradesSortCriteria;

    @Autowired
    private IStudentService studentsService;

    @Autowired
    private StudentConverter studentConverter;

    @Autowired
    private GradeConverter gradeConverter;

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<String> handleException(ConstraintViolationException e) {
        return new ResponseEntity<>("Validation Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<String> handleException2(MethodArgumentNotValidException e) {
        return new ResponseEntity<>("Validation Error: Your input is not a valid one!" , HttpStatus.BAD_REQUEST);
    }


    @RequestMapping(value = "/students", method = RequestMethod.GET)
    List<StudentDTO> getAllStudents() {
        log.trace("getAllStudents");
        List<Student> students = studentsService.get();
        log.trace("getAllStudents: students={}", students);
        return new ArrayList<>(studentConverter.convertModelsToDTOs(students));
    }

    @RequestMapping(value = "/students/{page}", method = RequestMethod.GET)
    List<StudentDTO> getStudents(@PathVariable Integer page) throws ValidatorException {
        log.trace("getStudents");
        Page<Student> students;
        if(this.studentsSorted) {
            students = this.studentsService.getSorted(page, this.studentSortCriteria);
        }
        else if(this.filtered)
            students = this.studentsService.filterService(page, argument, type);
        else
            students = studentsService.get(page);
        log.trace("getStudents: students={}", students);
        return new ArrayList<>(studentConverter.convertModelsToDTOs(students.getContent()));
    }

    @RequestMapping(value = "/student/{id}", method = RequestMethod.GET)
    StudentDTO getStudentById(@PathVariable Integer id) throws ValidatorException {
        log.trace("Get a student by ID - method entered");
        return studentConverter.convertModelToDTO(studentsService.getById(id));
    }

    @RequestMapping(value = "/students", method = RequestMethod.POST)
    ResponseEntity<?> saveStudent(@Valid @RequestBody StudentDTO studentDTO) {
        log.trace("Save a student - method entered");
        studentsService.add(studentConverter.convertDTOtoModel(studentDTO));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/students/{id}", method = RequestMethod.PUT)
    StudentDTO updateStudent(@PathVariable Integer id, @Valid @RequestBody StudentDTO studentDTO) throws ValidatorException {
        log.trace("Update a student - method entered");
        return studentConverter.convertModelToDTO(studentsService.update(id,
                studentConverter.convertDTOtoModel(studentDTO).getFirstName(),
                studentConverter.convertDTOtoModel(studentDTO).getLastName()));
    }

    @RequestMapping(value = "/students/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteStudent(@PathVariable Integer id) {
        log.trace("Delete a student - method entered");
        studentsService.remove(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/grades", method = RequestMethod.GET)
    List<GradeDTO> getGrades() {
        log.trace("getGrades");
        List<Grade> grades = this.studentsService.getGrades();
        log.trace("getGrades: grades={}", grades);
        return new ArrayList<>(gradeConverter.convertModelsToDTOs(grades));
    }

    @RequestMapping(value = "/grades/{page}", method = RequestMethod.GET)
    List<GradeDTO> getGrades(@PathVariable Integer page) {
        log.trace("getGrades");
        Page<Grade> grades;
        if(this.gradesSorted) {
            grades = this.studentsService.getGradesSorted(page, this.gradesSortCriteria);
        }
        else {
            grades = this.studentsService.getGrades(page);
        }
        log.trace("getGrades: grades={}", grades);
        return new ArrayList<>(gradeConverter.convertModelsToDTOs(grades.getContent()));
    }

    @RequestMapping(value = "/grades/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> removeGrades(@PathVariable Integer id) {
        log.trace("removeGrades - entered, problem ID = {}", id);
        studentsService.removeProblem(id);
        log.trace("removeGrade - method finished");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/grades/remove/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> removeGrade(@PathVariable Integer id) {
        log.trace("removeGrades - entered, problem ID = {}", id);
        studentsService.removeGrade(id);
        log.trace("removeGrade - method finished");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/grades/{id}", method = RequestMethod.PUT)
    GradeDTO saveGrade(@PathVariable Integer id, @Valid @RequestBody GradeDTO gradeDTO) throws ValidatorException {
        log.trace("Save a grade - method entered");
        return gradeConverter.convertModelToDTO(studentsService.assignGrade(
                gradeDTO.getStudentId(), gradeDTO.getProblemId(), gradeDTO.getActualGrade()
        ));
    }

    @RequestMapping(value = "/grade/{studentId}-{problemId}", method = RequestMethod.GET)
    GradeDTO getGrade(@PathVariable Integer studentId, @PathVariable Integer problemId) {
        log.trace("Get a grade by student ID and problem ID - method entered");
        return gradeConverter.convertModelToDTO(studentsService.getGrades(0).stream()
        .filter(grade -> grade.getStudentId() == studentId && grade.getProblemId() == problemId)
        .collect(Collectors.toList()).get(0));
    }

    @RequestMapping(value = "/grades", method = RequestMethod.POST)
    ResponseEntity<?> assignProblem(@Valid @RequestBody GradeDTO gradeDTO)
        throws ValidatorException {
        log.trace("Assign problem to student - method entered");
        studentsService.assignProblem(gradeDTO.getStudentId(), gradeDTO.getProblemId());
        log.trace("Assign problem to student - method finished");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value="/students/filter/{page}/{type}/{argument}", method = RequestMethod.GET)
    List<StudentDTO> filterStudents(@PathVariable Integer page, @PathVariable String type, @PathVariable String argument) throws ValidatorException {
        this.filtered = true;
        this.studentsSorted = false;
        this.argument = argument;
        this.type = type;
        log.trace("Filter students - method entered");
        return new ArrayList<>(studentConverter.convertModelsToDTOs(studentsService.filterService(page, argument, type).getContent()));
    }

    @RequestMapping(value = "/reports/student-max-grade", method = RequestMethod.GET)
    StudentDTO studentWithHighestGrade() {
        return studentConverter.convertModelToDTO(studentsService.getStudentWithMaxGrade());
    }

    @RequestMapping(value = "/reports/student-max-grade-hard", method = RequestMethod.GET)
    StudentDTO studentHighestGradeHard() {
        return studentConverter.convertModelToDTO(studentsService.getStudentHighestAverageHard());
    }

    @RequestMapping(value = "/reports/student-max-problems", method = RequestMethod.GET)
    StudentDTO studentDTO() {
        return studentConverter.convertModelToDTO(studentsService.getMostAssignedStudent());
    }

    @RequestMapping(value = "/students/sorted/{page}/{param}", method = RequestMethod.GET)
    List<StudentDTO> getStudentsSorted(@PathVariable Integer page, @PathVariable String param) {
        log.trace("getStudentsSorted " + param);
        this.studentsSorted = true;
        this.filtered = false;
        Map<String, Boolean> criteria = new HashMap<>();
        Arrays.stream(param.split("&")).forEach(
                cr -> criteria.put(cr.split("-")[0], cr.split("-")[1].equals("true"))
        );
        this.studentSortCriteria = criteria;
        return new ArrayList<>(studentConverter.convertModelsToDTOs(studentsService.getSorted(page, criteria).getContent()));
    }

    @RequestMapping(value = "/grades/sorted/{page}/{param}", method = RequestMethod.GET)
    List<GradeDTO> getGradesSorted(@PathVariable Integer page, @PathVariable String param) {
        this.gradesSorted = true;
        Map<String, Boolean> criteria = new HashMap<>();
        Arrays.stream(param.split("&")).forEach(
                cr -> criteria.put(cr.split("-")[0], cr.split("-")[1].equals("true"))
        );
        this.gradesSortCriteria = criteria;
        return new ArrayList<>(gradeConverter.convertModelsToDTOs(studentsService.getGradesSorted(page, criteria).getContent()));
        }
}
