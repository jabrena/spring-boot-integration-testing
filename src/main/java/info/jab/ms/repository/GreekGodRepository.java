package info.jab.ms.repository;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GreekGodRepository extends ListCrudRepository<GreekGod, Long> {
    // Spring Data JDBC will provide all basic CRUD operations
} 