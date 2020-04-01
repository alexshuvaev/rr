package rusrobots.ru.test.service.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rusrobots.ru.test.entity.PriceItem;
import rusrobots.ru.test.repository.PriceItemRepository;

import java.util.List;

@Service
public class PriceItemServiceImpl implements PriceItemService {
    private PriceItemRepository priceItemRepository;

    @Autowired
    public PriceItemServiceImpl(PriceItemRepository priceItemRepository) {
        this.priceItemRepository = priceItemRepository;
    }

    @Override
    public List<PriceItem> findAll() {
        return priceItemRepository.findAll();
    }

    @Override
    public void saveAll(List<PriceItem> priceItemList) {
        priceItemRepository.saveAll(priceItemList);
    }

    public long count(){
        return priceItemRepository.count();
    }

}
