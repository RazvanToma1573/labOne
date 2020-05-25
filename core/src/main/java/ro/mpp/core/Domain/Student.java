package ro.mpp.core.Domain;

import lombok.*;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Student extends BaseEntity<Integer>{
    @NotNull
    @Size(min = 2)
    @Pattern(regexp = "([a-zA-Z]+)")
    private String firstName;
    @NotNull
    @Size(min = 2)
    @Pattern(regexp = "([a-zA-Z]+)")
    private String lastName;

    @Override
    public String toString() {
        return "Student{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}' + super.toString();
    }
}
