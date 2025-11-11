package com.example.todo.todo.service;

import com.example.todo.todo.domain.TodoItem;
import com.example.todo.todo.dto.CreateTodoRequest;
import com.example.todo.todo.mapper.TodoMapper;
import com.example.todo.todo.repo.TodoRepository;
import io.smallrye.mutiny.Uni;
import java.util.UUID;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TodoServiceImpl implements TodoService {
  private final TodoRepository repo;
  private final TodoMapper mapper;

  @Inject
  public TodoServiceImpl(TodoRepository repo, TodoMapper mapper) {
    this.repo = repo;
    this.mapper = mapper;
  }

  @Override
  public Uni<TodoItem> create(CreateTodoRequest request) {
    var entity = mapper.toEntity(request);
    return repo.create(entity);
  }

  @Override
  public Uni<java.util.List<TodoItem>> list() {
    return repo.findAll();
  }

  @Override
  public Uni<TodoItem> findById(UUID id) {
    return repo.findById(id);
  }
}
