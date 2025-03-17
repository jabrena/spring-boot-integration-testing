package info.jab.ms.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import info.jab.ms.repository.GreekGod;
import info.jab.ms.repository.GreekGodRepository;

import java.util.List;

@Service
public class GreekGodsServiceImpl implements GreekGodsService {

    private static final Logger logger = LoggerFactory.getLogger(GreekGodsServiceImpl.class);
    private final GreekGodRepository greekGodRepository;

    public GreekGodsServiceImpl(GreekGodRepository greekGodRepository) {
        this.greekGodRepository = greekGodRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getGreekGods() {
        logger.info("Retrieving Greek god names from the database");
        return greekGodRepository.findAll().stream()
                .map(GreekGod::getName)
                .toList();
    }
    
    @Override
    @Transactional
    public void saveGod(String name) {
        logger.info("Saving Greek god name to the database: {}", name);
        GreekGod greekGod = new GreekGod();
        greekGod.setName(name);
        greekGodRepository.save(greekGod);
    }
} 