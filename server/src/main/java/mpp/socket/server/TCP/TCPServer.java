package mpp.socket.server.TCP;

import mpp.socket.common.Message;
import mpp.socket.common.SocketServiceException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.function.UnaryOperator;

public class TCPServer {
    private ExecutorService executorService;
    private Map<String, UnaryOperator<Message>> methodHandlers;

    private class ClientHandler implements Runnable{
        private Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                InputStream is = socket.getInputStream();
                OutputStream os = socket.getOutputStream();
                Message request = new Message();
                request.readFrom(is);
                System.out.println("Received request: " + request);
                Message response = methodHandlers.get(request.getHeader())
                        .apply(request);
                response.writeTo(os);
            } catch (IOException e) {
                throw new SocketServiceException("Error processing client", e);
            }
        }
    }

    public TCPServer(ExecutorService executorService) {
        this.executorService = executorService;
        this.methodHandlers = new HashMap<>();
    }

    public void addHandler(String method, UnaryOperator<Message> handler) {
        this.methodHandlers.put(method, handler);
    }

    public void start() {
        try {
            ServerSocket serverSocket = new ServerSocket(Message.PORT);
            while(true) {
                Socket client = serverSocket.accept();
                executorService.submit(new ClientHandler(client));
            }
        } catch (IOException e){
            throw new SocketServiceException("Error connecting clients", e);
        }
    }
}
