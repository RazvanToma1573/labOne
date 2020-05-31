package ro.mpp.web.converter;

import org.springframework.stereotype.Component;
import ro.mpp.core.Domain.Grade;
import ro.mpp.core.Domain.Student;
import ro.mpp.web.dto.StudentDTO;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class StudentConverter extends BaseConverter<Student, StudentDTO> {
    @Override
    public Student convertDTOtoModel(StudentDTO dto) {
        Student student = Student.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .build();
        student.setId(dto.getId());
        return student;
    }

    @Override
    public StudentDTO convertModelToDTO(Student student) {
        StudentDTO dto = StudentDTO.builder()
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .build();
        dto.setId(student.getId());
        return dto;
    }
}
