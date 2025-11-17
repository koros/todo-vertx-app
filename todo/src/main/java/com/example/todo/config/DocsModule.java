package com.example.todo.config;

import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public class DocsModule {
  @Provides
  @Singleton
  SwaggerUiHandler swaggerUiHandler() {
    return new SwaggerUiHandler();
  }
}
