package project.ohlife.service.s3;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.ohlife.common.properties.AwsProperties;
import project.ohlife.common.utils.s3.FileNameUtils;
import project.ohlife.exception.CustomException;
import project.ohlife.exception.ErrorCode;

@Slf4j
@Service
@EnableConfigurationProperties(AwsProperties.class)
@RequiredArgsConstructor
public class AwsS3Service {

  private final AwsProperties awsProperties;

  private AmazonS3 s3Client;

  @PostConstruct
  private void setS3Client() {
    log.info("S3 connect");
    AWSCredentials credentials = new BasicAWSCredentials(
        awsProperties.getAccessKey(),
        awsProperties.getSecretKey()
    );

    s3Client = AmazonS3ClientBuilder.standard()
        .withCredentials(new AWSStaticCredentialsProvider(credentials))
        .withRegion(awsProperties.getRegionStatic())
        .build();
  }

  public String uploadImage(MultipartFile file) {
    return upload(file, awsProperties.getBucket());
  }

  public String upload(MultipartFile file, String bucket) {
    log.info("bucket name = {}", bucket);
    String fileName = file.getOriginalFilename();
    log.info("originalFileName = {}", fileName);
    String convertedFileName = FileNameUtils.fileNameConvert(fileName);
    log.info("convertedFileName = {}", convertedFileName);
    String fullFileName = bucket + "/" + convertedFileName;
    try {
      String mimeType = new Tika().detect(file.getInputStream());
      ObjectMetadata metadata = new ObjectMetadata();

      FileNameUtils.checkImageType(mimeType);
      metadata.setContentType(mimeType);
      s3Client.putObject(
          new PutObjectRequest(bucket, fullFileName, file.getInputStream(), metadata)
              .withCannedAcl(CannedAccessControlList.PublicRead));
    } catch (IOException e) {
      throw new CustomException(ErrorCode.FILE_READ_FAILED);
    }
    return s3Client.getUrl(bucket, convertedFileName).toString();
  }

  public void deleteImage(String key) {
    delete(awsProperties.getBucket(), key);
  }

  public void delete(String bucket, String key) {
    s3Client.deleteObject(bucket, key);
  }

}
