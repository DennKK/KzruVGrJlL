package dkcorp.caselab_file_service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CaseLabTestTaskApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void mainTest() {
		CaseLabFileService.main(new String[]{});
	}
}
