package ro.mpp.core.Domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(exclude = {"student", "problem"}, callSuper = true)
@ToString(callSuper = true)
@Builder
public class Grade extends BaseEntity<Integer>{

    @Min(0)
    @Max(10)
    private int actualGrade;

    @ManyToOne(fetch = FetchType.LAZY)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    private Problem problem;
}
