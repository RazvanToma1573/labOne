package mpp.socket.server;


import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ServerApp {
    public static void main(String[] args) {
        System.out.println("Server started...");

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("mpp.socket.server.config");


        /*try {
            AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("mpp.socket.server.config");
            SocketService socketService = (SocketService) context.getBean("serviceServer");
            TCPServer tcpServer = (TCPServer) context.getBean("tcpServer");

            tcpServer.addHandler("ClientRequest", (request) -> {
                String command = request.getHeader() + " " + request.getBody();
                Future<String> future = socketService.command(command);
                try{
                    String result = future.get();
                    return new Message("Done", result);
                } catch (Exception e) {
                    e.printStackTrace();
                    return new Message("Error",e.getMessage());
                }
            });
            tcpServer.start();
        } catch (Exception e){
            e.printStackTrace();
        }*/
    }
}
