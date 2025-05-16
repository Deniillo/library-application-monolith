package itmo.deniill.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import itmo.deniill.service.services.interfaces.PhotoService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping("api/photos")
@Tag(name = "Photos")
@ApiResponses(@ApiResponse(responseCode = "200", useReturnTypeSchema = true))
@AllArgsConstructor
public class PhotoController {

    @Autowired private final PhotoService photoService;

    @PutMapping(consumes = MULTIPART_FORM_DATA_VALUE)
    public String uploadFile(@RequestParam MultipartFile photo, String basePath) throws IOException {
        return photoService.uploadFile(photo, basePath);
    }

    @GetMapping
    public ResponseEntity<byte[]> downloadFile(@RequestParam String key) throws IOException {

        var data = photoService.downloadFile(key);

        return ResponseEntity.ok()
                .body(data);
    }
}