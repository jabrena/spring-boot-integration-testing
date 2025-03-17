package info.jab.ms.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import info.jab.ms.utils.PostgreTestContainers;

import java.util.List;
import static org.assertj.core.api.BDDAssertions.then;
import org.springframework.jdbc.core.JdbcTemplate;

@PostgreTestContainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {
    "scheduler.enabled=false" 
})
class GreekGodsControllerE2EIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void setup() {
        // Clear any existing data
        jdbcTemplate.execute("DELETE FROM greek_gods");
        
        // Insert test data
        jdbcTemplate.execute("INSERT INTO greek_gods (name) VALUES ('Zeus')");
        jdbcTemplate.execute("INSERT INTO greek_gods (name) VALUES ('Poseidon')");
        jdbcTemplate.execute("INSERT INTO greek_gods (name) VALUES ('Hades')");
    }

    @AfterEach
    public void cleanup() {
        // Clean up after test
        jdbcTemplate.execute("DELETE FROM greek_gods");
    }

    @Test
    public void should_return_all_greek_gods() {
        //Given
        String address = "http://localhost:" + port + "/gods/greek";

        //When
        ResponseEntity<List<String>> result =
                restTemplate.exchange(
                        address,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>() {}
                );

        //Then
        then(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(result.getBody()).isNotNull();
        then(result.getBody()).contains("Zeus", "Poseidon", "Hades");
    }    
}