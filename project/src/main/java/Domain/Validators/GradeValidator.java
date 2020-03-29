package Domain.Validators;

import Domain.Grade;
import Domain.Problem;
import Domain.Student;

public class GradeValidator implements Validator<Grade> {

    @Override
    public void validate(Grade entity) throws ValidatorException {
        if(entity.getActualGrade() < 0 || entity.getActualGrade() > 10) throw new ValidatorException("Invalid grade");
    }
}
