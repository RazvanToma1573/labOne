package mpp.socket.client.config;

import mpp.socket.common.IServiceProblems;
import mpp.socket.common.IServiceStudents;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;

@Configuration
public class ClientConfig {
    @Bean
    RmiProxyFactoryBean rmiProxyFactoryBean1() {
        RmiProxyFactoryBean rmiProxyFactoryBean = new RmiProxyFactoryBean();
        rmiProxyFactoryBean.setServiceInterface(IServiceStudents.class);
        rmiProxyFactoryBean.setServiceUrl("rmi://localhost:1099/StudentsService");
        return rmiProxyFactoryBean;
    }

    @Bean
    RmiProxyFactoryBean rmiProxyFactoryBean2() {
        RmiProxyFactoryBean rmiProxyFactoryBean = new RmiProxyFactoryBean();
        rmiProxyFactoryBean.setServiceInterface(IServiceProblems.class);
        rmiProxyFactoryBean.setServiceUrl("rmi://localhost:1099/ProblemsService");
        return rmiProxyFactoryBean;
    }
}
