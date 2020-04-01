package rusrobots.ru.test.ui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UtilUi {
    private static final Logger log = LoggerFactory.getLogger(UtilUi.class);

    private UtilUi() {
    }

    public static TextField textFieldSetup(String placeholder) {
        TextField textField = new TextField();
        textField.setPlaceholder(placeholder);
        textField.setMinWidth("12%");
        return textField;
    }

    public static void showNotification(String text){
        Notification notification = new Notification(text, 2500);
        notification.setPosition(Notification.Position.TOP_CENTER);
        notification.addDetachListener(detachEvent -> UI.getCurrent().getPage().reload());
        notification.open();
        log.info(text);
    }
}
