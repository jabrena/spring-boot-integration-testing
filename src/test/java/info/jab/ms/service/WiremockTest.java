package info.jab.ms.service;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static io.restassured.RestAssured.given;


public class WiremockTest {

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
        wireMockServer.stubFor(get(urlEqualTo("/an/endpoint"))
                .willReturn(aResponse().withHeader("Content-Type", "text/plain")
                        .withStatus(200)
                        .withBodyFile("books.json")));

        //When
        //Then
        given()
                .when()
                .get("http://localhost:8090/an/endpoint")
                .then()
                .assertThat().statusCode(200);
    }
}
