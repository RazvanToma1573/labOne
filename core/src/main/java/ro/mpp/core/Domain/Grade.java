package ro.mpp.core.Domain;

import lombok.*;

import javax.persistence.Entity;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class Grade extends BaseEntity<Integer>{

    private int studentId;
    private int problemId;
    @Min(0)
    @Max(10)
    private int actualGrade;
}
