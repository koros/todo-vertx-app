package com.example.todo.todo.repo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.example.todo.todo.domain.TodoItem;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import org.hibernate.reactive.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("TodoRepositoryHr Tests")
class TodoRepositoryHrTest {

  @Mock(lenient = true)
  private Stage.SessionFactory sessionFactory;

  private TodoRepositoryHr repository;

  @BeforeEach
  void setUp() {
    repository = new TodoRepositoryHr(sessionFactory);
  }

  @Test
  @DisplayName("Should find all todos")
  void shouldFindAllTodos() {
    // Given
    TodoItem item1 =
        TodoItem.builder()
            .id(UUID.randomUUID())
            .title("Todo 1")
            .completed(false)
            .createdAt(Instant.now())
            .build();

    TodoItem item2 =
        TodoItem.builder()
            .id(UUID.randomUUID())
            .title("Todo 2")
            .completed(true)
            .createdAt(Instant.now())
            .build();

    List<TodoItem> todos = List.of(item1, item2);

    when(sessionFactory.withSession(any())).thenReturn(CompletableFuture.completedFuture(todos));

    // When
    List<TodoItem> result = repository.findAll().await().indefinitely();

    // Then
    assertThat(result).hasSize(2);
    assertThat(result).containsExactly(item1, item2);
    verify(sessionFactory).withSession(any());
  }

  @Test
  @DisplayName("Should find todo by id")
  void shouldFindTodoById() {
    // Given
    UUID id = UUID.randomUUID();
    TodoItem item =
        TodoItem.builder()
            .id(id)
            .title("Found Todo")
            .completed(false)
            .createdAt(Instant.now())
            .build();

    when(sessionFactory.withSession(any())).thenReturn(CompletableFuture.completedFuture(item));

    // When
    TodoItem result = repository.findById(id).await().indefinitely();

    // Then
    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo(id);
    assertThat(result.getTitle()).isEqualTo("Found Todo");
    verify(sessionFactory).withSession(any());
  }

  @Test
  @DisplayName("Should return null when todo not found by id")
  void shouldReturnNullWhenTodoNotFoundById() {
    // Given
    UUID id = UUID.randomUUID();

    when(sessionFactory.withSession(any())).thenReturn(CompletableFuture.completedFuture(null));

    // When
    TodoItem result = repository.findById(id).await().indefinitely();

    // Then
    assertThat(result).isNull();
    verify(sessionFactory).withSession(any());
  }

  @Test
  @DisplayName("Should return empty list when no todos exist")
  void shouldReturnEmptyListWhenNoTodosExist() {
    // Given
    when(sessionFactory.withSession(any()))
        .thenReturn(CompletableFuture.completedFuture(List.of()));

    // When
    List<TodoItem> result = repository.findAll().await().indefinitely();

    // Then
    assertThat(result).isEmpty();
    verify(sessionFactory).withSession(any());
  }
}
