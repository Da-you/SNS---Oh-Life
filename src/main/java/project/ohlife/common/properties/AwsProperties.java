package project.ohlife.common.properties;


import jakarta.mail.Folder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "cloud.aws")
@RequiredArgsConstructor
public class AwsProperties {

  private final Credentials credentials = new Credentials();
  private final S3 s3 = new S3();
  private final Region region = new Region();

//  @Getter
//  @Setter
//  public static class Folder {
//    private String folder;
//  }


  @Getter
  @Setter
  public static class Credentials {

    private String accessKey;
    private String secretKey;
  }

  @Getter
  @Setter
  public static class S3 {

    private String bucket;

  }


  @Getter
  @Setter
  public static class Region {

    private String statics;
  }

  public String getAccessKey() {
    return credentials.getAccessKey();
  }

  public String getSecretKey() {
    return credentials.getSecretKey();
  }

  public String getBucket() {
    return s3.getBucket();
  }


  public String getRegionStatic() {
    return region.getStatics();
  }

}
