package project.ohlife.common.utils.s3;

import java.util.UUID;
import project.ohlife.exception.CustomException;
import project.ohlife.exception.ErrorCode;

public class FileNameUtils {
  public static void checkImageType(String mimeType) {
    if (!(mimeType.equals("image/jpg") || mimeType.equals("image/jpeg")
        || mimeType.equals("image/png") || mimeType.equals("image/gif"))) {
      throw new CustomException(ErrorCode.ILLEGAL_MIME_TYPE);
    }
  }

  public static String fileNameConvert(String fileName) {
    StringBuilder builder = new StringBuilder();
    UUID uuid = UUID.randomUUID();
    String extension = getExtension(fileName);

    builder.append(uuid).append(".").append(extension);

    return builder.toString();
  }


  public static String getExtension(String fileName) {
    int pos = fileName.lastIndexOf(".");
    return fileName.substring(pos + 1);
  }

  public static String getFileName(String path) {
    int idx = path.lastIndexOf("/");
    return path.substring(idx + 1);
  }

}
