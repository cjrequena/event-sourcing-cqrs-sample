package com.cjrequena.sample.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * <p>
 * <p>
 * <p>
 * <p>
 *
 * @author cjrequena
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
  entityManagerFactoryRef = "entityManagerFactory",
  transactionManagerRef = "transactionManager",
  basePackages = {"com.cjrequena.sample.db.repository"}
)
public class DatabaseConfiguration {

  @Bean(name = "dataSource", destroyMethod = "")
  public DataSource dataSource() {
    return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).build();
  }

  /**
   *
   * @param builder
   * @param dataSource
   * @return
   */
  @Primary
  @Bean("entityManagerFactory")
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder, @Qualifier("dataSource") DataSource dataSource) {
    return builder
      .dataSource(dataSource)
      .packages("com.cjrequena.sample.entity")
      .persistenceUnit("cqrs")
      .build();
  }

  /**
   *
   * @param entityManagerFactory
   * @return
   */
  @Primary
  @Bean("transactionManager")
  public PlatformTransactionManager transactionManager(@Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory) {
    return new JpaTransactionManager(entityManagerFactory);
  }
}


