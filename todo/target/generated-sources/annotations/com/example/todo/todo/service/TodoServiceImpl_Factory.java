package com.example.todo.todo.service;

import com.example.todo.todo.mapper.TodoMapper;
import com.example.todo.todo.repo.TodoRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class TodoServiceImpl_Factory implements Factory<TodoServiceImpl> {
  private final Provider<TodoRepository> repoProvider;

  private final Provider<TodoMapper> mapperProvider;

  public TodoServiceImpl_Factory(Provider<TodoRepository> repoProvider,
      Provider<TodoMapper> mapperProvider) {
    this.repoProvider = repoProvider;
    this.mapperProvider = mapperProvider;
  }

  @Override
  public TodoServiceImpl get() {
    return newInstance(repoProvider.get(), mapperProvider.get());
  }

  public static TodoServiceImpl_Factory create(Provider<TodoRepository> repoProvider,
      Provider<TodoMapper> mapperProvider) {
    return new TodoServiceImpl_Factory(repoProvider, mapperProvider);
  }

  public static TodoServiceImpl newInstance(TodoRepository repo, TodoMapper mapper) {
    return new TodoServiceImpl(repo, mapper);
  }
}
