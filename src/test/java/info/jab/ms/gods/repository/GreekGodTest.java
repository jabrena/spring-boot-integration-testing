package info.jab.ms.gods.repository;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GreekGodTest {

    @Test
    void should_create_greek_god_with_default_constructor() {
        // When
        GreekGod greekGod = new GreekGod();
        
        // Then
        assertThat(greekGod).isNotNull();
        assertThat(greekGod.getId()).isNull();
        assertThat(greekGod.getName()).isNull();
    }
    
    @Test
    void should_create_greek_god_with_name() {
        // When
        GreekGod greekGod = new GreekGod("Zeus");
        
        // Then
        assertThat(greekGod).isNotNull();
        assertThat(greekGod.getId()).isNull();
        assertThat(greekGod.getName()).isEqualTo("Zeus");
    }
    
    @Test
    void should_set_and_get_id() {
        // Given
        GreekGod greekGod = new GreekGod();
        
        // When
        greekGod.setId(1L);
        
        // Then
        assertThat(greekGod.getId()).isEqualTo(1L);
    }
    
    @Test
    void should_set_and_get_name() {
        // Given
        GreekGod greekGod = new GreekGod();
        
        // When
        greekGod.setName("Poseidon");
        
        // Then
        assertThat(greekGod.getName()).isEqualTo("Poseidon");
    }
    
    @Test
    void should_generate_to_string() {
        // Given
        GreekGod greekGod = new GreekGod("Hades");
        greekGod.setId(3L);
        
        // When
        String result = greekGod.toString();
        
        // Then
        assertThat(result).contains("id=3");
        assertThat(result).contains("name='Hades'");
    }
} 