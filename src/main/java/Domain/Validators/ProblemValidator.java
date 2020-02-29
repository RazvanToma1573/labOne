package Domain.Validators;

import Domain.Problem;

public class ProblemValidator implements Validator<Problem> {

    @Override
    public void validate(Problem problem) throws ValidatorException {
        if (problem.getId() < 1) throw new ValidatorException("Invalid problem id!");
        if(!problem.getDescription().matches("[a-zA-Z]+")) throw new ValidatorException("Invalid description");
        if(!problem.getDifficulty().matches("[a-zA-Z]+")) throw new ValidatorException("Invalid difficulty");
    }
}
