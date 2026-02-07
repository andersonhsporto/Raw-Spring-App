package dev.spring.config;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

public class EnvInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        ConfigurableEnvironment env = applicationContext.getEnvironment();

        try {
            ClassPathResource resource = new ClassPathResource("application.properties");
            Properties props = PropertiesLoaderUtils.loadProperties(resource);

            String profile = props.getProperty("spring.profiles.active");

            if (profile == null || profile.trim().isEmpty()) {
                profile = System.getenv("SPRING_PROFILES_ACTIVE");
            }

            if (profile == null || profile.trim().isEmpty()) {
                profile = System.getProperty("spring.profiles.active");
            }

            if (profile == null || profile.trim().isEmpty()) {
                profile = "dev";
            }

            env.setActiveProfiles(profile);

        } catch (IOException e) {
            String profile = System.getenv("SPRING_PROFILES_ACTIVE");
            if (profile == null) {
                profile = System.getProperty("spring.profiles.active", "dev");
            }
            env.setActiveProfiles(profile);
        }
    }
}
