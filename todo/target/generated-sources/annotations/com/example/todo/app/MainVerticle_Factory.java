package com.example.todo.app;

import com.example.todo.todo.api.TodoRoutes;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import io.vertx.ext.web.Router;
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
public final class MainVerticle_Factory implements Factory<MainVerticle> {
  private final Provider<Router> routerProvider;

  private final Provider<TodoRoutes> routesProvider;

  public MainVerticle_Factory(Provider<Router> routerProvider,
      Provider<TodoRoutes> routesProvider) {
    this.routerProvider = routerProvider;
    this.routesProvider = routesProvider;
  }

  @Override
  public MainVerticle get() {
    return newInstance(routerProvider.get(), routesProvider.get());
  }

  public static MainVerticle_Factory create(Provider<Router> routerProvider,
      Provider<TodoRoutes> routesProvider) {
    return new MainVerticle_Factory(routerProvider, routesProvider);
  }

  public static MainVerticle newInstance(Router router, TodoRoutes routes) {
    return new MainVerticle(router, routes);
  }
}
