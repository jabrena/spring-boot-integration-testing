package info.jab.ms.gods;

import info.jab.ms.gods.config.PostgreTestContainers;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@PostgreTestContainers
@SpringBootTest
class GodsModuleTest {

    @Test
    void contextLoads() {
        // This test will verify that the gods module can be loaded correctly
    }
} 