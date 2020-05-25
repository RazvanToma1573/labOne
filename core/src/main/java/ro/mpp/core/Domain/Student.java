package ro.mpp.core.Domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@NamedEntityGraphs({
        @NamedEntityGraph(name = "studentWithGrades",
            attributeNodes = @NamedAttributeNode(value = "grades")),
        @NamedEntityGraph(name = "studentWithGradesAndProblem",
            attributeNodes = @NamedAttributeNode(value = "grades", subgraph = "studentWithGrade"),
            subgraphs = @NamedSubgraph(name = "studentWithGrade",
                attributeNodes = @NamedAttributeNode(value = "problem")))
})
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
public class Student extends BaseEntity<Integer>{
    @NotNull
    @Size(min = 2)
    @Pattern(regexp = "([a-zA-Z]+)")
    private String firstName;

    @NotNull
    @Size(min = 2)
    @Pattern(regexp = "([a-zA-Z]+)")
    private String lastName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "student", fetch = FetchType.LAZY)
    private Set<Grade> grades = new HashSet<>();

}
