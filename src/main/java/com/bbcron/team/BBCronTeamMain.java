package com.bbcron.team;

import com.bnauk.bbcron.config.LoggingConfig;
import com.bnauk.bbcron.user.repository.support.BBCronResourceRepositoryImpl;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.filter.ForwardedHeaderFilter;

@Import(LoggingConfig.class)
@EnableFeignClients
@EnableMongoRepositories(repositoryBaseClass = BBCronResourceRepositoryImpl.class)
@SpringBootApplication
public class BBCronTeamMain {

  public static void main(String[] args) {
    SpringApplication.run(BBCronTeamMain.class, args);
  }

  /** CustomOpenApi */
  @Bean
  public OpenAPI customOpenAPI(
      @Value("${springdoc.description}") String appDesciption,
      @Value("${springdoc.version}") String appVersion) {
    return new OpenAPI()
        .info(
            new Info()
                .title("BBCron.")
                .version(appVersion)
                .description(appDesciption)
                .termsOfService("http://swagger.io/terms/")
                .license(new License().name("Apache 2.0").url("http://springdoc.org")));
  }

  @Bean
  FilterRegistrationBean<ForwardedHeaderFilter> forwardedHeaderFilter() {
    FilterRegistrationBean<ForwardedHeaderFilter> bean = new FilterRegistrationBean<>();
    bean.setFilter(new ForwardedHeaderFilter());
    return bean;
  }
}
