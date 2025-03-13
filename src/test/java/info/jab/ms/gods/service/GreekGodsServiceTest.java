package info.jab.ms.gods.service;

import info.jab.ms.gods.repository.GreekGod;
import info.jab.ms.gods.repository.GreekGodRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GreekGodsServiceTest {

    private static final String TEST_ADDRESS = "http://test-address.com/api";

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private GreekGodRepository greekGodRepository;

    private GreekGodsServiceImpl greekGodsService;

    @BeforeEach
    void setUp() {
        greekGodsService = new GreekGodsServiceImpl(restTemplate, greekGodRepository);
        ReflectionTestUtils.setField(greekGodsService, "address", TEST_ADDRESS);
    }

    @Test
    void should_return_data_when_get_greek_gods() {
        // Given
        List<String> expectedData = List.of("Zeus", "Poseidon", "Hades");
        ResponseEntity<List<String>> responseEntity = new ResponseEntity<>(expectedData, HttpStatus.OK);
        
        doReturn(responseEntity).when(restTemplate).exchange(
                eq(TEST_ADDRESS),
                eq(HttpMethod.GET),
                eq(null),
                any(ParameterizedTypeReference.class)
        );

        // When
        List<String> result = greekGodsService.getGreekGods();

        // Then
        assertThat(result)
            .isNotNull()
            .hasSize(3)
            .containsExactly("Zeus", "Poseidon", "Hades");
        
        verify(greekGodRepository).deleteAll();
        verify(greekGodRepository).saveAll(any());
    }
    
    @Test
    void should_handle_null_response_when_get_greek_gods() {
        // Given
        ResponseEntity<List<String>> responseEntity = new ResponseEntity<>(null, HttpStatus.OK);
        
        doReturn(responseEntity).when(restTemplate).exchange(
                eq(TEST_ADDRESS),
                eq(HttpMethod.GET),
                eq(null),
                any(ParameterizedTypeReference.class)
        );

        // When
        List<String> result = greekGodsService.getGreekGods();

        // Then
        assertThat(result).isNull();
        
        // Verify repository methods were not called
        verify(greekGodRepository, never()).deleteAll();
        verify(greekGodRepository, never()).saveAll(any());
    }
    
    @Test
    void should_return_all_gods_from_database() {
        // Given
        List<GreekGod> expectedGods = List.of(
            new GreekGod("Zeus"),
            new GreekGod("Poseidon"),
            new GreekGod("Hades")
        );
        when(greekGodRepository.findAll()).thenReturn(expectedGods);

        // When
        List<GreekGod> result = greekGodsService.getAllGodsFromDatabase();

        // Then
        assertThat(result)
            .isNotNull()
            .hasSize(3);
        
        verify(greekGodRepository).findAll();
    }
} 