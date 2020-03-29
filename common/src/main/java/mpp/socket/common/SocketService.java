package mpp.socket.common;

import java.util.concurrent.Future;

public interface SocketService {

    Future<String> command (String name);
}
