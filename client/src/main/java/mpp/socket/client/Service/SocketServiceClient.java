package mpp.socket.client.Service;
import mpp.socket.client.Tcp.TcpClient;
import mpp.socket.common.Message;
import mpp.socket.common.SocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@Component
public class SocketServiceClient implements SocketService {

    @Autowired
    private SocketService service;

    @Override
    public String command(String name) {
        return service.command(name);
    }
}
