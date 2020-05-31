package ro.mpp.web.dto;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class GradeDTO extends BaseDTO {

    private StudentDTO student;
    private ProblemDTO problem;

    @Min(0)
    @Max(10)
    private Integer actualGrade;
}
