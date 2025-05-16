package itmo.deniill.service.services.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface PhotoService {
    String uploadFile(MultipartFile photo, String basePath);
    byte[] downloadFile(String fileName);
}
