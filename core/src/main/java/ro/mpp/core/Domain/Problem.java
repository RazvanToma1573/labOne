package ro.mpp.core.Domain;

import lombok.*;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
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
}
