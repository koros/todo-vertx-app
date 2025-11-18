package com.example.todo.config;

import dagger.Module;
import dagger.Provides;
import io.vertx.core.Vertx;
import javax.inject.Singleton;

/**
 * Provides the core Vert.x infrastructure components.
 *
 * <p>This module configures and provides the Vert.x instance that serves as the foundation for the
 * reactive application runtime. The Vert.x instance manages:
 *
 * <ul>
 *   <li>Event loop threads for non-blocking I/O
 *   <li>Worker threads for blocking operations
 *   <li>HTTP server and client infrastructure
 *   <li>Reactive event bus
 * </ul>
 */
@Module
public class VertxInfrastructureModule {

  /**
   * Provides a singleton Vert.x instance with default configuration.
   *
   * @return configured Vert.x runtime instance
   */
  @Provides
  @Singleton
  public Vertx vertx() {
    return Vertx.vertx();
  }
}
