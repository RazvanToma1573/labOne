package mpp.socket.common;

public class SocketServiceException extends RuntimeException {

    public SocketServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public SocketServiceException(Throwable cause) {
        super(cause);
    }
}
