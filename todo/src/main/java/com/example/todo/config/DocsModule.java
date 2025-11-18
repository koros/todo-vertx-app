package com.example.todo.config;

import com.example.todo.todo.api.SwaggerUiHandler;
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
