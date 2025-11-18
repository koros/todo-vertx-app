package com.example.todo.config;

import dagger.Module;
import dagger.Provides;
import jakarta.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Singleton;
import org.hibernate.reactive.stage.Stage;

/**
 * Configures database persistence layer using Hibernate Reactive.
 *
 * <p>This module provides the Hibernate Reactive SessionFactory for non-blocking database
 * operations. Configuration is driven by environment variables:
 *
 * <ul>
 *   <li>{@code LIQUIBASE_URL} - JDBC-style database URL (default:
 *       jdbc:postgresql://localhost:5432/todo)
 *   <li>{@code DB_USER} - Database username (default: todo)
 *   <li>{@code DB_PASSWORD} - Database password (default: todo)
 * </ul>
 *
 * <p>Schema management is handled externally by Liquibase ({@code hibernate.hbm2ddl.auto=none}).
 */
@Module
public class DatabasePersistenceModule {

  /**
   * Provides a singleton Hibernate Reactive SessionFactory configured for PostgreSQL.
   *
   * @return configured SessionFactory for reactive database operations
   */
  @Provides
  @Singleton
  public Stage.SessionFactory sessionFactory() {
    Map<String, Object> props = new HashMap<>();

    // Use JDBC-style URL: HR will still use the reactive Vert.x driver
    props.put(
        "hibernate.connection.url",
        System.getenv().getOrDefault("LIQUIBASE_URL", "jdbc:postgresql://localhost:5432/todo"));
    props.put("hibernate.connection.username", System.getenv().getOrDefault("DB_USER", "todo"));
    props.put("hibernate.connection.password", System.getenv().getOrDefault("DB_PASSWORD", "todo"));

    // No dialect here; let Hibernate infer it
    props.put("hibernate.hbm2ddl.auto", "none");
    props.put("hibernate.show_sql", "false");
    props.put("hibernate.format_sql", "true");

    var emf = Persistence.createEntityManagerFactory("todoPU", props);
    return emf.unwrap(Stage.SessionFactory.class);
  }
}
