package ro.mpp.core.config;

import com.google.common.cache.CacheBuilder;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.postgresql.ds.PGPoolingDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableJpaRepositories({"ro.mpp.core.Repository"})
@EnableTransactionManagement
@EnableCaching
@ComponentScan("ro.mpp.core.Service")
public class JPAConfig {
    @Bean
    public DataSource dataSource() {
        PGPoolingDataSource dataSource = new PGPoolingDataSource();
        //            dataSource.setUrl(jdbcURL);
        dataSource.setServerName("localhost");
        dataSource.setDatabaseName("postgres");
        dataSource.setUser("postgres");
        dataSource.setPassword("parola");
        dataSource.setMaxConnections(4);

        return dataSource;
    }

    @Bean
    public EntityManagerFactory entityManagerFactory(){
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabase(Database.POSTGRESQL);
        vendorAdapter.setGenerateDdl(true);
        vendorAdapter.setShowSql(true);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("ro.mpp.core.Domain");
        factory.setDataSource(dataSource());
        factory.afterPropertiesSet();
        return factory.getObject();
    }

    @Bean
    public EntityManager entityManager(){
        return entityManagerFactory().createEntityManager();
    }

    @Bean
    PlatformTransactionManager transactionManager(){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory());
        return transactionManager;
    }

    @Bean
    public HibernateExceptionTranslator hibernateExceptionTranslator(){
        return new HibernateExceptionTranslator();
    }

    @Bean
    public CacheManager cacheManager() {
        GuavaCacheManager guavaCacheManager = new GuavaCacheManager();
        guavaCacheManager.setCacheBuilder(CacheBuilder.newBuilder().expireAfterAccess(2, TimeUnit.HOURS));
        return guavaCacheManager;
    }
}
