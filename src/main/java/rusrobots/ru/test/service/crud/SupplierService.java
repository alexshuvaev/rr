package rusrobots.ru.test.service.crud;

import rusrobots.ru.test.entity.Supplier;

import java.util.List;

public interface SupplierService {
    /**
     * Supplier - сущность, которая является представлением конфигурации CSV файла конкретного поставщика.
     * Сущность отражает связь между колонками CSV файла и колонками в таблице БД.
     * @return Получить всех поставщиков (конфигурации).
     */
    List<Supplier> findAll();

    /**
     * @param id Id конкретного поставщика (конфигурации).
     * @return конкретного поставщика либо NotFoundException если поставщик с данным Id не найден.
     */
    Supplier findById(Integer id);

    /**
     * Удалить поставщика (конфигурацию).
     * @param id Id конкретного поставщика (конфигурации).
     */
    void deleteById(Integer id);

    /**
     * Сохранить поставщика (конфигурацию).
     * @param supplier поставщик, который будет сохранен.
     */
    void save(Supplier supplier);
}
