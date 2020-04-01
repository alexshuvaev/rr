package rusrobots.ru.test.service.crud;

import rusrobots.ru.test.entity.Supplier;

import java.util.List;

public interface SupplierService {
    List<Supplier> findAll();
    Supplier findById(Integer id);
    void deleteById(Integer id);
    void save(Supplier supplier);
}
