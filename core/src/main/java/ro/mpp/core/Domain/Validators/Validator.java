package ro.mpp.core.Domain.Validators;

public interface Validator<T> {
    void validate(T entity) throws ValidatorException;
}
