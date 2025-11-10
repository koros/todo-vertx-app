package com.example.todo.todo.service;

import com.example.todo.todo.domain.TodoItem;
import com.example.todo.todo.dto.CreateTodoRequest;
import com.example.todo.todo.repo.TodoRepository;
import io.smallrye.mutiny.Uni;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TodoServiceImpl implements TodoService {
  private final TodoRepository repo;

  @Inject
  public TodoServiceImpl(TodoRepository repo) {
    this.repo = repo;
  }

  @Override
  public Uni<TodoItem> create(CreateTodoRequest req) {
    var item = TodoItem.builder().title(req.getTitle()).completed(false).build();
    return repo.create(item);
  }

  @Override
  public Uni<List<TodoItem>> list() {
    return repo.findAll();
  }
}
