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
public class Problem extends BaseEntity<Integer> {

    private String description;
    private String difficulty; //easy/medium/hard
}
