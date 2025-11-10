package com.example.todo.config;

import com.example.todo.todo.api.TodoRoutes;
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
public final class TodoModule_ProvideRoutesFactory implements Factory<TodoRoutes> {
  private final Provider<TodoService> serviceProvider;

  public TodoModule_ProvideRoutesFactory(Provider<TodoService> serviceProvider) {
    this.serviceProvider = serviceProvider;
  }

  @Override
  public TodoRoutes get() {
    return provideRoutes(serviceProvider.get());
  }

  public static TodoModule_ProvideRoutesFactory create(Provider<TodoService> serviceProvider) {
    return new TodoModule_ProvideRoutesFactory(serviceProvider);
  }

  public static TodoRoutes provideRoutes(TodoService service) {
    return Preconditions.checkNotNullFromProvides(TodoModule.provideRoutes(service));
  }
}
