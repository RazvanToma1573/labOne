package ro.mpp.web.dto;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class StudentDTO extends BaseDTO {
    private String firstName;
    private String lastName;
}
