package info.jab.ms.gods.controller;

import info.jab.ms.gods.service.GreekGodsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GreekGodsControllerTest {

    @Mock
    private GreekGodsService greekGodsService;

    @InjectMocks
    private GreekGodsController greekGodsController;

    @Test
    void should_return_greek_gods() {
        // Given
        List<String> expectedGods = List.of("Zeus", "Poseidon", "Hades");
        when(greekGodsService.getGreekGods()).thenReturn(expectedGods);

        // When
        List<String> result = greekGodsController.getGreekGods();

        // Then
        assertThat(result)
                .isNotNull()
                .hasSize(3)
                .containsExactly("Zeus", "Poseidon", "Hades");
        
        verify(greekGodsService).getGreekGods();
    }
} 