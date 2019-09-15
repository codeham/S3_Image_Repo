package com.example.s3;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.image_processing.MetadataExtract;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.common.ImageMetadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;


@Service
@ConfigurationProperties
public class AmazonClient {

    private AmazonS3 s3Client;

    @Value("${amazonProperties.endpointUrl}")
    private String endpointUrl;

    @Value("${amazonProperties.accessKey}")
    private String accessKey;

    @Value("${amazonProperties.secretKey}")
    private String secretKey;

    @Value("${amazonProperties.bucketName}")
    private String bucketName;

    @PostConstruct
    private void initializeAmazon(){
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
        s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(Regions.US_WEST_1)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
    }

    private File convertMultiPartFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);

        fos.write(file.getBytes());
        fos.close();

        return convFile;
    }

    private String generateFileName(MultipartFile multiPart){
        return new Date().getTime() + "_" + multiPart.getOriginalFilename().replace(" ", "_");
    }

    private void uploadFileTos3bucket(String fileName, File file){
        try{
            PutObjectRequest request = new PutObjectRequest(bucketName, fileName, file);
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType("multipart/form-data");
            request.setMetadata(metadata);
            s3Client.putObject(request);
        }catch(AmazonServiceException e){
            e.printStackTrace();
        }catch(SdkClientException e){
            e.printStackTrace();
        }
    }

    public Map<String, String> uploadFile(MultipartFile multipartFile){

        String fileUrl = "";
        Map<String, String> metaData = new HashMap<>();
        try{
            File file = convertMultiPartFile(multipartFile);
            MetadataExtract metadataExtract = new MetadataExtract();
            metaData = metadataExtract.metadataExample(file);
            String fileName = generateFileName(multipartFile);
            fileUrl = endpointUrl + "/" + bucketName + "/" + fileName;
            uploadFileTos3bucket(fileName, file);
            file.delete();
        }catch (Exception e){
            e.printStackTrace();
        }
        return metaData;
    }

    public String deleteFileFromS3Bucket(String fileUrl) {
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        s3Client.deleteObject(new DeleteObjectRequest(bucketName + "/", fileName));
        return "Successfully deleted";
    }
}
