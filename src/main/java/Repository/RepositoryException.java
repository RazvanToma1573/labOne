package Repository;

public class RepositoryException extends Exception {
    public RepositoryException() {
        super();
    }

    public RepositoryException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
