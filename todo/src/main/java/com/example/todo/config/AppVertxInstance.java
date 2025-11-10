package com.example.todo.config;

import io.vertx.core.Vertx;
import org.hibernate.reactive.vertx.VertxInstance;

public final class AppVertxInstance implements VertxInstance {
  // set once at startup (from Dagger/bootstrap)
  public static Vertx VERTX;

  @Override
  public Vertx getVertx() {
    if (VERTX == null) {
      throw new IllegalStateException("AppVertxInstance.VERTX not initialized");
    }
    return VERTX;
  }
}
