package ro.mpp.web.converter;

import org.springframework.stereotype.Component;
import ro.mpp.core.Domain.Grade;
import ro.mpp.core.Domain.Problem;
import ro.mpp.web.dto.ProblemDTO;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ProblemConverter extends BaseConverter<Problem, ProblemDTO> {
    @Override
    public Problem convertDTOtoModel(ProblemDTO dto) {
        Problem problem = Problem.builder()
                .description(dto.getDescription())
                .difficulty(dto.getDifficulty())
                .build();
        problem.setId(dto.getId());
        return problem;
    }

    @Override
    public ProblemDTO convertModelToDTO(Problem problem) {
        ProblemDTO dto = ProblemDTO.builder()
                .description(problem.getDescription())
                .difficulty(problem.getDifficulty())
                .build();
        dto.setId(problem.getId());
        return dto;
    }
}
