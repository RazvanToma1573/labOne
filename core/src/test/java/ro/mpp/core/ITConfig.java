package ro.mpp.core;

import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import ro.mpp.core.config.JPAConfig;

@Configuration
@ComponentScan(value = "ro.mpp.core", excludeFilters = {@ComponentScan.Filter(value = {JPAConfig.class}, type = FilterType.ASSIGNABLE_TYPE)})
@Import({JPAConfigIT.class})
@PropertySources({@PropertySource(value = "classpath:db-h2.properties")})
public class ITConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();    }
}
