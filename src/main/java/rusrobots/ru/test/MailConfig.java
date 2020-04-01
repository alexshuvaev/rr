package rusrobots.ru.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Component
public class MailConfig {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private static final File FILE = new File("mailconfig.properties");
    public static final String MAIL_IMAP = "mail.imap";
    public static final String MAIL_USERNAME = "mail.username";
    public static final String MAIL_PASSWORD = "mail.password";

    private Properties prop;
    private boolean configure;

    public MailConfig() {
        if (FILE.exists()) {
            prop = new Properties();
            try {
                InputStream in = new FileInputStream(FILE);
                prop.load(in);
                in.close();

                this.configure = prop.containsKey(MAIL_IMAP) && prop.containsKey(MAIL_USERNAME) && prop.containsKey(MAIL_PASSWORD);

            } catch (IOException e) {
                log.error("Не удалось загрузить файл mailconfig.properties {}", e.getMessage());
            }
        } else {
            this.configure = false;
        }
    }

    public Properties getProp() {
        return prop;
    }

    public boolean isConfigure() {
        return configure;
    }

    public void setConfigure(boolean configure) {
        this.configure = configure;
    }

    public File getFile() {
        return FILE;
    }
}
