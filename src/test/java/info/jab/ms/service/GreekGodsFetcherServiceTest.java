package info.jab.ms.service;

import info.jab.ms.repository.GreekGod;
import info.jab.ms.repository.GreekGodRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import org.mockito.ArgumentMatchers;

@ExtendWith(MockitoExtension.class)
class GreekGodsFetcherServiceTest {

    private static final String TEST_ADDRESS = "http://test-address.com/api";

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private GreekGodRepository greekGodRepository;


    private GreekGodsFetcherService greekGodsFetcherService;
    
    @BeforeEach
    void setUp() {
        greekGodsFetcherService = new GreekGodsFetcherService(TEST_ADDRESS, restTemplate, greekGodRepository);
    }

    @Test
    void should_fetch_and_store_greek_gods() {
        // Given
        List<String> expectedData = List.of("Zeus", "Poseidon", "Hades");
        ResponseEntity<List<String>> responseEntity = new ResponseEntity<>(expectedData, HttpStatus.OK);
        
        doReturn(responseEntity).when(restTemplate).exchange(
                eq(TEST_ADDRESS),
                eq(HttpMethod.GET),
                eq(null),
                ArgumentMatchers.<ParameterizedTypeReference<List<String>>>any()
        );

        // When
        greekGodsFetcherService.fetchAndStoreGreekGods();

        // Then
        verify(greekGodRepository).deleteAll();
        verify(greekGodRepository).saveAll(argThat(gods -> {
            List<GreekGod> godList = (List<GreekGod>) gods;
            return godList.size() == 3 &&
                   godList.stream().map(GreekGod::getName)
                         .toList()
                         .containsAll(expectedData);
        }));
    }
    
    @Test
    void should_handle_rest_client_exception_gracefully() {
        // Given
        when(restTemplate.exchange(
                eq(TEST_ADDRESS),
                eq(HttpMethod.GET),
                eq(null),
                ArgumentMatchers.<ParameterizedTypeReference<List<String>>>any()
        )).thenThrow(new RestClientException("API not available"));

        // When
        greekGodsFetcherService.fetchAndStoreGreekGods();

        // Then
        verify(greekGodRepository, never()).deleteAll();
        verify(greekGodRepository, never()).saveAll(any());
    }

    @Test
    void should_handle_null_response() {
        // Given
        ResponseEntity<List<String>> responseEntity = new ResponseEntity<>(null, HttpStatus.OK);
        
        doReturn(responseEntity).when(restTemplate).exchange(
                eq(TEST_ADDRESS),
                eq(HttpMethod.GET),
                eq(null),
                any(ParameterizedTypeReference.class)
        );

        // When
        greekGodsFetcherService.fetchAndStoreGreekGods();


        // Then - verify the error is handled and no database operations occur
        verify(greekGodRepository, never()).deleteAll();
        verify(greekGodRepository, never()).saveAll(anyList());
    }

    @Test
    void should_handle_null_response_from_service() {
        // Given
        ResponseEntity<List<String>> responseEntity = new ResponseEntity<>(null, HttpStatus.OK);
        
        doReturn(responseEntity).when(restTemplate).exchange(
                eq(TEST_ADDRESS),
                eq(HttpMethod.GET),
                eq(null),
                ArgumentMatchers.<ParameterizedTypeReference<List<String>>>any());

        // When
        greekGodsFetcherService.fetchAndStoreGreekGods();


        // Then
        verify(greekGodRepository, never()).deleteAll();
        verify(greekGodRepository, never()).saveAll(any());
    }

    @Test
    void should_handle_exception_gracefully() {
        // Given
        doThrow(new RuntimeException("API Error")).when(restTemplate).exchange(
                eq(TEST_ADDRESS),
                eq(HttpMethod.GET),
                eq(null),
                any(ParameterizedTypeReference.class)

        );

        // When
        greekGodsFetcherService.fetchAndStoreGreekGods();

        // Then - verify the null response is handled and no database operations occur
        verify(greekGodRepository, never()).deleteAll();
        verify(greekGodRepository, never()).saveAll(anyList());

    }
} 