package dk.thebeerclub.brewhub.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import dk.thebeerclub.brewhub.model.Image;
import dk.thebeerclub.brewhub.repository.ImageRepository;
//import io.minio.MinioClient;
//import io.minio.ObjectWriteResponse;
//import io.minio.PutObjectArgs;
//import io.minio.errors.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class ImageService {

    @Value("${minio.bucketname}") private String minioBucketName;
    @Value("${minio.endpoint}") private String minioEndpoint;

    private final AmazonS3 amazonS3;
    private final ImageRepository imageRepository;

    public ImageService(AmazonS3 amazonS3, ImageRepository imageRepository) {
        this.amazonS3 = amazonS3;
        this.imageRepository = imageRepository;
    }

    public Image save(Image image) {
        return imageRepository.save(image);
    }

    public String upload(MultipartFile file, String brewId) {

//    public String upload(MultipartFile file, String brewId) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {

//        String objectPath = buildObjectPath(file.getOriginalFilename(), brewId);
//
//        PutObjectArgs obj = PutObjectArgs.builder()
//                .bucket(minioBucketName)
//                .object(objectPath)
//                .stream(file.getInputStream(), file.getSize(), -1)
//                .contentType(file.getContentType())
//                .build();
//
//        ObjectWriteResponse response = minioClient.putObject(obj);
//
//        return minioEndpoint + "/" + minioBucketName + objectPath;
        return null;
    }

    private String buildObjectPath(String originalFilename, String brewId) {
        String uuid = UUID.randomUUID().toString();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        return "/" + brewId + "/" + uuid + extension;
    }

    public boolean delete(String imageId) {
        Optional<Image> optional = imageRepository.findById(Long.valueOf(imageId));
        if (optional.isEmpty()) {
            return false;
        }
        imageRepository.delete(optional.get());
        return true;
    }

    public void tst() {

        List<Bucket> buckets = amazonS3.listBuckets();
        for (Bucket bucket : buckets) {
            log.info("bucket: {}", bucket.getName());
        }

    }
}
