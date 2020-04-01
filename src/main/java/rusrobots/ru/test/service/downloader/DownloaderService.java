package rusrobots.ru.test.service.downloader;

public interface DownloaderService {
    /**
     * Подключиться к e-mail и скачать вложение письма.
     * @param imapConnect Сервер входящей почты.
     * @param userName Логин/e-mail адрес.
     * @param password Пароль.
     * @return Название скаченного CSV файла.
     */
    String downloadCsvFile(String imapConnect, String userName, String password);
}
