package com.example.todo.todo.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.todo.todo.domain.TodoItem;
import com.example.todo.todo.dto.CreateTodoRequest;
import java.time.Instant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

@DisplayName("TodoMapper Tests")
class TodoMapperTest {

  private TodoMapper mapper;

  @BeforeEach
  void setUp() {
    mapper = Mappers.getMapper(TodoMapper.class);
  }

  @Test
  @DisplayName("Should map CreateTodoRequest to TodoItem")
  void shouldMapCreateTodoRequestToTodoItem() {
    // Given
    CreateTodoRequest request = new CreateTodoRequest();
    request.setTitle("Test Todo");

    Instant before = Instant.now();

    // When
    TodoItem item = mapper.toEntity(request);

    Instant after = Instant.now();

    // Then
    assertThat(item).isNotNull();
    assertThat(item.getTitle()).isEqualTo("Test Todo");
    assertThat(item.isCompleted()).isFalse();
    assertThat(item.getId()).isNull(); // ID is ignored
    assertThat(item.getUpdatedAt()).isNull(); // updatedAt is ignored
    assertThat(item.getCreatedAt()).isNotNull();
    assertThat(item.getCreatedAt()).isBetween(before.minusSeconds(1), after.plusSeconds(1));
  }

  @Test
  @DisplayName("Should set completed to false by default")
  void shouldSetCompletedToFalseByDefault() {
    // Given
    CreateTodoRequest request = new CreateTodoRequest();
    request.setTitle("Another Todo");

    // When
    TodoItem item = mapper.toEntity(request);

    // Then
    assertThat(item.isCompleted()).isFalse();
  }

  @Test
  @DisplayName("Should set createdAt to current timestamp")
  void shouldSetCreatedAtToCurrentTimestamp() {
    // Given
    CreateTodoRequest request = new CreateTodoRequest();
    request.setTitle("Timestamp Test");

    Instant before = Instant.now().minusSeconds(1);

    // When
    TodoItem item = mapper.toEntity(request);

    Instant after = Instant.now().plusSeconds(1);

    // Then
    assertThat(item.getCreatedAt()).isNotNull();
    assertThat(item.getCreatedAt()).isAfter(before);
    assertThat(item.getCreatedAt()).isBefore(after);
  }

  @Test
  @DisplayName("Should ignore ID field during mapping")
  void shouldIgnoreIdFieldDuringMapping() {
    // Given
    CreateTodoRequest request = new CreateTodoRequest();
    request.setTitle("ID Test");

    // When
    TodoItem item = mapper.toEntity(request);

    // Then
    assertThat(item.getId()).isNull();
  }

  @Test
  @DisplayName("Should ignore updatedAt field during mapping")
  void shouldIgnoreUpdatedAtFieldDuringMapping() {
    // Given
    CreateTodoRequest request = new CreateTodoRequest();
    request.setTitle("UpdatedAt Test");

    // When
    TodoItem item = mapper.toEntity(request);

    // Then
    assertThat(item.getUpdatedAt()).isNull();
  }

  @Test
  @DisplayName("Should handle empty title")
  void shouldHandleEmptyTitle() {
    // Given
    CreateTodoRequest request = new CreateTodoRequest();
    request.setTitle("");

    // When
    TodoItem item = mapper.toEntity(request);

    // Then
    assertThat(item.getTitle()).isEmpty();
    assertThat(item.isCompleted()).isFalse();
  }

  @Test
  @DisplayName("Should handle long title")
  void shouldHandleLongTitle() {
    // Given
    CreateTodoRequest request = new CreateTodoRequest();
    String longTitle = "A".repeat(1000);
    request.setTitle(longTitle);

    // When
    TodoItem item = mapper.toEntity(request);

    // Then
    assertThat(item.getTitle()).isEqualTo(longTitle);
  }

  @Test
  @DisplayName("Should handle title with special characters")
  void shouldHandleTitleWithSpecialCharacters() {
    // Given
    CreateTodoRequest request = new CreateTodoRequest();
    request.setTitle("Todo with special chars: @#$%^&*()");

    // When
    TodoItem item = mapper.toEntity(request);

    // Then
    assertThat(item.getTitle()).isEqualTo("Todo with special chars: @#$%^&*()");
  }

  @Test
  @DisplayName("Should preserve unicode characters in title")
  void shouldPreserveUnicodeCharacters() {
    // Given
    CreateTodoRequest request = new CreateTodoRequest();
    request.setTitle("Todo avec des caractères spéciaux: éàü 🚀");

    // When
    TodoItem item = mapper.toEntity(request);

    // Then
    assertThat(item.getTitle()).isEqualTo("Todo avec des caractères spéciaux: éàü 🚀");
  }

  @Test
  @DisplayName("Should create new instance for each mapping")
  void shouldCreateNewInstanceForEachMapping() {
    // Given
    CreateTodoRequest request1 = new CreateTodoRequest();
    request1.setTitle("First");

    CreateTodoRequest request2 = new CreateTodoRequest();
    request2.setTitle("Second");

    // When
    TodoItem item1 = mapper.toEntity(request1);
    TodoItem item2 = mapper.toEntity(request2);

    // Then
    assertThat(item1).isNotSameAs(item2);
    assertThat(item1.getTitle()).isEqualTo("First");
    assertThat(item2.getTitle()).isEqualTo("Second");
  }
}
