package com.example.todo.todo.repo;

import com.example.todo.todo.domain.TodoItem;
import io.smallrye.mutiny.Uni;
import java.util.List;
import java.util.UUID;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.hibernate.reactive.stage.Stage;

@Singleton
public class TodoRepositoryHr implements TodoRepository {
  private final Stage.SessionFactory sf;

  @Inject
  public TodoRepositoryHr(Stage.SessionFactory sf) {
    this.sf = sf;
  }

  @Override
  public Uni<TodoItem> create(TodoItem item) {
    return Uni.createFrom()
        .completionStage(
            sf.withTransaction(
                (s, tx) -> s.persist(item).thenCompose(v -> s.flush()).thenApply(v -> item)));
  }

  @Override
  public Uni<List<TodoItem>> findAll() {
    return Uni.createFrom()
        .completionStage(
            sf.withSession(
                s -> s.createSelectionQuery("from TodoItem", TodoItem.class).getResultList()));
  }

  @Override
  public Uni<TodoItem> findById(UUID id) {
    return Uni.createFrom().completionStage(sf.withSession(s -> s.find(TodoItem.class, id)));
  }
}
