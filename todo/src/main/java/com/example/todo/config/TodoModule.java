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

@Module
public interface TodoModule {
  @Binds
  @Singleton
  abstract TodoRepository bindRepo(TodoRepositoryHr impl);

  @Provides
  @Singleton
  static TodoService provideService(TodoRepository repo, TodoMapper mapper) {
    return new TodoServiceImpl(repo, mapper);
  }

  @Provides
  @Singleton
  static TodoRoutes provideRoutes(TodoService service) {
    return new TodoRoutes(service);
  }

  @Provides
  @Singleton
  static TodoRepositoryHr provideRepoHr(Stage.SessionFactory sf) {
    return new TodoRepositoryHr(sf);
  }
}
