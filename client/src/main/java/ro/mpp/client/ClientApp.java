package ro.mpp.client;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.client.RestTemplate;
import ro.mpp.client.UI.Console;

public class ClientApp {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext("ro.mpp.client.config");

        Console console = context.getBean(Console.class);

        console.run();
    }
}
