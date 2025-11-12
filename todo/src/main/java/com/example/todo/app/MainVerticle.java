package com.example.todo.app;

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
  private final Router api;
  private final Router docs;

  @Inject
  public MainVerticle(
      @Named("root") Router router,
      TodoRoutes routes,
      @Named("api") Router api,
      @Named("docs") Router docs) {
    this.router = router;
    this.routes = routes;
    this.api = api;
    this.docs = docs;
  }

  @Override
  public void start(Promise<Void> startPromise) {
    router.route("/api/*").subRouter(api); // was: mountSubRouter("/", api)
    router.route("/docs/*").subRouter(docs); // if your docs router serves /docs and /v3/openapi,
    router.route("/v3/*").subRouter(docs); // mount it under these prefixes too (optional)

    routes.mount(router);
    vertx
        .createHttpServer()
        .requestHandler(router)
        .listen(8080)
        .<Void>mapEmpty()
        .onComplete(startPromise);
  }
}
