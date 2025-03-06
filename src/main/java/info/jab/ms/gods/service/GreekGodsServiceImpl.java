package info.jab.ms.gods.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class GreekGodsServiceImpl implements GreekGodsService {

    private static final Logger logger = LoggerFactory.getLogger(GreekGodsServiceImpl.class);

    @Value("${address}")
    private String address;

    private final RestTemplate restTemplate;

    public GreekGodsServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<String> getGreekGods() {
        logger.info(address);

        ResponseEntity<List<String>> result =
                restTemplate.exchange(
                        address,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>() {}
                );

        return result.getBody();
    }
} 