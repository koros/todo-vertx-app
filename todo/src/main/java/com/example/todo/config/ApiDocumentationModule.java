package com.example.todo.config;

import com.example.todo.todo.api.SwaggerUiHandler;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

/**
 * Provides API documentation infrastructure using Swagger UI.
 *
 * <p>This module configures the Swagger UI handler that serves:
 *
 * <ul>
 *   <li>Interactive API documentation at {@code /swagger-ui}
 *   <li>OpenAPI specification at {@code /api-docs/openapi.yaml}
 *   <li>Static assets from the Swagger UI WebJar
 * </ul>
 */
@Module
public class ApiDocumentationModule {

  /**
   * Provides the Swagger UI handler for serving API documentation.
   *
   * @return configured Swagger UI handler
   */
  @Provides
  @Singleton
  SwaggerUiHandler swaggerUiHandler() {
    return new SwaggerUiHandler();
  }
}
