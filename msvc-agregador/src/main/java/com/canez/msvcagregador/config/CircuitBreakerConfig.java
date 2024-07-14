package com.canez.msvcagregador.config;

import io.github.resilience4j.common.circuitbreaker.configuration.CircuitBreakerConfigCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CircuitBreakerConfig {

    @Bean
    public CircuitBreakerConfigCustomizer reviewService() {
        return CircuitBreakerConfigCustomizer.of("breaker-generico-service", builder ->
                builder.minimumNumberOfCalls(4));
    }


}
