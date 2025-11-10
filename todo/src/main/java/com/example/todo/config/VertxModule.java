package com.example.todo.config;

import dagger.Module;
import dagger.Provides;
import io.vertx.core.Vertx;
import javax.inject.Singleton;

@Module
public class VertxModule {
  @Provides
  @Singleton
  public Vertx provideVertx() {
    return Vertx.vertx();
  }
}
