package org.humanResources.config;

import org.humanResources.time.TestTimeProvider;
import org.humanResources.time.TimeProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.Instant;

@Configuration
@Profile({"unitTest","integrationTest"})
public class TestServicesConfig {

        @Bean(name = "timeProvider")
        public TimeProvider timeProvider() {
            TimeProvider timeProvider = new TestTimeProvider(Instant.now());
            return timeProvider;
        }
}