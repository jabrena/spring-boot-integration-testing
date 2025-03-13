package info.jab.ms.gods.service;

import com.github.tomakehurst.wiremock.WireMockServer;

import info.jab.ms.gods.config.PostgreTestContainers;
import info.jab.ms.gods.repository.GreekGod;
import info.jab.ms.gods.repository.GreekGodRepository;
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
        "address=http://localhost:8090/greek-gods"
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
    public void should_wiremock_simulate_endpoint_and_store_in_database() {
        //Given
        wireMockServer.stubFor(get(urlEqualTo("/greek-gods"))
                .willReturn(aResponse().withHeader("Content-Type", "application/json")
                        .withStatus(200)
                        .withBodyFile("greek-gods-200.json")));

        //When
        List<String> result = greekGodsService.getGreekGods();

        //Then
        // Verify the result from the API call
        assertThat(result).isNotEmpty();
        assertThat(result.size()).isEqualTo(20); // adjust according to the content of greek-gods-200.json
        
        // Verify the gods were saved to the database
        List<GreekGod> storedGods = greekGodsService.getAllGodsFromDatabase();
        assertThat(storedGods).isNotEmpty();
        assertThat(storedGods.size()).isEqualTo(20);
        
        // Verify the names in the database match those from the API
        List<String> storedGodNames = storedGods.stream()
                .map(GreekGod::getName)
                .toList();
        assertThat(storedGodNames).containsExactlyInAnyOrderElementsOf(result);
    }
} 