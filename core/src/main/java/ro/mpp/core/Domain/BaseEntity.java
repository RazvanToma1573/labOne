package ro.mpp.core.Domain;

import javax.persistence.*;
import java.io.Serializable;

import lombok.*;

@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
public class BaseEntity<ID extends Serializable> implements Serializable {
    @Id
    @TableGenerator(name = "TABLE_GENERATOR", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GENERATOR")
    @Column(unique = true, nullable = false)
    private ID id;
}
