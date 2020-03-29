package mpp.socket.client.Service;
import mpp.socket.client.Tcp.TcpClient;
import mpp.socket.common.Message;
import mpp.socket.common.SocketService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class SocketServiceClient implements SocketService {

    private ExecutorService executorService;
    private TcpClient tcpClient;

    public SocketServiceClient(ExecutorService executorService, TcpClient tcpClient) {
        this.executorService = executorService;
        this.tcpClient = tcpClient;
    }

    @Override
    public Future<String> command(String name) {
        return executorService.submit(() -> {

            String[] parts = name.split(" ");
            String command = parts[0];
            String parameters = "";
            if (!(command.equals("3") || command.equals("6") || command.equals("8")))
                 parameters = parts[1];
            Message request = new Message(command, parameters);
            System.out.println("sending request: " + request);
            Message response = tcpClient.sendAndReceive(request);
            System.out.println("received response: " + response);

            return response.getHeader() + " " + response.getBody();
        });
    }
}
