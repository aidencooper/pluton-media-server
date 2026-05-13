package net.aidencooper.pluton.mediaserver;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

@SpringBootTest
class PlutonApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void verifiesModularStructure() {
		ApplicationModules.of(PlutonApplication.class).verify();
	}

	@Test
	void createModuleDocumentation() {
		new Documenter(ApplicationModules.of(PlutonApplication.class))
				.writeDocumentation()
				.writeIndividualModulesAsPlantUml();
	}
}
