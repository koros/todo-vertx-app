package com.example.todo.todo.api;

import com.example.todo.todo.dto.CreateTodoRequest;
import com.example.todo.todo.service.TodoService;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TodoRoutes {
  private final TodoService service;

  @Inject
  public TodoRoutes(TodoService service) {
    this.service = service;
  }

  public void mount(Router r) {
    r.get("/todos").handler(this::list);
    r.get("/todos/:id").handler(this::getById);
    r.post("/todos").handler(this::create);
  }

  protected void list(RoutingContext ctx) {
    service
        .list()
        .subscribe()
        .with(
            items ->
                ctx.response()
                    .putHeader("Content-Type", "application/json")
                    .end(Json.encode(items)),
            err -> ctx.fail(err));
  }

  protected void create(RoutingContext ctx) {
    var req = ctx.body().asJsonObject().mapTo(CreateTodoRequest.class);
    service
        .create(req)
        .subscribe()
        .with(
            item ->
                ctx.response()
                    .setStatusCode(201)
                    .putHeader("Content-Type", "application/json")
                    .end(Json.encode(item)),
            err -> ctx.fail(err));
  }

  protected void getById(RoutingContext ctx) {
    try {
      var id = java.util.UUID.fromString(ctx.pathParam("id"));
      service
          .findById(id)
          .subscribe()
          .with(
              item -> {
                if (item == null) {
                  ctx.fail(404, new java.util.NoSuchElementException("Todo not found: " + id));
                  return;
                }
                ctx.response()
                    .putHeader("Content-Type", "application/json")
                    .end(io.vertx.core.json.Json.encode(item));
              },
              ctx::fail);
    } catch (IllegalArgumentException e) {
      ctx.fail(400, e);
    }
  }
}
