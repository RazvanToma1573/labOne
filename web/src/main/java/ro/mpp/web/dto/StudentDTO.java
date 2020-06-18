package ro.mpp.web.dto;


import lombok.*;
import ro.mpp.core.Domain.Grade;
import ro.mpp.core.Domain.Student;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class StudentDTO extends BaseDTO {
    private String firstName;
    private String lastName;
    private Set<GradeDTO> grades;

    @Override
    public String toString() {
        return "StudentDTO{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}' + super.toString();
    }

    public Student convert() {
        Student student =  Student.builder()
                .firstName(firstName)
                .lastName(lastName)
                .build();
        student.setId(getId());
        return student;
    }
}
