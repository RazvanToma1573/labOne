package ro.mpp.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.mpp.core.Domain.Problem;
import ro.mpp.core.Domain.Student;
import ro.mpp.core.Domain.Validators.ValidatorException;
import ro.mpp.core.Service.IProblemService;
import ro.mpp.core.Service.ProblemsService;
import ro.mpp.web.converter.ProblemConverter;
import ro.mpp.web.dto.ProblemDTO;
import ro.mpp.web.dto.ProblemsDTO;
import ro.mpp.web.dto.StudentsDTO;

import javax.validation.Valid;
import java.util.*;

@RestController
public class ProblemController {
    public static final Logger log = LoggerFactory.getLogger(ProblemController.class);
    private boolean problemsSorted = false;
    private Map<String, Boolean> problemSortedCriteria;

    @Autowired
    private IProblemService problemsService;

    @Autowired
    private ProblemConverter problemConverter;

    @RequestMapping(value = "/problems", method = RequestMethod.GET)
    List<ProblemDTO> getProblems() {
        log.trace("getProblems");
        List<Problem> problems = problemsService.get();
        log.trace("getProblems: problems={}", problems);
        return new ArrayList<>(problemConverter.convertModelsToDTOs(problems));
    }

    @RequestMapping(value = "/problems/{page}", method = RequestMethod.GET)
    List<ProblemDTO> getProblems(@PathVariable Integer page) {
        log.trace("getProblems");
        Page<Problem> problems;
        if(this.problemsSorted) {
            problems = this.problemsService.getSorted(page, this.problemSortedCriteria);
        }
        else
            problems = problemsService.get(page);
        log.trace("getProblems: problems={}", problems);
        return new ArrayList<>(problemConverter.convertModelsToDTOs(problems.getContent()));
    }

    @RequestMapping(value = "/problem/{id}", method = RequestMethod.GET)
    ProblemDTO getProblemById(@PathVariable Integer id) throws ValidatorException {
        log.trace("Get problem by ID - method entered");
        return problemConverter.convertModelToDTO(problemsService.getById(id));
    }

    @RequestMapping(value = "/problems", method = RequestMethod.POST)
    ProblemDTO saveProblem(@Valid @RequestBody ProblemDTO problemDTO) throws ValidatorException {
        log.trace("Save a problem - method entered");
        return problemConverter.convertModelToDTO(problemsService.add(problemConverter.convertDTOtoModel(problemDTO)));
    }

    @RequestMapping(value = "/problems/{id}", method = RequestMethod.PUT)
    ProblemDTO updateProblem(@PathVariable Integer id, @Valid @RequestBody ProblemDTO problemDTO) throws ValidatorException {
        log.trace("Update a problem - method entered");
        return problemConverter.convertModelToDTO(problemsService.update(id,
                problemConverter.convertDTOtoModel(problemDTO).getDescription(),
                problemConverter.convertDTOtoModel(problemDTO).getDifficulty()));
    }

    @RequestMapping(value = "/problems/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteProblem(@PathVariable Integer id) {
        log.trace("Delete a problem - method entered");
        problemsService.remove(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/problems/sorted/{page}/{param}", method = RequestMethod.GET)
    List<ProblemDTO> getStudentsSorted(@PathVariable Integer page, @PathVariable String param) {
        Map<String, Boolean> criteria = new HashMap<>();
        this.problemsSorted = true;
        Arrays.stream(param.split("&")).forEach(
                cr -> criteria.put(cr.split("-")[0], cr.split("-")[1].equals("true"))
        );
        this.problemSortedCriteria = criteria;
        return new ArrayList<>(this.problemConverter.convertModelsToDTOs(problemsService.getSorted(page, criteria).getContent()));
    }

}
