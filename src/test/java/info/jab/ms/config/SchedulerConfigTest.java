package info.jab.ms.config;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SchedulerConfigTest {

    @Test
    void should_create_scheduler_config_instance() {
        // When
        SchedulerConfig schedulerConfig = new SchedulerConfig();
        
        // Then
        assertThat(schedulerConfig).isNotNull();
    }
} 