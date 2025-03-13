package info.jab.ms.gods.service;

import info.jab.ms.gods.repository.GreekGod;
import info.jab.ms.gods.repository.GreekGodRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class GreekGodsServiceImpl implements GreekGodsService {

    private static final Logger logger = LoggerFactory.getLogger(GreekGodsServiceImpl.class);

    @Value("${address}")
    private String address;

    private final RestTemplate restTemplate;
    private final GreekGodRepository greekGodRepository;

    public GreekGodsServiceImpl(RestTemplate restTemplate, GreekGodRepository greekGodRepository) {
        this.restTemplate = restTemplate;
        this.greekGodRepository = greekGodRepository;
    }

    @Override
    @Transactional
    public List<String> getGreekGods() {
        logger.info("Fetching Greek gods from external API: {}", address);

        ResponseEntity<List<String>> result =
                restTemplate.exchange(
                        address,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>() {}
                );

        List<String> godNames = result.getBody();
        
        if (godNames != null) {
            // Store all gods in the database
            logger.info("Storing {} Greek gods in the database", godNames.size());
            
            // Clear existing gods to avoid duplicates
            greekGodRepository.deleteAll();
            
            // Create and save GreekGod entities
            List<GreekGod> gods = godNames.stream()
                    .map(GreekGod::new)
                    .collect(Collectors.toList());
            
            greekGodRepository.saveAll(gods);
        }

        return godNames;
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<GreekGod> getAllGodsFromDatabase() {
        logger.info("Retrieving all Greek gods from the database");
        return StreamSupport.stream(greekGodRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }
} 