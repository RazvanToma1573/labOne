package ro.mpp.core.Domain;

import lombok.*;

import javax.persistence.Entity;
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
    private int actualGrade;
}
