package ro.mpp.web.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class GradeDTO extends BaseDTO {
    private Integer studentId;
    private Integer problemId;
    private Integer actualGrade;
}
