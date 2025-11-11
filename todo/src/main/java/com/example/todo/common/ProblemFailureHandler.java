package com.example.todo.common;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;
import jakarta.validation.ConstraintViolationException;
import java.net.URI;
import java.util.NoSuchElementException;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

public class ProblemFailureHandler implements Handler<RoutingContext> {

  @Override
  public void handle(RoutingContext ctx) {
    Throwable failure = ctx.failure();
    int status = ctx.statusCode() > 0 ? ctx.statusCode() : 500;

    // Heuristics: map exception types → HTTP code
    if (failure instanceof IllegalArgumentException) {
      status = 400;
    } else if (failure instanceof NoSuchElementException) {
      status = 404;
    } else if (failure instanceof ConstraintViolationException) {
      status = 422; // Unprocessable Entity
    }

    Problem problem = toProblem(failure, status, ctx);
    HttpServerResponse res = ctx.response();
    if (!res.ended()) {
      res.setStatusCode(status)
          .putHeader(HttpHeaders.CONTENT_TYPE, "application/problem+json")
          .end(Json.encode(problem));
    }
  }

  private Problem toProblem(Throwable failure, int status, RoutingContext ctx) {
    Status s = Status.valueOf(status); // Will default to 500 if unknown
    String title = s.getReasonPhrase();
    String detail =
        failure != null && failure.getMessage() != null
            ? failure.getMessage()
            : "An unexpected error occurred";

    // Optionally add instance/type for better traceability
    return Problem.builder()
        .withType(URI.create("about:blank"))
        .withTitle(title)
        .withStatus(s)
        .withDetail(detail)
        .with("path", ctx.normalizedPath())
        .build();
  }
}
