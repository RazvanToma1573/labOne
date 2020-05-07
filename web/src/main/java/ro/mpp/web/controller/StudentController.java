package ro.mpp.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.mpp.core.Domain.Grade;
import ro.mpp.core.Domain.Student;
import ro.mpp.core.Domain.Validators.ValidatorException;
import ro.mpp.core.Service.IStudentService;
import ro.mpp.core.Service.StudentsService;
import ro.mpp.web.converter.GradeConverter;
import ro.mpp.web.converter.StudentConverter;
import ro.mpp.web.dto.*;

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

    @RequestMapping(value = "/students", method = RequestMethod.GET)
    StudentsDTO getStudents() {
        log.trace("Get all students - method entered");
        return new StudentsDTO(studentConverter.convertModelsToDTOs(new ArrayList<Student>(studentsService.get())));
    }

    @RequestMapping(value = "/student/{id}", method = RequestMethod.GET)
    StudentDTO getStudentById(@PathVariable Integer id) throws ValidatorException {
        log.trace("Get a student by ID - method entered");
        return studentConverter.convertModelToDTO(studentsService.getById(id));
    }

    @RequestMapping(value = "/students", method = RequestMethod.POST)
    StudentDTO saveStudent(@RequestBody StudentDTO studentDTO) throws ValidatorException {
        log.trace("Save a student - method entered");
        return studentConverter.convertModelToDTO(studentsService.add(studentConverter.convertDTOtoModel(studentDTO)));
    }

    @RequestMapping(value = "/students/{id}", method = RequestMethod.PUT)
    StudentDTO updateStudent(@PathVariable Integer id, @RequestBody StudentDTO studentDTO) throws ValidatorException {
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
    GradesDTO getGrades() {
        log.trace("Get all grades - method entered");
        return new GradesDTO(gradeConverter.convertModelsToDTOs(new ArrayList<>(studentsService.getGrades())));
    }

    @RequestMapping(value = "/grades/{id}", method = RequestMethod.PUT)
    GradeDTO saveGrade(@PathVariable Integer id, @RequestBody GradeDTO gradeDTO) throws ValidatorException {
        log.trace("Save a grade - method entered");
        return gradeConverter.convertModelToDTO(studentsService.assignGrade(
                gradeDTO.getStudentId(), gradeDTO.getProblemId(), gradeDTO.getActualGrade()
        ));
    }

    @RequestMapping(value = "/grade/{studentId}-{problemId}", method = RequestMethod.GET)
    GradeDTO getGrade(@PathVariable Integer studentId, @PathVariable Integer problemId) {
        log.trace("Get a grade by student ID and problem ID - method entered");
        return gradeConverter.convertModelToDTO(studentsService.getGrades().stream()
        .filter(grade -> grade.getStudentId() == studentId && grade.getProblemId() == problemId)
        .collect(Collectors.toList()).get(0));
    }

    @RequestMapping(value = "/assign-problem", method = RequestMethod.POST)
    ResponseEntity<?> assignProblem(@RequestBody GradeDTO gradeDTO)
        throws ValidatorException {
        log.trace("Assign problem to student - method entered");
        studentsService.assignProblem(gradeDTO.getStudentId(), gradeDTO.getProblemId());
        log.trace("Assign problem to student - method finished");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value="/students/filter/{type}/{argument}", method = RequestMethod.GET)
    StudentsDTO filterStudents(@PathVariable String type, @PathVariable String argument) throws ValidatorException {
        log.trace("Filter students - method entered");
        return new StudentsDTO(studentConverter.convertModelsToDTOs(new ArrayList<>(studentsService.filterService(argument, type))));
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

    @RequestMapping(value = "/students/sorted/{param}", method = RequestMethod.GET)
    StudentsDTO getStudentsSorted(@PathVariable String param) {
        Map<String, Boolean> criteria = new HashMap<>();
        Arrays.stream(param.split("&")).forEach(
                cr -> criteria.put(cr.split("-")[0], cr.split("-")[1].equals("true"))
        );
        System.out.println(criteria);
        return new StudentsDTO(studentConverter.convertModelsToDTOs(new ArrayList<>(studentsService.getSorted(criteria))));
    }

    @RequestMapping(value = "/grades/sorted/{param}", method = RequestMethod.GET)
    GradesDTO getGradesSorted(@PathVariable String param) {
        Map<String, Boolean> criteria = new HashMap<>();
        Arrays.stream(param.split("&")).forEach(
                cr -> criteria.put(cr.split("-")[0], cr.split("-")[1].equals("true"))
        );
        System.out.println(criteria);
        return new GradesDTO(gradeConverter.convertModelsToDTOs(new ArrayList<>(studentsService.getGradesSorted(criteria))));
    }
}
