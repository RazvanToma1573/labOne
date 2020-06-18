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
public class Node extends BaseEntity<Long> {
    @Column(unique = true)
    private String name;
    private int totalCapacity;

    @Override
    public String toString() {
        return "Node{" +
                "name='" + name + '\'' +
                ", totalCapacity=" + totalCapacity +
                '}';
    }
}
