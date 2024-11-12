package bg.moviebox.config;

import bg.moviebox.repository.UserRoleRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;

@Configuration
public class AppConfig {

  //Populate the Database
  @Bean
  public DataSourceInitializer dataSourceInitializer(DataSource dataSource,
      UserRoleRepository userRoleRepository,
      ResourceLoader resourceLoader) {
    DataSourceInitializer initializer = new DataSourceInitializer();
    initializer.setDataSource(dataSource);

    if (userRoleRepository.count() == 0) {
      ResourceDatabasePopulator populate = new ResourceDatabasePopulator();
      populate.addScript(resourceLoader.getResource("classpath:data.sql"));
      initializer.setDatabasePopulator(populate);
    }

    return initializer;
  }

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }

}
