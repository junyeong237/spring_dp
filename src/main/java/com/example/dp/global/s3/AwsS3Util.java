package com.example.dp.global.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.dp.global.s3.exception.AwsS3ErrorCode;
import com.example.dp.global.s3.exception.AwsS3InternalException;
import com.example.dp.global.s3.exception.FileTypeNotAllowedException;
import com.example.dp.global.s3.exception.NotFoundS3FileException;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Component
public class AwsS3Util {

    @Getter
    @RequiredArgsConstructor
    public enum ImagePath {
        PROFILE("profile/"), MENU("menu/");

        private final String path;
    }

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    private final static String IMAGE_JPG = "image/jpeg";
    private final static String IMAGE_PNG = "image/png";

    public String uploadImage(MultipartFile multipartFile, ImagePath imagePath) throws IOException {
        if (!isImageFile(multipartFile)) {
            throw new FileTypeNotAllowedException(AwsS3ErrorCode.FILE_NOT_ALLOW);
        }

        String fileName = UUID.randomUUID().toString();
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());

        amazonS3.putObject(bucketName, imagePath.getPath() + fileName,
            multipartFile.getInputStream(), metadata);

        return fileName;
    }

    private boolean isImageFile(MultipartFile multipartFile) {
        return Objects.equals(multipartFile.getContentType(), IMAGE_JPG)
            || Objects.equals(multipartFile.getContentType(), IMAGE_PNG);
    }

    public String getImagePath(String fileName, ImagePath imagePath) {
        return amazonS3.getUrl(bucketName, imagePath.getPath() + fileName).toString();
    }

    public void deleteImage(String fileName, ImagePath imagePath) {
        if (fileName.isEmpty()
            || !amazonS3.doesObjectExist(bucketName, imagePath.getPath() + fileName)) {
            throw new NotFoundS3FileException(AwsS3ErrorCode.FILE_NOT_FOUND);
        }
        amazonS3.deleteObject(bucketName, imagePath.getPath() + fileName);
    }

    public boolean existsImage(String fileName, ImagePath imagePath){
        try{
            return amazonS3.doesObjectExist(bucketName, imagePath.getPath() + fileName);
        } catch (Exception e){
            throw new AwsS3InternalException(AwsS3ErrorCode.AWS_INTERNAL_ERROR);
        }
    }
}
