package com.fiap.dbe;

import com.fiap.dbe.model.Setup;
import com.fiap.dbe.repository.SetupRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.stream.LongStream;

@SpringBootApplication
public class CheckpointApplication {

    public static void main(String[] args) {
        SpringApplication.run(CheckpointApplication.class, args);
    }

    @Bean
    CommandLineRunner init(SetupRepository repository) {
        return args -> {
            repository.deleteAll();
            LongStream.range(1, 11)
                    .mapToObj(i -> {
                        Setup s = new Setup();
                        s.setName("Contact " + i);
                        s.setEmail("contact" + i + "@email.com");
                        s.setPhone("(111) 111-1111");
                        return s;
                    })
                    .map(v -> repository.save(v))
                    .forEach(System.out::println);
        };
    }
}
