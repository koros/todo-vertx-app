package com.example.todo.todo.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.todo.todo.domain.TodoItem;
import com.example.todo.todo.dto.CreateTodoRequest;
import com.example.todo.todo.service.TodoService;
import io.smallrye.mutiny.Uni;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith({VertxExtension.class, MockitoExtension.class})
@DisplayName("TodoRoutes Tests")
class TodoRoutesTest {

  @Mock private TodoService service;

  private TodoRoutes routes;

  @BeforeEach
  void setUp() {
    routes = new TodoRoutes(service);
  }

  @Test
  @DisplayName("Should mount all routes correctly")
  void shouldMountAllRoutes(Vertx vertx) {
    // Given
    Router router = Router.router(vertx);

    // When
    routes.mount(router);

    // Then
    assertThat(router.getRoutes()).hasSize(3);
    assertThat(router.getRoutes())
        .anySatisfy(
            route -> {
              assertThat(route.getPath()).isEqualTo("/todos");
              assertThat(route.methods()).contains(io.vertx.core.http.HttpMethod.GET);
            });
    assertThat(router.getRoutes())
        .anySatisfy(
            route -> {
              assertThat(route.getPath()).isEqualTo("/todos/:id");
              assertThat(route.methods()).contains(io.vertx.core.http.HttpMethod.GET);
            });
    assertThat(router.getRoutes())
        .anySatisfy(
            route -> {
              assertThat(route.getPath()).isEqualTo("/todos");
              assertThat(route.methods()).contains(io.vertx.core.http.HttpMethod.POST);
            });
  }

