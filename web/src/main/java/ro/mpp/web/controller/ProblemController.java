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
import ro.mpp.web.converter.ProblemConverter;
import ro.mpp.web.dto.ProblemDTO;
import ro.mpp.web.dto.StudentDTO;

import javax.validation.Valid;
import java.util.*;

@RestController
public class ProblemController {
    public static final Logger log = LoggerFactory.getLogger(ProblemController.class);

    @Autowired
    private IProblemService problemsService;

    @Autowired
    private ProblemConverter problemConverter;

    @RequestMapping(value = "/problems", method = RequestMethod.GET)
    List<ProblemDTO> getProblems() {
        log.trace("getProblems");
        List<Problem> problems = problemsService.findAll();
        log.trace("getProblems: problems={}", problems);
        return new ArrayList<>(problemConverter.convertModelsToDTOs(problems));
    }

    @RequestMapping(value = "/problem/{id}", method = RequestMethod.GET)
    ProblemDTO getProblemById(@PathVariable Integer id) throws ValidatorException {
        log.trace("Get problem by ID - method entered");
        return problemConverter.convertModelToDTO(problemsService.getById(id));
    }

    @RequestMapping(value = "/problems", method = RequestMethod.POST)
    ProblemDTO saveProblem(@Valid @RequestBody ProblemDTO problemDTO) throws ValidatorException {
        log.trace("Save a problem - method entered");
        Problem problem = problemsService.add(
                problemDTO.getId(),
                problemDTO.getDescription(),
                problemDTO.getDifficulty()
        );
        ProblemDTO problemDTO1 = problemConverter.convertModelToDTO(problem);

        return problemDTO1;
    }

    @RequestMapping(value = "/problems/{id}", method = RequestMethod.PUT)
    ProblemDTO updateProblem(@PathVariable Integer id, @Valid @RequestBody ProblemDTO problemDTO) throws ValidatorException {
        log.trace("Update a problem - method entered");
        Problem problem = problemsService.update(id,
                problemDTO.getDescription(),
                problemDTO.getDifficulty());
        log.trace("Update a problem - method finished");
        ProblemDTO problemDTO1 = problemConverter.convertModelToDTO(problem);
        return problemDTO1;
    }

    @RequestMapping(value = "/problems/{id}", method = RequestMethod.DELETE)
    ProblemDTO deleteProblem(@PathVariable Integer id) {
        log.trace("Delete a problem - method entered");
        Problem problem = problemsService.remove(id);
        log.trace("Delete a problem - method finished");
        ProblemDTO problemDTO = problemConverter.convertModelToDTO(problem);
        return problemDTO;
    }

    @RequestMapping(value = "/problems/filter/{description}", method = RequestMethod.GET)
    List<ProblemDTO> getAllProblemsByDescription(@PathVariable String description) {
        log.trace("Filter by description - entered");
        List<Problem> problems = problemsService.findAllByDescription(description);
        log.trace("Filter by description - finished");
        return new ArrayList<>(problemConverter.convertModelsToDTOs(problems));
    }

}
