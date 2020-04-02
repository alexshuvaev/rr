package rusrobots.ru.test.service.downloader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.MimeBodyPart;

@Service
public class ImapCsvDownloader implements DownloaderService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    public String downloadCsvFile(String imapConnect, String userName, String password) {
        log.info("Start download CSV file");
        String filename = "";
        try {
            Properties properties = new Properties();
            properties.setProperty("mail.store.protocol", "imaps");
            Session emailSession = Session.getDefaultInstance(properties);
            Store emailStore = null;
            try {
                emailStore = emailSession.getStore("imaps");
                emailStore.connect(imapConnect, userName, password);
            } catch (AuthenticationFailedException e) {
                log.error("Не удаётся подключиться к email. Неверный логин и/или пароль.");
            }

            Folder inboxFolder = emailStore.getFolder("INBOX");
            inboxFolder.open(Folder.READ_ONLY);

            Message[] messages = inboxFolder.getMessages();

            // Only fetch the top 3 messages for now
            for (int i = messages.length - 1; i < messages.length; i++) {
                log.info("Тема: {} \n От: {} \n Дата: {}", messages[i].getSubject(),  messages[i].getFrom()[0], messages[i].getSentDate());

                String contentType = messages[i].getContentType();
                if (contentType.contains("multipart")) {
                    Multipart multiPart = (Multipart) messages[i].getContent();
                    for (int j = 0; j < multiPart.getCount(); j++) {
                        MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(j);
                        if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition()) && part.getFileName().contains(".csv")) {
                            filename = part.getFileName();
                            part.saveFile(filename);
                        }
                    }
                }
            }
            inboxFolder.close(false);
            emailStore.close();
        } catch (NoSuchProviderException e) {
            log.error("NoSuchProviderException {}", e.getMessage());
        } catch (MessagingException e) {
            log.error("MessagingException {}", e.getMessage());
        } catch (IOException e) {
            log.error("IOException {}", e.getMessage());
        }
        return filename;
    }
}