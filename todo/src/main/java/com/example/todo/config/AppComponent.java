package com.example.todo.config;

import com.example.todo.app.Bootstrap;
import com.example.todo.app.MainVerticle;
import com.example.todo.todo.api.TodoRoutes;
import dagger.Component;
import io.vertx.core.Vertx;
import javax.inject.Singleton;
import org.hibernate.reactive.stage.Stage;

@Singleton
@Component(
    modules = {
      VertxModule.class,
      HibernateModule.class,
      TodoModule.class,
      AppModule.class,
      WebModule.class
    })
public interface AppComponent {
  Vertx vertx();

  Stage.SessionFactory sessionFactory();

  TodoRoutes todoRoutes();

  void inject(Bootstrap bootstrap);

  MainVerticle mainVerticle();
}
