package mpp.socket.common.Domain.Validators;


import mpp.socket.common.Domain.Grade;
import org.springframework.stereotype.Component;

@Component
public class GradeValidator implements Validator<Grade> {

    @Override
    public void validate(Grade entity) throws ValidatorException {
        if(entity.getActualGrade() < 0 || entity.getActualGrade() > 10) throw new ValidatorException("Invalid grade");
    }
}
