package info.jab.ms;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {"scheduler.enabled=false"})
class MainApplicationTests {

  @Test
  void contextLoads() {
  }
}