package mpp.socket.client.config;

import mpp.socket.common.IServiceProblems;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;

@Configuration
public class ClientProblemsConfig {
    @Bean
    RmiProxyFactoryBean rmiProxyFactoryBeanProblem() {
        RmiProxyFactoryBean rmiProxyFactoryBean = new RmiProxyFactoryBean();
        rmiProxyFactoryBean.setServiceInterface(IServiceProblems.class);
        rmiProxyFactoryBean.setServiceUrl("rmi://localhost:1099/ProblemsService");

        return rmiProxyFactoryBean;
    }
}
