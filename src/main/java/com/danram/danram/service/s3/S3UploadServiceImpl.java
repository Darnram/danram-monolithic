package com.danram.danram.service.s3;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.danram.danram.exception.s3.FileNameNotValidException;
import com.danram.danram.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class S3UploadServiceImpl implements S3UploadService {
    private final AmazonS3 amazonS3Client;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Override
    public String upload(MultipartFile multipartFile, String dirName, boolean profile) throws IOException {
        String fileName;

        fileName = multipartFile.getOriginalFilename().replaceAll("[^a-zA-Z0-9.\\(\\)]", "");

        String[] parts = fileName.split("\\.");

        if(parts[0].isEmpty()) {
            throw new FileNameNotValidException();
        }

        if (parts.length < 2) {
            throw new IllegalArgumentException("Invalid file type => file name: " + fileName);
        }
        String ext = parts[1];
        String contentType = "";

        //content type을 지정해서 올려주지 않으면 자동으로 "application/octet-stream"으로 고정이 되서 링크 클릭시 웹에서 열리는게 아니라 자동 다운이 시작됨.
        switch (ext) {
            case "jpeg":
                contentType = "image/jpeg";
                break;
            case "png":
                contentType = "image/png";
                break;
            case "txt":
                contentType = "text/plain";
                break;
            case "csv":
                contentType = "text/csv";
                break;
        }

        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(contentType);
            metadata.setContentLength(multipartFile.getSize());

            if(profile == true)
                fileName = JwtUtil.getMemberId().toString() + "." + ext;

            amazonS3Client.putObject(new PutObjectRequest(bucket, dirName + "/" + fileName, multipartFile.getInputStream(), metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (AmazonServiceException e) {
            e.printStackTrace();
        } catch (SdkClientException e) {
            e.printStackTrace();
        }

        //object 정보 가져오기
        ListObjectsV2Result listObjectsV2Result = amazonS3Client.listObjectsV2(bucket);
        List<S3ObjectSummary> objectSummaries = listObjectsV2Result.getObjectSummaries();

        for (S3ObjectSummary object: objectSummaries) {
            System.out.println("object = " + object.toString());
        }
        return amazonS3Client.getUrl(bucket, dirName + "/" + fileName).toString();
    }
}
