package com.example.todo.todo.api;

import com.example.todo.todo.service.TodoService;
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
public final class TodoRoutes_Factory implements Factory<TodoRoutes> {
  private final Provider<TodoService> serviceProvider;

  public TodoRoutes_Factory(Provider<TodoService> serviceProvider) {
    this.serviceProvider = serviceProvider;
  }

  @Override
  public TodoRoutes get() {
    return newInstance(serviceProvider.get());
  }

  public static TodoRoutes_Factory create(Provider<TodoService> serviceProvider) {
    return new TodoRoutes_Factory(serviceProvider);
  }

  public static TodoRoutes newInstance(TodoService service) {
    return new TodoRoutes(service);
  }
}
