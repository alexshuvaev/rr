package rusrobots.ru.test.ui.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.UnorderedList;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.klaudeta.PaginatedGrid;
import rusrobots.ru.test.MailConfig;
import rusrobots.ru.test.entity.PriceItem;
import rusrobots.ru.test.entity.Supplier;
import rusrobots.ru.test.service.crud.PriceItemService;
import rusrobots.ru.test.service.crud.SupplierService;
import rusrobots.ru.test.service.downloader.DownloaderService;
import rusrobots.ru.test.service.parser.ParseService;
import rusrobots.ru.test.ui.MainLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static rusrobots.ru.test.MailConfig.*;
import static rusrobots.ru.test.ui.UtilUi.showNotification;
import static rusrobots.ru.test.ui.UtilUi.textFieldSetup;

@PageTitle("Парсинг данных")
@UIScope
@SpringComponent
@Route(value = "supplier", layout = MainLayout.class)
public class SupplierView extends VerticalLayout implements HasUrlParameter<Integer> {
    private Integer supplierId = -1;
    private Supplier supplier;
    private Button start = new Button("Начать парсинг");

    private PriceItemService priceItemService;
    private SupplierService supplierService;
    private ParseService parseService;
    private DownloaderService downloaderService;
    private MailConfig mailConfig;

    @Autowired
    public SupplierView(PriceItemService priceItemService, SupplierService supplierService, ParseService parseService, DownloaderService downloaderService, MailConfig mailConfig) {
        this.mailConfig = mailConfig;
        this.priceItemService = priceItemService;
        this.supplierService = supplierService;
        this.parseService = parseService;
        this.downloaderService = downloaderService;
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, Integer supplierId) {
        if (!this.supplierId.equals(supplierId)) {
            this.supplierId = supplierId;
            supplier = supplierService.findById(supplierId);
        }
        Paragraph p = new Paragraph("");
        if (priceItemService.count() > 0){
            start.setEnabled(false);
            p = new Paragraph("Для повторного парсинга, перезапустите приложение.");
        }
        removeAll();
        add(createForm(), startParsing(), p, fillSupplierGrid());
    }

    private VerticalLayout createForm() {
        String vendorLabel = supplier.getVendorLabel();
        String numberLabel = supplier.getNumberLabel();
        String descriptionLabel = supplier.getDescriptionLabel();
        String priceLabel = supplier.getPriceLabel();
        String countLabel = supplier.getCountLabel();

        H2 h2 = new H2(supplier.getCompanyName());
        Paragraph p = new Paragraph("Текущая конфигурация парсинга:");
        UnorderedList unorderedList = new UnorderedList(
                new ListItem("Бренд : " + vendorLabel),
                new ListItem("Каталожный номер : " + numberLabel),
                new ListItem("Описание : " + descriptionLabel),
                new ListItem("Цена : " + priceLabel),
                new ListItem("Наличие : " + countLabel)
        );
        Paragraph p2 = new Paragraph("Для изменения конфигурации, заполните форму ниже. Либо оставьте без изменений.");

        TextField brand = textFieldSetup(vendorLabel);
        TextField number = textFieldSetup(numberLabel);
        TextField description = textFieldSetup(descriptionLabel);
        TextField price = textFieldSetup(priceLabel);
        TextField count = textFieldSetup(countLabel);
        List<TextField> formFields = new ArrayList<>(Arrays.asList(brand, number, description, price, count));

        Button save = new Button("Сохранить");
        save.setWidth("150px");
        save.addClickListener(e -> save(formFields));

        VerticalLayout beforeForm = new VerticalLayout(h2, p, unorderedList, p2);
        HorizontalLayout form = new HorizontalLayout(brand, number, description, price, count, save);

        return new VerticalLayout(beforeForm, form);
    }

    private void save(List<TextField> formFields) {
        List<TextField> updated = formFields.stream()
                .map(e -> {
                    if (e.getValue().isEmpty()) {
                        e.setValue(e.getPlaceholder());
                    }
                    return e;
                }).collect(Collectors.toList());

        supplier.setVendorLabel(updated.get(0).getValue());
        supplier.setNumberLabel(updated.get(1).getValue());
        supplier.setDescriptionLabel(updated.get(2).getValue());
        supplier.setPriceLabel(updated.get(3).getValue());
        supplier.setCountLabel(updated.get(4).getValue());
        supplierService.save(supplier);

        showNotification("Новая конфигурация успешно сохранена");
    }

    VerticalLayout startParsing() {
        String imapConnect = mailConfig.getProp().getProperty(MAIL_IMAP);
        String email = mailConfig.getProp().getProperty(MAIL_USERNAME);
        String password = mailConfig.getProp().getProperty(MAIL_PASSWORD);

        start.setWidth("250px");
        start.getElement().getThemeList().add("primary");

        start.addClickListener(e -> {
            String fileName = downloaderService.downloadCsvFile(imapConnect, email, password);

            parseService.startParsing(supplierId, fileName);
            showNotification("Прайс-лист успешно скачен и загружен в БД");
            start.setEnabled(false);
        });
        return new VerticalLayout(start);
    }

    private Grid<PriceItem> fillSupplierGrid() {

        PaginatedGrid<PriceItem> priceItemGrid = new PaginatedGrid<>();
        priceItemGrid.removeAllColumns();
        List<PriceItem> data = priceItemService.findAll();
        priceItemGrid.setItems(data);
        priceItemGrid.setPageSize(500);
        priceItemGrid.setPaginatorSize(20);

        priceItemGrid.addColumn(PriceItem::getId).setHeader("Id").setResizable(true)
        .setFooter("Всего: " + data.size() + " строк");
        priceItemGrid.addColumn(PriceItem::getVendor).setHeader("Бренд").setResizable(true);
        priceItemGrid.addColumn(PriceItem::getNumber).setHeader("Каталожный номер").setResizable(true);
        priceItemGrid.addColumn(PriceItem::getSearchVendor).setHeader("Бренд (поиск)").setResizable(true);
        priceItemGrid.addColumn(PriceItem::getSearchNumber).setHeader("Каталожный номер (поиск)").setResizable(true);
        priceItemGrid.addColumn(PriceItem::getDescription).setHeader("Описание").setResizable(true);
        priceItemGrid.addColumn(PriceItem::getPrice).setHeader("Цена").setResizable(true);
        priceItemGrid.addColumn(PriceItem::getCount).setHeader("Наличие").setResizable(true);

        setWidth("100%");
        priceItemGrid.setHeightByRows(true);
        return priceItemGrid;
    }
}
