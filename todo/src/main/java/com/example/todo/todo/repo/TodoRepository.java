package com.example.todo.todo.repo;

import com.example.todo.todo.domain.TodoItem;
import io.smallrye.mutiny.Uni;
import java.util.List;
import java.util.UUID;

public interface TodoRepository {
  Uni<TodoItem> create(TodoItem item);

  Uni<List<TodoItem>> findAll();

  Uni<TodoItem> findById(UUID id);
}
