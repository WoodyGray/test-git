package com.woody.firstService.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@Service
public class S3Service {
    private final AmazonS3 s3Client;

    @Value("${s3.bucketname}")
    private String userBucketName;

    @Autowired
    public S3Service(AmazonS3 s3Client){
        this.s3Client = s3Client;
    }

    public void uploadUserPhoto(Long userId, MultipartFile userPhoto){
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(userPhoto.getSize());
            metadata.setContentType(userPhoto.getContentType());

            s3Client.putObject(userBucketName, userId.toString(), userPhoto.getInputStream(), metadata);
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload user photo to s3", e);
        }
    }

    public byte[] downloadUserPhoto(Long userId) throws IOException {
        S3Object userPhotoObject = s3Client.getObject(userBucketName, userId.toString());

        return userPhotoObject.getObjectContent().readAllBytes();

    }

    public String getPhotoUrl(Long userId){
        URL url = s3Client.getUrl(userBucketName, userId.toString());

        return url.toString();
    }
}
