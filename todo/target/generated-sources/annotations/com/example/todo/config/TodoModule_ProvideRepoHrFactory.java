package com.example.todo.config;

import com.example.todo.todo.repo.TodoRepositoryHr;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class TodoModule_ProvideRepoHrFactory implements Factory<TodoRepositoryHr> {
  private final Provider<Stage.SessionFactory> sfProvider;

  public TodoModule_ProvideRepoHrFactory(Provider<Stage.SessionFactory> sfProvider) {
    this.sfProvider = sfProvider;
  }

  @Override
  public TodoRepositoryHr get() {
    return provideRepoHr(sfProvider.get());
  }

  public static TodoModule_ProvideRepoHrFactory create(Provider<Stage.SessionFactory> sfProvider) {
    return new TodoModule_ProvideRepoHrFactory(sfProvider);
  }

  public static TodoRepositoryHr provideRepoHr(Stage.SessionFactory sf) {
    return Preconditions.checkNotNullFromProvides(TodoModule.provideRepoHr(sf));
  }
}
