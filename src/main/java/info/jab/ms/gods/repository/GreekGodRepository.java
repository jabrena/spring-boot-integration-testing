package info.jab.ms.gods.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GreekGodRepository extends CrudRepository<GreekGod, Long> {
    // Spring Data JDBC will provide all basic CRUD operations
} 