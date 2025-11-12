package com.example.todo.config;

import com.example.todo.todo.api.DocsRoutes;
import dagger.Module;
import dagger.Provides;
import io.vertx.ext.web.Router;
import javax.inject.Named;
import javax.inject.Singleton;

@Module
public class DocsModule {
  @Provides
  @Singleton
  @Named("docs")
  Router docsRouter(DocsRoutes docs) {
    return docs.create();
  }
}
