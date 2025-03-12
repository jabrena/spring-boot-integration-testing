package info.jab.ms.gods.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GreekGodsServiceTest {

    @Mock
    private GreekGodsService greekGodsService;

    @Test
    void should_return_data_when_get_greek_gods() throws IOException {
        // Given
        List<String> expectedData = Files.readAllLines(
            Paths.get(getClass().getResource("/__files/greek-gods-200.json").getPath()),
            StandardCharsets.UTF_8
        );
        when(greekGodsService.getGreekGods()).thenReturn(expectedData);

        // When
        List<String> result = greekGodsService.getGreekGods();

        // Then
        assertThat(result)
            .isNotNull()
            .hasSize(22);
    }
} 