package rusrobots.ru.test.service.crud;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import rusrobots.ru.test.entity.PriceItem;
import rusrobots.ru.test.repository.PriceItemRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static rusrobots.ru.test.service.crud.TestData.*;

/**
 * Просто тест для примера.
 */

@SpringBootTest
@RunWith(SpringRunner.class)
class PriceItemServiceImplTest {

    @MockBean
    private PriceItemRepository priceItemRepository;
    @Autowired
    private PriceItemService priceItemService;

    @Test
    void findAll() {
        // given
        when(priceItemRepository.findAll()).thenReturn(PRICE_ITEM_LIST);
        // when
        List<PriceItem> result = priceItemService.findAll();
        // than
        assertIterableEquals(PRICE_ITEM_LIST, result);
        verify(priceItemRepository, times(1)).findAll();
    }
}