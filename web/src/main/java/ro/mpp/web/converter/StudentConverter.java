package ro.mpp.web.converter;

import org.springframework.stereotype.Component;
import ro.mpp.core.Domain.Student;
import ro.mpp.web.dto.StudentDTO;

@Component
public class StudentConverter extends BaseConverter<Student, StudentDTO> {
    @Override
    public Student convertDTOtoModel(StudentDTO dto) {
        Student student = new Student(dto.getFirstName(), dto.getLastName());
        student.setId(dto.getId());
        return student;
    }

    @Override
    public StudentDTO convertModelToDTO(Student student) {
        StudentDTO dto = new StudentDTO(student.getFirstName(), student.getLastName());
        dto.setId(student.getId());
        return dto;
    }
}
