package rusrobots.ru.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
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
        prop = new Properties();
        try {
            if (FILE.exists()) {
                InputStream in = new FileInputStream(FILE);
                prop.load(in);
                in.close();
                this.configure = prop.containsKey(MAIL_IMAP) && prop.containsKey(MAIL_USERNAME) && prop.containsKey(MAIL_PASSWORD);

            } else if (!FILE.exists() || !configure){
                prop.setProperty(MAIL_IMAP,"imap.mail.ru");
                prop.setProperty(MAIL_USERNAME,"rusrobtest@mail.ru");
                prop.setProperty(MAIL_PASSWORD,"123qwerty654");
                prop.store(new FileOutputStream(FILE), null);
                this.configure = true;
            }
        } catch (IOException e) {
            log.error("Не удалось загрузить/сохранить файл mailconfig.properties {}", e.getMessage());
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
