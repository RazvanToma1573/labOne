package ro.mpp.core.Domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
public class Pod extends BaseEntity<Long> {
    @Column(unique = true)
    private String name;
    private int cost;

    @Override
    public String toString() {
        return "Pod{" +
                "name='" + name + '\'' +
                ", cost=" + cost +
                '}';
    }
}
