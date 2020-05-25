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

    @Min(1)
    private Integer studentId;
    @Min(1)
    private Integer problemId;
    @Min(0)
    @Max(10)
    private Integer actualGrade;
}
