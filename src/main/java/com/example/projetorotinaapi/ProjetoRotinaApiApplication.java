package com.example.projetorotinaapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ProjetoRotinaApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjetoRotinaApiApplication.class, args);
    }

}
