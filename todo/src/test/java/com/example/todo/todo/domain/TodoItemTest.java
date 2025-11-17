package com.example.todo.todo.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("TodoItem Tests")
class TodoItemTest {

  @Test
  @DisplayName("Should initialize createdAt in prePersist when null")
  void shouldInitializeCreatedAtInPrePersist() {
    // Given
    TodoItem item = TodoItem.builder().title("Test").completed(false).build();
    assertThat(item.getCreatedAt()).isNull();

    // When
    item.prePersist();

    // Then
    assertThat(item.getCreatedAt()).isNotNull();
    assertThat(item.getCreatedAt()).isBeforeOrEqualTo(Instant.now());
  }

  @Test
  @DisplayName("Should not override existing createdAt in prePersist")
  void shouldNotOverrideExistingCreatedAt() {
    // Given
    Instant existing = Instant.parse("2024-01-01T10:00:00Z");
    TodoItem item = TodoItem.builder().title("Test").completed(false).createdAt(existing).build();

    // When
    item.prePersist();

    // Then
    assertThat(item.getCreatedAt()).isEqualTo(existing);
  }

  @Test
  @DisplayName("Should build TodoItem with all properties")
  void shouldBuildTodoItemWithAllProperties() {
    // Given
    UUID id = UUID.randomUUID();
    Instant createdAt = Instant.now();

    // When
    TodoItem item =
        TodoItem.builder().id(id).title("Test Todo").completed(true).createdAt(createdAt).build();

    // Then
    assertThat(item.getId()).isEqualTo(id);
    assertThat(item.getTitle()).isEqualTo("Test Todo");
    assertThat(item.isCompleted()).isTrue();
    assertThat(item.getCreatedAt()).isEqualTo(createdAt);
  }

  @Test
  @DisplayName("Should set properties using setters")
  void shouldSetPropertiesUsingSetters() {
    // Given
    TodoItem item = new TodoItem();
    UUID id = UUID.randomUUID();
    Instant createdAt = Instant.now();

    // When
    item.setId(id);
    item.setTitle("New Title");
    item.setCompleted(true);
    item.setCreatedAt(createdAt);

    // Then
    assertThat(item.getId()).isEqualTo(id);
    assertThat(item.getTitle()).isEqualTo("New Title");
    assertThat(item.isCompleted()).isTrue();
    assertThat(item.getCreatedAt()).isEqualTo(createdAt);
  }

  @Test
  @DisplayName("Should handle null values")
  void shouldHandleNullValues() {
    // When
    TodoItem item = TodoItem.builder().title(null).completed(false).createdAt(null).build();

    // Then
    assertThat(item.getId()).isNull();
    assertThat(item.getTitle()).isNull();
    assertThat(item.isCompleted()).isFalse();
    assertThat(item.getCreatedAt()).isNull();
  }

  @Test
  @DisplayName("Should default completed to false")
  void shouldDefaultCompletedToFalse() {
    // When
    TodoItem item = TodoItem.builder().title("Test").build();

    // Then
    assertThat(item.isCompleted()).isFalse();
  }
}
