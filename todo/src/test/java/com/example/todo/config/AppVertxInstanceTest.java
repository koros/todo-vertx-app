package com.example.todo.config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

import io.vertx.core.Vertx;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("AppVertxInstance Tests")
class AppVertxInstanceTest {

  private Vertx originalVertx;

  @BeforeEach
  void setUp() {
    // Save original state
    originalVertx = AppVertxInstance.VERTX;
  }

  @AfterEach
  void tearDown() {
    // Restore original state
    AppVertxInstance.VERTX = originalVertx;
  }

  @Test
  @DisplayName("Should throw exception when VERTX is not initialized")
  void shouldThrowExceptionWhenVertxNotInitialized() {
    // Given
    AppVertxInstance.VERTX = null;
    AppVertxInstance instance = new AppVertxInstance();

    // When/Then
    assertThatThrownBy(instance::getVertx)
        .isInstanceOf(IllegalStateException.class)
        .hasMessage("AppVertxInstance.VERTX not initialized");
  }

  @Test
  @DisplayName("Should return Vertx instance when initialized")
  void shouldReturnVertxInstanceWhenInitialized() {
    // Given
    Vertx vertx = mock(Vertx.class);
    AppVertxInstance.VERTX = vertx;
    AppVertxInstance instance = new AppVertxInstance();

    // When
    Vertx result = instance.getVertx();

    // Then
    assertThat(result).isSameAs(vertx);
  }

  @Test
  @DisplayName("Should allow setting VERTX instance")
  void shouldAllowSettingVertxInstance() {
    // Given
    Vertx vertx = mock(Vertx.class);

    // When
    AppVertxInstance.VERTX = vertx;

    // Then
    assertThat(AppVertxInstance.VERTX).isSameAs(vertx);
  }

  @Test
  @DisplayName("Should support multiple instances accessing same VERTX")
  void shouldSupportMultipleInstancesAccessingSameVertx() {
    // Given
    Vertx vertx = mock(Vertx.class);
    AppVertxInstance.VERTX = vertx;
    AppVertxInstance instance1 = new AppVertxInstance();
    AppVertxInstance instance2 = new AppVertxInstance();

    // When
    Vertx result1 = instance1.getVertx();
    Vertx result2 = instance2.getVertx();

    // Then
    assertThat(result1).isSameAs(result2);
    assertThat(result1).isSameAs(vertx);
  }

  @Test
  @DisplayName("Should allow overwriting VERTX instance")
  void shouldAllowOverwritingVertxInstance() {
    // Given
    Vertx vertx1 = mock(Vertx.class);
    Vertx vertx2 = mock(Vertx.class);
    AppVertxInstance instance = new AppVertxInstance();

    // When
    AppVertxInstance.VERTX = vertx1;
    Vertx result1 = instance.getVertx();

    AppVertxInstance.VERTX = vertx2;
    Vertx result2 = instance.getVertx();

    // Then
    assertThat(result1).isSameAs(vertx1);
    assertThat(result2).isSameAs(vertx2);
    assertThat(result1).isNotSameAs(result2);
  }
}
