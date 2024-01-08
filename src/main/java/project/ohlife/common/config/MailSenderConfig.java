package project.ohlife.common.config;

import java.util.Properties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import project.ohlife.common.properties.EmailAppProperties;
@Slf4j
@Configuration
@RequiredArgsConstructor
public class MailSenderConfig {

  private final EmailAppProperties properties;

  @Bean
  public JavaMailSender getJavaMailSender() {
    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    mailSender.setHost(properties.getHost());
    mailSender.setPort(properties.getPort());

    mailSender.setUsername(properties.getUsername());
    mailSender.setPassword(properties.getPassword());
    log.info("비밀번호 : ", properties.getPassword());
    log.info("아이디 : ", properties.getUsername());

    Properties props = mailSender.getJavaMailProperties();
    props.put("mail.transport.protocol", "smtp");
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.debug", "false");

    return mailSender;
  }


}
