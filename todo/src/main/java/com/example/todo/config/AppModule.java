package com.example.todo.config;

import com.example.todo.app.MainVerticle;
import com.example.todo.todo.api.TodoRoutes;
import dagger.Module;
import dagger.Provides;
import io.vertx.ext.web.Router;
import javax.inject.Singleton;

@Module
public class AppModule {
  @Provides
  @Singleton
  MainVerticle provideMainVerticle(Router router, TodoRoutes routes) {
    return new MainVerticle(router, routes);
  }
}
