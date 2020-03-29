package Domain.Validators;

public class ValidatorException extends Exception {
    public ValidatorException() {
        super();
    }

    public ValidatorException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
