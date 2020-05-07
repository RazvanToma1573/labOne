package ro.mpp.web.converter;

import org.springframework.stereotype.Component;
import ro.mpp.core.Domain.Grade;
import ro.mpp.web.dto.GradeDTO;

@Component
public class GradeConverter extends BaseConverter<Grade, GradeDTO> {
    @Override
    public Grade convertDTOtoModel(GradeDTO dto) {
        Grade grade = Grade.builder()
                .studentId(dto.getStudentId())
                .problemId(dto.getStudentId())
                .actualGrade(dto.getActualGrade())
                .build();
        grade.setId(dto.getId());
        return grade;
    }

    @Override
    public GradeDTO convertModelToDTO(Grade grade) {
        GradeDTO dto = GradeDTO.builder()
                .studentId(grade.getStudentId())
                .problemId(grade.getProblemId())
                .actualGrade(grade.getActualGrade())
                .build();
        dto.setId(grade.getId());
        return dto;
    }
}
