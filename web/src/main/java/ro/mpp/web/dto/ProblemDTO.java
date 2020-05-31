package ro.mpp.web.dto;


import lombok.*;
import ro.mpp.core.Domain.Problem;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class ProblemDTO extends BaseDTO {
    private String description;
    private String difficulty;
    private Set<GradeDTO> grades;

    public Problem convert() {
        Problem problem = Problem.builder()
                .description(description)
                .difficulty(difficulty)
                .build();
        problem.setId(getId());
        return problem;
    }
}
