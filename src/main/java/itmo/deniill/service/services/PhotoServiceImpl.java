package itmo.deniill.service.services;

import itmo.deniill.service.services.interfaces.PhotoService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.net.URI;

@Service
public class PhotoServiceImpl implements PhotoService {

    private static final String KEY_ID = "YCAJEVGu7gP8mHem1ZJuNypcM";
    private static final String SECRET_KEY = "YCMlavv7CbayubDiKT_U5DsCuNA-jwHPdZe7AAXz";
    private static final String REGION = "ru-central1";
    private static final String S3_ENDPOINT = "https://storage.yandexcloud.net";

    private static final String BUCKET = "library-s3";

    private final S3Client s3Client;

    public PhotoServiceImpl() {
        AwsCredentials credentials = AwsBasicCredentials.create(KEY_ID, SECRET_KEY);

        this.s3Client = S3Client.builder()
                .httpClient(ApacheHttpClient.create())
                .region(Region.of(REGION))
                .endpointOverride(URI.create(S3_ENDPOINT))
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();
    }

    @Override
    public String uploadFile(MultipartFile photo, String basePath) {
        String key = basePath + '/' + photo.getOriginalFilename();
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(BUCKET)
                .key(key)
                .contentType(photo.getContentType())
                .build();

        try {
            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(photo.getBytes()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return key;
    }

    @Override
    public byte[] downloadFile(String fileName) {
        GetObjectRequest objectRequest = GetObjectRequest.builder()
                .bucket(BUCKET)
                .key(fileName)
                .build();

        var inputStream = s3Client.getObject(objectRequest);
        byte[] data = null;
        try {
            data = inputStream.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return data;
    }
}
