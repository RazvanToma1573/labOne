package mpp.socket.server.Domain.Validators;

public interface Validator<T> {
    void validate(T entity) throws ValidatorException;
}
