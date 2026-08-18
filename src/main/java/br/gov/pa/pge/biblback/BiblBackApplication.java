package br.gov.pa.pge.biblback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class BiblBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(BiblBackApplication.class, args);
    }

}
