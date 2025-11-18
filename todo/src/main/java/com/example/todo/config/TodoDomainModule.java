package com.example.todo.config;

import com.example.todo.todo.api.TodoRoutes;
import com.example.todo.todo.mapper.TodoMapper;
import com.example.todo.todo.repo.TodoRepository;
import com.example.todo.todo.repo.TodoRepositoryHr;
import com.example.todo.todo.service.TodoService;
import com.example.todo.todo.service.TodoServiceImpl;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import org.hibernate.reactive.stage.Stage;

/**
 * Provides the complete Todo domain layer including HTTP routes, business logic, and data access.
 *
 * <p>This module wires together the layered architecture:
 *
 * <ol>
 *   <li><strong>HTTP Layer:</strong> {@link TodoRoutes} - REST endpoints for todo CRUD operations
 *   <li><strong>Service Layer:</strong> {@link TodoService} - Business logic and orchestration
 *   <li><strong>Repository Layer:</strong> {@link TodoRepository} - Data access abstraction
 * </ol>
 *
 * <p>Uses MapStruct for DTO-to-entity mapping and Hibernate Reactive for non-blocking database
 * access.
 */
@Module
public interface TodoDomainModule {

  /** Binds the Hibernate Reactive implementation to the repository interface. */
  @Binds
  @Singleton
  TodoRepository bindRepository(TodoRepositoryHr implementation);

  /** Provides the todo service with injected repository and mapper. */
  @Provides
  @Singleton
  static TodoService todoService(TodoRepository repository, TodoMapper mapper) {
    return new TodoServiceImpl(repository, mapper);
  }

  /** Provides the HTTP routes handler with injected service. */
  @Provides
  @Singleton
  static TodoRoutes todoRoutes(TodoService service) {
    return new TodoRoutes(service);
  }

  /** Provides the Hibernate Reactive repository implementation. */
  @Provides
  @Singleton
  static TodoRepositoryHr todoRepositoryImplementation(Stage.SessionFactory sessionFactory) {
    return new TodoRepositoryHr(sessionFactory);
  }
}
