package com.example.todo.config;

import com.example.todo.common.ProblemFailureHandler;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dagger.Module;
import dagger.Provides;
import io.vertx.core.Vertx;
import io.vertx.core.json.jackson.DatabindCodec;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import javax.inject.Singleton;
import org.zalando.problem.jackson.ProblemModule;

/**
 * Configures the HTTP server infrastructure and request processing pipeline.
 *
 * <p>This module provides:
 *
 * <ul>
 *   <li>Root router with JSON serialization configured for RFC-3339 timestamps
 *   <li>Body handler for processing request payloads
 *   <li>Global failure handler using RFC-7807 Problem JSON format
 *   <li>Jackson modules for Java 8 Time API and Problem JSON
 * </ul>
 */
@Module
public class HttpServerModule {

  /**
   * Provides the root HTTP router with configured handlers and JSON serialization.
   *
   * @param vertx Vert.x instance
   * @param failure RFC-7807 error handler
   * @return configured root router
   */
  @Provides
  @Singleton
  Router rootRouter(Vertx vertx, ProblemFailureHandler failure) {
    var mapper = DatabindCodec.mapper();
    mapper.registerModule(new JavaTimeModule());
    mapper.registerModule(new ProblemModule());
    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    var router = Router.router(vertx);
    router.route().handler(BodyHandler.create());
    router.route().failureHandler(failure);
    return router;
  }

  /**
   * Provides a global failure handler that formats errors as RFC-7807 Problem JSON.
   *
   * @return configured problem failure handler
   */
  @Provides
  @Singleton
  ProblemFailureHandler problemFailureHandler() {
    return new ProblemFailureHandler();
  }
}
