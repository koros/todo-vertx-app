package com.example.todo.common;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;
import jakarta.validation.ConstraintViolationException;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("ProblemFailureHandler Tests")
class ProblemFailureHandlerTest {

  @Mock private RoutingContext context;
  @Mock private HttpServerResponse response;

  private ProblemFailureHandler handler;

  @BeforeEach
  void setUp() {
    handler = new ProblemFailureHandler();
    lenient().when(context.response()).thenReturn(response);
    lenient().when(response.putHeader(any(CharSequence.class), anyString())).thenReturn(response);
    lenient().when(response.setStatusCode(anyInt())).thenReturn(response);
    lenient().when(response.ended()).thenReturn(false);
    lenient().when(context.normalizedPath()).thenReturn("/test/path");
  }

  @Test
  @DisplayName("Should handle IllegalArgumentException with 400 status")
  void shouldHandleIllegalArgumentExceptionWith400() {
    // Given
    IllegalArgumentException exception = new IllegalArgumentException("Invalid parameter");
    when(context.failure()).thenReturn(exception);
    when(context.statusCode()).thenReturn(-1);

    ArgumentCaptor<String> bodyCaptor = ArgumentCaptor.forClass(String.class);

    // When
    handler.handle(context);

    // Then
    verify(response).setStatusCode(400);
    verify(response).putHeader(HttpHeaders.CONTENT_TYPE, "application/problem+json");
    verify(response).end(bodyCaptor.capture());

    String body = bodyCaptor.getValue();
    assertThat(body).contains("\"status\":\"BAD_REQUEST\"");
    assertThat(body).contains("Invalid parameter");
    assertThat(body).contains("/test/path");
  }

  @Test
  @DisplayName("Should handle NoSuchElementException with 404 status")
  void shouldHandleNoSuchElementExceptionWith404() {
    // Given
    NoSuchElementException exception = new NoSuchElementException("Resource not found");
    when(context.failure()).thenReturn(exception);
    when(context.statusCode()).thenReturn(-1);

    ArgumentCaptor<String> bodyCaptor = ArgumentCaptor.forClass(String.class);

    // When
    handler.handle(context);

    // Then
    verify(response).setStatusCode(404);
    verify(response).putHeader(HttpHeaders.CONTENT_TYPE, "application/problem+json");
    verify(response).end(bodyCaptor.capture());

    String body = bodyCaptor.getValue();
    assertThat(body).contains("\"status\":\"NOT_FOUND\"");
    assertThat(body).contains("Resource not found");
  }

  @Test
  @DisplayName("Should handle ConstraintViolationException with 422 status")
  void shouldHandleConstraintViolationExceptionWith422() {
    // Given
    ConstraintViolationException exception = mock(ConstraintViolationException.class);
    when(exception.getMessage()).thenReturn("Validation failed");
    when(context.failure()).thenReturn(exception);
    when(context.statusCode()).thenReturn(-1);

    ArgumentCaptor<String> bodyCaptor = ArgumentCaptor.forClass(String.class);

    // When
    handler.handle(context);

    // Then
    verify(response).setStatusCode(422);
    verify(response).putHeader(HttpHeaders.CONTENT_TYPE, "application/problem+json");
    verify(response).end(bodyCaptor.capture());

    String body = bodyCaptor.getValue();
    assertThat(body).contains("\"status\":\"UNPROCESSABLE_ENTITY\"");
    assertThat(body).contains("Validation failed");
  }

  @Test
  @DisplayName("Should handle generic exception with 500 status")
  void shouldHandleGenericExceptionWith500() {
    // Given
    RuntimeException exception = new RuntimeException("Internal error");
    when(context.failure()).thenReturn(exception);
    when(context.statusCode()).thenReturn(-1);

    ArgumentCaptor<String> bodyCaptor = ArgumentCaptor.forClass(String.class);

    // When
    handler.handle(context);

    // Then
    verify(response).setStatusCode(500);
    verify(response).putHeader(HttpHeaders.CONTENT_TYPE, "application/problem+json");
    verify(response).end(bodyCaptor.capture());

    String body = bodyCaptor.getValue();
    assertThat(body).contains("\"status\":\"INTERNAL_SERVER_ERROR\"");
    assertThat(body).contains("Internal error");
  }

  @Test
  @DisplayName("Should use existing status code when provided")
  void shouldUseExistingStatusCode() {
    // Given
    RuntimeException exception = new RuntimeException("Error");
    when(context.failure()).thenReturn(exception);
    when(context.statusCode()).thenReturn(503);

    ArgumentCaptor<String> bodyCaptor = ArgumentCaptor.forClass(String.class);

    // When
    handler.handle(context);

    // Then
    verify(response).setStatusCode(503);
    verify(response).end(bodyCaptor.capture());

    String body = bodyCaptor.getValue();
    assertThat(body).contains("\"status\":\"SERVICE_UNAVAILABLE\"");
  }

  @Test
  @DisplayName("Should handle null failure message")
  void shouldHandleNullFailureMessage() {
    // Given
    RuntimeException exception = new RuntimeException();
    when(context.failure()).thenReturn(exception);
    when(context.statusCode()).thenReturn(-1);

    ArgumentCaptor<String> bodyCaptor = ArgumentCaptor.forClass(String.class);

    // When
    handler.handle(context);

    // Then
    verify(response).setStatusCode(500);
    verify(response).end(bodyCaptor.capture());

    String body = bodyCaptor.getValue();
    assertThat(body).contains("An unexpected error occurred");
  }

  @Test
  @DisplayName("Should not write response if already ended")
  void shouldNotWriteResponseIfAlreadyEnded() {
    // Given
    RuntimeException exception = new RuntimeException("Error");
    when(context.failure()).thenReturn(exception);
    when(context.statusCode()).thenReturn(500);
    when(response.ended()).thenReturn(true);

    // When
    handler.handle(context);

    // Then
    verify(response, never()).setStatusCode(anyInt());
    verify(response, never()).end(anyString());
  }

  @Test
  @DisplayName("Should include path in problem details")
  void shouldIncludePathInProblemDetails() {
    // Given
    RuntimeException exception = new RuntimeException("Error");
    when(context.failure()).thenReturn(exception);
    when(context.statusCode()).thenReturn(500);
    when(context.normalizedPath()).thenReturn("/api/todos/123");

    ArgumentCaptor<String> bodyCaptor = ArgumentCaptor.forClass(String.class);

    // When
    handler.handle(context);

    // Then
    verify(response).end(bodyCaptor.capture());

    String body = bodyCaptor.getValue();
    assertThat(body).contains("\"path\":\"/api/todos/123\"");
  }

  @Test
  @DisplayName("Should include about:blank type in problem details")
  void shouldIncludeAboutBlankType() {
    // Given
    RuntimeException exception = new RuntimeException("Error");
    when(context.failure()).thenReturn(exception);
    when(context.statusCode()).thenReturn(500);

    ArgumentCaptor<String> bodyCaptor = ArgumentCaptor.forClass(String.class);

    // When
    handler.handle(context);

    // Then
    verify(response).end(bodyCaptor.capture());

    String body = bodyCaptor.getValue();
    assertThat(body).contains("\"type\":\"about:blank\"");
  }

  @Test
  @DisplayName("Should set correct Content-Type header")
  void shouldSetCorrectContentTypeHeader() {
    // Given
    RuntimeException exception = new RuntimeException("Error");
    when(context.failure()).thenReturn(exception);
    when(context.statusCode()).thenReturn(500);

    // When
    handler.handle(context);

    // Then
    verify(response).putHeader(HttpHeaders.CONTENT_TYPE, "application/problem+json");
  }
}
