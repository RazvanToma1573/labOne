package ro.mpp.core.Domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@NamedEntityGraphs({
        @NamedEntityGraph(name = "problemWithGrades",
                attributeNodes = @NamedAttributeNode(value = "grades")),
        @NamedEntityGraph(name = "problemWithGradesAndStudent",
                attributeNodes = @NamedAttributeNode(value = "grades", subgraph = "problemWithGrade"),
                subgraphs = @NamedSubgraph(name = "problemWithGrade",
                        attributeNodes = @NamedAttributeNode(value = "student")))
})
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
public class Problem extends BaseEntity<Integer> {

    @NotNull
    @Size(min = 2)
    @Pattern(regexp = "([a-zA-Z]+)")
    private String description;

    @NotNull
    @Size(min = 2)
    @Pattern(regexp = "([a-zA-Z]+)")
    private String difficulty; //easy/medium/hard

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "problem", fetch = FetchType.LAZY)
    private Set<Grade> grades = new HashSet<>();

    @Override
    public String toString() {
        return "Problem(" + getId() + "," + description + "," + difficulty + ")";
    }
}
