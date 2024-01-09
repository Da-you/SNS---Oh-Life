package project.ohlife.common.utils.certification;

public interface Certification {

  void createCertification(String key,String certificationNumber);

  String getCertification(String key);

  void deleteCertification(String key);

  boolean hasKey(String key);

}
