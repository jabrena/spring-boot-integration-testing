package info.jab.ms.service;

import com.github.tomakehurst.wiremock.WireMockServer;

import info.jab.ms.config.PostgreTestContainers;
import info.jab.ms.repository.GreekGod;
import info.jab.ms.repository.GreekGodRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
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

    WireMockServer wireMockServer;

    @BeforeEach
    public void setup () {
        wireMockServer = new WireMockServer(8090);
        wireMockServer.start();
        
        // Clean up the database before each test
        greekGodRepository.deleteAll();
    }

    @AfterEach
    public void teardown () {
        wireMockServer.stop();
    }

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