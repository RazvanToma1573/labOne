package ro.mpp.web.converter;

import org.springframework.stereotype.Component;
import ro.mpp.core.Domain.Grade;
import ro.mpp.core.Domain.Problem;
import ro.mpp.web.dto.GradeDTO;
import ro.mpp.web.dto.ProblemDTO;
import ro.mpp.web.dto.StudentDTO;

@Component
public class GradeConverter extends BaseConverter<Grade, GradeDTO> {
    @Override
    public Grade convertDTOtoModel(GradeDTO dto) {
        Grade grade = Grade.builder()
                .student(dto.getStudent().convert())
                .problem(dto.getProblem().convert())
                .actualGrade(dto.getActualGrade())
                .build();
        grade.setId(dto.getId());
        return grade;
    }

    @Override
    public GradeDTO convertModelToDTO(Grade grade) {
        StudentDTO student = StudentDTO.builder()
                .firstName(grade.getStudent().getFirstName())
                .lastName(grade.getStudent().getLastName())
                .build();
        student.setId(grade.getStudent().getId());
        ProblemDTO problem = ProblemDTO.builder()
                .description(grade.getProblem().getDescription())
                .difficulty(grade.getProblem().getDifficulty())
                .build();
        problem.setId(grade.getProblem().getId());
        GradeDTO dto = GradeDTO.builder()
                .student(student)
                .problem(problem)
                .actualGrade(grade.getActualGrade())
                .build();
        dto.setId(grade.getId());
        return dto;
    }
}
