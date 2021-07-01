package dk.thebeerclub.brewhub.config;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.SetBucketPolicyArgs;
import io.minio.errors.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Configuration
@Slf4j
public class MinioConfig {

    @Value("${minio.accesskey}") private String minioAccessKey;
    @Value("${minio.secretkey}") private String minioSecretKey;
    @Value("${minio.endpoint}") private String minioEndpoint;
    @Value("${minio.bucketname}") private String minioBucketName;

    @Bean
    public MinioClient minioClient() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {

        MinioClient minioClient =
                MinioClient.builder()
                        .endpoint(minioEndpoint)
                        .credentials(minioAccessKey, minioSecretKey)
                        .build();

        // create bucket if not exists.
        boolean exists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(minioBucketName).build());
        if (!exists) {

            // create bucket
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(minioBucketName).build());

            // set policy -> all items public.
            String policyString = """
                    {
                        "Statement": [
                            {
                                "Action": [
                                    "s3:GetBucketLocation",
                                    "s3:ListBucket"
                                ],
                                "Effect": "Allow",
                                "Principal": "*",
                                "Resource": "arn:aws:s3:::[BUCKETNAME]"
                            },
                            {
                                "Action": "s3:GetObject",
                                "Effect": "Allow",
                                "Principal": "*",
                                "Resource": "arn:aws:s3:::[BUCKETNAME]/*.*"
                            }
                        ],
                        "Version": "2012-10-17"
                    }
                    """;
            policyString = policyString.replace("[BUCKETNAME]", minioBucketName);
            minioClient.setBucketPolicy(SetBucketPolicyArgs
                    .builder().bucket(minioBucketName).config(policyString).build());

        } else {
            log.info("minio bucket {} already exists.", minioBucketName);
        }

        return minioClient;
    }

}
