package com.example.todo.app;

import com.example.todo.todo.api.SwaggerUiHandler;
import com.example.todo.todo.api.TodoRoutes;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

@Singleton
public class MainVerticle extends AbstractVerticle {
  private final Router router;
  private final TodoRoutes routes;
  private final SwaggerUiHandler swaggerUi;

  @Inject
  public MainVerticle(@Named("root") Router router, TodoRoutes routes, SwaggerUiHandler swaggerUi) {
    this.router = router;
    this.routes = routes;
    this.swaggerUi = swaggerUi;
  }

  @Override
  public void start(Promise<Void> startPromise) {
    // Mount Swagger UI and OpenAPI static routes (from webroot + webjar)
    swaggerUi.mount(vertx, router);

    routes.mount(router);
    vertx
        .createHttpServer()
        .requestHandler(router)
        .listen(8080)
        .<Void>mapEmpty()
        .onComplete(startPromise);
  }
}
