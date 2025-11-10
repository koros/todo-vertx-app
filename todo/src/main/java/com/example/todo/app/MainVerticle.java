package com.example.todo.app;

import com.example.todo.todo.api.TodoRoutes;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MainVerticle extends AbstractVerticle {
  private final Router router;
  private final TodoRoutes routes;

  @Inject
  public MainVerticle(Router router, TodoRoutes routes) {
    this.router = router;
    this.routes = routes;
  }

  @Override
  public void start(Promise<Void> startPromise) {
    routes.mount(router);
    vertx
        .createHttpServer()
        .requestHandler(router)
        .listen(8080)
        .<Void>mapEmpty()
        .onComplete(startPromise);
  }
}
