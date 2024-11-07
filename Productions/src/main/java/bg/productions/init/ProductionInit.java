package bg.productions.init;

import bg.productions.repository.ProductionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

@Configuration
public class ProductionInit {

    private final ProductionRepository productionRepository;

    public ProductionInit(ProductionRepository productionRepository) {
        this.productionRepository = productionRepository;
    }

    @Bean
    public DataSourceInitializer dataSourceInitializer(DataSource dataSource, ResourceLoader resourceLoader) {
        DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource);

        if (productionRepository.count() == 0) {
            ResourceDatabasePopulator populate = new ResourceDatabasePopulator();
            populate.addScript(resourceLoader.getResource("classpath:data.sql"));
            initializer.setDatabasePopulator(populate);
        }

        return initializer;
    }
}
