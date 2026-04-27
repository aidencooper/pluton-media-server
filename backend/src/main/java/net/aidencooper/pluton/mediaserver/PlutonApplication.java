package net.aidencooper.pluton.mediaserver;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PlutonApplication implements CommandLineRunner {
	static void main(String[] args) {
		SpringApplication.run(PlutonApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {

    }
}
