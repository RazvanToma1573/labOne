package mpp.socket.client.config;

import mpp.socket.common.SocketService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;

@Configuration
public class ClientAutowireConfig {
    @Bean
    RmiProxyFactoryBean rmiProxyFactoryBean() {
        RmiProxyFactoryBean rmiProxyFactoryBean = new RmiProxyFactoryBean();
        rmiProxyFactoryBean.setServiceInterface(SocketService.class);
        rmiProxyFactoryBean.setServiceUrl("rmi://localhost:1099/StudentsService");

        return rmiProxyFactoryBean;
    }
}
