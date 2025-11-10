package com.example.todo.todo.repo;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import org.hibernate.reactive.stage.Stage;

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
public final class TodoRepositoryHr_Factory implements Factory<TodoRepositoryHr> {
  private final Provider<Stage.SessionFactory> sfProvider;

  public TodoRepositoryHr_Factory(Provider<Stage.SessionFactory> sfProvider) {
    this.sfProvider = sfProvider;
  }

  @Override
  public TodoRepositoryHr get() {
    return newInstance(sfProvider.get());
  }

  public static TodoRepositoryHr_Factory create(Provider<Stage.SessionFactory> sfProvider) {
    return new TodoRepositoryHr_Factory(sfProvider);
  }

  public static TodoRepositoryHr newInstance(Stage.SessionFactory sf) {
    return new TodoRepositoryHr(sf);
  }
}
