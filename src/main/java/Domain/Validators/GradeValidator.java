package Domain.Validators;

import Domain.Grade;
import Domain.Problem;
import Domain.Student;

public class GradeValidator implements Validator<Grade> {

    private Validator<Student> studentValidator;
    private Validator<Problem> problemValidator;

    public GradeValidator(Validator<Student> studentValidator, Validator<Problem> problemValidator) {
        this.studentValidator = studentValidator;
        this.problemValidator = problemValidator;
    }

    @Override
    public void validate(Grade entity) throws ValidatorException {
        if(entity.getId()<1) throw new ValidatorException("Invalid student ID");
        this.studentValidator.validate(entity.getStudent());
        this.problemValidator.validate(entity.getProblem());
    }
}
