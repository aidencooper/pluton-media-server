package net.aidencooper.pluton.mediaserver;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.modulith.core.ApplicationModules;

@SpringBootTest
class PlutonApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void verifiesModularStructure() {
		ApplicationModules.of(PlutonApplication.class).verify();
	}
}
