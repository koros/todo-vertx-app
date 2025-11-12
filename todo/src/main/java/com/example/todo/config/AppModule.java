package com.example.todo.config;

import com.example.todo.app.MainVerticle;
import com.example.todo.todo.api.TodoRoutes;
import dagger.Module;
import dagger.Provides;
import io.vertx.ext.web.Router;
import javax.inject.Named;
import javax.inject.Singleton;

@Module
public class AppModule {
  @Provides
  @Singleton
  MainVerticle provideMainVerticle(
      @Named("root") Router router,
      TodoRoutes routes,
      @Named("api") Router api,
      @Named("docs") Router docs) {
    return new MainVerticle(router, routes, api, docs);
  }
}
