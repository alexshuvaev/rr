package rusrobots.ru.test.service.crud;

import rusrobots.ru.test.entity.PriceItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestData {
    private TestData() {
    }

    static final PriceItem PI_1 = new PriceItem("Vendor first", "123", "VENDOR FIRST", "123", "Text 1", 9.9, 10);
    static final PriceItem PI_2 = new PriceItem("Vendor second", "456", "VENDOR SECOND", "456", "Text 2", 5.9, 20);
    static final PriceItem PI_3 = new PriceItem("Vendor third", "789", "VENDOR THIRD", "789", "Text 3", 3.9, 30);

    static final List<PriceItem> PRICE_ITEM_LIST = new ArrayList<>(Arrays.asList(PI_1, PI_2, PI_3));
}
