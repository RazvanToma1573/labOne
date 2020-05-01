package ro.mpp.spring.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"ro.mpp.spring.Repository", "ro.mpp.spring.Service", "ro.mpp.spring.UI", "ro.mpp.spring.Domain.Validators"})
public class CatalogConfig {
}
