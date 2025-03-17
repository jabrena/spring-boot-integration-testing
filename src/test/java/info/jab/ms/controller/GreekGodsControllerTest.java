package info.jab.ms.controller;

import info.jab.ms.service.GreekGodsService;
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
    void should_return_greek_god_names() {
        // Given
        List<String> expectedGodNames = List.of("Zeus", "Poseidon", "Hades");
        when(greekGodsService.getGreekGods()).thenReturn(expectedGodNames);

        // When
        List<String> result = greekGodsController.getGreekGodNames();

        // Then
        assertThat(result)
                .isNotNull()
                .hasSize(3)
                .containsExactly("Zeus", "Poseidon", "Hades");
        
        verify(greekGodsService).getGreekGods();
    }
} 