package com.example.todo.config;

import com.example.todo.app.MainVerticle;
import com.example.todo.todo.api.SwaggerUiHandler;
import com.example.todo.todo.api.TodoRoutes;
import dagger.Module;
import dagger.Provides;
import io.vertx.ext.web.Router;
import javax.inject.Singleton;

/**
 * Manages application lifecycle and wires together the HTTP server verticle.
 *
 * <p>This module provides the {@link MainVerticle} which orchestrates:
 *
 * <ul>
 *   <li>HTTP server startup and configuration
 *   <li>Route mounting (API endpoints and documentation)
 *   <li>Graceful shutdown handling
 * </ul>
 *
 * <p>The verticle is deployed by {@link com.example.todo.app.Bootstrap} during application startup.
 */
@Module
public class ApplicationLifecycleModule {

  /**
   * Provides the main application verticle with all required HTTP components.
   *
   * @param router root HTTP router with configured handlers
   * @param routes todo domain HTTP routes
   * @param swaggerUi API documentation handler
   * @return configured main verticle ready for deployment
   */
  @Provides
  @Singleton
  MainVerticle mainVerticle(Router router, TodoRoutes routes, SwaggerUiHandler swaggerUi) {
    return new MainVerticle(router, routes, swaggerUi);
  }
}
