package info.jab.ms.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class MyServiceImpl implements MyService {

    private static Logger logger = LoggerFactory.getLogger(MyServiceImpl.class);

    @Value("${address}")
    private String address;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<String> getData() {
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
