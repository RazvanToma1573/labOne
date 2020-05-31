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
        List<Student> students = studentsService.findAll();
        log.trace("getAllStudents: students={}", students);
        return new ArrayList<>(studentConverter.convertModelsToDTOs(students));
    }

    @RequestMapping(value = "/student/{id}", method = RequestMethod.GET)
    StudentDTO getStudentById(@PathVariable Integer id) throws ValidatorException {
        log.trace("Get a student by ID - method entered");
        return studentConverter.convertModelToDTO(studentsService.getById(id));
    }

    @RequestMapping(value = "/students", method = RequestMethod.POST)
    StudentDTO saveStudent(@Valid @RequestBody StudentDTO studentDTO) {
        log.trace("Save a student - method entered");
        Student student = studentsService.add(studentDTO.getId(), studentDTO.getFirstName(), studentDTO.getLastName());
        var studentDTO1 = studentConverter.convertModelToDTO(student);
        return studentDTO1;
    }

    @RequestMapping(value = "/students/{id}", method = RequestMethod.PUT)
    StudentDTO updateStudent(@PathVariable Integer id, @Valid @RequestBody StudentDTO studentDTO) throws ValidatorException {
        log.trace("Update a student - method entered");
        Student student = studentsService.update(id,
                studentDTO.getFirstName(),
                studentDTO.getLastName());
        StudentDTO studentDTO1 = studentConverter.convertModelToDTO(student);
        return studentDTO1;
    }

    @RequestMapping(value = "/students/{id}", method = RequestMethod.DELETE)
    StudentDTO deleteStudent(@PathVariable Integer id) {
        log.trace("Delete a student - method entered");
        Student student = studentsService.remove(id);
        var studentDTO = studentConverter.convertModelToDTO(student);
        return studentDTO;
    }

    @RequestMapping(value = "/grades", method = RequestMethod.GET)
    List<GradeDTO> getGrades() {
        log.trace("getGrades");
        Set<Grade> grades = this.studentsService.findAllGrades();
        log.trace("getGrades: grades={}", grades);
        return new ArrayList<>(gradeConverter.convertModelsToDTOs(grades));
    }

    @RequestMapping(value = "/grades/remove", method = RequestMethod.DELETE)
    ResponseEntity<?> removeGrade(@RequestBody Integer studentId, @RequestBody int problemId) {
        log.trace("removeGrade - entered");
        studentsService.removeGrade(studentId, problemId);
        log.trace("removeGrade - method finished");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/grades/{studentId}/{problemId}/{grade}", method = RequestMethod.PUT)
    ResponseEntity<?> saveGrade(@PathVariable Integer studentId, @PathVariable int  problemId, @PathVariable int grade) throws ValidatorException {
        log.trace("Save a grade - method entered");
        studentsService.assignGrade(
                studentId, problemId, grade
        );
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/grades/{studentId}/{problemId}", method = RequestMethod.POST)
    ResponseEntity<?> assignProblem(@PathVariable int studentId, @PathVariable int problemId)
        throws ValidatorException {
        log.trace("Assign problem to student - method entered");
        studentsService.assignProblem(studentId, problemId);
        log.trace("Assign problem to student - method finished");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/students/filter/{firstName}", method = RequestMethod.GET)
    List<StudentDTO> getAllStudentsByFirstName(@PathVariable String firstName) {
        log.trace("Filter by first name - entered");
        List<Student> students = studentsService.findAllByFirstName(firstName);
        log.trace("Filter by first name - finished");
        return new ArrayList<>(studentConverter.convertModelsToDTOs(students));
    }

}
