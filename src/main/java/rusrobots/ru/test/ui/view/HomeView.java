package rusrobots.ru.test.ui.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import rusrobots.ru.test.MailConfig;
import rusrobots.ru.test.entity.Supplier;
import rusrobots.ru.test.service.crud.SupplierService;
import rusrobots.ru.test.ui.MainLayout;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static rusrobots.ru.test.MailConfig.*;
import static rusrobots.ru.test.ui.UtilUi.showNotification;
import static rusrobots.ru.test.ui.UtilUi.textFieldSetup;

@PageTitle("Список поставщиков")
@UIScope
@SpringComponent
@Route(value = "", layout = MainLayout.class)
public class HomeView extends VerticalLayout {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private SupplierService supplierService;
    private MailConfig mailConfig;
    private Grid<Supplier> suppliersListGrid = new Grid<>();

    @Autowired
    public HomeView(SupplierService supplierService, MailConfig mailConfig) {
        this.mailConfig = mailConfig;
        this.supplierService = supplierService;
        fillPage();
    }

    private void fillPage() {
        addClassName("list-view");
        setSizeFull();

        Div content = new Div(fillMailConfigText(), mailConfigure(), fillSupplierText(), fillGrid());
        content.addClassName("content");
        content.setSizeFull();
        add(content);

    }

    private HorizontalLayout mailConfigure() {
        TextField imapConnect, username, password;

        if (mailConfig.isConfigure()){
            imapConnect = textFieldSetup(mailConfig.getProp().getProperty(MAIL_IMAP));
            username = textFieldSetup(mailConfig.getProp().getProperty(MAIL_USERNAME));
            password = textFieldSetup(mailConfig.getProp().getProperty(MAIL_PASSWORD));
        } else {
            String notConfig = "Не сконфигурировано";
            imapConnect = textFieldSetup(notConfig);
            username = textFieldSetup(notConfig);
            password = textFieldSetup(notConfig);
        }
        List<TextField> formFields = new ArrayList<>(Arrays.asList(imapConnect, username, password));

        Button save = new Button("Сохранить");
        save.setWidth("150px");
        save.addClickListener(e -> save(formFields, mailConfig));
        return new HorizontalLayout(imapConnect, username, password, save);
    }


    private void save(List<TextField> formFields, MailConfig mailConfig) {
        List<String> updated = formFields.stream()
                .map(e -> {
                    if (e.getValue().isEmpty()) {
                        e.setValue(e.getPlaceholder());
                    }
                    return e.getValue();
                }).collect(Collectors.toList());

        if (updated.contains("Не сконфигурировано")){
            showNotification("Пожалуйста заполните не сконфигурированные поля");
        } else {
            mailConfig.getProp().setProperty(MAIL_IMAP, updated.get(0).trim());
            mailConfig.getProp().setProperty(MAIL_USERNAME, updated.get(1).trim());
            mailConfig.getProp().setProperty(MAIL_PASSWORD, updated.get(2).trim());
            try {
                mailConfig.getProp().store(new FileOutputStream(mailConfig.getFile()), null);
                showNotification("Новая конфигурация успешно сохранена");
            } catch (IOException e) {
                log.error("Не удалось записать новую конфигурацию в {} , {}", mailConfig.getFile(), e.getMessage());
            }
        }
    }

    private Div fillSupplierText() {
        H2 h2 = new H2("Поставщики");
        Paragraph p = new Paragraph("В таблице перечислены поставщики и конфигурация парсинга – " +
                "колонка в Базе Данных и соответствующее название колонки в CSV файле. ");

        Paragraph p2 = new Paragraph("Для того чтобы изменить конфигурацию и/или приступить к парсингу данных, " +
                "выберите в таблице нужного поставщика. ");
        return new Div(h2, p, p2);
    }

    private Div fillMailConfigText() {
        H2 h2 = new H2("Конфигурация E-mail");
        Paragraph p2 = new Paragraph("Для того чтобы изменить конфигурацию email, " +
                "заполните форму. Либо ничего не меняйте.");
        Div div = new Div(
                new Paragraph("Тестовая конфигурация:"),
                new Paragraph("imap.mail.ru , rusrobtest@mail.ru , 123qwerty654"));
        return new Div(h2, p2, div);
    }

    private void updateList() {
        suppliersListGrid.setItems(supplierService.findAll());
    }

    public Grid<Supplier> fillGrid() {
        updateList();
        suppliersListGrid.getDataProvider().refreshAll();
        suppliersListGrid.addClassName("contact-suppliersListGrid");
        suppliersListGrid.setSizeFull();
        suppliersListGrid.setHeightByRows(true);
        suppliersListGrid.setMaxHeight("300px");
        suppliersListGrid.addColumn(Supplier::getId).setHeader("Id").setResizable(true);
        suppliersListGrid.addColumn(Supplier::getCompanyName).setHeader("Поставщик").setResizable(true);
        suppliersListGrid.addColumn(Supplier::getVendorLabel).setHeader("Бренд").setResizable(true);
        suppliersListGrid.addColumn(Supplier::getNumberLabel).setHeader("Каталожный номер").setResizable(true);
        suppliersListGrid.addColumn(Supplier::getDescriptionLabel).setHeader("Описание").setResizable(true);
        suppliersListGrid.addColumn(Supplier::getPriceLabel).setHeader("Цена").setResizable(true);
        suppliersListGrid.addColumn(Supplier::getCountLabel).setHeader("Наличие").setResizable(true);

        suppliersListGrid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) UI.getCurrent().navigate(SupplierView.class, event.getValue().getId());
        });
        suppliersListGrid.addComponentColumn(this::buildDeleteButton);
        suppliersListGrid.setHeightByRows(true);
        return suppliersListGrid;
    }

    private Button buildDeleteButton(Supplier supplier) {
        Button button = new Button(VaadinIcon.CLOSE.create());
        button.addClickListener(e -> {

                    Dialog dialog = new Dialog();
                    Button confirm = new Button("Удалить", VaadinIcon.TRASH.create());
                    Button cancel = new Button("Отмена");
                    dialog.add("Вы уверены что хотите удалить поставщика?");
                    dialog.add(confirm);
                    dialog.add(cancel);

                    confirm.addClickListener(clickEvent -> {
                        deleteSupplier(supplier);
                        dialog.close();
                        showNotification("Поставщик удален");
                        updateList();
                    });

                    cancel.addClickListener(clickEvent -> dialog.close());
                    dialog.open();
                }
        );
        return button;
    }

    private void deleteSupplier(Supplier supplier) {
        supplierService.deleteById(supplier.getId());
        updateList();
    }
}
