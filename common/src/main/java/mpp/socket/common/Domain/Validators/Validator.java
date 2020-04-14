package mpp.socket.common.Domain.Validators;

public interface Validator<T> {
    void validate(T entity) throws ValidatorException;
}
