package rusrobots.ru.test.service.crud;

import com.vaadin.flow.router.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rusrobots.ru.test.entity.Supplier;
import rusrobots.ru.test.repository.SupplierRepository;

import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {
    private SupplierRepository supplierRepository;

    @Autowired
    public SupplierServiceImpl(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Override
    public List<Supplier> findAll() {
        return supplierRepository.findAll();
    }

    @Override
    public Supplier findById(Integer id) {
        return supplierRepository.findById(id).orElseThrow(() -> new NotFoundException("Поставщик с данным id не найден."));
    }

    @Override
    public void deleteById(Integer id) {
        supplierRepository.deleteById(id);
    }

    @Override
    public void save(Supplier supplier) {
        supplierRepository.save(supplier);
    }
}
