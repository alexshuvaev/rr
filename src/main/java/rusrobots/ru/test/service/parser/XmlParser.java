package rusrobots.ru.test.service.parser;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rusrobots.ru.test.entity.PriceItem;
import rusrobots.ru.test.entity.Supplier;
import rusrobots.ru.test.service.crud.PriceItemService;
import rusrobots.ru.test.service.crud.SupplierService;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class XmlParser implements ParseService {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final List<String> HEADER_CONFIG = Arrays.asList("Бренд", "Каталожный номер", "Описание", "Цена, руб.", "Наличие");
    private final List<Integer> POSITIONS = new ArrayList<>();

    private PriceItemService priceItemService;
    private SupplierService supplierService;

    @Autowired
    public XmlParser(PriceItemService priceItemService, SupplierService supplierService) {
        this.priceItemService = priceItemService;
        this.supplierService = supplierService;
    }

    @Transactional
    public void startParsing(Integer id, String filename) {
        try {
            Supplier supplier = supplierService.findById(id);
            HEADER_CONFIG.set(0, supplier.getVendorLabel());
            HEADER_CONFIG.set(1, supplier.getNumberLabel());
            HEADER_CONFIG.set(2, supplier.getDescriptionLabel());
            HEADER_CONFIG.set(3, supplier.getPriceLabel());
            HEADER_CONFIG.set(4, supplier.getCountLabel());

            Stream<String> stream = Files.lines(Paths.get(filename));

            List<String> list = stream
                    .map(this::delimiter)
                    .collect(Collectors.toList());

            List<String> headerFile = Arrays.asList(list.get(0).split(";"));

            HEADER_CONFIG.forEach(e -> {
                if (headerFile.contains(e)) {
                    POSITIONS.add(headerFile.indexOf(e));
                }
            });

            for (int i = 1; i < list.size(); i++) {
                String s = list.get(i);
                Reader in = new StringReader(s);
                CSVFormat csvFileFormat;
                if (s.contains("\" ") || s.matches(".*\"\"\".*")) {
                    csvFileFormat = CSVFormat.DEFAULT.withQuote(null).withDelimiter(';');
                } else {
                    csvFileFormat = CSVFormat.DEFAULT.withQuote('\"').withDelimiter(';');
                }

                CSVParser csvFileParser = new CSVParser(in, csvFileFormat);

                List<PriceItem> priceItemList = new ArrayList<>();
                if (!priceItemList.isEmpty()) priceItemList.clear();
                List<CSVRecord> csvRecords = csvFileParser.getRecords();
                for (CSVRecord record : csvRecords) {
                    String vendor = record.get(POSITIONS.get(0));
                    String number = record.get(POSITIONS.get(1));
                    String description = record.get(POSITIONS.get(2));

                    String searchVendor = vendor.replaceAll("[^A-Za-zА-Яа-я0-9]", "").toUpperCase();
                    String searchNumber = number.replaceAll("[^A-Za-zА-Яа-я0-9]", "").toUpperCase();
                    double price = Double.parseDouble(record.get(POSITIONS.get(3)).replace(",", "."));

                    String countStr = record.get(POSITIONS.get(4));
                    if (countStr.contains("-")) {
                        countStr = countStr.split("-")[1];
                    }
                    if (countStr.contains("<") || countStr.contains(">")) {
                        countStr = countStr.substring(1);
                    }
                    int count = Integer.parseInt(countStr);
                    priceItemList.add(new PriceItem(vendor, number, searchVendor, searchNumber, description, price, count));
                }
                priceItemService.saveAll(priceItemList);
                priceItemList.clear();
            }

        } catch (IOException e) {
            log.error("Невозможно выполнить парсинг {}", e.getMessage());
        }
    }

    private String delimiter(String e) {
        if (e.contains(";;")) {
            e = e.replace(";;", ";null;");
        }
        return e;
    }

}