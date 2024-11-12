package bg.moviebox.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "productions.api")
public class ProductionsApiConfig {
  private String baseUrl;

  public String getBaseUrl() {
    return baseUrl;
  }

  public ProductionsApiConfig setBaseUrl(String baseUrl) {
    this.baseUrl = baseUrl;
    return this;
  }
}
