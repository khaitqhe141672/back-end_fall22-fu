package fpt.edu.backendfall22fu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class BackendFall22FuApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendFall22FuApplication.class, args);
    }

}
