package info.jab.ms.service;

import java.util.List;

public interface GreekGodsService {

    /**
     * Retrieves Greek god names from the database
     * @return List of Greek god names
     */
    List<String> getGreekGods();
    
    /**
     * Saves a Greek god name to the database
     * @param name The name of the Greek god to save
     */
    void saveGod(String name);
} 