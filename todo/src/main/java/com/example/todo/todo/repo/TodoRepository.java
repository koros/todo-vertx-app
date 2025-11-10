package com.example.todo.todo.repo;

import com.example.todo.todo.domain.TodoItem;
import io.smallrye.mutiny.Uni;
import java.util.List;

public interface TodoRepository {
  Uni<TodoItem> create(TodoItem item);

  Uni<List<TodoItem>> findAll();
}
