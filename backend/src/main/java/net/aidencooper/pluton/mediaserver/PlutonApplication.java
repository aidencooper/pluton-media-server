package net.aidencooper.pluton.mediaserver;

import net.aidencooper.pluton.mediaserver.media.scanner.port.IMediaScannerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PlutonApplication implements CommandLineRunner {
	static void main(String[] args) {
		SpringApplication.run(PlutonApplication.class, args);
	}

    private final IMediaScannerService mediaScannerService;

    public PlutonApplication(IMediaScannerService mediaScannerService) {
        this.mediaScannerService = mediaScannerService;
    }

    @Override
    public void run(String... args) throws Exception {
        this.mediaScannerService.scan();
    }
}
