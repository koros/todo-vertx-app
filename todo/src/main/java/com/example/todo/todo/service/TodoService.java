package com.example.todo.todo.service;

import com.example.todo.todo.domain.TodoItem;
import com.example.todo.todo.dto.CreateTodoRequest;
import io.smallrye.mutiny.Uni;
import java.util.List;

public interface TodoService {
  Uni<TodoItem> create(CreateTodoRequest req);

  Uni<List<TodoItem>> list();
}
