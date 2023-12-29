package com.HomeSahulat.service.impl;

import com.HomeSahulat.service.BucketService;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@Service
@Slf4j
public class bucketServiceImpl implements BucketService {

    public static final String USER = "User";
    public static final String SERVICE_PROVIDER = "ServiceProvider";
    @Autowired
    private AmazonS3 s3Client;
    @Value("${application.bucket.name}")
    String bucketName;
    private static final Logger logger = LoggerFactory.getLogger(bucketServiceImpl.class);

    @Override
    public String save(byte[] pdf, String folderName, String fileName, String folderType) {
        try {
            if (pdf == null || folderName == null || fileName == null || folderType == null) {
                throw new IllegalArgumentException("Invalid input parameters");
            }

            // Check if the folder exists
            String folderKey;
            if (folderType.equalsIgnoreCase(USER)) {
                folderKey = USER + "/" + folderName;
            } else if (folderType.equalsIgnoreCase(SERVICE_PROVIDER)) {
                folderKey = SERVICE_PROVIDER + "/" + folderName;
            } else {
                throw new IllegalArgumentException("Invalid folder type: " + folderType);
            }

            if (!s3Client.doesObjectExist(bucketName, folderKey + "/")) {
                // Create an empty object to simulate the directory
                s3Client.putObject(bucketName, folderKey + "/", new ByteArrayInputStream(new byte[0]), new ObjectMetadata());
            }

            // Use try-with-resources to automatically close the input stream
            try (ByteArrayInputStream inputStream = new ByteArrayInputStream(pdf)) {
                String key = folderKey + "/" + fileName;
                ObjectMetadata metadata = new ObjectMetadata();
                metadata.setContentLength(pdf.length);

                s3Client.putObject(new PutObjectRequest(bucketName, key, inputStream, metadata));
                return key;
            }
        } catch (Exception e) {
            logger.error("File {} not uploaded to S3 bucket", fileName, e);
            throw new RuntimeException(e.getMessage());
        }
    }


    @Override
    public byte[] downloadFile(String folderName, String fileName, String folderType) {
        try {
            String key = folderType + "/" + folderName + "/" + fileName;

            // Check if the file exists in the S3 bucket
            if (!s3Client.doesObjectExist(bucketName, key)) {
                throw new FileNotFoundException("File not found: " + key);
            }

            S3Object s3Object = s3Client.getObject(bucketName, key);

            try (S3ObjectInputStream inputStream = s3Object.getObjectContent()) {
                return IOUtils.toByteArray(inputStream);
            }
            // Ensure the input stream is closed
        } catch (IOException e) {
            logger.error("Error downloading file from S3 bucket: {}", fileName, e);
            throw new RuntimeException("Error downloading file from S3 bucket: " + e.getMessage());
        }
    }

    @Override
    public void deleteFile(String fileName) {
        try {
            s3Client.deleteObject(new DeleteObjectRequest(bucketName, fileName));
            logger.info("File deleted successfully: {}", fileName);
        } catch (Exception e) {
            logger.error("Error deleting file from S3 bucket: {}", fileName);
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void deleteFilesStartingWith(String folderKey, String prefix) {
        try {
            // List the objects in the specified folder
            ObjectListing objectListing = s3Client.listObjects(bucketName, folderKey);

            // Iterate through the objects and delete files with names starting with the specified prefix
            for (S3ObjectSummary summary : objectListing.getObjectSummaries()) {
                String objectKey = summary.getKey();

                // Check if the object is a file and its name starts with the specified prefix
                if (objectKey.startsWith(folderKey + "/" + prefix)) {
                    String file = folderKey + "/" + prefix;
                    s3Client.deleteObject(new DeleteObjectRequest(bucketName, objectKey));
                    logger.info("File deleted successfully: {}", objectKey);
                }
            }
        } catch (Exception e) {
            logger.error("Error deleting files from path {} in S3 bucket with prefix {}: {}", folderKey, prefix, e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }



    @Override
    public void deleteFolder(String folderName) {
        ObjectListing objectListing = s3Client.listObjects(bucketName, folderName);

        // Iterate through each object in the folder and delete it
        for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
            s3Client.deleteObject(bucketName, objectSummary.getKey());
        }

        // Delete the folder itself
        s3Client.deleteObject(bucketName, folderName);
    }

}

