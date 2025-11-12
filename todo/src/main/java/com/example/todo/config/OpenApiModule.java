package com.example.todo.config;

import com.example.todo.todo.api.OpenApiRouterFactory;
import dagger.Module;
import dagger.Provides;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import javax.inject.Named;
import javax.inject.Singleton;

@Module
public class OpenApiModule {
  @Provides
  @Singleton
  @Named("api")
  Router apiRouter(Vertx vertx, OpenApiRouterFactory factory) {
    return factory.buildFrom("openapi.yaml");
  }
}
