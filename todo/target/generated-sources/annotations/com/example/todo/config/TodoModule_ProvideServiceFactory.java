package com.example.todo.config;

import com.example.todo.todo.mapper.TodoMapper;
import com.example.todo.todo.repo.TodoRepository;
import com.example.todo.todo.service.TodoService;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class TodoModule_ProvideServiceFactory implements Factory<TodoService> {
  private final Provider<TodoRepository> repoProvider;

  private final Provider<TodoMapper> mapperProvider;

  public TodoModule_ProvideServiceFactory(Provider<TodoRepository> repoProvider,
      Provider<TodoMapper> mapperProvider) {
    this.repoProvider = repoProvider;
    this.mapperProvider = mapperProvider;
  }

  @Override
  public TodoService get() {
    return provideService(repoProvider.get(), mapperProvider.get());
  }

  public static TodoModule_ProvideServiceFactory create(Provider<TodoRepository> repoProvider,
      Provider<TodoMapper> mapperProvider) {
    return new TodoModule_ProvideServiceFactory(repoProvider, mapperProvider);
  }

  public static TodoService provideService(TodoRepository repo, TodoMapper mapper) {
    return Preconditions.checkNotNullFromProvides(TodoModule.provideService(repo, mapper));
  }
}
