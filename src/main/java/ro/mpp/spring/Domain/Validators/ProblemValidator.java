package ro.mpp.spring.Domain.Validators;

import ro.mpp.spring.Domain.Problem;
import org.springframework.stereotype.Component;

@Component
public class ProblemValidator implements Validator<Problem> {

    /**
     * Checks:
     * -id greater than 0
     * -description 1 or more letters
     * -difficulty 1 or more letters
     * @param problem Problem to be checked.
     * @throws ValidatorException custom exception.
     */
    @Override
    public void validate(Problem problem) throws ValidatorException {
        if(!problem.getDescription().matches("[a-zA-Z]+")) throw new ValidatorException("Invalid description");
        if(!problem.getDifficulty().matches("[a-zA-Z]+")) throw new ValidatorException("Invalid difficulty");
    }
}
