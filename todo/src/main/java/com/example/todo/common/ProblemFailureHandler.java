package com.example.todo.common;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpHeaders;
import io.vertx.ext.web.RoutingContext;
import java.net.URI;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import org.zalando.problem.ThrowableProblem;

public class ProblemFailureHandler implements Handler<RoutingContext> {
  @Override
  public void handle(RoutingContext ctx) {
    int status = ctx.statusCode() > 0 ? ctx.statusCode() : 500;
    Throwable failure = ctx.failure();
    Problem problem;

    if (failure instanceof ThrowableProblem tp) {
      problem = tp;
      if (tp.getStatus() != null) status = tp.getStatus().getStatusCode();
    } else if (failure != null) {
      problem =
          Problem.builder()
              .withType(URI.create("about:blank"))
              .withTitle(Status.valueOf(status).getReasonPhrase())
              .withStatus(Status.valueOf(status))
              .withDetail(failure.getMessage())
              .with("errorClass", failure.getClass().getName())
              .build();
    } else {
      problem =
          Problem.builder()
              .withType(URI.create("about:blank"))
              .withTitle(Status.INTERNAL_SERVER_ERROR.getReasonPhrase())
              .withStatus(Status.INTERNAL_SERVER_ERROR)
              .withDetail("Unexpected error")
              .build();
      status = 500;
    }

    String json = io.vertx.core.json.jackson.DatabindCodec.mapper().valueToTree(problem).toString();

    ctx.response()
        .setStatusCode(status)
        .putHeader(HttpHeaders.CONTENT_TYPE, "application/problem+json")
        .end(json);
  }
}
