package rusrobots.ru.test.service.parser;

import org.springframework.stereotype.Service;

@Service
public interface ParseService {
    void startParsing(Integer id, String filename);
}
