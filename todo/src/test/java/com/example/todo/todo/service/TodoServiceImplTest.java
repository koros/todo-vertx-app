package com.example.todo.todo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.todo.todo.domain.TodoItem;
import com.example.todo.todo.dto.CreateTodoRequest;
import com.example.todo.todo.mapper.TodoMapper;
import com.example.todo.todo.repo.TodoRepository;
import io.smallrye.mutiny.Uni;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("TodoServiceImpl Tests")
class TodoServiceImplTest {

  @Mock private TodoRepository repository;
  @Mock private TodoMapper mapper;

  private TodoServiceImpl service;

  @BeforeEach
  void setUp() {
    service = new TodoServiceImpl(repository, mapper);
  }

  @Test
  @DisplayName("Should create todo successfully")
  void shouldCreateTodo() {
    // Given
    CreateTodoRequest request = new CreateTodoRequest();
    request.setTitle("Test Todo");

    TodoItem entity =
        TodoItem.builder().title("Test Todo").completed(false).createdAt(Instant.now()).build();

    TodoItem savedEntity =
        TodoItem.builder()
            .id(UUID.randomUUID())
            .title("Test Todo")
            .completed(false)
            .createdAt(Instant.now())
            .build();

    when(mapper.toEntity(request)).thenReturn(entity);
    when(repository.create(entity)).thenReturn(Uni.createFrom().item(savedEntity));

    // When
    TodoItem result = service.create(request).await().indefinitely();

    // Then
    assertThat(result).isNotNull();
    assertThat(result.getId()).isNotNull();
    assertThat(result.getTitle()).isEqualTo("Test Todo");
    verify(mapper).toEntity(request);
    verify(repository).create(entity);
  }

  @Test
  @DisplayName("Should list all todos")
  void shouldListAllTodos() {
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

    when(repository.findAll()).thenReturn(Uni.createFrom().item(List.of(item1, item2)));

    // When
    List<TodoItem> result = service.list().await().indefinitely();

    // Then
    assertThat(result).hasSize(2);
    assertThat(result).extracting(TodoItem::getTitle).containsExactly("Todo 1", "Todo 2");
    verify(repository).findAll();
  }

  @Test
  @DisplayName("Should return empty list when no todos exist")
  void shouldReturnEmptyListWhenNoTodos() {
    // Given
    when(repository.findAll()).thenReturn(Uni.createFrom().item(List.of()));

    // When
    List<TodoItem> result = service.list().await().indefinitely();

    // Then
    assertThat(result).isEmpty();
    verify(repository).findAll();
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

    when(repository.findById(id)).thenReturn(Uni.createFrom().item(item));

    // When
    TodoItem result = service.findById(id).await().indefinitely();

    // Then
    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo(id);
    assertThat(result.getTitle()).isEqualTo("Found Todo");
    verify(repository).findById(id);
  }

  @Test
  @DisplayName("Should return null when todo not found")
  void shouldReturnNullWhenTodoNotFound() {
    // Given
    UUID id = UUID.randomUUID();
    when(repository.findById(id)).thenReturn(Uni.createFrom().nullItem());

    // When
    TodoItem result = service.findById(id).await().indefinitely();

    // Then
    assertThat(result).isNull();
    verify(repository).findById(id);
  }

  @Test
  @DisplayName("Should propagate repository errors on create")
  void shouldPropagateRepositoryErrorsOnCreate() {
    // Given
    CreateTodoRequest request = new CreateTodoRequest();
    request.setTitle("Test");

    TodoItem entity = TodoItem.builder().title("Test").build();
    when(mapper.toEntity(request)).thenReturn(entity);
    when(repository.create(any()))
        .thenReturn(Uni.createFrom().failure(new RuntimeException("DB Error")));

    // When/Then
    service
        .create(request)
        .subscribe()
        .withSubscriber(io.smallrye.mutiny.helpers.test.UniAssertSubscriber.create())
        .assertFailedWith(RuntimeException.class, "DB Error");
  }

  @Test
  @DisplayName("Should propagate repository errors on list")
  void shouldPropagateRepositoryErrorsOnList() {
    // Given
    when(repository.findAll())
        .thenReturn(Uni.createFrom().failure(new RuntimeException("Connection failed")));

    // When/Then
    service
        .list()
        .subscribe()
        .withSubscriber(io.smallrye.mutiny.helpers.test.UniAssertSubscriber.create())
        .assertFailedWith(RuntimeException.class, "Connection failed");
  }

  @Test
  @DisplayName("Should propagate repository errors on findById")
  void shouldPropagateRepositoryErrorsOnFindById() {
    // Given
    UUID id = UUID.randomUUID();
    when(repository.findById(id))
        .thenReturn(Uni.createFrom().failure(new RuntimeException("Query failed")));

    // When/Then
    service
        .findById(id)
        .subscribe()
        .withSubscriber(io.smallrye.mutiny.helpers.test.UniAssertSubscriber.create())
        .assertFailedWith(RuntimeException.class, "Query failed");
  }
}
