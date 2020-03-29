package mpp.socket.client.Tcp;

import mpp.socket.common.Message;
import mpp.socket.common.SocketServiceException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class TcpClient {

    public Message sendAndReceive(Message request) {
        try {
            Socket socket = new Socket(Message.HOST, Message.PORT);
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();

            System.out.println("SendAndReceive - sending request: " + request);
            request.writeTo(os);

            System.out.println("SendAndReceive - received response: ");
            Message response = new Message();
            response.readFrom(is);

            System.out.println(response);
            return response;
        } catch (IOException e) {
            throw new SocketServiceException("Error connection to server: " + e.getMessage(), e);
        }
    }
}
