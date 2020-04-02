package rusrobots.ru.test.service.crud;

import rusrobots.ru.test.entity.PriceItem;

import java.util.List;

public interface PriceItemService {
    /**
     * @return Получить все строки ранее загруженного CSV файла.
     */
    List<PriceItem> findAll();

    /**
     * Сохранить спарсенные данные из CSV файла в БД.
     * @param priceItemList Коллекция сущностей, каждая из которых представляет строку данных из CSV файле.
     */
    void saveAll(List<PriceItem> priceItemList);

    /**
     * Посчитать количество сохраненных строк в БД.
     * @return Количество строк в БД.
     */
    long count();

    /**
     * Очистить таблицу с прайс-листом.
     */
    void deleteAll();
}
