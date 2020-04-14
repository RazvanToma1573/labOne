package mpp.socket.client.config;

import mpp.socket.common.IServiceStudents;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;

@Configuration
public class ClientStudentsConfig {
    @Bean
    RmiProxyFactoryBean rmiProxyFactoryBeanStudent() {
        RmiProxyFactoryBean rmiProxyFactoryBean = new RmiProxyFactoryBean();
        rmiProxyFactoryBean.setServiceInterface(IServiceStudents.class);
        rmiProxyFactoryBean.setServiceUrl("rmi://localhost:1099/StudentsService");

        return rmiProxyFactoryBean;
    }
}