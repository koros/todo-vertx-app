package com.example.todo.todo.api;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.openapi.router.RouterBuilder;
import io.vertx.openapi.contract.OpenAPIContract;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class OpenApiRouterFactory {
  private final Vertx vertx;
  private final TodoRoutes todo;

  @Inject
  public OpenApiRouterFactory(Vertx vertx, TodoRoutes todo) {
    this.vertx = vertx;
    this.todo = todo;
  }

  public Router buildFrom(String specPath) {
    // Load & resolve the contract, then build RouterBuilder from it
    return OpenAPIContract.from(vertx, specPath)
        .map(
            contract -> {
              RouterBuilder rb = RouterBuilder.create(vertx, contract);
              // bind operationIds to existing handlers
              rb.getRoute("listTodos").addHandler(todo::list);
              rb.getRoute("createTodo").addHandler(todo::create);
              rb.getRoute("getTodo").addHandler(todo::getById);
              return rb.createRouter();
            })
        .toCompletionStage()
        .toCompletableFuture()
        .join();
  }
}
