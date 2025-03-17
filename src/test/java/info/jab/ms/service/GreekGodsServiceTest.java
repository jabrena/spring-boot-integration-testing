package info.jab.ms.service;

import info.jab.ms.repository.GreekGod;
import info.jab.ms.repository.GreekGodRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class GreekGodsServiceTest {

    @Mock
    private GreekGodRepository greekGodRepository;

    @InjectMocks
    private GreekGodsServiceImpl greekGodsService;

    @Test
    void should_return_god_names_from_database() {
        // Given
        List<GreekGod> gods = List.of(
            new GreekGod("Zeus"),
            new GreekGod("Poseidon"),
            new GreekGod("Hades")
        );
        when(greekGodRepository.findAll()).thenReturn(gods);

        // When
        List<String> result = greekGodsService.getGreekGods();

        // Then
        assertThat(result)
                .isNotNull()
                .hasSize(3)
                .containsExactly("Zeus", "Poseidon", "Hades");
        
        verify(greekGodRepository).findAll();
    }
    
    @Test
    void should_save_greek_god_to_database() {
        // Given
        String godName = "Athena";
        
        // When
        greekGodsService.saveGod(godName);
        
        // Then
        verify(greekGodRepository).save(any(GreekGod.class));
    }
} 