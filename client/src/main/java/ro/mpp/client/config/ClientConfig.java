package ro.mpp.client.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import ro.mpp.client.UI.Console;

@Configuration
public class ClientConfig {
    @Bean
    Console console() {
        return new Console(restTemplate());
    }

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
