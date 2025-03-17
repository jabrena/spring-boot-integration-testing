package info.jab.ms.service;

import info.jab.ms.repository.GreekGod;
import info.jab.ms.repository.GreekGodRepository;
import info.jab.ms.utils.PostgreTestContainers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

@PostgreTestContainers
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = {
        "address=http://localhost:8090/greek-gods",
        "scheduler.enabled=false"
    })
public class GreekGodsServiceIT {

    @Autowired
    private GreekGodsService greekGodsService;
    @Autowired
    private GreekGodRepository greekGodRepository;

    @Test
    public void should_return_data_from_database() {
        // Given
        GreekGod zeus = new GreekGod("Zeus");
        GreekGod poseidon = new GreekGod("Poseidon");
        greekGodRepository.saveAll(List.of(zeus, poseidon));

        // When
        List<String> names = greekGodsService.getGreekGods();

        // Then
        assertThat(names)
            .isNotEmpty()
            .hasSize(2)
            .containsExactlyInAnyOrder("Zeus", "Poseidon");
    }
} 