  @Test
  @DisplayName("Should list todos successfully")
  void shouldListTodos(Vertx vertx, VertxTestContext testContext) {
    // Given
    TodoItem item1 =
        TodoItem.builder()
            .id(UUID.randomUUID())
            .title("Todo 1")
            .completed(false)
            .createdAt(Instant.now())
            .build();

    TodoItem item2 =
        TodoItem.builder()
            .id(UUID.randomUUID())
            .title("Todo 2")
            .completed(true)
            .createdAt(Instant.now())
            .build();

    when(service.list()).thenReturn(Uni.createFrom().item(List.of(item1, item2)));

    Router router = Router.router(vertx);
    routes.mount(router);

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
                            io.vertx.core.http.HttpMethod.GET,
                            server.actualPort(),
                            "localhost",
                            "/todos")
                        .compose(
                            req -> req.send().compose(io.vertx.core.http.HttpClientResponse::body))
                        .onComplete(
                            testContext.succeeding(
                                body -> {
                                  testContext.verify(
                                      () -> {
                                        List<?> todos = body.toJsonArray().getList();
                                        assertThat(todos).hasSize(2);
                                        verify(service).list();
                                        testContext.completeNow();
                                      });
                                }))));
  }

  @Test
  @DisplayName("Should create todo successfully")
  void shouldCreateTodo(Vertx vertx, VertxTestContext testContext) {
    // Given
    UUID newId = UUID.randomUUID();
    TodoItem created =
        TodoItem.builder()
            .id(newId)
            .title("New Todo")
            .completed(false)
            .createdAt(Instant.now())
            .build();

    when(service.create(any())).thenReturn(Uni.createFrom().item(created));

    Router router = Router.router(vertx);
    router.route().handler(io.vertx.ext.web.handler.BodyHandler.create());
    routes.mount(router);

    JsonObject requestBody = new JsonObject().put("title", "New Todo");

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
                            io.vertx.core.http.HttpMethod.POST,
                            server.actualPort(),
                            "localhost",
                            "/todos")
                        .compose(
                            req ->
                                req.putHeader("Content-Type", "application/json")
                                    .send(requestBody.encode())
                                    .compose(io.vertx.core.http.HttpClientResponse::body))
                        .onComplete(
                            testContext.succeeding(
                                body -> {
                                  testContext.verify(
                                      () -> {
                                        JsonObject todo = body.toJsonObject();
                                        assertThat(todo.getString("id"))
                                            .isEqualTo(newId.toString());
                                        assertThat(todo.getString("title")).isEqualTo("New Todo");
                                        verify(service).create(any(CreateTodoRequest.class));
                                        testContext.completeNow();
                                      });
                                }))));
  }

  @Test
  @DisplayName("Should get todo by id successfully")
  void shouldGetTodoById(Vertx vertx, VertxTestContext testContext) {
    // Given
    UUID id = UUID.randomUUID();
    TodoItem item =
        TodoItem.builder()
            .id(id)
            .title("Found Todo")
            .completed(false)
            .createdAt(Instant.now())
            .build();

    when(service.findById(id)).thenReturn(Uni.createFrom().item(item));

    Router router = Router.router(vertx);
    routes.mount(router);

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
                            io.vertx.core.http.HttpMethod.GET,
                            server.actualPort(),
                            "localhost",
                            "/todos/" + id)
                        .compose(
                            req -> req.send().compose(io.vertx.core.http.HttpClientResponse::body))
                        .onComplete(
                            testContext.succeeding(
                                body -> {
                                  testContext.verify(
                                      () -> {
                                        JsonObject todo = body.toJsonObject();
                                        assertThat(todo.getString("id")).isEqualTo(id.toString());
                                        assertThat(todo.getString("title")).isEqualTo("Found Todo");
                                        verify(service).findById(id);
                                        testContext.completeNow();
                                      });
                                }))));
  }

  @Test
  @DisplayName("Should return 404 when todo not found")
  void shouldReturn404WhenTodoNotFound(Vertx vertx, VertxTestContext testContext) {
    // Given
    UUID id = UUID.randomUUID();
    when(service.findById(id)).thenReturn(Uni.createFrom().nullItem());

    Router router = Router.router(vertx);
    routes.mount(router);

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
                            io.vertx.core.http.HttpMethod.GET,
                            server.actualPort(),
                            "localhost",
                            "/todos/" + id)
                        .compose(io.vertx.core.http.HttpClientRequest::send)
                        .onComplete(
                            testContext.succeeding(
                                response -> {
                                  testContext.verify(
                                      () -> {
                                        assertThat(response.statusCode()).isEqualTo(404);
                                        verify(service).findById(id);
                                        testContext.completeNow();
                                      });
                                }))));
  }

  @Test
  @DisplayName("Should return 400 for invalid UUID")
  void shouldReturn400ForInvalidUuid(Vertx vertx, VertxTestContext testContext) {
    // Given
    Router router = Router.router(vertx);
    routes.mount(router);

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
                            io.vertx.core.http.HttpMethod.GET,
                            server.actualPort(),
                            "localhost",
                            "/todos/invalid-uuid")
                        .compose(io.vertx.core.http.HttpClientRequest::send)
                        .onComplete(
                            testContext.succeeding(
                                response -> {
                                  testContext.verify(
                                      () -> {
                                        assertThat(response.statusCode()).isEqualTo(400);
                                        testContext.completeNow();
                                      });
                                }))));
  }

  @Test
  @DisplayName("Should handle service error on list")
  void shouldHandleServiceErrorOnList(Vertx vertx, VertxTestContext testContext) {
    // Given
    when(service.list()).thenReturn(Uni.createFrom().failure(new RuntimeException("DB Error")));

    Router router = Router.router(vertx);
    routes.mount(router);

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
                            io.vertx.core.http.HttpMethod.GET,
                            server.actualPort(),
                            "localhost",
                            "/todos")
                        .compose(io.vertx.core.http.HttpClientRequest::send)
                        .onComplete(
                            testContext.succeeding(
                                response -> {
                                  testContext.verify(
                                      () -> {
                                        assertThat(response.statusCode()).isEqualTo(500);
                                        testContext.completeNow();
                                      });
                                }))));
  }
}
