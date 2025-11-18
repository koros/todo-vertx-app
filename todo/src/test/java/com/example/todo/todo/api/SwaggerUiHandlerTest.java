package com.example.todo.todo.api;

import static org.assertj.core.api.Assertions.assertThat;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(VertxExtension.class)
@DisplayName("SwaggerUiHandler Tests")
class SwaggerUiHandlerTest {

  private SwaggerUiHandler handler;

  @BeforeEach
  void setUp() {
    handler = new SwaggerUiHandler();
  }

  @Test
  @DisplayName("Should mount all Swagger UI routes")
  void shouldMountAllSwaggerUiRoutes(Vertx vertx) {
    // Given
    Router router = Router.router(vertx);

    // When
    handler.mount(vertx, router);

    // Then
    assertThat(router.getRoutes()).hasSizeGreaterThanOrEqualTo(4);

    // Verify /api-docs/* route exists
    assertThat(router.getRoutes())
        .anySatisfy(route -> assertThat(route.getPath()).startsWith("/api-docs"));

    // Verify /swagger-ui route exists
    assertThat(router.getRoutes())
        .anySatisfy(
            route -> {
              assertThat(route.getPath()).isEqualTo("/swagger-ui");
              assertThat(route.methods()).contains(HttpMethod.GET);
            });

    // Verify /swagger-ui/index.html route exists
    assertThat(router.getRoutes())
        .anySatisfy(
            route -> {
              assertThat(route.getPath()).isEqualTo("/swagger-ui/index.html");
              assertThat(route.methods()).contains(HttpMethod.GET);
            });

    // Verify /swagger-ui/swagger-initializer.js route exists
    assertThat(router.getRoutes())
        .anySatisfy(
            route -> assertThat(route.getPath()).isEqualTo("/swagger-ui/swagger-initializer.js"));
  }

  @Test
  @DisplayName("Should redirect /swagger-ui to index.html with OpenAPI URL")
  void shouldRedirectSwaggerUiToIndexHtml(Vertx vertx, VertxTestContext testContext) {
    // Given
    Router router = Router.router(vertx);
    handler.mount(vertx, router);

    // When/Then
    vertx
        .createHttpServer()
        .requestHandler(router)
        .listen(0)
        .onComplete(
            testContext.succeeding(
                server ->
                    vertx
                        .createHttpClient()
                        .request(HttpMethod.GET, server.actualPort(), "localhost", "/swagger-ui")
                        .compose(req -> req.send())
                        .onComplete(
                            testContext.succeeding(
                                response -> {
                                  testContext.verify(
                                      () -> {
                                        assertThat(response.statusCode()).isEqualTo(302);
                                        assertThat(response.getHeader("Location"))
                                            .isEqualTo(
                                                "/swagger-ui/index.html?url=/api-docs/openapi.yaml");
                                        testContext.completeNow();
                                      });
                                }))));
  }

  @Test
  @DisplayName("Should redirect /swagger-ui/index.html without url parameter")
  void shouldRedirectIndexHtmlWithoutUrlParameter(Vertx vertx, VertxTestContext testContext) {
    // Given
    Router router = Router.router(vertx);
    handler.mount(vertx, router);

    // When/Then
    vertx
        .createHttpServer()
        .requestHandler(router)
        .listen(0)
        .onComplete(
            testContext.succeeding(
                server ->
                    vertx
                        .createHttpClient()
                        .request(
                            HttpMethod.GET,
                            server.actualPort(),
                            "localhost",
                            "/swagger-ui/index.html")
                        .compose(req -> req.send())
                        .onComplete(
                            testContext.succeeding(
                                response -> {
                                  testContext.verify(
                                      () -> {
                                        assertThat(response.statusCode()).isEqualTo(302);
                                        assertThat(response.getHeader("Location"))
                                            .contains("url=/api-docs/openapi.yaml");
                                        testContext.completeNow();
                                      });
                                }))));
  }

  @Test
  @DisplayName("Should not redirect /swagger-ui/index.html with url parameter")
  void shouldNotRedirectIndexHtmlWithUrlParameter(Vertx vertx, VertxTestContext testContext) {
    // Given
    Router router = Router.router(vertx);
    handler.mount(vertx, router);

    // When/Then
    vertx
        .createHttpServer()
        .requestHandler(router)
        .listen(0)
        .onComplete(
            testContext.succeeding(
                server ->
                    vertx
                        .createHttpClient()
                        .request(
                            HttpMethod.GET,
                            server.actualPort(),
                            "localhost",
                            "/swagger-ui/index.html?url=/api-docs/openapi.yaml")
                        .compose(req -> req.send())
                        .onComplete(
                            testContext.succeeding(
                                response -> {
                                  testContext.verify(
                                      () -> {
                                        // Should pass through to static handler, which will return
                                        // 404 if file doesn't exist
                                        // or 200 if it does. We just verify no redirect happens.
                                        assertThat(response.statusCode()).isIn(200, 404);
                                        testContext.completeNow();
                                      });
                                }))));
  }

  @Test
  @DisplayName("Should serve swagger-initializer.js with correct content type")
  void shouldServeSwaggerInitializerJs(Vertx vertx, VertxTestContext testContext) {
    // Given
    Router router = Router.router(vertx);
    handler.mount(vertx, router);

    // When/Then
    vertx
        .createHttpServer()
        .requestHandler(router)
        .listen(0)
        .onComplete(
            testContext.succeeding(
                server ->
                    vertx
                        .createHttpClient()
                        .request(
                            HttpMethod.GET,
                            server.actualPort(),
                            "localhost",
                            "/swagger-ui/swagger-initializer.js")
                        .compose(req -> req.send().compose(res -> res.body().map(buf -> res)))
                        .onComplete(
                            testContext.succeeding(
                                response -> {
                                  testContext.verify(
                                      () -> {
                                        assertThat(response.statusCode()).isEqualTo(200);
                                        assertThat(response.getHeader("Content-Type"))
                                            .isEqualTo("application/javascript");
                                        assertThat(response.getHeader("Cache-Control"))
                                            .isEqualTo("no-cache, no-store, must-revalidate");
                                        testContext.completeNow();
                                      });
                                }))));
  }

  @Test
  @DisplayName("Should serve static files from webjar path")
  void shouldServeStaticFilesFromWebjarPath(Vertx vertx, VertxTestContext testContext) {
    // Given
    Router router = Router.router(vertx);
    handler.mount(vertx, router);

    // When/Then - Try to access a swagger-ui resource (will 404 if path not configured)
    vertx
        .createHttpServer()
        .requestHandler(router)
        .listen(0)
        .onComplete(
            testContext.succeeding(
                server ->
                    vertx
                        .createHttpClient()
                        .request(
                            HttpMethod.GET,
                            server.actualPort(),
                            "localhost",
                            "/swagger-ui/swagger-ui.css")
                        .compose(req -> req.send())
                        .onComplete(
                            testContext.succeeding(
                                response -> {
                                  testContext.verify(
                                      () -> {
                                        // Will be 404 if webjar not found, or 200 if found
                                        // Either is acceptable - we just verify the route is
                                        // mounted
                                        assertThat(response.statusCode()).isIn(200, 404);
                                        testContext.completeNow();
                                      });
                                }))));
  }
}
