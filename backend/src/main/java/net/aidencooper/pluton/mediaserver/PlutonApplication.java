package net.aidencooper.pluton.mediaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PlutonApplication {
	static void main(String[] args) {
        createDirectories();

		SpringApplication.run(PlutonApplication.class, args);
	}

    private static void createDirectories() {

    }
}
