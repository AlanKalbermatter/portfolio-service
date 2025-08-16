package com.akalber.alankalbermatter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.actuate.autoconfigure.metrics.SystemMetricsAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.metrics.MetricsAutoConfiguration;

@SpringBootApplication(exclude = {
    SystemMetricsAutoConfiguration.class,
    MetricsAutoConfiguration.class
})
public class AlanKalbermatterApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlanKalbermatterApplication.class, args);
    }

}
