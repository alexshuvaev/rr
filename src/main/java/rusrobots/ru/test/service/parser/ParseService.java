package rusrobots.ru.test.service.parser;

import org.springframework.stereotype.Service;

@Service
public interface ParseService {
    /**
     * Парсить скаченный CSV файл.
     * @param id Id поставщика, который является владельцем скаченного CSV файла.
     * @param filename Название скаченного CSV файла.
     */
    void startParsing(Integer id, String filename);
}
