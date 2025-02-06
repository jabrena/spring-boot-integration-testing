package info.jab.ms.service;

import com.github.tomakehurst.wiremock.WireMockServer;
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

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = {
        "address=http://localhost:8090/greek-gods"
    })
public class MyServiceITTest {

    @Autowired
    private MyService myService;

    WireMockServer wireMockServer;

    @BeforeEach
    public void setup () {
        wireMockServer = new WireMockServer(8090);
        wireMockServer.start();
    }

    @AfterEach
    public void teardown () {
        wireMockServer.stop();
    }

    @Test
    public void should_wiremock_simulate_endpoint() {
        //Given
        wireMockServer.stubFor(get(urlEqualTo("/greek-gods"))
                .willReturn(aResponse().withHeader("Content-Type", "application/json")
                        .withStatus(200)
                        .withBodyFile("greek-gods-200.json")));

        //When
        List<String> result = myService.getData();

        //Then
        assertThat(result).isNotEmpty();
        assertThat(result.size()).isEqualTo(20); // ajusta según el contenido de books.json
    }
}
