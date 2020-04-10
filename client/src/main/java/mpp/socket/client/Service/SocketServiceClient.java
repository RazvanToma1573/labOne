package mpp.socket.client.Service;
import mpp.socket.client.Tcp.TcpClient;
import mpp.socket.common.Message;
import mpp.socket.common.SocketService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class SocketServiceClient implements SocketService {

    @Autowired
    private SocketService service;

    @Autowired
    private ExecutorService executorService;

    @Override
    public Future<String> command(String name) {
        return executorService.submit(() -> service.command(name).get());
    }
}
