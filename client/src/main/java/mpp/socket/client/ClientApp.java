package mpp.socket.client;

import mpp.socket.client.Service.SocketServiceClient;
import mpp.socket.client.Tcp.TcpClient;
import mpp.socket.client.Ui.Console;
import mpp.socket.common.SocketService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientApp {

    public static void main(String[] args) {
        //ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        //TcpClient tcpClient = new TcpClient();
        //SocketService socketService = new SocketServiceClient(executorService, tcpClient);
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext("mpp.socket.client.config");
        Console console = new Console(context);
        console.runConsole();

        //executorService.shutdown();

        System.out.println("bye client");
    }
}
