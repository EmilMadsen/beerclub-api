package dk.thebeerclub.brewhub.config;

//import io.minio.BucketExistsArgs;
//import io.minio.MakeBucketArgs;
//import io.minio.MinioClient;
//import io.minio.SetBucketPolicyArgs;
//import io.minio.errors.*;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
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
    public AmazonS3 amazonS3() {

        AWSCredentials credentials = new BasicAWSCredentials(minioAccessKey, minioSecretKey);
        ClientConfiguration clientConfiguration = new ClientConfiguration();
        clientConfiguration.setSignerOverride("AWSS3V4SignerType");

        return AmazonS3ClientBuilder
                .standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(minioEndpoint, Regions.US_EAST_1.name()))
                .withPathStyleAccessEnabled(true)
                .withClientConfiguration(clientConfiguration)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();

    }

//    @Bean
//    public MinioClient minioClient() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
//
//        MinioClient minioClient =
//                MinioClient.builder()
//                        .endpoint(minioEndpoint)
//                        .credentials(minioAccessKey, minioSecretKey)
//                        .build();
//
//        // create bucket if not exists.
//        boolean exists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(minioBucketName).build());
//        if (!exists) {
//
//            // create bucket
//            minioClient.makeBucket(MakeBucketArgs.builder().bucket(minioBucketName).build());
//
//            // set policy -> all items public.
//            String policyString = """
//                    {
//                        "Statement": [
//                            {
//                                "Action": [
//                                    "s3:GetBucketLocation",
//                                    "s3:ListBucket"
//                                ],
//                                "Effect": "Allow",
//                                "Principal": "*",
//                                "Resource": "arn:aws:s3:::[BUCKETNAME]"
//                            },
//                            {
//                                "Action": "s3:GetObject",
//                                "Effect": "Allow",
//                                "Principal": "*",
//                                "Resource": "arn:aws:s3:::[BUCKETNAME]/*.*"
//                            }
//                        ],
//                        "Version": "2012-10-17"
//                    }
//                    """;
//            policyString = policyString.replace("[BUCKETNAME]", minioBucketName);
//            minioClient.setBucketPolicy(SetBucketPolicyArgs
//                    .builder().bucket(minioBucketName).config(policyString).build());
//
//        } else {
//            log.info("minio bucket {} already exists.", minioBucketName);
//        }
//
//        return minioClient;
//    }

}
