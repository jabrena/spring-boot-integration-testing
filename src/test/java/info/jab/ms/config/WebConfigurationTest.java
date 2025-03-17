package info.jab.ms.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WebConfigurationTest {

    @Mock
    private RestTemplateBuilder restTemplateBuilder;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private WebConfiguration webConfiguration;

    @Test
    void should_create_rest_template() {
        // Given
        when(restTemplateBuilder.build()).thenReturn(restTemplate);

        // When
        RestTemplate result = webConfiguration.restTemplate();

        // Then
        assertThat(result).isEqualTo(restTemplate);
    }
} 