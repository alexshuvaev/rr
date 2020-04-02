package rusrobots.ru.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rusrobots.ru.test.entity.PriceItem;

@Repository
public interface PriceItemRepository extends JpaRepository<PriceItem, Integer> {
}
