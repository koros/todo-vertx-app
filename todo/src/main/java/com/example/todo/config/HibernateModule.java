package com.example.todo.config;

import dagger.Module;
import dagger.Provides;
import jakarta.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Singleton;
import org.hibernate.reactive.stage.Stage;

@Module
public class HibernateModule {

  @Provides
  @Singleton
  public Stage.SessionFactory provideSessionFactory() {
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
