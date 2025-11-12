package com.example.todo.todo.api;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DocsRoutes {
  private final Vertx vertx;

  @Inject
  public DocsRoutes(Vertx vertx) {
    this.vertx = vertx;
  }

  // DocsRoutes.java
  // DocsRoutes.java
  public Router create() {
    Router r = Router.router(vertx);

    // /v3/openapi (your spec)
    r.get("/openapi")
        .handler(
            ctx ->
                vertx
                    .fileSystem()
                    .readFile("todo/src/main/resources/openapi.yaml")
                    .onSuccess(
                        buf ->
                            ctx.response().putHeader("content-type", "application/yaml").end(buf))
                    .onFailure(ctx::fail));

    // Serve our own index at /docs (mounted at /docs/* -> path here is "/")
    r.get("/")
        .handler(
            ctx ->
                ctx.response()
                    .putHeader("content-type", "text/html; charset=utf-8")
                    .end(
                        """
<!doctype html>
<html>
  <head>
    <meta charset="utf-8"/>
    <title>API Docs</title>
    <link rel="stylesheet" href="swagger-ui.css"/>
  </head>
  <body>
    <div id="swagger-ui"></div>
    <script src="swagger-ui-bundle.js"></script>
    <script src="swagger-ui-standalone-preset.js"></script>
    <script>
      window.onload = () => {
        SwaggerUIBundle({
          url: '/v3/openapi',
          dom_id: '#swagger-ui',
          presets: [SwaggerUIBundle.presets.apis, SwaggerUIStandalonePreset],
          layout: 'StandaloneLayout'
        });
      };
    </script>
  </body>
</html>
"""));

    // Static assets from the WebJar (MUST be last)
    r.route("/*")
        .handler(
            StaticHandler.create("META-INF/resources/webjars/swagger-ui/5.17.14")
                .setIndexPage("index.html") // not used now, but harmless
            );

    return r;
  }
}
