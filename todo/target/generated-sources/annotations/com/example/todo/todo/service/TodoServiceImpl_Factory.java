package com.example.todo.todo.service;

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

  public TodoServiceImpl_Factory(Provider<TodoRepository> repoProvider) {
    this.repoProvider = repoProvider;
  }

  @Override
  public TodoServiceImpl get() {
    return newInstance(repoProvider.get());
  }

  public static TodoServiceImpl_Factory create(Provider<TodoRepository> repoProvider) {
    return new TodoServiceImpl_Factory(repoProvider);
  }

  public static TodoServiceImpl newInstance(TodoRepository repo) {
    return new TodoServiceImpl(repo);
  }
}
