package rusrobots.ru.test.service.crud;

import rusrobots.ru.test.entity.PriceItem;

import java.util.List;

public interface PriceItemService {
    List<PriceItem> findAll();
    void saveAll(List<PriceItem> priceItemList);
    long count();
}
