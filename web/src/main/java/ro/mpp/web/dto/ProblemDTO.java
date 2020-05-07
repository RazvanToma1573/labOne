package ro.mpp.web.dto;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class ProblemDTO extends BaseDTO {
    private String description;
    private String difficulty;
}
