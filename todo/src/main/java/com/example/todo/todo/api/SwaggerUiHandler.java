package com.example.todo.todo.api;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.StaticHandler;
import javax.inject.Singleton;

@Singleton
public class SwaggerUiHandler {

  private static final String WEBJAR_PATH = "META-INF/resources/webjars/swagger-ui/5.17.14";
  private static final String SWAGGER_UI_PATH = "/swagger-ui";
  private static final String SWAGGER_UI_INDEX = "/swagger-ui/index.html";
  private static final String OPENAPI_URL = "/api-docs/openapi.yaml";
  private static final String SWAGGER_INITIALIZER = "/swagger-ui/swagger-initializer.js";
  private static final String SWAGGER_INITIALIZER_FILE =
      "webroot/swagger-ui/swagger-initializer.js";
  private static final String API_DOCS_PATH = "/api-docs/*";
  private static final int REDIRECT_STATUS = 302;

  public void mount(Vertx vertx, Router router) {
    mountApiDocsHandler(router);
    mountSwaggerUiRedirect(router);
    mountIndexHtmlRedirect(router);
    mountSwaggerInitializer(vertx, router);
    mountStaticResources(router);
  }

  private void mountApiDocsHandler(Router router) {
    router.route(API_DOCS_PATH).handler(StaticHandler.create("webroot").setCachingEnabled(false));
  }

  private void mountSwaggerUiRedirect(Router router) {
    router.get(SWAGGER_UI_PATH).handler(this::redirectToSwaggerUiWithUrl);
  }

  private void mountIndexHtmlRedirect(Router router) {
    router.get(SWAGGER_UI_INDEX).handler(this::handleIndexHtmlRequest);
  }

  private void mountSwaggerInitializer(Vertx vertx, Router router) {
    router.route(SWAGGER_INITIALIZER).handler(ctx -> handleSwaggerInitializer(vertx, ctx));
  }

  private void mountStaticResources(Router router) {
    router.route(SWAGGER_UI_PATH + "/*").handler(StaticHandler.create(WEBJAR_PATH));
  }

  private void redirectToSwaggerUiWithUrl(RoutingContext ctx) {
    String redirectUrl = SWAGGER_UI_INDEX + "?url=" + OPENAPI_URL;
    sendRedirect(ctx, redirectUrl);
  }

  private void handleIndexHtmlRequest(RoutingContext ctx) {
    String query = ctx.request().query();
    if (needsUrlParameter(query)) {
      redirectToSwaggerUiWithUrl(ctx);
    } else {
      ctx.next();
    }
  }

  private boolean needsUrlParameter(String query) {
    return query == null || !query.contains("url=");
  }

  private void handleSwaggerInitializer(Vertx vertx, RoutingContext ctx) {
    vertx
        .fileSystem()
        .readFile(SWAGGER_INITIALIZER_FILE)
        .onSuccess(buffer -> sendJavaScriptResponse(ctx, buffer))
        .onFailure(err -> ctx.next());
  }

  private void sendJavaScriptResponse(RoutingContext ctx, io.vertx.core.buffer.Buffer buffer) {
    ctx.response()
        .putHeader("Content-Type", "application/javascript")
        .putHeader("Cache-Control", "no-cache, no-store, must-revalidate")
        .end(buffer);
  }

  private void sendRedirect(RoutingContext ctx, String location) {
    ctx.response().setStatusCode(REDIRECT_STATUS).putHeader("Location", location).end();
  }
}
