package com.example.todo.todo.api;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;
import javax.inject.Singleton;

@Singleton
public class SwaggerUiHandler {

  private static final String WEBJAR_PATH = "META-INF/resources/webjars/swagger-ui/5.17.14";

  public void mount(Vertx vertx, Router router) {
    router.route("/api-docs/*").handler(StaticHandler.create("webroot").setCachingEnabled(false));

    router
        .get("/swagger-ui")
        .handler(
            ctx ->
                ctx.response()
                    .setStatusCode(302)
                    .putHeader("Location", "/swagger-ui/index.html?url=/api-docs/openapi.yaml")
                    .end());

    router
        .get("/swagger-ui/index.html")
        .handler(
            ctx -> {
              String q = ctx.request().query();
              if (q == null || !q.contains("url=")) {
                ctx.response()
                    .setStatusCode(302)
                    .putHeader("Location", "/swagger-ui/index.html?url=/api-docs/openapi.yaml")
                    .end();
              } else {
                ctx.next();
              }
            });

    router
        .route("/swagger-ui/swagger-initializer.js")
        .handler(
            ctx ->
                vertx
                    .fileSystem()
                    .readFile("webroot/swagger-ui/swagger-initializer.js")
                    .onSuccess(
                        buffer ->
                            ctx.response()
                                .putHeader("Content-Type", "application/javascript")
                                .putHeader("Cache-Control", "no-cache, no-store, must-revalidate")
                                .end(buffer))
                    .onFailure(err -> ctx.next()));

    router.route("/swagger-ui/*").handler(StaticHandler.create(WEBJAR_PATH));
  }
}
