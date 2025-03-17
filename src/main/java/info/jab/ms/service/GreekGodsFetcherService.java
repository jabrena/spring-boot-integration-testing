package info.jab.ms.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import info.jab.ms.repository.GreekGod;
import info.jab.ms.repository.GreekGodRepository;

import java.util.List;

@Service
public class GreekGodsFetcherService {

    private static final Logger logger = LoggerFactory.getLogger(GreekGodsFetcherService.class);

    @Value("${address}")
    private String address;

    private final RestTemplate restTemplate;
    private final GreekGodRepository greekGodRepository;

    public GreekGodsFetcherService(RestTemplate restTemplate, GreekGodRepository greekGodRepository) {
        this.restTemplate = restTemplate;
        this.greekGodRepository = greekGodRepository;
    }

    @Scheduled(fixedRate = 10000) // Run every 10 seconds
    @Transactional
    public void fetchAndStoreGreekGods() {
        logger.info("Scheduled task: Fetching Greek gods from external API: {}", address);

        try {
            ResponseEntity<List<String>> result =
                    restTemplate.exchange(
                            address,
                            HttpMethod.GET,
                            null,
                            new ParameterizedTypeReference<>() {}
                    );

            List<String> godNames = result.getBody();
            
            if (godNames != null) {
                logger.info("Storing {} Greek gods in the database", godNames.size());
                
                // Clear existing gods to avoid duplicates
                greekGodRepository.deleteAll();
                
                // Create and save GreekGod entities
                List<GreekGod> gods = godNames.stream()
                        .map(GreekGod::new)
                        .toList();
                
                greekGodRepository.saveAll(gods);
                logger.info("Successfully stored Greek gods in the database");
            }
        } catch (Exception e) {
            logger.error("Error while fetching and storing Greek gods: {}", e.getMessage(), e);
        }
    }
} 