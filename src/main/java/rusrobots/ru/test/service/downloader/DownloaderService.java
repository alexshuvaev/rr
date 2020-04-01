package rusrobots.ru.test.service.downloader;

public interface DownloaderService {
    String downloadCsvFile(String imapConnect, String userName, String password);
}
