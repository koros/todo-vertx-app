package com.example.todo.config;

import com.example.todo.app.Bootstrap;
import com.example.todo.app.MainVerticle;
import dagger.Component;
import io.vertx.core.Vertx;
import javax.inject.Singleton;
import org.hibernate.reactive.stage.Stage;

@Singleton
@Component(
    modules = {
      // Infrastructure
      VertxModule.class,
      HibernateModule.class,

      // Web layer
      WebModule.class,
      DocsModule.class,

      // Business domains
      TodoModule.class,

      // Cross-cutting
      MapperModule.class,

      // Application wiring
      AppModule.class
    })
public interface AppComponent {
  Vertx vertx();

  Stage.SessionFactory sessionFactory();

  void inject(Bootstrap bootstrap);

  MainVerticle mainVerticle();
}
