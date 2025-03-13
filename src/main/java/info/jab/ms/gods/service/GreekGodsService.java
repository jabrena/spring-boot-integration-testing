package info.jab.ms.gods.service;

import java.util.List;

import info.jab.ms.gods.repository.GreekGod;

public interface GreekGodsService {

    /**
     * Retrieves Greek gods from the external API and stores them in the database
     * @return List of Greek god names
     */
    List<String> getGreekGods();
    
    /**
     * Retrieves all Greek gods from the database
     * @return List of GreekGod entities
     */
    List<GreekGod> getAllGodsFromDatabase();
} 